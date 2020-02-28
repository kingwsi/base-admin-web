<template>
  <div class="app-container">
    <el-tree :data="treeList" :props="defaultProps" accordion @node-click="handleNodeClick">
      <span slot-scope="{ node, data }" class="custom-tree-node">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            type="text"
            size="mini"
            @click="() => append(data)"
          >
            Append
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => remove(node, data)"
          >
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>
  </div>
</template>
<script>
import { getRouteTree } from '@/api/resource'
export default {
  name: 'Route',
  data() {
    return {
      treeList: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getRouteTree().then((response) => {
        this.treeList = response.data
      })
    },
    handleNodeClick(data) {
      console.log(data)
    },
    append(data) {
      console.log(data)
    },

    remove(node, data) {
      console.log(data)
    }
  }
}
</script>
<style>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>
