package util.model.openbank;


/**
 * only for doc ,,if code nameof,,just use lombk
 * @FieldNameConstants
 */
//openbank obie  Account实体的所有强制性字段，做成一个枚举ObieAccFields
public enum OBIEField {
    // Account Information
    ACCOUNT_ID("AccountId"),
    ACCOUNT_TYPE("AccountType"),
    ACCOUNT_SUB_TYPE("AccountSubType"),
    ACCOUNT_STATUS("Status"),
    ACCOUNT_CURRENCY("Currency"),
    ACCOUNT_NAME("Name"),
    ACCOUNT_SCHEME_NAME("SchemeName"),
    ACCOUNT_IDENTIFICATION("Identification"),
    ACCOUNT_SECONDARY_IDENTIFICATION("SecondaryIdentification"),

    // Transaction Information
    TRANSACTION_ID("TransactionId"),
    TRANSACTION_AMOUNT("Amount"),
    TRANSACTION_CREDIT_DEBIT_INDICATOR("CreditDebitIndicator"),
    TRANSACTION_STATUS("Status"),
    TRANSACTION_BOOKING_DATE_TIME("BookingDateTime"),
    TRANSACTION_VALUE_DATE_TIME("ValueDateTime"),
    TRANSACTION_TRANSACTION_INFORMATION("TransactionInformation"),

    // Payment Initiation
    PAYMENT_ID("PaymentId"),
    PAYMENT_STATUS("Status"),
    PAYMENT_CREATION_DATE_TIME("CreationDateTime"),
    PAYMENT_STATUS_UPDATE_DATE_TIME("StatusUpdateDateTime"),
    PAYMENT_EXPECTED_EXECUTION_DATE_TIME("ExpectedExecutionDateTime"),
    PAYMENT_EXPECTED_SETTLEMENT_DATE_TIME("ExpectedSettlementDateTime"),
    PAYMENT_INSTRUCTION_IDENTIFICATION("InstructionIdentification"),
    PAYMENT_END_TO_END_IDENTIFICATION("EndToEndIdentification"),
    PAYMENT_INSTRUCTED_AMOUNT("InstructedAmount"),
    PAYMENT_CREDITOR_ACCOUNT("CreditorAccount"),
    PAYMENT_DEBTOR_ACCOUNT("DebtorAccount"),

    // Risk Information
    RISK_PAYMENT_CONTEXT_CODE("PaymentContextCode"),
    RISK_MERCHANT_CATEGORY_CODE("MerchantCategoryCode"),
    RISK_MERCHANT_CUSTOMER_IDENTIFICATION("MerchantCustomerIdentification"),
    RISK_DELIVERY_ADDRESS("DeliveryAddress"),

    // Meta and Links
    META_TOTAL_PAGES("TotalPages"),
    META_FIRST_AVAILABLE_DATE_TIME("FirstAvailableDateTime"),
    META_LAST_AVAILABLE_DATE_TIME("LastAvailableDateTime"),
    LINKS_SELF("Self"),
    LINKS_FIRST("First"),
    LINKS_PREV("Prev"),
    LINKS_NEXT("Next"),
    LINKS_LAST("Last");

    private final String fieldName;

    OBIEField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
