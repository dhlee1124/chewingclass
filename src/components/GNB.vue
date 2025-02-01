<template>
  <nav class="gnb">
    <ul class="gnb-menu">
      <li class="gnb-item">카테고리 ▼</li>
      <li class="gnb-item">인기 클래스</li>
      <li class="gnb-item">이벤트</li>
      <li class="gnb-item">게시판</li>
      <li class="gnb-item">공지사항</li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "GNB",
};
</script>

<style scoped>
.gnb {
  display: flex;
  justify-content: flex-start; /* 왼쪽 정렬 */
  padding: 10px 20px;
  background-color: #e7f1ff;
}

.gnb-menu {
  display: flex;
  gap: 20px;
  list-style: none;
  padding: 0;
  margin: 0;
}

.gnb-item {
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
}
</style>
