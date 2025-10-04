package backend.shopAPI.DTO.client.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDeleteDTO {
    @NotNull(message = "ID клиента обязателен")
    private UUID id;
}
