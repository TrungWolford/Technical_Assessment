package keyloop.assessment.dto.request.Lead;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLeadRequest {
    @NotBlank(message = "fullName is required")
    @Size(max = 120, message = "fullName must be less than or equal to 120 characters")
    private String fullName;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Size(max = 120, message = "email must be less than or equal to 120 characters")
    private String email;

    @NotBlank(message = "phoneNumber is required")
    @Size(max = 40, message = "phoneNumber must be less than or equal to 40 characters")
    private String phoneNumber;

    @NotBlank(message = "vehicleOfInterest is required")
    @Size(max = 120, message = "vehicleOfInterest must be less than or equal to 120 characters")
    private String vehicleOfInterest;

    @NotBlank(message = "source is required")
    @Size(max = 80, message = "source must be less than or equal to 80 characters")
    private String source;

    @Size(max = 1500, message = "notes must be less than or equal to 1500 characters")
    private String notes;

    @NotBlank(message = "dealershipId is required")
    @Size(max = 80, message = "dealershipId must be less than or equal to 80 characters")
    private String dealershipId;
}

