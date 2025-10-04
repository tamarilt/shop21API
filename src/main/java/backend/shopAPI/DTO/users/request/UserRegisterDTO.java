package backend.shopAPI.DTO.users.request;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class UserRegisterDTO {
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, message = "Имя должно содержать от 2 символов")
    private String name;
    
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, message = "Фамилия должна содержать от 2 символов")
    private String surname;
    
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Неверный формат номера телефона")
    private String phoneNumber;
    
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать от 6 символов")
    private String password;
}
