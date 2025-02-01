<template>
    <div class="user-info-form">
      <h1>회원가입</h1>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="name">이름</label>
          <input
            id="name"
            v-model="name"
            type="text"
            placeholder="이름을 입력해주세요"
            required
          />
        </div>
  
        <div class="form-group">
          <label for="rrn">주민등록번호</label>
          <div class="rrn-inputs">
            <input
              id="rrn-first"
              v-model="rrnFirst"
              type="text"
              maxlength="6"
              placeholder="생년월일 6자리"
              required
            />
            <span>-</span>
            <input
              id="rrn-second"
              v-model="rrnSecond"
              type="password"
              maxlength="7"
              placeholder="*******"
              required
            />
          </div>
        </div>
  
        <div class="form-group">
          <label for="carrier">통신사</label>
          <select id="carrier" v-model="carrier" required>
            <option value="" disabled>선택</option>
            <option value="SKT">SKT</option>
            <option value="KT">KT</option>
            <option value="LG U+">LG U+</option>
            <option value="알뜰폰">알뜰폰</option>
          </select>
        </div>
  
        <div class="form-group">
          <label for="phone">휴대폰 번호</label>
          <div class="phone-input">
            <input
              id="phone"
              v-model="phoneNumber"
              type="text"
              maxlength="11"
              placeholder="휴대폰 번호를 입력해주세요"
              required
            />
            <button type="button" @click="sendVerificationCode">인증</button>
          </div>
        </div>
  
        <div class="form-group" v-if="isVerificationSent">
          <label for="verification-code">인증번호</label>
          <input
            id="verification-code"
            v-model="verificationCode"
            type="text"
            maxlength="6"
            placeholder="인증번호 6자리"
            required
          />
          <p v-if="remainingTime > 0">남은 시간: {{ remainingTime }}초</p>
          <button type="button" @click="resendVerificationCode">인증번호 다시 받기</button>
        </div>
  
        <button @click="goToTermsAgreement">다음</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        name: "",
        rrnFirst: "",
        rrnSecond: "",
        carrier: "",
        phoneNumber: "",
        verificationCode: "",
        isVerificationSent: false,
        remainingTime: 180,
      };
    },
    methods: {
    goToTermsAgreement() {
      this.$router.push({ name: 'TermsAgreement' });
    },
  },
    computed: {
      isFormValid() {
        return (
          this.name &&
          this.rrnFirst.length === 6 &&
          this.rrnSecond.length === 7 &&
          this.carrier &&
          this.phoneNumber.length >= 10 &&
          (!this.isVerificationSent || this.verificationCode.length === 6)
        );
      },
    },
    methods: {
      sendVerificationCode() {
        if (this.phoneNumber.length < 10) {
          alert("휴대폰 번호를 정확히 입력해주세요.");
          return;
        }
        this.isVerificationSent = true;
        this.startCountdown();
        alert("인증번호가 발송되었습니다.");
      },
      resendVerificationCode() {
        this.remainingTime = 180;
        alert("인증번호가 다시 발송되었습니다.");
      },
      startCountdown() {
        const interval = setInterval(() => {
          if (this.remainingTime > 0) {
            this.remainingTime--;
          } else {
            clearInterval(interval);
            this.isVerificationSent = false;
          }
        }, 1000);
      },
      submitForm() {
        if (!this.isFormValid) {
          alert("모든 필드를 정확히 입력해주세요.");
          return;
        }
        alert("회원가입 정보를 제출합니다.");
        // 여기에서 서버로 데이터를 전송하거나 다음 페이지로 이동합니다.
        this.$router.push("/next-step");
      },
    },
  };
  </script>
  
  <style scoped>
  .user-info-form {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    text-align: left;
  }
  .form-group {
    margin-bottom: 20px;
  }
  .rrn-inputs,
  .phone-input {
    display: flex;
    align-items: center;
  }
  .rrn-inputs span {
    margin: 0 5px;
  }
  .phone-input button {
    margin-left: 10px;
  }
  button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px;
    cursor: pointer;
  }
  button:disabled {
    background-color: #ddd;
    cursor: not-allowed;
  }
  </style>
  