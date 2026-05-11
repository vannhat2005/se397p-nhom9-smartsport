<template>
  <div class="chi-tiet-san-page">
    <!-- Nút quay lại -->
    <div class="back-row">
      <button class="back-button" @click="$router.back()">
        <span class="material-symbols-outlined">arrow_back</span>
        <span>Quay lại</span>
      </button>
    </div>

    <div class="detail-layout">
      <!-- Cột trái -->
      <div class="left-column">
        <!-- Thông tin sân -->
        <section class="field-detail-card">
          <div class="field-image-box">
            <img class="field-image" :src="field.image" :alt="field.name" />

            <div class="field-badge">
              Sân nổi bật
            </div>
          </div>

          <div class="field-info">
            <div class="field-header">
              <div>
                <h1>{{ field.name }}</h1>

                <p class="field-address">
                  <span class="material-symbols-outlined">location_on</span>
                  {{ field.address }}
                </p>
              </div>

              <div class="price-list">
                <div v-for="price in fieldPrices" :key="price.id" class="price-item">
                  <span>{{ price.startTime }} - {{ price.endTime }}:</span>
                  <strong>{{ formatCurrency(price.price) }}</strong>
                </div>
              </div>
            </div>

            <div class="field-meta">
              <div>
                <span class="material-symbols-outlined">stadium</span>
                <strong>{{ fieldType.name }}</strong>
              </div>

              <div>
                <span class="material-symbols-outlined">schedule</span>
                <strong>{{ field.openTime }} - {{ field.closeTime }}</strong>
              </div>

              <div>
                <span class="material-symbols-outlined star-icon">star</span>
                <strong>4.8</strong>
              </div>
            </div>

            <p class="field-description">
              {{ field.description }}
            </p>
          </div>
        </section>

        <!-- Chọn khung giờ -->
        <section class="booking-card">
          <div class="booking-header">
            <h2>Chọn khung giờ</h2>

            <div class="legend-list">
              <div class="legend-item">
                <span class="legend-box available"></span>
                <span>Trống</span>
              </div>

              <div class="legend-item">
                <span class="legend-box booked"></span>
                <span>Đã đặt</span>
              </div>

              <div class="legend-item">
                <span class="legend-box locked"></span>
                <span>Khóa</span>
              </div>
            </div>
          </div>

          <div class="time-grid">
            <button v-for="slot in fieldSchedules" :key="slot.id" class="time-slot" :class="{
              selected: isSelected(slot),
              available: slot.status === 1,
              booked: slot.status === 2,
              locked: slot.status === 0,
            }" :disabled="slot.status !== 1" @click="toggleSlot(slot)">
              <div class="time-row">
                <span>{{ slot.startTime }}</span>
                <small>{{ slot.status === 0 ? '-' : slot.priceText }}</small>
              </div>

              <strong>
                {{ getSlotLabel(slot) }}
              </strong>
            </button>
          </div>
        </section>
      </div>

      <!-- Cột phải -->
      <aside class="right-column">
        <div class="summary-card">
          <h3>Tóm tắt đặt sân</h3>

          <div class="summary-section">
            <div class="summary-row">
              <span>Ngày</span>
              <strong>{{ bookingDateText }}</strong>
            </div>

            <div class="selected-slots">
              <span class="summary-label">Khung giờ đã chọn</span>

              <div v-if="selectedSlots.length === 0" class="empty-selected">
                Chưa chọn khung giờ nào
              </div>

              <div v-for="slot in selectedSlots" :key="slot.id" class="selected-item">
                <div class="selected-left">
                  <span class="material-symbols-outlined">timer</span>

                  <div>
                    <p>{{ slot.startTime }} - {{ slot.endTime }}</p>
                    <small>{{ field.name }} • {{ getPriceBlock(slot) }}</small>
                  </div>
                </div>

                <strong>{{ formatCurrency(slot.price) }}</strong>
              </div>
            </div>
          </div>

          <div class="total-section">
            <div class="summary-row">
              <span>Tổng thời gian</span>
              <strong>{{ totalHours }} giờ</strong>
            </div>

            <div class="summary-row">
              <span>Tạm tính</span>
              <strong>{{ formatCurrency(subtotal) }}</strong>
            </div>

            <div class="summary-row">
              <span>Phí dịch vụ</span>
              <strong>{{ formatCurrency(serviceFee) }}</strong>
            </div>

            <div class="summary-row total">
              <span>Tổng cộng</span>
              <strong>{{ formatCurrency(totalAmount) }}</strong>
            </div>
          </div>

          <button class="book-button" @click="handleBooking">
            Đặt sân ngay
          </button>

          <p class="note">
            Hủy sân miễn phí trước 24 giờ kể từ thời điểm bắt đầu.
          </p>
        </div>
      </aside>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChiTietSan",

  data() {
    return {
      bookingDate: "2026-05-06",

      field: {
        id: 1,
        userId: 2,
        fieldTypeId: 1,
        name: "Sân bóng đá Đại học Y",
        address: "1 Tôn Thất Tùng, Đống Đa, Hà Nội",
        description:
          "Sân bóng đá chất lượng cao, mặt sân đẹp, hệ thống đèn chiếu sáng tốt, phù hợp cho luyện tập và thi đấu phong trào.",
        openTime: "06:00",
        closeTime: "23:00",
        status: 1,
        image:
          "https://lh3.googleusercontent.com/aida-public/AB6AXuANOKmqYm270OK_PCslBdJvj-0kWoHtISkuBVDtYHGJMbORW6KRmz2vT29m4DodR7RzIfFPQC_GLnZyPtdVg3rvi5umRlIaM6EXXruAI2yTOYX-WFVlmg9wfRTxWjMV9rZ6gekf-yhZozqeDY8U12NsbTSDpN4tzG-eGxMvzS3yWVbfv70eQ4L42MW7qRSzSiLJ6ZDnbdAPtMX6ey_3gyYh2HAoYag8Nwhqg5aOZ0c7Yr8-ngnVHMDlsrTmFjarOmEMfvWxPAYT5JE",
      },

      fieldType: {
        id: 1,
        name: "Bóng đá",
        description: "Sân bóng đá mini",
        status: 1,
      },

      fieldPrices: [
        {
          id: 1,
          fieldId: 1,
          dayType: 1,
          startTime: "06:00",
          endTime: "12:00",
          price: 120000,
          status: 1,
        },
        {
          id: 2,
          fieldId: 1,
          dayType: 1,
          startTime: "12:00",
          endTime: "18:00",
          price: 150000,
          status: 1,
        },
        {
          id: 3,
          fieldId: 1,
          dayType: 1,
          startTime: "18:00",
          endTime: "23:00",
          price: 180000,
          status: 1,
        },
      ],

      /*
        Đây mới là dữ liệu đúng của bảng field_schedules trong database.

        Chỉ lưu những khung giờ đã phát sinh dữ liệu:
        - status = 1: Đã đặt
        - status = 2: Đang giữ chỗ

        Không lưu các giờ trống.
        Frontend sẽ tự sinh giờ trống từ field.openTime đến field.closeTime.
      */
      fieldScheduleDatabases: [
        {
          id: 1,
          fieldId: 1,
          bookingId: 15,
          scheduleDate: "2026-05-06",
          startTime: "08:00",
          endTime: "09:00",
          status: 1,
        },
        {
          id: 2,
          fieldId: 1,
          bookingId: 16,
          scheduleDate: "2026-05-06",
          startTime: "12:00",
          endTime: "13:00",
          status: 2,
        },
        {
          id: 3,
          fieldId: 1,
          bookingId: 17,
          scheduleDate: "2026-05-06",
          startTime: "15:00",
          endTime: "16:00",
          status: 1,
        },
        {
          id: 4,
          fieldId: 1,
          bookingId: 18,
          scheduleDate: "2026-05-06",
          startTime: "17:00",
          endTime: "18:00",
          status: 2,
        },
        {
          id: 5,
          fieldId: 1,
          bookingId: 19,
          scheduleDate: "2026-05-06",
          startTime: "19:00",
          endTime: "20:00",
          status: 1,
        },
      ],

      selectedSlotIds: [4, 5],
    };
  },

  computed: {
    /*
      fieldSchedules dùng cho giao diện cũ.

      Quy đổi để KHÔNG đổi giao diện:
      - Không có trong DB => status = 1 => Trống
      - DB status = 1 => UI status = 2 => Đã đặt
      - DB status = 2 => UI status = 0 => Khóa
    */
    fieldSchedules() {
      const slots = [];
      const openMinutes = this.timeToMinutes(this.field.openTime);
      const closeMinutes = this.timeToMinutes(this.field.closeTime);

      let slotId = 1;

      for (let time = openMinutes; time < closeMinutes; time += 60) {
        const startTime = this.minutesToTime(time);
        const endTime = this.minutesToTime(time + 60);

        const databaseSchedule = this.findDatabaseSchedule(startTime, endTime);
        const price = this.getPriceByTime(startTime, endTime);

        let uiStatus = 1;

        if (databaseSchedule && databaseSchedule.status === 1) {
          uiStatus = 2;
        }

        if (databaseSchedule && databaseSchedule.status === 2) {
          uiStatus = 0;
        }

        slots.push({
          id: slotId,
          fieldId: this.field.id,
          bookingId: databaseSchedule ? databaseSchedule.bookingId : null,
          scheduleDate: this.bookingDate,
          startTime,
          endTime,
          price,
          priceText: this.formatShortPrice(price),
          status: uiStatus,
          databaseStatus: databaseSchedule ? databaseSchedule.status : null,
        });

        slotId++;
      }

      return slots;
    },

    selectedSlots() {
      return this.fieldSchedules.filter((slot) =>
        this.selectedSlotIds.includes(slot.id)
      );
    },

    bookingDateText() {
      const [year, month, day] = this.bookingDate.split("-");
      return `${day}/${month}/${year}`;
    },

    totalHours() {
      return this.selectedSlots.length;
    },

    subtotal() {
      return this.selectedSlots.reduce((sum, slot) => sum + slot.price, 0);
    },

    serviceFee() {
      return Math.round(this.subtotal * 0.05);
    },

    totalAmount() {
      return this.subtotal + this.serviceFee;
    },
  },

  methods: {
    timeToMinutes(time) {
      const [hour, minute] = time.split(":").map(Number);
      return hour * 60 + minute;
    },

    minutesToTime(totalMinutes) {
      const hour = Math.floor(totalMinutes / 60);
      const minute = totalMinutes % 60;

      return `${String(hour).padStart(2, "0")}:${String(minute).padStart(
        2,
        "0"
      )}`;
    },

    isTimeOverlap(startA, endA, startB, endB) {
      const aStart = this.timeToMinutes(startA);
      const aEnd = this.timeToMinutes(endA);
      const bStart = this.timeToMinutes(startB);
      const bEnd = this.timeToMinutes(endB);

      return aStart < bEnd && aEnd > bStart;
    },

    findDatabaseSchedule(startTime, endTime) {
      return this.fieldScheduleDatabases.find((schedule) => {
        if (schedule.fieldId !== this.field.id) {
          return false;
        }

        if (schedule.scheduleDate !== this.bookingDate) {
          return false;
        }

        return this.isTimeOverlap(
          startTime,
          endTime,
          schedule.startTime,
          schedule.endTime
        );
      });
    },

    getPriceByTime(startTime, endTime) {
      const priceRule = this.fieldPrices.find((price) => {
        if (price.fieldId !== this.field.id) {
          return false;
        }

        if (price.status !== 1) {
          return false;
        }

        const slotStart = this.timeToMinutes(startTime);
        const slotEnd = this.timeToMinutes(endTime);
        const priceStart = this.timeToMinutes(price.startTime);
        const priceEnd = this.timeToMinutes(price.endTime);

        return slotStart >= priceStart && slotEnd <= priceEnd;
      });

      return priceRule ? priceRule.price : 0;
    },

    formatCurrency(value) {
      return new Intl.NumberFormat("vi-VN").format(value) + "đ";
    },

    formatShortPrice(value) {
      if (!value) {
        return "-";
      }

      return `${Math.round(value / 1000)}k`;
    },

    isSelected(slot) {
      return this.selectedSlotIds.includes(slot.id);
    },

    toggleSlot(slot) {
      if (slot.status !== 1) {
        return;
      }

      if (this.isSelected(slot)) {
        this.selectedSlotIds = this.selectedSlotIds.filter(
          (id) => id !== slot.id
        );
      } else {
        this.selectedSlotIds.push(slot.id);
      }
    },

    getSlotLabel(slot) {
      if (this.isSelected(slot)) {
        return "Đã chọn";
      }

      if (slot.status === 1) {
        return "Trống";
      }

      if (slot.status === 2) {
        return "Đã đặt";
      }

      return "Khóa";
    },

    getPriceBlock(slot) {
      if (slot.price === 120000) {
        return "Khung sáng";
      }

      if (slot.price === 150000) {
        return "Khung chiều";
      }

      return "Khung tối";
    },

    handleBooking() {
      if (this.selectedSlots.length === 0) {
        alert("Vui lòng chọn ít nhất một khung giờ.");
        return;
      }

      const bookingPayload = {
        field_id: this.field.id,
        booking_date: this.bookingDate,

        /*
          Khi khách bấm đặt:
          - bookings.status = 0: Chờ xác nhận
          - field_schedules.status = 2: Đang giữ chỗ

          Giao diện cũ sẽ quy đổi field_schedules.status = 2 thành slot Khóa.
        */
        booking_status: 0,
        field_schedule_status: 2,

        selected_schedules: this.selectedSlots.map((slot) => ({
          start_time: slot.startTime,
          end_time: slot.endTime,
          price: slot.price,
        })),

        total_amount: this.totalAmount,
      };

      console.log("Dữ liệu đặt sân:", bookingPayload);
      alert("Đã chọn đặt sân. Kiểm tra console để xem dữ liệu gửi lên API.");
    },
  },
};
</script>

<style scoped>
.chi-tiet-san-page {
  background: #fbf8ff;
  color: #191b25;
  font-family: "Lexend", sans-serif;
  padding: 24px 40px 64px;
  min-height: 100vh;
}

.back-row {
  margin-bottom: 24px;
}

.back-button {
  border: none;
  background: transparent;
  color: #003ec7;
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
}

.back-button:hover {
  gap: 12px;
}

.detail-layout {
  display: grid;
  grid-template-columns: 8fr 4fr;
  gap: 24px;
  max-width: 1920px;
  margin: 0 auto;
}

.left-column {
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.field-detail-card,
.booking-card,
.summary-card,
.map-card {
  background: #ffffff;
  border: 1px solid rgba(195, 197, 217, 0.3);
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.field-detail-card {
  overflow: hidden;
}

.field-image-box {
  position: relative;
  height: 320px;
  overflow: hidden;
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
  background: #68fc76;
  color: #007322;
  padding: 6px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
}

.field-info {
  padding: 32px;
}

.field-header {
  display: flex;
  justify-content: space-between;
  gap: 24px;
}

.field-header h1 {
  margin: 0;
  font-size: 32px;
  font-weight: 600;
  color: #191b25;
}

.field-address {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 8px 0 0;
  color: #737688;
}

.price-list {
  text-align: right;
  min-width: 220px;
}

.price-item {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-bottom: 6px;
}

.price-item span {
  color: #737688;
  font-size: 13px;
}

.price-item strong {
  color: #191b25;
}

.field-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid rgba(195, 197, 217, 0.25);
}

.field-meta div {
  display: flex;
  align-items: center;
  gap: 8px;
}

.field-meta .material-symbols-outlined {
  color: #003ec7;
}

.star-icon {
  color: #f59e0b !important;
  font-variation-settings: "FILL" 1;
}

.field-description {
  margin: 24px 0 0;
  line-height: 1.6;
  color: #434656;
}

.booking-card {
  padding: 32px;
}

.booking-header {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  align-items: center;
  margin-bottom: 32px;
}

.booking-header h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
}

.legend-list {
  display: flex;
  gap: 16px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
}

.legend-box {
  width: 16px;
  height: 16px;
  border-radius: 4px;
}

.legend-box.available {
  background: rgba(111, 255, 123, 0.25);
  border: 1px solid #006e20;
}

.legend-box.booked {
  background: rgba(255, 218, 214, 0.4);
  border: 1px solid #ba1a1a;
}

.legend-box.locked {
  background: #e1e1ef;
  border: 1px solid #737688;
}

.time-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 16px;
}

.time-slot {
  border: none;
  text-align: left;
  padding: 16px;
  border-radius: 10px;
  border-left: 4px solid transparent;
  cursor: pointer;
  transition: 0.2s;
  font-family: inherit;
}

.time-slot .time-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 6px;
  opacity: 0.75;
}

.time-slot.available {
  background: rgba(111, 255, 123, 0.15);
  border-left-color: #006e20;
  color: #191b25;
}

.time-slot.available:hover {
  background: #003ec7;
  border-left-color: #003ec7;
  color: #ffffff;
}

.time-slot.booked {
  background: rgba(255, 218, 214, 0.25);
  border-left-color: #ba1a1a;
  color: #93000a;
  opacity: 0.6;
  cursor: not-allowed;
}

.time-slot.locked {
  background: #e1e1ef;
  border-left-color: #737688;
  color: #737688;
  opacity: 0.7;
  cursor: not-allowed;
}

.time-slot.selected {
  background: #003ec7;
  color: #ffffff;
  border-left-color: #003ec7;
  box-shadow: 0 8px 20px rgba(0, 62, 199, 0.25);
  outline: 2px solid #003ec7;
  outline-offset: 2px;
}

.right-column {
  position: sticky;
  top: 100px;
  height: fit-content;
}

.summary-card {
  padding: 32px;
}

.summary-card h3 {
  margin: 0 0 24px;
  font-size: 24px;
  font-weight: 600;
}

.summary-section {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding: 8px 0;
  border-bottom: 1px solid rgba(195, 197, 217, 0.12);
}

.summary-row span {
  color: #737688;
}

.summary-label {
  display: block;
  margin-bottom: 8px;
  color: #737688;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.04em;
}

.empty-selected {
  padding: 14px;
  background: #f3f2ff;
  border-radius: 10px;
  color: #737688;
  font-size: 14px;
}

.selected-item {
  background: #f3f2ff;
  border: 1px solid rgba(0, 62, 199, 0.18);
  border-radius: 10px;
  padding: 12px;
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
  gap: 12px;
  align-items: center;
}

.selected-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.selected-left .material-symbols-outlined {
  color: #003ec7;
}

.selected-left p {
  margin: 0;
  font-weight: 600;
}

.selected-left small {
  color: #737688;
  font-size: 10px;
  text-transform: uppercase;
}

.selected-item strong {
  color: #003ec7;
}

.total-section {
  margin-top: 24px;
  padding-top: 24px;
  border-top: 2px dashed rgba(195, 197, 217, 0.5);
}

.summary-row.total {
  border-bottom: none;
  padding-top: 16px;
}

.summary-row.total span,
.summary-row.total strong {
  font-size: 22px;
  font-weight: 700;
}

.summary-row.total strong {
  color: #003ec7;
}

.book-button {
  width: 100%;
  margin-top: 24px;
  border: none;
  background: #003ec7;
  color: #ffffff;
  padding: 16px;
  border-radius: 12px;
  font-size: 20px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
  box-shadow: 0 10px 24px rgba(0, 62, 199, 0.22);
}

.book-button:hover {
  background: #0052ff;
}

.book-button:active {
  transform: scale(0.98);
}

.note {
  margin: 16px 0 0;
  text-align: center;
  font-size: 12px;
  color: #737688;
}

.map-card {
  margin-top: 24px;
  height: 192px;
  overflow: hidden;
  position: relative;
}

.map-card img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.map-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.6), transparent);
  display: flex;
  align-items: flex-end;
  padding: 16px;
}

.map-overlay button {
  border: none;
  background: rgba(255, 255, 255, 0.92);
  color: #191b25;
  padding: 8px 16px;
  border-radius: 8px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

/* Responsive */
@media (max-width: 1200px) {
  .detail-layout {
    grid-template-columns: 1fr;
  }

  .right-column {
    position: static;
  }
}

@media (max-width: 900px) {
  .chi-tiet-san-page {
    padding: 20px;
  }

  .field-header {
    flex-direction: column;
  }

  .price-list {
    text-align: left;
  }

  .price-item {
    justify-content: flex-start;
  }

  .booking-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .time-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 640px) {
  .field-image-box {
    height: 220px;
  }

  .field-info,
  .booking-card,
  .summary-card {
    padding: 20px;
  }

  .time-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>