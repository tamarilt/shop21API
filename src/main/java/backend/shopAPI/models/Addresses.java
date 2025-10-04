package backend.shopAPI.models;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Addresses {
    private UUID id;
    private String country;
    private String city;
    private String street;
}