<template>
  <div>
      <h1>路由传参!!!</h1>
      <el-button type="primary" @click="router.push('/manager/test?id=1')">路由传参</el-button>
      <el-button type="primary" @click="router.push({path:'/manager/test', query:{id:'001', name:'jack'}})">路由传参</el-button>
      <!--
          注意事项：
            ·
        -->
  </div>
</template>

<script setup>
  import router from "@/router/index.js";     // 导入路由
  import {reactive} from 'vue'
  const data = reactive({
      id: router.currentRoute.value.query.id,      // 接收传入参数
      name: router.currentRoute.value.query.name,
  });
  console.log(data.id);   // 是否按顺序执行
</script>

<style scoped>

</style>