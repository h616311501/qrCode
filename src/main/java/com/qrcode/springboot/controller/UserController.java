package com.qrcode.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qrcode.springboot.controller.dto.UserDTO;
import com.qrcode.springboot.entity.User;
import com.qrcode.springboot.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Struct;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiuTW
 * @since 2022-04-04
 */
@RestController
@Controller
@RequestMapping("/user")
public class UserController {

        @Resource
        private IUserService userService;
        //login
        @PostMapping("/login")
        public boolean login(@RequestBody UserDTO userDTO){
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();
            if(StrUtil.isBlank(username) || StrUtil.isBlank(password))
            {
                return false;
            }
            return userService.login(userDTO); }
        //查询
        @PostMapping
        public boolean save(@RequestBody User user){ return userService.saveOrUpdate(user); }

        @DeleteMapping("/{id}")
        public Boolean delete(@PathVariable Integer id) {
            return userService.removeById(id);
        }

        @PostMapping("/del/batch")
        public boolean deleteBatch(@RequestBody List<Integer> ids) {
            return userService.removeByIds(ids);
        }

        @GetMapping
        public List<User> findAll() {
            return userService.list();
        }

        @GetMapping("/{id}")
        public User findOne(@PathVariable Integer id) {
            return userService.getById(id);
        }

        @GetMapping("/page")
        public Page<User> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String username,
                                   @RequestParam(defaultValue = "") String email,
                                   @RequestParam(defaultValue = "") String address) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if(!"".equals(username)){
                queryWrapper.like("username", username);
            }
            if(!"".equals(email)){
                queryWrapper.like("email", email);
            }
            if(!"".equals(address)){
                queryWrapper.like("address", address);
            }
            queryWrapper.orderByDesc("id");
            //queryWrapper.like("address", address);在上面添加完后在这里添加like即可
            return userService.page(new Page<>(pageNum, pageSize), queryWrapper);

        }

}

