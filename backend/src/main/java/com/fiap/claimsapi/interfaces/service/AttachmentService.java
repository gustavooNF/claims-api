package com.fiap.claimsapi.interfaces.service;

import com.fiap.claimsapi.application.domain.entity.Attachment;
import com.fiap.claimsapi.application.domain.entity.Ticket;
import com.fiap.claimsapi.infrastructure.persistence.AttachmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


    public Attachment save(@RequestBody Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public List<Attachment> getAllAttachments(){
        return attachmentRepository.findAll();
    }

    public Optional<Attachment> getAttachmentById(@PathVariable Long id) {
        return attachmentRepository.findById(id);
    }

    public Optional<List<Attachment>> getAttachmentByTicket(@PathVariable Long ticketId) {
        return attachmentRepository.findAttachmentByTicket(ticketId);
    }

    public ResponseEntity<Attachment> deleteAttachment(@PathVariable Long id){
        Optional<Attachment> attachment = attachmentRepository.findById(id);
        if (attachment.isPresent()) {
            attachmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
