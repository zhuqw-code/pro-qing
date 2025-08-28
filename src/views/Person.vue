<template>
  <div>
    <!--  用户详情页面  -->
    <el-card :title="data.title" style="padding:50px; background-color:#cccccc; width:50%; margin:20px" destroy-on-close>
      <el-form :model="data.form">
        <el-form-item label="头像">
          <div style="width:100%; display:flex; justify-content: center">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:8080/files/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <img v-if="data.form.avatar" width="100%" :src="data.form.avatar" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="data.form.username" size="large" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="data.form.name" size="large"  autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <!--这里是用户信息-->
      <div v-if="data.form.role === 'EMP'">
        <el-form-item label="编号">
          <el-input v-model="data.form.no" autocomplete="off" placeholder="请输入编号" disabled/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="data.form.sex">
            <el-radio label="男" value="男"></el-radio>
            <el-radio label="女" value="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input-number :min="18" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"/>
        </el-form-item>
        <el-form-item label="描述">
          <el-input :rows="3" type="textarea" v-model="data.form.description" autocomplete="off" placeholder="员工描述信息"/>
          <!--<el-input type="textarea"></el-input>-->
        </el-form-item>
        <el-form-item label="部门id">
          <el-input v-model="data.form.departmentId" autocomplete="off" placeholder="所在部门编号"/>
        </el-form-item>
      </div>
      <!-- 提交逻辑 -->
      <template #footer>
        <div style="text-align: center">
          <el-button @click="modify" style="width:50%;" type="primary">修  改  用  户  信  息</el-button>
        </div>
      </template>
    </el-card>
  </div>
</template>

<script setup>
  import {reactive} from 'vue';
  import request from "@/utils/request.js";
  import {ElMessage} from "element-plus";
  import {Plus} from "@element-plus/icons-vue";
  // 数据
  const data = reactive({
    user: JSON.parse(localStorage.getItem('xm-pro-user')),
    form: {},
    rules:{
      username:[
        {required: true, message: '请输入用户名！！！', trigger: 'blur'}
      ],
    },
  })

  // 方法
  // 获取employee的详细信息并赋值给form
  if (data.user.role === 'EMP'){
    request.get('/employee/one?id='+data.user.id).then((ret) => {
      //Object.assign(data.form, ret.data);
      data.form = ret.data;
    })
  } else {
    //Object.assign(data.form, data.user);
    data.form = data.user;
  }

  // emit实现父子通信
  const emit = defineEmits(['updUser']);  // 自定义事件
  const modify = () => {
    if (data.user.role === 'EMP'){ // 如果是员工
      request.put('/employee/modify', data.form).then((ret) => {
        if (ret.code === '200') {
          ElMessage.success('修改成功!!!');
          // 将最新数据存储到本地内存中
          localStorage.setItem('xm-pro-user', JSON.stringify(data.form));  // !!!发送给服务器要是String类型数据
          // 向上发射给父级
          emit('updUser');
        } else {
          ElMessage.error('操作失败!!!');
        }
      })
    } else{  // 管理员
      request.put('/admin/modify', data.form).then((ret) => {
        if (ret.code === '200') {
          ElMessage.success('修改成功!!!');
          // 将最新数据存储到本地内存中
          //localStorage.setItem('xm-pro-user', data.form);
          localStorage.setItem('xm-pro-user', JSON.stringify(data.form));
          // 向上发射给父级
          emit('updUser');
        } else {
          ElMessage.error('操作失败!!!');
        }
      })
    }
  }
  // 加载用户头像(回调函数)
  const handleAvatarSuccess = (response) => {
    console.log(response.data);
    data.form.avatar = response.data;
  }
</script>

<!--
      问题：因为每个组件之间没有之间联系，当我们想要父子之间之间共享数据时，我们需要如何操作？
        #defineEmit（实现子传父）                   // 自定义事件
          先创建emit发射器【const emit = defineEmit(['updateUser'])】，然后调用更新函数完成本地xm-pro-user的更新。发送请求修改数据库信息，
          之后点火，发射emit【emit('updateUser')】，在父组件的router-view中拦截对应发射器【@updateUser = '父组件function'】
          从父组件中得到本地的最新数据
          【子--->父 首先要有一个车emit【给车起个名】，然后需要将货物准备好localStorage，发车emit(车名)，父组件需要标识等待的车，以及需要如何处理这个货物【@车名=处理】】
-->

<style scoped>
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
  /*border: 2px solid red;*/
  /*display: flex;*/
  /*justify-content: center;*/
}
</style>

<style>
.avatar-uploader .el-upload {
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

/*.avatar-uploader .el-upload:hover {*/
/*  border-color: var(--el-color-primary);*/
/*}*/

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: black;
  width: 120px;
  height: 120px;
  text-align: center;
  border: 1px solid #181010;
  border-radius: 50%;
}
</style>