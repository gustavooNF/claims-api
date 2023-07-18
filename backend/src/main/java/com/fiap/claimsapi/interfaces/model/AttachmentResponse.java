package com.fiap.claimsapi.interfaces.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentResponse {
    private Long id;
    private String url;
    private Long ticketId;
}
