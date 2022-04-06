package com.qrcode.springboot.service;

import com.qrcode.springboot.controller.dto.UserDTO;
import com.qrcode.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuTW
 * @since 2022-04-04
 */
public interface IUserService extends IService<User> {

    boolean login(UserDTO userDTO);
}
