<template>
  <v-app>
    <nav-bar :profile="profile"></nav-bar>
    <div class="container mt-5">
      <router-view></router-view>
    </div>
  </v-app>
</template>

<script>
  import NavBar from 'components/navbar/NavBar.vue'
  import Home from 'components/home/Home.vue'
  export default {
      components: {
        NavBar, Home
      },
      data: () => {
          return {
              messages: frontendData.messages,
              profile: frontendData.profile
          }
      },
      mounted() {
          this.commitProfile();
      },
      methods: {
          commitProfile() {
              this.$store.commit('setProfile', frontendData.profile);
              this.$store.commit('setMessages', frontendData.messages);
              this.$store.commit('setPageInfo', frontendData.page);
          }
      }
  }
</script>

<style lang="scss">
  @import "./../../style/main";
</style>