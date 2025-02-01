<template>
  <div class="login-container">
    <div class="login-page">
    <LoginHeader />
    <!-- 로그인 폼 -->
  </div>
  <div class="login-buttons">
  <img src="@/assets/google-login.png" alt="구글 로그인" class="social-login" @click="loginWithGoogle" />
  <img src="@/assets/facebook-login.png" alt="페이스북 로그인" class="social-login" @click="loginWithFacebook" />
  <img src="@/assets/kakao-login.png" alt="카카오 로그인" class="social-login" @click="loginWithKakao" />
  <img src="@/assets/email-login.png" alt="이메일 로그인" class="email-login" @click="goToLoginWithEmail" />
  <img src="@/assets/chewing-login.png" alt="츄잉 계정 로그인" class="chewing-login" @click="loginWithChewing" />
</div>

<div class="register-section">
  <img 
    src="@/assets/register-button.png" 
    alt="회원가입 버튼" 
    class="register-button" 
    @click="navigateToRegister"
  />
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
  color: rgb(0, 0, 0);
  cursor: pointer;
}

.chewing-login {
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  border: none;
  color: white;
  cursor: pointer;
}

.register-section {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.register-button {
  width: 100px; /* 버튼 크기 조절 */
  height: auto;
  cursor: pointer;
}
</style>
