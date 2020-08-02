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
            <a-select-option value="ROUTE">
              菜单
            </a-select-option>
            <a-select-option value="API">
              API
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="父级id">
          <a-select :options="options" v-decorator="['parentId',{rules: [{required: true}]}]"/>
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
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'

// 表单字段
const fields = ['id', 'uri', 'type', 'name', 'component', 'parentId']

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
      options: [
        {
          value: 'zhejiang',
          label: 'Zhejiang'
        },
        {
          value: 'jiangsu',
          label: 'Jiangsu'
        }
      ]
    }
  },
  created () {
    console.log('custom modal created')

    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      this.model && this.form.setFieldsValue(pick(this.model, fields))
    })
  }
}
</script>
