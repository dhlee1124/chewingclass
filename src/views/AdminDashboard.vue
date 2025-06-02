<template>
  <div class="dashboard">
    <AdminHeader />
    <GNB />

    <!-- 🔹 이미지 슬라이더 -->
    <section class="image-slider">
      <div class="carousel-section">
  <div class="carousel-slide main-slide">
    <img src="@/assets/banner.jpg" alt="배너" class="slide-img" />
  </div>
  <div class="carousel-slide side-slide left"></div>
  <div class="carousel-slide side-slide right"></div>
</div>
    </section>

    <!-- 🔹 강의 리스트 -->
    <section class="classes-section" v-for="(title, i) in sectionTitles" :key="i">
      <h2>{{ title }}</h2>
      <div class="class-list">
        <div
          class="class-card"
          v-for="(classData, j) in classList"
          :key="j"
          @click="goToClassDetail(classData.id)"
        >
          <img :src="classData.image" alt="class" class="class-thumbnail" />
          <div class="class-info">
            <p class="class-title">{{ classData.title }}</p>
            <p class="instructor">{{ classData.instructor }}</p>
            <div class="rating">
              <span class="stars">⭐⭐⭐⭐☆</span>
            </div>
            <div class="price">
              <span class="original">400,000원</span>
              <span class="discount">20%</span>
              <span class="final">320,000원</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <ChewingFooter />
  </div>
</template>

<script>
import AdminHeader from "@/components/AdminHeader.vue";
import GNB from "@/components/GNB.vue";

import "swiper/css";
import "swiper/css/navigation";
import ChewingFooter from "@/components/ChewingFooter.vue";

export default {
  name: "Dashboard",
  components: {
    AdminHeader,
    GNB,
 
    ChewingFooter,
  },
  data() {
    return {
      images: [
        require("@/assets/banner.jpg"),
        require("@/assets/banner.jpg"),
        require("@/assets/banner.jpg"),
      ],
      sectionTitles: [
        "츄잉님을 위한 추천 클래스",
        "츄잉 BEST 클래스",
        "츄잉 신규 클래스",
      ],
      classList: [
        {
          id: 1,
          image: require("@/assets/class.png"),
          title: "역할로 표현하는 다이내믹 캐릭터 핵썹 일러스트",
          instructor: "강사 이름",
        },
        {
          id: 2,
          image: require("@/assets/class.png"),
          title: "역할로 표현하는 다이내믹 캐릭터 핵썹 일러스트",
          instructor: "강사 이름",
        },
        {
          id: 3,
          image: require("@/assets/class.png"),
          title: "역할로 표현하는 다이내믹 캐릭터 핵썹 일러스트",
          instructor: "강사 이름",
        },
        {
          id: 4,
          image: require("@/assets/class.png"),
          title: "역할로 표현하는 다이내믹 캐릭터 핵썹 일러스트",
          instructor: "강사 이름",
        },
      ],
    };
  },
  methods: {
    goToClassDetail(id) {
      this.$router.push({ name: "ClassDetail", params: { id } });
    },
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
  padding: 30px 60px;
}

.class-list {
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
  margin-top: 20px;
}

.class-card {
  width: 240px;
  cursor: pointer;
  background: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease;
}

.class-card:hover {
  transform: translateY(-4px);
}

.class-thumbnail {
  width: 100%;
  height: 160px;
  object-fit: cover;
}

.class-info {
  padding: 12px;
}

.class-title {
  font-weight: bold;
  font-size: 14px;
  margin: 4px 0;
}

.instructor {
  font-size: 12px;
  color: #555;
}

.rating {
  font-size: 14px;
  margin-top: 6px;
}

.price {
  margin-top: 6px;
  font-size: 14px;
}

.original {
  text-decoration: line-through;
  color: #999;
  margin-right: 4px;
}

.discount {
  color: red;
  font-weight: bold;
  margin-right: 4px;
}

.final {
  font-weight: bold;
}
</style>