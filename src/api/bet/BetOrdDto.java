package api.bet;

import entityx.baseObj;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



//import javax.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
//  must jakarta not javax

    @Entity
    @Table(name = "OrdBet")
    @Data
    public class BetOrdDto extends baseObj {






        public String drawDate="";


        public long timestamp;
        @NotBlank
        public String uname="";

        @NotBlank
        public String bettxt="";
    }

