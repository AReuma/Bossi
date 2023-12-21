import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import vuetify from './plugins/vuetify'
import {router} from './routes'
import { loadFonts } from './plugins/webfontloader'
import { numberWithCommas } from './constant/util';

loadFonts()

const app = createApp(App)

app.use(store);
app.use(vuetify);
app.use(router);
app.config.globalProperties.$numberWithCommas = numberWithCommas;
app.mount('#app')
