package backend.shopAPI.DAO;

import java.util.UUID;

import backend.shopAPI.models.Images;

public interface ImagesDAO {
    UUID save(byte[] arr);
    void updatePicture(UUID id, byte[] arr);
    void deleteById(UUID id);
    Images getByProductId(UUID id);
    Images getById(UUID id);
}