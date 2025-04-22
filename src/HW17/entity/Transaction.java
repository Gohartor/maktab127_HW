package HW17.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    //card in entity -> source and destination
    private String sourceCardNumber;
    private String destinationCardNumber;
    private Double amount;
    //remove "transaction" from fields
    private Double transactionFee;
    private LocalDateTime transactionTimestamp;
    private TransactionType transactionType;
    private TransactionStatus status;

}
