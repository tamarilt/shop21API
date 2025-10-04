package backend.shopAPI.repository;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import backend.shopAPI.DAO.AddressesDAO;
import backend.shopAPI.models.Addresses;

@Repository
public class AddressesDAOImpl implements AddressesDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UUID save(Addresses address) {
        String request = "SELECT id FROM addresses WHERE country = ? AND city = ? AND street = ?";
        try {
            UUID existingId = jdbcTemplate.queryForObject(request, UUID.class,
                address.getCountry(), address.getCity(), address.getStreet());
            address.setId(existingId);
            return existingId;
        } catch (EmptyResultDataAccessException e) {
            request = "INSERT INTO addresses (country, city, street) VALUES (?,?,?) RETURNING id";
            UUID id = jdbcTemplate.queryForObject(request, UUID.class, 
                address.getCountry(), address.getCity(), address.getStreet());
            address.setId(id);
            return id;
        }
    }

    @Override
    public Addresses getById(UUID id) {
        String request = "SELECT * FROM addresses WHERE id = ?";
        return jdbcTemplate.queryForObject(request, addressesRowMapper, id);
    }
    
    @Override
    public void update(Addresses address) {
        String request = "UPDATE addresses SET country = ?, city = ?, street = ? WHERE id = ?";
        jdbcTemplate.update(request, address.getCountry(), address.getCity(), address.getStreet(), address.getId());
    }
    
    @Override
    public void deleteById(UUID id) {
        String request = """
            SELECT 
                (SELECT COUNT(*) FROM supplier WHERE address_id = ?) +
                (SELECT COUNT(*) FROM client WHERE address_id = ?) AS total_count
            """;
        
        Integer usageCount = jdbcTemplate.queryForObject(request, Integer.class, id, id);
        
        if (usageCount == null || usageCount == 0) {
            request = "DELETE FROM addresses WHERE id = ?";
            jdbcTemplate.update(request, id);
        }
    }
    
    private RowMapper<Addresses> addressesRowMapper = (rs,rowNum) -> {
        Addresses address = new Addresses();
        address.setId(rs.getObject("id", java.util.UUID.class));
        address.setCountry(rs.getString("country"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        return address;
    };
}
