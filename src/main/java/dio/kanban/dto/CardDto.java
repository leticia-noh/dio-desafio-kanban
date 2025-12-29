package dio.kanban.dto;

import dio.kanban.entity.BoardColumnKindEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardDto {

    private long cardId;

    private String cardTitle;

    private String cardDescription;

}
