<template>
  <div class="admin-dashboard">
    <!-- Page Header -->
    <div class="page-header">
      <h2>Tổng quan hệ thống</h2>
      <p>
        Chào mừng trở lại, Administrator. Đây là tóm tắt hoạt động hôm nay.
      </p>
    </div>

    <!-- Bento Grid Stats Section -->
    <div class="stats-grid">
      <!-- Stat Card: Total Users -->
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-top">
            <div class="stat-icon green">
              <span class="material-symbols-outlined">group</span>
            </div>

            <span class="percent green-percent">+12.5%</span>
          </div>

          <div>
            <p class="stat-label">Total Users</p>
            <h3>{{ totalUsers }}</h3>
          </div>
        </div>

        <div class="stat-bar green-bar"></div>
      </div>

      <!-- Stat Card: Total Fields -->
      <div class="stat-card">
        <div class="stat-content">
          <div class="stat-top">
            <div class="stat-icon blue">
              <span class="material-symbols-outlined">stadium</span>
            </div>

            <span class="percent blue-percent">+4.2%</span>
          </div>

          <div>
            <p class="stat-label">Total Fields</p>
            <h3>{{ totalFields }}</h3>
          </div>
        </div>

        <div class="stat-bar blue-bar"></div>
      </div>
    </div>

    <!-- Welcome Visual Section -->
    <section class="system-banner">
      <img
        class="banner-image"
        alt="System Overview Visual"
        src="https://lh3.googleusercontent.com/aida-public/AB6AXuDZKJpobJIDjo2PGzakXmI4AQXaqYyqBE2jOivFKqar2gMsngSS4SXI1lgSzKR1gD4erUAaglyEaVMhmaXWySDGqi1LinhP5smC6QjVFhLXTWulk-ZT9wb9NhPEmD9J5gr0hUqOZRiYu3TCltb4TD2yAB2lLyHVY4IZNPOftvb_d8pPE4yVLKvil1WaQo5AR3X02mKls3b4SftJftoudrud0HlZ4hWS5m1lDqhdm_gMgYUq1jId8s2pBS8IOMd7QfvKpJPgHlerCio"
      />

      <div class="banner-overlay">
        <div class="banner-content">
          <div class="system-status">
            System Status: Operational
          </div>

          <h2>SmartSport Central Console</h2>

          <p>
            Quản lý hiệu quả, dữ liệu chính xác và tối ưu hóa trải nghiệm người
            dùng thể thao hàng đầu.
          </p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "AdminTrangChu",

  data() {
    return {
      users: [
        { id: 1, fullName: "Nguyễn Văn A", role: "client" },
        { id: 2, fullName: "Trần Văn Chủ", role: "chu_san" },
        { id: 3, fullName: "Lê Thị Chủ Sân", role: "chu_san" },
        { id: 4, fullName: "Admin SmartSport", role: "admin" },
        { id: 5, fullName: "Phạm Minh Quân", role: "client" },
        { id: 6, fullName: "Hoàng Anh Tuấn", role: "client" },
        { id: 7, fullName: "Đỗ Thị Mai", role: "client" },
        { id: 8, fullName: "Võ Quốc Huy", role: "client" },
        { id: 9, fullName: "Bùi Ngọc Linh", role: "client" },
        { id: 10, fullName: "Ngô Thanh Bình", role: "chu_san" },
      ],

      fields: [
        { id: 1, name: "Sân bóng đá Đại học Y" },
        { id: 2, name: "Sân bóng rổ Thống Nhất" },
        { id: 3, name: "Sân bóng chuyền Phú Thọ" },
        { id: 4, name: "Sân cầu lông Hòa Xuân" },
        { id: 5, name: "Sân bóng đá Mỹ Đình" },
        { id: 6, name: "Sân bóng rổ Cầu Giấy" },
        { id: 7, name: "Sân bóng chuyền Bình Thạnh" },
        { id: 8, name: "Sân cầu lông Hải Châu" },
        { id: 9, name: "Sân bóng đá Lạch Tray" },
        { id: 10, name: "Sân bóng rổ Nguyễn Du" },
        { id: 11, name: "Sân bóng chuyền Đống Đa" },
        { id: 12, name: "Sân cầu lông Tân Bình" },
        { id: 13, name: "Sân bóng đá Hòa Khánh" },
        { id: 14, name: "Sân bóng rổ Thanh Xuân" },
        { id: 15, name: "Sân cầu lông Lê Văn Tám" },
      ],
    };
  },

  computed: {
    totalUsers() {
      return this.users.length;
    },

    totalFields() {
      return this.fields.length;
    },
  },
};
</script>

<style scoped>
.admin-dashboard {
  min-height: 100vh;
  background: #f8f9fa;
  color: #191c1d;
  font-family: "Inter", sans-serif;
}

.page-header {
  margin-bottom: 32px;
}

.page-header h2 {
  margin: 0;
  font-size: 30px;
  line-height: 38px;
  font-weight: 700;
  letter-spacing: -0.02em;
  color: #191c1d;
}

.page-header p {
  margin: 4px 0 0;
  color: #64748b;
  font-size: 16px;
  line-height: 24px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
  margin-bottom: 32px;
}

.stat-card {
  background: #ffffff;
  border: 1px solid #f1f5f9;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(15, 23, 42, 0.1);
  transition: 0.3s;
}

.stat-card:hover {
  box-shadow: 0 12px 28px rgba(15, 23, 42, 0.12);
}

.stat-content {
  padding: 24px;
}

.stat-top {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.25s;
}

.stat-icon.green {
  background: #f0fdf4;
  color: #16a34a;
}

.stat-icon.blue {
  background: #eff6ff;
  color: #2563eb;
}

.stat-card:hover .stat-icon.green {
  background: #16a34a;
  color: #ffffff;
}

.stat-card:hover .stat-icon.blue {
  background: #2563eb;
  color: #ffffff;
}

.percent {
  font-size: 10px;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: 999px;
}

.green-percent {
  background: #dcfce7;
  color: #15803d;
}

.blue-percent {
  background: #dbeafe;
  color: #1d4ed8;
}

.stat-label {
  margin: 0;
  color: #64748b;
  font-size: 12px;
  line-height: 16px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.stat-card h3 {
  margin: 4px 0 0;
  font-size: 36px;
  line-height: 44px;
  font-weight: 700;
  letter-spacing: -0.02em;
  color: #191c1d;
}

.stat-bar {
  height: 4px;
}

.green-bar {
  width: 66.666%;
  background: #22c55e;
}

.blue-bar {
  width: 50%;
  background: #3b82f6;
}

.system-banner {
  position: relative;
  height: 320px;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 20px 45px rgba(15, 23, 42, 0.2);
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: 0.7s;
}

.system-banner:hover .banner-image {
  transform: scale(1.05);
}

.banner-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(
    to top,
    rgba(15, 23, 42, 0.8),
    rgba(15, 23, 42, 0.4),
    transparent
  );
  display: flex;
  align-items: flex-end;
  padding: 32px;
}

.banner-content {
  max-width: 640px;
}

.system-status {
  display: inline-flex;
  align-items: center;
  background: #22c55e;
  color: #ffffff;
  font-size: 10px;
  font-weight: 800;
  padding: 4px 8px;
  border-radius: 6px;
  margin-bottom: 16px;
  text-transform: uppercase;
  letter-spacing: -0.02em;
}

.banner-content h2 {
  margin: 0 0 4px;
  color: #ffffff;
  font-size: 30px;
  line-height: 38px;
  font-weight: 700;
}

.banner-content p {
  margin: 0;
  color: #e2e8f0;
  font-size: 16px;
  line-height: 24px;
}

@media (max-width: 900px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
}
</style>