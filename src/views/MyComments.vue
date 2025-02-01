<template>
    <div class="my-classes">
      <AppHeader />
      <div class="content">
        <!-- 사이드바 -->
        <aside class="sidebar">
          <div class="profile">
            <img src="@/assets/profile.png" alt="프로필 이미지" class="profile-img" />
            <p class="username">김츄잉</p>
          </div>
          <ul class="menu">
            <li class="active">내 클래스</li>
            <li>북마크한 클래스</li>
            <li>내가 작성한 글</li>
            <li>결제 / 환불 내역</li>
            <li>개인정보 관리</li>
          </ul>
        </aside>
  
        <!-- 메인 컨텐츠 -->
        <main class="main-content">
          <h2>내가 작성한 글글</h2>
          <nav class="tabs">
            <span class="active">후기기</span>
            <span>QnA</span>
          </nav>
  
          <div class="class-list">
            <!-- 여기에 수강중인 강의 목록 추가 예정 -->
            <p>수강 중인 클래스가 여기에 표시됩니다.</p>
          </div>
        </main>
      </div>
    </div>
  </template>
  
  <script>
  import AppHeader from "@/components/AppHeader.vue";
  
  export default {
    name: "MyClasses",
    components: {
      AppHeader,
    },
  };
  </script>
  
  <style scoped>
  .my-classes {
    display: flex;
    flex-direction: column;
    height: 100vh;
  }
  
  .content {
    display: flex;
    flex: 1;
  }
  
  .sidebar {
    width: 220px;
    background: #f8f8f8;
    padding: 20px;
  }
  
  .profile {
    text-align: center;
    margin-bottom: 20px;
  }
  
  .profile-img {
    width: 80px;
    height: 80px;
    border-radius: 50%;
  }
  
  .username {
    font-size: 16px;
    font-weight: bold;
    margin-top: 10px;
  }
  
  .menu {
    list-style: none;
    padding: 0;
  }
  
  .menu li {
    padding: 10px 0;
    cursor: pointer;
  }
  
  .menu .active {
    font-weight: bold;
    color: #000;
  }
  
  .main-content {
    flex: 1;
    padding: 30px;
    background: #ffffff;
  }
  
  h2 {
    font-size: 20px;
    margin-bottom: 20px;
  }
  
  .tabs {
    display: flex;
    gap: 20px;
    border-bottom: 2px solid #ddd;
    padding-bottom: 10px;
    margin-bottom: 20px;
  }
  
  .tabs span {
    cursor: pointer;
  }
  
  .tabs .active {
    font-weight: bold;
    border-bottom: 2px solid black;
  }
  
  .class-list {
    background: #f8f8f8;
    padding: 20px;
    text-align: center;
  }
  </style>
  