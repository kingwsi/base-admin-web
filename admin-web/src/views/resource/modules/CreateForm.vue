<template>
  <a-modal
    title="新增资源"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="主键ID">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="资源类型">
          <a-select v-decorator="['type',{rules: [{required: true, message: '请输入至少2个字符的名称！'}]}]">
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
            placeholder="Please select"
            allow-clear
            tree-default-expand-all
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item label="资源名称">
          <a-input v-decorator="['name', {rules: [{required: true, min: 2, message: '请输入至少2个字符的名称！'}]}]" />
        </a-form-item>
        <a-form-item label="地址">
          <a-input v-decorator="['uri', {rules: [{required: true, message: '请输入资源地址！'}]}]"/>
        </a-form-item>
        <a-form-item label="组件">
          <a-input v-decorator="['component', {rules: [{required: true, message: '请输入资源地址！'}]}]"/>
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
import { getList } from '@/api/resource/index'
// import { listToTree } from '@/components/Tree/TreeUtils'

// 表单字段
const fields = ['id', 'uri', 'type', 'name', 'component', 'parentId', 'sort']

export default {
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
  components: {
    TreeSelect
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
      formData: null
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
      // 生成tree
      this.generatorTree()
    })
  },
  methods: {
    loadTreeData () {
      getList({ 'type': 'MENU' }).then(res => {
                this.resourceList = res.data
                this.generatorTree()
            }).catch(err => {
                console.log(`load user err: ${err.message}`)
            })
    },
    generatorTree () {
      this.treeData = []
      this.listToTree(this.resourceList, this.treeData, '-1')
      console.log('加载tree')
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
    }
  }
}
</script>
