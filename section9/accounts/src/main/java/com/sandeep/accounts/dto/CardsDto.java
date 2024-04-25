package com.sandeep.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Loans",
        description = "Schema to hold Cards information")
@Data
public class CardsDto {

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp="(^$|[0-9]{10})", message="Mobile number must be 10 digits")
    private String mobileNumber;


    @NotEmpty(message="Card number can't be null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "Card Number must be 12 digits")
    private String cardNumber;

    @Schema(
            description = "Card type of the customer", example = "Card Type"
    )
    @NotEmpty(message="Card type can't be null or empty")
    private String cardType;

    @Positive(message = "Total amount should be greater than zero")
    @Schema(
            description = "Total amount", example = "100000"
    )
    private int totalLimit;

    @Schema(
            description = "Amount used by the customer", example = "9345"
    )
    @PositiveOrZero(message = "Amount used should be equal or greater than zero")
    private int amountUsed;

    @Schema(
            description = "Total available amount against a card", example = "99000"
    )
    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    private int availableAmount;
}
