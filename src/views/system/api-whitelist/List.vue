<template>
  <div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="描述">
              <a-input v-model="queryParam.description" placeholder="描述"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="地址">
              <a-input v-model="queryParam.path" placeholder="地址"/>
            </a-form-item>
          </a-col>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="handleAdd()">新建</a-button>
    </div>

    <s-table
      ref="table"
      size="default"
      rowKey="id"
      :columns="columns"
      :data="loadData"
      :scroll="{ x: 800 }"
    >
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            title="确认删除这条数据？"
            ok-text="确认"
            okType="danger"
            cancel-text="取消"
            @confirm="handleDelete(record)"
            placement="left"
          >
            <a-icon slot="icon" type="question-circle-o" style="color: red" />
            <a style="color:#ff4d4f">删除</a>
          </a-popconfirm>
        </template>
      </span>
    </s-table>
    <form-modal
      ref="formModal"
      :visible="visible"
      :loading="confirmLoading"
      :model="mdl"
      @cancel="handleCancel"
      @ok="handleOk"
    />
  </div>
</template>

<script>
import { STable } from '@/components'
import { GetPage, UpdateById, Create, DeleteById } from '@/api/apiwhitelist'

import FormModal from './modules/FormModal'

export default {
  name: 'ApiWhitelist',
  components: {
    STable,
    FormModal
  },
  data () {
    return {
      // create model
      visible: false,
      warningVisible: false,
      confirmLoading: false,
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '描述',
          dataIndex: 'description'
        },
        {
          title: '地址',
          dataIndex: 'path'
        },
        {
          title: '请求方式',
          dataIndex: 'methods'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '110px',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return GetPage(Object.assign(parameter || {}, this.queryParam))
          .then(res => {
            return res.data
          })
      }
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    handleAdd () {
      this.mdl = {}
      this.visible = true
    },
    handleEdit (record) {
      this.visible = true
      this.mdl = { ...record }
    },
    handleOk () {
      const form = this.$refs.formModal.$refs.form
      this.confirmLoading = true
      form.validate(valid => {
        if (valid) {
          if (this.mdl.id) {
            // 修改 e.g.
            UpdateById(this.mdl).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()
              this.$message.info('修改成功')
            }).catch((err) => {
              console.log('form update error:->', err)
              this.$message.error('修改失败')
            }).finally(() => {
              this.confirmLoading = false
            })
          } else {
            // 新增
            Create(this.mdl).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()
              this.$message.info('新增成功')
            }).catch((err) => {
              console.log('form create error:->', err)
              this.$message.error('修改失败')
            }).finally(() => {
              this.confirmLoading = false
            })
          }
        } else {
          this.confirmLoading = false
          return false
        }
      })
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    handleCancel () {
      this.visible = false
      const form = this.$refs.formModal.$refs.form
      form.resetFields()
    },
    handleDelete (row) {
        DeleteById(row.id).then(res => {
                if (res.code === 200) {
                    this.$message.info('删除成功！')
                    // 刷新表格
                    this.$refs.table.refresh()
                } else {
                    this.$message.err('删除失败！')
                }
            })
    }
  }
}
</script>
