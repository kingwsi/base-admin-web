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
        <a-form-model-item v-show="model && model.id" label="ID">
          <a-input :disabled="model && model.id" v-model="model.id"/>
        </a-form-model-item>
        <a-form-model-item label="值" prop="value">
          <a-input v-model="model.value" placeholder="请输入值" />
        </a-form-model-item>
        <a-form-model-item label="编码" prop="code">
          <a-input v-model="model.code" placeholder="请输入编码" />
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-input v-model="model.description" placeholder="请输入描述" />
        </a-form-model-item>
        <a-form-model-item label="分组CODE" prop="groupCode">
          <a-input v-model="model.groupCode" placeholder="请输入分组CODE" />
        </a-form-model-item>
        <a-form-model-item label="排序" prop="sort">
          <a-input v-model="model.sort" placeholder="请输入排序" />
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
        value: [{ required: true, message: '请输入值', trigger: 'change' }],
        code: [{ required: true, message: '请输入编码', trigger: 'change' }],
        description: [{ required: true, message: '请输入描述', trigger: 'change' }],
        groupCode: [{ required: true, message: '请输入分组CODE', trigger: 'change' }],
        sort: [{ required: true, message: '请输入排序', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
