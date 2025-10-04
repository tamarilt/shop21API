package backend.shopAPI.DTO.client.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {
    private UUID id;
    private String client_name;
    private String client_surname;
    private OffsetDateTime birthday;
    private String gender;
    private OffsetDateTime registrationDate;
    private AddressesDTO address;
}
