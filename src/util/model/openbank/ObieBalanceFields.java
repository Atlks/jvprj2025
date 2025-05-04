package util.model.openbank;

public enum ObieBalanceFields {
    ACCOUNT_ID("AccountId"),
    AMOUNT("Amount"),
    CREDIT_DEBIT_INDICATOR("CreditDebitIndicator"),
    TYPE("Type"),
    DATE_TIME("DateTime"),
    CREDIT_LINE("CreditLine");

    private final String fieldName;

    ObieBalanceFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
