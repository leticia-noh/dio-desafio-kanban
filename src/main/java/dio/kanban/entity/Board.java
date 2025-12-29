package dio.kanban.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="board", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    private List<BoardColumn> boardColumns;

    public BoardColumn returnInitialColumn() {

        for (BoardColumn c : boardColumns) {
            if (c.getKind().equals(BoardColumnKindEnum.INITIAL)) {
                return c;
            }
        }
        return null;
    }
}
