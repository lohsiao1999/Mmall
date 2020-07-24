package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServiceResopnse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.pojo.UserExample;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServiceResopnse<User> login(String username, String password) {
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0){
            return ServiceResopnse.createByErrorMessage("用户名不存在");
        }


        User user = userMapper.selectLogin(username,MD5Util.md5Encrypt32Upper(password));
        if (user == null){
            return ServiceResopnse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServiceResopnse.createBySuccess("登录成功",user);
    }

    @Override
    public ServiceResopnse<String> register(User user) {
        ServiceResopnse<String> vaild = this.checkVaild(user.getUsername(),Const.USER_NAME);
        if (!vaild.isSuccess()) {
            return vaild;
        }
        vaild = this.checkVaild(user.getEmail(),Const.EMAIL);
        if (!vaild.isSuccess()) {
            return vaild;
        }
        user.setRole(Const.role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.md5Encrypt32Upper(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServiceResopnse.createByErrorMessage("注册失败");
        }
        return ServiceResopnse.createBySuccessMessage("注册成功");
    }

    public ServiceResopnse<String> checkVaild(String string ,String type){
        //此处isNotBlank会将空格默认为空值
        if (StringUtils.isNotBlank(type)) {
            if (Const.USER_NAME.equals(type)){
                int resultCount = userMapper.checkUserName(string);
                if (resultCount > 0){
                    return ServiceResopnse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(string);
                if (resultCount > 0){
                    return ServiceResopnse.createByErrorMessage("邮箱已存在");
                }
            }
        }else {
            return ServiceResopnse.createByErrorMessage("参数错误");
        }
        return ServiceResopnse.createBySuccessMessage("检验成功");
    }

    public ServiceResopnse<String> getQuestion(String username){
        ServiceResopnse<String> vaild = this.checkVaild(username,Const.USER_NAME);
        if (vaild.isSuccess()){
            return ServiceResopnse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.getQuestion(username);
        if (StringUtils.isNotBlank(question)) {
            return ServiceResopnse.createBySuccess(question);
        }
        return ServiceResopnse.createByErrorMessage("问题不存在");
    }

    public ServiceResopnse<String> checkAnswer(String username ,String question , String answer){
        int result = userMapper.checkAnswer(username, question, answer);
        if (result > 0) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServiceResopnse.createBySuccess(forgetToken);
        }
        return ServiceResopnse.createByErrorMessage("问题答案错误");
    }

    public ServiceResopnse<String> resetPassword(String username,String newPassword,String token){
        if (StringUtils.isBlank(token)) {
            return ServiceResopnse.createByErrorMessage("参数不能为空");
        }
        ServiceResopnse<String> vaild = this.checkVaild(username,Const.USER_NAME);
        if (vaild.isSuccess()){
            return ServiceResopnse.createByErrorMessage("用户名不存在");
        }
        String forgettoken = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if (StringUtils.isBlank(forgettoken)){
            return ServiceResopnse.createByErrorMessage("token 过期");
        }
        if (StringUtils.equals(forgettoken,token)){
            String newp = MD5Util.md5Encrypt32Upper(newPassword);
            int countrow = userMapper.resetPassword(username,newp);
            if (countrow > 0) {
                return ServiceResopnse.createBySuccessMessage("修改密码成功");
            }
        }else {
            return ServiceResopnse.createByErrorMessage("token错误");
        }
        return ServiceResopnse.createByErrorMessage("修改密码失败");
    }

    public ServiceResopnse<String> onlineResetPassword(String oldpassword,String newPassword,User user){
        String old = MD5Util.md5Encrypt32Upper(oldpassword);
       int count = userMapper.checkPassword(old,user.getId());
       if (count == 0) {
           return ServiceResopnse.createByErrorMessage("旧密码错误");
       }
       count = userMapper.resetPassword(user.getUsername(),MD5Util.md5Encrypt32Upper(newPassword));
       if (count > 0) {
           return ServiceResopnse.createBySuccessMessage("修改密码成功");
       }
        return ServiceResopnse.createByErrorMessage("修改密码失败");
    }

    public ServiceResopnse<User> updateInformation(User user){
        int count = userMapper.checkEmailByUserid(user.getEmail(),user.getId());
        if (count > 0){
            return ServiceResopnse.createByErrorMessage("邮箱已存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getPhone());
        updateUser.setAnswer(user.getAnswer());

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIdEqualTo(updateUser.getId());
        count = userMapper.updateByExampleSelective(updateUser,userExample);
        if (count > 0) {
            return ServiceResopnse.createBySuccess("修改个人信息成功",updateUser);
        }
        return ServiceResopnse.createByErrorMessage("修改个人信息失败");
    }

    public ServiceResopnse<User> getUserInfo(int userid){
        User user = userMapper.getInfoById(userid);
        if (user == null) {
            return ServiceResopnse.createByErrorMessage("个人信息不存在");
        }
        return ServiceResopnse.createBySuccess(user);
    }

    public ServiceResopnse<String> checkAdmin(User user){
        if (user != null || user.getRole().intValue() == Const.role.ROLE_ADMIN) {
            return ServiceResopnse.createBySuccess();
        }
        return ServiceResopnse.createByError();
    }
}
