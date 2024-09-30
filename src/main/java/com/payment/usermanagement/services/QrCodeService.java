package com.payment.usermanagement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.usermanagement.dtos.checklist.ChecklistItemRecordDto;
import com.payment.usermanagement.exceptions.QrCodeNotFoundException;
import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.models.checklist.Checklist;
import com.payment.usermanagement.models.checklist.QrCode;
import com.payment.usermanagement.repositories.QrCodeRepository;
import com.payment.usermanagement.services.interfaces.ICrud;
import com.payment.usermanagement.models.factories.ResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrCodeService implements ICrud<Object, Object> {

    final QrCodeRepository qrCodeRepository;
    final ChecklistService checklistService;
    final ResponseFactory responseFactory;
    final ObjectMapper objectMapper;

    public QrCodeService(QrCodeRepository qrCodeRepository, ChecklistService checklistService, ObjectMapper objectMapper, ResponseFactory responseFactory) {
        this.qrCodeRepository = qrCodeRepository;
        this.checklistService = checklistService;
        this.responseFactory = responseFactory;
        this.objectMapper = objectMapper;

    }

    @Override
    public Response save(Object dto, HttpServletRequest request) {
        UUID checklistId = ((ChecklistItemRecordDto) dto).checklistId();

        // Utiliza Optional para buscar o checklist
        Optional<Checklist> optionalChecklist = Optional.ofNullable(checklistService.findById(checklistId));

        // Se o checklist não for encontrado, retorna uma resposta de erro
        if (optionalChecklist.isEmpty()) {
            return responseFactory.createNotFoundResponse(request.getRequestURI(), "Checklist not found with ID: " + checklistId);
        }

        // Cria o QR code e associa ao checklist encontrado
        QrCode qrCode = new QrCode();
        BeanUtils.copyProperties(dto, qrCode);
        qrCode.setChecklist(optionalChecklist.get()); // Associa o checklist ao QR code
        qrCodeRepository.save(qrCode);

        return responseFactory.createCreatedResponse(request.getRequestURI(), "QR code successfully created for checklist ID: " + checklistId);
    }

    @Override
    public QrCode findById(Object id) {
        return qrCodeRepository.findById((UUID) id).orElseThrow(() -> new QrCodeNotFoundException("QR code not found with ID: " + id));
    }

    @Override
    public List<QrCode> findAll() {
        return qrCodeRepository.findAll();
    }

    @Override
    public Response update(Object id, Object dto, Object request) {
        QrCode existingQrCode = findById(id);
        BeanUtils.copyProperties(dto, existingQrCode);
        qrCodeRepository.save(existingQrCode);

        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "QR code with ID " + id + " successfully updated.");
    }

    @Override
    public Response delete(Object id, Object request) {
        QrCode existingQrCode = findById(id);
        qrCodeRepository.deleteById(existingQrCode.getId());

        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "QR code with ID " + id + " successfully deleted.");
    }
}
