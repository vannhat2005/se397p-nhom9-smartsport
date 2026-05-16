<template>
    <div class="approve-field-page">
        <!-- Page Header -->
        <div class="page-header">
            <h2>Duyệt sân</h2>
            <p>
                Xem xét và phê duyệt các yêu cầu đăng ký sân mới từ chủ sở hữu.
            </p>
        </div>

        <!-- Dashboard Stats Summary -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-top">
                    <div class="stat-icon blue">
                        <span class="material-symbols-outlined">pending_actions</span>
                    </div>

                    <span class="stat-percent blue-text">+12%</span>
                </div>

                <p>Đang chờ duyệt</p>
                <h3>{{ totalElements }}</h3>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <div class="stat-icon green">
                        <span class="material-symbols-outlined">check_circle</span>
                    </div>

                    <span class="stat-percent green-text">+5.4%</span>
                </div>

                <p>Đã duyệt tháng này</p>
                <h3>{{ approvedCount }}</h3>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <div class="stat-icon red">
                        <span class="material-symbols-outlined">cancel</span>
                    </div>

                    <span class="stat-percent red-text">-2.1%</span>
                </div>

                <p>Đã từ chối</p>
                <h3>{{ rejectedCount }}</h3>
            </div>
        </div>

        <!-- Danh sách sân chờ duyệt -->
        <div class="field-grid">
            <div v-for="field in displayedFields" :key="field.id" class="field-card">
                <div class="field-image-box">
                    <img class="field-image" :src="field.image" :alt="field.name" />

                    <div class="field-type-badge">
                        {{ field.field_type_name }}
                    </div>
                </div>

                <div class="field-content">
                    <h4>{{ field.name }}</h4>

                    <div class="field-address">
                        <span class="material-symbols-outlined">location_on</span>
                        <span>{{ field.address }}</span>
                    </div>

                    <div class="field-info-grid">
                        <div>
                            <span>Chủ sở hữu</span>
                            <strong>{{ field.owner_name || "Không rõ chủ sân" }}</strong>
                        </div>

                        <div>
                            <span>Ngày gửi</span>
                            <strong>{{ formatDate(field.created_at) }}</strong>
                        </div>
                    </div>

                    <div class="pending-box">
                        <span>Chờ duyệt</span>
                    </div>

                    <div class="action-buttons">
                        <button class="approve-button" @click="approveField(field)">
                            <span class="material-symbols-outlined">check_circle</span>
                            Phê duyệt
                        </button>

                        <button class="reject-button" @click="rejectField(field)">
                            Từ chối
                        </button>
                    </div>
                </div>
            </div>

            <div v-if="displayedFields.length === 0" class="empty-box">
                Không có sân nào đang chờ duyệt.
            </div>
        </div>

        <!-- Pagination -->
        <div class="pagination-row">
            <p>
                Hiển thị
                <span>{{ totalElements === 0 ? 0 : startIndex + 1 }} - {{ endIndex }}</span>
                trên
                <span>{{ totalElements }}</span>
                yêu cầu
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
</template>

<script>
import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export default {
    name: "DuyetSan",

    data() {
        return {
            currentPage: 1,
            pageSize: 4,
            totalPages: 1,
            totalElements: 0,

            approvedCount: 142,
            rejectedCount: 18,

            pendingFields: [],

            defaultImage:
                "https://images.unsplash.com/photo-1459865264687-595d652de67e?q=80&w=1200",
        };
    },

    computed: {
        startIndex() {
            return (this.currentPage - 1) * this.pageSize;
        },

        displayedFields() {
            return this.pendingFields;
        },

        endIndex() {
            return this.startIndex + this.displayedFields.length;
        },
    },

    mounted() {
        this.loadPendingFields();
    },

    methods: {
        loadPendingFields() {
            axios
                .get(`${API_BASE_URL}/api/fields/admin/pending`, {
                    params: {
                        page: this.currentPage,
                        size: this.pageSize,
                    },
                })
                .then((response) => {
                    const pageData = response.data.data;

                    this.pendingFields = pageData.data.map((field) => ({
                        id: field.id,
                        user_id: field.user_id ?? field.userId,
                        owner_name: field.owner_name ?? field.ownerName,
                        field_type_id: field.field_type_id ?? field.fieldTypeId,
                        field_type_name:
                            field.field_type_name ??
                            field.fieldTypeName ??
                            "Đang cập nhật",
                        name: field.name,
                        address: field.address,
                        description: field.description,
                        open_time: field.open_time ?? field.openTime,
                        close_time: field.close_time ?? field.closeTime,
                        status: field.status,
                        created_at: field.created_at ?? field.createdAt,
                        updated_at: field.updated_at ?? field.updatedAt,

                        // Tạm thời dùng ảnh chung
                        image: field.image || this.defaultImage,
                    }));

                    this.currentPage = pageData.currentPage;
                    this.pageSize = pageData.pageSize;
                    this.totalPages = pageData.totalPages || 1;
                    this.totalElements = pageData.totalElements;
                })
                .catch((error) => {
                    console.error("Lỗi khi tải danh sách sân chờ duyệt:", error);
                    alert("Không thể tải danh sách sân chờ duyệt. Vui lòng thử lại sau.");
                });
        },

        approveField(field) {
            if (!confirm(`Bạn có chắc muốn phê duyệt sân "${field.name}" không?`)) {
                return;
            }

            axios
                .put(`${API_BASE_URL}/api/fields/admin/${field.id}/approve`, null, {
                    params: {
                        isApproved: true,
                    },
                })
                .then((response) => {
                    this.$toast.success(response.data.message || "Đã duyệt sân thành công.");
                    this.loadPendingFields();
                })
                .catch((error) => {
                    console.error("Lỗi khi duyệt sân:", error);
                    this.$toast.error("Duyệt sân thất bại. Vui lòng thử lại.");
                });
        },

        rejectField(field) {
            if (!confirm(`Bạn có chắc muốn từ chối sân "${field.name}" không?`)) {
                return;
            }

            axios
                .put(`${API_BASE_URL}/api/fields/admin/${field.id}/approve`, null, {
                    params: {
                        isApproved: false,
                    },
                })
                .then((response) => {
                    this.$toast.success(response.data.message || "Đã từ chối sân bóng.");
                    this.loadPendingFields();
                })
                .catch((error) => {
                    console.error("Lỗi khi từ chối sân:", error);
                    this.$toast.error("Từ chối sân thất bại. Vui lòng thử lại.");
                });
        },

        goToPage(page) {
            if (page < 1 || page > this.totalPages) {
                return;
            }

            this.currentPage = page;
            this.loadPendingFields();
        },

        prevPage() {
            this.goToPage(this.currentPage - 1);
        },

        nextPage() {
            this.goToPage(this.currentPage + 1);
        },

        formatDate(dateString) {
            if (!dateString) {
                return "Đang cập nhật";
            }

            const date = new Date(dateString);

            if (Number.isNaN(date.getTime())) {
                return dateString;
            }

            return date.toLocaleDateString("vi-VN");
        },
    },
};
</script>

<style scoped>
.approve-field-page {
    min-height: 100vh;
    background: #f8f9fa;
    color: #191c1d;
    font-family: "Inter", sans-serif;
}

/* Header */
.page-header {
    margin-bottom: 32px;
}

.page-header h2 {
    margin: 0 0 8px;
    color: #0f172a;
    font-size: 36px;
    line-height: 44px;
    font-weight: 800;
    letter-spacing: -0.02em;
}

.page-header p {
    margin: 0;
    color: #64748b;
    font-size: 16px;
    line-height: 24px;
}

/* Stats */
.stats-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 24px;
    margin-bottom: 32px;
}

.stat-card {
    background: #ffffff;
    padding: 24px;
    border-radius: 12px;
    border: 1px solid #f3f4f6;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.stat-top {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 16px;
}

.stat-icon {
    padding: 12px;
    border-radius: 8px;
    display: flex;
}

.stat-icon .material-symbols-outlined {
    font-size: 24px;
}

.stat-icon.blue {
    background: #eff6ff;
    color: #2563eb;
}

.stat-icon.green {
    background: #f0fdf4;
    color: #16a34a;
}

.stat-icon.red {
    background: #fef2f2;
    color: #dc2626;
}

.stat-percent {
    font-size: 12px;
    font-weight: 700;
    padding: 4px 8px;
    border-radius: 999px;
}

.blue-text {
    background: #eff6ff;
    color: #2563eb;
}

.green-text {
    background: #f0fdf4;
    color: #16a34a;
}

.red-text {
    background: #fef2f2;
    color: #dc2626;
}

.stat-card p {
    margin: 0;
    color: #64748b;
    font-size: 14px;
    font-weight: 600;
}

.stat-card h3 {
    margin: 4px 0 0;
    color: #0f172a;
    font-size: 24px;
    line-height: 32px;
    font-weight: 700;
}

/* Field Cards */
.field-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;
}

.field-card {
    background: #ffffff;
    border: 1px solid #f3f4f6;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
    transition: 0.25s;
    display: flex;
    flex-direction: column;
}

.field-card:hover {
    box-shadow: 0 10px 24px rgba(15, 23, 42, 0.12);
}

.field-image-box {
    position: relative;
    height: 128px;
    flex-shrink: 0;
}

.field-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.field-type-badge {
    position: absolute;
    top: 8px;
    left: 8px;
    background: rgba(15, 23, 42, 0.8);
    backdrop-filter: blur(8px);
    color: #ffffff;
    padding: 3px 8px;
    border-radius: 6px;
    font-size: 9px;
    font-weight: 800;
    text-transform: uppercase;
    letter-spacing: 0.05em;
}

.field-content {
    padding: 16px;
    display: flex;
    flex-direction: column;
    flex: 1;
}

.field-content h4 {
    margin: 0 0 8px;
    color: #0f172a;
    font-size: 14px;
    line-height: 20px;
    font-weight: 800;
}

.field-address {
    display: flex;
    align-items: flex-start;
    gap: 6px;
    color: #64748b;
    font-size: 12px;
    line-height: 16px;
    margin-bottom: 12px;
}

.field-address .material-symbols-outlined {
    font-size: 16px;
    margin-top: 1px;
}

.field-info-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 8px;
    margin-bottom: 16px;
}

.field-info-grid div {
    min-width: 0;
    display: flex;
    flex-direction: column;
}

.field-info-grid span {
    color: #94a3b8;
    font-size: 10px;
    line-height: 14px;
}

.field-info-grid strong {
    color: #334155;
    font-size: 12px;
    font-weight: 700;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.pending-box {
    margin-bottom: 16px;
}

.pending-box span {
    background: #fef3c7;
    color: #b45309;
    padding: 3px 8px;
    border-radius: 6px;
    font-size: 9px;
    font-weight: 800;
    text-transform: uppercase;
}

.action-buttons {
    margin-top: auto;
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.approve-button,
.reject-button {
    width: 100%;
    border-radius: 8px;
    padding: 8px 12px;
    font-size: 12px;
    font-weight: 800;
    cursor: pointer;
    transition: 0.2s;
}

.approve-button {
    border: none;
    background: #22c55e;
    color: #ffffff;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 6px;
}

.approve-button:hover {
    background: #16a34a;
}

.approve-button .material-symbols-outlined {
    font-size: 16px;
}

.reject-button {
    border: 1px solid #fecaca;
    background: #ffffff;
    color: #dc2626;
}

.reject-button:hover {
    background: #fef2f2;
}

.empty-box {
    grid-column: 1 / -1;
    background: #ffffff;
    border: 1px dashed #cbd5e1;
    border-radius: 12px;
    padding: 32px;
    text-align: center;
    color: #64748b;
    font-weight: 700;
}

/* Pagination */
.pagination-row {
    margin-top: 40px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.pagination-row p {
    margin: 0;
    color: #64748b;
    font-size: 14px;
}

.pagination-row p span {
    color: #0f172a;
    font-weight: 700;
}

.pagination {
    display: flex;
    gap: 8px;
}

.pagination button {
    width: 40px;
    height: 40px;
    border: 1px solid #e5e7eb;
    background: #ffffff;
    color: #475569;
    border-radius: 8px;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
    display: flex;
    align-items: center;
    justify-content: center;
}

.pagination button:hover {
    background: #f9fafb;
}

.pagination button.active {
    background: #22c55e;
    color: #ffffff;
    border-color: #22c55e;
}

.pagination button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.pagination button:disabled:hover {
    background: #ffffff;
}

.pagination .material-symbols-outlined {
    font-size: 20px;
}

@media (max-width: 1300px) {
    .field-grid {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 1000px) {
    .field-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 900px) {
    .stats-grid {
        grid-template-columns: 1fr;
    }

    .pagination-row {
        flex-direction: column;
        align-items: flex-start;
        gap: 16px;
    }
}

@media (max-width: 640px) {
    .field-grid {
        grid-template-columns: 1fr;
    }
}
</style>