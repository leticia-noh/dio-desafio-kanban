package dio.kanban.entity;

import lombok.Data;

@Data
public class BoardColumn {

    private Long id;

    private String name;

    private int order;

    private BoardColumnKindEnum kind;
}
