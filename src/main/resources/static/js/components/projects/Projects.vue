<template>
    <div>
        <div class="form-row flex-between">
            <div class="form-group col-md-6">
                <form class="form-inline">
                    <input type="text" name="filter" placeholder="Поиск"  v-model="filter" />
                    <button v-on:click="getFilteredProjects()" class="btn btn-primary ml-2" type="button">Search</button>
                </form>
            </div>
            <div class="form-group col-md-6">
                <div class="flex-end">
                    <form class="form-inline">
                        <button class="btn btn-primary ml-2" type="button">
                            <router-link class="color-white" :to="`/new-project`">Create new project</router-link>
                        </button>
                    </form>
                </div>
            </div>
        </div>

        <v-simple-table>
            <template v-slot:default>
                <thead>
                <tr>
                    <th class="text-left">Name</th>
                    <th class="text-left">Initial Date</th>
                    <th class="text-left">End Date</th>
                    <th class="text-left">Modules count</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in projects" :key="item.name" class="table-item__cursor-pointer">
                    <td v-on:click="viewProject(item.id)">{{ item.name }}</td>
                    <td>{{ toDatetimeString(item.initialDate) }}</td>
                    <td>{{ toDatetimeString(item.endDate) }}</td>
                    <td>
                        <div class="flex-between">
                            <p>{{ item.modules.length }}</p>
                            <router-link class="edit-link-pencil"
                                         :to="{ path: `/edit-project/${item.id}` }"
                                         tag="img" src="/static/assets/edit.svg"></router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </template>
        </v-simple-table>
    </div>
</template>

<script>
    import axios from 'axios'
    import constants from "../../constants";

    export default {
        components: {  },
        computed: {
            projects() {
                return this.$store.getters.getProjects;
            },
            profile() {
                return this.$store.getters.getProfile;
            },
            pageInfo() {
                return this.$store.getters.getPageInfo;
            }
        },
        mounted() {
            this.fetchProjects();
        },
        data() {
            return {
                filter: "",
                toDatetimeString: constants.normaliseDateString
            }
        },
        methods: {
            getFilteredProjects() {
                if (this.filter !== "") {
                    this.$store.commit('setProjectFilter', this.filter);
                }
                return axios
                    .get(`${location.origin}/project?page=${this.pageInfo.currentPage}&filter=${this.filter}`)
                    .then(response => {
                        this.$store.commit('setProjects', response.data.projects);
                        this.$store.commit('setPageInfo', response.data.page);
                    })
                    .catch(err => {
                        debugger
                        console.log(err);
                    });
            },
            fetchProjects() {
                return axios
                    .get(`${location.origin}/project?page=${this.pageInfo.currentPage}`)
                    .then(response => {
                        this.$store.commit('setProjects', response.data.projects);
                        this.projects = response.data.projects;
                    });
            },
            viewProject(id) {
                return this.$router.push({ path: `/view-project/${id}` });
            }
        }
    }
</script>

<style lang="scss">

</style>