<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>{{ module !== null ? 'Update' : 'Create new' }} Module</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveModule()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="module !== null ? module.name : null" store-update-method="setName"
                                 entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="module !== null ? module.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <from-to-fields :from-date-for-update="module !== null ? module.initialDate.substring(0, 10) : null"
                        :to-date-for-update="module !== null ? module.endDate.substring(0, 10) : null" ></from-to-fields>

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
                this.getModuleForEdit()
            }
        },
        watch: {},
        data() {
            return {
                disableSave: true,
                module: null
            }
        },
        methods: {
            getModuleForEdit() {
                return axios.get(`${location.origin}/module/${this.$attrs.id}`)
                    .then(response => this.module = response.data)
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
            saveModule() {

                const updatedModule = this.module !== null;

                const projectDTO = constants.getModuleDTO({
                        id: updatedModule ? this.module.id : null,
                        status: updatedModule ? this.module.status : 'TO_DO',
                        name: this.$store.getters.getName,
                        description: this.$store.getters.getDescription,
                        initialDate: this.$store.getters.getInitialDate,
                        endDate: this.$store.getters.getEndDate,
                        projectId: this.$attrs.projectId});

                return axios({
                    method: updatedModule ? 'put' : 'post',
                    url: `${location.origin}/module`,
                    data: projectDTO
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({path: `/view-project/${this.$attrs.projectId}`})
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