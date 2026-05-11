<template>
  <div class="booking-page">
    <!-- Page Header -->
    <div class="page-header">
      <div>
        <h2>Quản lý Booking</h2>
        <p>Theo dõi và quản lý tất cả các đơn đặt sân tại các sân thể thao.</p>
      </div>
    </div>

    <!-- Stats -->
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-top">
          <span>Tổng xác nhận</span>
          <span class="material-symbols-outlined green-icon">check_circle</span>
        </div>

        <h3>{{ confirmedCount }}</h3>

        <p class="green-text">
          <span class="material-symbols-outlined">trending_up</span>
          +12% so với tháng trước
        </p>
      </div>

      <div class="stat-card">
        <div class="stat-top">
          <span>Chờ xử lý</span>
          <span class="material-symbols-outlined amber-icon">pending</span>
        </div>

        <h3>{{ pendingCount }}</h3>

        <p class="amber-text">Cần chú ý</p>
      </div>

      <div class="stat-card">
        <div class="stat-top">
          <span>Tổng doanh thu</span>
          <span class="material-symbols-outlined blue-icon">payments</span>
        </div>

        <h3>{{ formatCurrency(totalRevenue) }}</h3>

        <p class="muted-text">Dự báo tuần này</p>
      </div>

      <div class="stat-card">
        <div class="stat-top">
          <span>Đã hủy</span>
          <span class="material-symbols-outlined red-icon">cancel</span>
        </div>

        <h3>{{ cancelledPercent }}%</h3>

        <p class="red-text">-0.5% cải thiện</p>
      </div>
    </div>

    <!-- Booking Table -->
    <div class="table-card">
      <div class="table-header">
        <div class="table-title">
          <h3>Booking đang hoạt động</h3>

          <div class="live-badge">
            <span></span>
            Live
          </div>
        </div>
      </div>

      <div class="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Mã booking</th>
              <th>Tên khách hàng</th>
              <th>Sân</th>
              <th>Ngày & giờ</th>
              <th>Tổng tiền</th>
              <th>Hóa đơn</th>
              <th>Trạng thái</th>
              <th class="text-right">Thao tác</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="booking in displayedBookings" :key="booking.id" :class="{ cancelled: booking.status === 2 }">
              <td class="booking-code">
                {{ booking.code }}
              </td>

              <td>
                <div class="customer-cell">
                  <div class="customer-avatar" :class="getAvatarClass(booking.status)">
                    {{ getAvatarText(booking.customerName) }}
                  </div>

                  <span>{{ booking.customerName }}</span>
                </div>
              </td>

              <td>
                <span class="field-name">{{ booking.fieldName }}</span>
              </td>

              <td>
                <div class="date-cell">
                  <p>{{ booking.bookingDate }}</p>
                  <span>{{ booking.startTime }} - {{ booking.endTime }}</span>
                </div>
              </td>

              <td class="money">
                {{ formatCurrency(booking.totalAmount) }}
              </td>

              <td>
                <div class="bill-box" @click="viewBill(booking)">
                  <img :src="booking.billImage" alt="Bill" />
                </div>
              </td>

              <td>
                <span class="status-badge" :class="getStatusClass(booking.status)">
                  <span></span>
                  {{ getStatusText(booking.status) }}
                </span>
              </td>

              <td class="text-right">
                <div class="action-buttons">
                  <button class="confirm-button" :disabled="booking.status !== 0" @click="confirmBooking(booking)"
                    title="Xác nhận">
                    <span class="material-symbols-outlined">check_circle</span>
                  </button>

                  <button class="delete-button" :disabled="booking.status !== 0" @click="cancelBooking(booking)"
                    title="Hủy booking">
                    <span class="material-symbols-outlined">delete</span>
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <div class="table-footer">
        <p>
          Hiển thị {{ bookings.length === 0 ? 0 : startIndex + 1 }}
          đến {{ endIndex }} trong số {{ bookings.length }} booking
        </p>

        <div class="pagination">
          <button :disabled="currentPage === 1" @click="prevPage">
            <span class="material-symbols-outlined">chevron_left</span>
          </button>

          <button v-for="page in totalPages" :key="page" :class="{ active: currentPage === page }"
            @click="goToPage(page)">
            {{ page }}
          </button>

          <button :disabled="currentPage === totalPages" @click="nextPage">
            <span class="material-symbols-outlined">chevron_right</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BookingChuSan",

  data() {
    return {
      currentPage: 1,
      perPage: 4,

      bookings: [
        {
          id: 1,
          code: "#BK-9021",
          customerName: "Nguyễn Lê",
          fieldName: "Sân bóng đá Đại học Y",
          bookingDate: "24/10/2023",
          startTime: "18:00",
          endTime: "20:00",
          totalAmount: 240000,
          status: 1,
          billImage: "https://placeholder.pics/svg/300",
        },
        {
          id: 2,
          code: "#BK-9022",
          customerName: "Trần Hoàng",
          fieldName: "Sân bóng rổ Thống Nhất",
          bookingDate: "25/10/2023",
          startTime: "07:00",
          endTime: "09:00",
          totalAmount: 300000,
          status: 0,
          billImage: "https://placeholder.pics/svg/300",
        },
        {
          id: 3,
          code: "#BK-9018",
          customerName: "Phạm Vũ",
          fieldName: "Sân bóng đá Mỹ Đình",
          bookingDate: "22/10/2023",
          startTime: "10:00",
          endTime: "12:00",
          totalAmount: 260000,
          status: 2,
          billImage: "https://placeholder.pics/svg/300",
        },
        {
          id: 4,
          code: "#BK-9025",
          customerName: "Minh Anh",
          fieldName: "Sân bóng rổ Cầu Giấy",
          bookingDate: "26/10/2023",
          startTime: "15:00",
          endTime: "17:00",
          totalAmount: 300000,
          status: 1,
          billImage: "https://placeholder.pics/svg/300",
        },
        {
          id: 5,
          code: "#BK-9026",
          customerName: "Hoàng Tuấn",
          fieldName: "Sân cầu lông Lê Văn Tám",
          bookingDate: "27/10/2023",
          startTime: "08:00",
          endTime: "10:00",
          totalAmount: 160000,
          status: 0,
          billImage: "https://placeholder.pics/svg/300",
        },
        {
          id: 6,
          code: "#BK-9027",
          customerName: "Đỗ Mai",
          fieldName: "Sân bóng chuyền Đống Đa",
          bookingDate: "28/10/2023",
          startTime: "19:00",
          endTime: "21:00",
          totalAmount: 200000,
          status: 1,
          billImage: "https://placeholder.pics/svg/300",
        },
      ],
    };
  },

  computed: {
    totalPages() {
      return Math.ceil(this.bookings.length / this.perPage);
    },

    startIndex() {
      return (this.currentPage - 1) * this.perPage;
    },

    displayedBookings() {
      return this.bookings.slice(this.startIndex, this.startIndex + this.perPage);
    },

    endIndex() {
      return this.startIndex + this.displayedBookings.length;
    },

    confirmedCount() {
      return this.bookings.filter((booking) => booking.status === 1).length;
    },

    pendingCount() {
      return this.bookings.filter((booking) => booking.status === 0).length;
    },

    cancelledCount() {
      return this.bookings.filter((booking) => booking.status === 2).length;
    },

    cancelledPercent() {
      if (this.bookings.length === 0) {
        return 0;
      }

      return Math.round((this.cancelledCount / this.bookings.length) * 100);
    },

    totalRevenue() {
      return this.bookings
        .filter((booking) => booking.status === 1)
        .reduce((sum, booking) => sum + booking.totalAmount, 0);
    },
  },

  methods: {
    goToPage(page) {
      if (page < 1 || page > this.totalPages) {
        return;
      }

      this.currentPage = page;
    },

    prevPage() {
      this.goToPage(this.currentPage - 1);
    },

    nextPage() {
      this.goToPage(this.currentPage + 1);
    },

    formatCurrency(value) {
      return new Intl.NumberFormat("vi-VN").format(value) + "đ";
    },

    getAvatarText(name) {
      return name
        .split(" ")
        .map((item) => item[0])
        .join("")
        .slice(-2)
        .toUpperCase();
    },

    getAvatarClass(status) {
      if (status === 2) {
        return "gray";
      }

      if (status === 0) {
        return "purple";
      }

      return "blue";
    },

    getStatusText(status) {
      if (status === 1) {
        return "Đã xác nhận";
      }

      if (status === 2) {
        return "Đã hủy";
      }

      return "Đang chờ";
    },

    getStatusClass(status) {
      if (status === 1) {
        return "confirmed";
      }

      if (status === 2) {
        return "cancelled";
      }

      return "pending";
    },

    confirmBooking(booking) {
      booking.status = 1;
      alert(`Đã xác nhận booking ${booking.code}`);
    },

    cancelBooking(booking) {
      booking.status = 2;
      alert(`Đã hủy booking ${booking.code}`);
    },

    viewBill(booking) {
      alert(`Xem hóa đơn của booking ${booking.code}`);
    },
  },
};
</script>

<style scoped>
.booking-page {
  min-height: 100vh;
  background: #f8f9ff;
  color: #0b1c30;
  font-family: "Inter", sans-serif;
}

.page-header {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 32px;
}

.page-header h2 {
  margin: 0;
  color: #0b1c30;
  font-size: 30px;
  line-height: 38px;
  font-weight: 700;
  letter-spacing: -0.02em;
}

.page-header p {
  margin: 4px 0 0;
  color: #565e74;
  font-size: 14px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  margin-bottom: 32px;
}

.stat-card {
  background: #ffffff;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.stat-top {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  margin-bottom: 8px;
}

.stat-top span:first-child {
  color: #64748b;
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
}

.stat-card h3 {
  margin: 0;
  color: #0f172a;
  font-size: 24px;
  line-height: 32px;
  font-weight: 700;
}

.stat-card p {
  margin: 8px 0 0;
  font-size: 12px;
  font-weight: 700;
  display: flex;
  align-items: center;
  gap: 4px;
}

.stat-card p .material-symbols-outlined {
  font-size: 14px;
}

.green-icon,
.green-text {
  color: #16a34a;
}

.amber-icon,
.amber-text {
  color: #d97706;
}

.blue-icon {
  color: #2563eb;
}

.red-icon,
.red-text {
  color: #dc2626;
}

.muted-text {
  color: #64748b;
}

.table-card {
  background: #ffffff;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
  overflow: hidden;
}

.table-header {
  padding: 24px;
  border-bottom: 1px solid #f1f5f9;
}

.table-title {
  display: flex;
  align-items: center;
  gap: 16px;
}

.table-title h3 {
  margin: 0;
  color: #0f172a;
  font-size: 20px;
  font-weight: 600;
}

.live-badge {
  display: flex;
  align-items: center;
  gap: 6px;
  background: #e5eeff;
  color: #006e2f;
  padding: 4px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
}

.live-badge span {
  width: 8px;
  height: 8px;
  background: #006e2f;
  border-radius: 999px;
}

.table-wrapper {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

thead tr {
  background: rgba(248, 250, 252, 0.5);
}

th {
  padding: 16px 24px;
  color: #64748b;
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
  white-space: nowrap;
  text-align: left;
}

td {
  padding: 16px 24px;
  border-bottom: 1px solid #f1f5f9;
  white-space: nowrap;
}

tbody tr {
  transition: 0.2s;
}

tbody tr:hover {
  background: #f8fafc;
}

tbody tr.cancelled {
  opacity: 0.65;
}

.text-right {
  text-align: right;
}

.booking-code {
  color: #0f172a;
  font-weight: 700;
}

.customer-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.customer-avatar {
  width: 32px;
  height: 32px;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 800;
}

.customer-avatar.blue {
  background: #dbeafe;
  color: #2563eb;
}

.customer-avatar.purple {
  background: #f3e8ff;
  color: #9333ea;
}

.customer-avatar.gray {
  background: #f1f5f9;
  color: #94a3b8;
}

.field-name {
  color: #334155;
  font-weight: 600;
}

.date-cell p {
  margin: 0;
  color: #0f172a;
  font-size: 14px;
}

.date-cell span {
  color: #64748b;
  font-size: 12px;
}

.money {
  color: #0f172a;
  font-weight: 700;
}

.bill-box {
  width: 48px;
  height: 48px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: 0.2s;
}

.bill-box:hover {
  opacity: 0.8;
}

.bill-box img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 800;
}

.status-badge span {
  width: 6px;
  height: 6px;
  border-radius: 999px;
}

.status-badge.confirmed {
  background: #f0fdf4;
  color: #15803d;
}

.status-badge.confirmed span {
  background: #22c55e;
}

.status-badge.pending {
  background: #fffbeb;
  color: #b45309;
}

.status-badge.pending span {
  background: #f59e0b;
}

.status-badge.cancelled {
  background: #f1f5f9;
  color: #64748b;
}

.status-badge.cancelled span {
  background: #94a3b8;
}

.action-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.confirm-button,
.delete-button {
  border: none;
  background: transparent;
  border-radius: 999px;
  padding: 8px;
  cursor: pointer;
  transition: 0.2s;
}

.confirm-button {
  color: #16a34a;
}

.confirm-button:hover {
  background: #f0fdf4;
}

.delete-button {
  color: #ef4444;
}

.delete-button:hover {
  background: #fef2f2;
}

.confirm-button:disabled,
.delete-button:disabled {
  color: #cbd5e1;
  cursor: not-allowed;
}

.confirm-button:disabled:hover,
.delete-button:disabled:hover {
  background: transparent;
}

.confirm-button .material-symbols-outlined,
.delete-button .material-symbols-outlined {
  font-size: 22px;
}

.table-footer {
  padding: 16px 24px;
  background: rgba(248, 250, 252, 0.35);
  border-top: 1px solid #f1f5f9;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.table-footer p {
  margin: 0;
  color: #64748b;
  font-size: 14px;
}

.pagination {
  display: flex;
  align-items: center;
  gap: 4px;
}

.pagination button {
  min-width: 32px;
  height: 32px;
  border: 1px solid transparent;
  background: transparent;
  color: #475569;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 800;
  cursor: pointer;
  transition: 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.pagination button:hover {
  background: #ffffff;
  border-color: #e2e8f0;
}

.pagination button.active {
  background: #006e2f;
  color: #ffffff;
}

.pagination button:disabled {
  color: #cbd5e1;
  cursor: not-allowed;
}

.pagination button:disabled:hover {
  background: transparent;
  border-color: transparent;
}

.pagination .material-symbols-outlined {
  font-size: 20px;
}

@media (max-width: 1200px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }

  .table-footer {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
}
</style>