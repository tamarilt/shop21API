package backend.shopAPI.DTO.client.request;

import java.time.OffsetDateTime;

import backend.shopAPI.DTO.addresses.AddressesDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateDTO {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 15, message = "Имя не может быть длиннее 15 символов")
    private String client_name;
    
    @Size(max = 15, message = "Фамилия не может быть длиннее 15 символов")
    private String client_surname;
    
    @NotNull(message = "Дата рождения не может быть пустой")
    private OffsetDateTime birthday;
    
    @NotNull(message = "Пол не может быть пустым")
    @Pattern(regexp = "^(male|female)$", message = "Пол должен быть 'male' или 'female'")
    private String gender;
    
    @Valid
    @NotNull(message = "Адрес не может быть пустым")
    private AddressesDTO address;
}
