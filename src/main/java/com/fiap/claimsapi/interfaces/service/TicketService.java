package com.fiap.claimsapi.interfaces.service;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.infrastructure.persistence.TicketRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(@RequestBody Ticket ticket) {
        ticket.setDocumentNumber(ticketRepository.getDocumentNumber().toString());
        return ticketRepository.save(ticket);
    }

    public Optional<Ticket> update(Ticket ticket){
        Optional<Ticket> updatable = ticketRepository.findById(ticket.getId());

        if(updatable.isPresent()) {
            Ticket currentTicket = updatable.get();
            BeanUtils.copyProperties(ticket, currentTicket, "id");
            ticketRepository.save(currentTicket);
            return Optional.of(currentTicket);
        }
        return updatable;
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }
    public Optional<Ticket> getGameById(@PathVariable Long id) {
        return ticketRepository.findById(id);

    }

    public ResponseEntity<Ticket> deleteTicket(@PathVariable Long id){
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()) {
            ticketRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
