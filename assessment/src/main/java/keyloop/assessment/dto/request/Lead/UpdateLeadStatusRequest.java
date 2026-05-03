package keyloop.assessment.dto.request.Lead;

import jakarta.validation.constraints.NotNull;
import keyloop.assessment.model.ENUM.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateLeadStatusRequest {
    @NotNull(message = "status is required")
    private LeadStatus status;
}
