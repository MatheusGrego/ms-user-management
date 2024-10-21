package com.payment.usermanagement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.usermanagement.exceptions.checklist.ChecklistNotFoundException;
import com.payment.usermanagement.models.response.Response;
import com.payment.usermanagement.models.checklist.Checklist;
import com.payment.usermanagement.models.response.factories.ResponseFactory;
import com.payment.usermanagement.repositories.ChecklistRepository;
import com.payment.usermanagement.services.interfaces.ICrud;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ChecklistService implements ICrud<Object, Object> {

    final ChecklistRepository checklistRepository;
    final ResponseFactory responseFactory;
    final ObjectMapper objectMapper;

    public ChecklistService(ChecklistRepository checklistRepository, ResponseFactory responseFactory, ObjectMapper objectMapper) {
        this.checklistRepository = checklistRepository;
        this.responseFactory = responseFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public Response save(Object dto, HttpServletRequest request) {
        var checklist = new Checklist();
        BeanUtils.copyProperties(dto, checklist);
        checklistRepository.save(checklist);

        // aqui vai retornar o checklist ID para ser tratado no front-end, referente a criação dos itens da checklist no campo "messages"
        return responseFactory.createCreatedResponse(request.getRequestURI(), String.valueOf(checklist.getId()));
    }

    @Override
    public Checklist findById(Object id) {
        return checklistRepository.findById((UUID) id)
                .orElseThrow(() -> new ChecklistNotFoundException("Checklist not found with ID: " + id));
    }

    @Override
    public List<Checklist> findAll() {
        return checklistRepository.findAll();
    }

    @Override
    public Response update(Object id, Object dto, Object request) {
        Checklist existingChecklist = findById(id);
        BeanUtils.copyProperties(dto, Objects.requireNonNull(existingChecklist));
        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        checklistRepository.save(existingChecklist);

        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "Checklist with ID =%s successfully updated".formatted(id));
    }

    @Override
    public Response delete(Object id, Object request) {
        Checklist existingChecklist = findById(id);
        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        checklistRepository.deleteById(Objects.requireNonNull(existingChecklist).getId());

        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "Checklist with ID =%s successfully deleted".formatted(id));
    }
}
