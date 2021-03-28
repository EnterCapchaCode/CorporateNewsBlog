<template>
  <v-form>
    <v-row justify="center">
      <v-col
          cols="12"
          sm="10"
          md="3"
      >
        <v-text-field
            v-model="filter"
            label="Введите тег"
            solo
            dense
            name="input-tagg"
        ></v-text-field>
      </v-col>
      <v-btn
          v-on:click="getFilteredMessages()"
          class="btn btn-primary color-white ma-md-3"
          :loading="loading2"
          :disabled="loading2"
          color="#2196f3"
          @click="loader = 'loading2'"
      >
        Поиск
        <template v-slot:loader>
          <span>Готово</span>
        </template>
      </v-btn>

      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              v-bind="attrs"
              v-on="on"
              :loading="loading5"
              :disabled="loading5"
              color="#2196f3"
              class="btn btn-primary color-white ma-md-3"
              @click="loader = 'loading5'"
              v-on:click="getAllMessages()"
          >
            <v-icon dark>
              mdi-refresh
            </v-icon>
          </v-btn>
        </template>
        <span>Все посты</span>
      </v-tooltip>
    </v-row>
    <v-divider></v-divider>
    <div>
      <message-edit :profile="profile" :messages="messages"></message-edit>
      <message-list :pageInfo="pageInfo" :messages="messages" :profile="profile"></message-list>
    </div>
  </v-form>
</template>

<script>
import MessageEdit from './MessageEdit.vue'
import MessageList from './MessageList.vue'

import axios from 'axios'

export default {
  components: {
    MessageList, MessageEdit
  },
  computed: {
    profile() {
      return this.$store.getters.getProfile;
    },
    messages() {
      return this.$store.getters.getMessages;
    },
    pageInfo() {
      return this.$store.getters.getPageInfo;
    }
  },
  watch: {
    loader() {
      const l = this.loader
      this[l] = !this[l]

      setTimeout(() => (this[l] = false), 800)

      this.loader = null
    },
  },
  data() {
    return {
      filter: "",
      loading2: false,
      loading5: false,
      loader: null
    }
  },
  methods: {
    getFilteredMessages() {
      if (this.filter !== "") {
        this.$store.commit('setFilter', this.filter);
      }
      axios
          .get(`${location.origin}/posts/search/${this.filter}`)
          .then(response => {
            this.$store.commit('setMessages', response.data);
            // this.$store.commit('setPageInfo', response.data.page);
          })
          .catch(err => {
            console.log(err);
          });
    },
    getAllMessages() {
      axios
          .get(`${location.origin}/posts`)
          .then(response => {
            this.$store.commit('setMessages', response.data);
          })
          .catch(err => {
            console.log(err);
          });
      this.filter = ""
    },
  }
}
</script>

<style>

</style>
