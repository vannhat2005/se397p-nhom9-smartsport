<template>
    <div class="user-management-page">
        <!-- Header Section -->
        <div class="page-header">
            <div>
                <h2>Quản lý người dùng</h2>
                <p>Quản lý và phân quyền thành viên trong hệ thống SmartSport.</p>
            </div>
        </div>

        <!-- Stats Bento Grid -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon green">
                    <span class="material-symbols-outlined">group</span>
                </div>

                <div>
                    <p>Tổng người dùng</p>
                    <h3>{{ users.length }}</h3>

                    <span class="trend green-text">
                        <span class="material-symbols-outlined">trending_up</span>
                        +12% so với tháng trước
                    </span>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon blue">
                    <span class="material-symbols-outlined">person_check</span>
                </div>

                <div>
                    <p>Chủ sân hoạt động</p>
                    <h3>{{ activeOwnerCount }}</h3>

                    <span class="trend blue-text">
                        <span class="material-symbols-outlined">verified</span>
                        85% tỷ lệ xác thực
                    </span>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon indigo">
                    <span class="material-symbols-outlined">sports_tennis</span>
                </div>

                <div>
                    <p>Vận động viên mới</p>
                    <h3>{{ newClientCount }}</h3>

                    <span class="trend indigo-text">
                        Tuần này
                    </span>
                </div>
            </div>
        </div>

        <!-- Table Card -->
        <div class="table-card">
            <div class="table-header">
                <h4>Danh sách thành viên</h4>

                <div class="table-actions">
                    <button class="filter-button">
                        <span class="material-symbols-outlined">filter_list</span>
                        Lọc
                    </button>
                </div>
            </div>

            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Họ tên</th>
                            <th>Số điện thoại</th>
                            <th>Vai trò</th>
                            <th>Trạng thái</th>
                            <th>Ngày tham gia</th>
                            <th class="text-right">Hành động</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="user in displayedUsers" :key="user.id">
                            <td>
                                <div class="user-cell">
                                    <img v-if="user.avatar" class="avatar" :src="user.avatar" :alt="user.fullName" />

                                    <div v-else class="avatar-text">
                                        {{ getAvatarText(user.fullName) }}
                                    </div>

                                    <div>
                                        <p class="user-name">{{ user.fullName }}</p>
                                        <p class="user-email">{{ user.email }}</p>
                                    </div>
                                </div>
                            </td>

                            <td>{{ user.phone }}</td>

                            <td>
                                <span class="role-badge" :class="user.role">
                                    {{ getRoleText(user.role) }}
                                </span>
                            </td>

                            <td>
                                <div class="status-cell">
                                    <span class="status-dot" :class="{
                                        active: user.status === 1,
                                        offline: user.status === 0,
                                        locked: user.status === 2,
                                    }"></span>

                                    <span class="status-text" :class="{ muted: user.status === 0 }">
                                        {{ getStatusText(user.status) }}
                                    </span>
                                </div>
                            </td>

                            <td>{{ user.joinDate }}</td>

                            <td class="text-right">
                                <button class="edit-button" @click="editUser(user)">
                                    <span class="material-symbols-outlined">edit</span>
                                    Sửa
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Footer phân trang -->
            <div class="table-footer">
                <p>
                    Hiển thị {{ startIndex + 1 }} - {{ endIndex }} trên
                    {{ users.length }} người dùng
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
    name: "QuanLyNguoiDung",

    data() {
        return {
            currentPage: 1,
            perPage: 4,

            users: [
                {
                    id: 1,
                    fullName: "Nguyễn Văn An",
                    email: "an.nv@example.com",
                    phone: "0987 654 321",
                    role: "admin",
                    status: 1,
                    joinDate: "12/05/2023",
                    avatar:
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuA0ZPBIeNwDbp_zMrCK5PV75NYT1srUvm5h6lL0F2ScIzFzNrwyfMQsfD7k2Df9x_1NCFRk6HbBK3CsQ6sXfH9WTtIgn1AAe6T-Vkh9riPx-6TmWhKclKTuFmhqSWFDU5khnEgH6TXR-CP92bS-ly1Cd2LV0Pth3qOqZNnFemepfpKDIzYNVG7mxst1o49eX9iaCffNKPaHIvAXygLWgKJb8usgwlR7VCIyvrIV-NE0lcP2i4DLaE1R3fxH1dg9rtgUsY5ShvgQSqY",
                },
                {
                    id: 2,
                    fullName: "Trần Thị Hoa",
                    email: "hoa.tt@sports.vn",
                    phone: "0912 345 678",
                    role: "chu_san",
                    status: 1,
                    joinDate: "20/08/2023",
                    avatar: "",
                },
                {
                    id: 3,
                    fullName: "Lê Hoàng Nam",
                    email: "nam.lh@gmail.com",
                    phone: "0345 678 901",
                    role: "client",
                    status: 0,
                    joinDate: "15/11/2023",
                    avatar:
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuAq7PP-JEhZj4rOqivj3a334Gbb4UgvhE-3nCOeK1jwlDRfp7_slwilSKX21OWxx_6U2XO7FlO5muwGISaPIjOcmpGJwciRV-sp9Pd3YnjgteTFJfVJXZ4ByoTrkqpWCOLr8AdevzcBDxf6u1ZUCEi5ks361_L67PX70U9QBo95bMVdnHgVHY7I6DDH1SZO_64s0At5kJudwf0R_q2eHkST0QkgIYmCynEXs5H4qkCDJp9yb5VENmr23_u4PKSpKkX9GajLqR4eKbw",
                },
                {
                    id: 4,
                    fullName: "Phạm Minh Anh",
                    email: "anh.pm@outlook.com",
                    phone: "0765 432 109",
                    role: "client",
                    status: 2,
                    joinDate: "02/01/2024",
                    avatar:
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuB6aan6WQf849ReLi8UTXmXuZbrBsjC6Kd1Waw50Is6f0WXD3o9GUiBOvN9IJa-WE9dJpgGvlqMFUoxYTiduOBqgCMtRM-RbTGOlaXhiqoUkMa8za-IHYhZzmVAyYJp7hpIBeSZ0-aWVeOK8O602pq3wy3T2wAXkuE-xwfMeNEBotT5klTXEyn43f0OrORPnkf2QvV9UGgRWxw9-C8w6EKjw9KXi-gYgqfqdH7fDwVDI_NVy49-DhY93Rk74D4wwQF_QbW2n-_jqDU",
                },
                {
                    id: 5,
                    fullName: "Hoàng Anh Tuấn",
                    email: "tuanha@email.com",
                    phone: "0944 444 444",
                    role: "client",
                    status: 1,
                    joinDate: "05/02/2024",
                    avatar: "",
                },
                {
                    id: 6,
                    fullName: "Đỗ Thị Mai",
                    email: "maidt@email.com",
                    phone: "0955 555 555",
                    role: "client",
                    status: 1,
                    joinDate: "11/02/2024",
                    avatar: "",
                },
                {
                    id: 7,
                    fullName: "Võ Quốc Huy",
                    email: "huyvq@email.com",
                    phone: "0966 666 666",
                    role: "client",
                    status: 1,
                    joinDate: "18/02/2024",
                    avatar: "",
                },
                {
                    id: 8,
                    fullName: "Bùi Ngọc Linh",
                    email: "linhbn@email.com",
                    phone: "0977 777 777",
                    role: "client",
                    status: 0,
                    joinDate: "21/02/2024",
                    avatar: "",
                },
                {
                    id: 9,
                    fullName: "Ngô Thanh Bình",
                    email: "binhnt@email.com",
                    phone: "0988 888 888",
                    role: "chu_san",
                    status: 1,
                    joinDate: "02/03/2024",
                    avatar: "",
                },
                {
                    id: 10,
                    fullName: "Trần Văn Chủ",
                    email: "chusan1@email.com",
                    phone: "0911 111 111",
                    role: "chu_san",
                    status: 1,
                    joinDate: "08/03/2024",
                    avatar: "",
                },
            ],
        };
    },

    computed: {
        totalPages() {
            return Math.ceil(this.users.length / this.perPage);
        },

        startIndex() {
            return (this.currentPage - 1) * this.perPage;
        },

        endIndex() {
            const end = this.startIndex + this.displayedUsers.length;
            return end;
        },

        displayedUsers() {
            const start = (this.currentPage - 1) * this.perPage;
            const end = start + this.perPage;

            return this.users.slice(start, end);
        },

        activeOwnerCount() {
            return this.users.filter(
                (user) => user.role === "chu_san" && user.status === 1
            ).length;
        },

        newClientCount() {
            return this.users.filter((user) => user.role === "client").length;
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

        getAvatarText(name) {
            return name
                .split(" ")
                .map((item) => item[0])
                .join("")
                .slice(-2)
                .toUpperCase();
        },

        getRoleText(role) {
            if (role === "admin") {
                return "Admin";
            }

            if (role === "chu_san") {
                return "Chủ sân";
            }

            return "Vận động viên";
        },

        getStatusText(status) {
            if (status === 1) {
                return "Hoạt động";
            }

            if (status === 0) {
                return "Ngoại tuyến";
            }

            return "Đã khoá";
        },

        editUser(user) {
            console.log("Sửa người dùng:", user);
            alert("Chức năng sửa người dùng sẽ xử lý sau.");
        },
    },
};
</script>

<style scoped>
.user-management-page {
    min-height: 100vh;
    background: #f8f9fa;
    color: #191c1d;
    font-family: "Inter", sans-serif;
}

/* Header */
.page-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
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
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    padding: 24px;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
    display: flex;
    align-items: center;
    gap: 16px;
}

.stat-icon {
    width: 48px;
    height: 48px;
    border-radius: 999px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
}

.stat-icon .material-symbols-outlined {
    font-size: 32px;
}

.stat-icon.green {
    background: #f0fdf4;
    color: #16a34a;
}

.stat-icon.blue {
    background: #eff6ff;
    color: #2563eb;
}

.stat-icon.indigo {
    background: #eef2ff;
    color: #4f46e5;
}

.stat-card p {
    margin: 0;
    color: #64748b;
    font-size: 14px;
    font-weight: 600;
}

.stat-card h3 {
    margin: 2px 0 0;
    color: #191c1d;
    font-size: 24px;
    line-height: 32px;
    font-weight: 700;
}

.trend {
    display: flex;
    align-items: center;
    gap: 4px;
    margin-top: 4px;
    font-size: 12px;
    font-weight: 600;
}

.trend .material-symbols-outlined {
    font-size: 14px;
}

.green-text {
    color: #16a34a;
}

.blue-text {
    color: #2563eb;
}

.indigo-text {
    color: #4f46e5;
}

/* Table */
.table-card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
    overflow: hidden;
}

.table-header {
    padding: 16px 24px;
    border-bottom: 1px solid #f1f5f9;
    background: rgba(248, 250, 252, 0.5);
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.table-header h4 {
    margin: 0;
    font-size: 20px;
    font-weight: 600;
    color: #191c1d;
}

.filter-button {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    color: #475569;
    padding: 8px 16px;
    border-radius: 8px;
    font-size: 12px;
    font-weight: 700;
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    transition: 0.2s;
}

.filter-button:hover {
    background: #f8fafc;
}

.filter-button .material-symbols-outlined {
    font-size: 18px;
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
}

th {
    padding: 16px 24px;
    color: #64748b;
    font-size: 12px;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    border-bottom: 1px solid #f1f5f9;
    white-space: nowrap;
}

td {
    padding: 16px 24px;
    border-bottom: 1px solid #f1f5f9;
    color: #475569;
    font-size: 14px;
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

.user-cell {
    display: flex;
    align-items: center;
    gap: 12px;
}

.avatar,
.avatar-text {
    width: 40px;
    height: 40px;
    border-radius: 999px;
    flex-shrink: 0;
}

.avatar {
    object-fit: cover;
}

.avatar-text {
    background: #22c55e;
    color: #ffffff;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 800;
    font-size: 14px;
}

.user-name {
    margin: 0;
    color: #191c1d;
    font-size: 14px;
    font-weight: 600;
}

.user-email {
    margin: 2px 0 0;
    color: #64748b;
    font-size: 12px;
}

.role-badge {
    padding: 4px 12px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
}

.role-badge.admin {
    background: #eff6ff;
    color: #1d4ed8;
}

.role-badge.chu_san {
    background: #f0fdf4;
    color: #15803d;
}

.role-badge.client {
    background: #eef2ff;
    color: #4338ca;
}

.status-cell {
    display: flex;
    align-items: center;
    gap: 8px;
}

.status-dot {
    width: 8px;
    height: 8px;
    border-radius: 999px;
}

.status-dot.active {
    background: #22c55e;
}

.status-dot.offline {
    background: #cbd5e1;
}

.status-dot.locked {
    background: #ef4444;
}

.status-text {
    color: #334155;
    font-size: 12px;
    font-weight: 600;
}

.status-text.muted {
    color: #94a3b8;
}

.edit-button {
    border: none;
    background: transparent;
    color: #94a3b8;
    padding: 8px;
    border-radius: 8px;
    font-size: 12px;
    font-weight: 700;
    display: inline-flex;
    align-items: center;
    gap: 4px;
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
    justify-content: space-between;
    align-items: center;
}

.table-footer p {
    margin: 0;
    color: #64748b;
    font-size: 12px;
}

.pagination {
    display: flex;
    gap: 4px;
}

.pagination button {
    min-width: 32px;
    height: 32px;
    border: 1px solid #e2e8f0;
    background: #ffffff;
    color: #475569;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
    cursor: pointer;
    transition: 0.2s;
}

.pagination button:hover {
    background: #f8fafc;
}

.pagination button.active {
    background: #16a34a;
    color: #ffffff;
    border-color: #16a34a;
    box-shadow: 0 4px 10px rgba(22, 163, 74, 0.2);
}

.pagination button:disabled {
    opacity: 0.45;
    cursor: not-allowed;
}

.pagination button:disabled:hover {
    background: #ffffff;
}

.pagination .material-symbols-outlined {
    font-size: 18px;
}

@media (max-width: 1100px) {
    .stats-grid {
        grid-template-columns: 1fr;
    }

    .page-header {
        align-items: flex-start;
    }
}

@media (max-width: 768px) {
    .table-footer {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;
    }
}
</style>