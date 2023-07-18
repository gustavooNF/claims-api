package com.fiap.claimsapi.interfaces.service.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fiap.claimsapi.interfaces.model.dto.EmailMSgDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageQueueService {

    @Value("${cloud.aws.end-point.uri}")
    private String sqsUrl;

    private final AmazonSQS amazonSQSClient;

    public void createMessageQueue() {
        log.info("Creating message queue on AWS SQS");

        CreateQueueRequest request = new CreateQueueRequest();
        request.setQueueName(sqsUrl);
        try {
            CreateQueueResult queue = amazonSQSClient.createQueue(request);
            log.info("Create Queue Response {}", queue.getQueueUrl());

        } catch (QueueNameExistsException e) {
            log.error("Queue Name Exists {}", e.getErrorMessage());
        }
    }
    public void publishExpense(EmailMSgDto creatEmailMSgDto) {
        try {
            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(sqsUrl);
            log.info("Reading SQS Queue done: URL {}", queueUrl.getQueueUrl());
            Gson gson = new Gson();
            if(Objects.nonNull(creatEmailMSgDto)){
                String gsonEmailMsg = gson.toJson(creatEmailMSgDto);
                amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), gsonEmailMsg);
            }
        } catch (QueueDoesNotExistException | InvalidMessageContentsException e) {
            log.error("Queue does not exist {}", e.getMessage());
        }

    }
}
