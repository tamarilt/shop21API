package backend.shopAPI.DTO.users.request;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserChangePasswordDTO {
    @NotBlank(message = "Старый пароль не может быть пустым")
    private String oldPassword;
    
    @NotBlank(message = "Новый пароль не может быть пустым")
    @Size(min = 6, message = "Новый пароль должен содержать от 6 символов")
    private String newPassword;
}
