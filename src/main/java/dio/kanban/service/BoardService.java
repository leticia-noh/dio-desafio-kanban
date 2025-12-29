package dio.kanban.service;

import dio.kanban.dto.BoardColumnDetailsDto;
import dio.kanban.entity.Board;
import dio.kanban.repository.BoardColumnRepository;
import dio.kanban.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    BoardRepository repository;
    BoardColumnService boardColumnService;

    @Autowired
    public BoardService(BoardRepository repository, BoardColumnService boardColumnService) {
        this.repository = repository;
        this.boardColumnService = boardColumnService;
    }

    @Transactional
    public Board insert(Board entity) {
        return repository.save(entity);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public Board findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public void showBoardDetails(long id) {
        Board entity = repository.findById(id).orElse(null);
        List<BoardColumnDetailsDto> columns = boardColumnService.findByBoardIdWithCount(id);

        if (entity != null) {
            System.out.printf("\nBOARD %s [ID : %s]\n", entity.getName().toUpperCase(), id);
            columns.forEach(c -> {
                System.out.printf("[Coluna %s][%s]: %s cards", c.getName(), c.getKind(), c.getCardsAmount());
            });
        }
    }
}
