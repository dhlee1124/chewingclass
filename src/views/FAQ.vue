<template>
  <div class="faq">
    <h1>FAQ</h1>
    <ul>
      <li v-for="(faq, index) in faqs" :key="index">
        <strong>{{ faq.question }}</strong>
        <p>{{ faq.answer }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "FAQ",
  data() {
    return {
      faqs: [
        { question: "How do I join a class?", answer: "Go to the Classes page and select a class to join." },
        { question: "How can I contact support?", answer: "Email us at support@example.com." },
        { question: "Where can I find my assignments?", answer: "Check the Notifications page for assignment updates." },
      ],
    };
  },
};
</script>

<style scoped>
.faq {
  padding: 20px;
}
</style>
