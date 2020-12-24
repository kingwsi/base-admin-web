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
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id，有是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id" label="主键ID">
          <a-input v-decorator="['id']" disabled />
        </a-form-item>
        <a-form-item label="资源类型">
          <a-select
            v-decorator="[
              'type',
              { rules: [{ required: true, message: '请输入至少2个字符的名称！' }], initialValue: 'MENU' },
            ]"
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
        </a-form-item>
        <a-form-item label="父级id">
          <a-tree-select
            show-search
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            :tree-data="treeData"
            :replaceFields="treeFields"
            v-decorator="['parentId',{rules: [{required: true}]}]"
            placeholder="选择上级目录"
            allow-clear
            tree-default-expand-all
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item label="图标" v-if="resourceType!=='API'">
          <icon-selector v-model="selectedIcon" @change="handleIconChange"/>
        </a-form-item>
        <a-form-item label="资源名称">
          <a-input v-decorator="['name', {rules: [{required: true, min: 2, message: '请输入至少2个字符的名称！'}]}]" />
        </a-form-item>
        <a-form-item label="地址">
          <a-input placeholder="访问地址" v-decorator="['uri', {rules: [{required: true, message: '请输入资源地址！'}]}]"/>
        </a-form-item>
        <a-form-item label="组件" v-if="resourceType==='MENU'">
          <a-input placeholder="组件路径" v-decorator="['component', {rules: [{required: true, message: '请输入资源地址！'}]}]"/>
        </a-form-item>
        <a-form-item label="排序">
          <a-input v-decorator="['sort', {}]"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { TreeSelect } from 'ant-design-vue'
import { GetAllResources } from '@/api/resource/index'
import IconSelector from '@/components/IconSelector'
// import { listToTree } from '@/components/Tree/TreeUtils'

// 表单字段
const fields = ['id', 'uri', 'type', 'name', 'component', 'parentId', 'sort']

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
      resourceType: 'MENU',
      resourceList: [],
      treeData: [],
      treeFields: {
        key: 'id',
        title: 'name',
        value: 'id'
      },
      selectedIcon: null
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))
    this.loadTreeData()
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
      this.resourceType = this.model && this.model.type || this.resourceType
      this.selectedIcon = this.model.icon
      // 生成tree
      this.generatorTree(this.resourceType)
    })
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
    typeSelectChange (ele) {
      this.resourceType = ele
      // 重新构建树
      this.generatorTree(this.resourceType)
    },
    resetTree () {
      console.log('重置treeData')
      this.resourceType = 'MENU'
      this.generatorTree(this.resourceType)
    },
    handleIconChange (icon) {
      this.selectedIcon = icon
      this.$message.info(<span>选中图标 <code>{icon}</code></span>)
    }
  }
}
</script>
