<template>
  <div class="dashboard">
    <div class="dashboard-header">
      <AdminHeader />
      <GNB />

      <!-- 🔹 이미지 아이콘 3개 추가 -->
      <div class="dashboard-icons">
        <img src="@/assets/icon-bell.svg" alt="알림" class="dashboard-icon" />
        <img src="@/assets/icon-chat.svg" alt="채팅" class="dashboard-icon" />
        <img src="@/assets/icon-profile.svg" alt="프로필" class="dashboard-icon" />
      </div>

      <router-view />
    </div>

    <!-- 🔹 이미지 슬라이더 추가 -->
    <section class="image-slider">
      <swiper
        :modules="[Navigation]"
        :navigation="true"
        :loop="true"
        class="swiper-container"
      >
        <swiper-slide v-for="(image, index) in images" :key="index">
          <img :src="image" class="slide-image" />
        </swiper-slide>
      </swiper>
    </section>

    <!-- 🔹 강의 리스트 (이미지 파일로 대체) -->
    <section class="classes-section">
      <h2 class="clickable-header" @click="goToRecommendClass">츄잉님을 위한 추천 클래스</h2>
      <div class="class-list clickable-box" @click="goToRecommendClass">
        <div v-for="(image, index) in classImages" :key="index" class="class-item">
          <img :src="image" alt="추천 클래스" class="class-image" />
        </div>
      </div>
      <h2>츄잉 BEST 클래스</h2>
      <div class="class-list">
        <div v-for="(image, index) in classImages" :key="index" class="class-item">
          <img :src="image" alt="추천 클래스" class="class-image" />
        </div>
      </div>
      <h2>츄잉 신규 클래스</h2>
      <div class="class-list">
        <div v-for="(image, index) in classImages" :key="index" class="class-item">
          <img :src="image" alt="추천 클래스" class="class-image" />
        </div>
      </div>
      <h2>카테고리별 인기 클래스</h2>
      <div class="class-list">
        <div v-for="(image, index) in classImages" :key="index" class="class-item">
          <img :src="image" alt="추천 클래스" class="class-image" />
        </div>
      </div>
    </section>

    <ChewingFooter />
  </div>
</template>

<script>
import AdminHeader from "@/components/AdminHeader.vue";
import GNB from "@/components/GNB.vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Navigation } from "swiper/modules";
import "swiper/css";
import "swiper/css/navigation";
import ChewingFooter from "@/components/ChewingFooter.vue";

export default {
  name: "Dashboard",
  components: {
    AdminHeader,
    GNB,
    ChewingFooter,
    Swiper,
    SwiperSlide,
  },
  data() {
    return {
      images: [
        require("@/assets/banner.jpg"),
        require("@/assets/banner.jpg"),
        require("@/assets/banner.jpg"),
      ],
      classImages: [
        require("@/assets/class.png"),
        require("@/assets/class.png"),
        require("@/assets/class.png"),
        require("@/assets/class.png"),
      ],
    };
  },
};
</script>

<style scoped>
.image-slider {
  width: 100%;
  height: 300px;
  overflow: hidden;
}

.swiper-container {
  width: 100%;
  height: 100%;
}

.slide-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  display: block;
}

.swiper-button-next,
.swiper-button-prev {
  color: white;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 10px;
  border-radius: 50%;
}

.classes-section {
  padding: 20px;
}

.class-list {
  display: flex;
  gap: 16px;
  overflow-x: auto;
  padding: 10px;
}

.class-item {
  width: 240px;
  flex-shrink: 0;
}

.class-image {
  width: 100%;
  height: auto;
  border-radius: 10px;
  display: block;
}

.clickable-header {
  cursor: pointer;
}

.clickable-box {
  cursor: pointer;
}

.dashboard-icons {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 16px;
  padding: 10px 20px;
}

.dashboard-icon {
  width: 32px;
  height: 32px;
  object-fit: contain;
}
</style>
