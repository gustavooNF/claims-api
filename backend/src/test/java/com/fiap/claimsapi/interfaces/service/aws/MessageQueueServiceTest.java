package com.fiap.claimsapi.interfaces.service.aws;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.PredefinedClientConfigurations;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.buffered.QueueBufferConfig;
import com.amazonaws.services.sqs.model.AddPermissionRequest;
import com.amazonaws.services.sqs.model.AddPermissionResult;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.QueueNameExistsException;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fiap.claimsapi.interfaces.model.dto.EmailMSgDto;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageQueueServiceTest {

    @Test
    void testPublishExpense2() {

        AmazonSQSAsyncClient amazonSQSClient = mock(AmazonSQSAsyncClient.class);
        when(amazonSQSClient.sendMessage(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new SendMessageResult());
        when(amazonSQSClient.getQueueUrl(Mockito.<String>any())).thenReturn(new GetQueueUrlResult());
        MessageQueueService messageQueueService = new MessageQueueService(amazonSQSClient);
        messageQueueService.publishExpense(new EmailMSgDto());
        verify(amazonSQSClient).getQueueUrl(Mockito.<String>any());
        verify(amazonSQSClient).sendMessage(Mockito.<String>any(), Mockito.<String>any());
    }


    @Test
    void testPublishExpense3() {
        AmazonSQSAsyncClient amazonSQSClient = mock(AmazonSQSAsyncClient.class);
        when(amazonSQSClient.sendMessage(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new QueueNameExistsException("An error occurred"));
        when(amazonSQSClient.getQueueUrl(Mockito.<String>any())).thenReturn(new GetQueueUrlResult());
        MessageQueueService messageQueueService = new MessageQueueService(amazonSQSClient);
        assertThrows(QueueNameExistsException.class, () -> messageQueueService.publishExpense(new EmailMSgDto()));
        verify(amazonSQSClient).getQueueUrl(Mockito.<String>any());
        verify(amazonSQSClient).sendMessage(Mockito.<String>any(), Mockito.<String>any());
    }

    @Test
    void testPublishExpense4() {
        AmazonSQSAsyncClient amazonSQSClient = mock(AmazonSQSAsyncClient.class);
        when(amazonSQSClient.sendMessage(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new QueueDoesNotExistException("An error occurred"));
        when(amazonSQSClient.getQueueUrl(Mockito.<String>any())).thenReturn(new GetQueueUrlResult());
        MessageQueueService messageQueueService = new MessageQueueService(amazonSQSClient);
        messageQueueService.publishExpense(new EmailMSgDto());
        verify(amazonSQSClient).getQueueUrl(Mockito.<String>any());
        verify(amazonSQSClient).sendMessage(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link MessageQueueService#publishExpense(EmailMSgDto)}
     */

    @Test
    void testPublishExpense6() {
        GetQueueUrlResult getQueueUrlResult = mock(GetQueueUrlResult.class);
        when(getQueueUrlResult.getQueueUrl()).thenThrow(new QueueDoesNotExistException("An error occurred"));
        AmazonSQSAsyncClient amazonSQSClient = mock(AmazonSQSAsyncClient.class);
        when(amazonSQSClient.getQueueUrl(Mockito.<String>any())).thenReturn(getQueueUrlResult);
        MessageQueueService messageQueueService = new MessageQueueService(amazonSQSClient);
        messageQueueService.publishExpense(new EmailMSgDto());
        verify(amazonSQSClient).getQueueUrl(Mockito.<String>any());
        verify(getQueueUrlResult).getQueueUrl();
    }
}

