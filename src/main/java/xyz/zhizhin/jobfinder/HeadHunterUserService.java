package xyz.zhizhin.jobfinder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;
import xyz.zhizhin.jobfinder.repository.HeadHunterUserRepository;

@Service
@RequiredArgsConstructor
public class HeadHunterUserService {

    private final HeadHunterUserRepository repository;

    public HeadHunterUser createHeadHunterUser(HeadHunterUser headHunterUser) {
        return repository.save(headHunterUser);
    }

    public HeadHunterUser readHeadHunterUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public HeadHunterUser saveHeadHunterUser(HeadHunterUser user) {
        return repository.save(user);
    }

    public HeadHunterUser deleteHeadHunterUser(Long id) {
        HeadHunterUser headHunterUser = readHeadHunterUserById(id);
        repository.delete(headHunterUser);
        return headHunterUser;
    }



}
