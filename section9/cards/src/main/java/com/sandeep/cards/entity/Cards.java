package com.sandeep.cards.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Cards extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long cardId;

    @Column(name="mobile_number")
    private String mobileNumber;

    @Column(name="card_number")
    private String cardNumber;

    @Column(name="card_type")
    private String cardType;

    @Column(name="total_limit")
    private int totalLimit;

    @Column(name="amount_used")
    private int amountUsed;

    @Column(name="available_amount")
    private int availableAmount;


}
