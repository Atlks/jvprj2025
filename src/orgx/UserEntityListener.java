package orgx;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import orgx.u.User;

// 实体监听器类
public class UserEntityListener {

    /**
     * 可以做reg evt,不可以chrge evt
     *
     * 这个方法 只会在 INSERT 操作后执行，不会在 UPDATE 时触发
     * @PostPersist 专门用于 新记录插入（INSERT）。
     *
     * 如果要监听 UPDATE 操作，应该使用 @PostUpdate：
     * @param user
     */
    @PostPersist  // 插入后触发
    public void afterUserSaved(User user) {
        System.out.println("fun afterUserSaved");
        System.out.println("用户保存后执行: " + user.getId());

        // 示例：更新统计数据
        updateUserStatistics(user);
        System.out.println("endfun afterUserSaved");
    }
    @PostLoad
    @PostConstruct

    @PostUpdate  // 更新后触发
    public void afterUserUpdated(User user) {
        System.out.println("用户更新后执行: " + user.getId());
    }

    private void updateUserStatistics(User user) {
        // 更新用户统计数据（如总用户数、活跃用户等）
        System.out.println("<UNK>: " + user.getId());
    }


}