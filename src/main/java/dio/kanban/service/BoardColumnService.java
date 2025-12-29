package dio.kanban.service;

import dio.kanban.dto.CardDto;
import dio.kanban.dto.BoardColumnDetailsDto;
import dio.kanban.entity.BoardColumn;
import dio.kanban.entity.BoardColumnKindEnum;
import dio.kanban.repository.BoardColumnRepository;
import dio.kanban.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardColumnService {

    private BoardRepository boardRepository;
    private BoardColumnRepository repository;

    @Autowired
    public BoardColumnService(BoardColumnRepository repository, BoardRepository boardRepository) {
        this.repository = repository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public BoardColumn insert(BoardColumn entity) {
        return repository.save(entity);
    }

    public BoardColumn findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<CardDto> findCardsByBoardColumnId(Long id) {
        List<Object[]> list = repository.findCardsByBoardColumnId(id);

        return list.stream().map(o -> new CardDto(
                ((long) o[0]),
                (o[1].toString()),
                (o[2].toString())
                )).toList();
    }

    public void showColumn(long id) {
        List<CardDto> list = findCardsByBoardColumnId(id);
        BoardColumn column = findById(id);
        if (column != null) {
            System.out.printf("COLUNA %s [%s]\n]", column.getName().toUpperCase(), column.getKind());
            for (CardDto c : list) {
                System.out.printf("[%s] %s: %s\n", c.getCardId(), c.getCardTitle(), c.getCardDescription());
            }
        }
    }

    public List<BoardColumnDetailsDto> findByBoardIdWithCount(Long boardId) {
        List<Object[]> list = repository.findByBoardIdWithCount(boardId);
        return list.stream().map(o -> new BoardColumnDetailsDto(
                ((long) o[0]),
                (o[1].toString()),
                ((BoardColumnKindEnum) o[2]),
                ((int) o[3])
        )).toList();
    }

}
