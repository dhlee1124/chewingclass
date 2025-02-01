<template>
    <div class="terms-agreement">
      <div class="login-page">
    <LoginHeader />
    <!-- 로그인 폼 -->
    </div>
      <h1>이용약관 동의</h1>
      <div class="terms-list">
        <div>
          <input type="checkbox" v-model="allChecked" @change="toggleAll" />
          <label>전체 동의</label>
        </div>
        <div v-for="(term, index) in terms" :key="index">
          <input type="checkbox" v-model="term.checked" @change="checkAllChecked" />
          <label>{{ term.label }}</label>
          <button @click="viewTerm(term.label)">></button>
        </div>
      </div>
      <button @click="completeSignUp">완료</button>
    </div>
  </template>
  
  <script>
    import LoginHeader from "@/components/LoginHeader.vue";

  export default {
    components: {
    LoginHeader,
  },

    data() {
      return {
        allChecked: false,
        terms: [
          { label: "[필수] 츄잉 이용약관", checked: false, required: true },
          { label: "[필수] 휴대폰 본인인증 이용약관", checked: false, required: true },
          { label: "[필수] 개인정보 수집 이용 동의", checked: false, required: true },
        ],
      };
    },
    computed: {
      isAllRequiredChecked() {
        return this.terms.every((term) => !term.required || term.checked);
      },
    },
    methods: {
      toggleAll() {
        this.terms.forEach((term) => (term.checked = this.allChecked));
      },
      checkAllChecked() {
        this.allChecked = this.terms.every((term) => term.checked);
      },
      viewTerm(label) {
        alert(`${label} 보기`);
      },
      submit() {
        alert("이용약관에 동의하셨습니다!");
        this.$router.push("/next-step");
      },
    },
    methods: {
    completeSignUp() {
      alert('회원가입이 완료되었습니다.');
      this.$router.push({ name: 'Login' }); // 회원가입 완료 후 로그인 화면으로 이동
    },
  },
  };
  </script>
  
  <style scoped>
  .terms-agreement {
    text-align: center;
    padding: 20px;
  }
  .terms-list {
    margin: 20px 0;
  }
  button {
    margin-top: 20px;
  }
  </style>
  