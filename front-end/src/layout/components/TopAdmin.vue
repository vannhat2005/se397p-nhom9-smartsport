<template>
  <header class="top-admin">
    <div class="top-actions">
      <button class="icon-button">
        <span class="material-symbols-outlined">notifications</span>
      </button>

      <div class="divider"></div>

      <div class="admin-info">
        <p>Admin Manager</p>
        <span>Administrator</span>
      </div>

      <img class="avatar"
        src="https://lh3.googleusercontent.com/aida-public/AB6AXuBdJNIO9qpn-ivvCR1d5vJCkS0Jn0DVhQ7s-c9WMpH7-QIQ61GJOnBVwVPIAtJIsoaHEpgk6kl9Ekq1HMbCOwFgmoe9CGnC_IB_cZMFYNJHoTw_gS6hM_oUkNE860vwwcPljkOzPXaKbDOEnJjgyc7thqSmOQEcfMD93KmGbEcrRSGQnl-icia8AxvLvCbMvCFaq-gUaClKjqG28_gMbiUmxoEA-schenICmQ8EF9Qx43gHEvn1RG-PSTGYuh8dym3j0FxSaQnh1fQ"
        alt="Admin" />
    </div>
  </header>
</template>

<script>
export default {
  name: "TopAdmin",
};
</script>

<style scoped>
.top-admin {
  position: fixed;
  top: 0;
  left: 280px;
  right: 0;
  height: 64px;
  z-index: 40;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid #e2e8f0;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.06);
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 24px;
  font-family: "Inter", sans-serif;
}

.top-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.icon-button {
  border: none;
  background: transparent;
  color: #64748b;
  cursor: pointer;
  padding: 8px;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: 0.2s;
}

.icon-button:hover {
  background: #f8fafc;
  color: #16a34a;
}

.icon-button .material-symbols-outlined {
  font-size: 24px;
}

.divider {
  width: 1px;
  height: 32px;
  background: #e2e8f0;
  margin: 0 4px;
}

.admin-info {
  text-align: right;
}

.admin-info p {
  margin: 0;
  color: #191c1d;
  font-size: 14px;
  line-height: 20px;
  font-weight: 600;
}

.admin-info span {
  display: block;
  margin-top: 1px;
  color: #64748b;
  font-size: 10px;
  line-height: 14px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.06em;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 999px;
  object-fit: cover;
  border: 2px solid #ffffff;
  box-shadow: 0 2px 8px rgba(15, 23, 42, 0.15);
}

@media (max-width: 768px) {
  .top-admin {
    left: 0;
    padding: 0 16px;
  }

  .admin-info {
    display: none;
  }
}
</style>