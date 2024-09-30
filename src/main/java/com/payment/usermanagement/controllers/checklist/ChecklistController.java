package com.payment.usermanagement.controllers.checklist;

import com.payment.usermanagement.dtos.checklist.ChecklistRecordDto;
import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.models.checklist.Checklist;
import com.payment.usermanagement.services.ChecklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ChecklistController {

    final ChecklistService checklistService;;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @PostMapping("/checklists")
    public ResponseEntity<Response> save(@RequestBody @Valid ChecklistRecordDto checklistRecordDto, HttpServletRequest request)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(checklistService.save(checklistRecordDto, request));
    }

    @GetMapping("/checklists/{id}")
    public ResponseEntity<Checklist> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistService.findById(id));
    }

    @GetMapping("/checklists/")
    public ResponseEntity<List<Checklist>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(checklistService.findAll());
    }

    @PutMapping("/checklists/{id}")
    public ResponseEntity<Response> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ChecklistRecordDto checklistRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistService.update(id, checklistRecordDto, request));
    }

    @DeleteMapping("/checklists/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") UUID id, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistService.delete(id, request));
    }
}
