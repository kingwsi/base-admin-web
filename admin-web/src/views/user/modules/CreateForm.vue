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
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id" label="ID">
          <a-input v-decorator="['id', {}]" disabled />
        </a-form-item>
        <a-form-item label="头像">
          <a-upload
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :custom-request="customUpload"
            :before-upload="beforeUpload"
            @change="handleChange"
          >
            <img v-if="avatarUrl" :src="avatarUrl" alt="avatar" style="height:150px" />
            <div v-else>
              <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">
                上传
              </div>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item label="用户名">
          <a-input v-decorator="['username', {}]" :disabled="model && model.id?true:false" placeholder="请输入用户名，设置后将不可更改" />
        </a-form-item>
        <a-form-item v-show="!model || !model.id" label="密码">
          <a-input v-decorator="['password', {}]"/>
        </a-form-item>
        <a-form-item label="全称">
          <a-input v-decorator="['fullName']"/>
        </a-form-item>
        <a-form-item label="介绍">
          <a-input v-decorator="['introduction']"/>
        </a-form-item>
        <a-form-item label="角色">
          <a-select
            mode="multiple"
            v-decorator="['role']"
            style="width: 100%"
            placeholder="请选择角色"
          >
            <a-select-option v-for="role in roleList" :key="role.id">{{ role.name }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
    <a-form-model :model="tmpForm" v-bind="formLayout">
      <a-form-model-item label="name">
        <a-input v-model="tmpForm.username" />
      </a-form-model-item>
      <a-form-model-item label="age">
        <a-input v-model="tmpForm.password" />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'
import { UploadAvatar } from '@/api/fileResource'
import { GetRoleList } from '@/api/role'

// 表单字段
const fields = ['id', 'username', 'avatar', 'fullName', 'introduction', 'role']

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
      roleList: [],
      uploadLoading: false,
      avatarUrl: '',
      roleSelectorProps: {
        key: 'id',
        value: 'id',
        label: 'name'
      },
      tmpForm: {}
    }
  },
  created () {
    console.log('custom modal created')
    // 加载roles
    this.loadRoles()
    // 防止表单未注册
    fields.forEach(v => this.form.getFieldDecorator(v))
    // 当 model 发生改变时，为表单设置值
    this.$watch('model', () => {
      fields.forEach(v => this.form.getFieldDecorator(v))
      this.model && this.form.setFieldsValue(pick(this.model, fields))
      this.tmpForm = this.model
      if (this.model && this.model.avatar) {
        this.avatarUrl = this.model.avatar
      }
    })
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
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!')
      }
      return isJpgOrPng && isLt2M
    },
    customUpload (data) {
      this.uploadLoading = true
      const formData = new FormData()
      formData.append('file', data.file)
      UploadAvatar(formData).then(response => {
        if (response.code === 200) {
          this.avatarUrl = response.data
          this.uploadLoading = false
        }
      }).catch((err) => {
        console.log(err)
        this.$message.error('上传失败!')
        this.uploadLoading = false
      })
    }
  }
}
</script>
