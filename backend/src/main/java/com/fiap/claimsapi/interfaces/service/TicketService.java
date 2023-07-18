package com.fiap.claimsapi.interfaces.service;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.infrastructure.persistence.TicketRepository;
import com.fiap.claimsapi.interfaces.model.dto.EmailMSgDto;
import com.fiap.claimsapi.interfaces.service.aws.MessageQueueService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    private MessageQueueService messageQueueService;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(@RequestBody Ticket ticket) {
        String initMessage = "Seu ticket foi aberto com sucesso e já está em análise pelo responsável da área";
        ticket.setDocumentNumber(ticketRepository.getDocumentNumber().toString());
        log.info("Enviando mensagem para a QUEUE da AWS...");
        EmailMSgDto emailMSgDtoDTO = EmailMSgDto.toEmailMSgDtoDTO(ticket, initMessage);
        messageQueueService.publishExpense(emailMSgDtoDTO);
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> update(Ticket ticket) {
        Optional<Ticket> updatable = ticketRepository.findById(ticket.getId());
        if (updatable.isPresent()) {
            EmailMSgDto emailMSgDtoDTO = EmailMSgDto.toEmailMSgDtoDTO(ticket, INIT_MESSAGE);
            messageQueueService.publishExpense(emailMSgDtoDTO);
            Ticket currentTicket = updatable.get();
            BeanUtils.copyProperties(ticket, currentTicket, "id");
            ticketRepository.save(currentTicket);
            return Optional.of(currentTicket);
        }
        return updatable;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id);

    }

    public ResponseEntity<Ticket> deleteTicket(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            ticketRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
