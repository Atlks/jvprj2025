package util.wkflow;

import java.time.Duration;

public class RetryPolicy {
    /** 最大重试次数 */
    private int maxRetries;

    /** 每次重试之间的延时 */
    private Duration delay;

    /** 是否启用指数退避 */
    private boolean exponentialBackoff;

    // 构造方法、Getter、Setter 省略
}
