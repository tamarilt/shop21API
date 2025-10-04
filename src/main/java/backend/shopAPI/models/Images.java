package backend.shopAPI.models;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Images {
    private UUID id;
    private byte[] image;
}