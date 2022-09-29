package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import com.example.demo.entity.UserSub;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserSubMapper;
import com.example.demo.pojo.DTO.UserDoubleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSubMapper userSubMapper;

    public List<User> findByName(String userName) {
        return userMapper.findUserByName(userName);
    }

    public List<User> queryPage(Integer startRows) {
        return userMapper.queryPage(startRows);
    }

    public int getRowCount() {
        return userMapper.getRowCount();
    }

    @Transactional(rollbackFor = {Exception.class})
    public User insertUser(UserDoubleDTO userDoubleDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDoubleDTO,user);
        System.out.println(user);
        if (userDoubleDTO.getLink()==null){
            userMapper.insertUser(user);
        }
        else {
            List<UserSub> userSub = userDoubleDTO.getLink();
            userMapper.insertUser(user);
//            userSub.forEach(userSub1 -> {
//                userSubMapper.insert(userSub1);
//            });
            for (UserSub u :
                    userSub) {
                u.setUserName(userDoubleDTO.getUserName());
                userSubMapper.myInsert(u);
            }
        }
        return user;
    }

    public List<User> ListUser(){
        return userMapper.ListUser();
    }

    public int Update(User user){
        return userMapper.Update(user);
    }

    public int delete(int id){
        return userMapper.delete(id);
    }

}