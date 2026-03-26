package lk.acx.suwasavi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firebaseUid;
    private Long serviceId;
    private String serviceName;
    private double price;
    private String imageUrl;
}