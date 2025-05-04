package util.model.openbank;

public enum ObieAccFields {
    ACCOUNT_ID("AccountId"),
    ACCOUNT_TYPE("AccountType"),
    ACCOUNT_SUB_TYPE("AccountSubType"),
    ACCOUNT_STATUS("Status"),
    ACCOUNT_CURRENCY("Currency"),
    ACCOUNT_NAME("Name"),
    ACCOUNT_IDENTIFICATION("Identification"),
    ACCOUNT_SCHEME_NAME("SchemeName");

    private final String fieldName;

    ObieAccFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

