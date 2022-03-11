
<template>
  <div class="clearfix">
    <a-upload
      name="avatar"
      list-type="picture-card"
      :show-upload-list="false"
      :custom-request="customUpload"
      :before-upload="beforeUpload"
      :remove="handleRemove"
      @change="handleChange"
    >
      <img v-if="file" :src="file" alt="avatar" :style="'height: '+height+'px'" />
      <div v-else>
        <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
        <div class="ant-upload-text">上传</div>
      </div>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>
<script>
import { SingleUpload } from '@/api/fileResource'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = (error) => reject(error)
  })
}

export default {
  props: {
    file: {
      type: String,
      require: true,
      default: () => null
    },
    height: {
        type: Number,
        require: false,
        default: () => 150
    }
  },
  data () {
    return {
      previewVisible: false,
      previewImage: '',
      uploadLoading: false
    }
  },
  methods: {
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    handleChange ({ fileList }) {
      this.fileList = fileList
    },
    async handleRemove (file) {
      this.$emit('update:file', null)
      return true
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
      SingleUpload(formData)
        .then((response) => {
          if (response.code === 200) {
            this.$emit('update:file', response.data)
            this.uploadLoading = false
          }
        })
        .catch((err) => {
          console.log(err)
          this.$message.error('上传失败!')
          this.uploadLoading = false
        })
    }
  }
}
</script>
<style>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
