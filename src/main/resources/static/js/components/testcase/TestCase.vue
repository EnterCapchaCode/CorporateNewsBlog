<template>
    <div>
        <div class="form-row flex-between">
            <div class="form-group col-md-9 info-block">
                <h2>{{testCase.name}}</h2>
                <label>
                    <textarea readonly>{{testCase.description}}</textarea>
                </label>
                <p>Initial Date: {{toDatetimeString(testCase.initialDate)}}</p>
                <p>End Date: {{toDatetimeString(testCase.endDate)}}</p>
            </div>
            <div class="form-group col-md-3">
                <div class="flex-start">
                    <h5>Employees</h5>
                    <button v-on:click="openEmployeesModal" class="btn btn-primary ml-2" type="button">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <p v-for="user in testCase.users" :key="user.name">{{user.name}}</p>
            </div>
        </div>
        <div class="form-row flex-column margin-bottom-30">
            <div class="flex-start">
                <h5>Tasks</h5>
                <button class="btn btn-primary ml-2" type="button">
                    <router-link class="color-white" :to="`/new-task/${testCase.id}`"><i class="fas fa-plus"></i></router-link>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Initial Date</th>
                        <th class="text-left">End Date</th>
                        <th class="text-left">Type</th>
                        <th class="text-left">Severity</th>
                        <th class="text-left">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in testCase.tasks" :key="item.name" class="table-item__cursor-pointer">
                        <td>{{ item.name }}</td>
                        <td>{{ toDatetimeString(item.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.endDate) }}</td>
                        <td>{{ item.taskType }}</td>
                        <td>{{ item.severity }}</td>
                        <td>{{ item.status }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-task/${testCase.id}/${item.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                                <button v-on:click="deleteTask(item.id)" class="btn btn-primary ml-2" type="button">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <employees-modal url="test-case" :users="users" :entity="testCase" v-show="isModalVisible" @close="closeEmployeesModal"></employees-modal>
    </div>
</template>

<script>
    import axios from 'axios'
    import EmployeesModal from './../users/EmployeesModal.vue'
    import constants from "../../constants";

    export default {

        beforeRouteEnter(to, from, next) {
            return axios
                .get(`${location.origin}/test-case/${to.params.id}/full`)
                .then(response => {
                    next(vm => vm.setEntity(response.data));
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
                testCase: {
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
            setEntity(entity) {
                this.$store.commit('setFullTestCase', entity);
                this.testCase = entity;
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
                            this.testCase.users.forEach(projectUser => {
                                if (projectUser.id === user.id) {
                                    user.alreadyAssigned = true;
                                }
                            })
                        })
                        this.$store.commit('setUsers', users);
                        this.users = users;
                    });
            },
            deleteTask(id) {
                return axios
                    .delete(`${location.origin}/task/${id}`)
                    .then(response => {
                        const deletedIndex = this.testCase.tasks.map(item => item.id).indexOf(id);
                        if (deletedIndex > -1) {
                            this.testCase.tasks.splice(deletedIndex, 1);
                        }
                    });
            },
        }
    }
</script>

<style lang="scss">

</style>