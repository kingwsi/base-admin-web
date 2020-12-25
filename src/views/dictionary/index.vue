<template>
  <div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="资源名称">
              <a-input v-model="queryParam.name" placeholder="资源名称"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="类型">
              <a-select v-model="queryParam.type" placeholder="请选择" default-value="ROUTE">
                <a-select-option value="MENU">菜单</a-select-option>
                <a-select-option value="API">接口</a-select-option>
                <a-select-option value="BUTTON">按钮</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="8" :sm="24">
              <a-form-item label="调用次数">
                <a-input-number v-model="queryParam.callNo" style="width: 100%"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="更新日期">
                <a-date-picker v-model="queryParam.date" style="width: 100%" placeholder="请输入更新日期"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="使用状态">
                <a-select v-model="queryParam.useStatus" placeholder="请选择" default-value="0">
                  <a-select-option value="0">全部</a-select-option>
                  <a-select-option value="1">关闭</a-select-option>
                  <a-select-option value="2">运行中</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="使用状态">
                <a-select placeholder="请选择" default-value="0">
                  <a-select-option value="0">全部</a-select-option>
                  <a-select-option value="1">关闭</a-select-option>
                  <a-select-option value="2">运行中</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
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
    <create-form
      ref="createModal"
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
import { GetPage, UpdateById, Create, DeleteById } from '@/api/dictionary'

import CreateForm from './modules/CreateForm'

export default {
  name: 'Dictionary',
  components: {
    STable,
    CreateForm
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
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '编码',
          dataIndex: 'code'
        },
        {
          title: '描述',
          dataIndex: 'description'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        console.log('loadData.parameter', parameter)
        return GetPage(Object.assign(parameter, this.queryParam))
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
      const form = this.$refs.createModal.$refs.form
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
              console.log(`form update error:->${err}`)
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
              console.log(`form update error:->${err}`)
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

      const form = this.$refs.createModal.$refs.form
      form.resetFields() // 清理表单数据（可不做）
    },
    handleDelete (row) {
        DeleteById(row.id).then(res => {
                if (res.code === 200) {
                    this.$message.info('删除成功！')
                    this.$refs.table.refresh()
                } else {
                    this.$message.err('删除失败！')
                }
            })
    }
  }
}
</script>
