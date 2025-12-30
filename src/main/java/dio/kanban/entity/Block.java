package dio.kanban.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
public class Block {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime blockedAt;

    private String blockReason;

    private OffsetDateTime unblockedAt;

    private String unblockReason;

    @ManyToOne
    @JoinColumn(name="card_id")
    private Card card;
}
