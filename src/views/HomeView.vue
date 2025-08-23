<template>
    <div>
        <h1 style="background-color:red">hello vue!!!</h1>
        <select>
            <option style="width:200px;height:50px" v-for="(item, idx) in data.goods">{{item}}  ----  {{idx}}</option>
        </select>
        <div v-on:click="f(item)" v-for="item in data.goods">{{item}}</div>
    </div>
    <!--一般写法-->
    <div v-bind:style="{'width':'100px', 'height':'100px', 'backgroundColor':'blueviolet'}"></div>
    <!--开发写法-->
    <div v-bind:style="data.styles"></div>
    <div :style="data.styles"></div>
    <img :src="'https://www.runoob.com/wp-content/uploads/2017/01/vue.png'">   <!--注意这里要传递字符串-->
    <img :src="data.imgUrl">   <!--注意这里要传递字符串-->
<!--
    v-bind:就是将传统css样式写成js对象，实现动态样式
-->
    <br>
    <hr>
    <!--非编程式跳转-->
    <router-link to="/test">跳转到test</router-link>
    <router-link to="/element">跳转到Element</router-link>
    <!--编程式跳转-->
    <el-button type="primary" @click="changePageByPush">跳转到element页面</el-button>
    <el-button type="primary" @click="changePageByReplace">跳转到test页面</el-button>
    <el-button type="primary" @click="router.push('/params')">跳转到路由传参页面</el-button>
    <!--
        细节：
            ·push：实现的跳转能够返回
            ·replace：实现的跳转不能实现返回
      -->
</template>


<script setup>
import {reactive} from 'vue'

const data = reactive({
    goods:['a', 'b', 'c', 'd', 'e', 'f'],
    styles:{
        width:'100px',
        height:'100px',
        backgroundColor:'blue'
    },
    imgUrl:'https://www.runoob.com/wp-content/uploads/2017/01/vue.png'
})
const f = (value) => {
    alert(value)
}

// 编程式路由实现页面跳转
//import {useRouter} from 'vue-router'
import router from "@/router/index.js";
//const router = useRouter();
const changePageByPush = () => {
    router.push('/element');
}
const changePageByReplace = () => {
    router.replace('/test')
}
</script>
