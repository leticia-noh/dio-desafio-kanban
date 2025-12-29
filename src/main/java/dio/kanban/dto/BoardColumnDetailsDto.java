package dio.kanban.dto;

import dio.kanban.entity.BoardColumnKindEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class BoardColumnDetailsDto {

    private Long id;

    private String name;

    private BoardColumnKindEnum kind;

    private int cardsAmount;
}
