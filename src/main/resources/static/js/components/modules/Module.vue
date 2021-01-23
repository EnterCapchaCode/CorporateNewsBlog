<template>
    <div>
        <div class="form-row flex-between">
            <div class="form-group col-md-9 info-block">
                <h2>{{module.name}}</h2>
                <label>
                    <textarea>{{module.description}}</textarea>
                </label>
                <p>Initial Date: {{toDatetimeString(module.initialDate)}}</p>
                <p>End Date: {{toDatetimeString(module.endDate)}}</p>
            </div>
            <div class="form-group col-md-3">
                <div class="flex-start">
                    <h5>Employees</h5>
                    <button v-on:click="openEmployeesModal" class="btn btn-primary ml-2" type="button">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <p v-for="user in module.users" :key="user.name">{{user.name}}</p>
            </div>
        </div>
        <div class="form-row flex-column margin-bottom-30">
            <div class="flex-start">
                <h5>Test Plan</h5>
                <button class="btn btn-primary ml-2" type="button">
                    <router-link class="color-white" :to="`/new-test-plan/${module.id}`"><i class="fas fa-plus"></i></router-link>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Test Method</th>
                        <th class="text-left">Initial Date</th>
                        <th class="text-left">End Date</th>
                        <th class="text-left">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in module.testPlans" :key="item.name" class="table-item__cursor-pointer">
                        <td>{{ item.name }}</td>
                        <td>{{ item.testMethod }}</td>
                        <td>{{ toDatetimeString(item.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.endDate) }}</td>
                        <td>{{ item.status }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-test-plan/${module.id}/${item.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                                <button v-on:click="deleteTestPlan(item.id)" class="btn btn-primary ml-2" type="button">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <div class="form-row flex-column margin-bottom-30">
            <div class="flex-start">
                <h5>Requirements</h5>
                <button class="btn btn-primary ml-2" type="button">
                    <router-link class="color-white" :to="`/new-requirement/${module.id}`"><i class="fas fa-plus"></i></router-link>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Initial Date</th>
                        <th class="text-left">End Date</th>
                        <th class="text-left">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in module.requirements" :key="item.name" class="table-item__cursor-pointer">
                        <td v-on:click="viewRequirement(item.id)">{{ item.name }}</td>
                        <td>{{ toDatetimeString(item.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.endDate) }}</td>
                        <td>{{ item.status }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-requirement/${module.id}/${item.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                                <button v-on:click="deleteRequirement(item.id)" class="btn btn-primary ml-2" type="button">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <div class="form-row flex-column">
            <div class="flex-start">
                <h5>Check Lists</h5>
                <button class="btn btn-primary ml-2" type="button">
                    <router-link class="color-white" :to="`/new-check-list/${module.id}`"><i class="fas fa-plus"></i></router-link>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Initial Date</th>
                        <th class="text-left">End Date</th>
                        <th class="text-left">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in module.checkLists" :key="item.name" class="table-item__cursor-pointer">
                        <td v-on:click="viewCheckList(item.id)">{{ item.name }}</td>
                        <td>{{ toDatetimeString(item.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.endDate) }}</td>
                        <td>{{ item.status }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-check-list/${module.id}/${item.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                                <button v-on:click="deleteCheckList(item.id)" class="btn btn-primary ml-2" type="button">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <employees-modal url="module" :users="users" :entity="module" v-show="isModalVisible" @close="closeEmployeesModal"></employees-modal>
    </div>
</template>

<script>
    import axios from 'axios'
    import EmployeesModal from './../users/EmployeesModal.vue'
    import constants from "../../constants"

    export default {

        beforeRouteEnter(to, from, next) {
            return axios
                .get(`${location.origin}/module/${to.params.id}/full`)
                .then(response => {
                    next(vm => vm.setModule(response.data));
                });
        },
        components: { EmployeesModal },
        computed: {
            projects() {
                return this.$store.getters.getProjects;
            },
            profile() {
                return this.$store.getters.getProfile;
            },
            pageInfo() {
                return this.$store.getters.getPageInfo;
            },
        },
        mounted() {
            this.getAllUsers();
        },
        data() {
            return {
                isModalVisible: false,
                module: {
                    name: null,
                    description: null,
                    initialDate: null,
                    endDate: null,
                },
                users: null,
                toDatetimeString: constants.normaliseDateString
            }
        },
        methods: {
            viewRequirement(id) {
                return this.$router.push({ path: `/view-requirement/${id}` });
            },
            viewCheckList(id) {
                return this.$router.push({ path: `/view-check-list/${id}` });
            },
            setModule(module) {
                this.$store.commit('setFullModule', module);
                this.module = module;
            },
            closeEmployeesModal() {
                this.isModalVisible = false;
            },
            openEmployeesModal() {
                this.isModalVisible = true;
            },
            getAllUsers() {
                return axios
                    .get(`${location.origin}/user`)
                    .then(response => {

                        const users = response.data;

                        users.forEach(user => {

                            user.alreadyAssigned = false;
                            this.module.users.forEach(projectUser => {
                                if (projectUser.id === user.id) {
                                    user.alreadyAssigned = true;
                                }
                            })
                        })
                        this.$store.commit('setUsers', users);
                        this.users = users;
                    });
            },
            deleteTestPlan(id) {
                return axios
                    .delete(`${location.origin}/test-plan/${id}`)
                    .then(response => {
                        const deletedIndex = this.module.testPlans.map(item => item.id).indexOf(id);
                        if (deletedIndex > -1) {
                            this.module.testPlans.splice(deletedIndex, 1);
                        }
                    });
            },
            deleteRequirement(id) {
                return axios
                    .delete(`${location.origin}/requirement/${id}`)
                    .then(response => {
                        const deletedIndex = this.module.requirements.map(item => item.id).indexOf(id);
                        if (deletedIndex > -1) {
                            this.module.requirements.splice(deletedIndex, 1);
                        }
                    });
            },
            deleteCheckList(id) {
                return axios
                    .delete(`${location.origin}/check-list/${id}`)
                    .then(response => {
                        const deletedIndex = this.module.checkLists.map(item => item.id).indexOf(id);
                        if (deletedIndex > -1) {
                            this.module.checkLists.splice(deletedIndex, 1);
                        }
                    });
            },
        }
    }
</script>

<style lang="scss">

</style>