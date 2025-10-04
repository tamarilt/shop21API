package backend.shopAPI.models;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private UUID id;
    private String clientName;
    private String clientSurname;
    private OffsetDateTime birthday;
    private String gender;
    private OffsetDateTime registrationDate;
    private UUID addressId;
}