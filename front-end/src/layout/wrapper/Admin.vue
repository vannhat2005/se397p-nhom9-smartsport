<template>
  <div class="layout-admin">
    <MenuAdmin />

    <TopAdmin :placeholder="searchPlaceholder" />

    <main class="admin-content">
      <router-view />
    </main>
  </div>
</template>

<script>
import MenuAdmin from "../components/MenuAdmin.vue";
import TopAdmin from "../components/TopAdmin.vue";

export default {
  name: "AdminLayout",

  components: {
    MenuAdmin,
    TopAdmin,
  },

  computed: {
    searchPlaceholder() {
      return this.$route.meta.searchPlaceholder || "Tìm kiếm hệ thống...";
    },
  },
};
</script>

<style scoped>
.layout-admin {
  min-height: 100vh;
  background: #f8f9fa;
}

.admin-content {
  margin-top: 20px;
  margin-left: 280px;

  padding-top: 72px;
  min-height: 100vh;
  padding-left: 24px;
  padding-right: 24px;
  padding-bottom: 24px;
}
</style>