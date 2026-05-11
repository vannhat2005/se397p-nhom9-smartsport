<template>
  <header class="top-chu-san">
    <div class="top-left">
      <h2 v-if="pageTitle" class="page-title">
        {{ pageTitle }}
      </h2>
    </div>

    <div class="top-right">
      <button class="icon-button">
        <span class="material-symbols-outlined">notifications</span>
        <span class="notification-dot"></span>
      </button>

      <div class="divider"></div>

      <router-link
        to="/chu-san/thong-tin-ca-nhan"
        class="profile"
        :class="{ active: isProfileActive }"
      >
        <div class="profile-info">
          <p class="profile-name">Admin Sport</p>
          <p class="profile-role">Quản lý cơ sở</p>
        </div>

        <img
          class="avatar"
          src="https://lh3.googleusercontent.com/aida-public/AB6AXuAcucUDtlvHqPhGmdDPJ-k_vuzBQ7BI87xbC9OgXWd5rulEdrLuMUlbOs6ToBGnKEVq19Y-_w2nTXVAul2xRrk3SjyCd6XCPfy0oYd0CpK1G8Vkv_xKlgb1JpFuDLxNj1Mumq9uY4DhId6j-zlcp-WR49sogVWTB2fT8zBglHNR7oHxR0KxWuF7OPzFhGBldRxrAyJbgKIDNGpbj__8Drbj015gqQgdQy1HAoVRwgEt3PrDwHbXZvjRSQhmO9C8bJ7StNyGTxCHuJY"
          alt="User profile"
        />
      </router-link>
    </div>
  </header>
</template>

<script>
export default {
  name: "TopChuSan",

  computed: {
    pageTitle() {
      return this.$route.meta.topTitle || "";
    },

    isProfileActive() {
      return this.$route.path === "/chu-san/thong-tin-ca-nhan";
    },
  },
};
</script>

<style scoped>
.top-chu-san {
  position: fixed;
  top: 0;
  left: 280px;
  right: 0;
  height: 64px;
  z-index: 30;
  background: #ffffff;
  border-bottom: 1px solid #e2e8f0;
  box-shadow: 0 1px 2px rgba(15, 23, 42, 0.06);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  font-family: "Inter", sans-serif;
}

.top-left {
  display: flex;
  align-items: center;
  flex: 1;
}

.page-title {
  margin: 0;
  color: #0f172a;
  font-size: 20px;
  line-height: 28px;
  font-weight: 600;
}

.top-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.icon-button {
  position: relative;
  border: none;
  background: transparent;
  color: #475569;
  padding: 8px;
  border-radius: 999px;
  cursor: pointer;
  transition: 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon-button:hover {
  background: #f8fafc;
  color: #16a34a;
}

.notification-dot {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 8px;
  height: 8px;
  background: #ba1a1a;
  border: 2px solid #ffffff;
  border-radius: 999px;
}

.divider {
  width: 1px;
  height: 32px;
  background: #e2e8f0;
}

.profile {
  display: flex;
  align-items: center;
  gap: 12px;
  text-decoration: none;
  border-radius: 999px;
  padding: 4px 6px 4px 12px;
  transition: 0.2s;
}

.profile:hover {
  background: #f8fafc;
}

.profile.active {
  background: #f0fdf4;
}

.profile-info {
  text-align: right;
}

.profile-name {
  margin: 0;
  font-size: 14px;
  font-weight: 600;
  color: #0f172a;
  line-height: 1.2;
}

.profile-role {
  margin: 0;
  margin-top: 2px;
  font-size: 10px;
  color: #64748b;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.06em;
}

.profile.active .profile-name {
  color: #006e2f;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 999px;
  object-fit: cover;
  border: 1px solid #e2e8f0;
}

@media (max-width: 768px) {
  .top-chu-san {
    left: 0;
    padding: 0 16px;
  }

  .profile-info {
    display: none;
  }
}
</style>