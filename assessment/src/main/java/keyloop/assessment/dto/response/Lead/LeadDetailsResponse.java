package keyloop.assessment.dto.response.Lead;

import keyloop.assessment.dto.response.LeadActivity.LeadActivityResponse;
import keyloop.assessment.model.ENUM.LeadStatus;
import keyloop.assessment.model.Lead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadDetailsResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String vehicleOfInterest;
    private String source;
    private String notes;
    private LeadStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<LeadActivityResponse> activities;

    public static LeadDetailsResponse from(Lead lead) {
        return LeadDetailsResponse.builder()
                .id(lead.getId())
                .fullName(lead.getFullName())
                .email(lead.getEmail())
                .phoneNumber(lead.getPhoneNumber())
                .vehicleOfInterest(lead.getVehicleOfInterest())
                .source(lead.getSource())
                .notes(lead.getNotes())
                .status(lead.getStatus())
                .createdAt(lead.getCreatedAt())
                .updatedAt(lead.getUpdatedAt())
                .activities(lead.getActivities() != null ?
                        lead.getActivities().stream()
                                .map(LeadActivityResponse::from)
                                .collect(Collectors.toList())
                        : List.of())
                .build();
    }
}

