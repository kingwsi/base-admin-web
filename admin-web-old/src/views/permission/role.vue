<template>
  <div class="app-container">
    <el-button type="primary" @click="handleAddRole">New Role</el-button>

    <el-table :data="pageInfo.records" style="width: 100%;margin-top:30px;" border>
      <el-table-column
        fixed
        prop="name"
        label="角色名"
      />
      <el-table-column
        fixed
        prop="description"
        label="描述"
      />
      <el-table-column align="center" label="Operations">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">Edit</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" :title="operate==='edit'?'编辑':'新增'">
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item label="Name">
          <el-input v-model="role.name" placeholder="Role Name" />
        </el-form-item>
        <el-form-item label="Desc">
          <el-input
            v-model="role.description"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="Role Description"
          />
        </el-form-item>
        <el-form-item label="菜单">
          <el-tree
            ref="tree"
            :data="routesTree"
            show-checkbox
            :default-expand-all="true"
            node-key="id"
            :props="defaultProps"
          />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">Cancel</el-button>
        <el-button type="primary" @click="confirmRole">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import path from 'path'
import { deepClone, buildTree } from '@/utils'
import { getRoutes, addRole, deleteRole, getRolesPage, updateRole } from '@/api/role'

const defaultRole = {
  id: '',
  name: '',
  description: '',
  routes: []
}

export default {
  data() {
    return {
      role: Object.assign({}, defaultRole),
      routes: [],
      routesTree: [],
      pageInfo: {},
      dialogVisible: false,
      operate: 'create',
      checkStrictly: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getRoutes()
    this.page()
  },
  methods: {
    async getRoutes() {
      const res = await getRoutes()
      this.routesTree = buildTree(res.data, '-1')
    },
    async page() {
      const res = await getRolesPage()
      this.pageInfo = res.data
    },
    handleAddRole() {
      this.role = Object.assign({}, defaultRole)
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedNodes([])
      }
      this.operate = 'create'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.operate = 'update'
      this.dialogVisible = true
      this.role = deepClone(scope.row)
      this.$nextTick(() => {
        this.$refs.tree.setCheckedKeys(this.role.resourceIds)
      })
    },
    handleDelete({ $index, row }) {
      this.$confirm('Confirm to remove the role?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(async() => {
          await deleteRole(row.id)
          this.rolesList.splice($index, 1)
          this.$message({
            type: 'success',
            message: 'Delete succed!'
          })
        })
        .catch(err => { console.error(err) })
    },
    async confirmRole() {
      const isEdit = this.operate === 'update'
      this.role.resourceIds = this.$refs.tree.getCheckedKeys()
      if (isEdit) {
        await updateRole(this.role.id, this.role)
      } else {
        await addRole(this.role)
      }
      await this.page()
      const { description, id, name } = this.role
      this.dialogVisible = false
      this.$notify({
        title: 'Success',
        dangerouslyUseHTMLString: true,
        message: `
            <div>Role Key: ${id}</div>
            <div>Role Name: ${name}</div>
            <div>Description: ${description}</div>
          `,
        type: 'success'
      })
    },
    // reference: src/view/layout/components/Sidebar/SidebarItem.vue
    onlyOneShowingChild(children = [], parent) {
      let onlyOneChild = null
      const showingChildren = children.filter(item => !item.hidden)

      // When there is only one child route, the child route is displayed by default
      if (showingChildren.length === 1) {
        onlyOneChild = showingChildren[0]
        onlyOneChild.path = path.resolve(parent.path, onlyOneChild.path)
        return onlyOneChild
      }

      // Show parent if there are no child route to display
      if (showingChildren.length === 0) {
        onlyOneChild = { ... parent, path: '', noShowingChildren: true }
        return onlyOneChild
      }

      return false
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .roles-table {
    margin-top: 30px;
  }
  .permission-tree {
    margin-bottom: 30px;
  }
}
</style>
