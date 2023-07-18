package com.fiap.claimsapi.interfaces.service;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.infrastructure.persistence.TicketRepository;
import com.fiap.claimsapi.interfaces.service.aws.MessageQueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private MessageQueueService messageQueueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        when(ticketRepository.save(Mockito.any())).thenReturn(ticket);
        TicketService ticketService = new TicketService(ticketRepository, messageQueueService);

        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        assertSame(ticket, ticketService.save(ticket1));
        verify(ticketRepository).save(Mockito.any());
    }


    @Test
    void testGetAllTickets() {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        when(ticketRepository.findAll()).thenReturn(ticketList);
        List<Ticket> actualAllTickets = (new TicketService(ticketRepository, messageQueueService)).getAllTickets();
        assertSame(ticketList, actualAllTickets);
        assertTrue(actualAllTickets.isEmpty());
        verify(ticketRepository).findAll();
    }

    /**
     * Method under test: {@link AttachmentService#getAttachmentById(Long)}
     */
    @Test
    void testGetTicketsById() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Ticket> actualTicketById = (new TicketService(ticketRepository, messageQueueService)).getTicketById(1L);
        assertSame(ofResult, actualTicketById);
        assertTrue(actualTicketById.isPresent());
        verify(ticketRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AttachmentService#deleteAttachment(Long)}
     */
    @Test
    void testDeleteTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        Optional<Ticket> ofResult = Optional.of(ticket);
        doNothing().when(ticketRepository).deleteById(Mockito.<Long>any());
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<Ticket> actualDeleteTicketResult = (new TicketService(ticketRepository, messageQueueService))
                .deleteTicket(1L);
        assertNull(actualDeleteTicketResult.getBody());
        assertEquals(204, actualDeleteTicketResult.getStatusCode().value());
        assertTrue(actualDeleteTicketResult.getHeaders().isEmpty());
        verify(ticketRepository).findById(Mockito.<Long>any());
        verify(ticketRepository).deleteById(Mockito.<Long>any());
    }


    @Test
    void testDeleteTicket2() {
        when(ticketRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        ResponseEntity<Ticket> actualDeleteTicketResult = (new TicketService(ticketRepository, messageQueueService))
                .deleteTicket(1L);
        assertNull(actualDeleteTicketResult.getBody());
        assertEquals(404, actualDeleteTicketResult.getStatusCode().value());
        assertTrue(actualDeleteTicketResult.getHeaders().isEmpty());
        verify(ticketRepository).findById(Mockito.<Long>any());
    }
}

