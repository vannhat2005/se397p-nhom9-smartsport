import { createRouter, createWebHistory } from "vue-router";

const routes = [
  //Client
  {
    path: "/",
    component: () => import("../components/KhachHang/TrangChu.vue"),
    meta: { layout: "client" },
  },
  {
    path: "/client/xem-chi-tiet",
    component: () => import("../components/KhachHang/XemChiTiet.vue"),
    meta: { layout: "client" },
  },
  {
    path: "/client/xac-nhan-dat-san",
    component: () => import("../components/KhachHang/XacNhanDatSan.vue"),
    meta: { layout: "client" },
  },
  {
    path: "/client/lich-su-dat-san",
    component: () => import("../components/KhachHang/LichSuDatSan.vue"),
    meta: { layout: "client" },
  },
  {
    path: "/client/thanh-toan",
    component: () => import("../components/KhachHang/ThanhToan.vue"),
    meta: { layout: "client" },
  },
  {
    path: "/client/dat-san-thanh-cong",
    component: () => import("../components/KhachHang/DatSanThanhCong.vue"),
    meta: { layout: "client" },
  },
  {
    path: "/client/thong-tin-ca-nhan",
    component: () => import("../components/KhachHang/ThongTinCaNhan.vue"),
    meta: { layout: "client" },
  },
  //Admin
  {
    path: "/admin/trang-chu",
    component: () => import("../components/Admin/TrangChu.vue"),
    meta: { layout: "admin" },
  },
  {
    path: "/admin/quan-ly-nguoi-dung",
    component: () => import("../components/Admin/QuanLyNguoiDung.vue"),
    meta: { layout: "admin" },
  },
  {
    path: "/admin/quan-ly-loai-san",
    component: () => import("../components/Admin/QuanLyLoaiSan.vue"),
    meta: { layout: "admin"},
  },
  {
    path: "/admin/duyet-san",
    component: () => import("../components/Admin/DuyetSan.vue"),
    meta: { layout: "admin"},
  },
  //Chủ sân
  {
    path: "/chu-san/quan-ly-san",
    component: () => import("../components/ChuSan/QuanLySan.vue"),
    meta: { layout: "chu-san" },
  },
  {
    path: "/chu-san/booking",
    component: () => import("../components/ChuSan/Booking.vue"),
    meta: { layout: "chu-san" },
  },
  {
    path: "/chu-san/gia-san",
    component: () => import("../components/ChuSan/GiaSan.vue"),
    meta: { layout: "chu-san" },
  },
  {
    path: "/chu-san/thong-tin-ca-nhan",
    component: () => import("../components/ChuSan/ThongTinCaNhan.vue"),
    meta: { layout: "chu-san", topTitle: "Thông tin cá nhân" },
  },
  {
    path: "/chu-san/thong-ke",
    component: () => import("../components/ChuSan/ThongKe.vue"),
    meta: { layout: "chu-san" },
  },
  //Auth
  {
    path: "/auth/login",
    component: () => import("../components/Auth/Login.vue"),
    meta: { layout: "blank" },
  },
  {
    path: "/auth/register",
    component: () => import("../components/Auth/Register.vue"),
    meta: { layout: "blank" },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
});

export default router;
