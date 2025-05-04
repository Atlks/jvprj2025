package util.model.openbank;

public enum ObieTransactionFields {
    TRANSACTION_ID("TransactionId"),
    CREDIT_DEBIT_INDICATOR("CreditDebitIndicator"),
    STATUS("Status"),
    TRANSACTION_AMOUNT("TransactionAmount"),
    BOOKING_DATE_TIME("BookingDateTime"),
    VALUE_DATE_TIME("ValueDateTime"),
    TRANSACTION_INFORMATION("TransactionInformation"),
    MERCHANT_DETAILS("MerchantDetails"),
    CREDITOR_ACCOUNT("CreditorAccount"),
    DEBTOR_ACCOUNT("DebtorAccount"),
    BANK_TRANSACTION_CODE("BankTransactionCode"),
    PROPRIETARY_BANK_TRANSACTION_CODE("ProprietaryBankTransactionCode"),
    CHARGE_AMOUNT("ChargeAmount"),
    CHARGE_BEARER("ChargeBearer"),
    EXCHANGE_RATE_INFORMATION("ExchangeRateInformation");

    private final String fieldName;

    ObieTransactionFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
