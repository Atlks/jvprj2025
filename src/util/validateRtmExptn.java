package util;

import util.tx.findByIdExptn_CantFindData;

public class validateRtmExptn extends RuntimeException {
    public validateRtmExptn(String message, findByIdExptn_CantFindData e) {
        super(message,e);
    }
}
