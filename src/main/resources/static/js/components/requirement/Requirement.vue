<template>
    <div>
        <div class="form-row flex-between">
            <div class="form-group col-md-9 info-block">
                <h2>{{requirement.name}}</h2>
                <label>
                    <textarea readonly>{{requirement.description}}</textarea>
                </label>
                <p>Initial Date: {{toDatetimeString(requirement.initialDate)}}</p>
                <p>End Date: {{toDatetimeString(requirement.endDate)}}</p>
            </div>
            <div class="form-group col-md-3">
                <div class="flex-start">
                    <h5>Employees</h5>
                    <button v-on:click="openEmployeesModal" class="btn btn-primary ml-2" type="button">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <p v-for="user in requirement.users" :key="user.name">{{user.name}}</p>
            </div>
        </div>
        <div class="form-row flex-column margin-bottom-30">
            <div class="flex-start">
                <h5>Test Cases</h5>
                <button class="btn btn-primary ml-2" type="button">
                    <router-link class="color-white" :to="`/new-test-case/${requirement.id}`"><i class="fas fa-plus"></i></router-link>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Test Case</th>
                        <th class="text-left">Initial Date</th>
                        <th class="text-left">End Date</th>
                        <th class="text-left">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in requirement.testCases" :key="item.name" class="table-item__cursor-pointer">
                        <td v-on:click="viewTestCase(item.id)">{{ item.name }}</td>
                        <td>{{ item.testCase }}</td>
                        <td>{{ toDatetimeString(item.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.endDate) }}</td>
                        <td>{{ item.status }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-test-case/${requirement.id}/${item.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                                <button v-on:click="deleteTestCase(item.id)" class="btn btn-primary ml-2" type="button">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <employees-modal url="requirement" :users="users" :entity="requirement" v-show="isModalVisible" @close="closeEmployeesModal"></employees-modal>
    </div>
</template>

<script>
    import axios from 'axios'
    import EmployeesModal from './../users/EmployeesModal.vue'
    import constants from "../../constants";

    export default {

        beforeRouteEnter(to, from, next) {
            return axios
                .get(`${location.origin}/requirement/${to.params.id}/full`)
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
                requirement: {
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
            viewTestCase(id) {
                return this.$router.push({ path: `/view-test-case/${id}` });
            },
            setEntity(entity) {
                this.$store.commit('setFullRequirement', entity);
                this.requirement = entity;
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
                            this.requirement.users.forEach(projectUser => {
                                if (projectUser.id === user.id) {
                                    user.alreadyAssigned = true;
                                }
                            })
                        })
                        this.$store.commit('setUsers', users);
                        this.users = users;
                    });
            },
            deleteTestCase(id) {
                return axios
                    .delete(`${location.origin}/test-case/${id}`)
                    .then(response => {
                        const deletedIndex = this.requirement.testCases.map(item => item.id).indexOf(id);
                        if (deletedIndex > -1) {
                            this.requirement.testCases.splice(deletedIndex, 1);
                        }
                    });
            },
        }
    }
</script>

<style lang="scss">

</style>