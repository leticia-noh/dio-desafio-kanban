package dio.kanban.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class Board {

    private Long id;

    private String name;

    @OneToMany(cascade= CascadeType.PERSIST)
    @JoinColumn(name="board")
    private List<BoardColumn> boardColumns;
}
