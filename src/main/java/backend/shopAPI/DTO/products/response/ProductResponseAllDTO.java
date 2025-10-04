package backend.shopAPI.DTO.products.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseAllDTO {
    private List<ProductResponseDTO> products;
}
