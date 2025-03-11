package util;

import util.tx.findByIdExptn;

public class validateRtmExptn extends RuntimeException {
    public validateRtmExptn(String message, findByIdExptn e) {
        super(message,e);
    }
}
