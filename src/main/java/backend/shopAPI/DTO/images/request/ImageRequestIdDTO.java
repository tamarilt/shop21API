package backend.shopAPI.DTO.images.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequestIdDTO {
    @NotNull(message = "ID изображения обязателен")
    private UUID id;
}
