package util.wkflow;

import util.algo.RunnableThrowing;


/**
 * ConditionAction<value> case1 = new ConditionAction<>(
 *     v -> v.getCode().equals("ABC"),
 *     () -> System.out.println("Match ABC")
 * );
 */
public class ConditionBranch {
    public String key;

    /**
     * 若你将来希望不止比对 value，也支持任意条件判断，则建议用函数式接口 Predicate<T>：8
     */
    public Object valueToMatch;
    public RunnableThrowing act;
    public ConditionBranch(Object valueToMatch88, RunnableThrowing runnablex1
    ) {
        this.valueToMatch =valueToMatch88;
        this.act=runnablex1;
    }




    public ConditionBranch() {

    }
}
