package dio.kanban.repository;

import dio.kanban.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
    List<BoardColumn> findAllByBoard_Id(Long boardId);

    List<BoardColumn> findAllByBoard_IdOrderByOrder(Long boardId);
}
