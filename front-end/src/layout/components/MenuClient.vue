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

        <!-- Dropdown tài khoản -->
        <div class="account-dropdown" @click.stop="toggleAccountDropdown">
          <button
            type="button"
            class="account-button"
            :class="{ active: isProfileActive }"
          >
            <span class="material-symbols-outlined header-icon">
              account_circle
            </span>

            <span class="material-symbols-outlined arrow-icon">
              expand_more
            </span>
          </button>

          <div v-if="isAccountDropdownOpen" class="dropdown-menu-client">
            <router-link
              to="/auth/login"
              class="dropdown-item-client"
              @click="closeAccountDropdown"
            >
              <span class="material-symbols-outlined">login</span>
              Đăng nhập
            </router-link>

            <router-link
              to="/client/thong-tin-ca-nhan"
              class="dropdown-item-client"
              @click="closeAccountDropdown"
            >
              <span class="material-symbols-outlined">person</span>
              Xem thông tin cá nhân
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "MenuClient",

  data() {
    return {
      isAccountDropdownOpen: false,
    };
  },

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

  mounted() {
    document.addEventListener("click", this.closeAccountDropdown);
  },

  beforeUnmount() {
    document.removeEventListener("click", this.closeAccountDropdown);
  },

  methods: {
    toggleAccountDropdown() {
      this.isAccountDropdownOpen = !this.isAccountDropdownOpen;
    },

    closeAccountDropdown() {
      this.isAccountDropdownOpen = false;
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

/* Account dropdown */
.account-dropdown {
  position: relative;
}

.account-button {
  border: none;
  background: transparent;
  color: #475569;
  display: flex;
  align-items: center;
  gap: 2px;
  cursor: pointer;
  padding: 0;
}

.account-button:hover {
  color: #003ec7;
}

.account-button.active {
  color: #2563eb;
}

.account-button.active .header-icon {
  color: #2563eb;
  font-variation-settings: "FILL" 1;
}

.arrow-icon {
  font-size: 18px;
}

.dropdown-menu-client {
  position: absolute;
  top: 36px;
  right: 0;
  width: 220px;
  background: #ffffff;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  box-shadow: 0 12px 30px rgba(15, 23, 42, 0.16);
  padding: 8px;
  z-index: 100;
}

.dropdown-item-client {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  border-radius: 10px;
  color: #475569;
  text-decoration: none;
  font-size: 14px;
  font-weight: 600;
  transition: 0.2s;
}

.dropdown-item-client:hover {
  background: #eff6ff;
  color: #2563eb;
}

.dropdown-item-client .material-symbols-outlined {
  font-size: 20px;
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

  .dropdown-menu-client {
    right: -8px;
  }
}
</style>