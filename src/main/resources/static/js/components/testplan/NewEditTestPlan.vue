<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>{{ testPlan !== null ? 'Update' : 'Create new' }} Test Plan</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveEntity()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="testPlan !== null ? testPlan.name : null" store-update-method="setName"
                                 entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="testPlan !== null ? testPlan.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <create-edit-entity-text-field :name-for-update="testPlan !== null ? testPlan.testMethod : null" store-update-method="setTestMethod"
                                       entity-name="Method"></create-edit-entity-text-field>
        <create-edit-entity-select-field entity-name="Status" :pre-selected-value-for-update="testPlan !== null ? testPlan.status : null"
                                         store-update-method="setStatus" :items-for-update="statusValues"></create-edit-entity-select-field>
        <from-to-fields :from-date-for-update="testPlan !== null ? testPlan.initialDate.substring(0, 10) : null"
                        :to-date-for-update="testPlan !== null ? testPlan.endDate.substring(0, 10) : null"></from-to-fields>

    </div>
</template>

<script>
    import axios from 'axios'
    import FromToFields from "../common/FromToFields.vue";
    import CreateEditEntityTextField from "../common/CreateEditEntityTextField.vue";
    import CreateEditEntityDescription from "../common/CreateEditEntityDescription.vue";
    import CreateEditEntitySelectField from "../common/CreateEditEntitySelectField.vue";
    import constants from "../../constants";

    export default {
        components: {FromToFields, CreateEditEntityTextField, CreateEditEntityDescription, CreateEditEntitySelectField},
        computed: {},
        mounted() {
            if (this.$attrs.id) {
                this.getTestPlanForEdit()
            }
            this.statusValues = constants.STATUS_VALUES;
        },
        watch: {},
        data() {
            return {
                statusValues: null,
                disableSave: true,
                testPlan: null
            }
        },
        methods: {
            getTestPlanForEdit() {
                return axios.get(`${location.origin}/test-plan/${this.$attrs.id}`)
                    .then(response => this.testPlan = response.data)
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
                    || this.$store.getters.getEndDate === null
                    || this.$store.getters.getStatus === null
                    || this.$store.getters.getTestMethod === null;
            },
            saveEntity() {

                const updateTestPlan = this.testPlan !== null;

                const projectDTO = constants.getTestPlanDTO({
                    id: updateTestPlan ? this.testPlan.id : null,
                    status: updateTestPlan ? this.$store.getters.getStatus : 'TO_DO',
                    name: this.$store.getters.getName,
                    description: this.$store.getters.getDescription,
                    initialDate: this.$store.getters.getInitialDate,
                    endDate: this.$store.getters.getEndDate,
                    testMethod: this.$store.getters.getTestMethod,
                    moduleId: this.$attrs.parentId
                });

                return axios({
                    method: updateTestPlan ? 'put' : 'post',
                    url: `${location.origin}/test-plan`,
                    data: projectDTO
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({path: `/view-module/${this.$attrs.parentId}`})
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