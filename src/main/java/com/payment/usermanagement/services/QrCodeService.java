package com.payment.usermanagement.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.payment.usermanagement.dtos.checklist.ChecklistItemRecordDto;
import com.payment.usermanagement.exceptions.QrCodeGenerationException;
import com.payment.usermanagement.exceptions.QrCodeNotFoundException;
import com.payment.usermanagement.exceptions.checklist.ChecklistNotFoundException;
import com.payment.usermanagement.models.checklist.Checklist;
import com.payment.usermanagement.models.checklist.QrCode;
import com.payment.usermanagement.models.response.Response;
import com.payment.usermanagement.models.response.factories.ResponseFactory;
import com.payment.usermanagement.repositories.ChecklistRepository;
import com.payment.usermanagement.repositories.QrCodeRepository;
import com.payment.usermanagement.services.interfaces.ICrud;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class QrCodeService implements ICrud<Object, Object> {

    final QrCodeRepository qrCodeRepository;
    final ChecklistService checklistService;
    final ResponseFactory responseFactory;
    final ObjectMapper objectMapper;
    private final ChecklistRepository checklistRepository;

    public QrCodeService(QrCodeRepository qrCodeRepository, ChecklistService checklistService, ObjectMapper objectMapper, ResponseFactory responseFactory, ChecklistRepository checklistRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.checklistService = checklistService;
        this.responseFactory = responseFactory;
        this.objectMapper = objectMapper;
        this.checklistRepository = checklistRepository;
    }

    public Response generateQRCode(UUID checklistId, HttpServletRequest request) {
        // Encontrar a checklist pelo ID
        Checklist checklist = checklistRepository.findById(checklistId)
                .orElseThrow(() -> new ChecklistNotFoundException("Checklist not found with ID: " + checklistId));

        String data = "http://localhost:8080/checklist?id=" + checklistId; // URL com o ID da checklist
        String path = "qrcode-" + checklist + ".png"; // Caminho onde a imagem será salva
        int width = 300; // Largura do QR Code
        int height = 300; // Altura do QR Code

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(path));
            return responseFactory.createCreatedResponse(request.getRequestURI(), path);
        } catch (WriterException | IOException e) {
            throw new QrCodeGenerationException("Error generating the QR Code: " + e.getMessage());
        }
    }


    @Override
    public Response save(Object dto, HttpServletRequest request) {
        UUID checklistId = ((ChecklistItemRecordDto) dto).checklistId();

        // Validar e buscar a checklist
        Checklist checklist = checklistService.findById(checklistId);

        // Cria o QR code e associa ao checklist
        QrCode qrCode = new QrCode();
        BeanUtils.copyProperties(dto, qrCode);
        qrCode.setChecklist(checklist); // Associa o checklist ao QR code

        // Gerar o QR Code e obter o caminho da imagem
//
        // Setar o caminho da imagem no objeto QrCode
        // qrCode.setImagePath(qrCodeImagePath); // Supondo que exista um campo para o caminho da imagem

        // Salvar o objeto QrCode no banco de dados
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
