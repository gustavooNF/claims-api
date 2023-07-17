package com.fiap.claimsapi.interfaces.model.dto;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.interfaces.model.TicketRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailMSgDto {
    private UUID idMessage;
    private Long requestNumber;
    private String dthCreate;
    private String nome;
    private String email;
    private String status;
    private String message;

    public static EmailMSgDto  toEmailMSgDtoDTO(Ticket ticket, String message){

        return EmailMSgDto.builder()
                .idMessage(UUID.randomUUID())
                .requestNumber(ticket.getNumberRequest())
                .nome(ticket.getName())
                .email(ticket.getEmail())
                .status(ticket.getStatus())
                .message(message)
                .build();
    }
}
