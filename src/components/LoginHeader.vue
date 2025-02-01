<template>
    <header class="login-header">
      <div class="logo" @click="goToHome">
        <img src="@/assets/logo.png" alt="Chewing Class" />
      </div>
    </header>
  </template>
  
  <script>
  export default {
    name: "LoginHeader",
    methods: {
      goToHome() {
        this.$router.push("/");
      },
    },
  };
  </script>
  
  <style scoped>


.login-header {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background-color: #e7f1ff; /* 헤더 배경색 */
  padding: 10px 0;
  display: flex;
  align-items: center;
  justify-content: flex-start;
   padding-left: 20px;
}
.logo {
  display: flex;
  align-items: center;
  cursor: pointer;
}

  </style>
  