<template>
  <h1>管理员信息</h1>
  <div>
    <div>
      <el-card style="margin-top:10px">
        <el-input prefix-icon="search" v-model="data.name" placeholder="请输入要查询的数据" style="width:240px; margin-right: 10px"></el-input>
        <el-button @click="loadData"  type="primary">查询</el-button>
        <el-button @click="reset" type="warning">重置</el-button>
      </el-card>
    </div>
    <div>
      <el-card style="margin-top:10px">
        <el-button @click="handleAdd" type="primary">新增</el-button>
        <el-button @click="deleteBatch" type="danger">批量删除</el-button>
      </el-card>
    </div>
    <div>
      <!--table栏-->
      <el-card style="margin-top:10px">                <!--添加行变化监听事件@selection-change -->
        <el-table :data="data.tableData" stripe border @selection-change="handleSelectionChange">
          <!--添加多选框
            :selectable="function"仅对type = selection的列有有效
          -->
          <el-table-column type="selection" width="40px"/>
          <!--表格属性栏-->
          <el-table-column label="头像" prop="avatar" width="80px">
            <template #default="scope">
              <img v-if="scope.row.avatar" :src="scope.row.avatar" style="width:40px; height:40px; border-radius:50%"/>
            </template>
          </el-table-column>
          <el-table-column label="账号" prop="username"></el-table-column>
          <el-table-column label="姓名" prop="name"></el-table-column>
          <el-table-column label="角色" prop="role"></el-table-column>
          <el-table-column label="操作">
            <template #default="scope">     <!--获取到行对象-->
              <el-button circle @click="handleUpdate(scope.row)" type="primary">  <!--传递行对象-->
                <el-icon><Edit/></el-icon>
              </el-button>
              <!--  可以得到每行数据  -->
              <!--{{scope.row}}-->
              <!--  如何对每行数据进行删除操作呢？  -->
              <!--TODO :icon添加图标   也可以通过<el-icon>来添加 link可以去掉背景色 -->
              <!--<el-button :icon="Delete" circle @click="del(scope.row.name)" type="danger">-->
              <el-button circle @click="remove(scope.row.id)" type="danger">
                <el-icon><Delete/></el-icon>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <!--分页栏-->
      <el-card style="margin-top:10px">
        <el-pagination
            v-model:current-page="data.currentPage"
            v-model:page-size="data.pageSize"
            :page-sizes="[3, 6, 9, 12, 15]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
            size="default"
            @current-change="loadData"
            @size-change="loadData"
        />
      </el-card>
      <!--
          如何控制页面转化
            current-change=function
            size-change=function
            当有变化时就会重新加载最新的页面信息！！！！！！！！
            注意：pageNum和pageSize
     -->
    </div>
    <!--新增窗口-->
    <div>
      <el-dialog :title="data.title" v-model="data.dialogFormVisible" width="500" style="background-color:#cccccc" destroy-on-close>  <!--下次点入时销毁上次错误信息-->
        <el-form :rules="data.rules" ref="refForm" :model="data.form" style="padding-left:20px;padding-right:50px">
          <el-form-item label="头像" prop="avatar">
            <div style="width:100%; display:flex; justify-content: center">
              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:8080/files/upload"
                  list-type="picture"
                  :on-success="handleAvatarForm"
              >
                <!--<img v-if="data.form.avatar" :src="data.form.avatar" class="avatar"/>-->
                <!--<el-icon class="avatar-uploader-icon" v-else><Plus/></el-icon>-->
                <el-button type="primary">点击添加头像</el-button>
              </el-upload>
            </div>
          </el-form-item>
          <el-form-item label="账号" :label-width="formLabelWidth" prop="username">
            <el-input v-model="data.form.username" autocomplete="off" placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item label="姓名" :label-width="formLabelWidth">
            <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item label="角色" :label-width="formLabelWidth">
            <el-input v-model="data.form.role" autocomplete="off" placeholder="请输入职务"/>
          </el-form-item>
          <!--<el-form-item label="Zones" :label-width="data.form.formLabelWidth">-->
          <!--  <el-select v-model="data.form.region" placeholder="Please select a zone">-->
          <!--    <el-option label="Zone No.1" value="shanghai" />-->
          <!--    <el-option label="Zone No.2" value="beijing" />-->
          <!--  </el-select>-->
          <!--</el-form-item>-->
        <!--
            TODO: 表单校验
            1. 在el-form中添加ref="formRef";
            2. 在script中引入const refRef = ref();    // 用于获取form表单对象
            3. 在save这个发送请求的函数中做统一的处理函数; formRef.value.validate((valid) => {根据valid的值做出下面逻辑判断})
            4. 为每个form表单项添加对应规则，并通过prop绑定到所需位置

        -->
        </el-form>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="info" @click="data.dialogFormVisible = false">取消</el-button>
            <el-button @click="save" type="primary">确定</el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
  import {Delete, Edit, Plus, Search} from "@element-plus/icons-vue";
  import {reactive} from 'vue'
  import request from "@/utils/request.js";
  import {ElMessage, ElMessageBox} from "element-plus";
  import {ref} from 'vue';

  const data = reactive({
        name: null,
        //tableData: [
        //  {
        //    date: '2016-05-03',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-02',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-04',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-01',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-03',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-02',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-04',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //  {
        //    date: '2016-05-01',
        //    name: 'Tom',
        //    address: 'No. 189, Grove St, Los Angeles',
        //  },
        //],
        tableData: [],
        currentPage: 1,
        pageSize: 5,
        total: 0,
        dialogFormVisible: false,
        title: '',
        form: {},
        ids: [],          // 多选的数据id
        rules:{
          username:[
            {required: true, message: '请输入用户名！！！', trigger: 'blur'}
          ],
          password:[
            {required: true, message: '请输入密码！！！', trigger: 'blur'}
          ],
          avatar:[
            {required: true, message: '请选择用户头像！！！', trigger: 'blur'}
          ]
        }
      }
  );
  // 表单校验数据
  const refForm = ref();

  // 路径跳转发送axios请求/事件触发发送axios请求
  // 方案一  --> 根据js代码执行顺序来发送请求
  //request.get('/admin/all').then((res) => {
  //  data.tableData = res.data;
  //})

  // 无法调用，需手动调用
  // 方案二   --》 genasync/await来发送请求并手动调用
  //async () => {
  //  const res = await request.get('/admin/all');
  //  data.tableData = res.data;
  //}
  const loadData = async () => {
    const res = await request.get('/admin/page', {
      params:{
        pageNum:data.currentPage,      // v-model：已经实现双向绑定，故我们可以直接使用最新值
        pageSize:data.pageSize,        // 使用全局设置的page信息
        name:data.name,                // 根据姓名进行条件查询
      }
    });
    data.total = res.data.total;   // total的更新：在data中无法这样操作，故我们在获取到res时就更新total的值，正好响应数据中有total属性
    data.tableData = res.data.list;
  }
  loadData();

  // 重置按钮
  const reset = () => {
    data.name = '';
  }


  /**
   * 流程汇总
   *    1.通过事件显示操作弹窗
   *    2.<template #default="scope"> 通过scope这个参数进行表单数据获取并显示
   *    3.之后通过确认按钮发送对应请求 post/put
   *    4.重新加载数据
   */
  // 控制器用来处理发送什么请求
  const save = (row) => {
    refForm.value.validate(async(valid) => {   // 如果所有规则都成功就执行方法体中代码
      if (valid){
        // 两个共用一个窗口，根据是否有id判断是新增操作还是编辑操作
        data.form.id ? update() : add();
      }
    })
  }

  // 新增操作
  const handleAdd = () => {
    data.title = '新增员工';
    data.dialogFormVisible = true;
    data.form = {};
  }
  // 发送新增
  const add = async () => {
    //alert("新增");
    const ret = await request.post('/admin/add', data.form);
    if (ret.code === '200') {
      data.dialogFormVisible = false;
      ElMessage.success('添加成功');
      loadData();     // 新增数据后需要重新加载数据
    } else {
      ElMessage.error('添加失败');
    }
  }

  // 修改操作
  // TODO : 如果直接使用row会造成浅拷贝，直接修改了table的值，即使取消也会造成前端显示新数据，通过JSON.parsent(row)备份一份，防止污染原始数据（数据库信息不会造成影响，因为我们没有发送请求）
  const handleUpdate = (row) => {
    //const ret = request.put('/admin/modify', row);
    // 将行数据放到修改面板上
    data.form = JSON.parse(JSON.stringify(row));
    data.dialogFormVisible = true;
    data.title = '编辑员工信息';
  }
  // 发送修改
  const update = async () => {
    //alert("修改");
    const ret = await request.put('/admin/modify', data.form);
    if (ret.code === '200') {
      data.dialogFormVisible = false;
      ElMessage.success('修改成功');
      loadData();     // 新增数据后需要重新加载数据
    } else {
      ElMessage.error('修改失败');
    }
  }

  // 删除数据  （单个/多个）
  const remove = (id) => {
    const confirm = ElMessageBox.confirm('删除后数据无法恢复，您确认删除吗？', '删除确认', {type:"warning"});
    confirm.then(async() => {
      const ret = await request.delete('/admin/delete/' + id);
      if (ret.code === '200') {
        ElMessage.success('删除成功');
        loadData();
      } else {
        ElMessage.error('删除失败');
      }
    }).catch();
  }
  // 删除多个数据
  const handleSelectionChange = (rows) => {      // 行是否被选择，返回选择数组
    //console.log(rows);
    data.ids = rows.map(row => row.id);
  }
  // 发送批量删除的axios请求
  const deleteBatch = () => {
    if (data.ids.length === 0){
      ElMessage.warning('请选择数据');
      return;
    }
    const confirm = ElMessageBox.confirm('是否要进行批量删除', '批量删除', {type:'warning'});
    confirm.then(async () => {
      const ret = await request.delete('/admin/batch', {data : data.ids});  // 无论如何传递的都是js对象
      if (ret.code === '200'){
        ElMessage.success('批量操作成功');
        loadData();
      } else {
        ElMessage.error('批量操作失败');
      }
    }).catch();
  }

  // 图片回显
  const handleAvatarForm = (response) => {
    console.log(response.data);
    data.form.avatar = response.data;
  }

</script>

<style scoped>
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>

<style>
 .avatar-uploader .el-upload {
   /*border: 1px dashed var(--el-border-color);*/
   cursor: pointer;
   /*position: relative;*/
   overflow: hidden;
   transition: var(--el-transition-duration-fast);
 }

.avatar-uploader .el-upload:hover {
  /*border-color: var(--el-color-primary);*/
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  text-align: center;
  border-radius: 50%;
  border: 1px solid #35383a;
}
</style>

<!--
    总结：
        文件上传
          1.  Form
            <el-upload
              action="选择完文件后发送的请求路径"
              :on-success="后端响应结果后需要执行的操作"
              show-file-list="false"  是否显示图片列表
              list-type="picture"     会自动解析response.data路径将图片呈现出来
            >
              <img v-if="data.form.avatar" :src="data.form.avatar"/>
              <el-icon v-else><Plus/></el-icon>
            </el-upload>
          2.  Table
              <el-table :data="data.tableData" stripe border @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="40px"/>
              <el-table-column label="头像" prop="avatar" width="80px">
                <template #default="scope">      // scope能够获得每行的item
                  <img v-if="scope.row.avatar" :src="scope.row.avatar" style="width:40px; height:40px; border-radius:50%"/>
                </template>
              </el-table-column>
          3.  上传逻辑
              用户从本地上传图片，选择图片后会根据action路径发送post请求添加图片到后台静态目录
              之后会将 download路径 + 图片路径 返回，前端保存该图片路径数据.
              根据<img v-if="data.form.avatar"/>来发送请求访问后端的download请求，将图片通过输入流的方式发送给浏览器，得以呈现
-->