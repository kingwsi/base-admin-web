<template>
  <div class="clearfix">
    <a-upload
      :custom-request="customUpload"
      list-type="picture-card"
      :file-list="fileList"
      :remove="handleRemove"
      @preview="handlePreview"
      @change="handleChange"
    >
      <div v-if="fileList.length < max">
        <a-icon type="plus" />
        <div class="ant-upload-text">Upload</div>
      </div>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>
<script>
import { SingleUpload } from '@/api/fileResource'

export default {
  name: 'CUpload',
  props: {
      files: {
        type: null,
        require: true,
        default: () => ''
      },
      max: {
        type: Number,
        require: false,
        default: () => 1
      },
      format: {
        type: String,
        require: false,
        default: () => 'array'
      }
    },
    watch: {
      files (n, o) {
        // 绑定对象为数组，或是字符串
        if (this.fileList == null || this.fileList.length < 1) {
          let fileArrays = []
          if (n instanceof Array) {
            fileArrays = n
          } else {
            fileArrays = n.split(';')
          }
          let uid = 0
          fileArrays.forEach(item => {
            uid++
            this.fileList.push({
              uid: uid,
              name: `${uid}.jpg`,
              url: item,
              response: item
            })
          })
        }
      },
      deep: true,
      immediate: true
    },
    data () {
      return {
        previewVisible: false,
        previewImage: '',
        fileList: []
      }
    },
    methods: {
      handleCancel () {
        this.previewVisible = false
      },
      async handlePreview (file) {
        this.previewImage = file.url || file.preview
        this.previewVisible = true
      },
      handleChange ({ fileList }) {
        this.fileList = fileList
      },
      customUpload (options) {
        const formData = new FormData()
        formData.append('file', options.file)
        options.onProgress()
        SingleUpload(formData).then((response) => {
          if (response.code === 200) {
            if (this.files instanceof Array) {
              this.files.push(response.data)
            } else {
              let newstr = response.data
              if (this.files && this.files !== 'undefined') {
                newstr = `${this.files};${response.data}`
              }
              this.$emit('update:files', newstr)
            }
            options.onSuccess(response.data, options)
          } else {
            options.onError()
          }
        }).catch((e) => {
          this.$message.error('图片上传失败！')
          options.onError()
        })
      },
      async handleRemove (file) {
        if (this.files instanceof Array) {
          this.files.filter(o => o.url !== file.response)
        } else {
          const a = this.files.replace(file.response)
          this.$emit('update:files', a === 'undefined' ? '' : a)
        }
        return true
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
