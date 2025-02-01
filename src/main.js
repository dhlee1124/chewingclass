import Vue from "vue";
import VueRouter from "vue-router";
import App from "./App";
import "@/assets/scss/main.scss";

// router setup
import routes from "./routes/routes"; // 기존 routes.js 파일에서 라우트 가져오기

// Plugins
import GlobalComponents from "./globalComponents";
import GlobalDirectives from "./globalDirectives";
import Notifications from "./components/NotificationPlugin";

// MaterialDashboard plugin
import MaterialDashboard from "./material-dashboard";

import Chartist from "chartist";

// configure router
const router = new VueRouter({
  routes, // 라우터 경로 설정
  mode: "history", // HTML5 History API 사용
  base: process.env.BASE_URL,
  linkExactActiveClass: "nav-item active",
});

// Vue 전역 설정
Vue.prototype.$Chartist = Chartist;

Vue.use(VueRouter);
Vue.use(MaterialDashboard);
Vue.use(GlobalComponents);
Vue.use(GlobalDirectives);
Vue.use(Notifications);

/* eslint-disable no-new */
new Vue({
  el: "#app",
  render: (h) => h(App),
  router, // 라우터 등록
  data: {
    Chartist: Chartist,
  },
});
