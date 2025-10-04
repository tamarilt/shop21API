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

import backend.shopAPI.DTO.products.request.ProductCreateDTO;
import backend.shopAPI.DTO.products.request.ProductOnlyIdDTO;
import backend.shopAPI.DTO.products.request.ProductSubStockDTO;
import backend.shopAPI.DTO.products.response.ProductResponseAllDTO;
import backend.shopAPI.DTO.products.response.ProductResponseDTO;
import backend.shopAPI.DTO.errors.ErrorResponse;
import backend.shopAPI.services.ProductServices;
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
@RequestMapping("/api/v1/products")
@Tag(name = "Контроллеры для товаров")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @PostMapping
    @Operation(summary = "Создать товар")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Товар успешно создан",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO dto) {
        log.info("POST /api/v1/products - запрос создания товара: {}", dto.getName());
        ProductResponseDTO created = productServices.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить товар по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Товар найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "id товара", required = true)
            @PathVariable UUID id) {
        log.info("GET /api/v1/products/{} - запрос получения товара по ID", id);
        ProductOnlyIdDTO dto = new ProductOnlyIdDTO();
        dto.setId(id);
        ProductResponseDTO product = productServices.getProductById(dto);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @Operation(summary = "Получить все товары")
    @ApiResponse(responseCode = "200", description = "Список товаров успешно получен",
                content = @Content(mediaType = "application/json", 
                                 schema = @Schema(implementation = ProductResponseAllDTO.class)))
    @RequireAuth
    public ResponseEntity<ProductResponseAllDTO> getAllProducts() {
        log.info("GET /api/v1/products - запрос получения всех товаров");
        ProductResponseAllDTO products = productServices.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/substock")
    @Operation(summary = "Уменьшить количество товара")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Количество товара успешно уменьшено",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные или недостаточно товара на складе",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ProductResponseDTO> subStock(@Valid @RequestBody ProductSubStockDTO dto) {
        log.info("PUT /api/v1/products/substock - запрос уменьшения количества товара ID: {}", dto.getId());
        ProductResponseDTO updated = productServices.subStock(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    @Operation(summary = "Удалить товар")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Товар успешно удален",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ProductResponseDTO> deleteProduct(@Valid @RequestBody ProductOnlyIdDTO dto) {
        log.info("DELETE /api/v1/products - запрос удаления товара ID: {}", dto.getId());
        ProductResponseDTO deleted = productServices.deleteProduct(dto);
        return ResponseEntity.ok(deleted);
    }
}
