<template>
  <aside class="sidebar-chu-san">
    <div class="sidebar-brand">
      <div class="brand-title">SmartSport</div>
      <div class="brand-subtitle">Management System</div>
    </div>

    <nav class="sidebar-menu">
      <router-link
        to="/chu-san/thong-ke"
        class="menu-item"
        active-class="active"
      >
        <span class="material-symbols-outlined">analytics</span>
        <span>Thống kê</span>
      </router-link>
      <router-link
        to="/chu-san/quan-ly-san"
        class="menu-item"
        active-class="active"
      >
        <span class="material-symbols-outlined">stadium</span>
        <span>Quản lý sân</span>
      </router-link>

      <router-link
        to="/chu-san/gia-san"
        class="menu-item"
        active-class="active"
      >
        <span class="material-symbols-outlined">payments</span>
        <span>Giá sân</span>
      </router-link>

      <router-link
        to="/chu-san/booking"
        class="menu-item"
        active-class="active"
      >
        <span class="material-symbols-outlined">event_available</span>
        <span>Booking</span>
      </router-link>
      <router-link
        to="/chu-san/thong-tin-ca-nhan"
        class="menu-item"
        active-class="active"
      >
        <span class="material-symbols-outlined">person</span>
        <span>Profile</span>
      </router-link>
    </nav>

    <div class="sidebar-bottom">
      <a href="#" class="menu-item">
        <span class="material-symbols-outlined">logout</span>
        <span>Đăng xuất</span>
      </a>
    </div>
  </aside>
</template>

<script>
export default {
  name: "MenuChuSan",
};
</script>

<style scoped>
.sidebar-chu-san {
  position: fixed;
  left: 0;
  top: 0;
  width: 280px;
  height: 100vh;
  background: #0f172a;
  border-right: 1px solid #1e293b;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
  display: flex;
  flex-direction: column;
  padding: 24px 0;
  z-index: 40;
  font-family: "Inter", sans-serif;
}

.sidebar-brand {
  padding: 0 24px;
  margin-bottom: 32px;
}

.brand-title {
  font-size: 24px;
  font-weight: 900;
  color: #22c55e;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.brand-subtitle {
  margin-top: 4px;
  font-size: 12px;
  font-weight: 500;
  color: #94a3b8;
  text-transform: uppercase;
  letter-spacing: 1.5px;
}

.sidebar-menu {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.menu-item {
  margin: 0 8px;
  padding: 12px 16px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 12px;
  color: #94a3b8;
  text-decoration: none;
  font-size: 15px;
  font-weight: 500;
  transition: 0.2s;
}

.menu-item:hover {
  background: #1e293b;
  color: #ffffff;
}

.menu-item.active {
  background: #16a34a;
  color: #ffffff;
}

.sidebar-bottom {
  margin-top: auto;
  padding-top: 16px;
  border-top: 1px solid #1e293b;
}
</style>