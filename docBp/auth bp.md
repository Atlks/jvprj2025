

spec   


=javax anno secury
import jakarta.annotation.security.PermitAll;

=dft need login




=PermitAll set anno ,not login 

reg,login  page not need login ,,another all need 

@PermitAll
public class RegHandler

@PermitAll
//   http://localhost:8889/login?uname=008&pwd=000
public class LoginHdr extends BaseHdr {