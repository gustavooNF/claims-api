package com.fiap.claimsapi.interfaces.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fiap.claimsapi.application.domain.entity.Attachment;
import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.infrastructure.persistence.AttachmentRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

class AttachmentServiceTest {

    @Test
    void testSave() {


        Attachment attachment = new Attachment();
        attachment.setId(1L);
        attachment.setTicket(new Ticket());
        attachment.setUrl("https://example.org/example");
        AttachmentRepository attachmentRepository = mock(AttachmentRepository.class);
        when(attachmentRepository.save(Mockito.<Attachment>any())).thenReturn(attachment);
        AttachmentService attachmentService = new AttachmentService(attachmentRepository);

        Attachment attachment2 = new Attachment();
        attachment2.setId(1L);
        attachment2.setTicket(new Ticket());
        attachment2.setUrl("https://example.org/example");
        assertSame(attachment, attachmentService.save(attachment2));
        verify(attachmentRepository).save(Mockito.<Attachment>any());
    }


    @Test
    void testGetAllAttachments() {

        AttachmentRepository attachmentRepository = mock(AttachmentRepository.class);
        ArrayList<Attachment> attachmentList = new ArrayList<>();
        when(attachmentRepository.findAll()).thenReturn(attachmentList);
        List<Attachment> actualAllAttachments = (new AttachmentService(attachmentRepository)).getAllAttachments();
        assertSame(attachmentList, actualAllAttachments);
        assertTrue(actualAllAttachments.isEmpty());
        verify(attachmentRepository).findAll();
    }

    /**
     * Method under test: {@link AttachmentService#getAttachmentById(Long)}
     */
    @Test
    void testGetAttachmentById() {

        Attachment attachment = new Attachment();
        attachment.setId(1L);
        attachment.setTicket(new Ticket());
        attachment.setUrl("https://example.org/example");
        Optional<Attachment> ofResult = Optional.of(attachment);
        AttachmentRepository attachmentRepository = mock(AttachmentRepository.class);
        when(attachmentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Attachment> actualAttachmentById = (new AttachmentService(attachmentRepository)).getAttachmentById(1L);
        assertSame(ofResult, actualAttachmentById);
        assertTrue(actualAttachmentById.isPresent());
        verify(attachmentRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testGetAttachmentByTicket() {


        AttachmentRepository attachmentRepository = mock(AttachmentRepository.class);
        Optional<List<Attachment>> ofResult = Optional.of(new ArrayList<>());
        when(attachmentRepository.findAttachmentByTicket(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<List<Attachment>> actualAttachmentByTicket = (new AttachmentService(attachmentRepository))
                .getAttachmentByTicket(1L);
        assertSame(ofResult, actualAttachmentByTicket);
        assertTrue(actualAttachmentByTicket.isPresent());
        verify(attachmentRepository).findAttachmentByTicket(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AttachmentService#deleteAttachment(Long)}
     */
    @Test
    void testDeleteAttachment() {

        Attachment attachment = new Attachment();
        attachment.setId(1L);
        attachment.setTicket(new Ticket());
        attachment.setUrl("https://example.org/example");
        Optional<Attachment> ofResult = Optional.of(attachment);
        AttachmentRepository attachmentRepository = mock(AttachmentRepository.class);
        doNothing().when(attachmentRepository).deleteById(Mockito.<Long>any());
        when(attachmentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<Attachment> actualDeleteAttachmentResult = (new AttachmentService(attachmentRepository))
                .deleteAttachment(1L);
        assertNull(actualDeleteAttachmentResult.getBody());
        assertEquals(204, actualDeleteAttachmentResult.getStatusCodeValue());
        assertTrue(actualDeleteAttachmentResult.getHeaders().isEmpty());
        verify(attachmentRepository).findById(Mockito.<Long>any());
        verify(attachmentRepository).deleteById(Mockito.<Long>any());
    }


    @Test
    void testDeleteAttachment2() {

        AttachmentRepository attachmentRepository = mock(AttachmentRepository.class);
        when(attachmentRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        ResponseEntity<Attachment> actualDeleteAttachmentResult = (new AttachmentService(attachmentRepository))
                .deleteAttachment(1L);
        assertNull(actualDeleteAttachmentResult.getBody());
        assertEquals(404, actualDeleteAttachmentResult.getStatusCodeValue());
        assertTrue(actualDeleteAttachmentResult.getHeaders().isEmpty());
        verify(attachmentRepository).findById(Mockito.<Long>any());
    }
}

