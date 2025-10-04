package backend.shopAPI.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import backend.shopAPI.DTO.images.request.ImageRequestDTO;
import backend.shopAPI.DTO.images.request.ImageRequestIdDTO;
import backend.shopAPI.DTO.images.response.ImageResponseDTO;
import backend.shopAPI.services.ImagesServices;
import backend.shopAPI.DTO.errors.ErrorResponse;
import backend.shopAPI.auth.RequireAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/images")
@Tag(name = "Контроллеры для изображений")
public class ImagesController {

    @Autowired
    private ImagesServices imagesServices;

    @PostMapping
    @Operation(summary = "Создать изображение")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Изображение успешно создано",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ImageResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ImageResponseDTO> createImage(@Valid @RequestBody ImageRequestDTO dto) {
        ImageResponseDTO created = imagesServices.createImage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить изображение по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Изображение найдено",
                    content = @Content(mediaType = "application/octet-stream")),
        @ApiResponse(responseCode = "404", description = "Изображение не найдено",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<byte[]> getImageById(
            @Parameter(description = "id изображения", required = true)
            @PathVariable UUID id) {
        ImageResponseDTO imageResponse = imagesServices.getImageById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(imageResponse.getImage());
    }

    @PostMapping("/product")
    @Operation(summary = "Получить изображение по ID товара")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Изображение товара найдено",
                    content = @Content(mediaType = "application/octet-stream")),
        @ApiResponse(responseCode = "404", description = "Товар не найден или у товара нет изображения",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<byte[]> getImageByProductId(@Valid @RequestBody ImageRequestIdDTO dto) {
        ImageResponseDTO imageResponse = imagesServices.getImageByProductId(dto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(imageResponse.getImage());
    }

    @PutMapping
    @Operation(summary = "Обновить изображение")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Изображение успешно обновлено",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ImageResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Изображение не найдено",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ImageResponseDTO> updateImage(@Valid @RequestBody ImageRequestDTO dto) {
        ImageResponseDTO updated = imagesServices.updateImage(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    @Operation(summary = "Удалить изображение")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Изображение успешно удалено",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ImageResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Изображение не найдено",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ImageResponseDTO> deleteImage(@Valid @RequestBody ImageRequestIdDTO dto) {
        ImageResponseDTO deleted = imagesServices.deleteImage(dto);
        return ResponseEntity.ok(deleted);
    }
}
