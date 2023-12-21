import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import vuetify from './plugins/vuetify'
import {router} from './routes'
import { loadFonts } from './plugins/webfontloader'

loadFonts()

createApp(App)
    .use(store)
    .use(vuetify)
    .use(router)
.mount('#app')
