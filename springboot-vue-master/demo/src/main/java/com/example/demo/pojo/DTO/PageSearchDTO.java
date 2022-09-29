package com.example.demo.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSearchDTO {

    private Integer pageIndex;

    private Integer pageSize;

    private String  userName;
}
