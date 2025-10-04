package backend.shopAPI.mappers;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import backend.shopAPI.DTO.products.request.ProductCreateDTO;
import backend.shopAPI.DTO.products.response.ProductResponseDTO;
import backend.shopAPI.models.Product;

@Component
public class ProductMapper {
    
    public ProductResponseDTO toDto(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setAvailable_stock(product.getAvailable_stock());
        dto.setLast_update_date(product.getLast_update_date());
        dto.setSupplier_id(product.getSupplier_id());
        dto.setImage_id(product.getImage_id());
        return dto;
    }
    
    public List<ProductResponseDTO> toDtoList(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public Product toEntity(ProductCreateDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setAvailable_stock(dto.getAvailable_stock());
        product.setLast_update_date(OffsetDateTime.now());
        product.setSupplier_id(dto.getSupplier_id());
        return product;
    }
}
