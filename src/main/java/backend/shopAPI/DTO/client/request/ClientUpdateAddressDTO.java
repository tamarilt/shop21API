package backend.shopAPI.DTO.client.request;

import java.util.UUID;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUpdateAddressDTO {
    @NotNull(message = "ID клиента обязателен")
    private UUID clientId;
    
    @Valid
    @NotNull(message = "Адрес не может быть пустым")
    private AddressesDTO address;
}
