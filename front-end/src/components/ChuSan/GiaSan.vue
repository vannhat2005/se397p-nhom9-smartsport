<template>
    <div class="gia-san-page">
        <!-- Header Section -->
        <div class="page-header">
            <div>
                <h2>Giá sân</h2>
                <p>Thiết lập bảng giá linh hoạt theo thời gian và ngày trong tuần.</p>
                
            </div>

            <button class="add-button" @click="addPrice">
                <span class="material-symbols-outlined">add_circle</span>
                Thêm giá
            </button>
        </div>
        <p>VNĐ / giờ cho tất cả khung giờ.</p>

        <!-- Note -->



        <!-- Filter & Sort -->
        <div class="filter-card">
            <div class="day-filter">
                <button :class="{ active: selectedDayType === 'all' }" @click="selectedDayType = 'all'">
                    Tất cả các ngày
                </button>

                <button :class="{ active: selectedDayType === 'weekday' }" @click="selectedDayType = 'weekday'">
                    Ngày thường
                </button>

                <button :class="{ active: selectedDayType === 'weekend' }" @click="selectedDayType = 'weekend'">
                    Cuối tuần
                </button>
            </div>

            <div class="sort-box">
                <span>Sắp xếp theo:</span>

                <select v-model="sortType">
                    <option value="time_asc">Khung giờ tăng dần</option>
                    <option value="price_desc">Giá cao đến thấp</option>
                    <option value="price_asc">Giá thấp đến cao</option>
                </select>
            </div>
        </div>

        <!-- Pricing Table -->
        <div class="table-card">
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Loại ngày</th>
                            <th>Khung giờ</th>
                            <th class="text-right">Giá (VNĐ)</th>
                            <th class="text-center">Trạng thái</th>
                            <th class="text-right">Thao tác</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="price in displayedPrices" :key="price.id" :class="{ disabled: price.status === 0 }">
                            <td>
                                <span class="day-badge" :class="getDayTypeClass(price.dayType)">
                                    <span></span>
                                    {{ getDayTypeText(price.dayType) }}
                                </span>
                            </td>

                            <td>
                                <div class="time-range">
                                    <strong>{{ price.startTime }}</strong>
                                    <span class="material-symbols-outlined">trending_flat</span>
                                    <strong>{{ price.endTime }}</strong>
                                </div>
                            </td>

                            <td class="text-right">
                                <span class="price-text">
                                    {{ formatPrice(price.price) }}
                                </span>
                            </td>

                            <td class="text-center">
                                <label class="switch">
                                    <input type="checkbox" :checked="price.status === 1"
                                        @change="toggleStatus(price)" />
                                    <span class="slider"></span>
                                </label>
                            </td>

                            <td class="text-right">
                                <button class="action-button edit" @click="editPrice(price)">
                                    <span class="material-symbols-outlined">edit</span>
                                </button>

                                <button class="action-button delete" @click="deletePrice(price)">
                                    <span class="material-symbols-outlined">delete</span>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Pagination -->
            <div class="table-footer">
                <span>
                    Hiển thị {{ filteredPrices.length === 0 ? 0 : startIndex + 1 }}
                    đến {{ endIndex }} trong số {{ filteredPrices.length }} quy tắc giá
                </span>

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
    name: "GiaSan",

    data() {
        return {
            currentPage: 1,
            perPage: 5,
            selectedDayType: "all",
            sortType: "time_asc",

            prices: [
                {
                    id: 1,
                    dayType: "weekday",
                    startTime: "05:00",
                    endTime: "08:00",
                    price: 180000,
                    status: 1,
                },
                {
                    id: 2,
                    dayType: "weekday",
                    startTime: "08:00",
                    endTime: "17:00",
                    price: 220000,
                    status: 1,
                },
                {
                    id: 3,
                    dayType: "weekday",
                    startTime: "17:00",
                    endTime: "22:00",
                    price: 260000,
                    status: 1,
                },
                {
                    id: 4,
                    dayType: "weekday",
                    startTime: "22:00",
                    endTime: "23:00",
                    price: 200000,
                    status: 1,
                },
                {
                    id: 5,
                    dayType: "weekend",
                    startTime: "05:00",
                    endTime: "12:00",
                    price: 300000,
                    status: 1,
                },
                {
                    id: 6,
                    dayType: "weekend",
                    startTime: "12:00",
                    endTime: "18:00",
                    price: 350000,
                    status: 1,
                },
                {
                    id: 7,
                    dayType: "weekend",
                    startTime: "18:00",
                    endTime: "23:00",
                    price: 400000,
                    status: 1,
                },
            ],
        };
    },

    computed: {
        filteredPrices() {
            let result = [...this.prices];

            if (this.selectedDayType !== "all") {
                result = result.filter(
                    (price) => price.dayType === this.selectedDayType
                );
            }

            if (this.sortType === "price_desc") {
                result.sort((a, b) => b.price - a.price);
            } else if (this.sortType === "price_asc") {
                result.sort((a, b) => a.price - b.price);
            } else {
                result.sort((a, b) => a.startTime.localeCompare(b.startTime));
            }

            return result;
        },

        totalPages() {
            return Math.ceil(this.filteredPrices.length / this.perPage) || 1;
        },

        startIndex() {
            return (this.currentPage - 1) * this.perPage;
        },

        displayedPrices() {
            return this.filteredPrices.slice(
                this.startIndex,
                this.startIndex + this.perPage
            );
        },

        endIndex() {
            return this.startIndex + this.displayedPrices.length;
        },
    },

    watch: {
        selectedDayType() {
            this.currentPage = 1;
        },

        sortType() {
            this.currentPage = 1;
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

        formatPrice(value) {
            return new Intl.NumberFormat("vi-VN").format(value);
        },

        getDayTypeText(dayType) {
            if (dayType === "weekday") {
                return "Ngày thường";
            }

            return "Cuối tuần";
        },

        getDayTypeClass(dayType) {
            if (dayType === "weekend") {
                return "weekend";
            }

            return "weekday";
        },

        timeToMinutes(time) {
            const [hour, minute] = time.split(":").map(Number);
            return hour * 60 + minute;
        },

        isValidTimeFormat(time) {
            return /^([01]\d|2[0-3]):([0-5]\d)$/.test(time);
        },

        isTimeOverlap(startA, endA, startB, endB) {
            const aStart = this.timeToMinutes(startA);
            const aEnd = this.timeToMinutes(endA);
            const bStart = this.timeToMinutes(startB);
            const bEnd = this.timeToMinutes(endB);

            return aStart < bEnd && aEnd > bStart;
        },

        hasTimeConflict(newPrice, ignoreId = null) {
            return this.prices.some((price) => {
                if (price.id === ignoreId) {
                    return false;
                }

                if (price.dayType !== newPrice.dayType) {
                    return false;
                }

                return this.isTimeOverlap(
                    newPrice.startTime,
                    newPrice.endTime,
                    price.startTime,
                    price.endTime
                );
            });
        },

        validatePriceForm(price, ignoreId = null) {
            if (!price.dayType) {
                alert("Vui lòng chọn loại ngày.");
                return false;
            }

            if (!["weekday", "weekend"].includes(price.dayType)) {
                alert("Loại ngày không hợp lệ. Chỉ dùng weekday hoặc weekend.");
                return false;
            }

            if (!this.isValidTimeFormat(price.startTime)) {
                alert("Giờ bắt đầu không hợp lệ. Ví dụ đúng: 05:00");
                return false;
            }

            if (!this.isValidTimeFormat(price.endTime)) {
                alert("Giờ kết thúc không hợp lệ. Ví dụ đúng: 08:00");
                return false;
            }

            if (
                this.timeToMinutes(price.startTime) >=
                this.timeToMinutes(price.endTime)
            ) {
                alert("Giờ bắt đầu phải nhỏ hơn giờ kết thúc.");
                return false;
            }

            if (!price.price || Number(price.price) <= 0) {
                alert("Giá sân phải lớn hơn 0.");
                return false;
            }

            if (this.hasTimeConflict(price, ignoreId)) {
                alert(
                    "Khung giờ này bị trùng với khung giờ đã có trong cùng loại ngày. Vui lòng chọn khung giờ khác."
                );
                return false;
            }

            return true;
        },

        toggleStatus(price) {
            price.status = price.status === 1 ? 0 : 1;
        },

        addPrice() {
            const dayTypeInput = prompt(
                "Nhập loại ngày: weekday = Ngày thường, weekend = Cuối tuần",
                "weekday"
            );

            if (!dayTypeInput) {
                return;
            }

            const dayType = dayTypeInput.trim();

            if (!["weekday", "weekend"].includes(dayType)) {
                alert("Loại ngày không hợp lệ. Chỉ dùng weekday hoặc weekend.");
                return;
            }

            const startTime = prompt("Nhập giờ bắt đầu, ví dụ 05:00", "05:00");

            if (!startTime) {
                return;
            }

            const endTime = prompt("Nhập giờ kết thúc, ví dụ 08:00", "08:00");

            if (!endTime) {
                return;
            }

            const priceInput = prompt("Nhập giá sân theo giờ", "180000");

            if (!priceInput) {
                return;
            }

            const newPrice = {
                id: Date.now(),
                dayType,
                startTime: startTime.trim(),
                endTime: endTime.trim(),
                price: Number(priceInput),
                status: 1,
            };

            if (!this.validatePriceForm(newPrice)) {
                return;
            }

            this.prices.push(newPrice);
            alert("Thêm giá sân thành công.");
        },

        editPrice(price) {
            const dayTypeInput = prompt(
                "Nhập loại ngày: weekday = Ngày thường, weekend = Cuối tuần",
                price.dayType
            );

            if (!dayTypeInput) {
                return;
            }

            const dayType = dayTypeInput.trim();

            if (!["weekday", "weekend"].includes(dayType)) {
                alert("Loại ngày không hợp lệ. Chỉ dùng weekday hoặc weekend.");
                return;
            }

            const startTime = prompt("Nhập giờ bắt đầu", price.startTime);

            if (!startTime) {
                return;
            }

            const endTime = prompt("Nhập giờ kết thúc", price.endTime);

            if (!endTime) {
                return;
            }

            const priceInput = prompt("Nhập giá sân theo giờ", price.price);

            if (!priceInput) {
                return;
            }

            const updatedPrice = {
                ...price,
                dayType,
                startTime: startTime.trim(),
                endTime: endTime.trim(),
                price: Number(priceInput),
            };

            if (!this.validatePriceForm(updatedPrice, price.id)) {
                return;
            }

            price.dayType = updatedPrice.dayType;
            price.startTime = updatedPrice.startTime;
            price.endTime = updatedPrice.endTime;
            price.price = updatedPrice.price;

            alert("Cập nhật giá sân thành công.");
        },

        deletePrice(price) {
            const confirmDelete = confirm(
                `Bạn có chắc muốn xóa khung giá ${price.startTime} - ${price.endTime}?`
            );

            if (!confirmDelete) {
                return;
            }

            this.prices = this.prices.filter((item) => item.id !== price.id);

            if (this.currentPage > this.totalPages) {
                this.currentPage = this.totalPages;
            }

            alert("Đã xóa giá sân.");
        },
    },
};
</script>

<style scoped>
.gia-san-page {
    min-height: 100vh;
    background: #f8f9ff;
    color: #0b1c30;
    font-family: "Inter", sans-serif;
}

/* Header */
.page-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 24px;
    margin-bottom: 32px;
}

.page-header h2 {
    margin: 0;
    color: #0f172a;
    font-size: 30px;
    line-height: 38px;
    font-weight: 700;
    letter-spacing: -0.02em;
}

.page-header p {
    margin: 4px 0 0;
    color: #565e74;
    font-size: 14px;
}

.add-button {
    border: none;
    background: #006e2f;
    color: #ffffff;
    padding: 12px 24px;
    border-radius: 12px;
    font-weight: 700;
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    box-shadow: 0 8px 18px rgba(34, 197, 94, 0.2);
    transition: 0.2s;
}

.add-button:hover {
    background: #005321;
}

.add-button:active {
    transform: scale(0.96);
}

.add-button .material-symbols-outlined {
    font-size: 22px;
}

/* Note */
.note-card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    color: #565e74;
    border-radius: 12px;
    padding: 18px 24px;
    margin-bottom: 24px;
    font-size: 15px;
    font-weight: 600;
    box-shadow: 0 1px 4px rgba(15, 23, 42, 0.06);
}

/* Filter */
.filter-card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-radius: 12px 12px 0 0;
    padding: 16px 24px;
    display: flex;
    justify-content: space-between;
    gap: 16px;
    align-items: center;
    flex-wrap: wrap;
}

.day-filter {
    display: flex;
    border: 1px solid #e2e8f0;
    border-radius: 8px;
    overflow: hidden;
}

.day-filter button {
    border: none;
    background: #ffffff;
    color: #475569;
    padding: 10px 16px;
    font-weight: 700;
    cursor: pointer;
    border-right: 1px solid #e2e8f0;
    transition: 0.2s;
}

.day-filter button:last-child {
    border-right: none;
}

.day-filter button:hover {
    background: #f8fafc;
}

.day-filter button.active {
    background: #f1f5f9;
    color: #0f172a;
}

.sort-box {
    display: flex;
    align-items: center;
    gap: 10px;
}

.sort-box span {
    color: #565e74;
    font-size: 14px;
}

.sort-box select {
    border: 1px solid #e2e8f0;
    background: #ffffff;
    color: #0f172a;
    border-radius: 8px;
    padding: 9px 12px;
    font-family: inherit;
    outline: none;
}

.sort-box select:focus {
    border-color: #22c55e;
    box-shadow: 0 0 0 3px rgba(34, 197, 94, 0.16);
}

/* Table */
.table-card {
    background: #ffffff;
    border: 1px solid #e2e8f0;
    border-top: none;
    border-radius: 0 0 12px 12px;
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(15, 23, 42, 0.08);
}

.table-wrapper {
    overflow-x: auto;
}

table {
    width: 100%;
    border-collapse: collapse;
}

thead tr {
    background: rgba(248, 250, 252, 0.5);
    border-bottom: 1px solid #e2e8f0;
}

th {
    padding: 16px 24px;
    color: #565e74;
    font-size: 12px;
    font-weight: 800;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    white-space: nowrap;
    text-align: left;
}

td {
    padding: 20px 24px;
    border-bottom: 1px solid #f1f5f9;
    white-space: nowrap;
}

tbody tr {
    transition: 0.2s;
}

tbody tr:hover {
    background: #f8fafc;
}

tbody tr.disabled {
    opacity: 0.6;
}

.text-right {
    text-align: right;
}

.text-center {
    text-align: center;
}

.day-badge {
    display: inline-flex;
    align-items: center;
    gap: 6px;
    padding: 5px 12px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
}

.day-badge span {
    width: 6px;
    height: 6px;
    border-radius: 999px;
}

.day-badge.weekday {
    background: #eff6ff;
    color: #1d4ed8;
}

.day-badge.weekday span {
    background: #3b82f6;
}

.day-badge.weekend {
    background: #fff7ed;
    color: #c2410c;
}

.day-badge.weekend span {
    background: #f97316;
}

.time-range {
    display: flex;
    align-items: center;
    gap: 12px;
}

.time-range strong {
    color: #0f172a;
    font-size: 16px;
}

.time-range .material-symbols-outlined {
    color: #94a3b8;
    font-size: 18px;
}

.price-text {
    color: #0f172a;
    font-size: 20px;
    font-weight: 700;
}

/* Switch */
.switch {
    position: relative;
    display: inline-flex;
    width: 44px;
    height: 24px;
}

.switch input {
    opacity: 0;
    width: 0;
    height: 0;
}

.slider {
    position: absolute;
    inset: 0;
    background: #cbd5e1;
    border-radius: 999px;
    cursor: pointer;
    transition: 0.2s;
}

.slider::before {
    content: "";
    position: absolute;
    width: 20px;
    height: 20px;
    left: 2px;
    top: 2px;
    background: #ffffff;
    border-radius: 999px;
    transition: 0.2s;
    box-shadow: 0 1px 3px rgba(15, 23, 42, 0.25);
}

.switch input:checked+.slider {
    background: #006e2f;
}

.switch input:checked+.slider::before {
    transform: translateX(20px);
}

/* Actions */
.action-button {
    border: none;
    background: transparent;
    padding: 8px;
    border-radius: 8px;
    color: #94a3b8;
    cursor: pointer;
    transition: 0.2s;
}

.action-button .material-symbols-outlined {
    font-size: 22px;
}

.action-button.edit:hover {
    color: #006e2f;
    background: rgba(34, 197, 94, 0.1);
}

.action-button.delete:hover {
    color: #ba1a1a;
    background: #fff1f2;
}

/* Footer */
.table-footer {
    padding: 16px 24px;
    background: #f8fafc;
    border-top: 1px solid #e2e8f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.table-footer span {
    color: #565e74;
    font-size: 14px;
}

.pagination {
    display: flex;
    align-items: center;
    gap: 8px;
}

.pagination button {
    min-width: 36px;
    height: 36px;
    border: 1px solid #e2e8f0;
    background: #ffffff;
    color: #475569;
    border-radius: 8px;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: 0.2s;
}

.pagination button:hover {
    background: #ffffff;
    border-color: #bccbb9;
}

.pagination button.active {
    background: #006e2f;
    color: #ffffff;
    border-color: #006e2f;
}

.pagination button:disabled {
    opacity: 0.45;
    cursor: not-allowed;
}

.pagination button:disabled:hover {
    background: #ffffff;
    border-color: #e2e8f0;
}

.pagination .material-symbols-outlined {
    font-size: 20px;
}

/* Responsive */
@media (max-width: 900px) {
    .page-header {
        flex-direction: column;
        align-items: flex-start;
    }

    .filter-card,
    .table-footer {
        flex-direction: column;
        align-items: flex-start;
    }

    .sort-box {
        flex-direction: column;
        align-items: flex-start;
    }
}

@media (max-width: 640px) {
    .day-filter {
        flex-direction: column;
        width: 100%;
    }

    .day-filter button {
        width: 100%;
        border-right: none;
        border-bottom: 1px solid #e2e8f0;
    }

    .day-filter button:last-child {
        border-bottom: none;
    }
}
</style>