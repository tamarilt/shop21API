package backend.shopAPI.controllers;

import backend.shopAPI.DTO.users.request.UserRegisterDTO;
import backend.shopAPI.DTO.users.request.UserResetPasswordDTO;
import backend.shopAPI.DTO.users.request.UserChangePasswordDTO;
import backend.shopAPI.DTO.users.response.UserResponse;
import backend.shopAPI.DTO.users.request.UserLoginDTO;
import backend.shopAPI.DTO.users.response.UserLoginResponse;
import backend.shopAPI.grpc.AuthGrpcClient;
import backend.shopAPI.auth.RequireAuth;
import backend.shopAPI.DTO.errors.ErrorResponse;
import backend.shop21auth.AuthProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Аутентификация", description = "API для регистрации, входа и управления аккаунтом")
public class AuthController {

    @Autowired
    private AuthGrpcClient authGrpcClient;

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя", description = "Создание нового аккаунта пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибки валидации данных",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UserResponse register(@Valid @RequestBody UserRegisterDTO request) {
        AuthProto.RegisterReply reply = authGrpcClient.registerUser(
            request.getEmail(),
            request.getName(),
            request.getSurname(),
            request.getPhoneNumber(),
            request.getPassword()
        );
        UserResponse response = new UserResponse();
        response.setResponse(reply.getMessage());
        return response;
    }

    @PostMapping("/auth")
    @Operation(summary = "Авторизация пользователя", description = "Вход в систему")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизован",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = UserLoginResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибки валидации или неверные учетные данные",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UserLoginResponse login(@Valid @RequestBody UserLoginDTO request) {
        AuthProto.LoginReply reply = authGrpcClient.loginUser(
            request.getEmail(),
            request.getPassword()
        );
        UserLoginResponse response = new UserLoginResponse();
        response.setMessage(reply.getMessage());
        response.setToken(reply.getToken());
        return response;
    }

    @PostMapping("/reset")
    @Operation(summary = "Восстановление пароля", description = "Отправка ссылки для восстановления пароля на email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка для восстановления отправлена",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибки валидации email",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class)))
    })
    public UserResponse resetPassword(@Valid @RequestBody UserResetPasswordDTO request) {
        AuthProto.RecoverPasswordReply reply = authGrpcClient.recoverPassword(request.getEmail());
        UserResponse response = new UserResponse();
        response.setResponse(reply.getMessage());
        return response;
    }

    @PostMapping("/changepass")
    @RequireAuth
    @Operation(summary = "Изменение пароля", description = "Изменение пароля авторизованного пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пароль успешно изменен",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ошибки валидации паролей",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public UserResponse changePassword(@Valid @RequestBody UserChangePasswordDTO request, HttpServletRequest httpRequest) {
        String userId = (String) httpRequest.getAttribute("userId");
        AuthProto.ChangePasswordReply reply = authGrpcClient.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        UserResponse response = new UserResponse();
        response.setResponse(reply.getMessage());
        return response;
    }
}