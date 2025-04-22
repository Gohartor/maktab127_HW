package HW17.service;

import HW17.config.ApplicationContext;
import HW17.entity.Card;
import HW17.repository.CardRepository;

import java.util.List;
import java.util.Optional;

public class CardService {
    private final CardRepository cardRepository;

    //dto upper layer entity
    public CardService() {
        this.cardRepository = ApplicationContext.getInstance().getCardRepository();
    }

    public Card registerCard(Card card) {
        if (isCardNumberInvalid(card.getCardNumber())) {
            throw new RuntimeException("Invalid card number format.");
        }

        if (isCardNumberDuplicate(card.getCardNumber())) {
            throw new RuntimeException("Card number already exists.");
        }

        if (isBankNameInvalid(card.getBankName())) {
            throw new RuntimeException("Invalid bank name.");
        }

        if (isExpireDateInvalid(String.valueOf(card.getExpireDate()))) {
            throw new RuntimeException("Invalid expire date.");
        }

        if (isCvv2Invalid(card.getCvv2())) {
            throw new RuntimeException("Invalid CVV2.");
        }

        return cardRepository.create(card);
    }

    //delete by card number
    //show all card and delete
    public void deleteCard(Integer cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new RuntimeException("card with this ID does not exist.");
        }
        cardRepository.deleteById(cardId);
    }

    public Optional<Card> getCardByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }

    public List<Card> getCardsByBankName(String bankName) {
        return cardRepository.findByBankName(bankName);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    private boolean isCardNumberInvalid(String cardNumber) {
        return cardNumber == null || cardNumber.isEmpty();
    }

    private boolean isCardNumberDuplicate(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber).isPresent();
    }

    private boolean isBankNameInvalid(String bankName) {
        return bankName == null || bankName.isEmpty();
    }

    private boolean isExpireDateInvalid(String expireDate) {
        return expireDate == null;
    }

    private boolean isCvv2Invalid(String cvv2) {
        return cvv2 == null || cvv2.isEmpty();
    }
}
