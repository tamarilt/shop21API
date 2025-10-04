package backend.shopAPI.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.shopAPI.DAO.AddressesDAO;
import backend.shopAPI.DAO.ClientDAO;
import backend.shopAPI.DTO.client.request.ClientCreateDTO;
import backend.shopAPI.DTO.client.request.ClientDeleteDTO;
import backend.shopAPI.DTO.client.request.ClientUpdateAddressDTO;
import backend.shopAPI.DTO.client.response.ClientResponseAllDTO;
import backend.shopAPI.DTO.client.response.ClientResponseDTO;
import backend.shopAPI.mappers.AddressesMapper;
import backend.shopAPI.mappers.ClientMapper;
import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Client;

@Service
public class ClientServices {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private AddressesDAO addressesDAO;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private AddressesMapper addressesMapper;

    @Transactional
    public ClientResponseDTO createClient(ClientCreateDTO clientDTO) {
        Addresses address = addressesMapper.toEntity(clientDTO.getAddress());
        UUID addressId = addressesDAO.save(address);
        Client client = clientMapper.toEntity(clientDTO, addressId);
        clientDAO.save(client);
        return clientMapper.toDto(client);
    }

    @Transactional
    public ClientResponseDTO deleteClient(ClientDeleteDTO dto) {
        Client client = clientDAO.getById(dto.getId());
        UUID addressId = client.getAddressId();
        clientDAO.deleteById(dto.getId());
        addressesDAO.deleteById(addressId);
        return clientMapper.toDto(client);
    }

    @Transactional
    public List<ClientResponseDTO> getClientByFullname(String name, String surname) {
        List<Client> clients = clientDAO.getClientsByFullName(name, surname);
        return clientMapper.toDtoList(clients);
    }

    @Transactional
    public ClientResponseAllDTO getAllClients() {
        ClientResponseAllDTO response = new ClientResponseAllDTO();
        response.setClients(clientMapper.toDtoList(clientDAO.getAll()));
        return response;
    }

    @Transactional
    public ClientResponseDTO updateClientAddress(ClientUpdateAddressDTO dto) {
        Addresses address = addressesMapper.toEntity(dto.getAddress());
        clientDAO.updateAddress(dto.getClientId(), address);
        Client updatedClient = clientDAO.getById(dto.getClientId());
        return clientMapper.toDto(updatedClient);
    }
}
