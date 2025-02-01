<template>
  <div class="notifications">
    <h1>Notifications</h1>
    <ul>
      <li v-for="(notification, index) in notifications" :key="index">
        {{ notification }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Notifications",
  data() {
    return {
      notifications: [
        "Your Mathematics class starts in 10 minutes.",
        "Science assignment is due tomorrow.",
        "New announcement in History class.",
      ],
    };
  },
};
</script>

<style scoped>
.notifications {
  padding: 20px;
}
ul {
  list-style: none;
  padding: 0;
}
li {
  margin-bottom: 10px;
}
</style>
