<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="角色名称">
              <a-input placeholder="请输入" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="状态">
              <a-select placeholder="请选择" default-value="0" v-model="queryParam.status" allowClear>
                <a-select-option value="1">正常</a-select-option>
                <a-select-option value="0">禁用</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px">重置</a-button>
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
          <a @click="handleEditPermission(record)">权限</a>
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

  </a-card>
</template>

<script>
import { STable } from '@/components'
import FormModal from './modules/FormModal'
import { Page, UpdateById, CreateRole } from '@/api/role'

export default {
  name: 'TableList',
  components: {
    STable,
    FormModal
  },
  data () {
    return {
      description: '列表使用场景：后台管理中的权限管理以及角色管理，可用于基于 RBAC 设计的角色权限控制，颗粒度细到每一个操作类型。',

      visible: false,

      form: null,
      mdl: {},

      confirmLoading: false,

      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: 'ID',
          dataIndex: 'id'
        },
        {
          title: '角色名称',
          dataIndex: 'name'
        },
        {
          title: '状态',
          dataIndex: 'status'
        },
        {
          title: '创建时间',
          dataIndex: 'createdDate',
          sorter: true
        },
        {
          title: '操作',
          width: '180px',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        console.log('loadData.parameter', parameter)
        return Page(Object.assign(parameter, this.queryParam))
          .then(res => {
            return res.data
          })
      },
      selectedRowKeys: [],
      selectedRows: []
    }
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
      this.confirmLoading = true
      const form = this.$refs.formModal.$refs.form
      form.validate(valid => {
        if (valid) {
          if (this.mdl.id) {
            UpdateById(this.mdl).then(res => {
              this.confirmLoading = false
              this.visible = false
              this.$message.info('修改成功')
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()
            })
          } else {
            // 新增
            CreateRole(this.mdl).then(res => {
              this.confirmLoading = false
              this.visible = false
              this.$message.info('新增成功')
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()
            })
          }
        } else {
          this.visible = false
        }
      })
    },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    handleCancel () {
      this.visible = false
      const form = this.$refs.formModal.$refs.form
      form.resetFields() // 清理表单数据（可不做）
    },
    resetSearchForm () {
      this.queryParam = {}
    },
    handleEditPermission (record) {
      this.$router.push({ path: `/system/role/permission/${record.id}` })
    }
  },
  watch: {
  }
}
</script>
