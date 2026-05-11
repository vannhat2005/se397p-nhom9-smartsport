<template>
    <div class="register-page">
        <main class="register-main">
            <!-- Background -->
            <div class="background-layer">
                <img class="background-image" alt="Vận động viên chuyên nghiệp đang tập luyện"
                    src="https://lh3.googleusercontent.com/aida-public/AB6AXuBiHXOoXlgreTiFbqxEnMZkV18L1f_J6ucGDrszEGRr1AMtKZ8uzs9g6sKKoc1Twt8tI_XtF1CguPStyb1Fl0jIT0gLLdv_3LnfLIxHM_TcNHfMo4TmmmjBJ9YoJGknRKQRESyWrqr7CCwe7X2BvIdjSdugzy3HbssaCOd8lG0CQCE08sf2NSyqZqQw7vzCBakxA2zkFushLf5fJUMiM8zFATS6B7pHS7_ybSTOdDNtARwsyTtARdEiJNssia750rko-b7A8dJ5RD0" />
                <div class="background-gradient"></div>
            </div>

            <div class="register-container">
                <!-- Left Branding -->
                <section class="register-info">
                    <div class="brand-row">
                        <span class="material-symbols-outlined brand-icon">
                            sports_handball
                        </span>
                        <h1>SmartSport</h1>
                    </div>

                    <h2>Đặt sân chính xác cho phong độ đỉnh cao.</h2>

                    <p class="intro-text">
                        Tham gia cộng đồng tinh hoa của các nhà quản lý cơ sở và vận động
                        viên. Quản lý thời gian sân bãi của bạn với các công cụ chuyên
                        nghiệp.
                    </p>

                    <div class="feature-grid">
                        <div class="feature-card">
                            <span class="material-symbols-outlined">timer</span>
                            <h4>Đặt chỗ tức thì</h4>
                            <p>Giữ chỗ trong vài giây.</p>
                        </div>

                        <div class="feature-card">
                            <span class="material-symbols-outlined">analytics</span>
                            <h4>Phân tích thông minh</h4>
                            <p>Theo dõi số liệu hiệu suất.</p>
                        </div>
                    </div>
                </section>

                <!-- Form -->
                <section class="register-card">
                    <div class="form-title">
                        <h3>Tạo tài khoản của bạn</h3>
                        <p>Bước vào sân đấu cùng SmartSport.</p>
                    </div>

                    <div class="register-tabs">
                        <button class="tab active" type="button">Email</button>
                        <button class="tab" type="button">Điện thoại</button>
                    </div>

                    <form class="register-form" @submit.prevent="handleRegister">
                        <div class="form-group">
                            <label for="fullName">Họ và tên</label>
                            <div class="input-box">
                                <span class="material-symbols-outlined">person</span>
                                <input id="fullName" v-model="form.fullName" type="text" placeholder="Nguyễn Văn A" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="address">Địa chỉ</label>
                            <div class="input-box">
                                <span class="material-symbols-outlined">location_on</span>
                                <input id="address" v-model="form.address" type="text"
                                    placeholder="Nhập địa chỉ của bạn" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="email">Địa chỉ Email</label>
                            <div class="input-box">
                                <span class="material-symbols-outlined">mail</span>
                                <input id="email" v-model="form.email" type="email" placeholder="vidu@email.com" />
                            </div>
                        </div>

                        <div class="password-grid">
                            <div class="form-group">
                                <label for="password">Mật khẩu</label>
                                <div class="input-box">
                                    <span class="material-symbols-outlined">lock</span>
                                    <input id="password" v-model="form.password" type="password"
                                        placeholder="••••••••" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="confirmPassword">Xác nhận mật khẩu</label>
                                <div class="input-box">
                                    <span class="material-symbols-outlined">lock_reset</span>
                                    <input id="confirmPassword" v-model="form.confirmPassword" type="password"
                                        placeholder="••••••••" />
                                </div>
                            </div>
                        </div>

                        <div class="terms-row">
                            <input id="terms" v-model="form.acceptTerms" type="checkbox" />

                            <label for="terms">
                                Tôi đồng ý với
                                <a href="#">Điều khoản Dịch vụ</a>
                                và
                                <a href="#">Chính sách Bảo mật</a>.
                            </label>
                        </div>

                        <button class="register-button" type="submit">
                            Đăng ký
                        </button>
                    </form>

                    <p class="login-text">
                        Đã có tài khoản?
                        <router-link to="/login">Đăng nhập</router-link>
                    </p>
                </section>
            </div>
        </main>
    </div>
</template>

<script>
export default {
    name: "Register",

    data() {
        return {
            form: {
                fullName: "",
                address: "",
                email: "",
                password: "",
                confirmPassword: "",
                acceptTerms: false,
            },
        };
    },

    methods: {
        handleRegister() {
            console.log("Dữ liệu đăng ký:", this.form);
        },
    },
};
</script>

<style scoped>
.register-page {
    min-height: 100vh;
    background: #fbf8ff;
    color: #191b25;
    font-family: "Lexend", sans-serif;
    display: flex;
    flex-direction: column;
    overflow-x: hidden;
}

.register-main {
    position: relative;
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 64px 40px;
}

.background-layer {
    position: absolute;
    inset: 0;
    z-index: 0;
    overflow: hidden;
}

.background-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    opacity: 0.1;
    filter: grayscale(100%);
    transform: scale(1.05);
}

.background-gradient {
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, rgba(0, 62, 199, 0.05), #fbf8ff, #fbf8ff);
}

.register-container {
    position: relative;
    z-index: 1;
    width: 100%;
    max-width: 1200px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 24px;
    align-items: center;
}

.register-info {
    padding-right: 48px;
}

.brand-row {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 24px;
}

.brand-icon {
    color: #003ec7;
    font-size: 40px;
}

.brand-row h1 {
    margin: 0;
    font-size: 48px;
    line-height: 1.2;
    font-weight: 700;
    color: #003ec7;
    letter-spacing: -0.02em;
}

.register-info h2 {
    max-width: 460px;
    margin: 0;
    font-size: 32px;
    line-height: 1.3;
    font-weight: 600;
    color: #191b25;
}

.intro-text {
    max-width: 390px;
    margin-top: 8px;
    font-size: 18px;
    line-height: 1.6;
    color: #434656;
}

.feature-grid {
    margin-top: 48px;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
}

.feature-card {
    background: #f3f2ff;
    border: 1px solid rgba(195, 197, 217, 0.3);
    border-radius: 12px;
    padding: 16px;
}

.feature-card .material-symbols-outlined {
    color: #003ec7;
    margin-bottom: 4px;
}

.feature-card h4 {
    margin: 0;
    font-size: 14px;
    font-weight: 600;
}

.feature-card p {
    margin: 4px 0 0;
    font-size: 12px;
    color: #434656;
}

.register-card {
    background: #ffffff;
    border: 1px solid #c3c5d9;
    border-radius: 12px;
    padding: 48px;
    box-shadow: 0 1px 3px rgba(15, 23, 42, 0.08);
}

.form-title {
    margin-bottom: 48px;
}

.form-title h3 {
    margin: 0;
    font-size: 24px;
    line-height: 1.4;
    font-weight: 600;
    color: #191b25;
}

.form-title p {
    margin: 4px 0 0;
    color: #434656;
}

.register-tabs {
    display: flex;
    padding: 4px;
    background: #ededfb;
    border-radius: 8px;
    margin-bottom: 48px;
}

.tab {
    flex: 1;
    border: none;
    background: transparent;
    border-radius: 8px;
    padding: 8px 24px;
    font-size: 14px;
    font-weight: 600;
    color: #434656;
    cursor: pointer;
    transition: 0.2s;
}

.tab.active {
    background: #ffffff;
    color: #003ec7;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.12);
}

.register-form {
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

.input-box .material-symbols-outlined {
    position: absolute;
    left: 16px;
    top: 50%;
    transform: translateY(-50%);
    color: #737688;
    transition: 0.2s;
}

.input-box:focus-within .material-symbols-outlined {
    color: #003ec7;
}

.input-box input {
    width: 100%;
    padding: 16px 24px 16px 64px;
    background: #f3f2ff;
    border: 1px solid #c3c5d9;
    border-radius: 8px;
    font-size: 16px;
    outline: none;
    transition: 0.2s;
}

.input-box input:focus {
    border-color: #003ec7;
    box-shadow: 0 0 0 1px #003ec7;
}

.password-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
}

.terms-row {
    display: flex;
    align-items: flex-start;
    gap: 8px;
    margin-top: 8px;
}

.terms-row input {
    margin-top: 4px;
}

.terms-row label {
    font-size: 12px;
    line-height: 1.4;
    color: #434656;
}

.terms-row a {
    color: #003ec7;
    text-decoration: none;
}

.terms-row a:hover {
    text-decoration: underline;
}

.register-button {
    width: 100%;
    margin-top: 24px;
    border: none;
    background: #003ec7;
    color: #ffffff;
    padding: 16px;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.2s;
}

.register-button:hover {
    background: #0052ff;
    box-shadow: 0 8px 20px rgba(0, 62, 199, 0.2);
}

.register-button:active {
    transform: scale(0.98);
}

.login-text {
    margin-top: 48px;
    text-align: center;
    color: #434656;
}

.login-text a {
    color: #003ec7;
    font-weight: 600;
    text-decoration: none;
}

.login-text a:hover {
    text-decoration: underline;
}

.register-footer {
    width: 100%;
    padding: 24px 40px;
    border-top: 1px solid #c3c5d9;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 16px;
}

.register-footer p,
.footer-links a {
    margin: 0;
    font-size: 12px;
    color: #434656;
}

.footer-links {
    display: flex;
    gap: 24px;
}

.footer-links a {
    text-decoration: none;
    transition: 0.2s;
}

.footer-links a:hover {
    color: #003ec7;
}

@media (max-width: 992px) {
    .register-container {
        grid-template-columns: 1fr;
    }

    .register-info {
        display: none;
    }
}

@media (max-width: 768px) {
    .register-main {
        padding: 32px 20px;
    }

    .register-card {
        padding: 24px;
    }

    .password-grid {
        grid-template-columns: 1fr;
    }

    .register-footer {
        flex-direction: column;
        align-items: flex-start;
        padding: 24px 20px;
    }
}
</style>