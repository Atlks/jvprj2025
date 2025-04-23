package model.OpenBankingOBIE;

public enum CreditDebitIndicator {
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String value;

    CreditDebitIndicator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isCredit() {
        return this == CREDIT;
    }

    public boolean isDebit() {
        return this == DEBIT;
    }

    public static CreditDebitIndicator fromValue(String value) {
        for (CreditDebitIndicator indicator : values()) {
            if (indicator.value.equalsIgnoreCase(value)) {
                return indicator;
            }
        }
        throw new IllegalArgumentException("Unknown creditDebitIndicator: " + value);
    }
}
