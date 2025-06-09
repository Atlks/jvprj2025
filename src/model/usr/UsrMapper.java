package model.usr;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsrMapper extends BaseMapper<Usr> {
    public static void main(String[] args) {
        System.out.println(Usr.class);
    }
    // 可以添加自定义方法，但大多数常用操作 BaseMapper 已提供
}

