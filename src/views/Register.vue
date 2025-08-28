<template>
  <div class="login-container">
    <div class="login-box">
        <el-form :rules="data.rules" ref="formRef" :model="data.form" style="padding:40px; border:1px solid #cccccc; width:350px; background-color: skyblue">
          <div style="font-size: 40px; text-align:center; margin-bottom: 30px;">注册页面</div>
          <el-form-item prop="no">
            <el-input size="large" v-model="data.form.no" :prefix-icon="Edit" placeholder="请输入工号"></el-input>
          </el-form-item>
          <el-form-item prop="username">
            <el-input size="large" v-model="data.form.username" :prefix-icon="User" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password size="large" v-model="data.form.password" :prefix-icon="Lock" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input show-password size="large" v-model="data.form.confirmPassword" :prefix-icon="Lock" placeholder="请再次输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="name">
            <el-input size="large" v-model="data.form.name" :prefix-icon="Lock" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="age">
            <el-input-number size="large" v-model="data.form.age" :prefix-icon="Lock" placeholder="请输入年龄"></el-input-number>
          </el-form-item>
          <el-form-item prop="sex">
            <el-radio-group v-model="data.form.sex">
              <el-radio label="男" value="男"></el-radio>
              <el-radio label="女" value="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <div>
            <el-button @click="register" size="large" type="primary" style="width:100%">注 册</el-button>
          </div>
          <div style="float:right;margin-top:20px">已有账号，返回<a href="/login">登录</a>页面</div>
        </el-form>
    </div>
  </div>
</template>

<script setup>
  import {reactive, ref} from 'vue';
  import {Edit, Lock, User} from "@element-plus/icons-vue";
  import request from "@/utils/request.js";
  import {ElMessage} from "element-plus";
  import {useRouter} from 'vue-router';
  // 确认密码逻辑
  const validatePass = (rule, value, callback) => {
    if (!value) {
      callback(new Error('请再次输入密码！！！'));  // 抛出异常就是有错误
    } else if (value !== data.form.password) {   // 抛出异常就是有错误
      callback(new Error("两次密码不一致！！！"));
    } else {
      callback();   // 没有抛异常就是成功的逻辑
    }
  }
  // 数据
  const data = reactive({
    form:{},
    rules:{
      username:[
          {required:true, message:'请输入账号！！！', trigger:'blur',},
          {required:true, message:'输入账号过短', trigger:'blur', min:3},
          {required:true, message:'输入账号过长', trigger:'blur', max:5},
      ],
      password:[
          {required:true, message:'请输入密码！！！', trigger:'blur'},
      ],
      confirmPassword:[
          {validator:validatePass, trigger:'blur'},
      ],
      no:[
          {required:true, message:'请输入工号', trigger:'blur'},
      ],
      age:[
          {required:true, min:'18', trigger:'blur'},
      ]
    }
  })
  const formRef = ref();      // 获取到表单对象
  const router = useRouter();
  // 方法
  // 表单校验
  const register = () => {
    formRef.value.validate(async(valid) => {
      if (valid){
        // 发送请求验证用户是否存在
        const ret = await request.post('/register', data.form);
        if (ret.code === '200'){
          //console.log(ret.data);
          ElMessage.success('注册成功！！！');
          // 模拟等待时间
          setTimeout(() => {
            //location.href='/layout';
            router.push("/login");
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