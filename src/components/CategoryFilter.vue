<template>
    <div class="category-filter">
      <div class="categories">
        <button v-for="(category, index) in categories" :key="index" :class="{ active: index === 0 }">
          {{ category }}
        </button>
      </div>
      <div class="info-section">
        <span class="course-count">{{ totalCourses }}개</span>
        <button class="filter-button">
          <img src="@/assets/filter.png" alt="필터" class="filter-icon" />
        </button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      totalCourses: {
        type: Number,
        default: 12, // 기본값 설정
      },
    },
    data() {
      return {
        categories: ["전체", "카테고리", "카테고리", "카테고리", "카테고리", "카테고리"],
      };
    },
  };
  </script>
  
  <style scoped>
  .category-filter {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    border-bottom: 1px solid #ddd;
    margin-bottom: 20px;
  }
  
  .categories {
    display: flex;
    gap: 16px;
  }
  
  .categories button {
    background: none;
    border: none;
    font-size: 16px;
    color: #333;
    cursor: pointer;
    padding: 10px;
  }
  
  .categories .active {
    font-weight: bold;
    border-bottom: 2px solid black;
  }
  
  .info-section {
    display: flex;
    align-items: center;
    gap: 20px;
  }
  
  .course-count {
    font-size: 14px;
    color: #555;
  }
  
  .filter-button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 5px;
  }
  
  .filter-icon {
    width: 20px;
    height: 20px;
  }
  </style>
  