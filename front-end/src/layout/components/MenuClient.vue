<template>
  <header class="app-header">
    <div class="header-container">
      <div class="header-left">
        <router-link to="/" class="logo">
          SmartSport
        </router-link>

        <nav class="nav-menu">
          <router-link
            to="/"
            class="nav-link"
            :class="{ active: isSanVanDongActive }"
          >
            Sân vận động
          </router-link>

          <router-link
            to="/client/lich-su-dat-san"
            class="nav-link"
            :class="{ active: isLichDatActive }"
          >
            Lịch đặt của tôi
          </router-link>

          <a class="nav-link" href="#">
            Cơ sở vật chất
          </a>

          <a class="nav-link" href="#">
            Hỗ trợ
          </a>
        </nav>
      </div>

      <div class="header-right">
        <span class="material-symbols-outlined header-icon">
          notifications
        </span>

        <span class="material-symbols-outlined header-icon">
          chat_bubble
        </span>

        <router-link
          to="/client/thong-tin-ca-nhan"
          class="account-link"
          :class="{ active: isProfileActive }"
        >
          <span class="material-symbols-outlined header-icon">
            account_circle
          </span>
        </router-link>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "MenuClient",

  computed: {
    currentPath() {
      return this.$route.path;
    },

    isSanVanDongActive() {
      return (
        this.currentPath === "/" ||
        this.currentPath.startsWith("/client/xem-chi-tiet")
      );
    },

    isLichDatActive() {
      return this.currentPath === "/client/lich-su-dat-san";
    },

    isProfileActive() {
      return this.currentPath === "/client/thong-tin-ca-nhan";
    },
  },
};
</script>

<style scoped>
.app-header {
  background: #ffffff;
  border-bottom: 1px solid #e2e8f0;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  padding: 16px 40px;
  z-index: 50;
}

.header-container {
  max-width: 1920px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 48px;
}

.logo {
  font-size: 24px;
  font-weight: 900;
  color: #2563eb;
  letter-spacing: -1px;
  text-decoration: none;
}

.nav-menu {
  display: flex;
  align-items: center;
  gap: 32px;
}

.nav-link {
  font-size: 14px;
  font-weight: 500;
  color: #475569;
  text-decoration: none;
  transition: 0.2s;
  padding-bottom: 4px;
  border-bottom: 2px solid transparent;
}

.nav-link:hover {
  color: #1d4ed8;
}

.nav-link.active {
  color: #2563eb;
  font-weight: 700;
  border-bottom-color: #2563eb;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
  color: #475569;
}

.header-icon {
  cursor: pointer;
  transition: 0.2s;
}

.header-icon:hover {
  color: #003ec7;
}

.account-link {
  color: #475569;
  text-decoration: none;
  display: flex;
  align-items: center;
}

.account-link.active {
  color: #2563eb;
}

.account-link.active .header-icon {
  color: #2563eb;
  font-variation-settings: "FILL" 1;
}

@media (max-width: 768px) {
  .app-header {
    padding: 14px 20px;
  }

  .nav-menu {
    display: none;
  }

  .header-left {
    gap: 20px;
  }
}
</style>