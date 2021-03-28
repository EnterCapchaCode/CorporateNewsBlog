<template>
  <div v-if="!getProfile">
    <div class="login-block">
      <div>
        <h3>Hello</h3>
        <p class="greeting">Softeq News Blog is a web application that publishes and discusses important corporate news</p>
        <p class="greeting">To start using the application, please sign in with your corporate Google account</p>
      </div>
      <div class="login-background">
        <img src="/static/assets/login.png" alt="login image">
      </div>
    </div>
  </div>
  <messages v-else></messages>
</template>

<script>
import {mapGetters} from 'vuex'
import Messages from '../messages/Messages.vue'

export default {
  computed: {
    ...mapGetters([
      'getProfile'
    ])
  },
  components: {Messages}
}
</script>

<style lang="scss">
.login-block {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  align-items: center;

  .login-background {
    align-items: center;
    text-align: center;
    img {
      width: 50%;

    }
  }
}

.greeting {
  font-size: 1.3em;
}

</style>