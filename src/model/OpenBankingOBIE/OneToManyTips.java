package model.OpenBankingOBIE;

import jakarta.persistence.FetchType;

public @interface OneToManyTips {
    FetchType fetch();
}
