<template>
  <div class="thong-tin-ca-nhan-page">
    <div class="page-header">
      <h1>Thông tin cá nhân</h1>
      <p>Quản lý thông tin tài khoản và bảo mật của bạn</p>
    </div>

    <div class="profile-layout">
      <!-- Cột trái -->
      <aside class="left-column">
        <section class="profile-card">
          <div class="avatar-wrapper">
            <img
              class="avatar"
              :src="user.avatar"
              :alt="user.fullName"
            />

            <button class="edit-avatar-button">
              <span class="material-symbols-outlined">edit</span>
            </button>
          </div>

          <h2>{{ user.fullName }}</h2>

          <div class="role-badge">
            Khách hàng
          </div>

          <p class="join-date">
            Ngày tham gia: {{ user.joinDate }}
          </p>

          <button class="change-avatar-button">
            Đổi ảnh đại diện
          </button>
        </section>
      </aside>

      <!-- Cột phải -->
      <main class="right-column">
        <!-- Thông tin cơ bản -->
        <section class="form-card">
          <div class="section-title">
            <span class="material-symbols-outlined">person</span>
            <h3>Thông tin cơ bản</h3>
          </div>

          <form class="info-form">
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
              <label>Ngày sinh</label>
              <input v-model="user.birthday" type="date" />
            </div>

            <div class="form-group full">
              <label>Địa chỉ</label>
              <input v-model="user.address" type="text" />
            </div>
          </form>
        </section>

        <!-- Bảo mật -->
        <section class="form-card">
          <div class="section-title">
            <span class="material-symbols-outlined">lock_reset</span>
            <h3>Bảo mật tài khoản</h3>
          </div>

          <form class="security-form">
            <div class="form-group">
              <label>Mật khẩu hiện tại</label>
              <input
                v-model="passwordForm.currentPassword"
                type="password"
                placeholder="••••••••"
              />
            </div>

            <div class="form-group empty"></div>

            <div class="form-group">
              <label>Mật khẩu mới</label>
              <input
                v-model="passwordForm.newPassword"
                type="password"
                placeholder="••••••••"
              />
            </div>

            <div class="form-group">
              <label>Xác nhận mật khẩu mới</label>
              <input
                v-model="passwordForm.confirmPassword"
                type="password"
                placeholder="••••••••"
              />
            </div>
          </form>

          <div class="change-password-row">
            <button class="outline-primary-button" @click="changePassword">
              Đổi mật khẩu
            </button>
          </div>
        </section>

        <!-- Nút cuối -->
        <div class="action-row">
          <button class="cancel-button" @click="resetForm">
            Hủy thay đổi
          </button>

          <button class="save-button" @click="updateProfile">
            Cập nhật thông tin
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  name: "ThongTinCaNhan",

  data() {
    return {
      user: {
        id: 1,
        fullName: "Nguyễn Văn A",
        email: "nguyenvana@email.com",
        phone: "0901234567",
        birthday: "1995-05-15",
        address: "123 Đường Thể Thao, Quận 1, TP. HCM",
        role: "Khách hàng",
        joinDate: "20/10/2023",
        avatar:
          "https://lh3.googleusercontent.com/aida-public/AB6AXuCDs_kMoxk_7sPMKXfKD2yXmY4EiT90CblBULorUO0AjDmZO3uuPyp24cKoGlmBsYTnXLgf8Yr7U_IO6etDZBeOQ8MPfx4xh1UBR4XTwPEju76cQMv79hxlKdCC4vWS762GQBw5iDpixCj9GMUgkTvwBsIzcBMxo3lvjyRPeuT4xpGgX8LWgaNxxhDq9kC9SiWH-2tlK95y1eKuqTxYdlB1T76bjYq7Ft4pKVOqM15w8-AAj2Y5VEZ0jsoBfQVuZOj5IQv1pkvtpDg",
      },

      originalUser: null,

      passwordForm: {
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
    };
  },

  created() {
    this.originalUser = { ...this.user };
  },

  methods: {
    updateProfile() {
      const payload = {
        id: this.user.id,
        full_name: this.user.fullName,
        email: this.user.email,
        phone: this.user.phone,
        birthday: this.user.birthday,
        address: this.user.address,
      };

      console.log("Dữ liệu cập nhật thông tin:", payload);
      alert("Đã cập nhật thông tin cá nhân. Kiểm tra console để xem dữ liệu.");
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

      console.log("Dữ liệu đổi mật khẩu:", payload);
      alert("Đã gửi yêu cầu đổi mật khẩu. Kiểm tra console để xem dữ liệu.");

      this.passwordForm = {
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
      };
    },

    resetForm() {
      this.user = { ...this.originalUser };

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
.thong-tin-ca-nhan-page {
  min-height: 100vh;
  background: #fbf8ff;
  color: #191b25;
  font-family: "Lexend", sans-serif;
  padding: 48px 40px;
}

.page-header {
  max-width: 1440px;
  margin: 0 auto 24px;
}

.page-header h1 {
  margin: 0;
  font-size: 32px;
  line-height: 1.3;
  font-weight: 600;
  color: #191b25;
}

.page-header p {
  margin: 4px 0 0;
  font-size: 16px;
  color: #737688;
}

.profile-layout {
  max-width: 1440px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 4fr 8fr;
  gap: 24px;
}

.left-column,
.right-column {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.profile-card,
.form-card {
  background: #ffffff;
  border: 1px solid #c3c5d9;
  border-radius: 12px;
  padding: 48px;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.06);
}

.profile-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.avatar-wrapper {
  position: relative;
  margin-bottom: 16px;
}

.avatar {
  width: 128px;
  height: 128px;
  border-radius: 999px;
  object-fit: cover;
  border: 4px solid #ededfb;
  box-shadow: 0 4px 12px rgba(15, 23, 42, 0.12);
}

.edit-avatar-button {
  position: absolute;
  right: 0;
  bottom: 0;
  border: none;
  background: #003ec7;
  color: #ffffff;
  width: 36px;
  height: 36px;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 62, 199, 0.25);
  transition: 0.2s;
}

.edit-avatar-button:hover {
  transform: scale(1.05);
}

.edit-avatar-button .material-symbols-outlined {
  font-size: 18px;
}

.profile-card h2 {
  margin: 0;
  font-size: 24px;
  line-height: 1.4;
  font-weight: 600;
  color: #191b25;
}

.role-badge {
  margin-top: 8px;
  background: #dde1ff;
  color: #001452;
  padding: 6px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
}

.join-date {
  margin: 16px 0 0;
  color: #737688;
  font-size: 12px;
}

.change-avatar-button {
  margin-top: 24px;
  width: 100%;
  border: 1px solid #003ec7;
  color: #003ec7;
  background: #ffffff;
  padding: 10px 16px;
  border-radius: 8px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
}

.change-avatar-button:hover {
  background: #f3f2ff;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 24px;
}

.section-title .material-symbols-outlined {
  color: #003ec7;
}

.section-title h3 {
  margin: 0;
  font-size: 24px;
  line-height: 1.4;
  font-weight: 600;
}

.info-form,
.security-form {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
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
  color: #434656;
  font-size: 14px;
  font-weight: 700;
}

.form-group input {
  border: 1px solid #c3c5d9;
  border-radius: 8px;
  padding: 12px;
  font-size: 16px;
  font-family: inherit;
  outline: none;
  transition: 0.2s;
  color: #191b25;
  background: #ffffff;
}

.form-group input:focus {
  border-color: #003ec7;
  box-shadow: 0 0 0 1px #003ec7;
}

.change-password-row {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

.outline-primary-button {
  background: #ffffff;
  border: 1px solid #003ec7;
  color: #003ec7;
  padding: 10px 48px;
  border-radius: 8px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
}

.outline-primary-button:hover {
  background: #f3f2ff;
}

.action-row {
  display: flex;
  justify-content: flex-end;
  gap: 24px;
  padding-top: 24px;
}

.cancel-button,
.save-button {
  border-radius: 8px;
  padding: 14px 64px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
}

.cancel-button {
  background: #ffffff;
  border: 1px solid #737688;
  color: #737688;
}

.cancel-button:hover {
  background: #f3f2ff;
}

.save-button {
  border: none;
  background: #003ec7;
  color: #ffffff;
  box-shadow: 0 8px 18px rgba(0, 62, 199, 0.18);
}

.save-button:hover {
  background: #0052ff;
}

.cancel-button:active,
.save-button:active,
.outline-primary-button:active {
  transform: scale(0.95);
}

@media (max-width: 1100px) {
  .profile-layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .thong-tin-ca-nhan-page {
    padding: 32px 20px;
  }

  .profile-card,
  .form-card {
    padding: 24px;
  }

  .info-form,
  .security-form {
    grid-template-columns: 1fr;
  }

  .form-group.full {
    grid-column: span 1;
  }

  .form-group.empty {
    display: none;
  }

  .action-row {
    flex-direction: column;
    gap: 12px;
  }

  .cancel-button,
  .save-button {
    width: 100%;
    padding: 14px 24px;
  }

  .outline-primary-button {
    width: 100%;
  }
}
</style>