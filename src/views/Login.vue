<template>
  <div class="login-container">
    <div class="login-box">
        <el-form :rules="data.rules" ref="formRef" :model="data.form" style="padding:40px; border:1px solid #cccccc; width:350px; background-color: skyblue">
          <div style="font-size: 40px; text-align:center; margin-bottom: 30px;">后台管理系统</div>
          <el-form-item prop="username">
            <el-input size="large" v-model="data.form.username" :prefix-icon="User" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password size="large" v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="data.form.role" style="width:100%" size="large">
              <el-option value="MAN" label="管理员"></el-option>
              <el-option value="EMP" label="员工"></el-option>
            </el-select>
          </el-form-item>
          <div>
            <el-button @click="login" size="large" type="primary" style="width:100%">登   录</el-button>
          </div>
          <div style="float:right;margin-top:20px">您还没有账号，请<a href="/register">注册</a></div>
        </el-form>
    </div>
  </div>
</template>

<script setup>
  import {reactive, ref} from 'vue';
  import {Lock, User} from "@element-plus/icons-vue";
  import request from "@/utils/request.js";
  import {ElMessage} from "element-plus";
  import {useRouter} from 'vue-router';
  // 数据
  const data = reactive({
    form:{role:"MAN"},     // role设置默认值
    rules:{
      username:[
          {required:true, message:'请输入账号！！！', trigger:'blur',},
          {required:true, message:'输入账号过短', trigger:'blur', min:3},
          {required:true, message:'输入账号过长', trigger:'blur', max:5},
      ],
      password:[
          {required:true, message:'请输入密码！！！', trigger:'blur'},
      ],
    }
  })
  const formRef = ref();      // 获取到表单对象
  const router = useRouter();
  // 方法
  // 表单校验
  const login = () => {
    formRef.value.validate(async(valid) => {
      if (valid){
        // 发送请求验证用户是否存在
        const ret = await request.post('/login', data.form);
        if (ret.code === '200'){
          localStorage.setItem('xm-pro-user', JSON.stringify(ret.data));    // 将用户信息存储到用户本地内存中
          //console.log(ret.data);
          ElMessage.success('登录成功！！！');
          // 模拟等待时间
          setTimeout(() => {
            //location.href='/layout';
            router.push("/layout");
          }, 3000);
          // 存储后台返回的用户信息
        } else {
          ElMessage.error(ret.msg);
        }
      }
    })
  }

</script>

<style scoped>
  .login-container {
    height: 100vh;
    width: 200vh;
    background-image: url("@/assets/bg3.png");
    /*background-image: url("@/assets/girl4.jpg");*/
    background-size: cover;
    overflow: hidden;
    /*background-position-x: -250px;*/
  }
  .login-box{
    position: absolute;
    display: flex;
    align-items: center;
    right: 0;
    /*background-color: white;*/
    width: 38%;
    height: 80%;
  }
</style>