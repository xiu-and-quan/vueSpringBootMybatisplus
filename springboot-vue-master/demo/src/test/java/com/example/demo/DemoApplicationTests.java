package com.example.demo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.controller.UserCtrl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private UserCtrl userCtrl;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void pageTest() {
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page,null);
        System.out.println("分页查询结果"+page.getRecords());
        System.out.println("分页查询数据量"+page.getTotal());
        System.out.println("分页查询页码数"+page.getPages());
    }

}
