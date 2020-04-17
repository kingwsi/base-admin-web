<template>
  <div class="app-container">
    <el-row style="padding-bottom:10px">
      <el-button @click="handleCreate">新增</el-button>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-table
          highlight-current-row
          :data="pageInfo.records"
          border
          style="width: 100%"
          @current-change="handleCurrentChange"
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
        <el-card v-show="user.username!==null" class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>user info</span>
          </div>
          <el-form ref="form" :model="user" label-width="80px">
            <el-form-item label="用户名">
              <el-input v-model="user.username" />
            </el-form-item>
            <el-form-item v-if="operate==='create'" label="密码">
              <el-input v-model="user.password" />
            </el-form-item>
            <el-form-item label="介绍">
              <el-input v-model="user.introduction" />
            </el-form-item>
            <el-form-item label="角色">
              <el-select v-model="user.roles" multiple placeholder="请选择">
                <el-option
                  v-for="item in roles"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">{{ operate==='create' ? '确认':'更新' }}</el-button>
            </el-form-item>
          </el-form>
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
import { getPageInfo, update, create } from '@/api/user'
import { getRoles } from '@/api/role'
import { deepClone } from '@/utils'
export default {
  name: 'User',
  // components: {
  //   Pagination
  // },
  data() {
    return {
      operate: 'create',
      pageInfo: [],
      user: {
        username: '',
        introduction: '',
        roles: []
      },
      roles: []
    }
  },
  created() {
    this.page()
    this.getRoleList()
  },
  methods: {
    page() {
      getPageInfo().then(res => {
        if (res.data.total > 0) {
          this.pageInfo = res.data
        }
      })
    },
    getRoleList() {
      getRoles().then(res => {
        this.roles = res.data
      })
    },
    updateUser() {
      update(this.user)
    },
    handleClick(row) {
      console.log(row)
    },
    handleCurrentChange(val) {
      this.operate = 'update'
      this.user = deepClone(val)
    },
    handleCreate() {
      this.user = {}
    },
    onSubmit() {
      if (this.operate === 'create') {
        create(this.user).then(res => {
          this.$message({
            message: '新增成功',
            type: 'success'
          })
        })
      } else {
        update(this.user).then(res => {
          this.$message({
            message: '更新成功',
            type: 'success'
          })
        })
      }
    }
  }
}
</script>
