package dio.kanban.service;

import dio.kanban.entity.Board;
import dio.kanban.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    BoardRepository repository;

    @Autowired
    public BoardService(BoardRepository repository) {
        this.repository = repository;
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
}
