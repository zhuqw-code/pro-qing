<template>
  <div>{{data.empList}}</div>
</template>

<script setup>
  import {reactive} from 'vue'
  import request from "@/utils/request.js";
  const data = reactive({
    empList:[]
  })
  request.get('/employee/id/3/jujingyi').then(res => {
    data.empList = res.data;
  })
</script>

<style scoped>

</style>