<template>
    <div>
        <div class="form-row flex-between">
            <div class="form-group col-md-9">
                <h2>{{checkList.name}}</h2>
                <p>{{checkList.description}}</p>
                <p>Initial Date: {{ toDatetimeString(checkList.initialDate) }}</p>
                <p>End Date: {{ toDatetimeString(checkList.endDate) }}</p>
            </div>
            <div class="form-group col-md-3">
                <div class="flex-start">
                    <h5>Employees</h5>
                    <button v-on:click="openEmployeesModal" class="btn btn-primary ml-2" type="button">
                        <i class="fas fa-plus"></i>
                    </button>
                </div>
                <p v-for="user in checkList.users" :key="user.name">{{user.name}}</p>
            </div>
        </div>
        <div class="form-row flex-column margin-bottom-30">
            <div class="flex-start">
                <h5>Assigned Test Cases</h5>
                <button class="btn btn-primary ml-2" type="button" v-on:click="openTestCasesModal">
                    <i class="fas fa-plus"></i>
                </button>
            </div>
            <v-simple-table>
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">Name</th>
                        <th class="text-left">Order</th>
                        <th class="text-left">Status</th>
                        <th class="text-left">Test Case Name</th>
                        <th class="text-left">Test Case Initial Date</th>
                        <th class="text-left">Test Case End Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in checkList.checkListTestCases" :key="item.testCaseOrder" class="table-item__cursor-pointer">
                        <td>{{ item.name }}</td>
                        <td>{{ item.testCaseOrder }}</td>
                        <td>{{ item.status }}</td>
                        <td>{{ item.testCase.name }}</td>
                        <td>{{ toDatetimeString(item.testCase.initialDate) }}</td>
                        <td>{{ toDatetimeString(item.testCase.endDate) }}</td>
                        <td>
                            <div class="flex-end">
                                <button class="btn btn-primary ml-2" type="button">
                                    <router-link class="color-white" :to="`/edit-check-list-test-case/${checkList.id}/${item.testCase.id}`">
                                        <i class="fas fa-pencil-alt"></i>
                                    </router-link>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </div>
        <employees-modal url="check-list" :users="users" :entity="checkList" v-show="isUsersModalVisible" @close="closeEmployeesModal"></employees-modal>
        <available-test-cases-modal url="check-list" :listed-entities="availableTestCases" :entity="checkList" v-show="isTestCasesModalVisible" @close="closeTestCasesModal"></available-test-cases-modal>
    </div>
</template>

<script>
    import axios from 'axios'
    import EmployeesModal from './../users/EmployeesModal.vue'
    import AvailableTestCasesModal from './../checklist/AvailableTestCasesModal.vue'
    import constants from "../../constants";

    export default {

        beforeRouteEnter(to, from, next) {
            return axios
                .get(`${location.origin}/check-list/${to.params.id}/full`)
                .then(response => {
                    next(vm => vm.setEntity(response.data));
                });
        },
        components: { EmployeesModal, AvailableTestCasesModal },
        computed: {
            profile() {
                return this.$store.getters.getProfile;
            },
        },
        mounted() {
            this.getAllUsers();
            this.getAvailableTestCases();
        },
        data() {
            return {
                isUsersModalVisible: false,
                isTestCasesModalVisible: false,
                checkList: {
                    name: null,
                    description: null,
                    initialDate: null,
                    endDate: null,
                },
                users: null,
                availableTestCases: null,
                toDatetimeString: constants.normaliseDateString
            }
        },
        methods: {
            setEntity(entity) {
                this.$store.commit('setFullCheckList', entity);
                this.checkList = entity;
                this.checkList.checkListTestCases.sort((a, b) => a.testCaseOrder - b.testCaseOrder)

                const availableOrders = this.checkList.checkListTestCases.map(item => item.testCaseOrder);
                this.$store.commit('setAvailableOrders', availableOrders);
            },
            closeEmployeesModal() {
                this.isUsersModalVisible = false;
            },
            openEmployeesModal() {
                this.isUsersModalVisible = true;
            },
            closeTestCasesModal() {
                this.isTestCasesModalVisible = false;
            },
            openTestCasesModal() {
                this.isTestCasesModalVisible = true;
            },
            getAllUsers() {
                return axios
                    .get(`${location.origin}/user`)
                    .then(response => {

                        const users = response.data;

                        users.forEach(user => {

                            user.alreadyAssigned = false;
                            this.checkList.users.forEach(projectUser => {
                                if (projectUser.id === user.id) {
                                    user.alreadyAssigned = true;
                                }
                            })
                        })
                        this.$store.commit('setUsers', users);
                        this.users = users;
                    });
            },
            getAvailableTestCases() {
                return axios
                    .get(`${location.origin}/check-list/${this.$attrs.id}/available-test-cases`)
                    .then(response => {

                        const availableTestCaseCheckLists = response.data;

                        availableTestCaseCheckLists.forEach(tccl => {

                            tccl.alreadyAssigned = false;
                            this.checkList.checkListTestCases.forEach(item => {
                                if (item.testCase.id === tccl.id) {
                                    tccl.alreadyAssigned = true;
                                }
                            })
                        })
                        this.$store.commit('setAvailableTestCases', response.data);
                        this.availableTestCases = response.data;
                    });
            },
        }
    }
</script>

<style lang="scss">

</style>