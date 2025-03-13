

evt str btr than evtClz mode

=def evt as str betr




LgnHdr3.java

public static final String loginVldObsvs="loginVldObsvs";


=def evtHdr fun
@annos.Observes({LoginEvtObsvs})
public @NotNull Map<String, String> getTokenJwt(@NotNull RegDto Udto) {


=def evt n evtHdr    

=pubevt

      //  usrdto.set(dtoReg);
        evtPublisherObsv.publishEvent(loginVldObsvs, new UsernamePasswordCredential(dtoReg.uname, dtoReg.pwd));

        evtPublisherObsv.publishEvent(LoginEvtObsvs, dtoReg);