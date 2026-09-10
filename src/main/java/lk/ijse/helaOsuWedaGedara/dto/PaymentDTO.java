package lk.ijse.helaOsuWedaGedara.dto;

import lk.ijse.helaOsuWedaGedara.enumiration.PaymentMethod;
import lk.ijse.helaOsuWedaGedara.enumiration.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private BigDecimal amount;
    private String transactionRef;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;
    private Long appointmentId;
}
