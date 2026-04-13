package jota.developer.request;

import jota.developer.domain.Client;
import jota.developer.domain.School;
import jota.developer.enums.StatusPayment;
import jota.developer.enums.UniformSizeDown;
import jota.developer.enums.UniformSizeUp;
import jota.developer.enums.UniformType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostRequest {
    private Client client;
    private UniformType uniformType;
    private UniformSizeUp uniformSizeUp;
    private UniformSizeDown uniformSizeDown;
    private Integer quantity;
    private School school;
    private LocalDate deliveryDate;
    private String details;
    private StatusPayment statusPayment;
    private Double moneyGiven;
}
