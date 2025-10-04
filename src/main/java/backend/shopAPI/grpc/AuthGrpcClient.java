package backend.shopAPI.grpc;

import backend.shop21auth.AuthProto;
import backend.shop21auth.AuthServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class AuthGrpcClient {

    @GrpcClient("auth-service")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceStub;

    public AuthProto.RegisterReply registerUser(String email, String name, String surname, String phoneNumber, String password) {
        AuthProto.RegisterRequest request = AuthProto.RegisterRequest.newBuilder()
            .setEmail(email)
            .setName(name)
            .setSurname(surname)
            .setPhoneNumber(phoneNumber)
            .setPassword(password)
            .build();
        
        return authServiceStub.registerUser(request);
    }

    public AuthProto.LoginReply loginUser(String email, String password) {
        AuthProto.LoginRequest request = AuthProto.LoginRequest.newBuilder()
            .setEmail(email)
            .setPassword(password)
            .build();
        
        return authServiceStub.loginUser(request);
    }

    public AuthProto.ValidateTokenReply validateToken(String token) {
        AuthProto.ValidateTokenRequest request = AuthProto.ValidateTokenRequest.newBuilder()
            .setToken(token)
            .build();
        
        return authServiceStub.validateToken(request);
    }

    public AuthProto.RecoverPasswordReply recoverPassword(String email) {
        AuthProto.RecoverPasswordRequest request = AuthProto.RecoverPasswordRequest.newBuilder()
            .setEmail(email)
            .build();
        
        return authServiceStub.recoverPassword(request);
    }

    public AuthProto.ChangePasswordReply changePassword(String userId, String oldPassword, String newPassword) {
        AuthProto.ChangePasswordRequest request = AuthProto.ChangePasswordRequest.newBuilder()
            .setUserId(userId)
            .setOldPassword(oldPassword)
            .setNewPassword(newPassword)
            .build();
        
        return authServiceStub.changePassword(request);
    }
}