package keyloop.assessment.repo;

import keyloop.assessment.model.LeadActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadActivityRepository extends JpaRepository<LeadActivity, Long> {
    List<LeadActivity> findByLeadIdOrderByCreatedAtAsc(Long leadId);
}
