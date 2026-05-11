<template>
    <div class="trang-chu-client">
        <!-- Hero Banner -->
        <section class="hero-section">
            <img class="hero-image" alt="Sân thể thao SmartSport"
                src="https://lh3.googleusercontent.com/aida-public/AB6AXuAMoY1Muqsj0_VmNnSwhNhQZ4TxfJAtdtkv3pa7Li_eeeVHGWEbjfe_vFkdf2rtafX11c1AkNjK7_JRWSbCnM8KuaWBrGq5pDzAkpiXKiCIPH4j8FdCMnUCyNtdXD-5D8A_44lk8lnlt9I2CERUP5fzAYE1I5J-8fXEOgxRiU1ROvtn_Tp939uZQqhSNzJZzB17qaqbnhBqBFtuA6y7ZfZ7mRj0DPLZmfZEPKebEY91PzKumsK6oCDcYslHB8FkswP52HoawRDy1q4" />

            <div class="hero-overlay">
                <div class="hero-content">
                    <h1>Đặt sân thể thao nhanh <br> chóng</h1>

                    <p>
                        Tiếp cận tức thì các sân bãi, sân cỏ và cơ sở vật chất chuyên
                        nghiệp gần bạn. Trận đấu tiếp theo của bạn bắt đầu tại đây.
                    </p>

                    <div class="search-box">
                        <div class="search-item">
                            <span class="material-symbols-outlined">search</span>
                            <input type="text" placeholder="Tên sân..." />
                        </div>

                        <div class="search-item">
                            <span class="material-symbols-outlined">location_on</span>
                            <input type="text" placeholder="Địa điểm..." />
                        </div>

                        <div class="search-item">
                            <span class="material-symbols-outlined">stadium</span>
                            <select>
                                <option>Loại sân</option>
                                <option>Bóng đá</option>
                                <option>Bóng rổ</option>
                                <option>Bóng chuyền</option>
                                <option>Cầu lông</option>
                            </select>
                        </div>

                        <button class="search-button">Tìm kiếm</button>
                    </div>
                </div>
            </div>
        </section>

        <!-- Sân nổi bật -->
        <section class="venue-section">
            <div class="section-header">
                <div>
                    <h2>Sân nổi bật</h2>
                    <p>
                        Các sân thể thao chất lượng cao được tuyển chọn trong khu vực của
                        bạn.
                    </p>
                </div>

                <div class="section-actions">
                    <button>
                        <span class="material-symbols-outlined">filter_list</span>
                    </button>

                    <button>
                        <span class="material-symbols-outlined">grid_view</span>
                    </button>
                </div>
            </div>

            <div class="venue-grid">
                <div v-for="venue in featuredVenues" :key="venue.id" class="venue-card">
                    <div class="venue-image-box">
                        <img class="venue-image" :src="venue.image" :alt="venue.name" />

                        <div class="status-badge available">
                            CÒN TRỐNG
                        </div>
                    </div>

                    <div class="venue-content">
                        <div class="venue-title-row">
                            <h3>{{ venue.name }}</h3>

                            <div class="rating">
                                <span class="material-symbols-outlined star-icon">star</span>
                                <span>4.8</span>
                            </div>
                        </div>

                        <p class="location">
                            <span class="material-symbols-outlined">location_on</span>
                            {{ venue.location }}
                        </p>

                        <div class="venue-info-row">
                            <div>
                                <span class="material-symbols-outlined">stadium</span>
                                <span>{{ venue.sport }}</span>
                            </div>

                            <div>
                                <span class="material-symbols-outlined">schedule</span>
                                <span>{{ venue.time }}</span>
                            </div>
                        </div>

                        <button class="detail-button">Xem chi tiết</button>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>

<script>
export default {
    name: "TrangChuClient",

    data() {
        return {
            venues: [
                {
                    id: 1,
                    name: "Sân bóng đá Đại học Y",
                    location: "1 Tôn Thất Tùng, Đống Đa, Hà Nội",
                    sport: "Bóng đá",
                    time: "06:00 - 23:00",
                    image:
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuANOKmqYm270OK_PCslBdJvj-0kWoHtISkuBVDtYHGJMbORW6KRmz2vT29m4DodR7RzIfFPQC_GLnZyPtdVg3rvi5umRlIaM6EXXruAI2yTOYX-WFVlmg9wfRTxWjMV9rZ6gekf-yhZozqeDY8U12NsbTSDpN4tzG-eGxMvzS3yWVbfv70eQ4L42MW7qRSzSiLJ6ZDnbdAPtMX6ey_3gyYh2HAoYag8Nwhqg5aOZ0c7Yr8-ngnVHMDlsrTmFjarOmEMfvWxPAYT5JE",
                },
                {
                    id: 2,
                    name: "Sân bóng rổ Thống Nhất",
                    location: "138 Đào Duy Từ, Quận 10, TP.HCM",
                    sport: "Bóng rổ",
                    time: "08:00 - 21:00",
                    image:
                        "https://lh3.googleusercontent.com/aida-public/AB6AXuCc8RRNc6hpAiNAkeNWUKEiagb9K3DiWIRoOQgENf2cBRb14J5CSZtAhotDdjF3JdCHSp0uQ5ytzVFpwNM6e9Jk2GuZd8VDSQrE4yYouvVvlW_V72NvDcSBJzePhB_vpUPmQc236VCHUUxLVL2zgQF1pCztF2b367Su545PnND9Sfpuj37jalJRSdmcGJAim3UY8R6TyaQ-mhgDF4DxUsixvR7XAjWSwWIezFx4rIygSCfgKanb5HttomgYVZUbod-Kye-FEeO-3nA",
                },
                {
                    id: 3,
                    name: "Sân bóng chuyền Phú Thọ",
                    location: "219 Lý Thường Kiệt, Quận 11, TP.HCM",
                    sport: "Bóng chuyền",
                    time: "07:00 - 22:00",
                    image:
                        "https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?q=80&w=1200",
                },
                {
                    id: 4,
                    name: "Sân cầu lông Hòa Xuân",
                    location: "Cẩm Lệ, Đà Nẵng",
                    sport: "Cầu lông",
                    time: "05:30 - 22:30",
                    image:
                        "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
                },
                {
                    id: 5,
                    name: "Sân bóng đá Mỹ Đình",
                    location: "Lê Đức Thọ, Nam Từ Liêm, Hà Nội",
                    sport: "Bóng đá",
                    time: "06:00 - 22:00",
                    image:
                        "https://images.unsplash.com/photo-1459865264687-595d652de67e?q=80&w=1200",
                },
                {
                    id: 6,
                    name: "Sân bóng rổ Cầu Giấy",
                    location: "Cầu Giấy, Hà Nội",
                    sport: "Bóng rổ",
                    time: "08:00 - 22:00",
                    image:
                        "https://images.unsplash.com/photo-1505666287802-931dc83a7fe4?q=80&w=1200",
                },
                {
                    id: 7,
                    name: "Sân bóng chuyền Bình Thạnh",
                    location: "Bình Thạnh, TP.HCM",
                    sport: "Bóng chuyền",
                    time: "08:00 - 22:00",
                    image:
                        "https://images.unsplash.com/photo-1592656094267-764a45160876?q=80&w=1200",
                },
                {
                    id: 8,
                    name: "Sân cầu lông Hải Châu",
                    location: "Hải Châu, Đà Nẵng",
                    sport: "Cầu lông",
                    time: "06:00 - 23:00",
                    image:
                        "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
                },
                {
                    id: 9,
                    name: "Sân bóng đá Lạch Tray",
                    location: "Ngô Quyền, Hải Phòng",
                    sport: "Bóng đá",
                    time: "05:00 - 23:00",
                    image:
                        "https://images.unsplash.com/photo-1522778119026-d647f0596c20?q=80&w=1200",
                },
                {
                    id: 10,
                    name: "Sân bóng rổ Nguyễn Du",
                    location: "116 Nguyễn Du, Quận 1, TP.HCM",
                    sport: "Bóng rổ",
                    time: "07:00 - 21:30",
                    image:
                        "https://images.unsplash.com/photo-1546519638-68e109498ffc?q=80&w=1200",
                },
                {
                    id: 11,
                    name: "Sân bóng chuyền Đống Đa",
                    location: "Đống Đa, Hà Nội",
                    sport: "Bóng chuyền",
                    time: "06:00 - 21:00",
                    image:
                        "https://images.unsplash.com/photo-1609710228159-0fa9bd7c0827?q=80&w=1200",
                },
                {
                    id: 12,
                    name: "Sân cầu lông Tân Bình",
                    location: "Tân Bình, TP.HCM",
                    sport: "Cầu lông",
                    time: "05:30 - 22:00",
                    image:
                        "https://images.unsplash.com/photo-1613918431703-aa50889e3be9?q=80&w=1200",
                },
                {
                    id: 13,
                    name: "Sân bóng đá Hòa Khánh",
                    location: "Liên Chiểu, Đà Nẵng",
                    sport: "Bóng đá",
                    time: "05:00 - 22:30",
                    image:
                        "https://images.unsplash.com/photo-1518604666860-9ed391f76460?q=80&w=1200",
                },
                {
                    id: 14,
                    name: "Sân bóng rổ Thanh Xuân",
                    location: "Thanh Xuân, Hà Nội",
                    sport: "Bóng rổ",
                    time: "08:00 - 22:00",
                    image:
                        "https://images.unsplash.com/photo-1519861531473-9200262188bf?q=80&w=1200",
                },
                {
                    id: 15,
                    name: "Sân cầu lông Lê Văn Tám",
                    location: "Hai Bà Trưng, Hà Nội",
                    sport: "Cầu lông",
                    time: "06:00 - 21:30",
                    image:
                        "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
                },
            ],
        };
    },

    computed: {
        featuredVenues() {
            return this.venues.slice(0, 4);
        },
    },
};
</script>

<style scoped>
.trang-chu-client {
    background: #fbf8ff;
    color: #191b25;
    font-family: "Lexend", sans-serif;
}

/* Hero */
.hero-section {
    position: relative;
    width: 100%;
    height: 500px;
    overflow: hidden;
}

.hero-image {
    position: absolute;
    inset: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.hero-overlay {
    position: absolute;
    inset: 0;
    background: linear-gradient(to right,
            rgba(0, 20, 82, 0.8),
            rgba(0, 20, 82, 0.1),
            transparent);
    display: flex;
    align-items: center;
    padding: 0 40px;
}

.hero-content {
    max-width: 720px;
    color: #ffffff;
}

.hero-content h1 {
    margin: 0 0 24px;
    font-size: 48px;
    line-height: 1.2;
    font-weight: 700;
    letter-spacing: -0.02em;
}

.hero-content p {
    margin: 0 0 48px;
    font-size: 18px;
    line-height: 1.6;
    opacity: 0.9;
}

/* Search */
.search-box {
    background: #ffffff;
    padding: 8px;
    border-radius: 12px;
    display: flex;
    gap: 8px;
    box-shadow: 0 20px 45px rgba(15, 23, 42, 0.25);
}

.search-item {
    flex: 1;
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 0 16px;
    border-right: 1px solid #f1f5f9;
}

.search-item:last-of-type {
    border-right: none;
}

.search-item span {
    color: #737688;
}

.search-item input,
.search-item select {
    width: 100%;
    border: none;
    outline: none;
    background: transparent;
    color: #191b25;
    font-size: 16px;
    padding: 12px 0;
    font-family: inherit;
}

.search-button {
    border: none;
    background: #003ec7;
    color: #ffffff;
    padding: 12px 32px;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.2s;
}

.search-button:hover {
    background: #0052ff;
}

/* Venue */
.venue-section {
    max-width: 1920px;
    margin: 0 auto;
    padding: 64px 40px;
}

.section-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    margin-bottom: 48px;
}

.section-header h2 {
    margin: 0 0 8px;
    font-size: 32px;
    line-height: 1.3;
    font-weight: 600;
    color: #191b25;
}

.section-header p {
    margin: 0;
    color: #64748b;
    font-size: 16px;
}

.section-actions {
    display: flex;
    gap: 8px;
}

.section-actions button {
    border: 1px solid #e2e8f0;
    background: #ffffff;
    border-radius: 8px;
    padding: 8px;
    cursor: pointer;
    transition: 0.2s;
}

.section-actions button:hover {
    background: #f8fafc;
}

.venue-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 24px;
}

.venue-card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 12px;
    overflow: hidden;
    transition: 0.3s;
}

.venue-card:hover {
    box-shadow: 0 12px 28px rgba(15, 23, 42, 0.12);
    transform: translateY(-2px);
}

.venue-image-box {
    position: relative;
    aspect-ratio: 16 / 9;
    overflow: hidden;
}

.venue-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: 0.5s;
}

.venue-card:hover .venue-image {
    transform: scale(1.05);
}

.status-badge {
    position: absolute;
    top: 16px;
    left: 16px;
    font-size: 12px;
    font-weight: 700;
    padding: 4px 8px;
    border-radius: 6px;
}

.status-badge.available {
    background: #dcfce7;
    color: #166534;
}

.venue-content {
    padding: 24px;
}

.venue-title-row {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    margin-bottom: 8px;
}

.venue-title-row h3 {
    margin: 0;
    font-size: 20px;
    font-weight: 600;
    color: #191b25;
}

.rating {
    display: flex;
    align-items: center;
    color: #f59e0b;
    font-size: 12px;
    font-weight: 700;
}

.star-icon {
    font-size: 16px;
    font-variation-settings: "FILL" 1;
}

.location {
    display: flex;
    align-items: center;
    gap: 4px;
    color: #64748b;
    font-size: 12px;
    margin: 0 0 16px;
}

.location .material-symbols-outlined {
    font-size: 16px;
}

.venue-info-row {
    display: flex;
    align-items: center;
    gap: 16px;
    margin-bottom: 48px;
}

.venue-info-row div {
    display: flex;
    align-items: center;
    gap: 4px;
    color: #475569;
    font-size: 12px;
}

.venue-info-row .material-symbols-outlined {
    font-size: 16px;
    color: #94a3b8;
}

.detail-button {
    width: 100%;
    border: 1px solid #003ec7;
    background: #ffffff;
    color: #003ec7;
    padding: 8px;
    border-radius: 8px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.2s;
}

.detail-button:hover {
    background: #003ec7;
    color: #ffffff;
}

/* Responsive */
@media (max-width: 1200px) {
    .venue-grid {
        grid-template-columns: repeat(3, 1fr);
    }
}

@media (max-width: 992px) {
    .venue-grid {
        grid-template-columns: repeat(2, 1fr);
    }

    .search-box {
        flex-direction: column;
    }

    .search-item {
        border-right: none;
        border-bottom: 1px solid #f1f5f9;
    }

    .search-item:last-of-type {
        border-bottom: none;
    }
}

@media (max-width: 768px) {
    .hero-overlay {
        padding: 0 20px;
    }

    .hero-content h1 {
        font-size: 36px;
    }

    .section-header {
        flex-direction: column;
        align-items: flex-start;
        gap: 16px;
    }

    .venue-section {
        padding: 48px 20px;
    }

    .venue-grid {
        grid-template-columns: 1fr;
    }
}
</style>