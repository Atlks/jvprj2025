package util.model.openbank;


/**
 *
 *  * only for doc ,,if code nameof,,just use lombk
 *  * @FieldNameConstants
 */
public enum ObieAccFields {
    ACCOUNT("Account"),
    ACCOUNT_ID("AccountId"),
    ACCOUNT_TYPE("AccountType"),
    ACCOUNT_SUB_TYPE("AccountSubType"),
    ACCOUNT_STATUS("Status"),
    ACCOUNT_CURRENCY("Currency"),
    ACCOUNT_NAME("Name"),
    ACCOUNT_IDENTIFICATION("Identification"),
    ACCOUNT_SCHEME_NAME("SchemeName");

    private final String fieldName;

    ObieAccFields(String fieldName1) {
        this.fieldName = fieldName1;
    }

    public String getFieldName() {
        return fieldName;
    }
}

