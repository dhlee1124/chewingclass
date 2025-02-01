<template>
  <div class="dashboard">
    <div class="dashboard-header">
      <AppHeader />
      <GNB />
      <router-view />
    </div>


    <!-- 🔹 강의 리스트 (이미지 파일로 대체) -->
    <section class="classes-section">
      <h2 class="clickable-header" @click="goToRecommendClass">츄잉 클래스 EVENT</h2>
      <div class="class-list clickable-box" @click="goToRecommendClass">
        <div v-for="(image, index) in classImages" :key="index" class="class-item">
          <img :src="image" alt="추천 클래스" class="class-image" />
        </div>
      </div>
    </section>

    <ChewingFooter />
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";
import GNB from "@/components/GNB.vue";
import ChewingFooter from "@/components/ChewingFooter.vue";

export default {
  name: "Dashboard",
  components: {
    AppHeader,
    GNB,
    ChewingFooter,
  },
  data() {
    return {

      // 🔹 강의 리스트 (이미지로 대체)
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

/* 🔹 강의 리스트 스타일 */
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
}

.clickable-header {
  cursor: pointer;
}

.clickable-box {
  cursor: pointer;
}

.class-image {
  width: 100%;
  height: auto;
  display: block;
}
</style>
