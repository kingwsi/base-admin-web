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
        <a-form-model-item label="真实姓名" prop="realName">
          <a-input v-model="model.realName" placeholder="请输入真实姓名" />
        </a-form-model-item>
        <a-form-model-item label="昵称" prop="nickName">
          <a-input v-model="model.nickName" placeholder="请输入昵称" />
        </a-form-model-item>
        <a-form-model-item label="性别" prop="gender">
          <a-input v-model="model.gender" placeholder="请输入性别" />
        </a-form-model-item>
        <a-form-model-item label="手机号" prop="mobile">
          <a-input v-model="model.mobile" placeholder="请输入手机号" />
        </a-form-model-item>
        <a-form-model-item label="邮箱" prop="email">
          <a-input v-model="model.email" placeholder="请输入邮箱" />
        </a-form-model-item>
        <a-form-model-item label="头像" prop="avatar">
          <a-input v-model="model.avatar" placeholder="请输入头像" />
        </a-form-model-item>
        <a-form-model-item label="介绍" prop="introduce">
          <a-input v-model="model.introduce" placeholder="请输入介绍" />
        </a-form-model-item>
        <a-form-model-item label="最后登录时间" prop="lastLoginTime">
          <a-input v-model="model.lastLoginTime" placeholder="请输入最后登录时间" />
        </a-form-model-item>
        <a-form-model-item label="密码" prop="password">
          <a-input v-model="model.password" placeholder="请输入密码" />
        </a-form-model-item>
        <a-form-model-item label="最后登录ip" prop="lastLoginIp">
          <a-input v-model="model.lastLoginIp" placeholder="请输入最后登录ip" />
        </a-form-model-item>
        <a-form-model-item label="账户状态 1 正常 0 停用" prop="accountStatus">
          <a-input v-model="model.accountStatus" placeholder="请输入账户状态 1 正常 0 停用" />
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
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
        lastLoginIp: [{ required: true, message: '请输入最后登录ip', trigger: 'change' }],
        accountStatus: [{ required: true, message: '请输入账户状态 1 正常 0 停用', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  }
}
</script>
