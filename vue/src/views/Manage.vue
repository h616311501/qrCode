<template>
  <div style="height: 100%">
    <el-container style="min-height: 100vh">
          <el-aside style="box-shadow: 0px 0 1px rgb(0 0 0);width: 200px">
                <Aside :isCollapse="isCollapse" :logoTextshow="logoTextshow" />
          </el-aside>
    <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
             <Header :collapseBtnClass="collapseBtnClass" :collapse="isCollapse" />
        </el-header>
        <el-main>
<!--          表示当前页面的子路由在router里展示-->
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

  import Aside from "@/components/Aside";
  import Header from "@/components/Header";

export default {
    name: 'Home',
  data() {
    return {
      collapseBtnClass : 'el-icon-s-fold',
      isCollapse:false,
      logoTextshow:true,
    }
  },
  components:{
    Aside,
    Header
  },

  methods:{
    collapse(){ //点击按钮触发
      this.isCollapse=!this.isCollapse
      if (this.isCollapse){  //收起
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextshow = false
      }else{  //展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextshow = true
      }
     }
  }
}
</script>



