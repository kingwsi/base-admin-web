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
          <a-input v-if="model.id" disabled />
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input v-model="model.description" placeholder="请输入描述" />
        </a-form-model-item>
        <a-form-model-item label="地址" prop="path">
          <a-input v-model="model.path" placeholder="请输入地址" />
        </a-form-model-item>
        <a-form-model-item label="请求方式" prop="methods">
          <a-input v-model="model.methods" placeholder="请求方式，分号隔开" />
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
        description: [{ required: true, message: '请输入描述', trigger: 'change' }],
        methods: [{ required: true, message: '请输入请求方式', trigger: 'change' }],
        path: [{ required: true, message: '请输入地址', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
