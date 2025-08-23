<template>
  <div :style="{margin:'10px'}">
      <el-button type="success" @click="router.back()">返回</el-button>
      <h1>Test element-plus</h1>
      <el-row>
          <el-button color="#626aef">默认按钮</el-button>
          <el-button type="primary" plain>主要按钮</el-button>
          <el-button type="success" round>成功按钮</el-button>
          <el-button type="info" circle>信息按钮</el-button>
          <el-button type="warning" loading>警告按钮</el-button>
          <el-button type="danger" dark>危险按钮</el-button>
      </el-row>
  </div>
  <div :style="{margin:'10px'}">
        <el-icon :size="20">
            <Edit />
        </el-icon>
        <!-- 或者独立使用它，不从父级获取属性 -->
        <el-icon :size="20" :style="{top:'5px'}"><View/></el-icon>100
        <!--按钮中导入icon-->
        <el-button type="danger" :icon="Delete"></el-button>
        <!--输入框中使用icon-->
        <el-input
                v-model="username"
                style="width: 240px"
                placeholder="Pick a date"
                :prefix-icon="Search"
        />
    </div>
    {{data.empList}}
</template>

<script setup>
  import {Delete, Search} from "@element-plus/icons-vue";
  import {ref} from 'vue'
  import router from "@/router/index.js";
  import request from "@/utils/request.js";
  import {reactive} from 'vue'
  const data = reactive({
    empList: [],
  })
  request.get("/employee/all").then(res => {
    console.log(res.data);
    alert(res.data);
    data.empList = res.data;
  })
</script>

<style scoped>

</style>