package dio.kanban.repository;

import dio.kanban.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {

    @Query(value= """
    SELECT b.id, b.name, b.kind, (SELECT COUNT(c.id)
                                    FROM card c
                                    WHERE c.board_column_id = b.id
                                 )
    FROM board_column b
    WHERE b.board_id = :boardId
    ORDER BY b.`order`
    """, nativeQuery=true)
    List<Object[]> findByBoardIdWithCount(@Param("boardId") Long boardId);

    @Query(value= """
    SELECT c.id, c.title, c.description
    FROM board_column b
    LEFT JOIN card c
    ON c.board_column_id = b.id
    WHERE b.id = :id
    """, nativeQuery=true)
    List<Object[]> findCardsByBoardColumnId(Long id);

    List<BoardColumn> findAllByBoard_Id(Long boardId);

    BoardColumn findAllByBoard_IdAndOrder(Long boardId, int order);
}
