package dio.kanban.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDetailsDto {

    Long id;

    String title;

    String description;

    boolean blocked;

    OffsetDateTime blockedAt;

    String blockReason;

    long blocksAmount;

    Long columnId;

    String columnName;

}
