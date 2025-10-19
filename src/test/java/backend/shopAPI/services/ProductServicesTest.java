package backend.shopAPI.services;

import backend.shopAPI.DAO.ImagesDAO;
import backend.shopAPI.DAO.ProductDAO;
import backend.shopAPI.DTO.products.request.ProductCreateDTO;
import backend.shopAPI.DTO.products.request.ProductOnlyIdDTO;
import backend.shopAPI.DTO.products.response.ProductResponseAllDTO;
import backend.shopAPI.DTO.products.response.ProductResponseDTO;
import backend.shopAPI.mappers.ProductMapper;
import backend.shopAPI.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServicesTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ImagesDAO imagesDAO;

    @InjectMocks
    private ProductServices productServices;

    private Product testProduct;
    private ProductCreateDTO createDTO;
    private ProductResponseDTO responseDTO;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        UUID supplierId = UUID.randomUUID();
        
        testProduct = new Product();
        testProduct.setId(testId);
        testProduct.setName("Test Product");
        testProduct.setCategory("Electronics");
        testProduct.setPrice(BigDecimal.valueOf(100.0));
        testProduct.setAvailable_stock(10);
        testProduct.setSupplier_id(supplierId);
        
        createDTO = new ProductCreateDTO();
        createDTO.setName("Test Product");
        createDTO.setCategory("Electronics");
        createDTO.setPrice(BigDecimal.valueOf(100.0));
        createDTO.setAvailable_stock(10);
        createDTO.setSupplier_id(supplierId);
        
        responseDTO = new ProductResponseDTO();
        responseDTO.setId(testId);
        responseDTO.setName("Test Product");
        responseDTO.setCategory("Electronics");
        responseDTO.setPrice(BigDecimal.valueOf(100.0));
        responseDTO.setAvailable_stock(10);
        responseDTO.setSupplier_id(supplierId);
    }

    @Test
    void testCreateProduct_Success() {
        when(productMapper.toEntity(createDTO)).thenReturn(testProduct);
        when(productMapper.toDto(testProduct)).thenReturn(responseDTO);
        when(productDAO.save(testProduct)).thenReturn(testId);
        
        ProductResponseDTO result = productServices.createProduct(createDTO);
        
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(BigDecimal.valueOf(100.0), result.getPrice());
        verify(productDAO, times(1)).save(testProduct);
        verify(productMapper, times(1)).toEntity(createDTO);
        verify(productMapper, times(1)).toDto(testProduct);
    }

    @Test
    void testCreateProduct_WithImage() {
        UUID imageId = UUID.randomUUID();
        byte[] imageData = "base64imagedata".getBytes();
        createDTO.setImage(imageData);
        when(productMapper.toEntity(createDTO)).thenReturn(testProduct);
        when(imagesDAO.save(any(byte[].class))).thenReturn(imageId);
        when(productMapper.toDto(testProduct)).thenReturn(responseDTO);
        when(productDAO.save(testProduct)).thenReturn(testId);
        
        ProductResponseDTO result = productServices.createProduct(createDTO);
        
        assertNotNull(result);
        verify(imagesDAO, times(1)).save(imageData);
        verify(productDAO, times(1)).save(testProduct);
    }

    @Test
    void testGetProductById_Success() {
        ProductOnlyIdDTO idDTO = new ProductOnlyIdDTO();
        idDTO.setId(testId);
        when(productDAO.getById(testId)).thenReturn(testProduct);
        when(productMapper.toDto(testProduct)).thenReturn(responseDTO);
        
        ProductResponseDTO result = productServices.getProductById(idDTO);
        
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("Test Product", result.getName());
        verify(productDAO, times(1)).getById(testId);
        verify(productMapper, times(1)).toDto(testProduct);
    }

    @Test
    void testGetAllProducts_Success() {
        List<Product> productList = Arrays.asList(testProduct);
        List<ProductResponseDTO> responseDTOList = Arrays.asList(responseDTO);
        
        when(productDAO.getAll()).thenReturn(productList);
        when(productMapper.toDtoList(productList)).thenReturn(responseDTOList);
        
        ProductResponseAllDTO result = productServices.getAllProducts();
        
        assertNotNull(result);
        assertNotNull(result.getProducts());
        assertEquals(1, result.getProducts().size());
        assertEquals("Test Product", result.getProducts().get(0).getName());
        verify(productDAO, times(1)).getAll();
        verify(productMapper, times(1)).toDtoList(productList);
    }

    @Test
    void testDeleteProduct_Success() {
        ProductOnlyIdDTO idDTO = new ProductOnlyIdDTO();
        idDTO.setId(testId);
        when(productDAO.getById(testId)).thenReturn(testProduct);
        when(productMapper.toDto(testProduct)).thenReturn(responseDTO);
        doNothing().when(productDAO).deleteById(testId);
        
        ProductResponseDTO result = productServices.deleteProduct(idDTO);
        
        assertNotNull(result);
        assertEquals(testId, result.getId());
        verify(productDAO, times(1)).getById(testId);
        verify(productDAO, times(1)).deleteById(testId);
        verify(productMapper, times(1)).toDto(testProduct);
    }

    @Test
    void testGetProductById_NotFound() {
        ProductOnlyIdDTO idDTO = new ProductOnlyIdDTO();
        idDTO.setId(testId);
        when(productDAO.getById(testId)).thenThrow(new RuntimeException("Product not found"));
        
        assertThrows(RuntimeException.class, () -> {
            productServices.getProductById(idDTO);
        });
        verify(productDAO, times(1)).getById(testId);
    }
}
