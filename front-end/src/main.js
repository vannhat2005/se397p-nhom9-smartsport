import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Toaster from "@meforma/vue-toaster"

const app = createApp(App)

//Thông báo toast
app.use(Toaster, {
  position: "top-right"
});

app.use(router)

app.mount('#app')
