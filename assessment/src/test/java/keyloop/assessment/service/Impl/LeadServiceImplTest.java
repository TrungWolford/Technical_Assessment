package keyloop.assessment.service.Impl;

import keyloop.assessment.dto.request.LeadActivity.LogActivityRequest;
import keyloop.assessment.exception.ResourceNotFoundException;
import keyloop.assessment.model.ENUM.ActivityType;
import keyloop.assessment.model.ENUM.LeadStatus;
import keyloop.assessment.model.Lead;
import keyloop.assessment.model.LeadActivity;
import keyloop.assessment.repo.LeadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeadServiceImplTest {

    @Mock
    private LeadRepository leadRepository;

    @InjectMocks
    private LeadServiceImpl leadService;

    @Test
    // Verifies that when both dealershipId and leadStatus are null, the service defaults status to NEW and applies DESC sort on createdAt.
    void getLeadInbox_defaultsToNewStatusWhenLeadStatusNullAndNoDealership() {
        Lead lead = buildLead(1L, LeadStatus.NEW);
        Page<Lead> page = new PageImpl<>(List.of(lead));
        when(leadRepository.findByStatus(eq(LeadStatus.defaultValue()), any(Pageable.class)))
                .thenReturn(page);

        leadService.getLeadInbox(null, null, 0, 10);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(leadRepository).findByStatus(eq(LeadStatus.defaultValue()), pageableCaptor.capture());
        assertSortIsCreatedAtDesc(pageableCaptor.getValue());
    }

    @Test
    // Verifies that when leadStatus is null but dealershipId is provided, the service uses default status and filters by dealership.
    void getLeadInbox_filtersByDealershipAndDefaultStatusWhenLeadStatusNull() {
        Lead lead = buildLead(1L, LeadStatus.NEW);
        Page<Lead> page = new PageImpl<>(List.of(lead));
        when(leadRepository.findByDealershipIdAndStatus(eq("D1"), eq(LeadStatus.defaultValue()), any(Pageable.class)))
                .thenReturn(page);

        leadService.getLeadInbox("D1", null, 1, 5);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(leadRepository).findByDealershipIdAndStatus(eq("D1"), eq(LeadStatus.defaultValue()), pageableCaptor.capture());
        assertSortIsCreatedAtDesc(pageableCaptor.getValue());
    }

    @Test
    // Verifies that when dealershipId is null and leadStatus is provided, the service filters by that status.
    void getLeadInbox_filtersByProvidedStatusWhenNoDealership() {
        Lead lead = buildLead(1L, LeadStatus.WON);
        Page<Lead> page = new PageImpl<>(List.of(lead));
        when(leadRepository.findByStatus(eq(LeadStatus.WON), any(Pageable.class)))
                .thenReturn(page);

        leadService.getLeadInbox(null, LeadStatus.WON, 0, 10);

        verify(leadRepository).findByStatus(eq(LeadStatus.WON), any(Pageable.class));
    }

    @Test
    // Verifies that updating status throws ResourceNotFoundException when the lead does not exist.
    void updateLeadStatus_throwsWhenLeadMissing() {
        when(leadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> leadService.updateLeadStatus(99L, LeadStatus.CONTACTED));
    }

    @Test
    // Verifies that updating status persists the new status for an existing lead.
    void updateLeadStatus_updatesAndSaves() {
        Lead lead = buildLead(1L, LeadStatus.NEW);
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        when(leadRepository.save(any(Lead.class))).thenAnswer(invocation -> invocation.getArgument(0));

        leadService.updateLeadStatus(1L, LeadStatus.QUALIFIED);

        assertEquals(LeadStatus.QUALIFIED, lead.getStatus());
        verify(leadRepository).save(lead);
    }

    @Test
    // Verifies that updating activity type throws ResourceNotFoundException when activity is not found on the lead.
    void updateActivityType_throwsWhenActivityMissing() {
        Lead lead = buildLead(1L, LeadStatus.NEW);
        lead.setActivities(new ArrayList<>());
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));

        assertThrows(ResourceNotFoundException.class,
                () -> leadService.updateActivityType(1L, 77L, ActivityType.CALL));
    }

    @Test
    // Verifies that updating activity type persists the new type for an existing activity.
    void updateActivityType_updatesAndSaves() {
        Lead lead = buildLead(1L, LeadStatus.NEW);
        LeadActivity activity = buildActivity(10L, ActivityType.EMAIL);
        lead.setActivities(new ArrayList<>(List.of(activity)));
        when(leadRepository.findById(1L)).thenReturn(Optional.of(lead));
        when(leadRepository.save(any(Lead.class))).thenAnswer(invocation -> invocation.getArgument(0));

        leadService.updateActivityType(1L, 10L, ActivityType.SMS);

        assertEquals(ActivityType.SMS, activity.getActivityType());
        verify(leadRepository).save(lead);
    }

    @Test
    // Verifies that logging activity throws ResourceNotFoundException when the lead does not exist.
    void logActivity_throwsWhenLeadMissing() {
        when(leadRepository.findById(12L)).thenReturn(Optional.empty());

        LogActivityRequest request = LogActivityRequest.builder()
                .activityType(ActivityType.CALL)
                .description("desc")
                .createdBy("user")
                .build();

        assertThrows(ResourceNotFoundException.class,
                () -> leadService.logActivity(12L, request));
    }

    private Lead buildLead(Long id, LeadStatus status) {
        return Lead.builder()
                .id(id)
                .fullName("Test User")
                .email("test@example.com")
                .phoneNumber("123")
                .vehicleOfInterest("Car")
                .source("Web")
                .dealershipId("D1")
                .status(status)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .activities(new ArrayList<>())
                .build();
    }

    private LeadActivity buildActivity(Long id, ActivityType activityType) {
        return LeadActivity.builder()
                .id(id)
                .activityType(activityType)
                .description("desc")
                .createdBy("user")
                .occurredAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    private void assertSortIsCreatedAtDesc(Pageable pageable) {
        Sort.Order order = pageable.getSort().getOrderFor("createdAt");
        assertNotNull(order);
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }
}
