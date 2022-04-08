<template>
    <div class="wrapper">
        <div style="margin: 100px auto;background-color: #FFF ;width: 350px;height: 400px;padding: 20px;border-radius: 10px">
            <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>로그인</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input placeholder="请确认密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
                </el-form-item>
            <div style="margin: 10px 0; text-align: right">
<!--                登录-->
                <el-button type="primary" size="small" autocapitalize="off" @click="login">로그인</el-button>
<!--                注册-->
                <el-button type="warning" size="small" autocapitalize="off" @click="$router.push('/register')">회원가입</el-button>
            </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data(){
            return {
                user: {},
                rules: {
                    username: [
                        {required: true, message: '길이는 1~10문자이다.', trigger: 'blur'}, //请输入用户名
                        {min: 1, max: 10, message: '길이는 1~10문자이다.', trigger: 'blur'}//长度在3-5个字符
                    ],
                    password: [
                        {required: true, message: '길이는 1~20문자이다.', trigger: 'blur'},
                        {min: 1, max: 20, message: '길이는 1~20문자이다.', trigger: 'blur'}
                    ],
                    confirmPassword: [
                        {required: true, message: '길이는 1~20문자이다.', trigger: 'blur'},
                        {min: 1, max: 20, message: '길이는 1~20문자이다.', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            login() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) { //合法
                        if(this.user.password !== this.user.confirmPassword){
                            this.$message.error("不一致")
                            return false
                        }
                        this.request.post("/user/register", this.user).then(res =>{
                            if(res.code === '200'){
                                this.$message.error("注册成功") //登录成功

                            }else{
                                this.$message.error(res.msg) //用户名或者密码错误
                            }
                        })
                    }
                });
            }
        }
    }
</script>

<style>
    .wrapper{
        height: 100vh;
        background-image: linear-gradient(to bottom right, #0F2027 ,#203A43  , #2C5364);
        overflow: hidden;
    }

</style>
