package cn.zyjblogs.mmallbe.mapper;

import cn.zyjblogs.mmallbe.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * Mybatis数据库操作
 */
@Mapper
public interface UserMapper {
    @Select("select * from t_user where username=#{username} and password=#{password}")
     User login(User user);
    @Insert("insert t_user(username,password) values(#{username},#{password})")
    int addUser(User user);
    @Select("select id from t_user where username=#{username}")
    User findUserById(User user);
}

