<template>
  <div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="真实姓名">
              <a-input v-model="queryParam.realName" placeholder="真实姓名"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="昵称">
              <a-input v-model="queryParam.nickName" placeholder="昵称"/>
            </a-form-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="8" :sm="24">
              <a-form-item label="性别">
                <a-input v-model="queryParam.gender" placeholder="性别"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="手机号">
                <a-input v-model="queryParam.mobile" placeholder="手机号"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="邮箱">
                <a-input v-model="queryParam.email" placeholder="邮箱"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="头像">
                <a-input v-model="queryParam.avatar" placeholder="头像"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="介绍">
                <a-input v-model="queryParam.introduce" placeholder="介绍"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="最后登录时间">
                <a-input v-model="queryParam.lastLoginTime" placeholder="最后登录时间"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="密码">
                <a-input v-model="queryParam.password" placeholder="密码"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="最后登录ip">
                <a-input v-model="queryParam.lastLoginIp" placeholder="最后登录ip"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="账户状态 1 正常 0 停用">
                <a-input v-model="queryParam.accountStatus" placeholder="账户状态 1 正常 0 停用"/>
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
import { GetPage, UpdateById, Create, DeleteById } from '@/api/member'

import FormModal from './modules/FormModal'

export default {
  name: 'Member',
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
          title: '昵称',
          dataIndex: 'nickName'
        },
        {
          title: '真实姓名',
          dataIndex: 'realName'
        },
        {
          title: '性别',
          dataIndex: 'gender'
        },
        {
          title: '手机号',
          dataIndex: 'mobile'
        },
        {
          title: '账户状态',
          dataIndex: 'accountStatus',
          customRender: (val) => {
            return val === 0 ? '停用' : '正常'
          }
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
      const form = this.$refs.createModal.$refs.form
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
