<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-row :gutter="24">
      <a-col :md="24" :lg="24">
        <a-card :bordered="false">
          <div class="account-center-avatarHolder">
            <div class="avatar">
              <img :src="user.avatar">
              <span class="edit-btn" @click="edit">
                <a-icon type="edit" />
              </span>
            </div>
            <div class="username">{{ user.nickname }}</div>
            <div class="bio">{{ user.introduction || '什么介绍都没有' }}</div>
          </div>
          <div class="account-center-detail">
            <p>
              <i class="title"></i>登录名: {{ user.username }}
            </p>
            <p>
              <i class="group"></i>全称: {{ user.fullname }}
            </p>
            <p>
              <i class="address"></i>
              <span>浙江省</span>
              <span>杭州市</span>
            </p>
          </div>
          <a-divider/>

          <div class="account-center-tags">
            <div class="tagsTitle">角色</div>
            <div>
              <template v-for="(role, index) in user.roles">
                <a-tooltip :key="index">
                  <template slot="title">
                    {{ role.description || role.name }}
                  </template>
                  <a-tag
                    :key="index"
                  >{{ role.name }}</a-tag>
                </a-tooltip>
              </template>
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <edit
      ref="editModal"
      :visible="editVisible"
      :loading="confirmLoading"
      :model="mdl"
      @cancel="editVisible=false"
      @ok="handleOk"
    />
  </div>
</template>

<script>
import { PageView, RouteView } from '@/layouts'

import { mapGetters } from 'vuex'
import Edit from './modules/Edit'
import { UpdateUserInfo } from '@/api/user'

export default {
  components: {
    RouteView,
    PageView,
    Edit
  },
  data () {
    return {
      editVisible: false,
      tagInputValue: '',
      user: {},
      confirmLoading: false,
      mdl: {},
      images: []
    }
  },
  computed: {
    ...mapGetters(['nickname', 'avatar'])
  },
  mounted () {
    this.getCurrentUser()
  },
  methods: {
    getTeams () {
      this.$http.get('/workplace/teams').then(res => {
        this.teams = res.result
        this.teamSpinning = false
      })
    },
    getCurrentUser () {
      this.user = this.$store.getters.info
      this.teamSpinning = false
    },
    edit () {
      this.editVisible = true
      this.$nextTick(() => {
        this.mdl = Object.assign({}, this.user)
        this.mdl.password = ''
      })
    },
    handleOk () {
      const form = this.$refs.editModal.$refs.form
      this.confirmLoading = true
      if (this.mdl.password && this.mdl.password !== this.mdl.repeatPassword) {
        this.$message.error('两次输入的密码不相同!')
        return
      }
      form.validate(valid => {
        if (valid) {
          if (this.mdl.id) {
            UpdateUserInfo(this.mdl).then(res => {
              this.editVisible = false
              this.confirmLoading = false
              this.$message.info('修改成功')
              location.reload()
            }).finally(() => {
              this.confirmLoading = false
            })
          }
        } else {
          this.confirmLoading = false
          return false
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.page-header-wrapper-grid-content-main {
  width: 100%;
  height: 100%;
  min-height: 100%;
  transition: 0.3s;

  .account-center-avatarHolder {
    text-align: center;
    margin-bottom: 24px;

    & > .avatar {
      margin: 0 auto;
      width: 104px;
      height: 104px;
      margin-bottom: 20px;
      border-radius: 50%;
      overflow: hidden;
      img {
        height: 100%;
        width: 100%;
      }
      .edit-btn {
        position: relative;
        bottom: 24px;
        width: 100%;
        height: 25px;
        display: block;
        background-color: #000;
        opacity: .4;
        color: #fff;
        transition: all .3s;
        font-size: 1.1em;
      }
      .edit-btn:hover {
        opacity: .6;
      }
    }

    .username {
      color: rgba(0, 0, 0, 0.85);
      font-size: 20px;
      line-height: 28px;
      font-weight: 500;
      margin-bottom: 4px;
    }
  }

  .account-center-detail {
    p {
      margin-bottom: 8px;
      padding-left: 26px;
      position: relative;
    }

    i {
      position: absolute;
      height: 14px;
      width: 14px;
      left: 0;
      top: 4px;
      background: url(https://gw.alipayobjects.com/zos/rmsportal/pBjWzVAHnOOtAUvZmZfy.svg);
    }

    .title {
      background-position: 0 0;
    }
    .group {
      background-position: 0 -22px;
    }
    .address {
      background-position: 0 -44px;
    }
  }

  .account-center-tags {
    .ant-tag {
      margin-bottom: 8px;
    }
  }

  .account-center-team {
    .members {
      a {
        display: block;
        margin: 12px 0;
        line-height: 24px;
        height: 24px;
        .member {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.65);
          line-height: 24px;
          max-width: 100px;
          vertical-align: top;
          margin-left: 12px;
          transition: all 0.3s;
          display: inline-block;
        }
        &:hover {
          span {
            color: #1890ff;
          }
        }
      }
    }
  }

  .tagsTitle,
  .teamTitle {
    font-weight: 500;
    color: rgba(0, 0, 0, 0.85);
    margin-bottom: 12px;
  }
}
</style>
