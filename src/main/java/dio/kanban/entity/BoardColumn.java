package dio.kanban.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class BoardColumn {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "`order`")
    private int order;

    @Enumerated(EnumType.STRING)
    private BoardColumnKindEnum kind;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @OneToMany(mappedBy="boardColumn", cascade=CascadeType.PERSIST)
    List<Card> cards = new ArrayList<>();
}
