package backend.shopAPI.DTO.supplier.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierResponseAllDTO {
    private List<SupplierResponseDTO> suppliers;
}
