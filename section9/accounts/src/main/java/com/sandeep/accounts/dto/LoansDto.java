package com.sandeep.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
        name = "Loans",
        description = "Schema to hold Loans information")
public class LoansDto {

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp="(^$|[0-9]{10})", message="Mobile number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message="Loan number can't be null or empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits")
    @Schema(
            description = "Loan Number of the customer", example = "548732457654"
    )
    private String loanNumber;

    @Schema(
            description = "Loan type of the customer", example = "Home Loan"
    )
    @NotEmpty(message="Loan type can't be null or empty")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount", example = "100000"
    )
    private Integer totalLoan;

    @Schema(
            description = "Amount paid by the customer", example = "9345"
    )
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private Integer amountPaid;

    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private Integer outstandingAmount;
}
