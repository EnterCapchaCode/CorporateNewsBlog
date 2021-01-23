<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>{{ testCase !== null ? 'Update' : 'Create new' }} Test Case</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveEntity()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="testCase !== null ? testCase.name : null" store-update-method="setName"
                                       entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="testCase !== null ? testCase.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <create-edit-entity-text-field :name-for-update="testCase !== null ? testCase.name : null" store-update-method="setTestCase"
                                       entity-name="Test Case"></create-edit-entity-text-field>
        <create-edit-entity-select-field entity-name="Status" :pre-selected-value-for-update="testCase !== null ? testCase.status : null"
                                         store-update-method="setStatus" :items-for-update="statusValues"></create-edit-entity-select-field>
        <from-to-fields :from-date-for-update="testCase !== null ? testCase.initialDate.substring(0, 10) : null"
                        :to-date-for-update="testCase !== null ? testCase.endDate.substring(0, 10) : null"></from-to-fields>

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
                this.getEntityForEdit()
            }
            this.statusValues = constants.STATUS_VALUES;
        },
        watch: {},
        data() {
            return {
                statusValues: null,
                disableSave: true,
                testCase: null
            }
        },
        methods: {
            getEntityForEdit() {
                return axios.get(`${location.origin}/test-case/${this.$attrs.id}`)
                    .then(response => this.testCase = response.data)
                    .catch(err => {
                        debugger
                        console.log(err);
                    });
            },
            checkForDisable() {
                this.disableSave =
                    this.$store.getters.getName === null
                    || this.$store.getters.getDescription === null
                    || this.$store.getters.getTestCase === null
                    || this.$store.getters.getInitialDate === null
                    || this.$store.getters.getEndDate === null
                    || this.$store.getters.getStatus === null;
            },
            saveEntity() {

                const needUpdateAction = this.testCase !== null;

                const dtoForRequest = constants.getTestCaseDTO({
                    id: needUpdateAction ? this.testCase.id : null,
                    status: needUpdateAction ? this.$store.getters.getStatus : 'TO_DO',
                    name: this.$store.getters.getName,
                    description: this.$store.getters.getDescription,
                    testCase: this.$store.getters.getTestCase,
                    initialDate: this.$store.getters.getInitialDate,
                    endDate: this.$store.getters.getEndDate,
                    requirementId: this.$attrs.parentId
                });

                return axios({
                    method: needUpdateAction ? 'put' : 'post',
                    url: `${location.origin}/test-case`,
                    data: dtoForRequest
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({path: `/view-requirement/${this.$attrs.parentId}`})
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