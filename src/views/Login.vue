<template>
  <div class="login-container">
    <div class="login-page">
    <LoginHeader />
    <!-- 로그인 폼 -->
  </div>
    <div class="login-buttons">
      <button class="social-login google">구글로 시작하기</button>
      <button class="social-login facebook">페이스북으로 시작하기</button>
      <button class="social-login kakao">카카오로 시작하기</button>
      <button class="email-login" @click="goToLoginWithEmail">이메일로 시작하기</button>
      <button class="chewing-login">츄잉 계정으로 시작하기</button>
    </div>
    <div class="register-section">
      <button class="register-button" @click="navigateToRegister">츄잉이 처음이신가요?</button>
    </div>
  </div>
</template>

<script>
import LoginHeader from "@/components/LoginHeader.vue";

export default {

  components: {
    LoginHeader,
  },

  name: "Login",
  methods: {
    navigateToRegister() {
      this.$router.push({ name: "Register" });
    },
    goToLoginWithEmail() {
      this.$router.push({ name: "LoginWithEmail" }); // EmailVerification 페이지로 이동
    },
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.login-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.social-login {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
}

.email-login {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  background-color: #f2f2f2;
  color: rgb(0, 0, 0);
  cursor: pointer;
}

.chewing-login {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  background-color: #1e90ff;
  color: white;
  cursor: pointer;
}

.register-section {
  margin-top: 20px;
  text-align: center;
}

.register-button {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  background-color: #fcfcfc;
  color: rgb(0, 0, 0);
  cursor: pointer;
}
</style>
