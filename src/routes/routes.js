import Login from "@/views/Login.vue";
import Dashboard from "@/views/Dashboard.vue";
import UserInfoForm from "@/views/UserInfoForm.vue";

import TermsAgreement from "@/views/TermsAgreement.vue";






const routes = [
  { path: "/", name: "Dashboard", component: Dashboard },
  { path: "/login", name: "Login", component: Login },
  { path: "/user-info-form", name: "UserInfoForm", component: UserInfoForm },
  { path: "/terms-agreement", name: "TermsAgreement", component: TermsAgreement },

{
  path: "/my-classes",
  name: "MyClasses",
  component: () => import("@/views/MyClasses.vue"),
  },
   {
    path: "/login-with-email",
    name: "LoginWithEmail",
    component: () => import("@/views/LoginWithEmail.vue"),
    },

  {
    path: "/class-detail",
    name: "ClassDetail",
    component: () => import("@/views/ClassDetail.vue"),
    },

    {
      path: "/event-page",
      name: "EventPage",
      component: () => import("@/views/EventPage.vue"),
      },
      {
        path: "/bookmarked-class",
        name: "BookmarkedClass",
        component: () => import("@/views/BookmarkedClass.vue"),
        },
        {
          path: "/my-comments",
          name: "MyComments",
          component: () => import("@/views/MyComments.vue"),
          },
          {
            path: "/pay-list",
            name: "PayList",
            component: () => import("@/views/PayList.vue"),
            },
            {
              path: "/my-page",
              name: "MyPage",
              component: () => import("@/views/MyPage.vue"),
              },
             
              {
                path: "/payment",
                name: "PaymentPage",
                component: () => import("@/views/PaymentPage.vue"),
              },

              {
                path: "/register",
                name: "Register",
                component: () => import("@/views/Register.vue"),
              },

            ];

export default routes;
