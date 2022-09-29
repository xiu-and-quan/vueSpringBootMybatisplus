package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.User;
import com.example.demo.entity.UserSub;
import com.example.demo.pojo.DTO.PageSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSubMapper extends BaseMapper<UserSub> {
    void myInsert(UserSub u);
}
