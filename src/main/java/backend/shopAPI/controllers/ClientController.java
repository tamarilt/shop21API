package backend.shopAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import backend.shopAPI.DTO.client.request.ClientCreateDTO;
import backend.shopAPI.DTO.client.request.ClientDeleteDTO;
import backend.shopAPI.DTO.client.request.ClientUpdateAddressDTO;
import backend.shopAPI.DTO.client.response.ClientResponseAllDTO;
import backend.shopAPI.DTO.client.response.ClientResponseDTO;
import backend.shopAPI.services.ClientServices;
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
@RequestMapping("/api/v1/clients")
@Tag(name = "Контроллеры для клиентов")
public class ClientController {
    
    @Autowired
    private ClientServices clientServices;

    @GetMapping
    @Operation(summary = "Получить всех клиентов")
    @ApiResponse(responseCode = "200", description = "Список клиентов успешно получен",
                content = @Content(mediaType = "application/json", 
                                 schema = @Schema(implementation = ClientResponseAllDTO.class)))
    @RequireAuth
    public ResponseEntity<ClientResponseAllDTO> getAllClients() {
        log.info("GET /api/v1/clients - запрос получения всех клиентов");
        ClientResponseAllDTO clients = clientServices.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{surname}/{name}")
    @Operation(summary = "Найти клиентов по полному имени")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Клиенты найдены",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ClientResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Клиенты не найдены",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<List<ClientResponseDTO>> getClientByFullname(
            @Parameter(description = "Имя клиента", required = true)
            @PathVariable String name,
            @Parameter(description = "Фамилия клиента", required = true)
            @PathVariable String surname) {
        log.info("GET /api/v1/clients/{}/{} - запрос поиска клиента", surname, name);
        List<ClientResponseDTO> clients = clientServices.getClientByFullname(name, surname);
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    @Operation(summary = "Создать клиента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Клиент успешно создан",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ClientResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientCreateDTO dto) {
        log.info("POST /api/v1/clients - запрос создания клиента");
        ClientResponseDTO created = clientServices.createClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping
    @Operation(summary = "Обновить адрес клиента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Адрес клиента успешно обновлен",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ClientResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ClientResponseDTO> updateClientAddress(@Valid @RequestBody ClientUpdateAddressDTO dto) {
        log.info("PUT /api/v1/clients - запрос обновления адреса клиента");
        ClientResponseDTO updated = clientServices.updateClientAddress(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    @Operation(summary = "Удалить клиента")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Клиент успешно удален",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ClientResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequireAuth
    public ResponseEntity<ClientResponseDTO> deleteClient(@Valid @RequestBody ClientDeleteDTO dto) {
        log.info("DELETE /api/v1/clients - запрос удаления клиента");
        ClientResponseDTO deleted = clientServices.deleteClient(dto);
        return ResponseEntity.ok(deleted);
    }
}