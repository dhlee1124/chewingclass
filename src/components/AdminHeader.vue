<template>
  <header class="app-header">
    <div class="logo" @click="goToHome">
      <img src="@/assets/logo.png" alt="Chewing Class" />
    </div>

    <div class="search-container">
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

    <div class="header-actions">
      <template v-if="isLoggedIn">
        <button class="icon-btn">
          <img src="@/assets/logo.png" alt="Chewing Class" />
        </button>
        <button class="icon-btn">
          <MessageSquareIcon />
        </button>
        <button class="profile-btn" @click="goToMyPage">
          <img src="@/assets/profile.png" alt="Profile" />
        </button>
      </template>
    </div>
  </header>
</template>

<script>
import { MessageSquareIcon } from "lucide-vue";

export default {
  name: "AppHeader",
  components: {
    MessageSquareIcon,
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
  methods: {
    goToHome() {
      this.$router.push("/");
    },
    goToMyPage() {
      this.$router.push("/mypage");
    },
    openSearch() {
      this.isSearchActive = true;
    },
    closeSearch() {
      this.isSearchActive = false;
    },
    selectTag(tag) {
      console.log("검색어 선택:", tag);
    }
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

.icon-btn {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  margin-left: 15px;
}

.profile-btn img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  cursor: pointer;
}
</style>
