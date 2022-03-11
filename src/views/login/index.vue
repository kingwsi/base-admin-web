<template>
  <div class="main">
    <a-form-model
      class="user-layout-login"
      ref="formLogin"
      :model="form"
      :rules="rules"
      @submit="handleSubmit"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1" tab="账号密码登录">
          <a-form-model-item prop="username">
            <a-input
              size="large"
              type="text"
              placeholder="账户"
              v-model="form.username"
            >
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-model-item>

          <a-form-model-item prop="password">
            <a-input-password
              v-model="form.password"
              size="large"
              placeholder="密码"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input-password>
          </a-form-model-item>

          <a-row :gutter="16">
            <a-col class="gutter-row" :span="16">
              <a-form-model-item prop="captcha">
                <a-input size="large" type="text" placeholder="验证码" v-model="form.captcha">
                  <a-icon slot="prefix" type="safety-certificate" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                </a-input>
              </a-form-model-item>
            </a-col>
            <a-col class="gutter-row" :span="8">
              <a-spin :spinning="captchaImgLoading">
                <img :src="captcha.image" @click="reloadCaptcha" class="getCaptcha"/>
              </a-spin>
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane key="tab2" tab="手机号登录">
          <a-form-model-item>
            <a-input size="large" type="text" placeholder="手机号" v-model="form.mobile">
              <a-icon slot="prefix" type="mobile" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-model-item>

          <a-row :gutter="16">
            <a-col class="gutter-row" :span="16">
              <a-form-model-item>
                <a-input v-if="loginType==2" size="large" type="text" placeholder="验证码" v-model="form.captcha">
                  <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,225)' }"/>
                </a-input>
              </a-form-model-item>
            </a-col>
            <a-col class="gutter-row" :span="8">
              <a-button
                class="getCaptcha"
                tabindex="-1"
                :disabled="state.smsSendBtn"
                @click.stop.prevent="getCaptcha"
                v-text="!state.smsSendBtn && '暂不支持' || (state.time+' s')"
              ></a-button>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>

      <a-form-model-item>
        <router-link
          :to="{ name: 'recover', params: { user: 'aaa'} }"
          class="forge-password"
          style="float: right;"
        >忘记密码</router-link>
      </a-form-model-item>

      <a-form-model-item style="margin-top:24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >确定</a-button>
      </a-form-model-item>

      <div class="user-login-other" v-show="false">
        <span>其他登录方式</span>
        <a>
          <a-icon class="item-icon" type="alipay-circle"></a-icon>
        </a>
        <a>
          <a-icon class="item-icon" type="taobao-circle"></a-icon>
        </a>
        <a>
          <a-icon class="item-icon" type="weibo-circle"></a-icon>
        </a>
        <router-link class="register" :to="{ name: 'register' }">注册账户</router-link>
      </div>
    </a-form-model>

    <two-step-captcha
      v-if="requiredTwoStepCaptcha"
      :visible="stepCaptchaVisible"
      @success="stepCaptchaSuccess"
      @cancel="stepCaptchaCancel"
    ></two-step-captcha>
  </div>
</template>

<script>
import { GetCaptcha } from '@/api/common/captcha'
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
import { getSmsCaptcha } from '@/api/login'

export default {
  data () {
    return {
      customActiveKey: 'tab1',
      loginBtn: false,
      // login type: 0 email, 1 username, 2 telephone
      loginType: 0,
      isLoginError: false,
      requiredTwoStepCaptcha: false,
      captchaImgLoading: false,
      stepCaptchaVisible: false,
      form: {
        username: '',
        password: '',
        captcha: '',
        key: ''
      },
      state: {
        time: 60,
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        smsSendBtn: false
      },
      captcha: {
        image: ''
      },
      rules: {
        username: [{ required: true, message: '请输入登录名', trigger: 'change' }],
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
        // mobile: [{ required: true, pattern: /^1[34578]\d{9}$/, message: '请输入正确的手机号' }],
        captcha: [{ required: true, message: '请选择验证码', trigger: 'change' }]

      }
    }
  },
  created () {
    this.reloadCaptcha()
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    // handler
    handleUsernameOrEmail (rule, value, callback) {
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        state.loginType = 0
      } else {
        state.loginType = 1
      }
      callback()
    },
    handleTabClick (key) {
      this.customActiveKey = key
      // this.form.resetFields()
    },
    reloadCaptcha () {
      this.captchaImgLoading = true
      GetCaptcha().then(response => {
        this.captcha = response.data
        this.form.captcha = ''
        this.captchaImgLoading = false
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      const {
        state,
        Login,
        form
      } = this

      this.$refs.formLogin.validate(valid => {
        if (valid) {
          state.loginBtn = true
          console.log('login form', form)
          form.key = this.captcha.key
          Login(form)
            .then((res) => {
              this.loginSuccess(res)
            })
            .catch((e) => {
              this.reloadCaptcha()
              state.loginBtn = false
            })
        } else {
          return false
        }
      })
    },
    getCaptcha (e) {
      e.preventDefault()
      const { form: { validateFields }, state } = this

      validateFields(['mobile'], { force: true }, (err, values) => {
        if (!err) {
          state.smsSendBtn = true

          const interval = window.setInterval(() => {
            if (state.time-- <= 0) {
              state.time = 60
              state.smsSendBtn = false
              window.clearInterval(interval)
            }
          }, 1000)

          const hide = this.$message.loading('验证码发送中..', 0)
          getSmsCaptcha({ mobile: values.mobile }).then(res => {
            setTimeout(hide, 2500)
            this.$notification['success']({
              message: '提示',
              description: '验证码获取成功，您的验证码为：' + res.result.captcha,
              duration: 8
            })
          }).catch(err => {
            setTimeout(hide, 1)
            clearInterval(interval)
            state.time = 60
            state.smsSendBtn = false
            this.requestFailed(err)
          })
        }
      })
    },
    stepCaptchaSuccess () {
      this.loginSuccess()
    },
    stepCaptchaCancel () {
      this.Logout().then(() => {
        this.loginBtn = false
        this.stepCaptchaVisible = false
      })
    },
    loginSuccess (res) {
      this.$router.push({ path: '/' })
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
      this.isLoginError = false
    },
    requestFailed () {
      this.isLoginError = true
    }
  }
}
</script>

<style lang="less" scoped>
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
