package com.qrcode.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qrcode.springboot.common.Constants;
import com.qrcode.springboot.common.Result;
import com.qrcode.springboot.controller.dto.UserDTO;
import com.qrcode.springboot.entity.User;
import com.qrcode.springboot.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
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
        public Result login(@RequestBody UserDTO userDTO){
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();
            if(StrUtil.isBlank(username) || StrUtil.isBlank(password))
            {
                return Result.error(Constants.CODE_400,"参数错误");
            }
            UserDTO dto = userService.login(userDTO);
            return Result.success(dto) ;
        }
        @PostMapping("/register")
        public Result register(@RequestBody UserDTO userDTO){
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();
            if(StrUtil.isBlank(username) || StrUtil.isBlank(password))
            {
                return Result.error(Constants.CODE_400,"参数错误");
            }

            return Result.success(userService.register(userDTO));
        }

        //查询
        @PostMapping
        public Result save(@RequestBody User user){
            return Result.success(userService.saveOrUpdate(user));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
            return Result.success(userService.removeById(id));
        }

        @PostMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids) {
            return Result.success(userService.removeByIds(ids));
        }

        @GetMapping
        public Result findAll() {
            return Result.success(userService.list());
        }

        @GetMapping("/{id}")
        public Result findOne(@PathVariable Integer id) {
            return Result.success(userService.getById(id));
        }

        @GetMapping("/username/{username}")
        public Result findOne(@PathVariable String username) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            return Result.success(userService.getOne(queryWrapper));
        }

        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
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
            return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));

        }
//
//    导出接口
//
        @GetMapping("/export")
        public void export(HttpServletResponse response) throws Exception{
            List<User> list = userService.list();
            ExcelWriter writer = ExcelUtil.getWriter(true);
            //自定义别名
//            writer.addHeaderAlias("username","用户名");
//            writer.addHeaderAlias("password","密码");
//            writer.addHeaderAlias("nickname","昵称");
//            writer.addHeaderAlias("email","邮箱");
//            writer.addHeaderAlias("phone","电话");
//            writer.addHeaderAlias("address","地址");
//            writer.addHeaderAlias("createTime","创建时间");
//            writer.addHeaderAlias("avatarUrl","头像");
            //一次性写出所有list内的对象，使用默认样式
            writer.write(list,true);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            String fileName = URLEncoder.encode("用户信息","UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

            ServletOutputStream out = response.getOutputStream();
            writer.flush(out,true);
            out.close();
            writer.close();


        }
//        导入
        @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
            InputStream inputStream = file.getInputStream();
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<User> list = reader.readAll(User.class);
            userService.saveBatch(list);
            return Result.success(true);
        }
}

