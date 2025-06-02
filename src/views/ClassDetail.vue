<template>
  <div class="class-detail">
    <!-- 헤더 -->
    <AppHeader />
    <GNB />
    <router-view />

    <div class="container">
      <!-- 왼쪽: 커리큘럼 -->
      <div class="curriculum">
        <div class="tab-menu">
          <button class="tab active">클래스 소개</button>
          <button class="tab">커리큘럼</button>
          <button class="tab">강사 소개</button>
          <button class="tab">클래스 후기</button>
          <button class="tab">추천</button>
        </div>

        <div class="curriculum-content">
          <h3>커리큘럼 정보</h3>
          <div class="section">
            <h4>추후 작업.</h4>
          </div>
        </div>
      </div>

      <!-- 오른쪽: 강의 정보 -->
      <div class="lecture-info">
        <div class="info-card">
          <span class="badge">도움링</span>
          <h3>추후 작업</h3>

          <div class="price">
            <span class="original-price">400,000원</span>
            <span class="discount">20%</span>
            <span class="final-price">320,000원</span>
          </div>
          <button class="enroll-button">수강하기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";
import GNB from "@/components/GNB.vue";

export default {
  name: "ClassDetail",
  components: {
    AppHeader,
    GNB,
  },
};
</script>

<style scoped>

.class-detail {
  background-color: #ffffff;
}

.container {
  display: flex;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  
}

.curriculum {
  flex: 2;
}

.lecture-info {
  flex: 1;
  background: #F7F8F9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.tab-menu {
  display: flex;
  gap: 10px;
  border-bottom: 1px solid #ddd; /* 🔹 아래 회색 선 추가 */
  margin-bottom: 20px;
}

.tab {
  background: none;
  border: none;
  cursor: pointer;
  padding: 10px 20px;
  font-size: 16px;
  color: #000;
}

.tab.active {
  font-weight: bold;
  border-bottom: 2px solid black;
}

.info-card {
  padding: 20px;
}

.badge {
  background: #007aff;
  color: white;
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.price {
  display: flex;
  gap: 10px;
  margin: 10px 0;
  align-items: center;
}

.original-price {
  text-decoration: line-through;
  color: gray;
}

.discount {
  color: red;
  font-weight: bold;
}

.final-price {
  font-size: 20px;
  font-weight: bold;
}

.enroll-button {
  width: 100%;
  padding: 10px;
  background: #007aff;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
</style>
