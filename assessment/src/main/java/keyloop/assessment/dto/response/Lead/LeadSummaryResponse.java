package keyloop.assessment.dto.response.Lead;

import keyloop.assessment.model.ENUM.LeadStatus;
import keyloop.assessment.model.Lead;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadSummaryResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String vehicleOfInterest;
    private String source;
    private LeadStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private int activityCount;

    public static LeadSummaryResponse from(Lead lead) {
        return LeadSummaryResponse.builder()
                .id(lead.getId())
                .fullName(lead.getFullName())
                .email(lead.getEmail())
                .phoneNumber(lead.getPhoneNumber())
                .vehicleOfInterest(lead.getVehicleOfInterest())
                .source(lead.getSource())
                .status(lead.getStatus())
                .createdAt(lead.getCreatedAt())
                .updatedAt(lead.getUpdatedAt())
                .activityCount(lead.getActivities() != null ? lead.getActivities().size() : 0)
                .build();
    }
}
