package backend.shopAPI.services;

import backend.shopAPI.DAO.AddressesDAO;
import backend.shopAPI.DAO.ClientDAO;
import backend.shopAPI.DTO.addresses.AddressesDTO;
import backend.shopAPI.DTO.client.request.ClientCreateDTO;
import backend.shopAPI.DTO.client.request.ClientDeleteDTO;
import backend.shopAPI.DTO.client.response.ClientResponseDTO;
import backend.shopAPI.mappers.AddressesMapper;
import backend.shopAPI.mappers.ClientMapper;
import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServicesTest {

    @Mock
    private ClientDAO clientDAO;

    @Mock
    private AddressesDAO addressesDAO;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private AddressesMapper addressesMapper;

    @InjectMocks
    private ClientServices clientServices;

    private Client testClient;
    private ClientCreateDTO createDTO;
    private ClientResponseDTO responseDTO;
    private Addresses testAddress;
    private AddressesDTO addressDTO;
    private UUID testClientId;
    private UUID testAddressId;

    @BeforeEach
    void setUp() {
        testClientId = UUID.randomUUID();
        testAddressId = UUID.randomUUID();

        testAddress = new Addresses();
        testAddress.setId(testAddressId);
        testAddress.setStreet("Test Street");
        testAddress.setCity("Test City");
        testAddress.setCountry("Russia");

        addressDTO = new AddressesDTO();
        addressDTO.setStreet("Test Street");
        addressDTO.setCity("Test City");
        addressDTO.setCountry("Russia");

        testClient = new Client();
        testClient.setId(testClientId);
        testClient.setClientName("Ivan");
        testClient.setClientSurname("Ivanov");
        testClient.setGender("male");
        testClient.setBirthday(OffsetDateTime.now().minusYears(25));
        testClient.setAddressId(testAddressId);

        createDTO = new ClientCreateDTO();
        createDTO.setClient_name("Ivan");
        createDTO.setClient_surname("Ivanov");
        createDTO.setGender("male");
        createDTO.setBirthday(OffsetDateTime.now().minusYears(25));
        createDTO.setAddress(addressDTO);

        responseDTO = new ClientResponseDTO();
        responseDTO.setId(testClientId);
        responseDTO.setClient_name("Ivan");
        responseDTO.setClient_surname("Ivanov");
        responseDTO.setGender("male");
        responseDTO.setBirthday(OffsetDateTime.now().minusYears(25));
        responseDTO.setAddress(addressDTO);
    }

    @Test
    void testCreateClient_Success() {
        when(addressesMapper.toEntity(any())).thenReturn(testAddress);
        when(addressesDAO.save(any(Addresses.class))).thenReturn(testAddressId);
        when(clientMapper.toEntity(any(ClientCreateDTO.class), any(UUID.class))).thenReturn(testClient);
        when(clientMapper.toDto(testClient)).thenReturn(responseDTO);
        when(clientDAO.save(testClient)).thenReturn(testClientId);

        ClientResponseDTO result = clientServices.createClient(createDTO);

        assertNotNull(result);
        assertEquals("Ivan", result.getClient_name());
        assertEquals("Ivanov", result.getClient_surname());
        assertEquals("male", result.getGender());
        verify(addressesDAO, times(1)).save(any(Addresses.class));
        verify(clientDAO, times(1)).save(testClient);
        verify(clientMapper, times(1)).toDto(testClient);
    }

    @Test
    void testDeleteClient_Success() {
        ClientDeleteDTO deleteDTO = new ClientDeleteDTO();
        deleteDTO.setId(testClientId);
        
        when(clientDAO.getById(testClientId)).thenReturn(testClient);
        when(clientMapper.toDto(testClient)).thenReturn(responseDTO);
        doNothing().when(clientDAO).deleteById(testClientId);
        doNothing().when(addressesDAO).deleteById(testAddressId);

        ClientResponseDTO result = clientServices.deleteClient(deleteDTO);

        assertNotNull(result);
        assertEquals(testClientId, result.getId());
        verify(clientDAO, times(1)).getById(testClientId);
        verify(clientDAO, times(1)).deleteById(testClientId);
        verify(addressesDAO, times(1)).deleteById(testAddressId);
    }

    @Test
    void testCreateClient_NullAddress() {
        createDTO.setAddress(null);
        
        assertThrows(NullPointerException.class, () -> {
            clientServices.createClient(createDTO);
        });
    }

    @Test
    void testDeleteClient_NotFound() {
        ClientDeleteDTO deleteDTO = new ClientDeleteDTO();
        deleteDTO.setId(testClientId);
        when(clientDAO.getById(testClientId)).thenThrow(new RuntimeException("Client not found"));

        assertThrows(RuntimeException.class, () -> {
            clientServices.deleteClient(deleteDTO);
        });
        verify(clientDAO, times(1)).getById(testClientId);
        verify(clientDAO, never()).deleteById(any());
        verify(addressesDAO, never()).deleteById(any());
    }
}
