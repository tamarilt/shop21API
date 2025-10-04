package backend.shopAPI.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.shopAPI.DAO.ImagesDAO;
import backend.shopAPI.DAO.ProductDAO;
import backend.shopAPI.DTO.images.request.ImageRequestDTO;
import backend.shopAPI.DTO.images.request.ImageRequestIdDTO;
import backend.shopAPI.DTO.images.response.ImageResponseDTO;
import backend.shopAPI.mappers.ImagesMapper;
import backend.shopAPI.models.Images;

@Service
public class ImagesServices {

    @Autowired
    private ImagesDAO imagesDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ImagesMapper imagesMapper;

    @Transactional
    public ImageResponseDTO getImageById(UUID id) {
        Images image = imagesDAO.getById(id);
        return imagesMapper.toDto(image);
    }

    @Transactional
    public ImageResponseDTO getImageByProductId(ImageRequestIdDTO dto) {
        Images image = imagesDAO.getByProductId(dto.getId());
        return imagesMapper.toDto(image);
    }

    @Transactional
    public ImageResponseDTO createImage(ImageRequestDTO imageDTO) {
        UUID imageId = imagesDAO.save(imageDTO.getImage());
        productDAO.updateImageId(imageDTO.getId(), imageId);
        Images image = imagesMapper.toEntity(imageDTO, imageId);
        return imagesMapper.toDto(image);
    }

    @Transactional
    public ImageResponseDTO updateImage(ImageRequestDTO dto) {
        imagesDAO.updatePicture(dto.getId(), dto.getImage());
        Images updatedImage = imagesDAO.getById(dto.getId());
        return imagesMapper.toDto(updatedImage);
    }

    @Transactional
    public ImageResponseDTO deleteImage(ImageRequestIdDTO dto) {
        Images image = imagesDAO.getById(dto.getId());
        ImageResponseDTO response = imagesMapper.toDto(image);
        imagesDAO.deleteById(dto.getId());
        return response;
    }
}
