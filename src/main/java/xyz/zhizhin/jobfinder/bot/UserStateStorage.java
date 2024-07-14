package xyz.zhizhin.jobfinder.bot;

import lombok.Getter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class UserStateStorage {
    private final Map<Long, Pair<UserState, Integer>> userStates = new ConcurrentHashMap<>();
    private final Map<Long, Map<UserState, String>> userAnswer = new ConcurrentHashMap<>();

}
