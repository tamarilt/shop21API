package backend.shopAPI.DTO.users.request;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UserResetPasswordDTO {
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
}
