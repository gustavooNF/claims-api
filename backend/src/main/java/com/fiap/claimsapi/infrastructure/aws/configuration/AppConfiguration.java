package com.fiap.claimsapi.infrastructure.aws.configuration;


import com.fiap.claimsapi.interfaces.service.aws.MessageQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private final MessageQueueService messageQueueService;

    public void initializeMessageQueue() {
        messageQueueService.createMessageQueue();
    }
}
