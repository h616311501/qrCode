package com.qrcode.springboot.controller.dto;

import lombok.Data;

//接受参数

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
}
