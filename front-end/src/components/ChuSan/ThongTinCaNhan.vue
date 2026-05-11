<template>
    <div class="ho-so-page">
        <div class="profile-container">
            <!-- Profile Header -->
            <section class="profile-header-card">
                <div class="avatar-wrapper">
                    <div class="avatar-box">
                        <img class="profile-avatar" :src="user.avatar" :alt="user.fullName" />
                    </div>

                    <button class="camera-button" @click="changeAvatar">
                        <span class="material-symbols-outlined">photo_camera</span>
                    </button>
                </div>

                <div class="profile-summary">
                    <h2>{{ user.fullName }}</h2>
                    <p>Quản trị viên cơ sở • Thành viên SmartSport từ {{ user.joinYear }}</p>
                </div>
            </section>

            <div class="content-grid">
                <!-- Cột trái -->
                <div class="main-column">
                    <!-- Thông tin cá nhân -->
                    <section class="card">
                        <div class="card-title">
                            <span class="material-symbols-outlined">badge</span>
                            <h3>Thông tin cá nhân</h3>
                        </div>

                        <form class="form-grid">
                            <div class="form-group">
                                <label>Họ và tên</label>
                                <input v-model="user.fullName" type="text" />
                            </div>

                            <div class="form-group">
                                <label>Email</label>
                                <input v-model="user.email" type="email" />
                            </div>

                            <div class="form-group">
                                <label>Số điện thoại</label>
                                <input v-model="user.phone" type="tel" />
                            </div>

                            <div class="form-group">
                                <label>Khu vực quản lý</label>
                                <select v-model="user.address">
                                    <option>TP. Hồ Chí Minh, Việt Nam</option>
                                    <option>Hà Nội, Việt Nam</option>
                                    <option>Đà Nẵng, Việt Nam</option>
                                    <option>Hải Phòng, Việt Nam</option>
                                </select>
                            </div>
                        </form>
                    </section>

                    <!-- Tài khoản ngân hàng -->
                    <section class="card">
                        <div class="card-title">
                            <span class="material-symbols-outlined">account_balance</span>
                            <h3>Chi tiết tài khoản ngân hàng</h3>
                        </div>

                        <div class="bank-grid">
                            <div class="form-group full">
                                <label>Tên ngân hàng</label>
                                <input v-model="bank.bankName" type="text" />
                            </div>

                            <div class="form-group">
                                <label>Số tài khoản</label>
                                <input v-model="bank.accountNumber" type="text" />
                            </div>

                            <div class="form-group">
                                <label>Chủ tài khoản</label>
                                <input v-model="bank.accountHolder" type="text" />
                            </div>
                        </div>
                    </section>
                </div>

                <!-- Cột phải -->
                <div class="side-column">
                    <!-- Bảo mật -->
                    <section class="card">
                        <div class="card-title">
                            <span class="material-symbols-outlined">lock</span>
                            <h3>Bảo mật</h3>
                        </div>

                        <form class="security-form">
                            <div class="form-group">
                                <label>Mật khẩu hiện tại</label>
                                <input v-model="passwordForm.currentPassword" type="password" placeholder="••••••••" />
                            </div>

                            <div class="form-group">
                                <label>Mật khẩu mới</label>
                                <input v-model="passwordForm.newPassword" type="password" placeholder="••••••••" />
                            </div>

                            <div class="form-group">
                                <label>Xác nhận mật khẩu</label>
                                <input v-model="passwordForm.confirmPassword" type="password" placeholder="••••••••" />
                            </div>

                            <button type="button" class="change-password-button" @click="changePassword">
                                Đổi mật khẩu
                            </button>
                        </form>
                    </section>

                    <!-- Hỗ trợ -->
                    <section class="support-card">
                        <div class="support-content">
                            <h4>Cần hỗ trợ?</h4>
                            <p>
                                Đội ngũ hỗ trợ luôn sẵn sàng 24/7 cho các quản lý sân thể thao.
                            </p>

                            <button class="support-button">
                                <span class="material-symbols-outlined">support_agent</span>
                                Liên hệ hỗ trợ
                            </button>
                        </div>

                        <span class="material-symbols-outlined support-bg-icon">
                            contact_support
                        </span>
                    </section>
                </div>
            </div>

            <!-- Action buttons -->
            <div class="action-row">
                <button class="cancel-button" @click="resetForm">
                    Hủy thay đổi
                </button>

                <button class="save-button" @click="updateProfile">
                    Cập nhật thông tin
                </button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "HoSoChuSan",

    data() {
        return {
            user: {
                id: 2,
                fullName: "Nguyễn Văn An",
                email: "an.nguyen@smartsport.vn",
                phone: "+84 901 234 567",
                address: "TP. Hồ Chí Minh, Việt Nam",
                joinYear: "2023",
                avatar:
                    "https://lh3.googleusercontent.com/aida-public/AB6AXuD7Rs6e07s3S1QqMwCPQJaD2QETpQM_0MhqMiRYUrbIiAU8c_C3ud04sj4NJobdnk-H_gDUOrMywDfK18sXiqpQRRCAjM9cvqoMnOkSatvJaLzTne1RfEHe_kREp2G2mQFjJcOhoO0G4cRFHD32wGHaahb-z5lrWgCfIyTAUe-okEZEcqKYyq0zpHXLE02zzqXJc7qJK5uUylHrDr_9oAqvmfXHQenVVfxNf-UFZi0up3r-PCth3n2ohWQIqZh7UP3zgCQmi725q1A",
            },

            bank: {
                bankName: "Vietcombank (VCB)",
                accountNumber: "0071 0001 2345 6789",
                accountHolder: "NGUYEN VAN AN",
            },

            originalUser: null,
            originalBank: null,

            passwordForm: {
                currentPassword: "",
                newPassword: "",
                confirmPassword: "",
            },
        };
    },

    created() {
        this.originalUser = { ...this.user };
        this.originalBank = { ...this.bank };
    },

    methods: {
        changeAvatar() {
            alert("Chức năng đổi ảnh đại diện sẽ xử lý sau.");
        },

        updateProfile() {
            const payload = {
                user_id: this.user.id,
                full_name: this.user.fullName,
                email: this.user.email,
                phone: this.user.phone,
                address: this.user.address,
                bank_name: this.bank.bankName,
                account_number: this.bank.accountNumber,
                account_holder: this.bank.accountHolder,
            };

            console.log("Cập nhật hồ sơ chủ sân:", payload);
            alert("Đã cập nhật thông tin. Kiểm tra console để xem dữ liệu.");
        },

        changePassword() {
            if (!this.passwordForm.currentPassword) {
                alert("Vui lòng nhập mật khẩu hiện tại.");
                return;
            }

            if (!this.passwordForm.newPassword) {
                alert("Vui lòng nhập mật khẩu mới.");
                return;
            }

            if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
                alert("Mật khẩu xác nhận không khớp.");
                return;
            }

            const payload = {
                current_password: this.passwordForm.currentPassword,
                new_password: this.passwordForm.newPassword,
            };

            console.log("Đổi mật khẩu:", payload);
            alert("Đã gửi yêu cầu đổi mật khẩu. Kiểm tra console để xem dữ liệu.");

            this.passwordForm = {
                currentPassword: "",
                newPassword: "",
                confirmPassword: "",
            };
        },

        resetForm() {
            this.user = { ...this.originalUser };
            this.bank = { ...this.originalBank };

            this.passwordForm = {
                currentPassword: "",
                newPassword: "",
                confirmPassword: "",
            };
        },
    },
};
</script>

<style scoped>
.ho-so-page {
    min-height: 100vh;
    background: #f8f9ff;
    color: #0b1c30;
    font-family: "Inter", sans-serif;
}

.profile-container {
    max-width: 1024px;
    margin: 0 auto;
}

/* Profile Header */
.profile-header-card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    padding: 24px;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
    display: flex;
    align-items: center;
    gap: 24px;
    margin-bottom: 32px;
}

.avatar-wrapper {
    position: relative;
    flex-shrink: 0;
}

.avatar-box {
    width: 128px;
    height: 128px;
    border-radius: 999px;
    overflow: hidden;
    border: 4px solid #ffffff;
    box-shadow: 0 4px 12px rgba(15, 23, 42, 0.16);
}

.profile-avatar {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.camera-button {
    position: absolute;
    right: 0;
    bottom: 0;
    border: none;
    background: #22c55e;
    color: #ffffff;
    width: 36px;
    height: 36px;
    border-radius: 999px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 8px 18px rgba(34, 197, 94, 0.28);
    transition: 0.2s;
}

.camera-button:hover {
    transform: scale(1.05);
}

.camera-button .material-symbols-outlined {
    font-size: 18px;
}

.profile-summary h2 {
    margin: 0;
    color: #0f172a;
    font-size: 30px;
    line-height: 38px;
    font-weight: 700;
    letter-spacing: -0.02em;
}

.profile-summary p {
    margin: 4px 0 0;
    color: #64748b;
    font-size: 14px;
    font-weight: 600;
}

/* Layout */
.content-grid {
    display: grid;
    grid-template-columns: 2fr 1fr;
    gap: 32px;
}

.main-column,
.side-column {
    display: flex;
    flex-direction: column;
    gap: 32px;
}

/* Card */
.card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    padding: 24px;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
}

.card-title {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 24px;
}

.card-title .material-symbols-outlined {
    color: #006e2f;
}

.card-title h3 {
    margin: 0;
    color: #0b1c30;
    font-size: 20px;
    line-height: 28px;
    font-weight: 600;
}

/* Form */
.form-grid,
.bank-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
}

.security-form {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.form-group.full {
    grid-column: span 2;
}

.form-group label {
    color: #64748b;
    font-size: 12px;
    line-height: 16px;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.04em;
}

.form-group input,
.form-group select {
    width: 100%;
    border: 1px solid #e2e8f0;
    background: #f8fafc;
    color: #0f172a;
    border-radius: 8px;
    padding: 10px 14px;
    font-size: 14px;
    font-family: inherit;
    outline: none;
    transition: 0.2s;
}

.form-group input:focus,
.form-group select:focus {
    border-color: #22c55e;
    box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.18);
}

/* Buttons */
.change-password-button {
    width: 100%;
    border: 1px solid #006e2f;
    background: #ffffff;
    color: #006e2f;
    border-radius: 8px;
    padding: 10px 16px;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
}

.change-password-button:hover {
    background: #f0fdf4;
}

.action-row {
    margin-top: 32px;
    padding-top: 32px;
    border-top: 1px solid #e2e8f0;
    display: flex;
    justify-content: flex-end;
    gap: 16px;
}

.cancel-button,
.save-button {
    border-radius: 8px;
    padding: 12px 32px;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
    transition: 0.2s;
}

.cancel-button {
    border: none;
    background: transparent;
    color: #64748b;
}

.cancel-button:hover {
    background: #f1f5f9;
}

.save-button {
    border: none;
    background: #22c55e;
    color: #ffffff;
    box-shadow: 0 8px 18px rgba(34, 197, 94, 0.22);
}

.save-button:hover {
    background: #006e2f;
}

.cancel-button:active,
.save-button:active,
.change-password-button:active {
    transform: scale(0.96);
}

/* Support */
.support-card {
    position: relative;
    overflow: hidden;
    background: #0f172a;
    border-radius: 12px;
    padding: 24px;
    color: #ffffff;
    box-shadow: 0 18px 36px rgba(15, 23, 42, 0.22);
}

.support-content {
    position: relative;
    z-index: 2;
}

.support-card h4 {
    margin: 0 0 8px;
    font-size: 20px;
    line-height: 28px;
    font-weight: 600;
}

.support-card p {
    margin: 0 0 16px;
    color: #94a3b8;
    font-size: 14px;
    line-height: 20px;
}

.support-button {
    border: none;
    background: transparent;
    color: #4ade80;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 0;
}

.support-button:hover {
    color: #86efac;
}

.support-bg-icon {
    position: absolute;
    right: -16px;
    bottom: -20px;
    font-size: 120px;
    color: rgba(255, 255, 255, 0.08);
    pointer-events: none;
}

/* Responsive */
@media (max-width: 1100px) {
    .content-grid {
        grid-template-columns: 1fr;
    }
}

@media (max-width: 768px) {
    .profile-header-card {
        flex-direction: column;
        text-align: center;
    }

    .form-grid,
    .bank-grid {
        grid-template-columns: 1fr;
    }

    .form-group.full {
        grid-column: span 1;
    }

    .action-row {
        flex-direction: column;
    }

    .cancel-button,
    .save-button {
        width: 100%;
    }
}
</style>