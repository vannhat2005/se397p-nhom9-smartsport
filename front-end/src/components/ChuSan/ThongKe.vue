<template>
    <div class="thong-ke-page">
        <div class="page-header">
            <h1>Tổng quan thống kê</h1>
            <p>
                Thống kê hiệu suất kinh doanh dựa trên hai chỉ số trọng yếu:
                Doanh thu và Lượt đặt sân.
            </p>
        </div>

        <div class="stats-wrapper">
            <!-- Tổng doanh thu -->
            <div class="stat-card">
                <div class="card-bg-icon revenue-bg">
                    <span class="material-symbols-outlined">payments</span>
                </div>

                <div class="card-content">
                    <div class="card-title">
                        <div class="icon-box revenue-icon">
                            <span class="material-symbols-outlined">payments</span>
                        </div>

                        <span>Tổng doanh thu (VNĐ)</span>
                    </div>

                    <h2>{{ formatNumber(totalRevenue) }}</h2>

                    <p>doanh thu tháng này</p>
                </div>
            </div>

            <!-- Tổng lượt đặt sân -->
            <div class="stat-card">
                <div class="card-bg-icon booking-bg">
                    <span class="material-symbols-outlined">event_available</span>
                </div>

                <div class="card-content">
                    <div class="card-title">
                        <div class="icon-box booking-icon">
                            <span class="material-symbols-outlined">event_available</span>
                        </div>

                        <span>Tổng lượt đặt sân</span>
                    </div>

                    <h2>{{ formatNumber(totalBookings) }}</h2>

                    <p>Lượt đặt thành công trong tháng</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ThongKe',

    data() {
        return {
            totalRevenue: 4520000,
            totalBookings: 124
        }
    },

    methods: {
        formatNumber(value) {
            return new Intl.NumberFormat('vi-VN').format(value)
        }
    }
}
</script>

<style scoped>
.thong-ke-page {
    width: 100%;
    min-height: calc(100vh - 64px);
    padding: 32px;
    background: #f8f9ff;
    color: #0b1c30;
    font-family: Inter, Arial, sans-serif;
    box-sizing: border-box;
}

.page-header {
    margin-bottom: 32px;
}

.page-header h1 {
    margin: 0 0 8px;
    font-size: 30px;
    line-height: 38px;
    font-weight: 700;
    color: #0b1c30;
    letter-spacing: -0.02em;
}

.page-header p {
    margin: 0;
    font-size: 16px;
    line-height: 24px;
    color: #3d4a3d;
}

.stats-wrapper {
    display: flex;
    flex-direction: column;
    gap: 32px;
    width: 100%;
}

.stat-card {
    position: relative;
    width: 100%;
    min-height: 280px;
    padding: 48px;
    display: flex;
    align-items: center;
    overflow: hidden;
    background: #ffffff;
    border: 1px solid #bccbb9;
    border-radius: 24px;
    box-sizing: border-box;
    box-shadow:
        0 10px 15px -3px rgb(0 0 0 / 0.07),
        0 4px 6px -4px rgb(0 0 0 / 0.07);
}

.card-content {
    position: relative;
    z-index: 2;
}

.card-title {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 32px;
}

.card-title span:last-child {
    font-size: 18px;
    line-height: 28px;
    font-weight: 700;
    letter-spacing: 0.2em;
    text-transform: uppercase;
    color: #3d4a3d;
}

.icon-box {
    width: 56px;
    height: 56px;
    border-radius: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.icon-box .material-symbols-outlined {
    font-size: 32px;
}

.revenue-icon {
    background: rgba(34, 197, 94, 0.2);
    color: #006e2f;
}

.booking-icon {
    background: rgba(130, 171, 255, 0.2);
    color: #005ac2;
}

.stat-card h2 {
    margin: 0;
    font-size: 72px;
    line-height: 1;
    font-weight: 700;
    color: #0b1c30;
    letter-spacing: -0.04em;
}

.stat-card p {
    margin: 16px 0 0;
    font-size: 18px;
    line-height: 28px;
    color: #3d4a3d;
}

.card-bg-icon {
    position: absolute;
    top: 0;
    right: 0;
    padding: 64px;
    opacity: 0.1;
}

.card-bg-icon .material-symbols-outlined {
    font-size: 160px;
}

.revenue-bg {
    color: #006e2f;
}

.booking-bg {
    color: #005ac2;
}

.material-symbols-outlined {
    font-family: 'Material Symbols Outlined';
    font-weight: normal;
    font-style: normal;
    line-height: 1;
    display: inline-block;
    white-space: nowrap;
    direction: ltr;
    font-feature-settings: 'liga';
    -webkit-font-feature-settings: 'liga';
    -webkit-font-smoothing: antialiased;
    font-variation-settings:
        'FILL' 0,
        'wght' 400,
        'GRAD' 0,
        'opsz' 24;
}

@media (max-width: 768px) {
    .thong-ke-page {
        padding: 24px 16px;
    }

    .page-header h1 {
        font-size: 26px;
        line-height: 34px;
    }

    .page-header p {
        font-size: 14px;
        line-height: 22px;
    }

    .stats-wrapper {
        gap: 20px;
    }

    .stat-card {
        min-height: 220px;
        padding: 28px;
        border-radius: 18px;
    }

    .card-title {
        gap: 12px;
        margin-bottom: 24px;
    }

    .card-title span:last-child {
        font-size: 14px;
        line-height: 22px;
        letter-spacing: 0.12em;
    }

    .icon-box {
        width: 44px;
        height: 44px;
    }

    .icon-box .material-symbols-outlined {
        font-size: 26px;
    }

    .stat-card h2 {
        font-size: 42px;
    }

    .stat-card p {
        font-size: 14px;
        line-height: 22px;
    }

    .card-bg-icon {
        padding: 32px;
    }

    .card-bg-icon .material-symbols-outlined {
        font-size: 96px;
    }
}
</style>