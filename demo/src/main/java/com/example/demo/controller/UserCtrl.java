package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.DTO.PageSearchDTO;
import com.example.demo.service.UserService;
import com.example.demo.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页加上搜索条件
     * @return
     */
    @PostMapping(value="/bookManager_ajax/books")
    public IPage<User> page(@RequestBody PageSearchDTO pageSearchDTO){
        Page<User> page = new Page<>(pageSearchDTO.getPageIndex(),pageSearchDTO.getPageSize());
        //pageSearchDTO.getPageIndex(),pageSearchDTO.getPageSize()
        return userMapper.selectPage(page, null);

    }

    /**
     *添加用户信息
     */
    @PostMapping("/addBook")
    public User addBook(@RequestBody User user){
        return userService.insertUser(user);
    }

    /**
     *删除用户信息
     */
    @GetMapping(value = "/delete/{id}")
    public int delete(@PathVariable("id") Integer id) {
        int count = userService.delete(id);
        return count;
    }

    /**
     *根据id查询最新的数据用来更新
     */
    @GetMapping("/bookmanager-ajax/{id}")
    public User selectById(@PathVariable("id") Integer id){
        return userMapper.selectById(id);
    }

    /**
     *根据id更新数据
     */
     @PostMapping("/update")
     public int update(@RequestBody User user){
         return userService.Update(user);
     }

    /**
     * 查询完分页
     */
    @PostMapping("/pageAndSeclectByName")
    public IPage<User> pageAndSeclectByName(@RequestBody PageSearchDTO pageSearchDTO){
        Page<User> page = new Page<>(pageSearchDTO.getPageIndex(),pageSearchDTO.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("user_name",pageSearchDTO.getUserName());
//        wrapper.orderByDesc("user_id");
//        wrapper.orderByAsc("user_id");
        return userMapper.selectPage(page, wrapper);
    }

    /**
     * 倒序排序分页
     */
     @PostMapping("/pageByDesc")
    public IPage<User> pageByDesc(@RequestBody PageSearchDTO pageSearchDTO){
         Page<User> page = new Page<>(pageSearchDTO.getPageIndex(),pageSearchDTO.getPageSize());
         QueryWrapper<User> wrapper = new QueryWrapper<>();
         wrapper.orderByDesc("user_id");
//        wrapper.orderByAsc("user_id");
         return userMapper.selectPage(page, wrapper);
     }



//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    @ResponseBody
//    public String update(User user) {
//        int result = userService.Update(user);
//        if (result >= 1) {
//            return "修改成功";
//        } else {
//            return "修改失败";
//        }
//
//    }

//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public User insert(User user) {
//        return userService.insertUser(user);
//    }
//
//    @RequestMapping("/ListUser")
//    @ResponseBody
//    public List<User> ListUser() {
//        return userService.ListUser();
//    }
//
//    @RequestMapping("/ListByName")
//    @ResponseBody
//    public List<User> ListUserByName(String userName) {
//        return userService.findByName(userName);
//    }


//    //测试
//    @RequestMapping("/test")
//    public User test(){
//        return userMapper.selectById(50);
//    }

//    /**
//     * rows
//     * @return
//     */
//    @RequestMapping(value="/rows")
//    @ResponseBody
//    public int rows(){
//        return userService.getRowCount();
//    }
}
