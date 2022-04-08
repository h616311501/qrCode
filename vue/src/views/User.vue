<template>
    <div>
        <div style="margin-bottom: 30px">

        </div>
        <div style="margin: 10px 0">
<!--            请输入名称-->
            <el-input style="width: 200px " suffix-icon="el-icon-search" placeholder="이름을 입력하세요" v-model="username"></el-input>

            <el-input style="width: 200px " suffix-icon="el-icon-message" placeholder="우편함을 입력하세요" class="ml-5" v-model="email"></el-input>

            <el-input style="width: 200px " suffix-icon="el-icon-position" placeholder="주소를 입력하세요" class="ml-5" v-model="address"></el-input>
                <!--            搜索-->
            <el-button class="ml-5" type="primary" round @click="load">Search</el-button>
                <!--            重置-->
            <el-button type="warning" round @click="reset">Reset</el-button>
        </div>

        <div style="margin: 10px 0">
<!--            添加-->
            <el-button type="primary" round @click="handleAdd">Add<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='Yes'
                    cancel-button-text='No'
                    icon="el-icon-info"
                    icon-color="red"
                    title="확인？"
                    @contextmenu="delBatch"
            >
                <el-button type="danger" round slot="reference">대량삭제<i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
<!--            //导入与导出-->
            <el-upload action="http://localhost:9091/user/import" style="display: inline-block" :show-file-list="false" accept="xlsx" on-success="handleExcelImportSuccess">
            <el-button type="primary" round class="ml-5">Import<i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" round class="ml-5" @click="exp">Export<i class="el-icon-top"></i></el-button>
        </div>


        <el-table :data="tableData" border stripe header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">>
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="140"></el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="phone" label="电话"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除吗？"
                            @contextmenu="handleDelete(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[2, 5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
<!--        User Info-->
        <el-dialog title="User Info" :visible.sync="dialogFormVisible" width="30%">
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

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">취 소</el-button>
                <el-button type="primary" @click="save">확 인</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "User",
        data(){
            return {
                tableData: [],
                total:0,
                pageNum:1,
                pageSize:10,
                username: "",
                email: "",
                address: "",
                form: {},
                dialogFormVisible: false,
                multipleSelection: []
            }
        },
        created() {
            this.load()
        },
        methods:{
            load(){
                this.request.get("/user/page", {
                    params:{
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        username: this.username,
                        email: this.email,
                        address: this.address,
                    }
                }).then(res =>{
                    console.log(res.data)
                    this.tableData = res.data.records
                    this.total = res.data.total
                })
            },
                save(){
                    this.request.post("/user", this.form).then(res => {
                        if(res.data){
                            this.$message.success("저장 성공")//保存成功
                            this.dialogFormVisible = false
                            this.load()
                        } else {
                            this.$message.error("저장 실패")//保存失败
                        }
                    })
                },
            handleAdd(){
                this.dialogFormVisible = true
                this.form = {}
            },
            handleEdit(row){
                this.form = row
                this.dialogFormVisible = true
            },
            handleDelete(id){
                this.request.delete("/user/"+ id).then(res => {
                    if(res.data){
                        this.$message.success("삭제 성공")//删除成功
                        this.load()
                    } else {
                        this.$message.error("삭제 실패")//删除失败
                    }
                })
            },
            handleSelectionChange(val){
                console.log(val)
                this.multipleSelection = val
            },
            delBatch(){
                let ids = this.multipleSelection.map(v => v.id)
                this.request.post("/user/del/batch", ids).then(res => {
                    if(res.data){
                        this.$message.success("대량삭제 성공")//批量删除成功
                        this.load()
                    } else {
                        this.$message.error("대량삭제 실패")//批量删除失败
                    }
                })
            },
            reset(){
                this.username = ""
                this.email = ""
                this.address = ""
                this.load()
            },
            handleSizeChange(pageSize){
                this.pageSize = pageSize
                this.load()
            },
            handleCurrentChange(pageNum){
                this.pageNum = pageNum
                this.load()
            },
            exp(){
                window.open("hhtp://localhost:9091/user/export")
            },
            handleExcelImportSuccess(){
                this.$message.success("Import 성공")//导入成功
                this.load()
            }

        }
    }
</script>

<style>
    .headerBg{
        background-color: lightyellow !important;
    }
</style>
