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
        <a-form-model-item label="真实姓名">
          <a-input v-model="realName" placeholder="请输入真实姓名" />
        </a-form-model-item>
        <a-form-model-item label="昵称">
          <a-input v-model="nickName" placeholder="请输入昵称" />
        </a-form-model-item>
        <a-form-model-item label="性别">
          <a-input v-model="gender" placeholder="请输入性别" />
        </a-form-model-item>
        <a-form-model-item label="手机号">
          <a-input v-model="mobile" placeholder="请输入手机号" />
        </a-form-model-item>
        <a-form-model-item label="邮箱">
          <a-input v-model="email" placeholder="请输入邮箱" />
        </a-form-model-item>
        <a-form-model-item label="头像">
          <a-input v-model="avatar" placeholder="请输入头像" />
        </a-form-model-item>
        <a-form-model-item label="介绍">
          <a-input v-model="introduce" placeholder="请输入介绍" />
        </a-form-model-item>
        <a-form-model-item label="最后登录时间">
          <a-input v-model="lastLoginTime" placeholder="请输入最后登录时间" />
        </a-form-model-item>
        <a-form-model-item label="最后登录ip">
          <a-input v-model="lastLoginIp" placeholder="请输入最后登录ip" />
        </a-form-model-item>
        <a-form-model-item label="">
          <a-input v-model="password" placeholder="请输入" />
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
        realName: [{ required: true, message: '请输入真实姓名', trigger: 'change' }],
        nickName: [{ required: true, message: '请输入昵称', trigger: 'change' }],
        gender: [{ required: true, message: '请输入性别', trigger: 'change' }],
        mobile: [{ required: true, message: '请输入手机号', trigger: 'change' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'change' }],
        avatar: [{ required: true, message: '请输入头像', trigger: 'change' }],
        introduce: [{ required: true, message: '请输入介绍', trigger: 'change' }],
        lastLoginTime: [{ required: true, message: '请输入最后登录时间', trigger: 'change' }],
        lastLoginIp: [{ required: true, message: '请输入最后登录ip', trigger: 'change' }],
        password: [{ required: true, message: '请输入', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
