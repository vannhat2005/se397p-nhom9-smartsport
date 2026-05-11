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
                <h3>{{ totalPending }}</h3>
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
                        {{ field.type }}
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
                            <strong>{{ getOwnerName(field.ownerId) }}</strong>
                        </div>

                        <div>
                            <span>Ngày gửi</span>
                            <strong>{{ field.createdDate }}</strong>
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
        </div>

        <!-- Pagination -->
        <div class="pagination-row">
            <p>
                Hiển thị
                <span>{{ displayedFields.length }}</span>
                trên
                <span>{{ totalPending }}</span>
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
export default {
    name: "DuyetSan",

    data() {
        return {
            currentPage: 1,
            perPage: 4,

            approvedCount: 142,
            rejectedCount: 18,

            // Đồng bộ với danh sách chủ sân trong Quản lý người dùng
            owners: [
                {
                    id: 2,
                    fullName: "Trần Thị Hoa",
                    email: "hoa.tt@sports.vn",
                    phone: "0912 345 678",
                    role: "chu_san",
                },
                {
                    id: 9,
                    fullName: "Ngô Thanh Bình",
                    email: "binhnt@email.com",
                    phone: "0988 888 888",
                    role: "chu_san",
                },
                {
                    id: 10,
                    fullName: "Trần Văn Chủ",
                    email: "chusan1@email.com",
                    phone: "0911 111 111",
                    role: "chu_san",
                },
            ],

            // Đây là sân mới đăng ký, CHƯA DUYỆT
            // Không lấy 15 sân đang hiện ở Client
            pendingFields: [
                {
                    id: 1,
                    ownerId: 2,
                    name: "Sân bóng đá Hoa Sen Arena",
                    type: "Bóng đá",
                    address: "45 Nguyễn Trãi, Thanh Xuân, Hà Nội",
                    createdDate: "15/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1459865264687-595d652de67e?q=80&w=1200",
                },
                {
                    id: 2,
                    ownerId: 2,
                    name: "Sân bóng rổ Sunrise Court",
                    type: "Bóng rổ",
                    address: "88 Hoàng Quốc Việt, Cầu Giấy, Hà Nội",
                    createdDate: "14/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1546519638-68e109498ffc?q=80&w=1200",
                },
                {
                    id: 3,
                    ownerId: 2,
                    name: "Sân cầu lông Green Shuttle",
                    type: "Cầu lông",
                    address: "12 Lê Văn Lương, Thanh Xuân, Hà Nội",
                    createdDate: "13/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
                },
                {
                    id: 4,
                    ownerId: 2,
                    name: "Sân bóng chuyền Sao Mai",
                    type: "Bóng chuyền",
                    address: "26 Trường Chinh, Đống Đa, Hà Nội",
                    createdDate: "12/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?q=80&w=1200",
                },
                {
                    id: 5,
                    ownerId: 9,
                    name: "Sân bóng đá Hải Phòng Sport",
                    type: "Bóng đá",
                    address: "55 Lạch Tray, Ngô Quyền, Hải Phòng",
                    createdDate: "11/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1522778119026-d647f0596c20?q=80&w=1200",
                },
                {
                    id: 6,
                    ownerId: 9,
                    name: "Sân bóng rổ River Side",
                    type: "Bóng rổ",
                    address: "18 Bạch Đằng, Hồng Bàng, Hải Phòng",
                    createdDate: "10/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1505666287802-931dc83a7fe4?q=80&w=1200",
                },
                {
                    id: 7,
                    ownerId: 9,
                    name: "Sân cầu lông Hải Âu",
                    type: "Cầu lông",
                    address: "72 Văn Cao, Ngô Quyền, Hải Phòng",
                    createdDate: "09/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
                },
                {
                    id: 8,
                    ownerId: 9,
                    name: "Sân bóng chuyền Biển Đông",
                    type: "Bóng chuyền",
                    address: "30 Lê Hồng Phong, Hải Phòng",
                    createdDate: "08/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1592656094267-764a45160876?q=80&w=1200",
                },
                {
                    id: 9,
                    ownerId: 10,
                    name: "Sân bóng đá Nam Sài Gòn",
                    type: "Bóng đá",
                    address: "101 Nguyễn Hữu Thọ, Quận 7, TP.HCM",
                    createdDate: "07/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1518604666860-9ed391f76460?q=80&w=1200",
                },
                {
                    id: 10,
                    ownerId: 10,
                    name: "Sân bóng rổ Galaxy Arena",
                    type: "Bóng rổ",
                    address: "22 Nguyễn Văn Linh, Quận 7, TP.HCM",
                    createdDate: "06/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1519861531473-9200262188bf?q=80&w=1200",
                },
                {
                    id: 11,
                    ownerId: 10,
                    name: "Sân cầu lông Tân Phú Center",
                    type: "Cầu lông",
                    address: "39 Lũy Bán Bích, Tân Phú, TP.HCM",
                    createdDate: "05/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1613918431703-aa50889e3be9?q=80&w=1200",
                },
                {
                    id: 12,
                    ownerId: 10,
                    name: "Sân bóng chuyền Phương Nam",
                    type: "Bóng chuyền",
                    address: "64 Phan Văn Trị, Bình Thạnh, TP.HCM",
                    createdDate: "04/10/2023",
                    image:
                        "https://images.unsplash.com/photo-1609710228159-0fa9bd7c0827?q=80&w=1200",
                },
            ],
        };
    },

    computed: {
        totalPending() {
            return this.pendingFields.length;
        },

        totalPages() {
            return Math.ceil(this.pendingFields.length / this.perPage);
        },

        startIndex() {
            return (this.currentPage - 1) * this.perPage;
        },

        displayedFields() {
            const start = this.startIndex;
            const end = start + this.perPage;

            return this.pendingFields.slice(start, end);
        },
    },

    methods: {
        getOwnerName(ownerId) {
            const owner = this.owners.find((item) => item.id === ownerId);

            return owner ? owner.fullName : "Không rõ chủ sân";
        },

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

        approveField(field) {
            alert(
                `Đã phê duyệt sân "${field.name}" của chủ sân ${this.getOwnerName(
                    field.ownerId
                )}`
            );
        },

        rejectField(field) {
            alert(
                `Đã từ chối sân "${field.name}" của chủ sân ${this.getOwnerName(
                    field.ownerId
                )}`
            );
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