package dio.kanban.repository;

import dio.kanban.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    List<BoardColumn> findAllByBoard_Id(Long boardId);

    List<BoardColumn> findAllByBoard_IdOrderByOrder(Long boardId);

    @Query(value= """
    SELECT b.id, b.name, b.kind, COUNT(
        SELECT c.id
        FROM card c
        WHERE c.board_column_id = b.id
        ))
    FROM board_column b
    WHERE b.board_id = :id
    ORDER BY b.`order`
    """, nativeQuery=true)
    List<Object[]> findByBoardIdWithCount(Long id);
}
