package backend.shopAPI.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import backend.shopAPI.DTO.images.request.ImageRequestDTO;
import backend.shopAPI.DTO.images.response.ImageResponseDTO;
import backend.shopAPI.models.Images;

@Component
public class ImagesMapper {
    
    public ImageResponseDTO toDto(Images image) {
        ImageResponseDTO dto = new ImageResponseDTO();
        dto.setImage(image.getImage());
        return dto;
    }

    public Images toEntity(ImageRequestDTO dto, UUID imageId) {
        Images image = new Images();
        image.setId(imageId);
        image.setImage(dto.getImage());
        return image;
    }
}
