import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import 'bootstrap/dist/css/bootstrap.css';
import '@fortawesome/fontawesome-free/css/all.css';
import moment from 'moment';

const app = createApp(App);
app.config.globalProperties.moment = moment;
app.use(router);
app.mount('#app');
