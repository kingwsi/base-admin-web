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
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="loadData">查询</a-button>
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

    <a-table
      :columns="columns"
      :data-source="treeData"
      :loading="treeDataLoading"
      rowKey="id"
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
    </a-table>
    <create-form
      ref="createModal"
      :visible="visible"
      :loading="confirmLoading"
      :resources="resources"
      :model="mdl"
      @cancel="handleCancel"
      @ok="handleOk"
    />
  </div>
</template>

<script>
import moment from 'moment'
import { STable } from '@/components'
import { GetAllResources, UpdateById, Create, DeleteById } from '@/api/resource'
import { listToTree } from '@/utils/util'

import CreateForm from './modules/CreateForm'

export default {
  name: 'ResourceList',
  components: {
    STable,
    CreateForm
  },
  data () {
    return {
      // create model
      visible: false,
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
          title: '地址',
          dataIndex: 'uri'
        },
        {
          title: '资源类型',
          dataIndex: 'type'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      treeData: null,
      treeDataLoading: false,
      menuList: [],
      resources: []
      // 加载数据方法 必须为 Promise 对象
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    loadData () {
      this.treeDataLoading = true
      GetAllResources(Object.assign({}, this.queryParam))
        .then(res => {
          this.menuList = res.data.filter(item => item.type === 'MENU')
          this.resources = this.menuList
          const tree = []
          listToTree(res.data, tree, -1)
          this.treeData = tree
          this.treeDataLoading = false
        })
    },
    handleAdd () {
      this.mdl = {
        type: 'API',
        methodList: []
      }
      this.visible = true
    },
    handleEdit (record) {
      this.visible = true
      this.resources = this.menuList.filter(item => item.id !== record.id)
      record.methodList = record.methods.split(';')
      this.mdl = { ...record }
    },
    handleOk () {
      const form = this.$refs.createModal.$refs.form
      this.confirmLoading = true
      form.validate(valid => {
        if (valid) {
          if (this.mdl.methodList) {
            this.mdl.methods = this.mdl.methodList.join(';')
          }
          if (this.mdl.id) {
            // 修改 e.g.
            UpdateById(this.mdl).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.loadData()
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
              this.loadData()

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
      // 重置
      const form = this.$refs.createModal.$refs.form
      form.resetFields()
    },
    handleDelete (row) {
      DeleteById(row.id).then(res => {
        this.$message.info('删除成功')
        // 刷新表格
        this.loadData()
      })
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    }
  }
}
</script>
