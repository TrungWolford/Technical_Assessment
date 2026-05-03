package keyloop.assessment.dto.request.LeadActivity;

import jakarta.validation.constraints.NotNull;
import keyloop.assessment.model.ENUM.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateActivityTypeRequest {
    @NotNull(message = "activityType is required")
    private ActivityType activityType;
}
