<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12">
        <el-table
          :data="pageInfo.records"
          border
          style="width: 100%"
        >
          <el-table-column
            fixed
            prop="username"
            label="用户名"
          />
          <el-table-column
            fixed
            prop="introduction"
            label="介绍"
          />
          <el-table-column
            fixed
            prop="createdDate"
            label="创建时间"
          />
          <el-table-column
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="handleClick(scope.row)">查看</el-button>
              <el-button type="text" size="small">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="11" :offset="1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>卡片名称</span>
            <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination
      :total="5"
      layout="prev, pager, next"
    />
  </div>
</template>
<script>
import { getPageInfo } from '@/api/user'
export default {
  name: 'User',
  // components: {
  //   Pagination
  // },
  data() {
    return {
      pageInfo: []
    }
  },
  created() {
    this.page()
  },
  methods: {
    page() {
      getPageInfo().then(res => {
        this.pageInfo = res.data
      })
    },
    handleClick(row) {
      console.log(row)
    }
  }
}
</script>
