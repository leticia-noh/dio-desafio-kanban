package dio.kanban.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class BoardColumn {

    private Long id;

    private String name;

    private int order;

    private BoardColumnKindEnum kind;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;
}
