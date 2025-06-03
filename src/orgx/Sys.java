package orgx;

public class Sys {
    public static Object exit(ExitDto o) {
        if(o.getPwd().equals("888"))
            System.exit(0);
        return null;
    }
}
