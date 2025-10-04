package backend.shopAPI.DTO.products.request;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSubStockDTO {
    @NotNull(message = "ID товара обязателен")
    private UUID id;
    
    @Min(value = 1, message = "Количество для вычитания должно быть больше 0")
    @Max(value = 1000000, message = "Количество для вычитания слишком большое")
    private int subAmount;
}
