package dio.kanban.service;

import dio.kanban.dto.CardDetailsDto;
import dio.kanban.entity.Block;
import dio.kanban.entity.BoardColumn;
import dio.kanban.entity.Card;
import dio.kanban.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;

@Service
public class CardService {

    CardRepository repository;
    BlockService blockService;
    BoardColumnService boardColumnService;

    @Autowired
    public CardService(CardRepository repository, BoardColumnService boardColumnService, BlockService blockService) {
        this.repository = repository;
        this.boardColumnService = boardColumnService;
        this.blockService = blockService;
    }

    public CardDetailsDto findDetailsById(long id) {
        List<Object[]> list = repository.findDetailsById(id);
        Object[] o = !list.isEmpty() ? list.getFirst() : null;

        if (o != null) {
            CardDetailsDto dto = new CardDetailsDto();
            dto.setId(id);
            dto.setTitle(o[1].toString());
            dto.setDescription(o[2].toString());

            LocalDateTime time = (LocalDateTime) o[3];
            dto.setBlockedAt(time != null ? time.atOffset(UTC) : null);
            dto.setBlocked(time != null);
            dto.setBlockReason(o[4] != null ? o[4].toString() : "-");
            dto.setColumnId((long) o[5]);
            dto.setColumnName(o[6].toString());
            dto.setBlocksAmount((long) o[7]);

            return dto;
        }
        return null;
    }

    @Transactional
    public Card insert(Card card) {
        return repository.save(card);
    }

    @Transactional
    public Card updateColumn(long id, BoardColumn boardColumn) {
        Card card = repository.findById(id).orElse(null);
        if (card == null) {
            return null;
        }

        card.setBoardColumn(boardColumn);
        return repository.save(card);
    }

    public Card block(long id, String reason) {
        Block block = new Block();
        block.setBlockReason(reason);
        block.setBlockedAt(OffsetDateTime.now());

        Card card = repository.findById(id).orElse(null);
        if  (card == null) {
            return null;
        }
        block.setCard(card);
        card.getBlock().add(block);

        Block result = blockService.insert(block);

        if (result == null) {
            return null;
        }

        return card;
    }
}
