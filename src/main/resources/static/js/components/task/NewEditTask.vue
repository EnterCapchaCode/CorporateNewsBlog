<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>{{ task !== null ? 'Update' : 'Create new' }} Task</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveEntity()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="task !== null ? task.name : null" store-update-method="setName"
                                       entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="task !== null ? task.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <create-edit-entity-select-field entity-name="Status" :pre-selected-value-for-update="task !== null ? task.status : null"
                                         store-update-method="setStatus" :items-for-update="statusValues"></create-edit-entity-select-field>
        <create-edit-entity-select-field entity-name="Type" :pre-selected-value-for-update="task !== null ? task.taskType : null"
                                         store-update-method="setTaskType" :items-for-update="taskTypeValues"></create-edit-entity-select-field>
        <create-edit-entity-select-field entity-name="Severity" :pre-selected-value-for-update="task !== null ? task.severity : null"
                                         store-update-method="setTaskSeverity" :items-for-update="taskSeverityValues"></create-edit-entity-select-field>
        <from-to-fields :from-date-for-update="task !== null ? task.initialDate.substring(0, 10) : null"
                        :to-date-for-update="task !== null ? task.endDate.substring(0, 10) : null"></from-to-fields>

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
            this.taskTypeValues = constants.TASK_TYPE_VALUES;
            this.taskSeverityValues = constants.TASK_SEVERITY_VALUES;
        },
        watch: {},
        data() {
            return {
                statusValues: null,
                taskTypeValues: null,
                taskSeverityValues: null,
                disableSave: true,
                task: null
            }
        },
        methods: {
            getEntityForEdit() {
                return axios.get(`${location.origin}/task/${this.$attrs.id}`)
                    .then(response => this.task = response.data)
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
                    || this.$store.getters.getTaskType === null
                    || this.$store.getters.getTaskSeverity === null;
            },
            saveEntity() {

                const needUpdateAction = this.task !== null;

                const dtoForRequest = constants.getTaskDTO({
                    id: needUpdateAction ? this.task.id : null,
                    status: needUpdateAction ? this.$store.getters.getStatus : 'TO_DO',
                    name: this.$store.getters.getName,
                    taskType: this.$store.getters.getTaskType,
                    severity: this.$store.getters.getTaskSeverity,
                    description: this.$store.getters.getDescription,
                    task: this.$store.getters.getTestCase,
                    initialDate: this.$store.getters.getInitialDate,
                    endDate: this.$store.getters.getEndDate,
                    testCaseId: this.$attrs.parentId
                });

                return axios({
                    method: needUpdateAction ? 'put' : 'post',
                    url: `${location.origin}/task`,
                    data: dtoForRequest
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({path: `/view-test-case/${this.$attrs.parentId}`})
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