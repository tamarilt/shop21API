package backend.shopAPI.DAO;

import java.util.List;
import java.util.UUID;

import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Supplier;

public interface SupplierDAO {
    UUID save(Supplier supplier);
    void updateSupplierAddress(UUID supplierId, Addresses address);
    void deleteById(UUID id);
    List<Supplier> getAll();
    Supplier getById(UUID id);
}