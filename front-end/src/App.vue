<template>
  <component :is="layout">
    <router-view />
  </component>
</template>


<script>

import ClientLayout from './layout/wrapper/Client.vue';
import AdminLayout from './layout/wrapper/Admin.vue';
import ChuSanLayout from './layout/wrapper/ChuSan.vue';
import Blank from './layout/wrapper/Blank.vue';


export default {
  name: 'App',
  computed: {
    layout() {
      const layout = this.$route.meta.layout;
      if (layout === 'admin') {
        return AdminLayout;
      } else if (layout === 'client')
        return ClientLayout;
      else if (layout === 'chu-san')
        return ChuSanLayout;
      else {
        return Blank;
      }
    }
  }
}
</script>
<style></style>