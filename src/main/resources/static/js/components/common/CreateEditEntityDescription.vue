<template>
    <div>
        <v-row class="ma-1">
            <v-col cols="12" md="6">
                <v-textarea
                        outlined
                        name="input-7-4"
                        :label="entityName"
                        :rules="rules"
                        v-model="descriptionValue"
                ></v-textarea>
            </v-col>
        </v-row>
    </div>
</template>

<script>
    import constants from "../../constants";
    export default {
        props: [ 'entityName', 'descriptionForUpdate' ],
        components: {},
        computed: {
        },
        mounted() {
        },
        data() {
            return {
                descriptionValue: null,
                rules: [
                    value => !!value || constants.REQUIRED_FIELD,
                    value => (value && value.length >= 100) || constants.MIN_100_CHARS_FIELD,
                ]
            }
        },
        watch: {
            descriptionValue: function (to, from) {
                for (let i = 0; i < this.rules.length; i++) {
                    if (this.rules[i](to) !== true) {
                        this.$store.commit('setDescription', null);
                        this.$parent.checkForDisable();
                        return;
                    }
                }
                this.$store.commit('setDescription', to);
                this.$parent.checkForDisable();
            },
            descriptionForUpdate: function (to, from) {
                this.descriptionValue = to;
            }
        },
        methods: {
        }
    }
</script>

<style lang="scss">
</style>