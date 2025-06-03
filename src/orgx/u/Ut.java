package orgx.u;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;

import static orgx.CfgSvs.buildSessionFactory;

public class Ut {
    public static void main(String[] args) throws Exception {

       // System.out.println(org.mybatis.logging.LoggerFactory.);
        // 1. 读取 MyBatis 配置

        SqlSessionFactory sqlSessionFactory =buildSessionFactory();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // 2. 插入数据
            User newUser = new User(81, "Alice", 25);
            userMapper.insert(newUser);
            System.out.println("插入用户: " + newUser);

            // 3. 查询用户
            QueryWrapper<User> query = Wrappers.query();
            query.eq("name", "Alice");
            User foundUser = userMapper.selectOne(query);
            System.out.println("查询用户: " + foundUser);

            // 4. 更新用户
//            foundUser.setAge(30);
//            userMapper.updateById(foundUser);
//            System.out.println("更新用户: " + foundUser);
//
//            // 5. 删除用户
//            userMapper.deleteById(foundUser.getId());
//            System.out.println("用户已删除: " + foundUser.getId());

            session.commit(); // 提交事务
        }
    }
}
