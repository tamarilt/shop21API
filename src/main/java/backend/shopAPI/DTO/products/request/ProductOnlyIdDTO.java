package backend.shopAPI.DTO.products.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOnlyIdDTO {
    @NotNull(message = "ID товара обязателен")
    private UUID id;
}
