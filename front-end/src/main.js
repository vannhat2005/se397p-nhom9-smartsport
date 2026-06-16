import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Toaster from "@meforma/vue-toaster"
import { formatTime } from "./utils/formatters";

const app = createApp(App)

//Thông báo toast
app.use(Toaster, {
  position: "top-right"
});

//Định dạng thời gian
app.config.globalProperties.$formatTime = formatTime;

app.use(router)

app.mount('#app')
