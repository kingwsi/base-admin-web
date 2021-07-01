<template>
  <a-modal
    title="修改"
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
        <a-form-model-item v-show="false" label="ID">
          <a-input v-model="model.id" disabled />
        </a-form-model-item>
        <a-form-model-item label="头像">
          <a-upload
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :custom-request="customUpload"
            :before-upload="beforeUpload"
            @change="handleChange"
          >
            <img v-if="model.avatar" :src="model.avatar" alt="avatar" style="height:150px" />
            <div v-else>
              <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">
                上传
              </div>
            </div>
          </a-upload>
        </a-form-model-item>
        <a-form-model-item label="用户名" prop="username">
          <a-input v-model="model.username" :disabled="true"/>
        </a-form-model-item>
        <a-form-model-item v-if="!model || !model.id" label="密码" placeholder="请输入密码，8-16位,字母数字混合" prop="password">
          <a-input v-model="model.password"/>
        </a-form-model-item>
        <a-form-model-item label="昵称" prop="nickname">
          <a-input v-model="model.nickname"/>
        </a-form-model-item>
        <a-form-model-item label="全称" prop="fullName">
          <a-input v-model="model.fullName"/>
        </a-form-model-item>
        <a-form-model-item label="介绍" prop="introduction">
          <a-input v-model="model.introduction" type="textarea"/>
        </a-form-model-item>
        <a-form-model-item label="旧密码" prop="oldPassword">
          <a-input v-model="model.oldPassword"/>
        </a-form-model-item>
        <a-form-model-item label="新密码" prop="password">
          <a-input v-model="model.password"/>
        </a-form-model-item>
        <a-form-model-item label="重复密码" prop="repeatPassword">
          <a-input v-model="model.repeatPassword"/>
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script>
import { UploadAvatar } from '@/api/fileResource'

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
      roleList: [],
      uploadLoading: false,
      rules: {
        fullName: [{ required: true, message: '请输入全称', trigger: 'change' }],
        username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
        nickname: [{ required: true, message: '请输入昵称', trigger: 'change' }],
        oldPassword: [{ required: true, message: '请输入密码，8-16位,字母数字混合', trigger: 'change' }]
      }
    }
  },
  created () {
    console.log('custom modal created')
  },
  methods: {
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
          this.model.avatar = response.data
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
