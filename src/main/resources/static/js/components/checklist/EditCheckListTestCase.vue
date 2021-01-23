<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <div class="flex-between">
                    <h2>Update Test Case</h2>
                    <button v-bind:disabled="disableSave"
                            v-on:click="saveEntity()"
                            class="btn btn-primary ml-2" type="button">Save
                    </button>
                </div>
            </v-col>
        </v-row>
        <create-edit-entity-text-field :name-for-update="checkListTestCase !== null ? checkListTestCase.name : null" store-update-method="setName"
                                       entity-name="Name"></create-edit-entity-text-field>
        <create-edit-entity-description :description-for-update="checkListTestCase !== null ? checkListTestCase.description : null"
                                        entity-name="Description"></create-edit-entity-description>
        <create-edit-entity-select-field entity-name="Status"
                                         :pre-selected-value-for-update="checkListTestCase !== null ? checkListTestCase.status : null"
                                         store-update-method="setStatus" :items-for-update="statusValues"></create-edit-entity-select-field>
        <create-edit-entity-select-field entity-name="Order in Check List"
                                         :pre-selected-value-for-update="checkListTestCase !== null ? checkListTestCase.testCaseOrder : null"
                                         store-update-method="setOrder" :items-for-update="availableOrders">
        </create-edit-entity-select-field>

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
        computed: {
        },
        mounted() {
            if (this.$attrs.id && this.$attrs.parentId) {
                this.getEntityForEdit()
            }
            this.statusValues = constants.STATUS_VALUES;
            this.availableOrders = this.$store.getters.getAvailableOrders;
        },
        watch: {},
        data() {
            return {
                statusValues: null,
                availableOrders: null,
                disableSave: true,
                checkListTestCase: null
            }
        },
        methods: {
            getEntityForEdit() {
                return axios.get(`${location.origin}/check-list/${this.$attrs.parentId}/test-case/${this.$attrs.id}`)
                    .then(response => this.checkListTestCase = response.data)
                    .catch(err => {
                        debugger
                        console.log(err);
                    });
            },
            checkForDisable() {
                this.disableSave =
                    this.$store.getters.getName === null
                    || this.$store.getters.getDescription === null
                    || this.$store.getters.getStatus === null
                    || this.$store.getters.getOrder === null;
            },
            saveEntity() {

                const needUpdateAction = this.checkListTestCase !== null;

                const dtoForRequest = constants.getCheckListTestCaseDTO({
                    id: needUpdateAction ? this.checkListTestCase.id : null,
                    status: needUpdateAction ? this.$store.getters.getStatus : 'TO_DO',
                    name: this.$store.getters.getName,
                    description: this.$store.getters.getDescription,
                    testCaseOrder: this.$store.getters.getOrder,
                });

                return axios({
                    method: needUpdateAction ? 'put' : 'post',
                    url: `${location.origin}/check-list/${this.$attrs.parentId}/test-case/${this.$attrs.id}`,
                    data: dtoForRequest
                })
                    .then(response => {
                        if (response.status === 200) {
                            this.$router.push({path: `/view-check-list/${this.$attrs.parentId}`})
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