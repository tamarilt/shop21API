package backend.shopAPI.mappers;

import backend.shopAPI.models.Supplier;
import backend.shopAPI.DTO.supplier.request.SupplierCreateDTO;
import backend.shopAPI.DTO.supplier.response.SupplierResponseDTO;
import backend.shopAPI.models.Addresses;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    
    @Autowired
    private AddressesMapper addressesMapper;
    
    public SupplierResponseDTO toDto(Supplier supplier, Addresses address) {
        SupplierResponseDTO dto = new SupplierResponseDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setPhone_number(supplier.getPhone_number());
        if (address != null) {
            dto.setAddress(addressesMapper.toDto(address));
        }
        
        return dto;
    }
    
    public Supplier toEntity(SupplierCreateDTO dto) {
        return toEntity(dto, null);
    }
    
    public Supplier toEntity(SupplierCreateDTO dto, UUID addressId) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setPhone_number(dto.getPhone_number());
        if (addressId != null) {
            supplier.setAddress_id(addressId);
        }
        return supplier;
    }
}
