<template>
  <div class="register-page">
    <div class="login-page">
    <LoginHeader />
    <!-- 로그인 폼 -->
    </div>

    <h1 class="register-header">회원가입</h1>
    <form class="register-form">
      <div class="form-group">
        <label>이메일</label>
        <input type="email" placeholder="이메일을 입력해주세요" />
      </div>
      <div class="form-group">
        <label>비밀번호</label>
        <input type="password" placeholder="비밀번호를 입력해주세요" />
      </div>
      <div class="form-group">
        <label>비밀번호 확인</label>
        <input type="password" placeholder="비밀번호를 다시 입력해주세요" />
      </div>
      <button @click="goToUserInfoForm">다음</button>
    </form>
  </div>
</template>

<script>
  import LoginHeader from "@/components/LoginHeader.vue";

export default {
  components: {
    LoginHeader,
  },
  
  methods: {
    goToUserInfoForm() {
      this.$router.push({ name: 'UserInfoForm' });
    },
  },
  name: "Register",
};
</script>

<style scoped>
.register-page {
  text-align: center;
  padding: 2rem;
}

.register-header {
  font-size: 24px;
  margin-bottom: 1rem;
  padding-top: 60px;
}

.register-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  
}

.form-group {
  margin-bottom: 1rem;
  width: 100%;
  max-width: 300px;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.next-button {
  background-color: #0066ff;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 5px;
  width: 100%;
  max-width: 300px;
}
</style>
