package orgx;

import jakarta.persistence.PostPersist;
import orgx.u.User;

// 实体监听器类
public class UserEntityListener {

    @PostPersist  // 插入后触发
    public void afterUserSaved(User user) {
        System.out.println("用户保存后执行: " + user.getId());

        // 示例：更新统计数据
        updateUserStatistics(user);
    }

    private void updateUserStatistics(User user) {
        // 更新用户统计数据（如总用户数、活跃用户等）
        System.out.println("<UNK>: " + user.getId());
    }


}