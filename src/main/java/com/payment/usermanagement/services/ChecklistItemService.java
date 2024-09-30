package com.payment.usermanagement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.usermanagement.dtos.checklist.ChecklistItemRecordDto;
import com.payment.usermanagement.exceptions.checklist.ChecklistItemNotFoundException;
import com.payment.usermanagement.exceptions.checklist.ChecklistNotFoundException;
import com.payment.usermanagement.models.Response;
import com.payment.usermanagement.models.checklist.Checklist;
import com.payment.usermanagement.models.checklist.ChecklistItem;
import com.payment.usermanagement.models.factories.ResponseFactory;
import com.payment.usermanagement.repositories.ChecklistItemRepository;
import com.payment.usermanagement.repositories.ChecklistRepository;
import com.payment.usermanagement.services.interfaces.ICrud;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChecklistItemService implements ICrud<Object, Object> {

    final ChecklistItemRepository checklistItemRepository;
    final ChecklistRepository checklistRepository;
    final ResponseFactory responseFactory;
    final ObjectMapper objectMapper;

    public ChecklistItemService(ChecklistItemRepository checklistItemRepository, ChecklistRepository checklistRepository,
                                ResponseFactory responseFactory, ObjectMapper objectMapper) {
        this.checklistItemRepository = checklistItemRepository;
        this.checklistRepository = checklistRepository;
        this.responseFactory = responseFactory;
        this.objectMapper = objectMapper;
    }
    public Response addItemToChecklist(ChecklistItemRecordDto checklistItemDto, HttpServletRequest request) {
        // Encontrar a checklist pelo ID
        Checklist checklist = checklistRepository.findById(checklistItemDto.checklistId())
                .orElseThrow(() -> new ChecklistNotFoundException("Checklist not found with ID: " + checklistItemDto.checklistId()));

        // Criar um novo ChecklistItem e associá-lo à checklist
        ChecklistItem checklistItem = new ChecklistItem();
        BeanUtils.copyProperties(checklistItemDto, checklistItem);
        checklistItem.setChecklist(checklist);

        // Salvar o item
        checklistItemRepository.save(checklistItem);

        // Retornar a resposta
        return responseFactory.createCreatedResponse(request.getRequestURI(), "Checklist item successfully created");
    }

    // ?? provavelmente vai ser removido no futuro.
    @Override
    public Response save(Object dto, HttpServletRequest request) {
        ChecklistItemRecordDto checklistItemRecordDto = (ChecklistItemRecordDto) dto;

        // Encontre a checklist usando o ID fornecido
        Checklist checklist = checklistRepository.findById(checklistItemRecordDto.checklistId())
                .orElseThrow(() -> new IllegalArgumentException("Checklist not found with ID: " + checklistItemRecordDto.checklistId()));

        var checklistItem = new ChecklistItem();
        BeanUtils.copyProperties(checklistItemRecordDto, checklistItem);
        checklistItem.setChecklist(checklist); // Associa o checklist ao item

        checklistItemRepository.save(checklistItem);

        return responseFactory.createCreatedResponse(request.getRequestURI(), "Checklist item successfully created");
    }

    @Override
    public Response findById(Object id) {
        UUID checklistItemId = (UUID) id; // Converter o ID para UUID
        ChecklistItem checklistItem = checklistItemRepository.findById(checklistItemId)
                .orElseThrow(() -> new ChecklistItemNotFoundException("Checklist item not found with ID: " + id));
        return responseFactory.createSuccessResponse("Checklist item found", String.valueOf(checklistItem));
    }

    @Override
    public Response findAll() {
        List<ChecklistItem> checklistItems = checklistItemRepository.findAll();

        // Criar uma lista de descrições dos itens
        List<String> checklistItemDescriptions = checklistItems.stream()
                .map(item -> String.format("ID: %s, Description: %s", item.getId(), item.getDescription()))
                .toList();

        return responseFactory.createSuccessResponse("All checklist items found", String.valueOf(checklistItemDescriptions));
    }

    @Override
    public Response update(Object id, Object dto, Object request) {
        UUID checklistItemId = (UUID) id; // Converter o ID para UUID
        ChecklistItemRecordDto checklistItemRecordDto = (ChecklistItemRecordDto) dto;

        // Verificar se o checklist item existe
        ChecklistItem existingChecklistItem = checklistItemRepository.findById(checklistItemId)
                .orElseThrow(() -> new ChecklistItemNotFoundException("Checklist item not found with ID: " + id));

        // Atualizar propriedades do checklist item existente
        BeanUtils.copyProperties(checklistItemRecordDto, existingChecklistItem, "id", "checklist"); // Exclui 'id' e 'checklist'
        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        checklistItemRepository.save(existingChecklistItem);

        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "Checklist item with ID =%s successfully updated".formatted(id));
    }

    @Override
    public Response delete(Object id, Object request) {
        UUID checklistItemId = (UUID) id; // Converter o ID para UUID
        ChecklistItem existingChecklistItem = checklistItemRepository.findById(checklistItemId)
                .orElseThrow(() -> new ChecklistItemNotFoundException("Checklist item not found with ID: " + id));

        var mapperTest = objectMapper.convertValue(request, HttpServletRequest.class);
        checklistItemRepository.delete(existingChecklistItem);

        return responseFactory.createSuccessResponse(mapperTest.getRequestURI(), "Checklist item with ID =%s successfully deleted".formatted(id));
    }

}
