<template>
  <div>
    <el-card class="login-form-layout">
      <el-form
          autocomplete="on"
          :model="loginForm"
          ref="loginForm"
          label-position="left"
      >
        <div style="text-align: center">
<!--          <el-icon class="el-icon-house" style="width: 56px;height: 56px;color: #409EFF"></el-icon>-->
        </div>
        <h2 class="login-title color-main">平台登陆器</h2>
        <el-form-item prop="username">
          <el-input
              name="username"
              type="text"
              v-model="loginForm.username"
              autocomplete="on"
              placeholder="请输入用户名"
          >
            <span slot="prefix">
<!--              <el-icon class="el-icon-house" style="width: 56px;height: 56px;color: #409EFF"></el-icon>-->
            </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              name="password"
              :type="pwdType"
              @keyup.enter.native="handleLogin"
              v-model="loginForm.password"
              autocomplete="on"
              placeholder="请输入密码"
          >
            <span slot="prefix">
<!--              <el-icon class="el-icon-house" style="width: 56px;height: 56px;color: #409EFF"></el-icon>-->
            </span>
            <span slot="suffix" @click="showPwd">
<!--              <el-icon class="el-icon-house" style="width: 56px;height: 56px;color: #409EFF"></el-icon>-->
            </span>
          </el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 60px">
          <el-button
              style="width: 100%"
              type="primary"
              :loading="loading"
              @click.native.prevent="handleLogin"
          >登录</el-button>
        </el-form-item>
        <el-button type="primary" style="margin-left: 39%" @click="handleSignUp">注册</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      loginForm: {
        username: "luo",
        password: "luohhh"
      },
      loading: false,
      pwdType: "password",
    };
  },
  methods: {
    showPwd() {
      if (this.pwdType === "password") {
        this.pwdType = "";
      } else {
        this.pwdType = "password";
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          //调用store下的Login方法
          this.$store
              .dispatch("Login", this.loginForm)//读取loginForm里面的username和password
              .then(response => {//声明response变量，类型是json
                this.loading = false;
                let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
                if (code === 200) {
                  let username = response.data.data
                  localStorage.setItem('Username',username)
                  this.$store.commit('loginUsername',username)
                  if (username === 'root')
                    this.$store.commit('loginUsertype','admin')
                  else {this.$store.commit('loginUsertype','user')}
                  this.$router.push({
                    path: "/index",
                  });
                }
                else {
                  alert("登陆失败："+response.data.message)
                }
              })
              .catch(() => {
                this.loading = false;
              });
        } else {
          // eslint-disable-next-line no-console
          console.log("参数验证不合法！");
          return false;
        }
      });
    },
    handleSignUp() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          //调用store下的Login方法
          this.$store
              .dispatch("signUp", this.loginForm)//读取loginForm里面的username和password
              .then(response => {//声明response变量，类型是json
                this.loading = false;
                let code = response.data.code;//后端传给前端的值为data，code，message，传值给code
                if (code === 200) {
                  alert("注册成功！请登录")
                }
                else {
                  alert("注册失败失败："+response.data.message)
                }
              })
              .catch(() => {
                this.loading = false;
              });
        } else {
          // eslint-disable-next-line no-console
          console.log("参数验证不合法！");
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 360px;
  margin: 140px auto;
  border-top: 10px solid #409eff;
}

.login-title {
  text-align: center;
}

.login-center-layout {
  background: #409eff;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-top: 200px;
}
</style>
