package com.fiap.claimsapi.infrastructure.util;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.interfaces.model.TicketRequest;
import com.fiap.claimsapi.interfaces.model.TicketResponse;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TicketTransactionalUtil {

    public Ticket toEntity(TicketRequest ticketRequest) {
        Ticket ticket = Ticket.builder()
                .id(ticketRequest.getId())
                .numberRequest(ticketRequest.getNumberRequest())
                .documentNumber(ticketRequest.getDocumentNumber())
                .name(ticketRequest.getName())
                .email(ticketRequest.getEmail())
                .phone(ticketRequest.getPhone())
                .contactReason(ticketRequest.getContactReason())
                .description(ticketRequest.getDescription())
                .status(ticketRequest.getStatus()).build();
        ticketRequest.getAttachments().forEach(ticket::addAttachments);
        return ticket;
    }

    public TicketResponse toResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        BeanUtils.copyProperties(ticket, response);
        return response;
    }

    public List<TicketResponse> toResponse(List<Ticket> tickets) {
        List<TicketResponse> responseList = new ArrayList<>();
        for(Ticket t : tickets) {
            TicketResponse response = new TicketResponse();
            BeanUtils.copyProperties(t, response);
            responseList.add(response);
        }
        return responseList;
    }
}
