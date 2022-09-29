package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_sub")
public class UserSub {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableId(value = "user_name")
    private String userName;

    @TableId(value = "user_native_place")
    private String nativePlace;

    @TableId(value = "user_tel")
    private String tel;
}
