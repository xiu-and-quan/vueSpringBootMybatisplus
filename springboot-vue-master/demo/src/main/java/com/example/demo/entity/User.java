package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer userId;
    @TableId(value = "user_date")
    private String userDate;
    @TableId(value = "user_name")
    private String userName;
    @TableId(value = "user_address")
    private String userAddress;
    @TableId(value = "user_course")
    private String userCourse;
}
