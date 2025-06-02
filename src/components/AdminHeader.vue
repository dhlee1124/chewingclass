<template>
  <header class="app-header">
    <div class="logo" @click="goToHome">
      <img src="@/assets/logo.png" alt="Chewing Class" />
    </div>

    <div class="search-container">
      <img src="@/assets/icon-search.svg" alt="검색" class="search-icon" />
      <input
        type="text"
        placeholder="어떤 클래스를 찾으시나요?"
        class="search-input"
        @focus="openSearch"
      />
      <div v-if="isSearchActive" class="overlay" @click="closeSearch"></div>
      <div v-if="isSearchActive" class="search-dropdown">
        <p>추천 검색어</p>
        <div class="search-tags">
          <span
            v-for="(tag, index) in searchTags"
            :key="index"
            class="tag"
            @click="selectTag(tag)"
          >
            {{ tag }}
          </span>
        </div>
      </div>
    </div>

    <div class="icons">
        <img src="@/assets/icon-bell.svg" alt="알림" class="icon" />
        <img src="@/assets/icon-chat.svg" alt="채팅" class="icon" />
         <img
        src="@/assets/profile.png"
        alt="프로필"
        class="icon profile-icon"
        @click="goToMyPage"
      />
      </div>
  </header>
</template>

<script>


export default {
  name: "AppHeader",
  methods: {
    goToMyPage() {
      this.$router.push("/my-page");
    },
    goToHome() {
      this.$router.push("/admin-dashboard");
    },
  },
  
  data() {
    return {
      isLoggedIn: false,
      isSearchActive: false,
      searchTags: [
        "블렌더", "캐릭터 디자인", "일러스트", "포토샵",
        "이모티콘", "서비스 기획", "드로잉", "베이킹",
        "파이썬", "프로그래밍"
      ],
    };
  },
  
};
</script>

<style scoped>
.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #e7f1ff;
  padding: 0 20px;
  height: 60px;
  width: 100vw;
}

.logo {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.search-container {
  position: relative;
}

.search-input {
  width: 500px;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 20px;
  outline: none;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 50;
}

.search-dropdown {
  position: absolute;
  top: 40px;
  left: 0;
  background: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 12px;
  border-radius: 8px;
  width: 320px;
  z-index: 100;
}

.search-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.tag {
  background-color: #f5f5f5;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
}

.header-actions {
  display: flex;
  align-items: center;
}

..icons {
  display: flex;
  align-items: center;
  gap: 16px;
}

.icon {
  width: 24px;
  height: 24px;
  object-fit: contain;
  cursor: pointer;
}

.icon.profile {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}
</style>
