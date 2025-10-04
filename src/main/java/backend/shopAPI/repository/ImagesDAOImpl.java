package backend.shopAPI.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import backend.shopAPI.DAO.ImagesDAO;
import backend.shopAPI.models.Images;

@Repository
public class ImagesDAOImpl implements ImagesDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteById(UUID id) {
        String request = """
        DELETE FROM images
        WHERE id = ?
        """;
        jdbcTemplate.update(request, id);
    }

    @Override
    public Images getById(UUID id) {
        String request = """
        SELECT * FROM images
        WHERE id = ?
        """;
        return jdbcTemplate.queryForObject(request, imageRowMapper, id);
    }

    @Override
    public Images getByProductId(UUID id) {
        String request = """
        SELECT images.* FROM product 
        JOIN images ON product.image_id = images.id
        WHERE product.id = ?
        """;
        return jdbcTemplate.queryForObject(request, imageRowMapper, id);
    }

    @Override
    public UUID save(byte[] arr) {
        String request = "INSERT INTO images (image) VALUES (?) RETURNING id";
        return jdbcTemplate.queryForObject(request, java.util.UUID.class, arr);
    }

    @Override
    public void updatePicture(UUID id, byte[] arr) {
        String request = """
        UPDATE images
        SET image = ?
        WHERE id = ?
        """;
        jdbcTemplate.update(request, arr, id);
    }

    private final RowMapper<Images> imageRowMapper = (rs, rowNum) -> {
        Images images = new Images();
        images.setId(rs.getObject("id", java.util.UUID.class));
        images.setImage(rs.getBytes("image"));
        return images;
    };
}
