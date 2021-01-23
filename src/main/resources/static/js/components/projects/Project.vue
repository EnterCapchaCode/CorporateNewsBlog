<template>
    <div>
        <div class="form-row flex-between">
            <div class="form-group col-md-9 info-block">
                <h2>{{project.name}}</h2>
                <label>
                    <textarea readonly>{{project.description}}</textarea>
                </label>
                <p>Initial Date: {{toDatetimeString(project.initialDate)}}</p>
                <p>End Date: {{toDatetimeString(project.endDate)}}</p>
            </div>
            <div class="form-group col-md-3">
                <div class="flex-start">
                    <h5>Employees</h5>
                    <button v-on:click="openEmployeesModal" class="btn btn-primary ml-2" type="button">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <p v-for="user in project.users" :key="user.name">{{user.name}}</p>
            </div>
        </div>
        <div class="form-row flex-column">
            <div class="flex-start">
                <h5>Modules</h5>
                <button class="btn btn-primary ml-2" type="button">
                    <router-link class="color-white" :to="`/new-module/${project.id}`"><i class="fas fa-plus"></i></router-link>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Initial Date</th>
                        <th class="text-left">End Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in project.modules" :key="item.name" class="table-item__cursor-pointer">
                        <td v-on:click="viewModule(item.id)">{{ item.name }}</td>
                        <td>{{ toDatetimeString(item.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.endDate) }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-module/${project.id}/${item.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                                <button v-on:click="deleteModule(item.id)" class="btn btn-primary ml-2" type="button">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <employees-modal url="project" :users="users" :entity="project" v-show="isModalVisible" @close="closeEmployeesModal"></employees-modal>
    </div>
</template>

<script>
    import axios from 'axios'
    import EmployeesModal from './../users/EmployeesModal.vue'
    import constants from "../../constants";

    export default {

        beforeRouteEnter(to, from, next) {
            return axios
                .get(`${location.origin}/project/${to.params.id}/full`)
                .then(response => {
                    next(vm => vm.setProject(response.data));
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
                project: {
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
            viewModule(id) {
                return this.$router.push({ path: `/view-module/${id}` });
            },
            setProject(project) {
                this.$store.commit('setFullProject', project);
                this.project = project;
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
                            this.project.users.forEach(projectUser => {
                                if (projectUser.id === user.id) {
                                    user.alreadyAssigned = true;
                                }
                            })
                        })
                        this.$store.commit('setUsers', users);
                        this.users = users;
                    });
            },
            deleteModule(id) {
                return axios
                    .delete(`${location.origin}/module/${id}`)
                    .then(response => {
                        const deletedIndex = this.project.modules.map(item => item.id).indexOf(id);
                        if (deletedIndex > -1) {
                            this.project.modules.splice(deletedIndex, 1);
                        }
                    });
            },
        }
    }
</script>

<style lang="scss">

</style>