<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12">
        <div class="left-tree">
          <el-tree :data="treeList" :default-expand-all="true" :props="defaultProps" accordion @node-click="handleNodeClick">
            <span slot-scope="{ node, data }" class="custom-tree-node">
              <span>{{ node.label }}</span>
              <span>
                <el-button
                  type="text"
                  icon="el-icon-edit"
                  size="mini"
                  @click="() => append(data)"
                >
                  编辑
                </el-button>
                <el-button
                  type="text"
                  icon="el-icon-edit"
                  size="mini"
                  @click="() => append(data)"
                >
                  新增下级
                </el-button>
                <el-button
                  type="text"
                  icon="el-icon-delete"
                  size="mini"
                  @click="() => remove(node, data)"
                >
                  删除
                </el-button>
              </span>
            </span>
          </el-tree>
        </div>
      </el-col>
      <el-col :span="11" :offset="1">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>编辑</span>
          </div>
          <el-form ref="form" :model="route" label-width="80px">
            <el-form-item label="路由名称">
              <el-input v-model="route.name" />
            </el-form-item>
            <el-form-item label="类型">
              <el-select v-model="route.type" placeholder="组件类型">
                <el-option value="ROUTE">菜单</el-option>
                <el-option value="BUTTON">按钮</el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="组件名称">
              <el-input v-model="route.uri" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">{{ operate==='create' ? '确认':'更新' }}</el-button>
            </el-form-item>
          </el-form>
        </el-card>
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
          <el-tree
            ref="tree"
            :data="treeList"
            show-checkbox
            :default-expand-all="true"
            node-key="id"
            :props="defaultProps"
          />
        </el-form-item>
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
      operate: 'create',
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

    },
    append(data) {
      this.handleCreate()
    },
    remove(node, data) {
      console.log(data)
    },
    handleCreate() {
      this.route = {}
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
    font-size: 15px;
    padding-right: 8px;
  }
  .left-tree{
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    padding: 10px
  }
</style>
