<template>
  <div class="class-detail-page">
    <AdminHeader />
    <GNB />
    <!-- 캐러셀 -->
   <div class="carousel-section">
  <div class="carousel-slide main-slide">
    <img src="@/assets/banner.jpg" alt="배너" class="slide-img" />
  </div>
  <div class="carousel-slide side-slide left"></div>
  <div class="carousel-slide side-slide right"></div>
</div>

    <!-- 콘텐츠 -->
    <div class="content-wrapper">
      <!-- 탭 -->
      <div class="tabs">
        <div class="tab active">클래스 소개</div>
        <div class="tab" @click="goToCurriculum">커리큘럼</div>
        <div class="tab">강사소개</div>
        <div class="tab">클래스 후기</div>
        <div class="tab">추천</div>
      </div>

      <!-- 본문 -->
      <div class="main-content">
        <div class="description">
          <h3>1. 강의 소개</h3>
          <ul>
            <li>설명: 강의 개요와 학습 목표, 커리큘럼 요약 정보.</li>
            <li>
              조건 및 요소:
              <ul>
                <li>강의 설명: 전체 강의 개요와 학습 목표 제공.</li>
                <li>커리큘럼 요약: 강의 구성과 주요 내용 요약.</li>
              </ul>
            </li>
          </ul>
        </div>

        <!-- 정보 박스 -->
        <div class="info-box">
          <span class="tag">드로잉</span>
          <h2>디자이너를 위한<br />데이터 수집과 시각화</h2>
          <ul class="info-list">
            <li><img class="icon" src="@/assets/icon-play.svg" /> VOD</li>
            <li><img class="icon" src="@/assets/icon-time.svg" /> 100회, 57시간 59분</li>
            <li><img class="icon" src="@/assets/icon-person.svg" /> 정영호</li>
            <li><img class="icon" src="@/assets/icon-level.svg" /> 입문 ~ 기초</li>
            <li><img class="icon" src="@/assets/icon-data.svg" /> 강의자료 포함</li>
          </ul>
          <div class="price-box">
            <del>400,000원</del>
            <span class="discount">20%</span>
            <strong>320,000원</strong>
          </div>
          <button class="buy-button">수강하기</button>
          <img class="bookmark-icon" src="@/assets/bookmark.svg" />
        </div>
      </div>
    </div>
  

  <ChewingFooter />
</div>
   
</template>

<script>
import AdminHeader from "@/components/AdminHeader.vue";
import GNB from "@/components/GNB.vue";
import ChewingFooter from "@/components/ChewingFooter.vue";

export default {
  name: "ClassDetail",
  components: {
    AdminHeader,
    GNB,
    ChewingFooter,
  },
  methods: {
    goToCurriculum() {
      this.$router.push("/curriculum");
    },
  },
};
</script>

<style scoped>
.class-detail-page {
  width: 100%;
  background: #f9f9f9;
}

.carousel-section {
  display: flex;
  justify-content: center;
  gap: 16px;
  background: #f2f2f2;
  padding: 16px 0;
}

.carousel-slide {
  background: #dcdcdc;
  border-radius: 10px;
}

.main-slide {
  width: 600px;
  height: 280px;
  border-radius: 10px;
  overflow: hidden;
}

.slide-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  border-radius: 10px;
}


.content-wrapper {
  display: flex;
  flex-direction: column;
  padding: 24px 64px;
  background: white;
}

.tabs {
  display: flex;
  border-bottom: 2px solid #ddd;
  margin-bottom: 24px;
}

.tab {
  margin-right: 32px;
  padding: 12px;
  font-weight: 600;
  color: #888;
  cursor: pointer;
}

.tab.active {
  color: black;
  border-bottom: 3px solid black;
}

.main-content {
  display: flex;
  justify-content: space-between;
  gap: 40px;
}

.description {
  flex: 1;
  background: #f3f3f3;
  padding: 24px;
  border-radius: 8px;
}

.info-box {
  width: 280px;
  padding: 24px;
  background: #f7f7f7;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.tag {
  background-color: #d8eafd;
  color: #003366;
  font-size: 12px;
  padding: 4px 8px;
  border-radius: 16px;
  width: fit-content;
}

.info-box h2 {
  font-size: 18px;
  line-height: 1.4;
}

.info-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.info-list li {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  font-size: 14px;
}

.icon {
  width: 20px;
  height: 20px;
}

.bookmark-icon {
  width: 24px;
  height: 24px;
  align-self: flex-end;
  cursor: pointer;
}

.price-box {
  display: flex;
  align-items: baseline;
  gap: 8px;
}

.price-box del {
  color: #bbb;
  font-size: 14px;
}

.discount {
  color: #e60023;
  font-weight: bold;
}

.price-box strong {
  font-size: 18px;
}

.buy-button {
  padding: 12px;
  background-color: #3578e5;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}
</style>
