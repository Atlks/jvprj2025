package model.OpenBankingOBIE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class StatementAmount {


    /**
     * Indicates whether the amount is a credit or a debit.
     * OBIE Rule: A zero amount is considered a credit.
     * Enum: Credit | Debit
     */
    @NotNull

    private CreditDebitIndicator creditDebitIndicator;

    /**
     * Type of amount (e.g. Fee, Interest, Debit, Credit, etc.).
     * Based on OBExternalStatementAmountType1Code.
     */
    @NotNull

    private StatementAmountType1Code type;

    /**
     * Monetary amount with currency.
     * Amount: decimal value, max 13 integer + 5 fraction digits.
     * Currency: ISO 4217 code.
     */
    @NotNull
    @Valid

    private BigDecimal amount;
}
