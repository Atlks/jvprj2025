package util.annos;

import jakarta.persistence.FetchType;

public @interface OneToManyTips {
    FetchType fetch();
}
