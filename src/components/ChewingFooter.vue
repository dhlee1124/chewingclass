<template>
  <footer class="chewing-footer">
    <div class="footer-content">
      <!-- 로고 -->
      <div class="footer-logo">
        <img src="@/assets/logoWhite.png" alt="Chewing Class" />
      </div>

      <!-- 이용약관 & 개인정보 처리방침 -->
      <div class="footer-links">
        <span>이용약관</span>
        <span>|</span>
        <span>개인정보처리방침</span>
      </div>

      <!-- 회사 정보 -->
      <div class="footer-info">
        <p>주식회사 츄잉 사업자등록번호 : 177-88-02306</p>
      </div>

      <!-- 저작권 -->
      <div class="copyright">© 2022 chewing.ai</div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "ChewingFooter",
};
</script>

<style scoped>
.chewing-footer {
  width: 100%;
  background-color: #1e1e1e;
  color: #ffffff;
  padding: 40px;
  display: flex;
  justify-content: flex-start; /* 왼쪽 정렬 */
}

.footer-content {
  width: 80%;
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 왼쪽 정렬 */
  text-align: left; /* 텍스트 왼쪽 정렬 */
}

.footer-links {
  display: flex;
  gap: 16px;
  margin-bottom: 10px;
}

.footer-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
}

.footer-logo img {
  height: 40px;
}

.copyright {
  margin-top: 20px;
  font-size: 14px;
  color: #b0b0b0;
}
</style>
