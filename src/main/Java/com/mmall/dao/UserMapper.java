package com.mmall.dao;

import com.mmall.pojo.User;
import com.mmall.pojo.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int checkUserName(String username);

    User selectLogin(@Param("username") String username,@Param("password") String password);

    int checkEmail(String email);

    String getQuestion(String username);

    int checkAnswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);

    int resetPassword(@Param("username")String username , @Param("password")String password);

    int checkPassword(@Param("password")String password,@Param("userid")int userid);

    int checkEmailByUserid(@Param("email")String email,@Param("userid")int userid);

    User getInfoById(int userid);
}