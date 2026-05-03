package keyloop.assessment.model;


import jakarta.persistence.*;
import keyloop.assessment.model.ENUM.ActivityType;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "lead_activities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(nullable = false, length = 1500)
    private String description;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private OffsetDateTime occurredAt;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (this.occurredAt == null) {
            this.occurredAt = now;
        }
        this.createdAt = now;
    }
}

