package backend.shopAPI.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import backend.shopAPI.DAO.ClientDAO;
import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Client;

@Repository
public class ClientDAOImpl implements ClientDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteById(UUID id) {
        String request = """
                DELETE FROM client
                WHERE id = ?
                """;
        jdbcTemplate.update(request, id);
    }

    @Override
    public List<Client> getAll() {
        String request = """
                SELECT * FROM client
                """;
        return jdbcTemplate.query(request, clientRowMapper);
    }

    @Override
    public List<Client> getAll(int limit, int offset) {
       String request = """
               SELECT * FROM client
               LIMIT ? OFFSET ?
               """;
        return jdbcTemplate.query(request, clientRowMapper, limit, offset);
    }

    @Override
    public Client getById(UUID id) {
        String request = """
                SELECT * FROM client
                WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(request, clientRowMapper, id);
    }

    @Override
    public List<Client> getClientsByFullName(String name, String surname) {
        String request = """
                SELECT * FROM client
                WHERE client_name = ? AND client_surname = ?
                """;
        return jdbcTemplate.query(request, clientRowMapper, name, surname);
    }

    @Override
    public UUID save(Client client) {
        String request = """
                INSERT INTO client (client_name,client_surname, birthday, gender, registration_date, address_id)
                VALUES(?,?,?,?,?,?)
                RETURNING id
                """;
        UUID id = jdbcTemplate.queryForObject(request, java.util.UUID.class, client.getClientName(), client.getClientSurname(), client.getBirthday(), client.getGender(), client.getRegistrationDate(), client.getAddressId());
        client.setId(id);
        return id;
    }

    @Override
    public void updateAddress(UUID id, Addresses newAddress) {
        String request = """
                UPDATE addresses
                SET country = ?, city = ?, street = ?
                WHERE id = (SELECT address_id FROM client WHERE id = ?)
                """;
        jdbcTemplate.update(request, newAddress.getCountry(), newAddress.getCity(), newAddress.getStreet(), id);
    }
    private final RowMapper<Client> clientRowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getObject("id", java.util.UUID.class));
        client.setClientName(rs.getString("client_name"));
        client.setClientSurname(rs.getString("client_surname"));
        client.setBirthday(rs.getObject("birthday", java.time.OffsetDateTime.class));
        client.setGender(rs.getString("gender"));
        client.setRegistrationDate(rs.getObject("registration_date", java.time.OffsetDateTime.class));
        client.setAddressId(rs.getObject("address_id", java.util.UUID.class));
        return client;
    };
}