<template>
  <div class="app-container">
    <el-row>
      <el-button @click="openDialog">新增</el-button>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-tree :data="treeList" :props="defaultProps" accordion @node-click="handleNodeClick">
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <span>{{ node.label }}</span>
            <span>
              <el-button
                type="text"
                size="mini"
                @click="() => append(data)"
              >
                Append
              </el-button>
              <el-button
                type="text"
                size="mini"
                @click="() => remove(node, data)"
              >
                Delete
              </el-button>
            </span>
          </span>
        </el-tree>
      </el-col>
    </el-row>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form ref="route" :model="route" label-width="80px">
        <el-form-item label="上级路由">
          <el-cascader
            v-model="route.parentId"
            :props="defaultProps"
            :options="treeList"
            @change="handleChange"
          />
        </el-form-item>
        <el-form-item label="路由名称">
          <el-input v-model="route.name" />
        </el-form-item>
        <el-form-item label="路由类型">
          <el-input v-model="route.type" />
        </el-form-item>
        <el-form-item label="URL">
          <el-input v-model="route.url" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="createRoute">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getRoutes } from '@/api/resource'
import { buildTree } from '@/utils'
export default {
  name: 'Route',
  data() {
    return {
      dialogVisible: false,
      route: {},
      treeList: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getRoutes().then((response) => {
        const tree = buildTree(response.data, '-1')
        console.log(tree)
        this.treeList = tree
      })
    },
    handleNodeClick(data) {
      console.log(data)
    },
    append(data) {
      console.log(data)
    },
    remove(node, data) {
      console.log(data)
    },
    openDialog() {
      this.dialogVisible = true
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    createRoute() {
      console.log(this.route)
    },
    handleChange(value) {
      console.log(value)
    }
  }
}
</script>
<style>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>
