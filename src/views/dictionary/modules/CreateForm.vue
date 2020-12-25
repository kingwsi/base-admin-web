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
        <a-form-model-item label="字典名称">
          <a-input v-model="model.name" />
        </a-form-model-item>
        <a-form-model-item label="字典编码">
          <a-input v-model="model.code"/>
        </a-form-model-item>
        <a-form-model-item label="描述">
          <a-input v-model="model.description"/>
        </a-form-model-item>
        <a-form-model-item label="排序">
          <a-input type="number" v-model="model.sort"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
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
      form: {},
      rules: {
        code: [{ required: true, message: '请输入编码', trigger: 'change' }],
        name: [{ required: true, message: '请输入名称', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
    // 防止表单未注册
    // fields.forEach(v => this.form.getFieldDecorator(v))

    // 当 model 发生改变时，为表单设置值
    // this.$watch('model', () => {
    //   console.log(this.model)
    // })
  }
}
</script>
