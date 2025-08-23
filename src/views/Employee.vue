<template>
  <h1>员工管理</h1>
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
          <el-table-column label="名称" prop="name"></el-table-column>
          <el-table-column label="性别" prop="sex"></el-table-column>
          <el-table-column label="编号" prop="no"></el-table-column>
          <el-table-column label="年龄" prop="age"></el-table-column>
          <el-table-column label="描述" prop="description" show-overflow-tooltip></el-table-column>
          <!--show-overflow-tooltip：用于对多文字进行省略，鼠标悬浮会显示-->
          <el-table-column label="部门id" prop="departmentId"></el-table-column>
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
      <el-dialog :title="data.title" v-model="data.dialogFormVisible" width="500" style="background-color:#cccccc">
        <el-form :model="data.form" style="padding-left:20px;padding-right:50px">
          <el-form-item label="姓名" :label-width="formLabelWidth">
            <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入姓名"/>
          </el-form-item>
          <el-form-item label="编号" :label-width="formLabelWidth">
            <el-input v-model="data.form.no" autocomplete="off" placeholder="请输入编号"/>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth">
            <el-radio-group v-model="data.form.sex">
              <el-radio label="男" value="男"></el-radio>
              <el-radio label="女" value="女"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄" :label-width="formLabelWidth">
            <el-input-number :min="18" v-model="data.form.age" autocomplete="off" placeholder="请输入年龄"/>
          </el-form-item>
          <el-form-item label="描述" :label-width="formLabelWidth">
            <el-input :rows="3" type="textarea" v-model="data.form.description" autocomplete="off" placeholder="员工描述信息"/>
            <!--<el-input type="textarea"></el-input>-->
          </el-form-item>
          <el-form-item label="部门id" :label-width="formLabelWidth">
            <el-input v-model="data.form.departmentId" autocomplete="off" placeholder="所在部门编号"/>
          </el-form-item>
          <!--<el-form-item label="Zones" :label-width="data.form.formLabelWidth">-->
          <!--  <el-select v-model="data.form.region" placeholder="Please select a zone">-->
          <!--    <el-option label="Zone No.1" value="shanghai" />-->
          <!--    <el-option label="Zone No.2" value="beijing" />-->
          <!--  </el-select>-->
          <!--</el-form-item>-->
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
  import {Delete, Edit, Search} from "@element-plus/icons-vue";
  import {reactive} from 'vue'
  import request from "@/utils/request.js";
  import {ElMessage, ElMessageBox} from "element-plus";
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
      }
  );

  // 路径跳转发送axios请求/事件触发发送axios请求
  // 方案一  --> 根据js代码执行顺序来发送请求
  //request.get('/employee/all').then((res) => {
  //  data.tableData = res.data;
  //})

  // 无法调用，需手动调用
  // 方案二   --》 genasync/await来发送请求并手动调用
  //async () => {
  //  const res = await request.get('/employee/all');
  //  data.tableData = res.data;
  //}
  const loadData = async () => {
    const res = await request.get('/employee/page', {
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
  const save = async (row) => {
    // 两个共用一个窗口，根据是否有id判断是新增操作还是编辑操作
    data.form.id ? update() : add();
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
    const ret = await request.post('/employee/add', data.form);
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
    //const ret = request.put('/employee/modify', row);
    // 将行数据放到修改面板上
    data.form = JSON.parse(JSON.stringify(row));
    data.dialogFormVisible = true;
    data.title = '编辑员工信息';
  }
  // 发送修改
  const update = async () => {
    //alert("修改");
    const ret = await request.put('/employee/modify', data.form);
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
      const ret = await request.delete('/employee/delete/' + id);
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
      const ret = await request.delete('/employee/batch', {data : data.ids});  // 无论如何传递的都是js对象
      if (ret.code === '200'){
        ElMessage.success('批量操作成功');
        loadData();
      } else {
        ElMessage.error('批量操作失败');
      }
    }).catch();
  }
</script>

<style scoped>

</style>