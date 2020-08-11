<template>
  <page-header-wrapper :title="false" content="角色资源配置">
    <a-row>
      <a-col :span="12">
        <a-tree
          v-model="roleInfo.menuResources"
          :defaultExpandAll="true"
          checkable
          :tree-data="treeData.menuTree"
          :replaceFields="treeFields"
          @select="onSelect"
        />
      </a-col>
      <a-col :span="12">
        <a-tree
          v-model="roleInfo.apiResources"
          checkable
          :tree-data="treeData.apiTree"
          :replaceFields="treeFields"
          @select="onSelect"
        />
      </a-col>
    </a-row>
  </page-header-wrapper>
</template>
<script>
import { GetAllResources } from '@/api/resource/index'
import { GetRoleById } from '@/api/role'
import { listToTree } from '@/utils/util'
import { Tree } from 'ant-design-vue'

export default {
    name: 'EditResource',
    components: {
      Tree
    },
    data () {
      return {
        roleId: null,
        roleInfo: {
          menuResources: [],
          apiResources: [],
          btnResources: []
        },
        resourceList: [],
        expandedKeys: ['-1'],
        autoExpandParent: true,
        selectedKeys: [],
        treeData: {
          apiTree: [],
          btnTree: [],
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
          this.resourceList = response.data
          this.treeData.menuTree = [{ 'id': '-1', 'name': '根目录' }]
          this.treeData.apiTree = [{ 'id': '-1', 'name': '根目录' }]
          this.treeData.btnTree = [{ 'id': '-1', 'name': '根目录' }]
          // response.data.forEach(element =>{
          //   switch (element.type) {
          //     case 'MENU':
          //       this.treeData.menuTree.push(element)
          //       break
          //     case 'API':
          //       this.treeData.apiTree.push(element)
          //       break
          //     case 'BUTTON':
          //       this.treeData.btnTree.push(element)
          //       break
          //   }
          // })
          listToTree(response.data.filter(res => res.type === 'MENU'), this.treeData.menuTree, '-1')
          listToTree(response.data.filter(res => res.type === 'API'), this.treeData.apiTree, '-1')
          listToTree(response.data.filter(res => res.type === 'BUTTON'), this.treeData.btnTree, '-1')
          console.log(this.treeData)
        })
      },
      /* 加载当前角色拥有的资源 */
      loadSelectedList () {
        this.roleId = this.$route.params.id
        GetRoleById(this.roleId).then(resp => {
          this.roleInfo = resp.data
          this.getResourceByType()
        })
      },
      getResourceByType () {
        if (this.roleInfo && this.roleInfo.resourceList) {
          this.roleInfo.menuResources = []
          this.roleInfo.apiResources = []
          this.roleInfo.btnResources = []
          this.roleInfo.resourceList.forEach(element => {
            switch (element.type) {
              case 'MENU':
                this.roleInfo.menuResources.push(element.id)
                break
              case 'API':
                this.roleInfo.apiResources.push(element.id)
                break
              case 'BUTTON':
                this.roleInfo.btnResources.push(element.id)
                break
            }
          })
          console.log(this.roleInfo)
        }
      },
      onSelect (selectedKeys, info) {
        console.log('onSelect', info)
        this.selectedKeys = selectedKeys
      }
    },
    created () {
      this.loadTree()
      this.loadSelectedList()
    }
}
</script>
