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
      <span slot="avatar" slot-scope="avatar">
        <a-avatar v-if="avatar" :src="avatar" />
        <a-avatar v-else icon="user" />
      </span>
      <span slot="status" slot-scope="status">
        <a-tag v-if="status==='1'" color="green">启用</a-tag>
        <a-tag v-else color="red">禁用</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            title="确认重置密码？"
            ok-text="确认"
            okType="danger"
            cancel-text="取消"
            @confirm="handleResetPassword(record)"
            placement="left"
          >
            <a-icon slot="icon" type="question-circle-o" style="color: red" />
            <a style="color:#1890ff">重置密码</a>
          </a-popconfirm>
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
    <reset-result
      :visible="resetResultVisible"
      @cancel="resetResultVisible=false"
      :text="resultPwd"/>
  </div>
</template>

<script>
import moment from 'moment'
import { STable } from '@/components'
// eslint-disable-next-line
import { GetUserPage, UpdateUserById, CreateUser, DeleteUserById, ResetPwdById } from '@/api/user'

import CreateForm from './modules/FormModal'
import ResetResult from './modules/ResetResult'
export default {
  name: 'User',
  components: {
    STable,
    CreateForm,
    ResetResult
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
          title: '头像',
          dataIndex: 'avatar',
          scopedSlots: { customRender: 'avatar' }
        },
        {
          title: '用户名',
          dataIndex: 'username'
        },
        {
          title: '全名',
          dataIndex: 'fullName'
        },
        {
          title: '介绍',
          dataIndex: 'introduction'
        },
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '220px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        console.log('loadData.parameter', parameter)
        return GetUserPage(Object.assign(parameter, this.queryParam))
          .then(res => {
            return res.data
          })
      },
      resetResultVisible: false,
      resultPwd: ''
    }
  },
  created () {
    this.loadData({ t: new Date() })
  },
  methods: {
    handleAdd () {
      this.mdl = {}
      this.visible = true
    },
    handleEdit (record) {
      this.visible = true
      console.log(record)
      this.$nextTick(() => {
          this.mdl = Object.assign({}, record)
      })
    },
    handleResetPassword (record) {
      ResetPwdById(record.id).then(response => {
        if (response.code === 200) {
          this.resetResultVisible = true
          this.resultPwd = response.data
        }
      })
    },
    handleOk () {
      const form = this.$refs.createModal.$refs.form
      this.confirmLoading = true
      form.validate(valid => {
        if (valid) {
          console.log('formData', this.mdl)
          if (this.mdl.id) {
            // 修改 e.g.
            UpdateUserById(this.mdl).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              this.resetForm()
              // 刷新表格
              this.$refs.table.refresh()
              this.$message.info('修改成功')
            })
          } else {
            // 新增
            CreateUser(this.mdl).then(res => {
              this.visible = false
              this.confirmLoading = false
              this.resetForm()
              this.$message.info('新增成功')
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
      this.$nextTick(() => {
        this.resetForm()
      })
    },
    handleDelete (row) {
        DeleteUserById(row.id).then(res => {
                if (res.code === 200) {
                    this.$message.info('删除成功！')
                    // 刷新表格
                    this.$refs.table.refresh()
                } else {
                    this.$message.err('删除失败！')
                }
            }).catch((err) => console.log(err))
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    },
    resetForm () {
      const form = this.$refs.createModal.$refs.form
      console.log(form)
      form.clearValidate()
      this.mdl = {}
    }
  }
}
</script>
<style>
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
