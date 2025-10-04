package backend.shopAPI.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import backend.shopAPI.DTO.supplier.request.SupplierCreateDTO;
import backend.shopAPI.DTO.supplier.request.SupplierDeleteDTO;
import backend.shopAPI.DTO.supplier.request.SupplierUpdateAddressDTO;
import backend.shopAPI.DTO.supplier.response.SupplierResponseAllDTO;
import backend.shopAPI.DTO.supplier.response.SupplierResponseDTO;
import backend.shopAPI.services.SupplierServices;
import backend.shopAPI.DTO.errors.ErrorResponse;
import backend.shopAPI.auth.RequireAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RestController
@RequestMapping("/api/v1/suppliers")
@Tag(name = "Контроллеры для поставщивиков")
public class SupplierController {
    
    @Autowired
    private SupplierServices supplierServices;

    @GetMapping
    @Operation(summary = "Получить всех поставщиков")
    @ApiResponse(responseCode = "200", description = "Список поставщиков успешно получен",
                content = @Content(mediaType = "application/json", 
                                 schema = @Schema(implementation = SupplierResponseAllDTO.class)))
    @RequireAuth
    public ResponseEntity<SupplierResponseAllDTO> getAllSuppliers() {
        log.info("GET /api/v1/suppliers - запрос получения всех поставщиков");
        SupplierResponseAllDTO suppliers = supplierServices.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить поставщика по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Поставщик найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = SupplierResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Поставщик не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<SupplierResponseDTO> getSupplierById(
            @Parameter(description = "id поставщика", required = true)
            @PathVariable UUID id) {
        log.info("GET /api/v1/suppliers/{} - запрос получения поставщика по ID", id);
        SupplierResponseDTO supplier = supplierServices.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    @Operation(summary = "Создать поставщика")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Поставщик успешно создан",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = SupplierResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierCreateDTO dto) {
        log.info("POST /api/v1/suppliers - запрос создания поставщика: {}", dto.getName());
        SupplierResponseDTO created = supplierServices.createSupplier(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    @Operation(summary = "Обновить адрес поставщика")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Адрес поставщика успешно обновлен",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = SupplierResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Поставщик не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<SupplierResponseDTO> updateSupplierAddress(@Valid @RequestBody SupplierUpdateAddressDTO dto) {
        SupplierResponseDTO updated = supplierServices.updateSupplierAddress(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    @Operation(summary = "Удалить поставщика")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Поставщик успешно удален",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = SupplierResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Поставщик не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Невозможно удалить поставщика (есть связанные товары)",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<SupplierResponseDTO> deleteSupplier(@Valid @RequestBody SupplierDeleteDTO dto) {
        SupplierResponseDTO deleted = supplierServices.deleteSupplier(dto);
        return ResponseEntity.ok(deleted);
    }
}
