<template>
    <div>
        <v-row class="ma-1">
            <v-col class="d-flex" cols="12" sm="6">
                <v-select
                        :items="items"
                        :label="entityName"
                        v-model="selectedValue"
                ></v-select>
            </v-col>
        </v-row>
    </div>
</template>

<script>

    export default {
        props: [ 'entityName', 'preSelectedValueForUpdate', 'itemsForUpdate', 'storeUpdateMethod' ],
        components: {},
        computed: {
        },
        mounted() {
        },
        data() {
            return {
                items: [],
                selectedValue: null
            }
        },
        watch: {
            selectedValue: function (to, from) {
                this.$store.commit(this.storeUpdateMethod, to);
                this.$parent.checkForDisable();
            },
            preSelectedValueForUpdate: function (to, from) {
                this.selectedValue = to;
            },
            itemsForUpdate: function (to, from) {
                this.items = to;
            }
        },
        methods: {
        }
    }
</script>

<style lang="scss">
</style>