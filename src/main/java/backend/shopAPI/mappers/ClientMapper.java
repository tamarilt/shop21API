package backend.shopAPI.mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.shopAPI.DAO.AddressesDAO;
import backend.shopAPI.DTO.addresses.AddressesDTO;
import backend.shopAPI.DTO.client.response.ClientResponseDTO;
import backend.shopAPI.DTO.client.request.ClientCreateDTO;
import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Client;

@Component
public class ClientMapper {
    
    @Autowired
    private AddressesDAO addressesDAO;
    
    @Autowired
    private AddressesMapper addressesMapper;
    
    public ClientResponseDTO toDto(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(client.getId());
        dto.setClient_name(client.getClientName());
        dto.setClient_surname(client.getClientSurname());
        dto.setBirthday(client.getBirthday());
        dto.setGender(client.getGender());
        dto.setRegistrationDate(client.getRegistrationDate());
        if (client.getAddressId() != null) {
            Addresses address = addressesDAO.getById(client.getAddressId());
            AddressesDTO addressDTO = addressesMapper.toDto(address);
            dto.setAddress(addressDTO);
        }
        
        return dto;
    }
    
    public List<ClientResponseDTO> toDtoList(List<Client> clients) {
        return clients.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public Client toEntity(ClientCreateDTO dto) {
        return toEntity(dto, null);
    }
    
    public Client toEntity(ClientCreateDTO dto, UUID addressId) {
        Client client = new Client();
        client.setClientName(dto.getClient_name());
        client.setClientSurname(dto.getClient_surname());
        client.setBirthday(dto.getBirthday());
        client.setGender(dto.getGender());
        if (addressId != null) {
            client.setAddressId(addressId);
        }
        return client;
    }
}
