package com.sandeep.cards.service;

import com.sandeep.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCards(String mobileNumber);

    boolean updateCardDetails(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
