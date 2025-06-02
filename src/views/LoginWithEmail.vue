<template>
  <div class="login-container">
    <div class="login-page">
      <LoginHeader />
    </div>

    <h1 class="login-title">로그인</h1>
    <form @submit.prevent="handleLogin">
      <div class="input-group">
        <label for="email">이메일</label>
        <input
          id="email"
          type="email"
          v-model="email"
          placeholder="이메일을 입력해주세요"
          required
        />
      </div>
      <div class="input-group">
        <label for="password">비밀번호</label>
        <div class="password-container">
          <input
            id="password"
            type="password"
            v-model="password"
            placeholder="비밀번호를 입력해주세요"
            required
          />
          <button type="button" class="toggle-visibility" @click="togglePasswordVisibility">
            {{ showPassword ? "숨기기" : "보기" }}
          </button>
        </div>
      </div>
      <div class="actions">
        <button type="submit" class="login-button" :disabled="loading">
          {{ loading ? "로그인 중..." : "로그인" }}
        </button>
        <button type="button" class="forgot-password" @click="goToForgotPassword">
          비밀번호 찾기
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import LoginHeader from "@/components/LoginHeader.vue";

export default {
  components: {
    LoginHeader,
  },
  name: "LoginWithEmail",
  data() {
    return {
      email: "",
      password: "",
      showPassword: false,
      loading: false,
    };
  },
  methods: {
    handleLogin() {
      if (!this.email || !this.password) {
        alert("이메일과 비밀번호를 입력해주세요.");
        return;
      }

      this.loading = true;
      setTimeout(() => {
        this.loading = false;
        alert("로그인 성공!");
        this.$router.push("/admin-dashboard");
      }, 1500);
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
      const passwordField = document.getElementById("password");
      passwordField.type = this.showPassword ? "text" : "password";
    },
    goToForgotPassword() {
      this.$router.push("/forgot-password");
    },
  },
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  padding-top: 60px;
  font-family: 'Pretendard', sans-serif;
}
.login-title {
  font-size: 24px;
  margin-bottom: 20px;
}
.input-group {
  margin-bottom: 20px;
  text-align: left;
}
.input-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
  font-family: 'Pretendard', sans-serif;
}
.input-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #c9cdd3;
  border-radius: 8px;
  font-family: 'Pretendard', sans-serif;
}
.password-container {
  position: relative;
}
.password-container .toggle-visibility {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 12px;
}
.actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.login-button {
  background-color: #0f84f4;
  color: white;
  padding: 14px 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-family: 'Pretendard', sans-serif;
  flex: 1;
  margin-right: 10px;
}
.forgot-password {
  background: none;
  border: none;
  color: #007bff;
  cursor: pointer;
  font-size: 14px;
  font-family: 'Pretendard', sans-serif;
}
</style>
