package dio.kanban.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name="board_column_id")
    private BoardColumn boardColumn;

    @OneToMany(mappedBy="card", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    private List<Block> block;
}
