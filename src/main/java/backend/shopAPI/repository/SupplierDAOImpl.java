package backend.shopAPI.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import backend.shopAPI.DAO.AddressesDAO;
import backend.shopAPI.DAO.SupplierDAO;
import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Supplier;

@Repository
public class SupplierDAOImpl implements SupplierDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AddressesDAO addressesDAO;

    @Override
    public void deleteById(UUID id) {
        String request = """
                DELETE FROM supplier
                WHERE id = ?
                """;
        jdbcTemplate.update(request, id);
    }

    @Override
    public List<Supplier> getAll() {
        String request = """
                SELECT * FROM supplier
                """;
        return jdbcTemplate.query(request, supplierRowMapper);
    }

    @Override
    public Supplier getById(UUID id) {
        String request = """
                SELECT * FROM supplier
                WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(request, supplierRowMapper, id);
    }

    @Override
    public UUID save(Supplier supplier) {
        String request = """
                INSERT INTO supplier (name, address_id, phone_number)
                VALUES (?,?,?)
                RETURNING id
                """;
        UUID id = jdbcTemplate.queryForObject(request, java.util.UUID.class, supplier.getName(), supplier.getAddress_id(), supplier.getPhone_number());
        supplier.setId(id);
        return id;
    }

    @Override
    public void updateSupplierAddress(UUID supplierId, Addresses address) {
        UUID newAddressId = addressesDAO.save(address);
        String updateRequest = "UPDATE supplier SET address_id = ? WHERE id = ?";
        jdbcTemplate.update(updateRequest, newAddressId, supplierId);
    }
    private final RowMapper<Supplier> supplierRowMapper = (rs,rowNum) -> {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getObject("id", java.util.UUID.class));
        supplier.setName(rs.getString("name"));
        supplier.setAddress_id(rs.getObject("address_id", java.util.UUID.class));
        supplier.setPhone_number(rs.getString("phone_number"));
        return supplier;
    };
}