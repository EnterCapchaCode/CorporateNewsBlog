<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>{{ checkList !== null ? 'Update' : 'Create new' }} Check List</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveEntity()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="checkList !== null ? checkList.name : null" store-update-method="setName"
                                       entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="checkList !== null ? checkList.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <create-edit-entity-select-field entity-name="Status" :pre-selected-value-for-update="checkList !== null ? checkList.status : null"
                                         store-update-method="setStatus" :items-for-update="statusValues"></create-edit-entity-select-field>
        <from-to-fields :from-date-for-update="checkList !== null ? checkList.initialDate.substring(0, 10) : null"
                        :to-date-for-update="checkList !== null ? checkList.endDate.substring(0, 10) : null"></from-to-fields>

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
                checkList: null
            }
        },
        methods: {
            getEntityForEdit() {
                return axios.get(`${location.origin}/check-list/${this.$attrs.id}`)
                    .then(response => this.checkList = response.data)
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
                    || this.$store.getters.getStatus === null;
            },
            saveEntity() {

                const needUpdateAction = this.checkList !== null;

                const dtoForRequest = constants.getCheckListDTO({
                    id: needUpdateAction ? this.checkList.id : null,
                    status: needUpdateAction ? this.$store.getters.getStatus : 'TO_DO',
                    name: this.$store.getters.getName,
                    description: this.$store.getters.getDescription,
                    initialDate: this.$store.getters.getInitialDate,
                    endDate: this.$store.getters.getEndDate,
                    moduleId: this.$attrs.parentId
                });

                return axios({
                    method: needUpdateAction ? 'put' : 'post',
                    url: `${location.origin}/check-list`,
                    data: dtoForRequest
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