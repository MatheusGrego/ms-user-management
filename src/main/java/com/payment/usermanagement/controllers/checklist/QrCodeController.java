package com.payment.usermanagement.controllers.checklist;

import com.payment.usermanagement.dtos.checklist.QrCodeRecordDto;
import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.models.checklist.QrCode;
import com.payment.usermanagement.services.QrCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class QrCodeController {

    final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    // Criar um QR Code associado a uma checklist existente
    @PostMapping("/qrcodes")
    public ResponseEntity<Response> save(@RequestBody @Valid QrCodeRecordDto qrCodeRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(qrCodeService.save(qrCodeRecordDto, request));
    }

    // Buscar um QR Code pelo seu ID
    @GetMapping("/qrcodes/{id}")
    public ResponseEntity<QrCode> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.findById(id));
    }

    // Buscar todos os QR Codes
    @GetMapping("/qrcodes")
    public ResponseEntity<List<QrCode>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.findAll());
    }

    // Atualizar um QR Code associado a uma checklist existente
    @PutMapping("/qrcodes/{id}")
    public ResponseEntity<Response> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid QrCodeRecordDto qrCodeRecordDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.update(id, qrCodeRecordDto, request));
    }

    // Deletar um QR Code
    @DeleteMapping("/qrcodes/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") UUID id, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(qrCodeService.delete(id, request));
    }
}
