package com.sandeep.cards.service.impl;

import com.sandeep.cards.constants.CardsConstants;
import com.sandeep.cards.dto.CardsDto;
import com.sandeep.cards.entity.Cards;
import com.sandeep.cards.exception.CardAlreadyExistsException;
import com.sandeep.cards.exception.ResourceNotFoundException;
import com.sandeep.cards.mapper.CardsMapper;
import com.sandeep.cards.repository.CardsRepository;
import com.sandeep.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardsService {

    CardsRepository cardsRepository;
    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()) {
            throw  new CardAlreadyExistsException("Loan already registered with given mobile number " + mobileNumber);
        }
       cardsRepository.save(createNewCard(mobileNumber));

    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        return newCard;
    }

    @Override
    public CardsDto fetchCards(String mobileNumber) {

       Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
               () -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber)
        );
        return CardsMapper.maptoCardsDto(card, new CardsDto());
    }

    @Override
    public boolean updateCardDetails(CardsDto cardsDto) {
        boolean isUpdated = false;
        if(cardsDto != null) {
            Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Cards", "cardNumber", cardsDto.getCardNumber())
            );
            CardsMapper.maptoCards(cardsDto, cards);
            cardsRepository.save(cards);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Cards", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(card.getCardId());
        return true;
    }
}
