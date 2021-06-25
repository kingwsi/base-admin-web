<template>
  <page-header-wrapper title="角色资源配置">
    <a-tabs default-active-key="api" @change="tabChanelHandle" tabPosition="top" style="height: 80%">
      <a-tab-pane key="api">
        <span slot="tab">
          <a-icon type="api" />
          接口权限
        </span>
        <a-spin :spinning="loading">
          <a-card :bordered="false">
            <a-col :span="10">
              <a-col :span="10">
                <a-tree
                  :checkStrictly="true"
                  checkable
                  show-line
                  defaultExpandAll
                  v-model="apiSelectedKeys"
                  :tree-data="treeData.apiTree"
                  :replaceFields="treeFields"
                />
              </a-col>
            </a-col>
          </a-card>
        </a-spin>
      </a-tab-pane>
      <a-tab-pane key="menu">
        <span slot="tab">
          <a-icon type="menu" />
          菜单权限
        </span>
        <a-spin :spinning="loading">
          <a-card :bordered="false">
            <a-col :span="10">
              <a-tree
                v-model="menuSelectedKeys"
                :checkStrictly="true"
                checkable
                show-line
                defaultExpandAll
                :tree-data="treeData.menuTree"
                :replaceFields="treeFields"
              >
                <a-icon slot="switcherIcon" type="down" />
              </a-tree>
            </a-col>
          </a-card>
        </a-spin>
      </a-tab-pane>
      <template slot="tabBarExtraContent">
        <a-button size="small" icon="rollback" @click="()=>{this.$router.go(-1)}">返回</a-button>
        <a-divider type="vertical" />
        <a-button size="small" icon="reload" @click="loadSelectedList">重置</a-button>
        <a-divider type="vertical" />
        <a-button size="small" icon="close" @click="clearSelectedKeys">清空</a-button>
        <a-divider type="vertical" />
        <a-button size="small" type="primary" icon="check" :loading="submitLoading" @click="handleOk">提交</a-button>
      </template>
    </a-tabs>
  </page-header-wrapper>
</template>
<script>
import { GetAllResources } from '@/api/resource'
import { GetRoleById, UpdatePermissions } from '@/api/role'
import { listToTree } from '@/utils/util'
import { Tree } from 'ant-design-vue'
// 表单字段
export default {
    name: 'Permission',
    components: {
      Tree
    },
    data () {
      return {
        roleId: null,
        model: {},
        loading: false,
        submitLoading: false,
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
        }
      }
    },
    methods: {
      /* 加载所有资源 */
      loadTree () {
        GetAllResources().then(response => {
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
      handleOk () {
        this.loading = true
        this.submitLoading = true
        const form = { id: this.roleId }
        form.resourceIdList = (this.menuSelectedKeys.checked || this.menuSelectedKeys).concat(this.apiSelectedKeys.checked || this.apiSelectedKeys)
        UpdatePermissions(form).then(res => {
          this.loading = false
          this.$message.info('修改成功')
          this.submitLoading = false
          this.$router.push({
            // path: `/system/role/permission/${formData.id}`
            path: '/system/role'
          })
        })
      },
      clearSelectedKeys () {
        this.menuSelectedKeys = []
        this.apiSelectedKeys = []
      },
      tabChanelHandle (activityKey) {
        console.log(activityKey)
      }
    },
    created () {
      this.loadTree()
      this.loadSelectedList()
    }
}
</script>
