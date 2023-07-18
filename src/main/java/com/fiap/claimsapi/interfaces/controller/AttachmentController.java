package com.fiap.claimsapi.interfaces.controller;

import com.fiap.claimsapi.application.domain.entity.Attachment;
import com.fiap.claimsapi.infrastructure.util.AttachmentTransactionalUtil;
import com.fiap.claimsapi.interfaces.model.AttachmentRequest;
import com.fiap.claimsapi.interfaces.model.AttachmentResponse;
import com.fiap.claimsapi.interfaces.service.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Attachment save(@RequestBody AttachmentRequest attachmentRequest) {
        return attachmentService.save(AttachmentTransactionalUtil.toEntity(attachmentRequest));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AttachmentResponse> getAttachmentById(@PathVariable Long id) {
        Optional<Attachment> attachment = attachmentService.getAttachmentById(id);
        return attachment.map(value -> ResponseEntity.ok(AttachmentTransactionalUtil.toResponse(value))).orElseGet(() ->
                ResponseEntity.noContent().build());
    }

    @GetMapping("/find/ticket/{id}")
    public ResponseEntity<List<AttachmentResponse>> getAttachmenstByTicket(@PathVariable Long id) {
        Optional<List<Attachment>> attachments = attachmentService.getAttachmentByTicket(id);
        return attachments.map(value -> ResponseEntity.ok(AttachmentTransactionalUtil.toResponse(value))).orElseGet(() ->
                ResponseEntity.noContent().build());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AttachmentResponse>> getAllTickets() {
        return ResponseEntity.ok(AttachmentTransactionalUtil.toResponse(attachmentService.getAllAttachments()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Attachment> deleteAttachment(@PathVariable Long id){
        return attachmentService.deleteAttachment(id);

    }
}
