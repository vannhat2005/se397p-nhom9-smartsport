<template>
  <div class="layout">
    <!-- Thanh menu -->
    <MenuClient />

    <!-- Nội dung chính của các trang -->
    <main class="main-content">
      <router-view />
    </main>

    <!-- Footer -->
    <BotClient />

    <!-- Chatbot -->
    <Chatbot />
  </div>
</template>

<script>
import MenuClient from "../components/MenuClient.vue";
import BotClient from "../components/BotClient.vue";
import Chatbot from "../components/Chatbot.vue";

export default {
  name: "ClientLayout",

  components: {
    MenuClient,
    BotClient,
    Chatbot,
  },
};
</script>

<style scoped>
.layout {
  min-height: 100vh;
  background-color: #fbf8ff;
}

.main-content {
  padding-top: 80px;
  min-height: 100vh;
  background-color: #fbf8ff;
}
</style>