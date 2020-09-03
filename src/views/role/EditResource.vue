<template>
  <page-header-wrapper title="角色资源配置">
    <a-spin :spinning="loading">
      <a-card :bordered="false">
        <a-row>
          <a-form-model
            ref="form"
            :model="model"
            :rules="rules"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 20 }">
            <a-form-model-item v-show="roleId" label="主键ID" :wrapper-col="{ span: 12 }">
              <a-input v-model="model.id" disabled />
            </a-form-model-item>
            <a-form-model-item label="名称" :wrapper-col="{ span: 12 }">
              <a-input v-model="model.name" />
            </a-form-model-item>
            <a-form-model-item label="描述" :wrapper-col="{ span: 12 }">
              <a-input v-model="model.description" type="textarea"/>
            </a-form-model-item>
            <a-form-model-item label="菜单权限">
              <a-row>
                <a-col :span="10">
                  <a-tree
                    v-model="menuSelectedKeys"
                    :default-expand-all="true"
                    checkable
                    show-line
                    :tree-data="treeData.menuTree"
                    :replaceFields="treeFields"
                    @check="onMenuCheck"
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
                    @check="onApiCheck"
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
        roleInfo: null,
        loading: false,
        rules: {
          name: [{ required: true, message: '请输入角色名称', trigger: 'change' }]
        },
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
        test: []
      }
    },
    methods: {
      /* 加载所有资源 */
      loadTree () {
        GetAllResources().then(response => {
          this.resourceList = response.data
          this.treeData.menuTree = []
          this.treeData.apiTree = []
          // listToTree(response.data.filter(res => res.type === 'MENU' || res.type === 'BUTTON'), this.treeData.menuTree, '-1')
          this.treeData.apiTree = response.data.filter(res => res.type === 'API')
          this.loadResTree(response.data.filter(res => res.type === 'MENU' || res.type === 'BUTTON'), this.treeData.menuTree, '-1')
          this.loadSelectedList()
        })
      },
      /* 加载当前角色信息以及拥有的资源 */
      loadSelectedList () {
        this.loading = true
        this.roleId = this.$route.params.id
          GetRoleById(this.roleId).then(resp => {
            this.roleInfo = resp.data
            // 为form表单填充值
            this.model = this.roleInfo
            // 选中复选框处理
            if (resp.data && resp.data.resourceList) {
              this.menuSelectedKeys = []
              this.apiSelectedKeys = []
              this.roleInfo.resourceList.forEach(element => {
                if (element.type === 'MENU') {
                  this.menuSelectedKeys.push(element.id)
                } else {
                  this.apiSelectedKeys.push(element.id)
                }
              })
              this.copyChecked(this.menuSelectedKeys)
          }
          this.loading = false
        })
      },
      onMenuCheck (checkedKeys, info) {
        this.menuSelectedKeys = checkedKeys
        // 半选节点处理
        this.parentIds = info.halfCheckedKeys
      },
      onApiCheck (selectedKeys, info) {
        this.apiSelectedKeys = selectedKeys
      },
      handleOk () {
        this.loading = true
        this.$refs.form.validate(valid => {
          if (valid) {
            this.model.resourceIdList = this.menuSelectedKeys.concat(this.apiSelectedKeys).concat(this.parentIds)
            if (this.model.id) {
              // 修改
              UpdateById(this.model).then(res => {
                this.loading = false
                this.$message.info('修改成功')
                this.$router.push({
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
            return false
          }
        })
      },
      clearSelectedKeys () {
        this.menuSelectedKeys = []
        this.apiSelectedKeys = []
      },
      /**
       * 反选问题处理
       * 找出已选中keys，对比所有childKeys
       */
      copyChecked (arr) {
        this.menuSelectedKeys = []
        arr.forEach(item => {
          if (this.test.indexOf(item) > -1) {
            this.menuSelectedKeys.push(item)
          }
        })
      },
      loadResTree (list, tree, parentId) {
        list.forEach(item => {
          // 判断是否为父级
          if (item.parentId === parentId) {
            const child = {
              ...item,
              children: []
            }
            // 迭代 list
            this.loadResTree(list, child.children, item.id)
            // 删掉不存在 children 值的属性
            if (child.children.length <= 0) {
              delete child.children
              // 收集没有children的节点
              this.test.push(item.id)
            }
            // 加入到树中
            tree.push(child)
          }
        })
      }
    },
    created () {
      this.loadTree()
    }
}
</script>
