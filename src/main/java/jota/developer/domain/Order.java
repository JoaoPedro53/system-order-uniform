package jota.developer.domain;

import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeDown;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @EqualsAndHashCode.Include
    private Long orderId;
    private Client client;
    private UniformType uniformType;
    private UniformSizeUp uniformSizeUp;
    private UniformSizeDown uniformSizeDown;
    private Integer quantity;
    private School school;
    private LocalDate deliveryDate;
    private LocalDate purchaseDate;
    private String details;
    private StatusPayment statusPayment;
    private Double moneyGiven;
}

