package dio.kanban.service;

import dio.kanban.dto.CardDetailsDto;
import dio.kanban.dto.CardDto;
import dio.kanban.entity.Card;
import dio.kanban.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CardService {

    CardRepository repository;

    @Autowired
    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public CardDetailsDto findDetailsById(long id) {
        Object[] o = repository.findDetailsById(id);

        if (o != null) {
            CardDetailsDto dto = new CardDetailsDto();
            dto.setId(id);
            dto.setTitle(o[1].toString());
            dto.setDescription(o[2].toString());
            dto.setBlocked(true);

            Timestamp ts = (Timestamp) o[3];
            dto.setBlockedAt(ts != null ? ts.toInstant().atOffset(ZoneOffset.UTC) : null);

            dto.setBlockReason(o[4].toString());
            dto.setColumnId((long) o[5]);
            dto.setColumnName(o[6].toString());
            dto.setBlocksAmount((int) o[7]);

            return dto;
        }
        return null;
    }

    public void showCard(long id) {
        CardDetailsDto dto = findDetailsById(id);

        if (dto == null) {
            System.out.printf("Não existe um card com o ID %s!\n\n", id);
        }
        else {
            System.out.printf("\n[%s] CARD %s: %s\n", id, dto.getTitle(), dto.getDescription());
            System.out.println(dto.isBlocked() ? "Está bloqueado pelo motivo: %s\n".formatted(dto.getBlockReason()) : "Não está bloqueado\n");
            System.out.printf("Número de vezes bloqueado: %d\n", dto.getBlocksAmount());
            System.out.printf("Atualmente, está na coluna %s [%s]\n\n", dto.getColumnName(), dto.getColumnId());
        }
    }
}
