package keyloop.assessment.model;


import jakarta.persistence.*;
import keyloop.assessment.model.ENUM.LeadStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "leads")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String vehicleOfInterest;

    @Column(nullable = false)
    private String source;

    @Column(length = 1500)
    private String notes;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'UNKNOWN'")
    private String dealershipId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeadStatus status;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("occurredAt DESC, createdAt DESC")
    @Builder.Default
    private List<LeadActivity> activities = new ArrayList<>();

    public void addActivity(LeadActivity activity) {
        activities.add(activity);
        activity.setLead(this);
    }

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = LeadStatus.defaultValue();
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
