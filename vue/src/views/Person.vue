<template>
    <el-card style="width: 500px;padding: 20px;">
        <el-form labal-width="80px" size="small">
            <el-form-item label="아이디" >
                <el-input v-model="form.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="이름" >
                <el-input v-model="form.nickname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="우편함" >
                <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="전화번호" >
                <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="주소" >
                <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                    <el-button type="primary" @click="save">확 인</el-button>

            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    export default {
        name: "Person",
        data(){
            return{
                form:{},
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
            }

        },
        created() {
            this.request.get("/user/username/"+ this.user.username ).then(res =>{
                if(res.code === '200'){
                    this.form = res.data
                }

            })
        },
        methods:{
            save(){
                this.request.post("/user", this.form).then(res => {
                    if(res.data){
                        this.$message.success("저장 성공")//保存成功
                    } else {
                        this.$message.error("저장 실패")//保存失败
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>
