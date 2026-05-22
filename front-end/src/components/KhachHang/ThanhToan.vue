<template>
  <div class="thanh-toan-page">
    <!-- Header nội dung -->
    <div class="page-header">
      <nav class="breadcrumb">
        <span>Địa điểm</span>
        <span class="material-symbols-outlined">chevron_right</span>
        <span>{{ field.name }}</span>
        <span class="material-symbols-outlined">chevron_right</span>
        <strong>Thanh toán</strong>
      </nav>

      <h1>Thanh toán chuyển khoản</h1>
      <p>Vui lòng quét mã QR và tải ảnh minh chứng để hoàn tất đặt sân.</p>
    </div>

    <div class="payment-layout">
      <!-- Cột trái: Thông tin ngân hàng + QR -->
      <section class="bank-card">
        <div class="bank-info-grid">
          <div class="bank-info-item">
            <span>Tên tài khoản</span>
            <strong>{{ bank.accountName }}</strong>
          </div>

          <div class="bank-info-item">
            <span>Số tài khoản</span>

            <div class="copy-row">
              <strong class="primary-text">{{ bank.accountNumber }}</strong>

              <button class="copy-button" @click="copyAccountNumber">
                <span class="material-symbols-outlined">content_copy</span>
              </button>
            </div>
          </div>

          <div class="bank-info-item">
            <span>Ngân hàng</span>
            <strong>{{ bank.bankName }}</strong>
          </div>
        </div>

        <div class="qr-wrapper">
          <div class="qr-box">
            <img
              class="qr-image"
              :src="bank.qrImage"
              alt="QR Code Payment"
            />

            <div class="qr-label">
              QUÉT ĐỂ THANH TOÁN
            </div>
          </div>
        </div>

        <div class="secure-box">
          <div>
            <span class="material-symbols-outlined">verified</span>
            <strong>Giao dịch được mã hóa an toàn</strong>
          </div>

          <p>
            Hệ thống sẽ đối soát sau khi bạn tải ảnh minh chứng chuyển khoản lên.
          </p>
        </div>
      </section>

      <!-- Cột phải: Upload + Tóm tắt -->
      <aside class="right-column">
        <section class="upload-card">
          <h3>
            <span class="material-symbols-outlined">upload_file</span>
            Bằng chứng thanh toán
          </h3>

          <label class="upload-box">
            <input
              type="file"
              accept="image/png,image/jpeg,application/pdf"
              @change="handleFileChange"
            />

            <div class="upload-icon">
              <span class="material-symbols-outlined">add</span>
            </div>

            <p>{{ proofFileName || "Tải ảnh minh chứng chuyển khoản" }}</p>
            <small>PNG, JPG hoặc PDF, tối đa 5MB</small>
          </label>
        </section>

        <section class="summary-card">
          <h3>Chi tiết đặt sân</h3>

          <div class="summary-list">
            <div class="summary-row">
              <span>Sân vận động</span>
              <strong>{{ field.name }}</strong>
            </div>

            <div class="summary-row">
              <span>Địa chỉ</span>
              <strong>{{ field.address }}</strong>
            </div>

            <div class="summary-row">
              <span>Thời gian</span>
              <strong>{{ bookingTimeText }}</strong>
            </div>

            <div class="summary-row">
              <span>Sử dụng tủ đồ</span>
              <strong>Miễn phí</strong>
            </div>

            <div class="summary-total">
              <span>Tổng cộng</span>
              <strong>{{ formatCurrency(totalAmount) }}</strong>
            </div>
          </div>
        </section>

        <button class="confirm-payment-button" @click="confirmPayment">
          Xác nhận thanh toán
          <span class="material-symbols-outlined">check_circle</span>
        </button>
      </aside>
    </div>
  </div>
</template>

<script>
export default {
  name: "ThanhToan",

  data() {
    return {
      proofFileName: "",

      field: {
        id: 1,
        name: "Sân bóng đá Đại học Y",
        address: "1 Tôn Thất Tùng, Đống Đa, Hà Nội",
        sport: "Bóng đá",
        image:
          "https://lh3.googleusercontent.com/aida-public/AB6AXuANOKmqYm270OK_PCslBdJvj-0kWoHtISkuBVDtYHGJMbORW6KRmz2vT29m4DodR7RzIfFPQC_GLnZyPtdVg3rvi5umRlIaM6EXXruAI2yTOYX-WFVlmg9wfRTxWjMV9rZ6gekf-yhZozqeDY8U12NsbTSDpN4tzG-eGxMvzS3yWVbfv70eQ4L42MW7qRSzSiLJ6ZDnbdAPtMX6ey_3gyYh2HAoYag8Nwhqg5aOZ0c7Yr8-ngnVHMDlsrTmFjarOmEMfvWxPAYT5JE",
      },

      bank: {
        accountName: "NGUYEN VAN A",
        accountNumber: "1234567890",
        bankName: "Vietcombank",
        qrImage:
          "https://lh3.googleusercontent.com/aida-public/AB6AXuBrSkbkT2hVdANB0GZoRTG14L-_3P_tgpYpX5n6Gq6KFhhgH-VGPG8f-h_D9SF5dgJGRhtuCBEfHPdn4FfPkNyNHaZu-Q-UcOznZPfi2q1xbeaVTBdVBHxQHAuGmAoNwVEPn03Ghq4dTjUrRnOetCSavaF6ScZ2egIWE8WazQ8dzUprzFIZ1UaxRuHhSsempn8ZiZwklg-KQPnihyNt3_-nkPko0Icdko4vg2wNkkoPaiZTfhoClHFkc2Ts-GLZwj7vK_ZXbsAPGfg",
      },

      selectedSlots: [
        {
          id: 4,
          fieldId: 1,
          scheduleDate: "2026-05-06",
          startTime: "09:00",
          endTime: "10:00",
          price: 120000,
        },
        {
          id: 5,
          fieldId: 1,
          scheduleDate: "2026-05-06",
          startTime: "10:00",
          endTime: "11:00",
          price: 120000,
        },
      ],
    };
  },

  computed: {
    totalAmount() {
      return this.selectedSlots.reduce((sum, slot) => sum + slot.price, 0);
    },

    bookingTimeText() {
      if (this.selectedSlots.length === 0) {
        return "Chưa chọn khung giờ";
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

    copyAccountNumber() {
      navigator.clipboard.writeText(this.bank.accountNumber);
      alert("Đã sao chép số tài khoản.");
    },

    handleFileChange(event) {
      const file = event.target.files[0];

      if (!file) {
        this.proofFileName = "";
        return;
      }

      const maxSize = 5 * 1024 * 1024;

      if (file.size > maxSize) {
        alert("File không được vượt quá 5MB.");
        event.target.value = "";
        this.proofFileName = "";
        return;
      }

      this.proofFileName = file.name;
    },

    confirmPayment() {
      

      const paymentPayload = {
        field_id: this.field.id,
        field_name: this.field.name,
        booking_date: "2026-05-06",
        selected_schedules: this.selectedSlots.map((slot) => ({
          schedule_id: slot.id,
          start_time: slot.startTime,
          end_time: slot.endTime,
          price: slot.price,
        })),
        payment_method: "bank_transfer",
        payment_proof: this.proofFileName,
        total_amount: this.totalAmount,
        status: "pending_confirm",
      };

      this.$router.push("/client/dat-san-thanh-cong");
    },
  },
};
</script>

<style scoped>
.thanh-toan-page {
  min-height: 100vh;
  background: #fbf8ff;
  color: #191b25;
  font-family: "Lexend", sans-serif;
  padding: 40px;
}

.page-header {
  max-width: 1920px;
  margin: 0 auto 40px;
  padding: 0 24px;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #64748b;
  font-size: 12px;
  margin-bottom: 8px;
}

.breadcrumb .material-symbols-outlined {
  font-size: 16px;
}

.breadcrumb strong {
  color: #003ec7;
}

.page-header h1 {
  margin: 0;
  font-size: 48px;
  line-height: 1.2;
  font-weight: 700;
  color: #191b25;
  letter-spacing: -0.02em;
}

.page-header p {
  margin: 8px 0 0;
  color: #434656;
  font-size: 16px;
}

.payment-layout {
  max-width: 1920px;
  margin: 0 auto;
  padding: 0 24px;
  display: grid;
  grid-template-columns: 7fr 5fr;
  gap: 24px;
  align-items: flex-start;
}

.bank-card,
.upload-card,
.summary-card {
  background: #ffffff;
  border: 1px solid #c3c5d9;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.bank-card {
  padding: 24px;
}

.bank-info-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
  margin-bottom: 24px;
}

.bank-info-item {
  background: #ffffff;
  border: 1px solid #c3c5d9;
  border-radius: 8px;
  padding: 16px;
}

.bank-info-item span {
  display: block;
  font-size: 12px;
  color: #434656;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin-bottom: 4px;
}

.bank-info-item strong {
  color: #191b25;
  font-size: 14px;
}

.primary-text {
  color: #003ec7 !important;
}

.copy-row {
  display: flex;
  align-items: center;
  gap: 8px;
}

.copy-button {
  border: none;
  background: transparent;
  color: #003ec7;
  cursor: pointer;
  padding: 0;
  display: flex;
}

.copy-button .material-symbols-outlined {
  font-size: 18px;
}

.qr-wrapper {
  display: flex;
  justify-content: center;
  margin: 32px 0 24px;
}

.qr-box {
  position: relative;
  background: #ffffff;
  border: 2px dashed #003ec7;
  border-radius: 12px;
  padding: 24px;
}

.qr-image {
  width: 320px;
  height: 320px;
  object-fit: contain;
  display: block;
}

.qr-label {
  position: absolute;
  top: -14px;
  left: 50%;
  transform: translateX(-50%);
  background: #003ec7;
  color: #ffffff;
  padding: 6px 16px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
  white-space: nowrap;
}

.secure-box {
  text-align: center;
  max-width: 480px;
  margin: 0 auto;
}

.secure-box div {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  color: #005316;
  margin-bottom: 8px;
}

.secure-box .material-symbols-outlined {
  color: #006e20;
}

.secure-box p {
  margin: 0;
  color: #434656;
  font-size: 12px;
  line-height: 1.5;
}

.right-column {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.upload-card {
  padding: 24px;
}

.upload-card h3 {
  margin: 0 0 24px;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 24px;
  font-weight: 600;
}

.upload-card h3 .material-symbols-outlined {
  color: #003ec7;
}

.upload-box {
  position: relative;
  height: 256px;
  border: 2px dashed #c3c5d9;
  border-radius: 12px;
  background: #ffffff;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
  text-align: center;
}

.upload-box:hover {
  border-color: #003ec7;
  background: #f3f2ff;
}

.upload-box input {
  position: absolute;
  inset: 0;
  opacity: 0;
  cursor: pointer;
}

.upload-icon {
  width: 64px;
  height: 64px;
  background: #68fc76;
  color: #007322;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
  transition: 0.2s;
}

.upload-box:hover .upload-icon {
  transform: scale(1.1);
}

.upload-icon .material-symbols-outlined {
  font-size: 40px;
}

.upload-box p {
  margin: 0 0 8px;
  font-weight: 700;
  color: #191b25;
}

.upload-box small {
  color: #434656;
  font-size: 12px;
}

.summary-card {
  background: #0052ff;
  color: #dfe3ff;
  padding: 24px;
}

.summary-card h3 {
  margin: 0 0 16px;
  color: #ffffff;
  font-size: 18px;
  font-weight: 700;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(223, 227, 255, 0.25);
}

.summary-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.summary-row,
.summary-total {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
}

.summary-row span {
  opacity: 0.85;
}

.summary-row strong {
  color: #ffffff;
  text-align: right;
}

.summary-total {
  margin-top: 8px;
  padding-top: 16px;
  border-top: 1px solid rgba(223, 227, 255, 0.25);
}

.summary-total span {
  color: #ffffff;
  font-size: 18px;
}

.summary-total strong {
  color: #ffffff;
  font-size: 28px;
  font-weight: 900;
}

.confirm-payment-button {
  width: 100%;
  border: none;
  background: #003ec7;
  color: #ffffff;
  padding: 20px;
  border-radius: 12px;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  box-shadow: 0 10px 24px rgba(0, 62, 199, 0.22);
  transition: 0.2s;
}

.confirm-payment-button:hover {
  filter: brightness(1.1);
}

.confirm-payment-button:active {
  transform: scale(0.98);
}

@media (max-width: 1200px) {
  .payment-layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 900px) {
  .thanh-toan-page {
    padding: 32px 20px;
  }

  .page-header {
    padding: 0;
  }

  .payment-layout {
    padding: 0;
  }

  .page-header h1 {
    font-size: 36px;
  }

  .bank-info-grid {
    grid-template-columns: 1fr;
  }

  .qr-image {
    width: 260px;
    height: 260px;
  }
}
</style>