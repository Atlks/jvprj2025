package util.model.bet;

import entityx.baseObj;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

//  must jakarta not javax


    @Data
    public class BetOrdDto extends baseObj {






        public String drawDate="";


        public long timestamp;
        @NotBlank
        public String uname="";

        @NotBlank
        public String bettxt="";
    }

