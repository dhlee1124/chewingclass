<template>
    <div class="email-verification">
      <h1 class="title">이메일 인증</h1>
      <form @submit.prevent="handleSubmit">
        <div v-if="emailSent">
          <p class="info-text">
            입력하신 이메일로 비밀번호 재설정 링크가 전송되었습니다.
          </p>
          <p class="info-text">
            이메일을 받지 못하셨나요? <a href="#" @click.prevent="resendEmail">인증 링크 재전송</a>
          </p>
        </div>
        <div v-else>
          <label for="email" class="label">이메일</label>
          <input
            id="email"
            type="email"
            v-model="email"
            placeholder="이메일을 입력해주세요"
            class="input"
            required
          />
        </div>
        <button
          type="submit"
          class="btn"
          :disabled="emailSent && !canResend"
          @click="handleButtonClick"
        >
          확인
        </button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: "EmailVerification",
    data() {
      return {
        email: "",
        emailSent: false,
        canResend: false,
      };
    },
    methods: {
      handleSubmit() {
        if (this.email) {
          this.emailSent = true;
          this.canResend = false;
          setTimeout(() => {
            this.canResend = true;
          }, 60000); // 1분 후 재전송 가능
        } else {
          alert("이메일을 입력해주세요.");
        }
      },
      resendEmail() {
        if (this.canResend) {
          alert("인증 링크가 재전송되었습니다.");
          this.canResend = false;
          setTimeout(() => {
            this.canResend = true;
          }, 60000); // 1분 후 다시 활성화
        } else {
          alert("잠시 후에 다시 시도해주세요.");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .email-verification {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    text-align: center;
  }
  .title {
    font-size: 24px;
    margin-bottom: 20px;
  }
  .label {
    display: block;
    margin-bottom: 8px;
    font-size: 16px;
    text-align: left;
  }
  .input {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    font-size: 14px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  .btn {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    color: #fff;
    background-color: #007bff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  .btn:disabled {
    background-color: #ccc;
  }
  .info-text {
    font-size: 14px;
    color: #666;
    margin-bottom: 20px;
  }
  </style>
  