<template>
    <div class="user-management-page">
        <!-- Header Section -->
        <div class="page-header">
            <div>
                <h2>Quản lý người dùng</h2>
                <p>Quản lý và phân quyền thành viên trong hệ thống SmartSport.</p>
            </div>

            <button class="add-button" data-bs-toggle="modal" data-bs-target="#themNguoiDungModal">
                <span class="material-symbols-outlined">add</span>
                Thêm người dùng
            </button>
        </div>

        <!-- Stats Bento Grid -->
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon green">
                    <span class="material-symbols-outlined">group</span>
                </div>

                <div>
                    <p>Tổng người dùng</p>
                    <h3>{{ totalElements }}</h3>

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
                    <h3>{{ 10 }}</h3>

                    <span class="trend blue-text">
                        <span class="material-symbols-outlined">verified</span>
                        Đang hoạt động
                    </span>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon indigo">
                    <span class="material-symbols-outlined">sports_tennis</span>
                </div>

                <div>
                    <p>Khách hàng</p>
                    <h3>{{ 10 }}</h3>

                    <span class="trend indigo-text">
                        Trong trang hiện tại
                    </span>
                </div>
            </div>
        </div>

        <!-- Table Card -->
        <div class="table-card">
            <div class="table-header">
                <h4>Danh sách thành viên</h4>
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
                                    <img v-if="user.avatar" class="avatar" :src="user.avatar" :alt="user.full_name" />

                                    <div v-else class="avatar-text">
                                        {{ getAvatarText(user.full_name) }}
                                    </div>

                                    <div>
                                        <p class="user-name">{{ user.full_name }}</p>
                                        <p class="user-email">{{ user.email }}</p>
                                    </div>
                                </div>
                            </td>

                            <td>{{ user.phone }}</td>

                            <td>
                                <span class="role-badge" :class="getRoleClass(user.role_id)">
                                    {{ getRoleText(user.role_id) }}
                                </span>
                            </td>

                            <td>
                                <div class="status-cell">
                                    <span class="status-dot" :class="{
                                        active: user.status === 1,
                                        locked: user.status === 0,
                                    }"></span>

                                    <span class="status-text" :class="{ muted: user.status === 0 }">
                                        {{ getStatusText(user.status) }}
                                    </span>
                                </div>
                            </td>

                            <td>{{ formatDate(user.created_at) }}</td>
                            <td class="text-right">
                                <button class="edit-button" data-bs-toggle="modal"
                                    
                                    @click="editUser()">
                                    <span class="material-symbols-outlined">edit</span>
                                    Sửa
                                </button>
                            </td>
                            <!-- <td class="text-right">
                                <button class="edit-button"  data-bs-toggle="modal"
                                    data-bs-target="#capNhatNguoiDungModal"
                                    @click="Object.assign(updateUser, { ...user, status: Number(user.status), role_id: Number(user.role_id) })">
                                    <span class="material-symbols-outlined">edit</span>
                                    Sửa
                                </button>
                            </td> -->
                        </tr>

                        <tr v-if="displayedUsers.length === 0">
                            <td colspan="6" class="empty-row">
                                Không có người dùng nào.
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Footer phân trang -->
            <div class="table-footer">
                <p>
                    Hiển thị
                    {{ totalElements === 0 ? 0 : startIndex + 1 }} - {{ endIndex }}
                    trên {{ totalElements }} người dùng
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

        <!-- Modal thêm người dùng -->
        <div class="modal fade" id="themNguoiDungModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5">
                            Thêm mới người dùng
                        </h1>

                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Họ tên</label>
                            <input v-model="createUser.full_name" type="text" class="form-control"
                                placeholder="Nhập họ tên" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input v-model="createUser.email" type="email" class="form-control"
                                placeholder="Nhập email" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input v-model="createUser.phone" type="text" class="form-control"
                                placeholder="Nhập số điện thoại" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mật khẩu</label>
                            <input v-model="createUser.password" type="password" class="form-control"
                                placeholder="Nhập mật khẩu" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Địa chỉ</label>
                            <input v-model="createUser.address" type="text" class="form-control"
                                placeholder="Nhập địa chỉ" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Vai trò</label>
                            <select v-model="createUser.role_id" class="form-select">
                                <option value="">Chọn vai trò</option>
                                <option :value="1">Admin</option>
                                <option :value="2">Chủ sân</option>
                                <option :value="3">Khách hàng</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <select v-model="createUser.status" class="form-select">
                                <option value="">Chọn trạng thái</option>
                                <option :value="1">Hoạt động</option>
                                <option :value="0">Bị khóa</option>
                            </select>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            Hủy
                        </button>

                        <button type="button" class="btn btn-primary" @click="addUser" data-bs-dismiss="modal">
                            Lưu
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal cập nhật người dùng -->
        <!-- <div class="modal fade" id="capNhatNguoiDungModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5">
                            Cập nhật người dùng
                        </h1>

                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Họ tên</label>
                            <input v-model="updateUser.full_name" type="text" class="form-control"
                                placeholder="Nhập họ tên" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input v-model="updateUser.email" type="email" class="form-control"
                                placeholder="Nhập email" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Số điện thoại</label>
                            <input v-model="updateUser.phone" type="text" class="form-control"
                                placeholder="Nhập số điện thoại" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Địa chỉ</label>
                            <input v-model="updateUser.address" type="text" class="form-control"
                                placeholder="Nhập địa chỉ" />
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Vai trò</label>
                            <select v-model="updateUser.role_id" class="form-select">
                                <option value="">Chọn vai trò</option>
                                <option :value="1">Admin</option>
                                <option :value="2">Chủ sân</option>
                                <option :value="3">Khách hàng</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <select v-model="updateUser.status" class="form-select">
                                <option value="">Chọn trạng thái</option>
                                <option :value="1">Hoạt động</option>
                                <option :value="0">Bị khóa</option>
                            </select>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            Hủy
                        </button>

                        <button type="button" class="btn btn-primary" @click="editUser" data-bs-dismiss="modal">
                            Lưu
                        </button>
                    </div>
                </div>
            </div>
        </div> --> -->
    </div>
</template>

<script>
import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export default {
    name: "QuanLyNguoiDung",

    data() {
        return {
            currentPage: 1,
            pageSize: 4,
            totalPages: 1,
            totalElements: 0,

            users: [],

            createUser: {
                full_name: "",
                email: "",
                phone: "",
                password: "",
                avatar: "",
                address: "",
                role_id: "",
                status: "",
            },

            updateUser: {
                id: "",
                full_name: "",
                email: "",
                phone: "",
                avatar: "",
                address: "",
                role_id: "",
                status: "",
            },
        };
    },

    computed: {
        startIndex() {
            return (this.currentPage - 1) * this.pageSize;
        },

        displayedUsers() {
            return this.users;
        },

        endIndex() {
            return this.startIndex + this.displayedUsers.length;
        },

        activeOwnerCount() {
            return this.users.filter(
                (user) => Number(user.role_id) === 2 && Number(user.status) === 1
            ).length;
        },

        clientCount() {
            return this.users.filter((user) => Number(user.role_id) === 3).length;
        },
    },

    mounted() {
        this.loadUsers();
    },

    methods: {
        loadUsers() {
            axios
                .get(`${API_BASE_URL}/api/users`, {
                    params: {
                        page: this.currentPage,
                        size: this.pageSize,
                    },
                })
                .then((response) => {
                    const pageData = response.data.data;

                    this.users = pageData.data;

                    this.currentPage = pageData.currentPage;
                    this.pageSize = pageData.pageSize;
                    this.totalPages = pageData.totalPages;
                    this.totalElements = pageData.totalElements;
                })
                .catch((error) => {
                    console.error("Lỗi khi tải danh sách người dùng:", error);
                    alert("Không thể tải danh sách người dùng. Vui lòng thử lại sau.");
                });
        },

        addUser() {
            axios
                .post(`${API_BASE_URL}/api/users`, this.createUser)
                .then((response) => {
                    this.$toast.success(response.data.message);

                    this.createUser = {
                        full_name: "",
                        email: "",
                        phone: "",
                        password: "",
                        avatar: "",
                        address: "",
                        role_id: "",
                        status: "",
                    };

                    this.loadUsers();
                })
                .catch((error) => {
                    console.error("Lỗi thêm người dùng:", error);
                    this.$toast.error("Thêm người dùng thất bại. Vui lòng thử lại.");
                });
        },

        editUser() {
            alert("Phân quyền và khóa/mở tài khoản");
            // const payload = {
            //     id: this.updateUser.id,
            //     role_id: Number(this.updateUser.role_id),
            //     full_name: this.updateUser.full_name,
            //     email: this.updateUser.email,
            //     phone: this.updateUser.phone,
            //     avatar: this.updateUser.avatar,
            //     address: this.updateUser.address,
            //     status: Number(this.updateUser.status),
            // };

            // axios
            //     .put(`${API_BASE_URL}/api/users/${this.updateUser.id}`, payload)
            //     .then((response) => {
            //         this.$toast.success(response.data.message || "Cập nhật người dùng thành công.");
            //         this.loadUsers();
            //     })
            //     .catch((error) => {
            //         console.error("Lỗi cập nhật người dùng:", error);
            //         this.$toast.error("Cập nhật người dùng thất bại. Vui lòng thử lại.");
            //     });
        },

        goToPage(page) {
            if (page < 1 || page > this.totalPages) {
                return;
            }

            this.currentPage = page;
            this.loadUsers();
        },

        prevPage() {
            this.goToPage(this.currentPage - 1);
        },

        nextPage() {
            this.goToPage(this.currentPage + 1);
        },

        getAvatarText(name) {
            if (!name) {
                return "ND";
            }

            return name
                .split(" ")
                .map((item) => item[0])
                .join("")
                .slice(-2)
                .toUpperCase();
        },

        getRoleClass(roleId) {
            if (Number(roleId) === 1) {
                return "admin";
            }

            if (Number(roleId) === 2) {
                return "chu_san";
            }

            return "client";
        },

        getRoleText(roleId) {
            if (Number(roleId) === 1) {
                return "Admin";
            }

            if (Number(roleId) === 2) {
                return "Chủ sân";
            }

            return "Khách hàng";
        },

        getStatusText(status) {
            return Number(status) === 1 ? "Hoạt động" : "Bị khóa";
        },

        formatDate(dateString) {
            if (!dateString) {
                return "";
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

.add-button {
    border: none;
    background: #22c55e;
    color: #004b1e;
    padding: 10px 16px;
    border-radius: 12px;
    font-size: 14px;
    font-weight: 700;
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    transition: 0.2s;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.add-button:hover {
    opacity: 0.9;
    transform: translateY(-1px);
}

.add-button .material-symbols-outlined {
    font-size: 20px;
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

.empty-row {
    text-align: center;
    color: #94a3b8;
    font-weight: 600;
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
    .page-header {
        flex-direction: column;
        gap: 16px;
        align-items: flex-start;
    }

    .table-footer {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;
    }
}
</style>