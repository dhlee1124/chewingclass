<template>
  <div class="class-detail">
    <h1>{{ classDetail.title }}</h1>
    <p>{{ classDetail.description }}</p>
    <ul>
      <li v-for="(topic, index) in classDetail.topics" :key="index">{{ topic }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "ClassDetail",
  data() {
    return {
      classDetail: {
        title: "Mathematics",
        description: "This course covers the basics of algebra and geometry.",
        topics: ["Algebra", "Geometry", "Trigonometry"],
      },
    };
  },
};
</script>

<style scoped>
.class-detail {
  padding: 20px;
}
</style>
