package dio.kanban.service;

import dio.kanban.dto.CardDto;
import dio.kanban.dto.BoardColumnDetailsDto;
import dio.kanban.entity.BoardColumn;
import dio.kanban.entity.BoardColumnKindEnum;
import dio.kanban.repository.BoardColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardColumnService {

    private BoardColumnRepository repository;

    @Autowired
    public BoardColumnService(BoardColumnRepository repository) {
        this.repository = repository;
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

    public List<BoardColumnDetailsDto> findByBoardIdWithCount(Long boardId) {
        List<Object[]> list = repository.findByBoardIdWithCount(boardId);
        return list.stream().map(o -> new BoardColumnDetailsDto(
                ((long) o[0]),
                (o[1].toString()),
                (BoardColumnKindEnum.valueOf(o[2].toString())),
                ((long) o[3])
        )).toList();
    }

}
