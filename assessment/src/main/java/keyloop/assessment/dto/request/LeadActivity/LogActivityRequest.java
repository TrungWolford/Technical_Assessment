package keyloop.assessment.dto.request.LeadActivity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import keyloop.assessment.model.ENUM.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    LogActivityRequest {
    @NotNull(message = "activityType is required")
    private ActivityType activityType;

    @NotBlank(message = "description is required")
    @Size(max = 1500, message = "description must be less than or equal to 1500 characters")
    private String description;

    @NotBlank(message = "createdBy is required")
    @Size(max = 80, message = "createdBy must be less than or equal to 80 characters")
    private String createdBy;

    private OffsetDateTime occurredAt;
}
