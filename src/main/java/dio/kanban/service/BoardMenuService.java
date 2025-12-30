package dio.kanban.service;

import dio.kanban.dto.BoardColumnDetailsDto;
import dio.kanban.dto.CardDetailsDto;
import dio.kanban.dto.CardDto;
import dio.kanban.entity.Board;
import dio.kanban.entity.BoardColumn;
import dio.kanban.entity.BoardColumnKindEnum;
import dio.kanban.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardMenuService {

    BoardService boardService;

    BoardColumnService boardColumnService;

    CardService cardService;

    @Autowired
    public BoardMenuService(BoardService boardService, BoardColumnService boardColumnService, CardService cardService) {
        this.boardService = boardService;
        this.boardColumnService = boardColumnService;
        this.cardService = cardService;
    }

    @Transactional
    public void insertCard(String title, String description, long boardId) {
        Card card = new Card();
        card.setTitle(title);
        card.setDescription(description);

        Board board = boardService.findById(boardId);

        BoardColumn boardColumn = board.getInitialColumn();
        card.setBoardColumn(boardColumn);

        Card created = cardService.insert(card);
        if (created == null) {
            System.out.println("Não foi possível criar o card!\n");
        }
        else {
            System.out.printf("O card foi criado com sucesso! Ele possui o ID: %s\n\n", created.getId());
        }
    }

    public void moveCard(long id, List<BoardColumn> columns) {
        CardDetailsDto dto = cardService.findDetailsById(id);
        if (dto == null) {
            System.out.printf("O card de ID %s não foi encontrado!\n\n", id);
            return;
        }

        if (dto.isBlocked()) {
            System.out.println("O card está bloqueado, não é possível movê-lo\n");
            return;
        }

        BoardColumn column = boardColumnService.findById(dto.getColumnId());
        if (column == null) {
            System.out.println("Erro ao buscar a coluna do card\n");
            return;
        }

        if (!column.getBoard().getId().equals(columns.getFirst().getBoard().getId())) {
            System.out.printf("O card de ID %s não pertence ao kanban atual\n\n", id);
            return;
        }

        if (column.getKind().equals(BoardColumnKindEnum.FINAL)) {
            System.out.println("O card já se encontra na coluna final. Não é mais possível movê-lo\n");
            return;
        }

        if (column.getKind().equals(BoardColumnKindEnum.CANCEL)) {
            System.out.println("Esse card foi cancelado, não é possível movê-lo\n");
            return;
        }

        BoardColumn boardColumn = boardColumnService.findByBoardIdAndOrder(column.getBoard().getId(), column.getOrder() + 1);
        Card updated = cardService.updateColumn(id, boardColumn);

        if (updated == null) {
            System.out.println("Não foi possível mover o card\n");
        }
        else {
            System.out.printf("O card foi movido com sucesso! Agora, ele se encontra na coluna %s\n\n", updated.getBoardColumn().getName());
        }
    }

    public void cancelCard(long id, BoardColumn cancelColumn) {
        CardDetailsDto dto = cardService.findDetailsById(id);
        if (dto == null) {
            System.out.printf("O card de ID %s não foi encontrado!\n\n", id);
            return;
        }

        if (dto.isBlocked()) {
            System.out.println("O card está bloqueado, não é possível cancelá-lo\n");
            return;
        }

        BoardColumn column = boardColumnService.findById(dto.getColumnId());
        if (!column.getBoard().getId().equals(cancelColumn.getBoard().getId())) {
            System.out.printf("O card de ID %s não pertence ao kanban atual\n\n", id);
            return;
        }

        if (column.getKind().equals(BoardColumnKindEnum.FINAL)) {
            System.out.println("O card já se encontra na coluna final. Não é mais possível movê-lo\n");
            return;
        }

        if (column.getKind().equals(BoardColumnKindEnum.CANCEL)) {
            System.out.println("O card já está cancelado\n");
            return;
        }

        Card updated = cardService.updateColumn(id, cancelColumn);

        if (updated == null) {
            System.out.println("Não foi possível cancelar o card\n");
        }
        else {
            System.out.printf("O card foi cancelado com sucesso! Agora, ele se encontra na coluna %s\n\n", updated.getBoardColumn().getName());
        }
    }

    public void blockCard(long id, String reason, Board board) {
        CardDetailsDto dto = cardService.findDetailsById(id);
        if (dto == null) {
            System.out.printf("O card de ID %s não foi encontrado!\n\n", id);
            return;
        }

        if (dto.isBlocked()) {
            System.out.println("O card já está bloqueado\n");
            return;
        }

        BoardColumn column = boardColumnService.findById(dto.getColumnId());
        if (column == null) {
            System.out.println("Erro ao buscar a coluna do card\n");
            return;
        }

        if (!column.getBoard().getId().equals(board.getId())) {
            System.out.printf("O card de ID %s não pertence ao kanban atual\n\n", id);
            return;
        }

        if (column.getKind().equals(BoardColumnKindEnum.FINAL)) {
            System.out.println("O card se encontra na coluna final. Não é possível bloqueá-lo\n");
            return;
        }

        if (column.getKind().equals(BoardColumnKindEnum.CANCEL)) {
            System.out.println("Esse card foi cancelado, não é possível bloqueá-lo\n");
            return;
        }

        Card card = cardService.block(id, reason);
        if (card == null) {
            System.out.println("Não foi possível bloquear o card\n");
        }
        else {
            System.out.printf("O card foi bloqueado com sucesso!\n\n", card.getBoardColumn().getName());
        }
    }

    public void unblockCard(long id, String reason, Board board) {
        CardDetailsDto dto = cardService.findDetailsById(id);
        if (dto == null) {
            System.out.printf("O card de ID %s não foi encontrado!\n\n", id);
            return;
        }

        if (!dto.isBlocked()) {
            System.out.println("O card não está bloqueado\n");
            return;
        }

        Card card = cardService.unblock(id, reason);

        if (card == null) {
            System.out.println("Não foi possível desbloquear o card\n");
        }
        else {
            System.out.printf("O card foi desbloqueado com sucesso!\n\n", card.getBoardColumn().getName());
        }
    }

    public void showBoardDetails(long id) {
        Board entity = boardService.findById(id);
        List<BoardColumnDetailsDto> columns = boardColumnService.findByBoardIdWithCount(id);

        if (entity != null) {
            System.out.printf("\n|----------- KANBAN %s [ID : %s] -----------|\n", entity.getName(), id);
            columns.forEach(c -> {
                System.out.printf("| Coluna %s | %s | %s card(s) |\n", c.getName(), c.getKind(), c.getCardsAmount());
            });
        }
        System.out.println();
    }

    public void showColumn(long id) {
        List<CardDto> list = boardColumnService.findCardsByBoardColumnId(id);
        BoardColumn column = boardColumnService.findById(id);
        if (column != null) {
            System.out.printf("COLUNA %s [%s]\n", column.getName().toUpperCase(), column.getKind());
            if (list != null) {
                for (CardDto c : list) {
                    System.out.printf("[%s] %s: %s\n", c.getCardId(), c.getCardTitle(), c.getCardDescription());
                }
            }
            System.out.println();
        }
    }

    public void showCard(long id) {
        CardDetailsDto dto = cardService.findDetailsById(id);

        if (dto == null) {
            System.out.printf("Não existe um card com o ID %s!\n\n", id);
        }
        else {
            System.out.printf("\n[%s] CARD %s: %s\n", id, dto.getTitle(), dto.getDescription());
            System.out.println(dto.isBlocked() ? "Está bloqueado pelo motivo: %s\n".formatted(dto.getBlockReason()) : "Não está bloqueado\n");
            System.out.printf("Número de vezes bloqueado: %d\n", dto.getBlocksAmount());
            System.out.printf("Atualmente, está na coluna %s [ID: %s]\n\n", dto.getColumnName(), dto.getColumnId());
        }
    }
}
