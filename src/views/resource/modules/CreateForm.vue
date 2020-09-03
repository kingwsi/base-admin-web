<template>
  <a-modal
    :title="model && model.id ? '新增资源':'更新资源'"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form-model
        ref="form"
        :model="model"
        :rules="rules"
        v-bind="formLayout">
        <!-- 检查是否有 id，有是修改。其他是新增，新增不显示主键ID -->
        <a-form-model-item v-if="model && model.id" label="主键ID">
          <a-input v-model="model.id" disabled />
        </a-form-model-item>
        <a-form-model-item label="资源类型">
          <a-select
            v-model="model.type"
            @change="typeSelectChange">
            <a-select-option value="MENU">
              菜单
            </a-select-option>
            <a-select-option value="API">
              API
            </a-select-option>
            <a-select-option value="BUTTON">
              按钮
            </a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="父级id">
          <a-tree-select
            show-search
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            :tree-data="treeData"
            :replaceFields="treeFields"
            v-model="model.parentId"
            placeholder="选择上级目录"
            allow-clear
            tree-default-expand-all
          >
          </a-tree-select>
        </a-form-model-item>
        <a-form-model-item label="资源名称">
          <a-input v-model="model.name"/>
        </a-form-model-item>
        <a-form-model-item label="地址" v-if="model.type==='MENU'">
          <a-input v-model="model.uri"/>
        </a-form-model-item>
        <a-form-model-item label="组件" v-if="model.type==='MENU'">
          <a-input v-model="model.component"/>
        </a-form-model-item>
        <a-form-model-item label="隐藏菜单" v-if="model.type==='MENU'">
          <a-switch
            :checked="model.remark=='hidden'"
            @change="onHideChange"/>
        </a-form-model-item>
        <a-form-model-item label="排序">
          <a-input v-model="model.sort"/>
        </a-form-model-item>
        <a-form-model-item label="图标" v-if="model.type!=='API'">
          <icon-selector v-model="model.icon"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import { TreeSelect } from 'ant-design-vue'
import { GetAllResources } from '@/api/resource/index'
import IconSelector from '@/components/IconSelector'
// import { listToTree } from '@/components/Tree/TreeUtils'

// 表单字段
export default {
  components: {
    TreeSelect,
    IconSelector
  },
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    loading: {
      type: Boolean,
      default: () => false
    },
    model: {
      type: Object,
      default: () => null
    }
  },
  data () {
    this.formLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      }
    }
    return {
      form: this.$form.createForm(this),
      resourceList: [],
      treeData: [],
      treeFields: {
        key: 'id',
        title: 'name',
        value: 'id'
      },
      rules: {
          name: [{ required: true, min: 2, message: '请输入至少2个字符的名称！' }],
          component: [{ required: true, message: '请输入资源地址！' }],
          uri: [{ required: true, message: '请输入资源地址！' }],
          type: [{ required: true, message: '请输入至少2个字符的名称！' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
    this.generatorTree(this.model.type)
  },
  methods: {
    loadTreeData () {
      GetAllResources().then(res => {
                this.resourceList = res.data
                this.generatorTree(this.resourceType)
            }).catch(err => {
                console.log(`load user err: ${err.message}`)
            })
    },
    generatorTree (type) {
      this.treeData = [{ 'id': '-1', 'name': '根目录' }]
      console.log('resource filter, type -> ', type)
      this.listToTree(this.resourceList.filter(item => item.type === type), this.treeData, '-1')
    },
    listToTree (list, tree, parentId) {
      list.forEach(item => {
        // 禁用当前节点
        if (this.model && this.model.id === item.id) {
          item.disabled = true
        } else {
          item.disabled = false
        }
        // 判断是否为父级
        if (item.parentId === parentId) {
          const child = {
            ...item,
            children: []
          }
          // 迭代 list
          this.listToTree(list, child.children, item.id)
          // 删掉不存在 children 值的属性
          if (child.children.length <= 0) {
            delete child.children
          }
          // 加入到树中
          tree.push(child)
        }
      })
    },
    typeSelectChange () {
      // 重新构建树
      this.generatorTree(this.model.type === 'BUTTON' ? 'MENU' : this.model.type)
    },
    resetTree () {
      console.log('重置treeData')
      this.model.type = 'MENU'
      this.generatorTree(this.model.type)
    },
    handleIconChange (icon) {
      this.selectedIcon = icon
      this.$message.info(<span>选中图标 <code>{icon}</code></span>)
    },
    onHideChange (checked) {
      const result = checked ? 'hidden' : null
      this.model.remark = result
    }
  }
}
</script>
