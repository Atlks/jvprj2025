package orgx.uti.qry;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

public class QueryBuilder<T> {

    private LambdaQueryWrapper<T> queryWrapper;

    public static <T> QueryBuilder<T> from(Class<T> entityClass) {
        QueryBuilder<T> queryWrapperBuilder = new QueryBuilder<>();
        queryWrapperBuilder.queryWrapper = new LambdaQueryWrapper<>();
        return queryWrapperBuilder;
    }

    //nullsafe

    /**
     * nullsafe
     * dsl qry  优化为中缀表达式
     * @param function
     * @param type
     * @param value
     * @return
     * @param <R>
     */
    public <R> QueryBuilder<T> and(SFunction<T, R> function, OpType type, R value) {
        if (value != null) {
            if (type == OpType.eq)
                queryWrapper.eq(function, value);
            if (type == OpType.like)
                queryWrapper.like(function, value);
            if (type == OpType.ge)
                queryWrapper.ge(function, value);
            if (type == OpType.le)
                queryWrapper.le(function, value);
        }

        return this;
    }


    public LambdaQueryWrapper<T> build() {
        return queryWrapper;
    }


}
