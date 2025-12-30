package dio.kanban.repository;

import dio.kanban.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    Block findByCard_IdAndUnblockReasonIsNull(Long cardId);
}
