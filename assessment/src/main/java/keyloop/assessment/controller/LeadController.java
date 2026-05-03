package keyloop.assessment.controller;

import jakarta.validation.Valid;
import keyloop.assessment.common.ApiResponse;
import keyloop.assessment.model.ENUM.LeadStatus;
import keyloop.assessment.dto.request.Lead.CreateLeadRequest;
import keyloop.assessment.dto.request.LeadActivity.LogActivityRequest;
import keyloop.assessment.common.PageResponse;
import keyloop.assessment.dto.response.Lead.LeadDetailsResponse;
import keyloop.assessment.dto.response.Lead.LeadSummaryResponse;
import keyloop.assessment.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import keyloop.assessment.dto.request.Lead.UpdateLeadStatusRequest;
import keyloop.assessment.dto.request.LeadActivity.UpdateActivityTypeRequest;

@RestController
@RequestMapping("/api/v1/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<LeadSummaryResponse>>> getLeadInbox(
            @RequestParam(required = false) String dealershipId,
            @RequestParam(required = false) LeadStatus leadStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(leadService.getLeadInbox(dealershipId, leadStatus, page, size)));
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<ApiResponse<LeadDetailsResponse>> getLeadDetails(@PathVariable Long leadId) {
        return ResponseEntity.ok(ApiResponse.success(leadService.getLeadDetails(leadId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LeadDetailsResponse>> createLead(@Valid @RequestBody CreateLeadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(leadService.createLead(request), "Lead created successfully"));
    }

    @PostMapping("/{leadId}/activities")
    public ResponseEntity<ApiResponse<LeadDetailsResponse>> logActivity(@PathVariable Long leadId,
                                                                        @Valid @RequestBody LogActivityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(leadService.logActivity(leadId, request), "Activity logged successfully"));
    }

    @PatchMapping("/{leadId}/status")
    public ResponseEntity<ApiResponse<LeadDetailsResponse>> updateLeadStatus(@PathVariable Long leadId,
                                                                             @Valid @RequestBody UpdateLeadStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success(leadService.updateLeadStatus(leadId, request.getStatus()), "Lead status updated"));
    }

    @PatchMapping("/{leadId}/activities/{activityId}/type")
    public ResponseEntity<ApiResponse<LeadDetailsResponse>> updateActivityType(@PathVariable Long leadId,
                                                                               @PathVariable Long activityId,
                                                                               @Valid @RequestBody UpdateActivityTypeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(leadService.updateActivityType(leadId, activityId, request.getActivityType()), "Activity type updated"));
    }
}

