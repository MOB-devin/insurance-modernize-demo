import { createRouter, createWebHashHistory } from 'vue-router'
import Home from './views/HomeView.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/chat',
        name: 'chat',
        component: () => import('./views/ChatView.vue')
    },
    {
        path: '/chatbot',
        name: 'chatbot',
        component: () => import('./views/ChatbotView.vue')
    },
    {
        path: '/account',
        name: 'account',
        component: () => import('./views/AccountView.vue')
    },
    {
        path: '/products',
        name: 'products',
        component: () => import('./views/ProductsView.vue')
    },
    {
        path: '/dashboard',
        name: 'dashboard',
        component: () => import('./views/DashboardView.vue')
    },
    {
        path: '/products/:productCode',
        name: 'product',
        props: true,
        component: () => import('./views/ProductDetailsView.vue')
    },
    {
        path: '/policy/fromOffer/:offerNumber',
        name: 'createPolicy',
        props: true,
        component: () => import('./views/PolicyCreateView.vue')
    },
    {
        path: '/policies',
        name: 'policies',
        component: () => import('./views/PoliciesView.vue')
    },
    {
        path: '/policies/:policyNumber',
        name: 'policyDetails',
        props: true,
        component: () => import('./views/PolicyDetailsView.vue')
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
