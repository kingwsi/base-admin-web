<template>
  <page-header-wrapper title="角色资源配置">
    <a-spin :spinning="loading">
      <a-card :bordered="false">
        <a-row>
          <a-form-model
            ref="form"
            :model="model"
            :rules="rules"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }">
            <a-form-model-item v-show="roleId" label="ID">
              <a-input v-model="model.id" disabled />
            </a-form-model-item>
            <a-form-model-item label="角色名称">
              <a-input v-model="model.name" />
            </a-form-model-item>
            <a-form-model-item label="角色描述">
              <a-input v-model="model.description" />
            </a-form-model-item>
            <a-form-model-item label="菜单权限">
              <a-row>
                <a-col :span="10">
                  <a-tree
                    v-model="menuSelectedKeys"
                    :checkStrictly="true"
                    checkable
                    show-line
                    :defaultExpandAll="true"
                    :tree-data="treeData.menuTree"
                    :replaceFields="treeFields"
                  >
                    <a-icon slot="switcherIcon" type="down" />
                  </a-tree>
                </a-col>
                <a-col :span="10">
                  <a-tree
                    checkable
                    show-line
                    :checkedKeys="apiSelectedKeys"
                    :tree-data="treeData.apiTree"
                    :replaceFields="treeFields"
                  />
                </a-col>
              </a-row>
            </a-form-model-item>
          </a-form-model>
        </a-row>
      </a-card>
    </a-spin>
    <footer-tool-bar>
      <a-button icon="reload" @click="loadSelectedList">重置</a-button>
      <a-divider type="vertical" />
      <a-button icon="close" @click="clearSelectedKeys">清空</a-button>
      <a-divider type="vertical" />
      <a-button type="primary" icon="check" @click="handleOk">提交</a-button>
    </footer-tool-bar>
  </page-header-wrapper>
</template>
<script>
import { GetAllResources } from '@/api/resource/index'
import { GetRoleById, UpdateById, CreateRole } from '@/api/role'
import { listToTree } from '@/utils/util'
import { Tree } from 'ant-design-vue'
import FooterToolBar from '@/components/FooterToolbar'
// 表单字段
export default {
    name: 'EditResource',
    components: {
      Tree,
      FooterToolBar
    },
    data () {
      return {
        roleId: null,
        model: {},
        loading: false,
        resourceList: [],
        menuSelectedKeys: [],
        apiSelectedKeys: [],
        treeData: {
          apiTree: [],
          menuTree: []
        },
        treeFields: {
          key: 'id',
          title: 'name',
          value: 'id'
        },
        parentIds: [],
        rules: {
          name: [{ required: true, message: '请输入角色名称', trigger: 'change' }]
        }
      }
    },
    methods: {
      /* 加载所有资源 */
      loadTree () {
        GetAllResources().then(response => {
          this.resourceList = response.data
          this.treeData.menuTree = []
          this.treeData.apiTree = []
          listToTree(response.data.filter(res => res.type === 'MENU' || res.type === 'BUTTON'), this.treeData.menuTree, '-1')
          listToTree(response.data.filter(res => res.type === 'API'), this.treeData.apiTree, '-1')
        })
      },
      /* 加载当前角色信息以及拥有的资源 */
      loadSelectedList () {
        this.loading = true
        this.roleId = this.$route.params.id
          GetRoleById(this.roleId).then(resp => {
            this.model = resp.data
            // 选中复选框处理
            if (resp.data && resp.data.resourceList) {
              this.menuSelectedKeys = []
              this.apiSelectedKeys = []
              resp.data.resourceList.forEach(element => {
                if (element.type === 'MENU') {
                  this.menuSelectedKeys.push(element.id)
                } else {
                  this.apiSelectedKeys.push(element.id)
                }
              })
          }
          this.loading = false
        })
      },
      onMenuCheck (checkedKeys, info) {
        this.menuSelectedKeys = checkedKeys
        console.log(info.halfCheckedKeys)
      },
      onApiCheck (selectedKeys, info) {
        this.apiSelectedKeys = selectedKeys
      },
      handleOk () {
        this.loading = true
        const form = this.$refs.form
        form.validate(valid => {
          if (valid) {
            const checkedArrays = (this.menuSelectedKeys.checked || this.menuSelectedKeys).concat(this.apiSelectedKeys.checked || this.apiSelectedKeys)
            console.log(checkedArrays)
            this.model.resourceIdList = checkedArrays
            if (this.model.id) {
              // 修改 e.g.
              UpdateById(this.model).then(res => {
                this.loading = false
                this.$message.info('修改成功')
                this.$router.push({
                  // path: `/system/role/permission/${formData.id}`
                  path: '/system/role'
                })
              }).catch((err) => {
                console.log(`form update error:->${err}`)
                this.confirmLoading = false
              })
            } else {
              // 新增
              CreateRole(this.model).then(res => {
                this.loading = false
                this.$message.info('新增成功')
                this.$router.push({
                  path: '/system/role'
                })
              }).catch((err) => {
                console.log(`form update error:->${err}`)
                this.loading = false
              })
            }
          } else {
            this.loading = false
          }
        })
      },
      clearSelectedKeys () {
        this.menuSelectedKeys = []
        this.apiSelectedKeys = []
      },
      /**
       *
       */
      handleTreeChecked () {
        const parentMap = new Map()
        this.resourceList.forEach(item => {
          const child = []
          this.resourceList.forEach(o2 => {
            if (o2.parentId === item.id) {
              child.push(o2.id)
            }
          })
          if (child.length > 0) {
            parentMap.set(item.id, child)
          }
        })
        console.log(parentMap)
      }
    },
    created () {
      this.loadTree()
      this.loadSelectedList()
    }
}
</script>
