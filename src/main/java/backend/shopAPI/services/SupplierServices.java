package backend.shopAPI.services;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.shopAPI.DAO.AddressesDAO;
import backend.shopAPI.DAO.SupplierDAO;
import backend.shopAPI.DTO.supplier.request.SupplierCreateDTO;
import backend.shopAPI.DTO.supplier.request.SupplierDeleteDTO;
import backend.shopAPI.DTO.supplier.request.SupplierUpdateAddressDTO;
import backend.shopAPI.DTO.supplier.response.SupplierResponseAllDTO;
import backend.shopAPI.DTO.supplier.response.SupplierResponseDTO;
import backend.shopAPI.mappers.AddressesMapper;
import backend.shopAPI.mappers.SupplierMapper;
import backend.shopAPI.models.Addresses;
import backend.shopAPI.models.Supplier;

@Service
public class SupplierServices {
    @Autowired
    private SupplierDAO supplierDAO;

    @Autowired
    private AddressesDAO addressesDAO;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private AddressesMapper addressesMapper;

    @Transactional
    public SupplierResponseDTO createSupplier(SupplierCreateDTO supplierDTO) {
        Addresses address = addressesMapper.toEntity(supplierDTO.getAddress());
        UUID addressId = addressesDAO.save(address);
        Supplier supplier = supplierMapper.toEntity(supplierDTO, addressId);
        supplierDAO.save(supplier);
        Addresses createdAddress = addressesDAO.getById(addressId);
        return supplierMapper.toDto(supplier, createdAddress);
    }

    @Transactional
    public SupplierResponseDTO updateSupplierAddress(SupplierUpdateAddressDTO dto) {
        Supplier supplier = supplierDAO.getById(dto.getSupplierId());
        Addresses address = addressesMapper.toEntity(dto.getAddress());
        address.setId(supplier.getAddress_id());
        addressesDAO.update(address);
        Addresses updatedAddress = addressesDAO.getById(supplier.getAddress_id());
        return supplierMapper.toDto(supplier, updatedAddress);
    }

    @Transactional
    public SupplierResponseDTO deleteSupplier(SupplierDeleteDTO dto) {
        Supplier supplier = supplierDAO.getById(dto.getId());
        Addresses address = addressesDAO.getById(supplier.getAddress_id());
        UUID addressId = supplier.getAddress_id();
        supplierDAO.deleteById(dto.getId());
        addressesDAO.deleteById(addressId);
        return supplierMapper.toDto(supplier, address);
    }    
    
    @Transactional
    public SupplierResponseAllDTO getAllSuppliers() {
        List<Supplier> suppliers = supplierDAO.getAll();
        List<SupplierResponseDTO> supplierDTOs = suppliers.stream()
                .map(supplier -> {
                    Addresses address = addressesDAO.getById(supplier.getAddress_id());
                    return supplierMapper.toDto(supplier, address);
                })
                .toList();
        SupplierResponseAllDTO response = new SupplierResponseAllDTO();
        response.setSuppliers(supplierDTOs);
        return response;
    }
    
    @Transactional
    public SupplierResponseDTO getSupplierById(UUID id) {
        Supplier supplier = supplierDAO.getById(id);
        Addresses address = addressesDAO.getById(supplier.getAddress_id());
        return supplierMapper.toDto(supplier, address);
    }
}
