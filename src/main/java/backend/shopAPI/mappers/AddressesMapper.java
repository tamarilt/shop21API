package backend.shopAPI.mappers;

import org.springframework.stereotype.Component;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import backend.shopAPI.models.Addresses;

@Component
public class AddressesMapper {
    public AddressesDTO toDto(Addresses address) {
        AddressesDTO dto = new AddressesDTO();
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setStreet(address.getStreet());
        return dto;
    }
    public Addresses toEntity(AddressesDTO dto) {
        Addresses address = new Addresses();
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setStreet(dto.getStreet());
        return address;
    }
}
