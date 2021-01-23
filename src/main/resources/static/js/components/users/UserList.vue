<template>
    <div>
        <v-simple-table>
            <template v-slot:default>
                <thead>
                <tr>
                    <th class="text-left">Name</th>
                    <th class="text-left">Email</th>
                    <th class="text-left">Last Visit</th>
                    <th class="text-left">Admin</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in users" :key="item.name" class="table-item__cursor-pointer">
                    <td>{{ item.name }}</td>
                    <td>{{ item.email }}</td>
                    <td>{{ item.lastVisit }}</td>
                    <td>{{ item.role === "ADMIN" ? '+' : '-' }}</td>
                </tr>
                </tbody>
            </template>
        </v-simple-table>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {

        beforeRouteEnter(to, from, next) {
            return axios
                .get(`${location.origin}/user/`)
                .then(response => {
                    next(vm => vm.setEntity(response.data));
                });
        },
        components: {  },
        computed: {
        },
        mounted() {
        },
        data() {
            return {
                users: [],
            }
        },
        methods: {
            setEntity(entity) {
                this.$store.commit('setUsers', entity);
                this.users = entity;
            }
        }
    }
</script>

<style lang="scss">

</style>
