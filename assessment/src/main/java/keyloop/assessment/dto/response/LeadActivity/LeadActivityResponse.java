package keyloop.assessment.dto.response.LeadActivity;

import keyloop.assessment.model.ENUM.ActivityType;
import keyloop.assessment.model.LeadActivity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadActivityResponse {
    private Long id;
    private ActivityType activityType;
    private String description;
    private String createdBy;
    private OffsetDateTime occurredAt;
    private OffsetDateTime createdAt;

    public static LeadActivityResponse from(LeadActivity activity) {
        return LeadActivityResponse.builder()
                .id(activity.getId())
                .activityType(activity.getActivityType())
                .description(activity.getDescription())
                .createdBy(activity.getCreatedBy())
                .occurredAt(activity.getOccurredAt())
                .createdAt(activity.getCreatedAt())
                .build();
    }
}
