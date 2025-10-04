package backend.shopAPI.DTO.supplier.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDeleteDTO {
    @NotNull(message = "ID поставщика обязателен")
    private UUID id;
}
