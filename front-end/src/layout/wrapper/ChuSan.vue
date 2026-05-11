<template>
  <div class="layout-chu-san">
    <MenuChuSan />

    <TopChuSan :placeholder="searchPlaceholder" />

    <main class="chu-san-content">
      <router-view />
    </main>
  </div>
</template>

<script>
import MenuChuSan from "../components/MenuChuSan.vue";
import TopChuSan from "../components/TopChuSan.vue";

export default {
  name: "ChuSanLayout",

  components: {
    MenuChuSan,
    TopChuSan,
  },

  computed: {
    searchPlaceholder() {
      return this.$route.meta.searchPlaceholder || "Tìm kiếm sân vận động...";
    },
  },
};
</script>

<style scoped>
.layout-chu-san {
  min-height: 100vh;
  background: #f8f9ff;
}

.chu-san-content {
  margin-left: 310px;
  margin-top: 20px;
  padding-top: 64px;
  min-height: 100vh;
  background: #f8f9ff;
}

.chu-san-content :deep(.page-content) {
  padding: 32px;
}
</style>