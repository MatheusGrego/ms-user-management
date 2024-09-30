package com.payment.usermanagement.repositories;

import com.payment.usermanagement.models.checklist.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, UUID> {
}
