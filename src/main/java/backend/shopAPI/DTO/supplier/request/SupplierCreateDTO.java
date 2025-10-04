package backend.shopAPI.DTO.supplier.request;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierCreateDTO {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 31, message = "Имя не может быть длиннее 31 символа")
    private String name;
    
    @Valid
    @NotNull(message = "Адрес не может быть пустым")
    private AddressesDTO address;
    
    @NotBlank(message = "Телефонный номер не может быть пустым")
    @Size(max = 15, message = "Номер телефона не может быть длиннее 15 символов")
    private String phone_number;
}
