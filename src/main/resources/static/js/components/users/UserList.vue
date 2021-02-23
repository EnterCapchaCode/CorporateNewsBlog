<template>
  <div>
    <p>dfrfg4fe</p>
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
        <tr v-for="user in users" :key="user.name" class="table-item__cursor-pointer">
          <td>{{ user.name }}</td>
          <td>{{ user.surname }}</td>
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
  props: ['users'],
  data() {
    return {
      user: this.user,
    }
  },

  beforeRouteEnter(to, from, next) {
    return axios
        .get(`${location.origin}/user/`)
        .then(response => {
          next(vm => vm.setEntity(response.data));
        });
  },
}
</script>

<style lang="scss">

</style>
