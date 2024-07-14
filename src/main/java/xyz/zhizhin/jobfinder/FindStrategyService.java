package xyz.zhizhin.jobfinder;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import xyz.zhizhin.jobfinder.model.FindStrategy;
import xyz.zhizhin.jobfinder.repository.FindStrategyRepository;

@Service
@RequiredArgsConstructor
public class FindStrategyService {

    private final FindStrategyRepository repository;

    public FindStrategy createFindStrategy(FindStrategy findStrategy) {
        return repository.save(findStrategy);
    }

    private FindStrategy readStrategyById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Could not find strategy by id: " + id));
    }

    @NotNull
    private FindStrategy saveFindStrategy(FindStrategy findStrategy) {
        return repository.save(findStrategy);
    }


    private FindStrategy deleteFindStrategy(@NotNull FindStrategy findStrategy) {
        FindStrategy findStrategyRead = readStrategyById(findStrategy.getId());
        repository.delete(findStrategy);
        return findStrategyRead;
    }

}
