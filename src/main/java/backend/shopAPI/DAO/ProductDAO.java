package backend.shopAPI.DAO;

import java.util.List;
import java.util.UUID;

import backend.shopAPI.models.Product;

public interface ProductDAO {
    UUID save(Product product);
    Integer subStock(UUID id, int amount);
    Product getById(UUID id);
    List<Product> getAll();
    void deleteById(UUID id);
    void updateImageId(UUID productId, UUID imageId);
}