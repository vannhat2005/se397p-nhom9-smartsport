<template>
    <div class="xac-nhan-page">
        <!-- Header nội dung -->
        <div class="page-header">
            <nav class="breadcrumb">
                <span>Địa điểm</span>
                <span class="material-symbols-outlined">chevron_right</span>
                <span>{{ field.name }}</span>
                <span class="material-symbols-outlined">chevron_right</span>
                <strong>Kiểm tra thông tin</strong>
            </nav>

            <h1>Kiểm tra thông tin đặt sân</h1>
            <p>
                Vui lòng kiểm tra kỹ các chi tiết bên dưới trước khi hoàn tất việc đặt sân.
            </p>
        </div>

        <div class="confirm-layout">
            <!-- Cột trái -->
            <div class="left-column">
                <!-- Thông tin sân -->
                <section class="venue-card">
                    <div class="venue-image-box">
                        <img class="venue-image" :src="field.image" :alt="field.name" />
                    </div>

                    <div class="venue-info">
                        <div class="verified-badge">
                            Cơ sở đã xác minh
                        </div>

                        <h2>{{ field.name }}</h2>

                        <div class="venue-meta">
                            <div>
                                <span class="material-symbols-outlined">location_on</span>
                                <span>{{ field.address }}</span>
                            </div>

                            <div>
                                <span class="material-symbols-outlined star-icon">star</span>
                                <span>4.8 đánh giá</span>
                            </div>

                            <div>
                                <span class="material-symbols-outlined">stadium</span>
                                <span>{{ fieldType.name }}</span>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Ngày và giờ -->
                <section class="selection-grid">
                    <div class="selection-card">
                        <div class="date-box">
                            <span>Th05</span>
                            <strong>06</strong>
                        </div>

                        <div>
                            <p>Ngày đã chọn</p>
                            <h3>Thứ Tư, 06/05/2026</h3>
                        </div>
                    </div>

                    <div class="selection-card">
                        <div class="icon-box">
                            <span class="material-symbols-outlined">schedule</span>
                        </div>

                        <div>
                            <p>Khung giờ</p>

                            <div class="time-tags">
                                <span v-for="slot in selectedSlots" :key="slot.id">
                                    {{ slot.startTime }} - {{ slot.endTime }}
                                </span>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Chính sách -->
                <section class="policy-card">
                    <div class="policy-title">
                        <span class="material-symbols-outlined">info</span>
                        <h3>Chính sách đặt sân</h3>
                    </div>

                    <ul>
                        <li>
                            <span class="material-symbols-outlined check-icon">check_circle</span>
                            <span>Hủy miễn phí trước 24 giờ kể từ khi giờ chơi bắt đầu.</span>
                        </li>

                        <li>
                            <span class="material-symbols-outlined check-icon">check_circle</span>
                            <span>Vui lòng có mặt trước giờ đặt sân 10 phút để làm thủ tục.</span>
                        </li>

                        <li>
                            <span class="material-symbols-outlined">lock</span>
                            <span>Thông tin đặt sân sẽ được ghi nhận sau khi xác nhận.</span>
                        </li>
                    </ul>
                </section>
            </div>

            <!-- Cột phải -->
            <aside class="right-column">
                <div class="order-card">
                    <div class="order-header">
                        <h3>Tóm tắt đơn hàng</h3>
                    </div>

                    <div class="order-body">
                        <div class="order-row">
                            <span>Thuê sân ({{ totalHours }} giờ)</span>
                            <strong>{{ formatCurrency(subtotal) }}</strong>
                        </div>

                        <div class="order-row">
                            <span>Phí dịch vụ</span>
                            <strong>{{ formatCurrency(serviceFee) }}</strong>
                        </div>

                        <div class="order-row dashed">
                            <span>Sử dụng tủ đồ</span>
                            <strong class="free-text">Miễn phí</strong>
                        </div>

                        <div class="order-total">
                            <span>Tổng cộng</span>
                            <strong>{{ formatCurrency(totalAmount) }}</strong>
                        </div>
                    </div>

                    <div class="order-actions">
                        <button class="confirm-button" @click="confirmBooking">
                            Xác nhận đặt sân
                            <span class="material-symbols-outlined">arrow_forward</span>
                        </button>

                        <div class="secure-text">
                            <span class="material-symbols-outlined">lock</span>
                            <span>Thông tin đặt sân được bảo mật</span>
                        </div>

                        <button class="cancel-button" @click="$router.back()">
                            Hủy bỏ
                        </button>
                    </div>

                    <div class="protect-row">
                        <span class="material-symbols-outlined">shield</span>
                        <p>Bảo vệ thông tin người dùng</p>
                    </div>
                </div>
            </aside>
        </div>
    </div>
</template>

<script>
export default {
    name: "XacNhanDatSan",

    data() {
        return {
            // fields
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

            // field_types
            fieldType: {
                id: 1,
                name: "Bóng đá",
                description: "Sân bóng đá mini",
                status: 1,
            },

            // Dữ liệu khung giờ đã chọn từ trang chi tiết sân
            selectedSlots: [
                {
                    id: 4,
                    fieldId: 1,
                    scheduleDate: "2026-05-06",
                    startTime: "09:00",
                    endTime: "10:00",
                    price: 120000,
                    status: 1,
                },
                {
                    id: 5,
                    fieldId: 1,
                    scheduleDate: "2026-05-06",
                    startTime: "10:00",
                    endTime: "11:00",
                    price: 120000,
                    status: 1,
                },
            ],
        };
    },

    computed: {
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
        formatCurrency(value) {
            return new Intl.NumberFormat("vi-VN").format(value) + "đ";
        },

        confirmBooking() {
            const bookingPayload = {
                field_id: this.field.id,
                field_name: this.field.name,
                booking_date: "2026-05-06",
                schedules: this.selectedSlots.map((slot) => ({
                    schedule_id: slot.id,
                    start_time: slot.startTime,
                    end_time: slot.endTime,
                    price: slot.price,
                })),
                total_amount: this.totalAmount,
                status: 1,
            };

            this.$router.push("/client/thanh-toan");
        },
    },
};
</script>

<style scoped>
.xac-nhan-page {
    min-height: 100vh;
    background: #fbf8ff;
    color: #191b25;
    font-family: "Lexend", sans-serif;
    padding: 64px 40px;
}

.page-header {
    max-width: 1920px;
    margin: 0 auto 48px;
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
    font-size: 18px;
    line-height: 1.6;
    color: #434656;
}

.confirm-layout {
    max-width: 1920px;
    margin: 0 auto;
    display: grid;
    grid-template-columns: 8fr 4fr;
    gap: 24px;
}

.left-column {
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.venue-card,
.selection-card,
.policy-card,
.order-card {
    background: #ffffff;
    border: 1px solid #c3c5d9;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.venue-card {
    padding: 24px;
    display: flex;
    gap: 24px;
}

.venue-image-box {
    width: 33.333%;
    aspect-ratio: 16 / 9;
    overflow: hidden;
    border-radius: 8px;
    flex-shrink: 0;
}

.venue-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.venue-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.verified-badge {
    width: fit-content;
    background: #68fc76;
    color: #007322;
    font-size: 10px;
    font-weight: 700;
    padding: 4px 8px;
    border-radius: 6px;
    text-transform: uppercase;
    letter-spacing: 0.04em;
    margin-bottom: 8px;
}

.venue-info h2 {
    margin: 0;
    font-size: 24px;
    line-height: 1.4;
    font-weight: 600;
    color: #191b25;
}

.venue-meta {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    margin-top: 8px;
    color: #434656;
}

.venue-meta div {
    display: flex;
    align-items: center;
    gap: 4px;
    font-weight: 600;
}

.venue-meta .material-symbols-outlined {
    font-size: 18px;
}

.star-icon {
    color: #f59e0b;
    font-variation-settings: "FILL" 1;
}

.selection-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 24px;
}

.selection-card {
    padding: 24px;
    display: flex;
    align-items: center;
    gap: 24px;
}

.date-box {
    width: 64px;
    height: 64px;
    background: #dde1ff;
    color: #001452;
    border-radius: 12px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.date-box span {
    font-size: 12px;
    font-weight: 700;
    text-transform: uppercase;
    opacity: 0.75;
}

.date-box strong {
    font-size: 32px;
    line-height: 1;
}

.icon-box {
    width: 64px;
    height: 64px;
    background: #e7e7f5;
    color: #003ec7;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.icon-box .material-symbols-outlined {
    font-size: 32px;
}

.selection-card p {
    margin: 0;
    color: #434656;
    font-size: 12px;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.08em;
}

.selection-card h3 {
    margin: 4px 0 0;
    font-size: 20px;
    font-weight: 600;
}

.time-tags {
    margin-top: 4px;
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.time-tags span {
    background: #003ec7;
    color: #ffffff;
    padding: 6px 12px;
    border-radius: 6px;
    font-size: 12px;
    font-weight: 700;
}

.policy-card {
    background: #f3f2ff;
    padding: 24px;
    border-color: rgba(195, 197, 217, 0.3);
}

.policy-title {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #003ec7;
    margin-bottom: 16px;
}

.policy-title h3 {
    margin: 0;
    font-size: 16px;
}

.policy-card ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.policy-card li {
    display: flex;
    align-items: flex-start;
    gap: 12px;
    color: #434656;
    line-height: 1.6;
    margin-bottom: 12px;
}

.policy-card li:last-child {
    margin-bottom: 0;
}

.check-icon {
    color: #16a34a;
    font-variation-settings: "FILL" 1;
}

.right-column {
    position: relative;
}

.order-card {
    position: sticky;
    top: 100px;
}

.order-header {
    padding: 24px;
    border-bottom: 1px solid #c3c5d9;
}

.order-header h3 {
    margin: 0;
    font-size: 24px;
    font-weight: 600;
}

.order-body {
    padding: 24px;
}

.order-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #434656;
    margin-bottom: 16px;
}

.order-row strong {
    color: #191b25;
}

.order-row.dashed {
    padding-bottom: 16px;
    border-bottom: 1px dashed #c3c5d9;
}

.free-text {
    color: #16a34a !important;
}

.order-total {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-top: 8px;
}

.order-total span {
    font-size: 24px;
    font-weight: 600;
}

.order-total strong {
    font-size: 32px;
    font-weight: 600;
    color: #003ec7;
}

.order-actions {
    padding: 24px;
    background: #ffffff;
}

.confirm-button {
    width: 100%;
    border: none;
    background: #003ec7;
    color: #ffffff;
    padding: 16px;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    transition: 0.2s;
}

.confirm-button:hover {
    background: #0052ff;
}

.confirm-button:active {
    transform: scale(0.98);
}

.secure-text {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 6px;
    color: #434656;
    opacity: 0.8;
    font-size: 12px;
    padding: 12px 0;
}

.secure-text .material-symbols-outlined {
    font-size: 16px;
}

.cancel-button {
    width: 100%;
    background: #ffffff;
    border: 1px solid #737688;
    color: #434656;
    padding: 16px;
    border-radius: 8px;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
}

.cancel-button:hover {
    border-color: #003ec7;
    color: #003ec7;
}

.protect-row {
    padding: 24px;
    border-top: 1px solid #c3c5d9;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 12px;
    color: #737688;
    filter: grayscale(100%);
    opacity: 0.65;
}

.protect-row p {
    margin: 0;
    font-size: 12px;
}

/* Responsive */
@media (max-width: 1200px) {
    .confirm-layout {
        grid-template-columns: 1fr;
    }

    .order-card {
        position: static;
    }
}

@media (max-width: 900px) {
    .xac-nhan-page {
        padding: 32px 20px;
    }

    .page-header h1 {
        font-size: 36px;
    }

    .venue-card {
        flex-direction: column;
    }

    .venue-image-box {
        width: 100%;
    }

    .selection-grid {
        grid-template-columns: 1fr;
    }
}
</style>