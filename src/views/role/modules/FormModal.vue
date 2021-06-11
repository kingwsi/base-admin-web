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
      <a-card :bordered="false">
        <a-row>
          <a-form-model
            ref="form"
            :model="model"
            :rules="rules"
            :label-col="{ span: 5 }"
            :wrapper-col="{ span: 12 }">
            <a-form-model-item v-if="model.id" label="ID">
              <a-input v-model="model.id" disabled />
            </a-form-model-item>
            <a-form-model-item label="角色名称">
              <a-input v-model="model.name" />
            </a-form-model-item>
            <a-form-model-item label="角色描述">
              <a-input v-model="model.description" />
            </a-form-model-item>
          </a-form-model>
        </a-row>
      </a-card>
    </a-spin>
  </a-modal>
</template>

<script>
import { Tree } from 'ant-design-vue'
import FooterToolBar from '@/components/FooterToolbar'
// 表单字段
export default {
    name: 'RoleModal',
    components: {
      Tree,
      FooterToolBar
    },
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
      return {
        rules: {
          name: [{ required: true, message: '请输入角色名称', trigger: 'change' }]
        }
      }
    }
}
</script>
