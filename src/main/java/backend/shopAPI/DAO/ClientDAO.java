package backend.shopAPI.DAO;

import java.util.List;
import java.util.UUID;

import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Client;

public interface ClientDAO {
    UUID save(Client client);
    void deleteById(UUID id);
    Client getById(UUID id);
    List<Client> getClientsByFullName(String name, String surname);
    List<Client> getAll();
    List<Client> getAll(int limit, int offset);
    void updateAddress(UUID id, Addresses newAddress);
}