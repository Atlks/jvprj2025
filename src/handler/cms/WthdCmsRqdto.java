package handler.cms;

import lombok.Data;
import org.springframework.context.event.EventListener;
import util.annos.CurrentUsername;


import java.math.BigDecimal;
@Data
public class WthdCmsRqdto {

    @CurrentUsername
    public  String uname;
    public BigDecimal amt;
}
