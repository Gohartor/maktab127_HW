package HW17.entity;


import lombok.*;

import java.time.YearMonth;

@Setter
@Getter
@ToString //include and exclude
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {

    private String cardNumber;
    private String bankName;
    private YearMonth expireDate;
    private String cvv2;
    private Double balance;
    private User user;
}
