<template>
  <div>
      <el-button type="primary" @click="router.push('/manager/test')">a</el-button>
      <el-button type="primary" @click="router.push('/manager/params')">b</el-button>
      <el-button type="primary" @click="router.push('/manager/element')">c</el-button>
      <el-button type="success" @click="router.push('/manager/axios')">Axios向后端发送请求</el-button>
      <el-button type="success" @click="getAll">获取所有信息</el-button>
      {{data.all }}
      <router-view/>
  </div>
</template>

<script setup>
  import router from "@/router/index.js";
  import request from "@/utils/request.js";
  import {reactive} from 'vue';

  const data = reactive({
    all:[]
  })

  const getAll = async () => {            // 异步加载获取信息
    const res = await request.get('/employee/all');
    console.log(res.data);
    data.all = res.data;
  }
</script>
<style scoped>

</style>