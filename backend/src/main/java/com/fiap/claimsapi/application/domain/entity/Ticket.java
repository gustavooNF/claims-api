package com.fiap.claimsapi.application.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {
    @Serial
    private static final long serialVersionUID = -6605642980216169354L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numberRequest;
    private String documentNumber;
    private String name;
    private String email;
    private String phone;
    private String contactReason;
    private String description;
    private String status;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    private List<Attachment> attachments = new ArrayList<>();
    public void addAttachments(Attachment attachment) {
        if(this.attachments == null) this.attachments = new ArrayList<>();
        this.attachments.add(attachment);
        attachment.setTicket(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(numberRequest, ticket.numberRequest) &&
                Objects.equals(documentNumber, ticket.documentNumber) &&
                Objects.equals(name, ticket.name) &&
                Objects.equals(email, ticket.email) &&
                Objects.equals(phone, ticket.phone) &&
                Objects.equals(contactReason, ticket.contactReason) &&
                Objects.equals(description, ticket.description) &&
                Objects.equals(status, ticket.status) &&
                Objects.equals(attachments, ticket.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberRequest, documentNumber, name, email,
                phone, contactReason, description, status, attachments);
    }
}
