package xyz.zhizhin.jobfinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.zhizhin.jobfinder.client.OpenAiRequestSender;

@Service
@RequiredArgsConstructor
public class ResponseLetterService {

    private final OpenAiRequestSender openAiRequestSender;

}
