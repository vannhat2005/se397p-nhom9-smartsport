<template>
  <div class="login-page">
    <main class="login-container">
      <!-- Bên trái: Branding -->
      <section class="brand-side">
        <div class="brand-name">SmartSport</div>

        <div class="brand-image-layer">
          <img
            alt="Sự tập trung thể thao"
            src="https://lh3.googleusercontent.com/aida-public/AB6AXuBs05srWRPLPbne_jSxTKZWY3V9G1lqDIEOjFSLOxZxolEdTorMRJBW0uPezjssHrFwC4MkQV0Bj41_KJsVX29CkpLCtQ3eeC685oZBg1VsN_BNB8trEe1rIuSEkazslSDgHm6sz9ysp95jL5jn-IZYPFdO1BNYuH0Upeq0upe2AkZqqipl3Y11gbKo3xfvaX-F8PToGc-gt95u_SaPXempY2vmS_WpXtL-OYgH62eIzfZD6lKKanHMYexVOg-TjLMsn2d8iEx0gSk"
          />
        </div>

        <div class="brand-content">
          <h1>KHƠI DẬY BẢN LĨNH CHIẾN BINH.</h1>
          <p>
            Quản lý đặt lịch, theo dõi hiệu suất và thống trị sân chơi từ
            trung tâm điều khiển của chúng tôi.
          </p>
        </div>

        <div class="dot-pattern"></div>
      </section>

      <!-- Bên phải: Form đăng nhập -->
      <section class="form-side">
        <div class="form-wrapper">
          <div class="mobile-brand">
            <span class="material-symbols-outlined">sports_basketball</span>
            <span>SmartSport</span>
          </div>

          <div class="form-heading">
            <h2>Chào mừng trở lại</h2>
            <p>Đăng nhập để quản lý các cơ sở thể thao và các phiên tập của bạn.</p>
          </div>

          <form class="login-form" @submit.prevent="handleLogin">
            <div class="form-group">
              <label for="identifier">Số điện thoại hoặc Email</label>

              <div class="input-box">
                <span class="material-symbols-outlined">account_circle</span>
                <input
                  id="identifier"
                  v-model="form.identifier"
                  type="text"
                  placeholder="Nhập email hoặc số điện thoại của bạn"
                />
              </div>
            </div>

            <div class="form-group">
              <label for="password">Mật khẩu</label>

              <div class="input-box">
                <span class="material-symbols-outlined">lock</span>

                <input
                  id="password"
                  v-model="form.password"
                  :type="showPassword ? 'text' : 'password'"
                  placeholder="••••••••"
                />

                <button
                  class="eye-button"
                  type="button"
                  @click="showPassword = !showPassword"
                >
                  <span class="material-symbols-outlined">
                    {{ showPassword ? "visibility_off" : "visibility" }}
                  </span>
                </button>
              </div>
            </div>

            <button class="login-button" type="submit">
              Đăng nhập
              <span class="material-symbols-outlined">login</span>
            </button>
          </form>

          <div class="register-text">
            <p>
              Chưa có tài khoản?
              <router-link to="/auth/register">Đăng ký</router-link>
            </p>
          </div>
        </div>

        <footer class="login-footer">
          <p>© 2026 SmartSport Technologies.</p>

          <div class="footer-links">
            <a href="#">Điều khoản</a>
            <a href="#">Bảo mật</a>
          </div>
        </footer>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  name: "Login",

  data() {
    return {
      showPassword: false,
      form: {
        identifier: "",
        password: "",
      },
    };
  },

  methods: {
    handleLogin() {
      console.log("Dữ liệu đăng nhập:", this.form);
    },
  },
};
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: #fbf8ff;
  color: #191b25;
  font-family: "Lexend", sans-serif;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  overflow: hidden;
}

.login-container {
  width: 100%;
  max-width: 1440px;
  height: 800px;
  display: flex;
  background: #ffffff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 25px 60px rgba(15, 23, 42, 0.22);
}

.brand-side {
  width: 60%;
  position: relative;
  padding: 64px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background: linear-gradient(135deg, #003ec7 0%, #0052ff 100%);
  color: #ffffff;
  overflow: hidden;
}

.brand-name {
  position: relative;
  z-index: 2;
  font-size: 24px;
  font-weight: 900;
  letter-spacing: -0.5px;
}

.brand-image-layer {
  position: absolute;
  inset: 0;
  opacity: 0.4;
}

.brand-image-layer img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: grayscale(100%);
  mix-blend-mode: overlay;
}

.brand-content {
  position: relative;
  z-index: 2;
}

.brand-content h1 {
  max-width: 520px;
  margin: 0 0 24px;
  font-size: 48px;
  line-height: 1.2;
  letter-spacing: -0.02em;
  font-weight: 700;
  text-transform: uppercase;
}

.brand-content p {
  max-width: 420px;
  margin: 0;
  color: #dde1ff;
  font-size: 18px;
  line-height: 1.6;
}

.dot-pattern {
  position: absolute;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 50%;
  opacity: 0.1;
  pointer-events: none;
  background-image: radial-gradient(#ffffff 1px, transparent 1px);
  background-size: 20px 20px;
}

.form-side {
  width: 40%;
  background: #ffffff;
  padding: 40px 64px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.form-wrapper {
  width: 100%;
  max-width: 448px;
  margin: 0 auto;
}

.mobile-brand {
  display: none;
  align-items: center;
  gap: 8px;
  margin-bottom: 48px;
  color: #003ec7;
  font-size: 20px;
  font-weight: 900;
}

.mobile-brand .material-symbols-outlined {
  font-size: 32px;
}

.form-heading {
  margin-bottom: 48px;
}

.form-heading h2 {
  margin: 0 0 8px;
  font-size: 32px;
  line-height: 1.3;
  font-weight: 600;
  color: #191b25;
}

.form-heading p {
  margin: 0;
  color: #434656;
  font-size: 16px;
  line-height: 1.6;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.form-group label {
  font-size: 14px;
  font-weight: 600;
  color: #191b25;
}

.input-box {
  position: relative;
}

.input-box > .material-symbols-outlined {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #737688;
  transition: 0.2s;
}

.input-box:focus-within > .material-symbols-outlined {
  color: #003ec7;
}

.input-box input {
  width: 100%;
  height: 48px;
  padding: 12px 48px;
  background: #ededfb;
  border: 1px solid #c3c5d9;
  border-radius: 8px;
  outline: none;
  font-size: 16px;
  color: #191b25;
  transition: 0.2s;
}

.input-box input:focus {
  border-color: #003ec7;
  box-shadow: 0 0 0 1px #003ec7;
}

.eye-button {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  border: none;
  background: transparent;
  color: #737688;
  cursor: pointer;
  padding: 0;
  display: flex;
  align-items: center;
}

.eye-button:hover {
  color: #191b25;
}

.login-button {
  width: 100%;
  margin-top: 4px;
  border: none;
  border-radius: 8px;
  padding: 16px;
  background: linear-gradient(135deg, #003ec7 0%, #0052ff 100%);
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  box-shadow: 0 10px 24px rgba(0, 62, 199, 0.2);
  transition: 0.2s;
}

.login-button:hover {
  transform: translateY(-1px);
}

.login-button:active {
  transform: scale(0.95);
}

.register-text {
  margin-top: 64px;
  text-align: center;
}

.register-text p {
  margin: 0;
  color: #434656;
  font-size: 16px;
}

.register-text a {
  color: #003ec7;
  font-weight: 600;
  text-decoration: none;
}

.register-text a:hover {
  text-decoration: underline;
}

.login-footer {
  margin-top: auto;
  padding-top: 48px;
  border-top: 1px solid #f1f5f9;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.login-footer p,
.footer-links a {
  margin: 0;
  font-size: 10px;
  color: #737688;
  text-decoration: none;
}

.footer-links {
  display: flex;
  gap: 24px;
}

.footer-links a:hover {
  color: #003ec7;
}

@media (max-width: 1024px) {
  .login-container {
    height: auto;
    min-height: 720px;
  }

  .brand-side {
    display: none;
  }

  .form-side {
    width: 100%;
  }

  .mobile-brand {
    display: flex;
  }
}

@media (max-width: 640px) {
  .login-page {
    padding: 0;
  }

  .login-container {
    min-height: 100vh;
    border-radius: 0;
  }

  .form-side {
    padding: 32px 24px;
  }

  .form-heading h2 {
    font-size: 28px;
  }

  .login-footer {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }
}
</style>