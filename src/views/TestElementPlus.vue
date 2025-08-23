<template>
  <div>
      <h1> ElementPlus!!! </h1>
      <el-input v-model="data.username" style="width: 240px" placeholder="Please input" readonly/> {{ data.username }}
      <!--普通输入框
          注意事项：
            ·这里如果不进行数据的双向绑定，就不能进行数据写入操作
            ·一些属性
              ·readonly
              ·disabled
        -->
      <el-input prefix-icon="Search" suffix-icon="Calendar" v-model="data.search" style="width: 240px" placeholder="Please input"/>
      <el-input type="textarea" v-model="data.desc" style="width: 240px" placeholder="Please input"/>
      <!--设计搜索框
          搜索框：
            ·添加指定icon就能实现搜索框效果
            ·如果文本过多就要使用type="textarea"
      -->
  </div>
  <div>
      <el-select v-model="data.value" placeholder="Select" style="width: 240px">
          <el-option
                  v-for="item in data.options"
                  :key="item"
                  :label="item"
                  :value="item"
          />
      </el-select> {{data.value}}
      <!--
        注意事项
          ·select中含有option，我们使用v-model="data.value"来将options中选择的项传递给value
      -->
      <el-select
              multiple
              clearable
              v-model="data.food"
              placeholder="Select"
              style="width: 240px"
      >
          <el-option
                  v-for="item in data.fruits"
                  :key="item.id"
                  :label="item.label"
                  :value="item.name"
          />
      </el-select>{{data.food}}
      <!--
        注意事项
          ·select中含有option，我们使用v-model="data.value"来将options中选择的项传递给value
          ·:key的值必须是unique，否则会报错
          ·通过clearable来对选择的项进行清空
          ·如何实现多选框（multiple），虽然可以多选，但是会根据name判断是否是重复的，无论：key设置的为对象那个属性
          ·label：指的是显示项，下拉框中可供选择的栏目
          ·value：。。。。
          ·如果id相同点击一个就会将id相同的都选上
      -->
  </div>
  <div>
      <!--男女单选框-->
      <el-radio-group v-model="data.sex">
          <el-radio value="男">男</el-radio>
          <el-radio value="女">女</el-radio>
      </el-radio-group>
      <!--单选框-->
      <el-radio-group v-model="data.tag" size="large">
          <el-radio-button label="New York" value="1" />
          <el-radio-button label="Washington" value="2" />
          <el-radio-button label="Los Angeles" value="3" />
          <el-radio-button label="Chicago" value="4" />
      </el-radio-group>
      <!--
        注意事项:
          ·v-model="xxx"，xxx双向绑定的值value属性一定要是字符串类型
      -->
      <!--单选按钮-->
      <el-checkbox-group v-model="data.checkList">
          <el-checkbox disabled checked v-for="item in data.fruits" :key="item.id" :label="item.label" :value="item.name"></el-checkbox>
      </el-checkbox-group>  {{ data.checkList }}
      <!--
          注意事项:
          ·label指的是显示的标签
          ·value指的是更具体的信息
        -->
  </div>
  <div>
    <img src="@/assets/logo.svg" :style="{width:'100px'}">
    <el-image :src="data.url" ></el-image>
    <el-image
            :src=img
            style="width:50px"
            :preview-src-list="data.srcList"
    >
    </el-image>
    <!--
        注意细节：
          ·访问网络路径没有问题，但是访问本地路径就加载失败
          ·图片预览
      -->
  </div>
  <div>
      <!--轮播图-->
      <el-carousel>
          <el-carousel-item v-for="item in data.imgs" :key="item" height="300px" style="text-align:center;">
              <!--<h3 class="small justify-center" text="1xl">{{ item }}</h3>-->
              <img :src="item" height="300px" style="width:500px">
          </el-carousel-item>
      </el-carousel>
  </div>
  <div>
      <!--日期选择器-->
      <el-date-picker
              v-model="data.date"
              type="date"
              placeholder="请选择一个日期"
              :size="'default'"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
      />{{data.date}}
      <!--日期时间选择器-->
      <el-date-picker
              v-model="data.datetime"
              type="datetime"
              placeholder="请选择一个日期"
              :size="'default'"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
      />{{data.datetime}}
      <!--时间选择器-->
      <el-date-picker
              v-model="data.time"
              type="datetime"
              placeholder="请选择一个日期"
              :size="size"
              format="HH:mm:ss"
              value-format="HH:mm:ss"
      />{{data.time}}
      <!--范围选择器-->
      <el-date-picker
              v-model="data.daterange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
      />{{data.daterange ? data.daterange[0].length - data.daterange[1].length : 0}}
      <!--
          注意事项：
            ·format是对框中的日期格式进行格式转换
            ·value-format是对绑定的属性值进行格式化
        -->

  </div>
  <div>
      <!--表格呈现-->
      <el-table :data="data.tableData" style="width: 100%" stripe border>
          <el-table-column prop="date" label="日期" width="180" />
          <el-table-column prop="name" label="姓名" width="180" />
          <el-table-column prop="address" label="地址" />
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
      <!--
          注意细节：
            ·:data:用于进行数据绑定
            ·prop:是将每个元组的属性对应拿出来
            ·stripe:设置斑马纹
        -->
  </div>
  <div>
      <!--分页呈现-->
      <el-pagination
              v-model:current-page="data.currentPage"
              v-model:page-size="data.pageSize"
              :page-sizes="[3, 6, 9, 12, 15]"
              background
              layout="total, sizes, prev, pager, next, jumper"
              :total="data.total"
              size="default"
      />
  </div>
  <div>
      <el-dialog v-model="data.dialogTableVisible" title="编辑信息" width="800">
        <div style="padding: 20px">
            <h1>{{data.row}}</h1>
        </div>
      </el-dialog>
      <!--
          注意事项:
            ·dialogTableVisible:用于设置弹窗是否显示
      -->

  </div>
</template>

<script setup>
  import {ref, reactive} from 'vue'
  import {Search, Calendar, Delete, Edit} from '@element-plus/icons-vue'
  import img from '@/assets/logo.svg'
  import png1 from '@/assets/img1.png'
  import png2 from '@/assets/img2.png'
  import png3 from '@/assets/img3.png'
  import png4 from '@/assets/img4.png'
  const data = reactive({
      username:'zhangsan',
      search:'',
      desc:'sssssaaaaaaaaaaaaaaffdfffffffffffffffffffsssssaaaaaaaaaaaaaaffdfffffffffffffffffff',
      value:'',    // 用于接收options的值
      options:['苹果', '香蕉', '桃子'],
      food:'',
      fruits:[
          {id:1, name:"苹果0", label:"苹果"},
          {id:2, name:"蓝莓0", label:"蓝莓"},
          {id:3, name:"葡萄1", label:"葡萄"},
          {id:4, name:"葡萄2", label:"葡萄"},
          {id:4, name:"葡萄2", label:"葡萄"},  // 如果id相同选择一个就会导致多个被选择
      ],
      sex:'男',
      tag:'2',
      checkList:[],
      url:'@/assets/logo.svg',
      srcList:[
          img,
          'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
          'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
          'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
          'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
          'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
          'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
          'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
      ],
      imgs:[png1, png2, png3, png4],
      date:'',
      datetime:'',
      time:'',
      daterange:null,
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
      currentPage:1,    // 开始页码
      pageSize:3,       // 每页记录数
      total:50,
      dialogTableVisible:false,     // 控制弹窗显示
      row:null,
  })
  const del = (name) => {
      alert(name)
  }
  const edit = (row) => {
    data.dialogTableVisible = true;
    // 着个row我们想放到页面中，（先将其赋值给全局变量，然后再在需要的地方使用）
    data.row = row;
  }
</script>

<style scoped>

</style>