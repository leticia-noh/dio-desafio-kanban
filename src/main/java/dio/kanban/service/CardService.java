package dio.kanban.service;

import dio.kanban.dto.CardDetailsDto;
import dio.kanban.entity.Card;
import dio.kanban.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class CardService {

    CardRepository repository;
    BoardColumnService boardColumnService;

    @Autowired
    public CardService(CardRepository repository, BoardColumnService boardColumnService) {
        this.repository = repository;
        this.boardColumnService = boardColumnService;
    }

    public CardDetailsDto findDetailsById(long id) {
        List<Object[]> list = repository.findDetailsById(id);
        Object[] o = !list.isEmpty() ? list.getFirst() : null;

        if (o != null) {
            CardDetailsDto dto = new CardDetailsDto();
            dto.setId(id);
            dto.setTitle(o[1].toString());
            dto.setDescription(o[2].toString());

            Timestamp ts = (Timestamp) o[3];
            dto.setBlockedAt(ts != null ? ts.toInstant().atOffset(ZoneOffset.UTC) : null);
            dto.setBlocked(ts != null);
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
}
