import VueRouter from "vue-router";
import PodsPage from "@/pages/PodsPage";
import DeploymentsPage from "@/pages/DeploymentsPage";
import InstancePage from "@/pages/InstancePage";
import ApplicationPage from "@/pages/ApplicationPage";
import ServicePage from "@/pages/ServicePage";
import AccountPage from "@/pages/AccountPage";
import Login from "@/pages/Login";
// import LoginSuccess from "@/components/LoginSuccess";
import IndexPage from "@/pages/IndexPage";
import Overview from "@/pages/Overview";
import Xterminal from "@/pages/Xterminal";
import Xterminal2 from "@/pages/Xterminal2";
import NamespacedServicePage from "@/pages/NamespacedServicePage";
import PodApplicationPage from "@/pages/PodApplicationPage";
import UserRecordPage from "@/pages/UserRecordPage";


const router = new VueRouter({
    routes:[
        {
            path:'/index',
            component:IndexPage,
            children:[
                {
                    path:'overview',
                    component:Overview
                },
                {
                    path:'/',
                    component:Overview
                },
                {
                    path:'podsPage',
                    component:PodsPage
                },
                {
                    path:'deploymentsPage',
                    component:DeploymentsPage
                },
                {
                    path:'instancePage',
                    component:InstancePage
                },
                {
                    path:'applicationPage',
                    component:ApplicationPage,
                },
                {
                    path:'servicePage',
                    component:ServicePage
                },
                {
                    path:'accountPage',
                    component:AccountPage
                },
                {
                    path:'namespacedServicePage',
                    component:NamespacedServicePage
                },
                {
                    path:'podApplicationPage',
                    component:PodApplicationPage,
                },
                {
                    path:'userRecordPage',
                    component:UserRecordPage
                }
            ]
        },
        {
            path:'/',
            component:Login
        },

        {
            path:'/webssh',
            component:Xterminal
        },
        {
            path:'/webssh2',
            component:Xterminal2
        }
    ]
})

router.beforeEach((to,from,next) => {
    if(to.path === '/') { next() }
    else {
        let username = localStorage.getItem('Username')
        if(username === null || username === '') { next('/') }
        else {next()}
    }
})

export default router