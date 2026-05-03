package keyloop.assessment.service;

import keyloop.assessment.common.PageResponse;
import keyloop.assessment.dto.request.Lead.CreateLeadRequest;
import keyloop.assessment.dto.request.LeadActivity.LogActivityRequest;
import keyloop.assessment.dto.response.Lead.LeadDetailsResponse;
import keyloop.assessment.dto.response.Lead.LeadSummaryResponse;
import keyloop.assessment.model.ENUM.LeadStatus;

public interface LeadService {

    PageResponse<LeadSummaryResponse> getLeadInbox(String dealershipId, LeadStatus leadStatus, int page, int size);

    LeadDetailsResponse getLeadDetails(Long leadId);

    LeadDetailsResponse createLead(CreateLeadRequest request);

    LeadDetailsResponse logActivity(Long leadId, LogActivityRequest request);

    LeadDetailsResponse updateLeadStatus(Long leadId, keyloop.assessment.model.ENUM.LeadStatus status);

    LeadDetailsResponse updateActivityType(Long leadId, Long activityId, keyloop.assessment.model.ENUM.ActivityType activityType);
}

