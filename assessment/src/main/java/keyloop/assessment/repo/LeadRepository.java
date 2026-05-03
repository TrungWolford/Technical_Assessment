package keyloop.assessment.repo;

import keyloop.assessment.model.Lead;
import keyloop.assessment.model.ENUM.LeadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    // Filter by status
    Page<Lead> findByDealershipIdAndStatus(
	    String dealershipId, LeadStatus status, Pageable pageable);

    // Filter by dealership only
    Page<Lead> findByDealershipId(String dealershipId, Pageable pageable);

    // Filter by status only
    Page<Lead> findByStatus(LeadStatus status, Pageable pageable);

        // Search by customer full name
        Page<Lead> findByDealershipIdAndFullNameContainingIgnoreCase(
            String dealershipId, String name, Pageable pageable);
}
