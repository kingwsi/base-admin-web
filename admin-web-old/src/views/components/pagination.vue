<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="date"
        label="日期"
        width="180"
      />
      <el-table-column
        prop="name"
        label="姓名"
        width="180"
      />
      <el-table-column
        prop="address"
        label="地址"
      />
    </el-table>
    <el-pagination
      layout="prev, pager, next"
      :total="50"
    />
  </div>
</template>
<script>
export default {
  name: 'Pagination',
  props: {
    pageInfo: {
      type: Object,
      required: true
    },
    columns: {
      type: Array,
      required: true
    }
  }
}
</script>
