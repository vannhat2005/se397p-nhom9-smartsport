<template>
    <div class="lich-su-page">
        <!-- Header -->
        <div class="page-header">
            <div>
                <h1>Lịch sử đặt sân</h1>
                <p>Quản lý và theo dõi các đơn đặt sân thể thao của bạn.</p>
            </div>
        </div>

        <!-- Table -->
        <section class="history-card">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Mã đơn</th>
                            <th>Tên sân</th>
                            <th>Ngày đặt</th>
                            <th>Giờ bắt đầu</th>
                            <th>Giờ kết thúc</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th class="text-right">Thao tác</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="booking in bookings" :key="booking.id">
                            <td class="booking-code">{{ booking.code }}</td>

                            <td>
                                <div class="field-cell">
                                    <div class="field-icon">
                                        <span class="material-symbols-outlined">
                                            {{ booking.icon }}
                                        </span>
                                    </div>

                                    <span>{{ booking.fieldName }}</span>
                                </div>
                            </td>

                            <td>{{ booking.bookingDate }}</td>
                            <td>{{ booking.startTime }}</td>
                            <td>{{ booking.endTime }}</td>
                            <td class="money">{{ formatCurrency(booking.totalAmount) }}</td>

                            <td>
                                <span class="status-badge" :class="booking.statusClass">
                                    {{ booking.statusText }}
                                </span>
                            </td>

                            <td class="text-right">
                                <button class="detail-button" @click="openModal(booking)">
                                    Xem chi tiết
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="table-footer">
                <span>Đang hiển thị {{ bookings.length }} trên 4 đơn đặt</span>

                <div class="pagination">
                    <button>
                        <span class="material-symbols-outlined">chevron_left</span>
                    </button>

                    <button>
                        <span class="material-symbols-outlined">chevron_right</span>
                    </button>
                </div>
            </div>
        </section>

        <!-- Modal -->
        <div v-if="selectedBooking" class="modal-overlay" @click.self="closeModal">
            <div class="modal-box">
                <div class="modal-header">
                    <h2>Chi tiết đơn đặt sân</h2>

                    <button class="close-button" @click="closeModal">
                        <span class="material-symbols-outlined">close</span>
                    </button>
                </div>

                <div class="modal-body">
                    <div class="detail-grid">
                        <div>
                            <p>Mã đơn hàng</p>
                            <strong class="primary-text">{{ selectedBooking.code }}</strong>
                        </div>

                        <div>
                            <p>Trạng thái</p>
                            <span class="status-badge small" :class="selectedBooking.statusClass">
                                {{ selectedBooking.statusText }}
                            </span>
                        </div>
                    </div>

                    <div class="detail-list">
                        <div class="detail-row">
                            <span>Tên khách hàng:</span>
                            <strong>{{ selectedBooking.customerName }}</strong>
                        </div>

                        <div class="detail-row">
                            <span>Tên sân:</span>
                            <strong>{{ selectedBooking.fieldName }}</strong>
                        </div>

                        <div class="detail-row">
                            <span>Loại sân:</span>
                            <strong>{{ selectedBooking.fieldType }}</strong>
                        </div>

                        <div class="detail-row">
                            <span>Ngày đặt:</span>
                            <strong>{{ selectedBooking.bookingDate }}</strong>
                        </div>

                        <div class="detail-row">
                            <span>Thời gian:</span>
                            <strong>
                                {{ selectedBooking.startTime }} - {{ selectedBooking.endTime }}
                                ({{ selectedBooking.totalHours }} giờ)
                            </strong>
                        </div>

                        <div class="detail-row total-row">
                            <span>Tổng tiền:</span>
                            <strong>{{ formatCurrency(selectedBooking.totalAmount) }}</strong>
                        </div>
                    </div>

                    <div class="note-box">
                        <p>Ghi chú</p>
                        <div>
                            {{ selectedBooking.note }}
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="cancel-button" @click="closeModal">
                        Đóng
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "LichSuDatSan",

    data() {
        return {
            selectedBooking: null,

            bookings: [
                {
                    id: 1,
                    code: "#SS-060526",
                    customerName: "Nguyễn Văn A",
                    fieldName: "Sân bóng đá Đại học Y",
                    fieldType: "Bóng đá",
                    bookingDate: "06/05/2026",
                    startTime: "09:00",
                    endTime: "11:00",
                    totalHours: 2,
                    totalAmount: 240000,
                    statusText: "Đang xử lý",
                    statusClass: "processing",
                    icon: "sports_soccer",
                    note: "Khách đã chuyển khoản và đang chờ chủ sân xác nhận.",
                },
                {
                    id: 2,
                    code: "#SS-060527",
                    customerName: "Nguyễn Văn A",
                    fieldName: "Sân bóng rổ Thống Nhất",
                    fieldType: "Bóng rổ",
                    bookingDate: "08/05/2026",
                    startTime: "18:00",
                    endTime: "20:00",
                    totalHours: 2,
                    totalAmount: 300000,
                    statusText: "Đã xác nhận",
                    statusClass: "confirmed",
                    icon: "sports_basketball",
                    note: "Đơn đặt sân đã được chủ sân xác nhận.",
                },
                {
                    id: 3,
                    code: "#SS-060528",
                    customerName: "Nguyễn Văn A",
                    fieldName: "Sân cầu lông Hòa Xuân",
                    fieldType: "Cầu lông",
                    bookingDate: "10/05/2026",
                    startTime: "07:00",
                    endTime: "08:00",
                    totalHours: 1,
                    totalAmount: 80000,
                    statusText: "Đã thanh toán",
                    statusClass: "paid",
                    icon: "sports_tennis",
                    note: "Đã thanh toán thành công.",
                },
                {
                    id: 4,
                    code: "#SS-060529",
                    customerName: "Nguyễn Văn A",
                    fieldName: "Sân bóng chuyền Phú Thọ",
                    fieldType: "Bóng chuyền",
                    bookingDate: "12/05/2026",
                    startTime: "16:00",
                    endTime: "18:00",
                    totalHours: 2,
                    totalAmount: 200000,
                    statusText: "Đã hủy",
                    statusClass: "cancelled",
                    icon: "sports_volleyball",
                    note: "Khách hàng đã hủy đơn trước thời gian bắt đầu.",
                },
            ],
        };
    },

    methods: {
        formatCurrency(value) {
            return new Intl.NumberFormat("vi-VN").format(value) + "đ";
        },

        openModal(booking) {
            this.selectedBooking = booking;
        },

        closeModal() {
            this.selectedBooking = null;
        },
    },
};
</script>

<style scoped>
.lich-su-page {
    min-height: 100vh;
    background: #fbf8ff;
    color: #191b25;
    font-family: "Lexend", sans-serif;
    padding: 48px 40px;
}

.page-header {
    max-width: 1920px;
    margin: 0 auto 32px;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
}

.page-header h1 {
    margin: 0;
    font-size: 48px;
    line-height: 1.2;
    font-weight: 700;
    letter-spacing: -0.02em;
}

.page-header p {
    margin: 8px 0 0;
    color: #737688;
    font-size: 18px;
    line-height: 1.6;
}

.history-card {
    max-width: 1920px;
    margin: 0 auto;
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.table-wrapper {
    width: 100%;
    overflow-x: auto;
}

table {
    width: 100%;
    border-collapse: collapse;
    text-align: left;
}

thead tr {
    background: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
}

th {
    padding: 16px 24px;
    color: #434656;
    font-size: 12px;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.06em;
    white-space: nowrap;
}

td {
    padding: 20px 24px;
    border-bottom: 1px solid #f1f5f9;
    color: #191b25;
    white-space: nowrap;
}

tbody tr {
    transition: 0.2s;
}

tbody tr:hover {
    background: #f8fafc;
}

.text-right {
    text-align: right;
}

.booking-code {
    color: #003ec7;
    font-weight: 700;
}

.money {
    font-weight: 700;
}

.field-cell {
    display: flex;
    align-items: center;
    gap: 12px;
    font-weight: 700;
}

.field-icon {
    width: 32px;
    height: 32px;
    background: #dde1ff;
    color: #003ec7;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
}

.field-icon .material-symbols-outlined {
    font-size: 18px;
}

.status-badge {
    display: inline-flex;
    align-items: center;
    padding: 4px 10px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
}

.status-badge.small {
    font-size: 10px;
}

.status-badge.paid {
    background: #dcfce7;
    color: #166534;
}

.status-badge.processing {
    background: #fef3c7;
    color: #92400e;
}

.status-badge.confirmed {
    background: #dbeafe;
    color: #1e40af;
}

.status-badge.cancelled {
    background: #fee2e2;
    color: #991b1b;
}

.detail-button {
    border: none;
    background: transparent;
    color: #003ec7;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
}

.detail-button:hover {
    text-decoration: underline;
}

.table-footer {
    padding: 16px 24px;
    background: #f8fafc;
    border-top: 1px solid #e2e8f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.table-footer span {
    color: #737688;
    font-size: 12px;
}

.pagination {
    display: flex;
    gap: 8px;
}

.pagination button {
    border: 1px solid #c3c5d9;
    background: #ffffff;
    border-radius: 6px;
    padding: 6px;
    cursor: pointer;
    display: flex;
}

.pagination button:hover {
    background: #ededfb;
}

/* Modal */
.modal-overlay {
    position: fixed;
    inset: 0;
    z-index: 100;
    background: rgba(15, 23, 42, 0.5);
    backdrop-filter: blur(4px);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 16px;
}

.modal-box {
    width: 100%;
    max-width: 560px;
    background: #ffffff;
    border-radius: 16px;
    overflow: hidden;
    box-shadow: 0 25px 60px rgba(15, 23, 42, 0.3);
}

.modal-header {
    padding: 24px;
    background: #f8fafc;
    border-bottom: 1px solid #f1f5f9;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.modal-header h2 {
    margin: 0;
    font-size: 24px;
    font-weight: 600;
}

.close-button {
    border: none;
    background: transparent;
    color: #737688;
    cursor: pointer;
}

.close-button:hover {
    color: #191b25;
}

.modal-body {
    padding: 24px;
}

.detail-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 24px;
}

.detail-grid p,
.note-box p {
    margin: 0 0 6px;
    color: #737688;
    font-size: 12px;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.06em;
}

.primary-text {
    color: #003ec7;
}

.detail-list {
    margin-top: 24px;
    padding-top: 16px;
    border-top: 1px solid #f1f5f9;
}

.detail-row {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 16px;
    margin-bottom: 14px;
}

.detail-row span {
    color: #737688;
}

.detail-row strong {
    color: #191b25;
    text-align: right;
}

.total-row {
    padding-top: 12px;
    border-top: 1px solid #f1f5f9;
}

.total-row strong {
    color: #003ec7;
    font-size: 24px;
}

.note-box {
    margin-top: 24px;
    padding-top: 16px;
    border-top: 1px solid #f1f5f9;
}

.note-box div {
    background: #f8fafc;
    border: 1px solid #f1f5f9;
    border-radius: 8px;
    padding: 12px;
    color: #191b25;
    font-style: italic;
    line-height: 1.6;
}

.modal-footer {
    padding: 24px;
    background: #f8fafc;
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}

.cancel-button,
.print-button {
    border-radius: 8px;
    padding: 10px 24px;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
}

.cancel-button {
    background: #ffffff;
    border: 1px solid #c3c5d9;
    color: #191b25;
}

.cancel-button:hover {
    background: #f8fafc;
}

.print-button {
    border: none;
    background: #003ec7;
    color: #ffffff;
}

.print-button:hover {
    background: #0052ff;
}

@media (max-width: 900px) {
    .lich-su-page {
        padding: 32px 20px;
    }

    .page-header h1 {
        font-size: 36px;
    }

    .table-footer {
        flex-direction: column;
        gap: 12px;
        align-items: flex-start;
    }
}

@media (max-width: 640px) {
    .detail-grid {
        grid-template-columns: 1fr;
    }

    .detail-row {
        flex-direction: column;
        gap: 4px;
    }

    .detail-row strong {
        text-align: left;
    }

    .modal-footer {
        flex-direction: column;
    }

    .cancel-button,
    .print-button {
        width: 100%;
    }
}
</style>