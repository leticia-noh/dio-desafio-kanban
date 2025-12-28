package dio.kanban.service;

import dio.kanban.entity.Board;
import dio.kanban.entity.BoardColumn;
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

    public BoardColumn findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<BoardColumn> findByBoardId(Long id) {
        return repository.findAllByBoard_IdOrderByOrder(id);
    }

}
