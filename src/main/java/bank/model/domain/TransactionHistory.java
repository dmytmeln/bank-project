package bank.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionHistory {

    private Long id;

    private String msg;

    private String transactionType;

    private Double moneyAmount;

    private LocalDateTime transactionDate;

}
