package backend.shopAPI.DTO.supplier.request;

import java.util.UUID;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierUpdateAddressDTO {
    @NotNull(message = "ID поставщика обязателен")
    private UUID supplierId;
    
    @Valid
    @NotNull(message = "Адрес не может быть пустым")
    private AddressesDTO address;
}
