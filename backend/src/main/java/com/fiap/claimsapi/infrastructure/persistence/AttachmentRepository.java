package com.fiap.claimsapi.infrastructure.persistence;

import com.fiap.claimsapi.application.domain.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
     @Query(value = "select a.* from attachments a where a.ticket_id = ?", nativeQuery = true)
     Optional<List<Attachment>> findAttachmentByTicket(Long ticketId);
}
