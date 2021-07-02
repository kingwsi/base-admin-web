<template>
  <div>
    <a-modal
      :width="600"
      :visible="visible"
      :footer="null"
      :closable="false"
      :keyboard="false"
      :maskClosable="false">
      <a-result
        status="success"
        title="重置成功"
      >
        <template #subTitle>
          <p>请复制后保存，关闭后将无法查看</p>
          <p style="font-size: 20px; text-align: center;letter-spacing:4px;line-height:40px">
            {{ text }}
          </p>
        </template>
        <template #extra>
          <a-button
            key="console"
            :disabled="!closeButton"
            type="primary"
            @click="() => { $emit('cancel') }"
          >
            {{ closeButton ? '关闭' : '关闭 ' + time + 's' }}
          </a-button>
        </template>
      </a-result>
    </a-modal>
  </div>
</template>
<script>
export default {
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    text: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      time: 5,
      closeButton: false
    }
  },
  created () {},
  methods: {
  },
  watch: {
    text (item1, item2) {
      console.log(item1)
      const interval = window.setInterval(() => {
        if (this.time-- <= 1) {
          this.closeButton = true
          window.clearInterval(interval)
        }
      }, 1000)
    }
  }
}
</script>

<style scoped>
</style>
