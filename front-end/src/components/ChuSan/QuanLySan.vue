<template>
  <div class="quan-ly-san-page">
    <!-- Page Header -->
    <div class="page-header">
      <div>
        <h1>Quản lý sân</h1>
        <p>Xem và quản lý tất cả các sân thuộc quyền sở hữu của bạn.</p>
      </div>

      <button class="add-button" @click="addField">
        <span class="material-symbols-outlined">add</span>
        Thêm sân mới
      </button>
    </div>

    <!-- Table Section -->
    <div class="table-card">
      <div class="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Chi tiết sân</th>
              <th>Loại</th>
              <th>Địa chỉ</th>
              <th>Trạng thái</th>
              <th class="text-right">Thao tác</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="field in displayedFields" :key="field.id">
              <td>
                <div class="field-detail">
                  <div class="field-image-box">
                    <img
                      v-if="field.image"
                      class="field-image"
                      :src="field.image"
                      :alt="field.name"
                    />

                    <div v-else class="empty-image">
                      <span class="material-symbols-outlined">image</span>
                    </div>
                  </div>

                  <div>
                    <p class="field-name">{{ field.name }}</p>
                  </div>
                </div>
              </td>

              <td>
                <span class="type-badge" :class="getTypeClass(field.sport)">
                  {{ field.sport }}
                </span>
              </td>

              <td>
                <p class="field-address">{{ field.location }}</p>
              </td>

              <td>
                <div class="status-cell">
                  <span
                    class="status-dot"
                    :class="getStatusClass(field.status)"
                  ></span>

                  <span
                    class="status-text"
                    :class="getStatusClass(field.status)"
                  >
                    {{ getStatusText(field.status) }}
                  </span>
                </div>
              </td>

              <td class="text-right">
                <button class="edit-button" @click="editField(field)">
                  <span class="material-symbols-outlined">edit</span>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Pagination -->
      <div class="table-footer">
        <p>
          Hiển thị {{ ownerFields.length === 0 ? 0 : startIndex + 1 }}
          đến {{ endIndex }} trong số {{ ownerFields.length }} mục
        </p>

        <div class="pagination">
          <button :disabled="currentPage === 1" @click="prevPage">
            <span class="material-symbols-outlined">chevron_left</span>
          </button>

          <button
            v-for="page in totalPages"
            :key="page"
            :class="{ active: currentPage === page }"
            @click="goToPage(page)"
          >
            {{ page }}
          </button>

          <button :disabled="currentPage === totalPages" @click="nextPage">
            <span class="material-symbols-outlined">chevron_right</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Quick Stats -->
    <div class="quick-stats">
      <div class="quick-card">
        <div class="quick-icon green">
          <span class="material-symbols-outlined">task_alt</span>
        </div>

        <div>
          <p>Đang hoạt động</p>
          <h4>{{ activeCount }}</h4>
        </div>
      </div>

      <div class="quick-card">
        <div class="quick-icon blue">
          <span class="material-symbols-outlined">pending</span>
        </div>

        <div>
          <p>Đang chờ duyệt</p>
          <h4>{{ pendingCount }}</h4>
        </div>
      </div>

      <div class="quick-card">
        <div class="quick-icon red">
          <span class="material-symbols-outlined">block</span>
        </div>

        <div>
          <p>Từ chối / Tạm ngưng</p>
          <h4>{{ stoppedCount }}</h4>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QuanLySan",

  data() {
    return {
      currentPage: 1,
      perPage: 4,

      // Giả lập chủ sân đang đăng nhập
      // Chủ sân này chỉ thấy sân có ownerId = 2
      currentOwnerId: 2,

      fields: [
        {
          id: 1,
          ownerId: 2,
          name: "Sân bóng đá Đại học Y",
          location: "1 Tôn Thất Tùng, Đống Đa, Hà Nội",
          sport: "Bóng đá",
          status: 1,
          image:
            "https://lh3.googleusercontent.com/aida-public/AB6AXuANOKmqYm270OK_PCslBdJvj-0kWoHtISkuBVDtYHGJMbORW6KRmz2vT29m4DodR7RzIfFPQC_GLnZyPtdVg3rvi5umRlIaM6EXXruAI2yTOYX-WFVlmg9wfRTxWjMV9rZ6gekf-yhZozqeDY8U12NsbTSDpN4tzG-eGxMvzS3yWVbfv70eQ4L42MW7qRSzSiLJ6ZDnbdAPtMX6ey_3gyYh2HAoYag8Nwhqg5aOZ0c7Yr8-ngnVHMDlsrTmFjarOmEMfvWxPAYT5JE",
        },
        {
          id: 2,
          ownerId: 2,
          name: "Sân bóng rổ Thống Nhất",
          location: "138 Đào Duy Từ, Quận 10, TP.HCM",
          sport: "Bóng rổ",
          status: 1,
          image:
            "https://lh3.googleusercontent.com/aida-public/AB6AXuCc8RRNc6hpAiNAkeNWUKEiagb9K3DiWIRoOQgENf2cBRb14J5CSZtAhotDdjF3JdCHSp0uQ5ytzVFpwNM6e9Jk2GuZd8VDSQrE4yYouvVvlW_V72NvDcSBJzePhB_vpUPmQc236VCHUUxLVL2zgQF1pCztF2b367Su545PnND9Sfpuj37jalJRSdmcGJAim3UY8R6TyaQ-mhgDF4DxUsixvR7XAjWSwWIezFx4rIygSCfgKanb5HttomgYVZUbod-Kye-FEeO-3nA",
        },
        {
          id: 3,
          ownerId: 3,
          name: "Sân bóng chuyền Phú Thọ",
          location: "219 Lý Thường Kiệt, Quận 11, TP.HCM",
          sport: "Bóng chuyền",
          status: 2,
          image:
            "https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?q=80&w=1200",
        },
        {
          id: 4,
          ownerId: 3,
          name: "Sân cầu lông Hòa Xuân",
          location: "Cẩm Lệ, Đà Nẵng",
          sport: "Cầu lông",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
        },
        {
          id: 5,
          ownerId: 2,
          name: "Sân bóng đá Mỹ Đình",
          location: "Lê Đức Thọ, Nam Từ Liêm, Hà Nội",
          sport: "Bóng đá",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1459865264687-595d652de67e?q=80&w=1200",
        },
        {
          id: 6,
          ownerId: 2,
          name: "Sân bóng rổ Cầu Giấy",
          location: "Cầu Giấy, Hà Nội",
          sport: "Bóng rổ",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1505666287802-931dc83a7fe4?q=80&w=1200",
        },
        {
          id: 7,
          ownerId: 3,
          name: "Sân bóng chuyền Bình Thạnh",
          location: "Bình Thạnh, TP.HCM",
          sport: "Bóng chuyền",
          status: 2,
          image:
            "https://images.unsplash.com/photo-1592656094267-764a45160876?q=80&w=1200",
        },
        {
          id: 8,
          ownerId: 3,
          name: "Sân cầu lông Hải Châu",
          location: "Hải Châu, Đà Nẵng",
          sport: "Cầu lông",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
        },
        {
          id: 9,
          ownerId: 10,
          name: "Sân bóng đá Lạch Tray",
          location: "Ngô Quyền, Hải Phòng",
          sport: "Bóng đá",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1522778119026-d647f0596c20?q=80&w=1200",
        },
        {
          id: 10,
          ownerId: 10,
          name: "Sân bóng rổ Nguyễn Du",
          location: "116 Nguyễn Du, Quận 1, TP.HCM",
          sport: "Bóng rổ",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1546519638-68e109498ffc?q=80&w=1200",
        },
        {
          id: 11,
          ownerId: 2,
          name: "Sân bóng chuyền Đống Đa",
          location: "Đống Đa, Hà Nội",
          sport: "Bóng chuyền",
          status: 0,
          image:
            "https://images.unsplash.com/photo-1609710228159-0fa9bd7c0827?q=80&w=1200",
        },
        {
          id: 12,
          ownerId: 3,
          name: "Sân cầu lông Tân Bình",
          location: "Tân Bình, TP.HCM",
          sport: "Cầu lông",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1613918431703-aa50889e3be9?q=80&w=1200",
        },
        {
          id: 13,
          ownerId: 10,
          name: "Sân bóng đá Hòa Khánh",
          location: "Liên Chiểu, Đà Nẵng",
          sport: "Bóng đá",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1518604666860-9ed391f76460?q=80&w=1200",
        },
        {
          id: 14,
          ownerId: 10,
          name: "Sân bóng rổ Thanh Xuân",
          location: "Thanh Xuân, Hà Nội",
          sport: "Bóng rổ",
          status: 2,
          image:
            "https://images.unsplash.com/photo-1519861531473-9200262188bf?q=80&w=1200",
        },
        {
          id: 15,
          ownerId: 2,
          name: "Sân cầu lông Lê Văn Tám",
          location: "Hai Bà Trưng, Hà Nội",
          sport: "Cầu lông",
          status: 1,
          image:
            "https://images.unsplash.com/photo-1626224583764-f87db24ac4ea?q=80&w=1200",
        },
      ],
    };
  },

  computed: {
    ownerFields() {
      return this.fields.filter(
        (field) => field.ownerId === this.currentOwnerId
      );
    },

    totalPages() {
      return Math.ceil(this.ownerFields.length / this.perPage);
    },

    startIndex() {
      return (this.currentPage - 1) * this.perPage;
    },

    displayedFields() {
      const start = this.startIndex;
      const end = start + this.perPage;

      return this.ownerFields.slice(start, end);
    },

    endIndex() {
      return this.startIndex + this.displayedFields.length;
    },

    activeCount() {
      return this.ownerFields.filter((field) => field.status === 1).length;
    },

    pendingCount() {
      return this.ownerFields.filter((field) => field.status === 2).length;
    },

    stoppedCount() {
      return this.ownerFields.filter((field) => field.status === 0).length;
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

    getTypeClass(sport) {
      if (sport === "Bóng rổ") {
        return "basketball";
      }

      if (sport === "Bóng chuyền") {
        return "volleyball";
      }

      if (sport === "Cầu lông") {
        return "badminton";
      }

      return "football";
    },

    getStatusText(status) {
      if (status === 1) {
        return "Hoạt động";
      }

      if (status === 2) {
        return "Chờ duyệt";
      }

      return "Tạm ngưng";
    },

    getStatusClass(status) {
      if (status === 1) {
        return "active";
      }

      if (status === 2) {
        return "pending";
      }

      return "stopped";
    },

    addField() {
      alert("Chức năng thêm sân mới sẽ xử lý sau.");
    },

    editField(field) {
      console.log("Sửa sân:", field);
      alert("Chức năng chỉnh sửa sân sẽ xử lý sau.");
    },
  },
};
</script>

<style scoped>
.quan-ly-san-page {
  min-height: 100vh;
  background: #f8f9ff;
  color: #0b1c30;
  font-family: "Inter", sans-serif;
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
  margin-bottom: 32px;
}

.page-header h1 {
  margin: 0;
  color: #0b1c30;
  font-size: 30px;
  line-height: 38px;
  font-weight: 700;
  letter-spacing: -0.02em;
}

.page-header p {
  margin: 4px 0 0;
  color: #565e74;
  font-size: 14px;
  line-height: 20px;
}

.add-button {
  border: none;
  background: #006e2f;
  color: #ffffff;
  padding: 12px 24px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.12);
  transition: 0.2s;
}

.add-button:hover {
  background: #22c55e;
}

.add-button:active {
  transform: scale(0.96);
}

.add-button .material-symbols-outlined {
  font-size: 20px;
}

.table-card {
  background: #ffffff;
  border: 1px solid #bccbb9;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(15, 23, 42, 0.08);
  overflow: hidden;
  margin-bottom: 32px;
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
  background: #f8fafc;
  border-bottom: 1px solid #f1f5f9;
}

th {
  padding: 16px 24px;
  color: #565e74;
  font-size: 12px;
  line-height: 16px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.04em;
  white-space: nowrap;
}

td {
  padding: 24px;
  border-bottom: 1px solid #f1f5f9;
  white-space: nowrap;
}

tbody tr {
  transition: 0.2s;
}

tbody tr:hover {
  background: #f8fafc;
}

.text-right {
  text-align: right;
}

.field-detail {
  display: flex;
  align-items: center;
  gap: 16px;
}

.field-image-box {
  width: 64px;
  height: 48px;
  border-radius: 8px;
  overflow: hidden;
  background: #f1f5f9;
  border: 1px solid #e2e8f0;
  flex-shrink: 0;
}

.field-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.empty-image {
  width: 100%;
  height: 100%;
  color: #cbd5e1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.field-name {
  margin: 0;
  color: #0b1c30;
  font-size: 14px;
  font-weight: 700;
}

.type-badge {
  padding: 4px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 700;
}

.type-badge.football {
  background: #dae2fd;
  color: #5c647a;
}

.type-badge.basketball {
  background: rgba(130, 171, 255, 0.2);
  color: #005ac2;
}

.type-badge.volleyball {
  background: #fff7ed;
  color: #ea580c;
}

.type-badge.badminton {
  background: #f0fdf4;
  color: #006e2f;
}

.field-address {
  max-width: 320px;
  margin: 0;
  color: #0b1c30;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
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

.status-dot.pending {
  background: #f59e0b;
}

.status-dot.stopped {
  background: #ba1a1a;
}

.status-text {
  font-size: 12px;
  font-weight: 700;
}

.status-text.active {
  color: #006e2f;
}

.status-text.pending {
  color: #d97706;
}

.status-text.stopped {
  color: #ba1a1a;
}

.edit-button {
  border: none;
  background: transparent;
  color: #64748b;
  padding: 8px;
  border-radius: 8px;
  cursor: pointer;
  transition: 0.2s;
}

.edit-button:hover {
  color: #006e2f;
  background: rgba(34, 197, 94, 0.1);
}

.edit-button .material-symbols-outlined {
  font-size: 22px;
}

.table-footer {
  padding: 16px 24px;
  border-top: 1px solid #f1f5f9;
  background: #ffffff;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.table-footer p {
  margin: 0;
  color: #565e74;
  font-size: 14px;
}

.pagination {
  display: flex;
  align-items: center;
  gap: 8px;
}

.pagination button {
  min-width: 32px;
  height: 32px;
  border: 1px solid #e2e8f0;
  background: #ffffff;
  color: #334155;
  border-radius: 4px;
  padding: 0 10px;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.pagination button:hover {
  background: #f8fafc;
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
}

.pagination .material-symbols-outlined {
  font-size: 18px;
}

.quick-stats {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
}

.quick-card {
  background: #eff4ff;
  border: 1px solid #bccbb9;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 24px;
}

.quick-icon {
  width: 48px;
  height: 48px;
  border-radius: 999px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.quick-icon.green {
  background: rgba(34, 197, 94, 0.2);
  color: #006e2f;
}

.quick-icon.blue {
  background: rgba(130, 171, 255, 0.2);
  color: #005ac2;
}

.quick-icon.red {
  background: rgba(255, 218, 214, 0.35);
  color: #ba1a1a;
}

.quick-card p {
  margin: 0;
  color: #565e74;
  font-size: 12px;
  line-height: 16px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.04em;
}

.quick-card h4 {
  margin: 2px 0 0;
  color: #0b1c30;
  font-size: 24px;
  line-height: 32px;
  font-weight: 700;
}

@media (max-width: 1100px) {
  .quick-stats {
    grid-template-columns: 1fr;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
  }
}

@media (max-width: 768px) {
  .table-footer {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
}
</style>