package backend.shopAPI.DTO.client.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseAllDTO {
    private List<ClientResponseDTO> clients;
}
