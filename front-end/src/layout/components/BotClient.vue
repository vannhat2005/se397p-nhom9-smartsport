<template>
    <footer class="app-footer">
        <div class="footer-container">
            <div class="footer-brand">
                <span class="footer-logo">SmartSport</span>
                <p class="footer-copyright">
                    © 2026 SmartSport Technologies. Bảo lưu mọi quyền.
                </p>
            </div>

            <div class="footer-links">
                <a href="#">Điều khoản dịch vụ</a>
                <a href="#">Chính sách bảo mật</a>
                <a href="#">Đối tác cơ sở</a>
                <a href="#">Liên hệ hỗ trợ</a>
            </div>
        </div>
    </footer>
</template>

<script>
export default {
    name: "BotClient",
};
</script>

<style scoped>
.app-footer {
    background: #ffffff;
    border-top: 1px solid #e2e8f0;
}

.footer-container {
    max-width: 1920px;
    margin: 0 auto;
    padding: 32px 40px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.footer-brand {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.footer-logo {
    font-size: 18px;
    font-weight: 700;
    color: #0f172a;
}

.footer-copyright {
    font-size: 12px;
    color: #64748b;
    margin: 0;
}

.footer-links {
    display: flex;
    align-items: center;
    gap: 32px;
}

.footer-links a {
    font-size: 12px;
    color: #64748b;
    text-decoration: none;
    transition: 0.2s;
}

.footer-links a:hover {
    color: #2563eb;
}

@media (max-width: 768px) {
    .footer-container {
        padding: 24px 20px;
        flex-direction: column;
        align-items: flex-start;
        gap: 20px;
    }

    .footer-links {
        flex-direction: column;
        align-items: flex-start;
        gap: 12px;
    }
}
</style>