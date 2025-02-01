<template>
  <div class="class-card">
    <h3>{{ classItem.title }}</h3>
    <p>강사: {{ classItem.teacher }}</p>
    <p>
      <span class="original-price">{{ classItem.originalPrice }}원</span>
      <span class="discount-price">{{ classItem.price }}원</span>
    </p>
  </div>
</template>

<script>
export default {
  name: "ClassCard",
  props: {
    classItem: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>
.class-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  text-align: center;
  margin: 8px;
  font-family: Arial, sans-serif;
}
.original-price {
  text-decoration: line-through;
  color: #999;
}
.discount-price {
  color: red;
  font-weight: bold;
}
</style>
