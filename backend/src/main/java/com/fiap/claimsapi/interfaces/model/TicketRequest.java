package com.fiap.claimsapi.interfaces.model;

import com.fiap.claimsapi.application.domain.entity.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private Long id;
    private Long numberRequest;
    private String documentNumber;
    private String name;
    private String email;
    private String phone;
    private String contactReason;
    private String description;
    private String status;
    private List<Attachment> attachments;
}
