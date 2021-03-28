<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light ">
    <a class="navbar-brand" href="/">Softeq News Blog</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link v-if="profile && profile.role === 'ADMIN'" class="nav-link" to="/">Home</router-link>
        </li>
        <li v-if="profile && profile.role === 'ADMIN'" class="nav-item">
          <router-link class="nav-link" :to="`/users/${profile.id}`">Users list</router-link>
        </li>
      </ul>
      <div v-if="profile" class="navbar-text mr-3">{{ profile.name }}</div>
      <div v-if="profile">
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <a v-bind="attrs" v-on="on" class="btn btn-primary color-white" href="/logout">
              <i class="fas fa-toggle-off"></i>
            </a>
          </template>
          <span>Sign out</span>
        </v-tooltip>

      </div>
      <div v-else>
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <a v-bind="attrs" v-on="on" class="btn btn-primary color-white" href="/login">
              <i class="fas fa-toggle-on"></i>
            </a>
          </template>
          <span>Sign in</span>
        </v-tooltip>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  computed: {
    profile() {
      return this.$store.getters.getProfile;
    }
  }
}
</script>

<style lang="scss">

</style>
