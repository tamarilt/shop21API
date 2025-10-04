package backend.shopAPI.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private UUID id;
    private String name;
    private String category;
    private BigDecimal price;
    private int available_stock;
    private OffsetDateTime last_update_date;
    private UUID supplier_id;
    private UUID image_id;
}