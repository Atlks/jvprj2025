package orgx.u;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 继承 BaseMapper 后，MyBatis-Plus 自动生成 insert()、selectById()、updateById()、deleteById() ✅ 无需写 SQL，直接调用方法
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
