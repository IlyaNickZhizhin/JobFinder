package xyz.zhizhin.jobfinder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhizhin.jobfinder.HeadHunterUserService;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;

@RestController
@RequiredArgsConstructor
public class HeadHunterUserController {

    private final HeadHunterUserService service;

    public HeadHunterUser createHeadHunterUser(HeadHunterUser headHunterUser) {
        return service.createHeadHunterUser(headHunterUser);
    }

    public HeadHunterUser getHeadHunterUser(Long id) {
        return service.readHeadHunterUserById(id);
    }

    public HeadHunterUser updateHeadHunterUser(HeadHunterUser headHunterUser) {
        HeadHunterUser user = service.readHeadHunterUserById(headHunterUser.getId());
        user.setName(headHunterUser.getName());
        user.setSurname(headHunterUser.getSurname());
        user.setMiddleName(headHunterUser.getMiddleName());
        user.setGender(headHunterUser.getGender());
        user.setAge(headHunterUser.getAge());
        user.setAdditionalText(headHunterUser.getAdditionalText());
        user.setExpirience(headHunterUser.getExpirience());
        user.setSetSkills(headHunterUser.getSetSkills());
        user.setFindStrategy(headHunterUser.getFindStrategy());
        return service.createHeadHunterUser(user);
    }

    public HeadHunterUser deleteHeadHunterUser(Long id) {
        return service.deleteHeadHunterUser(id);
    }


}
