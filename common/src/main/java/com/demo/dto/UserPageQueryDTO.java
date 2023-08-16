package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class UserPageQueryDTO implements Serializable {

    //手机号码
    private String phone;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
