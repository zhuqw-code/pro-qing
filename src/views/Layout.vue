<template>
  <div>
    <!--<h1>Layout!!!</h1>-->
    <!--  头部导航栏  -->
    <div style="height:60px; background-color: #748596; display: flex; align-items: center">
      <div style="width:200px; display: flex; color: white; margin-left:20px">
        <img src="@/assets/bg2.png" width="50">
        <span style="margin-left:10px;margin-top:15px">Code管理系统</span>
      </div>
      <div style="flex: 1"></div>
      <div style="width:150px; display: flex; align-items: center"></div>
        <span style="margin-right:40px">{{data.user.role === 'EMP' ? '角色：员工' : '角色：管理员'}}</span>
        <img :src="data.user.avatar" style="width:50px"></img>
        <span style="margin:10px">{{data.user.name}}</span>
        <!--
            TODO: Layout.vue: Uncaught (in promise) TypeError: Cannot read properties of null (reading 'name') at Proxy._sfc_render (Layout.vue:13:92)
              在浏览器还没有缓存的时候直接无密码访问layout页面就会导致一些数据还未加载而报错
              1.解决方法就是先登录，后访问layout页面【修改路由信息】
         -->
      <!-- 三个flex均分主导航栏 -->
    </div>
    <!--  下部内容 -->
    <div style="display:flex">
      <!--  侧边栏  -->
      <div style="width:200px; border-right:1px solid #cccccc; background-color: #545c64; min-height: calc(100vh - 60px)">
        <el-menu
            active-text-color="#ffd04b"
            background-color="#545c64"
            text-color="#fff"
            router
            :default-active="router.currentRoute.value.path"
            :default-openeds="['1']"
        >
          <!--为了解决刷新高量没了，我们添加default-active来设置-->
          <!--如何设置默认展开 :default-openeds="['x','x']"，传入需要展开的数组-->
          <el-menu-item index="/layout/data">
            <el-icon><Location/></el-icon>
            <!--<span @click="router.push('/layout/data')">数据汇总</span>-->
            <span>数据汇总</span>
          </el-menu-item>
          <el-menu-item index="/layout/employee">
            <el-icon><Management /></el-icon>
            <!--<span @click="router.push('/layout/employee')">员工管理</span>-->
            <span>员工管理</span>
          </el-menu-item>
          <el-menu-item index="/layout/admin">
            <el-icon><Connection /></el-icon>
            <span>管理员信息</span>
          </el-menu-item>
          <el-menu-item index="/layout/server">
            <el-icon><Connection /></el-icon>
            <!--<span @click="router.push('/layout/server')">售后服务</span>-->
            <span>售后服务</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <!--每个el-sub-menu都会有一个分组介绍-->
            <template #title>
              <el-icon><user /></el-icon>
              <!--<span @click="router.push()">个人中心</span>-->
              <span>个人中心</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/layout/person">
                <el-icon><UserFilled/></el-icon>
                <!--<span @click="router.push()">用户详情</span>-->
                <span>用户详情</span>
              </el-menu-item>
              <el-menu-item index="/layout/password">
                <el-icon><EditPen/></el-icon>
                <!--<span @click="router.push('/layout/data')">修改信息</span>-->
                <span>修改密码</span>
              </el-menu-item>
              <!--<el-menu-item index="/login"> 使用点击事件来执行更多操作包括清除本地缓存和跳转页面-->
              <el-menu-item @click="logout">
                <el-icon><Promotion/></el-icon>
                <!--<span @click="router.push('/layout/data')">退出登录</span>-->
                <span>退出登录</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </div>
      <!--  主要内容区  -->
      <div style="flex:1; width:0; background-color: #ffffff; padding-left:10px">
        <!--  flex:1将剩余部分全部占据-->
        <router-view @updUser="updateFatherUser"/>
      </div>
    </div>
  </div>
</template>

<script setup>
  import router from '@/router/index.js'
  import {EditPen, Location, Promotion, User, UserFilled} from "@element-plus/icons-vue";
  import {reactive} from 'vue'
  import girl from '../assets/girl8.jpg'
  // 数据
  const data = reactive({
    user: JSON.parse(localStorage.getItem('xm-pro-user')),
    //imgPath: girl,
    imgPath: "",
    username: '',
  })

  // 方法
  const logout = () => {
    localStorage.removeItem('xm-pro-user');    //清除用户缓存数据
    location.href = '/login';
  }

  // 子级发射的导弹需要父级来接收，并可以让父级做一些事情
  const updateFatherUser = () => {
    // 获取本地最新数据
    data.user = JSON.parse(localStorage.getItem('xm-pro-user'));
  }
</script>

<style scoped>

</style>

<!--
    问题引入：
      1.如何让用户和管理员公用同一个登录页面？
        ？不同用户需要访问不同数据库，》同一个登陆页面根据role+判断条件

      2.登录需要记录用户信息，退出需要清除用户信息
-->