package backend.shopAPI.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import backend.shopAPI.DAO.ProductDAO;
import backend.shopAPI.models.Product;

@Repository
public class ProductDAOImpl implements ProductDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getAll() {
        String request = """
                Select * FROM product;
                """;
        return jdbcTemplate.query(request, productRowMapper);
    }

    @Override
    public void deleteById(UUID id) {
        String request = """
                DELETE FROM product
                WHERE id = ?;
                """;
        jdbcTemplate.update(request, id);
    }

    @Override
    public Product getById(UUID id) {
        String request = """
                SELECT * FROM product
                WHERE id = ?;
                """;
        return jdbcTemplate.queryForObject(request, productRowMapper, id);
    }

    @Override
    public UUID save(Product product) {
       String request = """
               INSERT INTO product (name, category, price, available_stock, last_update_date, supplier_id, image_id)
               VALUES (?,?,?,?,?,?,?)
               RETURNING id;
               """;
        UUID id = jdbcTemplate.queryForObject(request,java.util.UUID.class, product.getName(), product.getCategory(), product.getPrice(), product.getAvailable_stock(), product.getLast_update_date(), product.getSupplier_id(), product.getImage_id());
        product.setId(id);
        return id;
    }

    @Override
    public Integer subStock(UUID id, int amount) {
        String request = """
                UPDATE product
                SET available_stock = available_stock - ?
                WHERE id = ? AND available_stock >= ?
                RETURNING available_stock;
                """;
        try {
            Integer newStock = jdbcTemplate.queryForObject(request, Integer.class, amount, id, amount);
            return newStock;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateImageId(UUID productId, UUID imageId) {
        String request = """
                UPDATE product
                SET image_id = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(request, imageId, productId);
    }
    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getObject("id", java.util.UUID.class));
        product.setName(rs.getString("name"));
        product.setCategory(rs.getString("category"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setAvailable_stock(rs.getInt("available_stock"));
        product.setLast_update_date(rs.getObject("last_update_date",java.time.OffsetDateTime.class));
        product.setSupplier_id(rs.getObject("supplier_id", java.util.UUID.class));
        product.setImage_id(rs.getObject("image_id", java.util.UUID.class));
        return product;
    };
}