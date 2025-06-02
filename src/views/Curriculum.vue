<template>
  <div class="curriculum-page">
    <AdminHeader />
    <GNB />

    <!-- 탭 -->
    <div class="tabs">
      <div class="tab">클래스 소개</div>
      <div class="tab active">커리큘럼</div>
      <div class="tab">강사소개</div>
      <div class="tab">클래스 후기</div>
      <div class="tab">추천</div>
    </div>

    <div class="page-content">
      <!-- 커리큘럼 목록 -->
      <div class="curriculum-list">
        <h2>커리큘럼</h2>
        <div v-for="(section, idx) in sections" :key="idx" class="section">
          <div class="section-header">
            <strong>{{ section.title }}</strong>
            <span>{{ section.count }}개 | {{ section.time }}</span>
          </div>
          <ul class="lecture-list">
            <li v-for="(lecture, lidx) in section.lectures" :key="lidx">
              {{ lecture.title }}
              <span class="duration">{{ lecture.duration }}</span>
            </li>
          </ul>
        </div>
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

    <ChewingFooter />
  </div>
</template>

<script>
import AdminHeader from "@/components/AdminHeader.vue";
import GNB from "@/components/GNB.vue";
import ChewingFooter from "@/components/ChewingFooter.vue";

export default {
  name: "ClassCurriculum",
  components: { AdminHeader, GNB, ChewingFooter },
  data() {
    return {
      sections: [
        {
          title: "SECTION 01. 사용자 데이터로 할 수 있는 것들",
          count: 5,
          time: "40:48",
          lectures: [
            { title: "1-1. 디자이너가 데이터를 알아야 하는 이유", duration: "09:15" },
            { title: "1-2. 데이터는 어느 단계에서 필요할까", duration: "09:15" },
            { title: "1-3. 내부 데이터를 수집하는 방법", duration: "09:15" },
            { title: "1-4. 외부 데이터를 수집하는 방법", duration: "09:15" },
            { title: "1-5. 데이터로 커뮤니케이션하는 4가지 원칙", duration: "09:15" },
          ],
        },
        {
          title: "SECTION 02. 사용자 데이터로 할 수 있는 것들",
          count: 5,
          time: "40:48",
          lectures: [
            { title: "2-1. 디자이너가 데이터를 알아야 하는 이유", duration: "09:15" },
            { title: "2-2. 데이터는 어느 단계에서 필요할까", duration: "09:15" },
            { title: "2-3. 내부 데이터를 수집하는 방법", duration: "09:15" },
            { title: "2-4. 외부 데이터를 수집하는 방법", duration: "09:15" },
            { title: "2-5. 데이터로 커뮤니케이션하는 4가지 원칙", duration: "09:15" },
          ],
        },
      ],
    };
  },
};
</script>

<style scoped>
.curriculum-page {
  background-color: #f9f9f9;
}

.tabs {
  display: flex;
  border-bottom: 1px solid #ccc;
  padding: 20px 120px 0;
  background: #fff;
}

.tab {
  margin-right: 40px;
  padding: 10px;
  font-weight: 600;
  color: #888;
  cursor: pointer;
}

.tab.active {
  color: #000;
  border-bottom: 3px solid black;
}

.page-content {
  display: flex;
  justify-content: space-between;
  padding: 40px 120px;
  background: white;
}

.curriculum-list {
  flex: 2;
  padding-right: 40px;
}

.section {
  margin-bottom: 32px;
}

.section-header {
  background-color: #f3f3f3;
  padding: 12px 16px;
  font-weight: 600;
  display: flex;
  justify-content: space-between;
  border-radius: 8px;
}

.lecture-list {
  list-style: none;
  padding-left: 16px;
  margin-top: 16px;
}

.lecture-list li {
  display: flex;
  justify-content: space-between;
  padding: 6px 0;
  font-size: 14px;
}

.duration {
  color: #888;
  font-size: 12px;
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

.info-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.info-list li {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  margin-bottom: 8px;
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
