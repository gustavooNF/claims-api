package com.fiap.claimsapi.infrastructure.util;

import com.fiap.claimsapi.application.domain.entity.Attachment;
import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.interfaces.model.AttachmentRequest;
import com.fiap.claimsapi.interfaces.model.AttachmentResponse;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class AttachmentTransactionalUtil {

    public Attachment toEntity(AttachmentRequest attachmentRequest) {
        Attachment attachment = new Attachment();
        Ticket ticket = new Ticket();
        ticket.setId(attachmentRequest.getTicketId());
        BeanUtils.copyProperties(attachmentRequest, attachment);
        attachment.setTicket(ticket);
        return attachment;
    }

    public AttachmentResponse toResponse(Attachment attachment) {
        AttachmentResponse response = new AttachmentResponse();
        BeanUtils.copyProperties(attachment, response);
        return response;
    }

    public List<AttachmentResponse> toResponse(List<Attachment> attachments) {
        List<AttachmentResponse> responseList = new ArrayList<>();
        for(Attachment a : attachments) {
            AttachmentResponse response = new AttachmentResponse();
            BeanUtils.copyProperties(a, response);
            response.setTicketId(a.getTicket().getId());
            responseList.add(response);
        }
        return responseList;
    }
}
