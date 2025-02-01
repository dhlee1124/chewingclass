import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import Dashboard from "@/views/Dashboard.vue";
import UserInfoForm from "@/views/UserInfoForm.vue";
import LoginWithEmail from "@/views/LoginWithEmail.vue";
import TermsAgreement from "@/views/TermsAgreement.vue";






const routes = [
  { path: "/", name: "Dashboard", component: Dashboard },
  { path: "/login", name: "Login", component: Login },
  { path: "/register", name: "Register", component: Register },
  { path: "/user-info-form", name: "UserInfoForm", component: UserInfoForm },
  { path: "/terms-agreement", name: "TermsAgreement", component: TermsAgreement },
  { path: "/LoginWithEmail", name: "LoginWithEmail", component: LoginWithEmail },
  /**{
  path: "/my-classes",
  name: "MyClasses",
  component: () => import("@/views/MyClasses.vue"),
  },**/
];

export default routes;
