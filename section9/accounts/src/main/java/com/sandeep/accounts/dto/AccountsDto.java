package com.sandeep.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information")
public class AccountsDto {

	@Schema(
			description = "Account Number of Bank account", example = "3454433243"
	)
	@NotEmpty(message="Account number can't be a null or empty")
	@Pattern(regexp="(^$|[0-9]{10})", message="Account number must be 10 digits")
	private Long accountNumber;

	@Schema(
			description = "Account type of Bank account", example = "Savings"
	)
	@NotEmpty(message="Account type can't be a null or empty")
	private String accountType;

	@Schema(
			description = "Bank branch address", example = "123 NewYork"
	)
	@NotEmpty(message="Branch address can't be a null or empty")
	private String branchAddress;
}
