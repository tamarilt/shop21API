package backend.shopAPI.DTO.images.request;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequestDTO {
    @NotEmpty(message = "Изображение обязательно")
    private byte[] image;
    
    @NotNull(message = "ID товара обязателен")
    private UUID id;
}
