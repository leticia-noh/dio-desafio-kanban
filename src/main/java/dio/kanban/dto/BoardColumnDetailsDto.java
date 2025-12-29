package dio.kanban.dto;

import dio.kanban.entity.BoardColumnKindEnum;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BoardColumnDetailsDto {

    private Long id;

    private String name;

    private BoardColumnKindEnum kind;

    private long cardsAmount;

}
