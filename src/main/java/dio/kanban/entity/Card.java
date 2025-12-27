package dio.kanban.entity;

import lombok.Data;

@Data
public class Card {

    private Long id;

    private String title;

    private String description;
}
