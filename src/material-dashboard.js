// asset imports
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.css";
import "./assets/scss/main.scss";

// library auto imports
import "es6-promise/auto";

/**
 * This is the main Light Bootstrap Dashboard Vue plugin where dashboard related plugins are registerd.
 */
export default {
  install(Vue) {
    Vue.use(VueMaterial);
  },
};
