package com.payment.usermanagement.repositories;

import com.payment.usermanagement.models.checklist.Checklist;
import com.payment.usermanagement.models.checklist.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, UUID> {

    // Encontra um ChecklistItem pelo seu ID e a Checklist a que ele pertence
    Optional<ChecklistItem> findByIdAndChecklist(UUID id, Checklist checklist);

    // Retorna todos os itens de uma determinada Checklist
    List<ChecklistItem> findAllByChecklist(Checklist checklist);
}
