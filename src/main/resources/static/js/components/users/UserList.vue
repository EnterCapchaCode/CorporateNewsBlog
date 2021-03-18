<template>
  <v-card>
    <v-card-title>
      Сотрудники
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Поиск..."
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :items-per-page="users.length"
        hide-default-footer
        :headers="headers"
        :items="users"
        :search="search"
        sort-by="surname"
    >
      <template v-slot:item.actions="{ item }">
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-icon
                v-if="item.role !== 'READER'"
                small
                class="mr-2"
                disabled
            >
              mdi-crown
            </v-icon>
            <v-icon
                v-else
                v-bind="attrs"
                v-on="on"
                small
                class="mr-2"
                @click="setCreatorRole(item)"
            >
              mdi-crown
            </v-icon>
          </template>
          <span>Повысить роль до CREATOR</span>
        </v-tooltip>
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-icon
                v-bind="attrs"
                v-on="on"
                small
                @click="dropEmployee(item)"
            >
              mdi-delete
            </v-icon>
          </template>
          <span>Удалить пользователя</span>
        </v-tooltip>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
import axios from 'axios'

export default {
  props: ['users', 'profile', 'pageInfo'],
  data() {
    return {
      user: this.user,
      search: '',
      headers: [
        {text: 'Фамилия', value: 'surname'},
        {text: 'Имя', value: 'name'},
        {text: 'Почта', value: 'login'},
        {text: 'Роль', value: 'role'},
        {text: 'Действия', value: 'actions', sortable: false},
      ],
    }
  },
  methods: {
    dropEmployee(item) {
      fetch(`${location.origin}/employees/${item.id}`, {
        method: 'delete'
      })
          .then(res => {
            if (res.apierror) {
              this.$toasted.error(` ${res.apierror.user}: ${res.apierror.status}!`, {
                theme: "bubble",
                position: "top-center",
                duration: 3000,
                icon: "error_outline"
              });
            } else {
              const index = this.users.indexOf(item);
              if (index > -1) {
                this.users.splice(index, 1);
                this.$store.commit('setUsers', this.users);
              }
              this.snackbar = true
              this.timeout = 2000
            }
          })
          .catch(err => {
            debugger;
            console.log(err);
          });
    },
    getAllEmployees() {
      return axios
          .get(`${location.origin}/employees`)
          .then(response => {
            const users = response.data;
            this.$store.commit('setUsers', users);
            this.users = users;
          });
    },
    setCreatorRole(item) {
      fetch(`${location.origin}/employees/setCreator/${item.id}`, {
        method: 'put'
      })
          .then(res => {
            if (res.apierror) {
              this.$toasted.error(` ${res.apierror.user}: ${res.apierror.status}!`, {
                theme: "bubble",
                position: "top-center",
                duration: 3000,
                icon: "error_outline"
              });
            } else {
              const index = this.users.indexOf(item);
              const updated = this.users[index];
              updated.role = 'CREATOR'
              if (index > -1) {
                this.users.splice(index, 1, updated);
                this.$store.commit('setUsers', this.users);
              }
              this.snackbar = true
              this.timeout = 2000
            }
          })
          .catch(err => {
            debugger;
            console.log(err);
          });
    },
  },
  beforeMount() {
    this.getAllEmployees()
  }
}
</script>

<style lang="scss">

</style>
