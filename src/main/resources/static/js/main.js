import Vue from 'vue'
import Vuetify from 'vuetify'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import Toasted from 'vue-toasted'

import mainStore from './store/mainStore.js'
import subscripModalStore from './store/subscripModalStore.js'
import pagerStore from './store/pagerStore.js'
import projectsStore from "./store/projectsStore.js";
import createEditEntityStore from "./store/createEditEntityStore.js";
import checkListOrderStore from "./store/checkListOrderStore.js";

import App from 'pages/App.vue'
import Home from 'components/home/Home.vue'
import Profile from 'components/profile/Profile.vue'
import UserMessages from 'components/messages/UserMessages.vue'
import Projects from "components/projects/Projects.vue";
import NewEditProject from "./components/projects/NewEditProject.vue";
import NewEditModule from "./components/modules/NewEditModule.vue";
import Project from "./components/projects/Project.vue";
import Module from "./components/modules/Module.vue";
import NewEditTestPlan from "./components/testplan/NewEditTestPlan.vue";
import NewEditRequirement from "./components/requirement/NewEditRequirement.vue";
import Requirement from "./components/requirement/Requirement.vue";
import NewEditTestCase from "./components/testcase/NewEditTestCase.vue";
import TestCase from "./components/testcase/TestCase.vue";
import NewEditTask from "./components/task/NewEditTask.vue";
import NewEditTestCheckList from "./components/checklist/NewEditTestCheckList.vue";
import CheckList from "./components/checklist/CheckList.vue";
import EditCheckListTestCase from "./components/checklist/EditCheckListTestCase.vue";
import UserList from "./components/users/UserList.vue";

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.use(Toasted);
Vue.use(Vuetify);

const routes = [
    { path: '/', component: Home, props: true },
    { path: '/user/profile', component: Profile, props: true },
    { path: '/user-messages/:id', component: UserMessages, props: true },
    { path: '/projects/:id', component: Projects, props: true },
    { path: '/new-project', component: NewEditProject, props: true },
    { path: '/edit-project/:id', component: NewEditProject, props: true },
    { path: '/view-project/:id', component: Project, props: true },
    { path: '/new-module/:projectId', component: NewEditModule, props: true },
    { path: '/edit-module/:projectId/:id', component: NewEditModule, props: true },
    { path: '/view-module/:id', component: Module, props: true },
    { path: '/new-test-plan/:parentId', component: NewEditTestPlan, props: true },
    { path: '/edit-test-plan/:parentId/:id', component: NewEditTestPlan, props: true },
    { path: '/new-requirement/:parentId', component: NewEditRequirement, props: true },
    { path: '/edit-requirement/:parentId/:id', component: NewEditRequirement, props: true },
    { path: '/view-requirement/:id', component: Requirement, props: true },
    { path: '/new-test-case/:parentId', component: NewEditTestCase, props: true },
    { path: '/edit-test-case/:parentId/:id', component: NewEditTestCase, props: true },
    { path: '/view-test-case/:id', component: TestCase, props: true },
    { path: '/new-task/:parentId', component: NewEditTask, props: true },
    { path: '/edit-task/:parentId/:id', component: NewEditTask, props: true },
    { path: '/new-check-list/:parentId', component: NewEditTestCheckList, props: true },
    { path: '/edit-check-list/:parentId/:id', component: NewEditTestCheckList, props: true },
    { path: '/view-check-list/:id', component: CheckList, props: true },
    { path: '/edit-check-list-test-case/:parentId/:id', component: EditCheckListTestCase, props: true },
    { path: '/users', component: UserList, props: true },
];

const router = new VueRouter({
    routes
});

const store = new Vuex.Store({
    modules: {
        main: mainStore,
        subscripModal: subscripModalStore,
        pager: pagerStore,
        projects: projectsStore,
        createEditEntity: createEditEntityStore,
        checkListOrder: checkListOrderStore,
    }
});

new Vue({
    el: '#app',
    router,
    store,
    vuetify: new Vuetify({ }),
    render: a => a(App)
});