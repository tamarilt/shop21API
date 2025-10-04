package backend.shopAPI.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.shopAPI.DAO.ImagesDAO;
import backend.shopAPI.DAO.ProductDAO;
import backend.shopAPI.DTO.products.request.ProductCreateDTO;
import backend.shopAPI.DTO.products.request.ProductOnlyIdDTO;
import backend.shopAPI.DTO.products.request.ProductSubStockDTO;
import backend.shopAPI.DTO.products.response.ProductResponseAllDTO;
import backend.shopAPI.DTO.products.response.ProductResponseDTO;
import backend.shopAPI.exception.InsufficientStockException;
import backend.shopAPI.mappers.ProductMapper;
import backend.shopAPI.models.Product;

@Service
public class ProductServices {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ImagesDAO imagesDAO;

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        if (productDTO.getImage() != null) {
            UUID imageId = imagesDAO.save(productDTO.getImage());
            product.setImage_id(imageId);
        }
        
        productDAO.save(product);
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductResponseDTO getProductById(ProductOnlyIdDTO dto) {
        Product product = productDAO.getById(dto.getId());
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductResponseAllDTO getAllProducts() {
        ProductResponseAllDTO response = new ProductResponseAllDTO();
        response.setProducts(productMapper.toDtoList(productDAO.getAll()));
        return response;
    }

    @Transactional
    public ProductResponseDTO deleteProduct(ProductOnlyIdDTO dto) {
        Product product = productDAO.getById(dto.getId());
        productDAO.deleteById(dto.getId());
        return productMapper.toDto(product);
    }
    @Transactional
    public ProductResponseDTO subStock(ProductSubStockDTO dto) {
        Product currentProduct = productDAO.getById(dto.getId());
        if (currentProduct.getAvailable_stock() - dto.getSubAmount() < 0) {
            throw new InsufficientStockException(
                String.format("Остаток не может быть меньше 0. Текущий остаток: %d, запрошено: %d", 
                    currentProduct.getAvailable_stock(), dto.getSubAmount())
            );
        }
        productDAO.subStock(dto.getId(), dto.getSubAmount());
        Product updatedProduct = productDAO.getById(dto.getId());
        return productMapper.toDto(updatedProduct);
    }
}
