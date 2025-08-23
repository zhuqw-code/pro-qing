import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect:{name:'layout'}},   // 设置默认跳转路由
    { path: '/home', name: 'home', component: () => import('../views/HomeView.vue')},
    { path: '/404', name: '404', component: () => import('../views/404.vue')},
    { path: '/:pathMatch(.*)', redirect: '/404'},   // 不合法页面跳转
    { path: '/manager', component: () => import('../views/Manager.vue'), children:[
        { path: 'test', name: 'test', meta:{title:'测试页'}, component: () => import('../views/Test.vue')},
        { path: 'element', name: 'element', meta:{title:'element页'}, component: () => import('../views/TestElementPlus.vue')},
        { path: 'params', name: 'params', meta:{title:'传参页'}, component: () => import('../views/SendParams.vue')},
        { path: 'axios', name: 'axios', meta:{title:'测试交互'}, component: () => import('../views/AxiosTest.vue')},
    ]},        // 1.子路由路径不能以/开始，因为父路由会自动在后面增加/
               // 2.需要在父级中用router-view来标明组件的位置
    { path: '/layout', name:"layout", component: () => import('../views/Layout.vue'), children:[
        { path: 'data', name: 'data', component: () => import('../views/Data.vue') },
        { path: 'employee', name: 'employee', component: () => import('../views/Employee.vue') },
        { path: 'server', name: 'server', component: () => import('../views/Server.vue') },
        { path: 'own', name: 'own', component: () => import('../views/Own.vue') },
      ]}
  ],
})
// 添加路由守卫，在跳转之前进行路由合法性校验
router.beforeEach((to, from, next) => {
    document.title=to.meta.title;
    next();
})
export default router
