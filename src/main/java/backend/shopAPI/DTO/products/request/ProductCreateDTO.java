package backend.shopAPI.DTO.products.request;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {
    @NotBlank(message = "Название обязательно")
    @Size(max = 31, message = "Название не может быть длиннее 31 символа")
    private String name;

    @NotBlank(message = "Категория обязательна")
    @Size(max = 15, message = "Категория не может быть длиннее 15 символов")
    private String category;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    @DecimalMax(value = "99999999.99", message = "Цена слишком большая (максимум 99999999.99)")
    private BigDecimal price;

    @Min(value = 0, message = "Количество не может быть отрицательным")
    private int available_stock;

    @NotNull(message = "Поставщик обязателен")
    private UUID supplier_id;

    private byte[] image;
}
