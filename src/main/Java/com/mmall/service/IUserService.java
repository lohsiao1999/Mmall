package com.mmall.service;

import com.mmall.common.ServiceResopnse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    ServiceResopnse<User> login(String username, String password);

    ServiceResopnse<String> register(User user);

    ServiceResopnse<String> checkVaild(String string ,String type);

    ServiceResopnse<String> getQuestion(String username);

    ServiceResopnse<String> checkAnswer(String username ,String question , String answer);

    ServiceResopnse<String> resetPassword(String username,String newPassword,String token);

    ServiceResopnse<String> onlineResetPassword(String oldpassword,String newPassword,User user);

    ServiceResopnse<User> updateInformation(User user);

    ServiceResopnse<User> getUserInfo(int userid);

    ServiceResopnse<String> checkAdmin(User user);

}
