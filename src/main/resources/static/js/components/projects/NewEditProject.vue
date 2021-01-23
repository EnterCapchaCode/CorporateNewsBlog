<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>{{ project !== null ? 'Update' : 'Create new' }} Project</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveProject()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="project !== null ? project.name : null" store-update-method="setName"
                                 entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="project !== null ? project.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <from-to-fields :from-date-for-update="project !== null ? project.initialDate.substring(0, 10) : null"
                        :to-date-for-update="project !== null ? project.endDate.substring(0, 10) : null" ></from-to-fields>

    </div>
</template>

<script>
    import axios from 'axios'
    import FromToFields from "../common/FromToFields.vue";
    import CreateEditEntityTextField from "../common/CreateEditEntityTextField.vue";
    import CreateEditEntityDescription from "../common/CreateEditEntityDescription.vue";
    import constants from "../../constants";

    export default {
        components: {FromToFields, CreateEditEntityTextField, CreateEditEntityDescription},
        computed: {
            profile() {
                return this.$store.getters.getProfile;
            },
        },
        mounted() {
            if (this.$attrs.id) {
                this.getProjectForEdit()
            }
        },
        watch: {},
        data() {
            return {
                disableSave: true,
                project: null
            }
        },
        methods: {
            getProjectForEdit() {
                return axios.get(`${location.origin}/project/${this.$attrs.id}`)
                    .then(response => this.project = response.data)
                    .catch(err => {
                        debugger
                        console.log(err);
                    });
            },
            checkForDisable() {
                this.disableSave =
                    this.$store.getters.getName === null
                    || this.$store.getters.getDescription === null
                    || this.$store.getters.getInitialDate === null
                    || this.$store.getters.getEndDate === null;
            },
            saveProject() {

                const updateProject = this.project !== null;

                const projectDTO = constants.getProjectDTO({
                        id: updateProject ? this.project.id : null,
                        status: updateProject ? this.project.status : 'TO_DO',
                        name: this.$store.getters.getName,
                        description: this.$store.getters.getDescription,
                        initialDate: this.$store.getters.getInitialDate,
                        endDate: this.$store.getters.getEndDate});

                const userId = this.profile.id;
                return axios({
                    method: updateProject ? 'put' : 'post',
                    url: `${location.origin}/project`,
                    data: projectDTO
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({path: `/projects/${userId}`})
                        }
                    })
                    .catch(err => {
                        debugger
                        console.log(err);
                    });
            },
        }
    }
</script>

<style lang="scss">
</style>