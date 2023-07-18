package com.fiap.claimsapi.infrastructure.persistence;

import com.fiap.claimsapi.application.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "select nextval(doc_nr_seq)", nativeQuery = true)
    Long getDocumentNumber();
}
