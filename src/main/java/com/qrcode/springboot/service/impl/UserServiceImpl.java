package com.qrcode.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qrcode.springboot.common.Constants;
import com.qrcode.springboot.controller.dto.UserDTO;
import com.qrcode.springboot.entity.User;
import com.qrcode.springboot.exception.ServerException;
import com.qrcode.springboot.mapper.UserMapper;
import com.qrcode.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.math3.analysis.function.Log;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuTW
 * @since 2022-04-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserDTO login(UserDTO userDTO) {
            User one =getUserInfo(userDTO);
            if (one != null) {
                BeanUtil.copyProperties(one,userDTO, true);
                return userDTO;
            }else {
                throw new ServerException(Constants.CODE_600,"用户名或密码错误");
            }

        }

    @Override
    public User register(UserDTO userDTO) {
        User one =getUserInfo(userDTO);
        if(one == null)
        {
            one = new User();
            BeanUtil.copyProperties(userDTO,one, true);
            save(one);
        }
        else {
            throw new ServerException(Constants.CODE_600,"用户已存在");
        }


        return null;
    }
    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); //数据库查询
        }
        catch (Exception e) {
            throw new ServerException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
