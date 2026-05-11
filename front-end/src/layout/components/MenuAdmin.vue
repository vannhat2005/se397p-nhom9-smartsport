<template>
  <aside class="sidebar-admin">
    <div class="sidebar-logo">
      <div>
        <h1 class="logo-title">SmartSport</h1>
        <p class="logo-subtitle">Admin Management</p>
      </div>
    </div>

    <nav class="sidebar-menu">
      <router-link
        to="/admin/trang-chu"
        class="menu-item"
        active-class="active"
      >
        <i class="fa-solid fa-chart-line menu-icon"></i>
        <span>Dashboard</span>
      </router-link>

      <router-link
        to="/admin/quan-ly-nguoi-dung"
        class="menu-item"
        active-class="active"
      >
        <i class="fa-solid fa-users menu-icon"></i>
        <span>Quản lý người dùng</span>
      </router-link>

      <router-link
        to="/admin/quan-ly-loai-san"
        class="menu-item"
        active-class="active"
      >
        <i class="fa-solid fa-layer-group menu-icon"></i>
        <span>Quản lý loại sân</span>
      </router-link>

      <router-link
        to="/admin/duyet-san"
        class="menu-item"
        active-class="active"
      >
        <i class="fa-solid fa-futbol menu-icon"></i>
        <span>Duyệt sân</span>
      </router-link>
    </nav>

    <div class="sidebar-bottom">
      <a href="#" class="menu-item">
        <i class="fa-solid fa-right-from-bracket menu-icon"></i>
        <span>Đăng xuất</span>
      </a>
    </div>
  </aside>
</template>

<script>
export default {
  name: "SidebarAdmin",
};
</script>

<style scoped>
.sidebar-admin {
  position: fixed;
  left: 0;
  top: 0;
  width: 280px;
  height: 100vh;
  z-index: 50;
  background: #0f172a;
  border-right: 1px solid #1e293b;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
  padding: 16px;
  display: flex;
  flex-direction: column;
  font-family: "Inter", sans-serif;
}

.sidebar-logo {
  padding: 24px 16px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-title {
  margin: 0;
  font-size: 24px;
  font-weight: 700;
  color: #22c55e;
  letter-spacing: -0.5px;
}

.logo-subtitle {
  margin: 0;
  margin-top: 2px;
  font-size: 12px;
  font-weight: 500;
  color: #64748b;
  text-transform: uppercase;
  letter-spacing: 1.5px;
}

.sidebar-menu {
  flex: 1;
  margin-top: 32px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  border-radius: 12px;
  color: #94a3b8;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  transition: 0.2s;
}

.menu-icon {
  width: 20px;
  font-size: 16px;
  text-align: center;
}

.menu-item:hover {
  background: #1e293b;
  color: #ffffff;
}

.menu-item.active {
  background: #16a34a;
  color: #ffffff;
  box-shadow: 0 10px 20px rgba(20, 83, 45, 0.2);
}

.sidebar-bottom {
  padding-top: 24px;
  border-top: 1px solid #1e293b;
}
</style>