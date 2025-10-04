package backend.shopAPI.DTO.supplier.response;

import java.util.UUID;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierResponseDTO {
    private UUID id;
    private String name;
    private AddressesDTO address;
    private String phone_number;
}
