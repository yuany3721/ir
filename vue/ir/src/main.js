import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);

//axios
import axios from "./axios";
app.config.globalProperties.$http = axios;

app.mount("#app");
