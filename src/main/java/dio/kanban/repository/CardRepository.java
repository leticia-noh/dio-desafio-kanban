package dio.kanban.repository;

import dio.kanban.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value= """
    SELECT c.id, c.title, c.description, c.blocked_at, b.block_reason, c.board_column_id, bc.name, (SELECT COUNT(b2.id)
                                                                                                    FROM block b2
                                                                                                    WHERE b2.card_id = c.id)
    FROM card c
    LEFT JOIN block b
    ON c.id = b.card_id AND b.unblocked_at IS NULL
    INNER JOIN board_column bc
    ON bc.id = c.board_column_id
    WHERE c.id = :id
    """, nativeQuery=true)
    Object[] findDetailsById(@Param("id") long id);


}
