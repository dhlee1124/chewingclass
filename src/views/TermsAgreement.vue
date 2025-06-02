<template>
  <div class="terms-wrapper">
    <header class="header">
      <img class="logo" src="@/assets/logo.png" alt="Chewing Class" />
    </header>

    <main class="content">
      <h1 class="title">이용약관 동의</h1>
      <section class="terms-section">
        <div class="term all">
          <label class="term-item">
            <input type="checkbox" v-model="allChecked" @change="toggleAll" />
            <span class="all-label">전체 동의</span>
          </label>
        </div>

        <div v-for="(term, index) in terms" :key="index" class="term white">
          <div class="term-item">
            <input type="checkbox" v-model="term.checked" @change="checkAllChecked" />
            <span>{{ term.label }}</span>
          </div>
          <img class="arrow" src="@/assets/chervon-right.svg" alt="view" />
        </div>
      </section>

      <button
        class="submit-button"
        :class="{ active: isAllChecked }"
        :disabled="!isAllChecked"
        @click="submit"
      >
        완료
      </button>
    </main>
  </div>
</template>

<script>
export default {
  data() {
    return {
      allChecked: false,
      terms: [
        { label: '[필수] 츄잉 이용약관', checked: false },
        { label: '[필수] 휴대폰 본인인증 이용약관', checked: false },
        { label: '[필수] 개인정보 수집 이용 동의', checked: false },
      ],
    };
  },
  computed: {
    isAllChecked() {
      return this.terms.every(term => term.checked);
    },
  },
  methods: {
    toggleAll() {
      this.terms.forEach(term => term.checked = this.allChecked);
    },
    checkAllChecked() {
      this.allChecked = this.terms.every(term => term.checked);
    },
    submit() {
      if (!this.isAllChecked) {
        alert('모든 필수 항목에 동의해주세요.');
        return;
      }
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
.terms-wrapper {
  background: #fff;
  font-family: Pretendard, sans-serif;
  color: #1b1d1f;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #e3f2ff;
  height: 80px;
  display: flex;
  align-items: center;
  padding: 0 120px;
}

.logo {
  width: 191px;
  height: 28px;
  object-fit: contain;
}

.content {
  margin: 0 auto;
  padding: 40px 20px;
  max-width: 480px;
  text-align: left;
}

.title {
  font-size: 32px;
  font-weight: 600;
  margin-bottom: 40px;
  text-align: center;
  color: #1b1d1f;
}

.terms-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.term {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  border-radius: 8px;
}

.term.white {
  background: #ffffff;
  border: 1px solid #e9ebed;
}

.term.all {
  background: #e9ebed;
}

.term-item {
  display: flex;
  align-items: center;
  gap: 6px;
}

.all-label {
  margin-left: 2px;
}

.arrow {
  width: 24px;
  height: 24px;
  object-fit: contain;
}

.submit-button {
  margin-top: 40px;
  width: 100%;
  padding: 16px;
  font-size: 17px;
  font-weight: 500;
  border: none;
  border-radius: 8px;
  background-color: #c9cdd3;
  color: #73787e;
  cursor: pointer;
}

.submit-button.active {
  background-color: #0f84f4;
  color: #ffffff;
}
</style>
