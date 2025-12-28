package dio.kanban.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
