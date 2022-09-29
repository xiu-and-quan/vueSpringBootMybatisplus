package com.example.demo.pojo.DTO;

import com.example.demo.entity.User;
import com.example.demo.entity.UserSub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDoubleDTO {
    private String userName;
    private String userDate;
    private String userAddress;
    private List<UserSub> link;
}
