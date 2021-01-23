<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <v-menu

                        v-model="fromDateMenu"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        max-width="290px"
                        min-width="290px"
                >
                    <template v-slot:activator="{ on }">
                        <v-text-field
                                label="Initial date"
                                readonly
                                :value="fromDate"
                                v-on="on"
                        ></v-text-field>
                    </template>
                    <v-date-picker
                            locale="en-in"
                            v-model="fromDate"
                            no-title
                            @input="fromDateMenu = false"
                            :min="minDateFromPicker"
                            :max="maxDateFromPicker()"
                    ></v-date-picker>
                </v-menu>
            </v-col>
        </v-row>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <v-menu

                        v-model="toDateMenu"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        max-width="290px"
                        min-width="290px"
                >
                    <template v-slot:activator="{ on }">
                        <v-text-field
                                label="End date"
                                readonly
                                :value="toDate"
                                v-on="on"
                        ></v-text-field>
                    </template>
                    <v-date-picker
                            locale="en-in"
                            v-model="toDate"
                            no-title
                            @input="toDateMenu = false"
                            :min="minDateToPicker()"
                    ></v-date-picker>
                </v-menu>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    export default {
        props: [ 'fromDateForUpdate', 'toDateForUpdate' ],
        components: {},
        computed: {
        },
        mounted() {
        },
        data() {
            return {
                fromDateMenu: false,
                toDateMenu: false,
                fromDate: null,
                toDate: null,
                minDateFromPicker: new Date().toISOString().substr(0, 10),
                minDateToPicker: () => this.fromDate,
                maxDateFromPicker: () => this.toDate,
            }
        },
        watch: {
            fromDate: function (to, from) {
                this.$store.commit('setInitialDate', to);
                this.$parent.checkForDisable();
            },
            toDate: function (to, from) {
                this.$store.commit('setEndDate', to);
                this.$parent.checkForDisable();
            },
            fromDateForUpdate: function (to, from) {
                this.fromDate = to;
            },
            toDateForUpdate: function (to, from) {
                this.toDate = to;
            }
        },
        methods: {
        }
    }
</script>

<style lang="scss">
</style>