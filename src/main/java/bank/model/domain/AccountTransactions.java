package bank.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AccountTransactions {

    private Long accountId;

    private Long transactionId;

}
