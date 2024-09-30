package com.payment.usermanagement.controllers.checklist;

import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.dtos.checklist.ChecklistItemRecordDto;
import com.payment.usermanagement.services.ChecklistItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistItemController {

    final ChecklistItemService checklistItemService;

    public ChecklistItemController(ChecklistItemService checklistItemService) {
        this.checklistItemService = checklistItemService;
    }


    @PostMapping("/checklist-items")
    public ResponseEntity<Response> addItemToChecklist(
                                                       @RequestBody @Valid ChecklistItemRecordDto checklistItemDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checklistItemService.addItemToChecklist(checklistItemDto, request));
    }


    @GetMapping("/checklist-items/{id}")
    public ResponseEntity<Response> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistItemService.findById(id));
    }

    @GetMapping("/checklist-items")
    public ResponseEntity<Response> findAll() {
        Response checklistItems = checklistItemService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(checklistItems);
    }

    @PutMapping("/checklist-items/{id}")
    public ResponseEntity<Response> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ChecklistItemRecordDto checklistItemRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistItemService.update(id, checklistItemRecordDto, request));
    }

    @DeleteMapping("/checklist-items/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") UUID id, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistItemService.delete(id, request));
    }
}
