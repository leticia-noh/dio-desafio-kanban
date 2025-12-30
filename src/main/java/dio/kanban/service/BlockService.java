package dio.kanban.service;

import dio.kanban.entity.Block;
import dio.kanban.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    BlockRepository repository;

    @Autowired
    public BlockService(BlockRepository repository) {
        this.repository = repository;
    }

    public Block insert(Block block) {
        return repository.save(block);
    }
}
