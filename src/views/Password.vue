<template>
  <div>
    <el-card>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" style="padding:30px;">
        <el-form-item label="原密码 " prop="password">
          <el-input v-model="data.form.password"  placeholder="请输入原密码" size="large"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="data.form.newPassword"  placeholder="请输入新密码" size="large"></el-input>
        </el-form-item>
        <el-form-item required label="确认密码" prop="reNewPassword">
          <el-input show-password v-model="data.form.reNewPassword"  placeholder="请再次确认密码" size="large"></el-input>
        </el-form-item>
        <el-button @click="updatePassword" type="success" style="width:100%" size="large">立 即 修 改</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
  import {reactive, ref} from 'vue';
  import request from "@/utils/request.js";
  import {ElMessage} from "element-plus";
  // 判断两次密码是否一致
  const checkPassword = (rule, value, callback) => {
    if (!value){
      callback(new Error("输入密码不合法!!!"));
    }
    else if (data.form.newPassword !== value){
      callback(new Error("两次密码不一致！！！"));
    } else {
      callback();       // 两次密码信息一致，放行
    }
  }
  const data = reactive({
    user: JSON.parse(localStorage.getItem('xm-pro-user')),
    form: {},
    rules: {
      password:[
        {required:true, message:'请输入原始密码', trigger:'blur'},
      ],
      newPassword:[
        {required:true, message:'请输入新密码', trigger:'blur'},
      ],
      reNewPassword:[
        {validator:checkPassword, trigger:'blur'},
      ]
    }
  })
  const formRef = ref();
  // 发送axios请求修改密码

  const updatePassword = () => {
    data.form.id = data.user.id;
    data.form.role = data.user.role;
    formRef.value.validate(async (valid) => {
      if (valid){
        const ret = await request.put("/updatePassword", data.form);
        if (ret.code === '200') {
          // 修改密码后需要将旧的本地信息'xm-pro-user'清除掉
          localStorage.removeItem('xm-pro-user');
          ElMessage.success("修改成功");
          setTimeout(() => {
            location.href = '/login';         // !!!!string类型不是function
          }, 3000);
        }
        else {
          ElMessage.error(ret.data);
        }
      }
    })
  }
</script>