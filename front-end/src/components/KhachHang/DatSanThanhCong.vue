<template>
  <div class="dat-san-thanh-cong-page">
    <main class="success-main">
      <div class="success-container">
        <!-- Icon thành công -->
        <div class="success-icon-wrapper">
          <div class="success-icon">
            <span class="material-symbols-outlined">check_circle</span>
          </div>

          <div class="celebration-icon">
            <span class="material-symbols-outlined">celebration</span>
          </div>
        </div>

        <!-- Tiêu đề -->
        <div class="success-header">
          <h1>Đặt sân thành công!</h1>

          <p>
            Cảm ơn bạn đã tin tưởng SmartSport. Yêu cầu đặt sân của bạn đang
            được xử lý và sẽ sớm được xác nhận bởi chủ sân.
          </p>
        </div>

        <!-- Thẻ thông tin đặt sân -->
        <div class="booking-card">
          <div class="field-image-box">
            <img class="field-image" :src="field.image" :alt="field.name" />

            <div class="field-badge">
              {{ fieldType.name }}
            </div>
          </div>

          <div class="booking-info">
            <div>
              <h3>{{ field.name }}</h3>

              <div class="info-list">
                <div class="info-item">
                  <span class="material-symbols-outlined">calendar_today</span>

                  <div>
                    <p>Thời gian</p>
                    <strong>{{ bookingTimeText }}</strong>
                  </div>
                </div>

                <div class="info-item">
                  <span class="material-symbols-outlined">location_on</span>

                  <div>
                    <p>Địa chỉ</p>
                    <strong>{{ field.address }}</strong>
                  </div>
                </div>

                <div class="info-item">
                  <span class="material-symbols-outlined">payments</span>

                  <div>
                    <p>Tổng cộng</p>
                    <strong class="price-text">
                      {{ formatCurrency(totalAmount) }}
                    </strong>
                  </div>
                </div>
              </div>
            </div>

            <div class="booking-footer">
              <span class="status-badge">Đang xử lý</span>
              <span class="booking-code">Mã đơn: {{ bookingCode }}</span>
            </div>
          </div>
        </div>

        <!-- Nút hành động -->
        <div class="action-buttons">
          <button class="primary-button" @click="goToBookingHistory">
            Xem lịch sử đặt sân
          </button>

          <button class="outline-button" @click="goHome">
            Về trang chủ
          </button>
        </div>

        <!-- Hỗ trợ -->
        <p class="support-text">
          <span class="material-symbols-outlined">help_outline</span>
          Cần hỗ trợ?
          <span class="hotline-label">Liên hệ tổng đài 1900 xxxx</span>
        </p>
      </div>
    </main>

    <!-- Trang trí nền -->
    <div class="decor decor-right"></div>
    <div class="decor decor-left"></div>
  </div>
</template>

<script>
export default {
  name: "DatSanThanhCong",

  data() {
    return {
      field: {
        id: 1,
        name: "Sân bóng đá Đại học Y",
        address: "1 Tôn Thất Tùng, Đống Đa, Hà Nội",
        image:
          "https://lh3.googleusercontent.com/aida-public/AB6AXuANOKmqYm270OK_PCslBdJvj-0kWoHtISkuBVDtYHGJMbORW6KRmz2vT29m4DodR7RzIfFPQC_GLnZyPtdVg3rvi5umRlIaM6EXXruAI2yTOYX-WFVlmg9wfRTxWjMV9rZ6gekf-yhZozqeDY8U12NsbTSDpN4tzG-eGxMvzS3yWVbfv70eQ4L42MW7qRSzSiLJ6ZDnbdAPtMX6ey_3gyYh2HAoYag8Nwhqg5aOZ0c7Yr8-ngnVHMDlsrTmFjarOmEMfvWxPAYT5JE",
      },

      fieldType: {
        id: 1,
        name: "Bóng đá",
      },

      selectedSlots: [
        {
          id: 4,
          scheduleDate: "2026-05-06",
          startTime: "09:00",
          endTime: "10:00",
          price: 120000,
        },
        {
          id: 5,
          scheduleDate: "2026-05-06",
          startTime: "10:00",
          endTime: "11:00",
          price: 120000,
        },
      ],

      bookingCode: "#SS-060526",
    };
  },

  computed: {
    totalAmount() {
      return this.selectedSlots.reduce((sum, slot) => sum + slot.price, 0);
    },

    bookingTimeText() {
      if (this.selectedSlots.length === 0) {
        return "Chưa có khung giờ";
      }

      const firstSlot = this.selectedSlots[0];
      const lastSlot = this.selectedSlots[this.selectedSlots.length - 1];

      return `${firstSlot.startTime} - ${lastSlot.endTime}, 06/05/2026`;
    },
  },

  methods: {
    formatCurrency(value) {
      return new Intl.NumberFormat("vi-VN").format(value) + "đ";
    },

    goHome() {
      this.$router.push("/");
    },

    goToBookingHistory() {
      this.$router.push("/client/lich-su-dat-san");
    },
  },
};
</script>

<style scoped>
.dat-san-thanh-cong-page {
  position: relative;
  min-height: 100vh;
  background: #fbf8ff;
  color: #191b25;
  font-family: "Lexend", sans-serif;
  overflow: hidden;
}

.success-main {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 48px 20px;
}

.success-container {
  width: 100%;
  max-width: 720px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.success-icon-wrapper {
  position: relative;
  margin-bottom: 32px;
}

.success-icon {
  width: 96px;
  height: 96px;
  background: #68fc76;
  color: #007322;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.success-icon .material-symbols-outlined {
  font-size: 56px;
  font-variation-settings: "FILL" 1;
}

.celebration-icon {
  position: absolute;
  top: -8px;
  right: -8px;
  width: 32px;
  height: 32px;
  background: #003ec7;
  color: #ffffff;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.celebration-icon .material-symbols-outlined {
  font-size: 18px;
}

.success-header {
  text-align: center;
  margin-bottom: 40px;
}

.success-header h1 {
  margin: 0 0 16px;
  font-size: 48px;
  line-height: 1.2;
  font-weight: 700;
  color: #191b25;
  letter-spacing: -0.02em;
}

.success-header p {
  max-width: 560px;
  margin: 0 auto;
  color: #434656;
  font-size: 18px;
  line-height: 1.6;
}

.booking-card {
  width: 100%;
  background: #ffffff;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
  display: grid;
  grid-template-columns: 1fr 2fr;
  margin-bottom: 40px;
  transition: 0.2s;
}

.booking-card:hover {
  box-shadow: 0 12px 28px rgba(15, 23, 42, 0.12);
}

.field-image-box {
  position: relative;
  min-height: 260px;
}

.field-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.field-badge {
  position: absolute;
  top: 16px;
  left: 16px;
  background: #006e20;
  color: #ffffff;
  padding: 6px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
}

.booking-info {
  padding: 32px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.booking-info h3 {
  margin: 0 0 24px;
  font-size: 24px;
  line-height: 1.4;
  font-weight: 600;
  color: #191b25;
}

.info-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.info-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.info-item>.material-symbols-outlined {
  color: #003ec7;
  font-size: 24px;
  margin-top: 2px;
}

.info-item p {
  margin: 0 0 4px;
  color: #434656;
  font-size: 14px;
  font-weight: 600;
}

.info-item strong {
  color: #191b25;
  font-size: 16px;
  font-weight: 500;
}

.price-text {
  color: #003ec7 !important;
  font-size: 24px !important;
  font-weight: 600 !important;
}

.booking-footer {
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid #f1f5f9;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.status-badge {
  background: #ecfdf5;
  color: #047857;
  border: 1px solid #d1fae5;
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 700;
}

.booking-code {
  color: #434656;
  font-size: 12px;
  font-weight: 500;
}

.action-buttons {
  display: flex;
  gap: 16px;
  margin-bottom: 48px;
}

.primary-button,
.outline-button {
  border-radius: 12px;
  padding: 16px 40px;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
}

.primary-button {
  border: none;
  background: #003ec7;
  color: #ffffff;
  box-shadow: 0 10px 24px rgba(0, 62, 199, 0.18);
}

.primary-button:hover {
  background: #0052ff;
}

.outline-button {
  background: #ffffff;
  color: #003ec7;
  border: 1px solid #003ec7;
}

.outline-button:hover {
  background: #f8fafc;
}

.primary-button:active,
.outline-button:active {
  transform: scale(0.95);
}

.support-text {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #434656;
  font-size: 12px;
  font-weight: 500;
}

.support-text .material-symbols-outlined {
  font-size: 16px;
}

.support-text span {
  color: #003ec7;
  font-weight: 700;
  text-decoration: none;
}

.support-text a:hover {
  text-decoration: underline;
}

.decor {
  position: fixed;
  border-radius: 999px;
  filter: blur(48px);
  pointer-events: none;
  z-index: 0;
}

.decor-right {
  top: 80px;
  right: 5%;
  width: 260px;
  height: 260px;
  background: rgba(0, 62, 199, 0.03);
}

.decor-left {
  bottom: 40px;
  left: 5%;
  width: 320px;
  height: 320px;
  background: rgba(0, 110, 32, 0.03);
}

@media (max-width: 768px) {
  .success-header h1 {
    font-size: 36px;
  }

  .booking-card {
    grid-template-columns: 1fr;
  }

  .field-image-box {
    height: 220px;
  }

  .action-buttons {
    width: 100%;
    flex-direction: column;
  }

  .primary-button,
  .outline-button {
    width: 100%;
  }

  .booking-footer {
    flex-direction: column;
    align-items: flex-start;
  }

}
</style>