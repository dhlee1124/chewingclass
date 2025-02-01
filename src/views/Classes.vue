<template>
  <div class="classes-page">
    <h1>Classes</h1>
    <div class="classes-list">
      <div class="class-card" v-for="(classItem, index) in classes" :key="index">
        <h3>{{ classItem.title }}</h3>
        <p>{{ classItem.description }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Classes",
  data() {
    return {
      classes: [
        { title: "Mathematics", description: "Learn the basics of algebra and geometry." },
        { title: "Science", description: "Explore physics, chemistry, and biology." },
        { title: "History", description: "Dive into world history and ancient civilizations." },
      ],
    };
  },
};
</script>

<style scoped>
.classes-page {
  padding: 20px;
}
.classes-list {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}
.class-card {
  background: #fff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  flex: 1 1 calc(33.333% - 20px);
}
</style>
