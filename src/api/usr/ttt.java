package api.usr;

import static biz.BaseHdr.getPrmClass;

public class ttt {
    public static void main(String[] args) {
        Class cls=   getPrmClass(new RegSvs(),"handle3");
        System.out.println(cls);
    }
}
