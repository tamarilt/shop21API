package backend.shopAPI.models;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Supplier {
    private UUID id;
    private String name;
    private UUID address_id;
    private String phone_number;
}