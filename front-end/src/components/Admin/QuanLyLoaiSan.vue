<template>
    <div class="field-type-page">
        <!-- Page Header -->
        <div class="page-header">
            <div>
                <h2>Quản lý loại sân</h2>
                <p>Quản lý và cấu hình các loại hình thể thao trong hệ thống.</p>
            </div>
        </div>

        <!-- Dashboard Stats Preview -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-top">
                    <span>TỔNG SỐ LOẠI SÂN</span>

                    <div class="stat-icon blue">
                        <span class="material-symbols-outlined">sports_soccer</span>
                    </div>
                </div>

                <h3>{{ fieldTypes.length }}</h3>

                <p class="trend green-text">
                    <span class="material-symbols-outlined">trending_up</span>
                    +2 tháng này
                </p>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <span>ĐANG HOẠT ĐỘNG</span>

                    <div class="stat-icon green">
                        <span class="material-symbols-outlined">check_circle</span>
                    </div>
                </div>

                <h3>{{ activeCount }}</h3>

                <p class="trend gray-text">
                    {{ activePercent }}% tổng số
                </p>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <span>LƯỢT ĐẶT SÂN</span>

                    <div class="stat-icon orange">
                        <span class="material-symbols-outlined">book_online</span>
                    </div>
                </div>

                <h3>50</h3>

                <p class="trend green-text">
                    <span class="material-symbols-outlined">trending_up</span>
                    +12%
                </p>
            </div>

            <div class="stat-card">
                <div class="stat-top">
                    <span>DOANH THU ƯỚC TÍNH</span>

                    <div class="stat-icon purple">
                        <span class="material-symbols-outlined">payments</span>
                    </div>
                </div>

                <h3>45.2M</h3>

                <p class="trend gray-text">
                    VND / Tháng
                </p>
            </div>
        </div>

        <!-- Main Data Table Container -->
        <div class="table-card">
            <div class="table-header">
                <div class="table-title">
                    <span>Danh sách phân loại</span>

                    <div class="divider"></div>

                    <div class="filter-label">
                        <span class="material-symbols-outlined">filter_list</span>
                        Bộ lọc
                    </div>
                </div>

                <div class="table-actions">
                    <button>
                        <span class="material-symbols-outlined">download</span>
                    </button>

                    <button>
                        <span class="material-symbols-outlined">more_vert</span>
                    </button>
                </div>
            </div>

            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tên loại sân</th>
                            <th>Trạng thái</th>
                            <th class="text-right">Thao tác</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="type in displayedFieldTypes" :key="type.id">
                            <td class="type-code">{{ type.code }}</td>

                            <td>
                                <div class="type-cell">
                                    <div>
                                        <p class="type-name">{{ type.name }}</p>
                                        <p class="type-description">{{ type.description }}</p>
                                    </div>
                                </div>
                            </td>

                            <td>
                                <span class="status-badge" :class="type.status === 1 ? 'active' : 'maintenance'">
                                    {{ type.status === 1 ? "HOẠT ĐỘNG" : "BẢO TRÌ" }}
                                </span>
                            </td>

                            <td class="text-right">
                                <button class="edit-button" @click="editFieldType(type)">
                                    <span class="material-symbols-outlined">edit</span>
                                    Sửa
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Pagination Footer -->
            <div class="table-footer">
                <div>
                    Hiển thị
                    <span>{{ startIndex + 1 }} - {{ endIndex }}</span>
                    trên
                    <span>{{ fieldTypes.length }}</span>
                    kết quả
                </div>

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
    name: "QuanLyLoaiSan",

    data() {
        return {
            currentPage: 1,
            perPage: 4,

            fieldTypes: [
                {
                    id: 1,
                    code: "#FT-001",
                    name: "Bóng đá 7 người",
                    description: "Cỏ nhân tạo chuẩn FIFA",
                    status: 1,
                },
                {
                    id: 2,
                    code: "#BT-002",
                    name: "Cầu lông",
                    description: "Thảm chuyên dụng Victor",
                    status: 1,
                },
                {
                    id: 3,
                    code: "#TN-003",
                    name: "Tennis",
                    description: "Sân cứng tiêu chuẩn Úc",
                    status: 0,
                },
                {
                    id: 4,
                    code: "#PB-004",
                    name: "Pickleball",
                    description: "Xu hướng thể thao mới",
                    status: 1,
                },
                {
                    id: 5,
                    code: "#BB-005",
                    name: "Bóng rổ",
                    description: "Sân bóng rổ ngoài trời",
                    status: 1,
                },
                {
                    id: 6,
                    code: "#VB-006",
                    name: "Bóng chuyền",
                    description: "Sân bóng chuyền tiêu chuẩn",
                    status: 1,
                },
                {
                    id: 7,
                    code: "#FT-007",
                    name: "Bóng đá 5 người",
                    description: "Sân mini cỏ nhân tạo",
                    status: 1,
                },
                {
                    id: 8,
                    code: "#BD-008",
                    name: "Cầu lông đôi",
                    description: "Sân trong nhà ánh sáng tốt",
                    status: 1,
                },
                {
                    id: 9,
                    code: "#FS-009",
                    name: "Futsal",
                    description: "Sân trong nhà tiêu chuẩn",
                    status: 1,
                },
                {
                    id: 10,
                    code: "#GY-010",
                    name: "Gym",
                    description: "Không gian tập luyện đa năng",
                    status: 0,
                },
                {
                    id: 11,
                    code: "#SW-011",
                    name: "Bơi lội",
                    description: "Hồ bơi tiêu chuẩn",
                    status: 1,
                },
                {
                    id: 12,
                    code: "#TT-012",
                    name: "Bóng bàn",
                    description: "Bàn thi đấu trong nhà",
                    status: 1,
                },
            ],
        };
    },

    computed: {
        totalPages() {
            return Math.ceil(this.fieldTypes.length / this.perPage);
        },

        startIndex() {
            return (this.currentPage - 1) * this.perPage;
        },

        displayedFieldTypes() {
            const start = this.startIndex;
            const end = start + this.perPage;

            return this.fieldTypes.slice(start, end);
        },

        endIndex() {
            return this.startIndex + this.displayedFieldTypes.length;
        },

        activeCount() {
            return this.fieldTypes.filter((type) => type.status === 1).length;
        },

        activePercent() {
            return Math.round((this.activeCount / this.fieldTypes.length) * 100);
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

        editFieldType(type) {
            console.log("Sửa loại sân:", type);
            alert("Chức năng sửa loại sân sẽ xử lý sau.");
        },
    },
};
</script>

<style scoped>
.field-type-page {
    min-height: 100vh;
    background: #f8f9fa;
    color: #191c1d;
    font-family: "Inter", sans-serif;
}

/* Header */
.page-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
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
    color: #555f6f;
    font-size: 14px;
    line-height: 20px;
}

/* Stats */
.stats-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 24px;
    margin-bottom: 32px;
}

.stat-card {
    background: #ffffff;
    padding: 24px;
    border-radius: 12px;
    border: 1px solid #f1f5f9;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.stat-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 8px;
}

.stat-top span:first-child {
    color: #64748b;
    font-size: 12px;
    line-height: 16px;
    font-weight: 700;
    letter-spacing: 0.05em;
}

.stat-icon {
    padding: 8px;
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

.stat-icon.orange {
    background: #fff7ed;
    color: #ea580c;
}

.stat-icon.purple {
    background: #faf5ff;
    color: #9333ea;
}

.stat-card h3 {
    margin: 0;
    font-size: 24px;
    line-height: 32px;
    font-weight: 700;
}

.trend {
    margin: 4px 0 0;
    font-size: 12px;
    display: flex;
    align-items: center;
    gap: 4px;
}

.trend .material-symbols-outlined {
    font-size: 14px;
}

.green-text {
    color: #16a34a;
}

.gray-text {
    color: #94a3b8;
}

/* Table */
.table-card {
    background: #ffffff;
    border-radius: 12px;
    border: 1px solid #f1f5f9;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
    overflow: hidden;
}

.table-header {
    padding: 16px 24px;
    border-bottom: 1px solid #f1f5f9;
    background: rgba(248, 250, 252, 0.5);
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.table-title {
    display: flex;
    align-items: center;
    gap: 16px;
}

.table-title>span {
    color: #191c1d;
    font-size: 14px;
    font-weight: 600;
}

.divider {
    width: 1px;
    height: 16px;
    background: #cbd5e1;
}

.filter-label {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #64748b;
    font-size: 14px;
}

.filter-label .material-symbols-outlined {
    font-size: 18px;
}

.table-actions {
    display: flex;
    align-items: center;
    gap: 8px;
}

.table-actions button {
    border: none;
    background: transparent;
    color: #94a3b8;
    padding: 8px;
    cursor: pointer;
    transition: 0.2s;
}

.table-actions button:hover {
    color: #006e2f;
}

.table-wrapper {
    overflow-x: auto;
}

table {
    width: 100%;
    border-collapse: collapse;
    text-align: left;
}

thead tr {
    background: rgba(248, 250, 252, 0.5);
    border-bottom: 1px solid #f1f5f9;
}

th {
    padding: 16px 24px;
    color: #64748b;
    font-size: 12px;
    line-height: 16px;
    font-weight: 700;
    letter-spacing: 0.05em;
    white-space: nowrap;
}

td {
    padding: 16px 24px;
    border-bottom: 1px solid #f8fafc;
    white-space: nowrap;
}

tbody tr {
    transition: 0.2s;
}

tbody tr:hover {
    background: rgba(248, 250, 252, 0.8);
}

.text-right {
    text-align: right;
}

.type-code {
    color: #0f172a;
    font-size: 14px;
    font-weight: 600;
}

.type-cell {
    display: flex;
    align-items: center;
}

.type-name {
    margin: 0;
    color: #191c1d;
    font-size: 14px;
    line-height: 20px;
    font-weight: 600;
}

.type-description {
    margin: 2px 0 0;
    color: #64748b;
    font-size: 12px;
}

.status-badge {
    padding: 4px 8px;
    border-radius: 999px;
    font-size: 11px;
    font-weight: 800;
}

.status-badge.active {
    background: #dcfce7;
    color: #15803d;
}

.status-badge.maintenance {
    background: #f1f5f9;
    color: #475569;
}

.edit-button {
    border: none;
    background: transparent;
    color: #94a3b8;
    padding: 8px;
    border-radius: 8px;
    display: inline-flex;
    align-items: center;
    gap: 4px;
    font-size: 12px;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
}

.edit-button:hover {
    color: #006e2f;
    background: rgba(0, 110, 47, 0.1);
}

.edit-button .material-symbols-outlined {
    font-size: 18px;
}

/* Footer */
.table-footer {
    padding: 16px 24px;
    border-top: 1px solid #f1f5f9;
    background: #ffffff;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.table-footer div:first-child {
    color: #64748b;
    font-size: 14px;
}

.table-footer span {
    color: #191c1d;
    font-weight: 600;
}

.pagination {
    display: flex;
    align-items: center;
    gap: 8px;
}

.pagination button {
    min-width: 32px;
    height: 32px;
    border: none;
    background: transparent;
    color: #475569;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.2s;
    display: flex;
    align-items: center;
    justify-content: center;
}

.pagination button:hover {
    background: #f1f5f9;
}

.pagination button.active {
    background: #22c55e;
    color: #004b1e;
}

.pagination button:disabled {
    border: 1px solid #e2e8f0;
    color: #94a3b8;
    cursor: not-allowed;
    opacity: 0.5;
}

.pagination button:disabled:hover {
    background: transparent;
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

    .table-header,
    .table-footer {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;
    }
}
</style>