<template>
  <a-modal
    :title="model && model.id?'修改':'新增'"
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
        <a-form-model-item v-show="false" label="ID">
          <a-input v-model="model.id" disabled />
        </a-form-model-item>
        <a-form-model-item label="资源类型">
          <a-select
            v-model="model.type">
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
        <a-form-model-item label="父级id" v-if="model.type==='MENU'">
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
        <a-form-model-item label="图标" v-if="model.type!=='API'">
          <icon-selector v-model="model.icon" @change="handleIconChange"/>
        </a-form-model-item>
        <a-form-model-item :label="model.type==='MENU'?'菜单名称':model.type==='API'?'接口名称':'按钮名称'">
          <a-input v-model="model.name" />
        </a-form-model-item>
        <a-form-model-item label="地址" v-if="model.type!=='BUTTON'">
          <a-input :placeholder="model.type==='API'?'接口地址':'页面访问地址'" v-model="model.uri"/>
        </a-form-model-item>
        <a-form-model-item label="组件" v-if="model.type==='MENU'">
          <a-input placeholder="组件路径" v-model="model.component"/>
        </a-form-model-item>
        <a-form-model-item label="排序">
          <a-input v-model="model.sort"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import { TreeSelect } from 'ant-design-vue'
import { GetAllResources } from '@/api/resource/index'
import IconSelector from '@/components/IconSelector'

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
      default: () => {
        return {
          type: 'API'
        }
      }
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
      selectedIcon: null,
      rules: {
        name: [{ required: true, message: '请输入至少2个字符的名称！', trigger: 'change' }],
        uri: [{ required: true, min: 2, message: '请输入至少2个字符的名称！', trigger: 'change' }],
        component: [{ required: true, message: '请输入资源地址！' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    // fields.forEach(v => this.form.getFieldDecorator(v))
    this.loadTreeData()
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      // this.model && this.form.setFieldsValue(pick(this.model, fields))
      // this.resourceType = this.model && this.model.type || this.resourceType
      // this.selectedIcon = this.model.icon
      // 生成tree
      // this.generatorTree(this.model.type)
    })
  },
  methods: {
    loadTreeData () {
      GetAllResources().then(res => {
                this.resourceList = res.data
                this.treeData = [{ 'id': '-1', 'name': '根目录' }]
                this.listToTree(this.resourceList.filter(item => item.type === 'MENU'), this.treeData, '-1')
            }).catch(err => {
                console.log(`load user err: ${err.message}`)
            })
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
    handleIconChange (icon) {
      this.$message.info(<span>选中图标 <code>{icon}</code></span>)
    }
  }
}
</script>
