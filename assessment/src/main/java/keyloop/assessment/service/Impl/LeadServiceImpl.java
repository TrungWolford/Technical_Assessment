package keyloop.assessment.service.Impl;

import keyloop.assessment.dto.request.Lead.CreateLeadRequest;
import keyloop.assessment.dto.request.LeadActivity.LogActivityRequest;
import keyloop.assessment.dto.response.Lead.LeadDetailsResponse;
import keyloop.assessment.dto.response.Lead.LeadSummaryResponse;
import keyloop.assessment.dto.response.LeadActivity.LeadActivityResponse;
import keyloop.assessment.model.ENUM.LeadStatus;
import keyloop.assessment.model.Lead;
import keyloop.assessment.model.LeadActivity;
import keyloop.assessment.repo.LeadRepository;
import keyloop.assessment.exception.ResourceNotFoundException;
import keyloop.assessment.service.LeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;

    @Override
    @Transactional(readOnly = true)
    public keyloop.assessment.common.PageResponse<LeadSummaryResponse> getLeadInbox(String dealershipId, LeadStatus leadStatus, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        LeadStatus effectiveLeadStatus = leadStatus == null ? LeadStatus.defaultValue() : leadStatus;
        log.info("Fetching lead inbox for dealershipId={} leadStatus={} page={} size={}", dealershipId, effectiveLeadStatus, page, size);

        org.springframework.data.domain.Page<Lead> leadPage;
        if (dealershipId != null) {
            leadPage = leadRepository.findByDealershipIdAndStatus(dealershipId, effectiveLeadStatus, pageable);
        } else {
            leadPage = leadRepository.findByStatus(effectiveLeadStatus, pageable);
        }

        java.util.List<LeadSummaryResponse> content = leadPage.getContent().stream()
            .map(this::toLeadSummary)
            .toList();

        log.debug("Fetched {} leads for dealershipId={} leadStatus={}", content.size(), dealershipId, effectiveLeadStatus);

        return keyloop.assessment.common.PageResponse.of(leadPage, content);
    }

    @Override
    @Transactional(readOnly = true)
    public LeadDetailsResponse getLeadDetails(Long leadId) {
        log.info("Fetching lead details for id={}", leadId);
        Lead lead = findLeadById(leadId);
        LeadDetailsResponse resp = toLeadDetails(lead);
        log.debug("Found lead id={} with {} activities", leadId, resp.getActivities() == null ? 0 : resp.getActivities().size());
        return resp;
    }

    @Override
    public LeadDetailsResponse createLead(CreateLeadRequest request) {
        log.info("Creating lead for dealershipId={} fullName={}", request.getDealershipId(), request.getFullName());
        Lead lead = Lead.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .vehicleOfInterest(request.getVehicleOfInterest())
                .source(request.getSource())
                .dealershipId(request.getDealershipId())
                .notes(request.getNotes())
                .status(LeadStatus.defaultValue())
                .build();

        Lead savedLead = leadRepository.save(lead);
        log.debug("Created lead id={} for dealershipId={}", savedLead.getId(), savedLead.getDealershipId());
        return toLeadDetails(savedLead);
    }

    @Override
    @Transactional
    public LeadDetailsResponse logActivity(Long leadId, LogActivityRequest request) {
        log.info("Logging activity for leadId={} activityType={}", leadId, request.getActivityType());
        Lead lead = findLeadById(leadId);

        LeadActivity activity = LeadActivity.builder()
                .activityType(request.getActivityType())
                .description(request.getDescription())
                .createdBy(request.getCreatedBy())
                .occurredAt(request.getOccurredAt())
                .build();

        lead.addActivity(activity);
        Lead savedLead = leadRepository.save(lead);

        log.debug("Logged activity id={} for leadId={}", activity.getId(), leadId);

        return toLeadDetails(savedLead);
    }

    @Override
    public LeadDetailsResponse updateLeadStatus(Long leadId, keyloop.assessment.model.ENUM.LeadStatus status) {
        Lead lead = findLeadById(leadId);
        lead.setStatus(status);
        Lead saved = leadRepository.save(lead);
        return toLeadDetails(saved);
    }

    @Override
    public LeadDetailsResponse updateActivityType(Long leadId, Long activityId, keyloop.assessment.model.ENUM.ActivityType activityType) {
        Lead lead = findLeadById(leadId);
        LeadActivity target = lead.getActivities().stream()
                .filter(a -> a.getId() != null && a.getId().equals(activityId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Activity with id " + activityId + " was not found for lead " + leadId));

        target.setActivityType(activityType);
        Lead saved = leadRepository.save(lead);
        return toLeadDetails(saved);
    }

    private Lead findLeadById(Long leadId) {
        return leadRepository.findById(leadId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead with id " + leadId + " was not found"));
    }

    private LeadSummaryResponse toLeadSummary(Lead lead) {
        return new LeadSummaryResponse(
                lead.getId(),
                lead.getFullName(),
                lead.getEmail(),
                lead.getPhoneNumber(),
                lead.getVehicleOfInterest(),
                lead.getSource(),
                lead.getStatus(),
                lead.getCreatedAt(),
                lead.getUpdatedAt(),
                lead.getActivities() == null ? 0 : lead.getActivities().size()
        );
    }

    private LeadDetailsResponse toLeadDetails(Lead lead) {
        List<LeadActivityResponse> activityResponses = lead.getActivities().stream()
            .map(this::toActivityResponse)
            .toList();

        return new LeadDetailsResponse(
                lead.getId(),
                lead.getFullName(),
                lead.getEmail(),
                lead.getPhoneNumber(),
                lead.getVehicleOfInterest(),
                lead.getSource(),
                lead.getNotes(),
                lead.getStatus(),
                lead.getCreatedAt(),
                lead.getUpdatedAt(),
                activityResponses
        );
    }

    private LeadActivityResponse toActivityResponse(LeadActivity activity) {
        return new LeadActivityResponse(
                activity.getId(),
                activity.getActivityType(),
                activity.getDescription(),
                activity.getCreatedBy(),
                activity.getOccurredAt(),
                activity.getCreatedAt()
        );
    }
}

