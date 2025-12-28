package dio.kanban.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BoardColumn {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "`order`")
    private int order;

    private BoardColumnKindEnum kind;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;
}
