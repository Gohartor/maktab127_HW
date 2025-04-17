package HW17.repository;


import HW17.entity.Card;
import HW17.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card, Integer> {

    Optional<Card> findByCardNumber(String cardNumber);

    List<Card> findByBankName(String bankName);

    List<Card> findByUserId(Integer userId);
}
