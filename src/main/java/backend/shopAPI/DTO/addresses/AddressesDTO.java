package backend.shopAPI.DTO.addresses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressesDTO {
    @NotBlank(message = "Страна не может быть пустой")
    @Size(max = 15, message = "Страна не может быть длиннее 15 символов")
    private String country;
    
    @NotBlank(message = "Город не может быть пустым")
    @Size(max = 15, message = "Город не может быть длиннее 15 символов")
    private String city;
    
    @NotBlank(message = "Улица не может быть пустой")
    @Size(max = 15, message = "Улица не может быть длиннее 15 символов")
    private String street;
}
