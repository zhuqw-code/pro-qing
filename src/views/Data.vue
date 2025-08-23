<template>
  <!--<h1>数据汇总</h1>-->
  <div>
    <div>
      <el-card style="margin-top:10px">
        <el-input prefix-icon="search" v-model="data.name" placeholder="请输入要查询的数据" style="width:240px; margin-right: 10px"></el-input>
        <el-button type="primary">查询</el-button>
        <el-button type="warning">重置</el-button>
      </el-card>
    </div>
    <div>
      <el-card style="margin-top:10px">
        <el-button type="primary">新增</el-button>
        <el-button type="danger">删除</el-button>
        <el-button type="success">导入</el-button>
        <el-button type="warning">导出</el-button>
      </el-card>
    </div>
    <div>
      <el-card style="margin-top:10px">
        <el-table :data="data.tableData" stripe border>
          <el-table-column label="日期" prop="date"></el-table-column>
          <el-table-column label="昵称" prop="name"></el-table-column>
          <el-table-column label="地址" prop="address"></el-table-column>
          <el-table-column label="操作">
            <template #default="scope">
              <el-button circle @click="edit(scope.row)" type="primary">
                <el-icon><Edit/></el-icon>
              </el-button>
              <!--  可以得到每行数据  -->
              <!--{{scope.row}}-->
              <!--  如何对每行数据进行删除操作呢？  -->
              <el-button circle @click="del(scope.row.name)" type="danger">
                <el-icon><Delete/></el-icon>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <el-card style="margin-top:10px">
        <el-pagination
            v-model:current-page="data.currentPage"
            v-model:page-size="data.pageSize"
            :page-sizes="[3, 6, 9, 12, 15]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total"
            size="default"
        />
      </el-card>
    </div>
  </div>
</template>

<script setup>
import {Delete, Edit, Search} from "@element-plus/icons-vue";
  import {reactive} from 'vue'
  const data = reactive({
        name: null,
        tableData:[
          {
            date: '2016-05-03',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-02',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-04',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-01',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-03',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-02',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-04',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
          {
            date: '2016-05-01',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles',
          },
        ],
        currentPage:1,
        pageSize:5,
        total:36,
      }
  );
</script>

<style scoped>

</style>