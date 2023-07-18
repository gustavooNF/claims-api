package com.fiap.claimsapi.interfaces.controller;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.infrastructure.util.TicketTransactionalUtil;
import com.fiap.claimsapi.interfaces.model.TicketRequest;
import com.fiap.claimsapi.interfaces.model.TicketResponse;
import com.fiap.claimsapi.interfaces.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket save(@RequestBody TicketRequest ticketRequest) {
        return ticketService.save(TicketTransactionalUtil.toEntity(ticketRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<TicketResponse> update(@RequestBody TicketRequest ticketRequest){
        return ticketService.update(TicketTransactionalUtil.toEntity(ticketRequest)).map(ticket ->
                ResponseEntity.ok(TicketTransactionalUtil.toResponse(ticket))).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.getGameById(id);
        return ticket.map(value -> ResponseEntity.ok(TicketTransactionalUtil.toResponse(value))).orElseGet(() ->
                ResponseEntity.noContent().build());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(TicketTransactionalUtil.toResponse(ticketService.getAllTickets()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Ticket> deleteGame(@PathVariable Long id){
            return ticketService.deleteTicket(id);

    }
}
