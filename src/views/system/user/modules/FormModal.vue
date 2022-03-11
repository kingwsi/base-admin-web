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
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-model-item v-show="model && model.id" label="ID">
          <a-input v-model="model.id" disabled />
        </a-form-model-item>
        <a-form-model-item label="头像">
          <c-upload
            :files.sync="model.avatar"
            :max="1"
          />
        </a-form-model-item>
        <a-form-model-item label="用户名" prop="username">
          <a-input v-model="model.username" :disabled="model && model.id?true:false" placeholder="请输入用户名，设置后将不可更改" />
        </a-form-model-item>
        <a-form-model-item v-if="!model || !model.id" label="密码" placeholder="请输入密码，6-20位" prop="password">
          <a-input v-model="model.password"/>
        </a-form-model-item>
        <a-form-model-item label="昵称" prop="nickname">
          <a-input v-model="model.nickname"/>
        </a-form-model-item>
        <a-form-model-item label="全称" prop="fullName">
          <a-input v-model="model.fullName"/>
        </a-form-model-item>
        <a-form-model-item label="角色" prop="roleIds">
          <a-select
            mode="multiple"
            v-model="model.roleIds"
            style="width: 100%"
            placeholder="请选择角色"
          >
            <a-select-option v-for="role in roleList" :key="role.id">{{ role.description }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="介绍" prop="introduction">
          <a-input v-model="model.introduction" type="textarea"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import { GetRoleList } from '@/api/role'
import SingleUpload from '@/components/upload/SingleUpload'
import CUpload from '@/components/upload/CUpload'

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
    SingleUpload,
    CUpload
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
      roleList: [],
      uploadLoading: false,
      rules: {
        fullName: [{ required: true, message: '请输入全称', trigger: 'change' }],
        username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
        roleIds: [{ required: true, message: '请选择角色', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
    // 加载roles
    this.loadRoles()
  },
  methods: {
    loadRoles () {
      GetRoleList().then(res => {
        this.roleList = res.data
      })
    },
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.uploadLoading = true
      }
    }
  }
}
</script>
