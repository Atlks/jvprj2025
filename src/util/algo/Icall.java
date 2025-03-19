package util.algo;


/**  aop itfs....for webapi,svs
 * 如果 doAct() 主要负责业务逻辑，并且每个操作对应一个类，这样的设计方式确实可以提高代码的可维护性和可读性，类似于 命令模式（Command Pattern） 或 策略模式（Strategy Pattern）。
 * @param <P>
 */
public interface Icall<P, R> {

    public R main(P arg) throws Throwable;

    default R main2(P arg) throws Throwable  {
       throw   new RuntimeException("not eimplt");
    }
    default R call3(P arg) throws Throwable  {
        throw   new RuntimeException("not eimplt");
    }
}
