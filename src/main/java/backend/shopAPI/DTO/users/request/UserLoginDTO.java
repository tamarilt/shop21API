package backend.shopAPI.DTO.users.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class UserLoginDTO {
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
