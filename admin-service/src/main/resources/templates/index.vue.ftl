<template>
  <div>
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <#list table.fields as field>
          <#if !field.keyFlag>
          <#if field_index lt 2>
          <a-col :md="8" :sm="24">
            <a-form-item label="资源名称">
              <a-input v-model="queryParam.${field.propertyName}" placeholder="${field.comment}"/>
            </a-form-item>
          </a-col>
    </#if>
  </#if>
</#list>
<#if (table.fields?size>2)>
          <template v-if="advanced">
  <#list table.fields as field>
    <#if !field.keyFlag && field_index gt 1>
            <a-col :md="8" :sm="24">
              <a-form-item label="资源名称">
                <a-input v-model="queryParam.${field.propertyName}" placeholder="${field.comment}"/>
              </a-form-item>
            </a-col>
     </#if>
  </#list>
          </template>
</#if>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              <#if (table.fields?size>2)>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
              </#if>
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
import moment from 'moment'
import { STable } from '@/components'
import { page, updateById, create, deleteById } from '@/api/${entity?uncap_first}'

import CreateForm from './modules/CreateForm'

export default {
  name: '${entity}',
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
      mdl: null,
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        <#list table.fields as field>
          <#if !field.keyFlag><#--生成普通字段 -->
        {
          title: '${field.comment}',
          dataIndex: '${field.propertyName}'
        }<#if field_has_next>,</#if>
          </#if>
        </#list>
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        console.log('loadData.parameter', parameter)
        return page(Object.assign(parameter, this.queryParam))
          .then(res => {
            return res.data
          })
      }
    }
  },
  created () {
    this.loadData({ t: new Date() })
  },
  methods: {
    handleAdd () {
      this.mdl = null
      this.visible = true
    },
    handleEdit (record) {
      this.visible = true
      this.mdl = { ...record }
    },
    handleOk () {
      const form = this.$refs.createModal.form
      this.confirmLoading = true
      form.validateFields((errors, values) => {
        if (!errors) {
          console.log('values', values)
          if (values.id > 0) {
            // 修改 e.g.
            updateById(values).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()

              this.$message.info('修改成功')
            }).catch((err) => {
              console.log(`form update error:-><#noparse>${err}</#noparse>`)
              this.confirmLoading = false
            })
          } else {
            // 新增
            create(values).then(res => {
              this.visible = false
              this.confirmLoading = false
              // 重置表单数据
              form.resetFields()
              // 刷新表格
              this.$refs.table.refresh()

              this.$message.info('新增成功')
            }).catch((err) => {
              console.log(`form update error:-><#noparse>${err}</#noparse>`)
              this.confirmLoading = false
            })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    handleCancel () {
      this.visible = false

      const form = this.$refs.createModal.form
      form.resetFields() // 清理表单数据（可不做）
    },
    handleDelete (row) {
        deleteById(row.id).then(res => {
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
    }
  }
}
</script>
