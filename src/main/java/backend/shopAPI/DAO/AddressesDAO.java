package backend.shopAPI.DAO;

import java.util.UUID;
import backend.shopAPI.models.Addresses;

public interface AddressesDAO {
    UUID save(Addresses address);
    Addresses getById(UUID id);
    void update(Addresses address);
    void deleteById(UUID id);
}
