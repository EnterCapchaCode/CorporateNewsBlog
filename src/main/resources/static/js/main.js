import Vue from 'vue'
import Vuetify from 'vuetify'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Toasted from 'vue-toasted'

import mainStore from './store/mainStore.js'
import pagerStore from './store/pagerStore.js'
import usersStore from "./store/usersStore";
import commentsStore from "./store/commentsStore";

import App from 'pages/App.vue'
import Home from 'components/home/Home.vue'
import Profile from 'components/profile/Profile.vue'
import UserMessages from 'components/messages/UserMessages.vue'
import UserList from "./components/users/UserList.vue";

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(Toasted);
Vue.use(Vuetify);

const routes = [
    { path: '/', component: Home, props: true },
    { path: '/user/profile', component: Profile, props: true },
    { path: '/user-messages/:id', component: UserMessages, props: true },
    { path: '/users/:id', component: UserList, props: true },
];

const router = new VueRouter({
    routes
});

const store = new Vuex.Store({
    modules: {
        main: mainStore,
        pager: pagerStore,
        usersStore: usersStore,
        commentsStore: commentsStore
    }
});

new Vue({
    el: '#app',
    router,
    store,
    vuetify: new Vuetify({ }),
    render: a => a(App)
});