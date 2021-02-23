<template>
  <div>
    <pager v-if="pageInfo && pageInfo.totalPages > 1"></pager>
    <div class="card-columns">
      <div v-if="sortedMessages && sortedMessages.length">
        <div class="card my-3" v-for="message in sortedMessages">
          <v-card-title class="blue white--text">
            <span class="headline">{{ message.title }}</span>
            <v-list-item-subtitle>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                      v-bind="attrs"
                      v-on="on"
                      depressed
                      x-small
                      color="white"
                      v-on:click="getFilteredMessages(message.tag.tagName)"
                  >
                    #{{ message.tag.tagName }}
                  </v-btn>
                </template>
                <span>Search</span>
              </v-tooltip>
            </v-list-item-subtitle>
          </v-card-title>
          <div class="m-2">
            <span>{{ message.text }}</span><br>
          </div>
          <v-card-actions>
            <v-btn
                text
                color="#2196f3"
            >
              Comments
            </v-btn>
          </v-card-actions>
          <div class="card-footer text-muted message-footer_flex-block__parent">
            <div class="message-footer_flex-block"
                 v-bind:class="{ directColumn: message.author.id !== profile.id }">
              <span>
                <div v-if="message.author.surname != null">
                  {{ "Author: " + message.author.name + " " + message.author.surname }}
                </div>
                <div v-else>
                  {{ "Author: " + message.author.name }}
                </div>
              </span>
              <span>Created: {{ message.creationDate }}</span>
            </div>
            <v-tooltip bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-icon
                    v-bind="attrs"
                    v-on="on"
                    class="fas fa-pencil-alt"
                    small
                ></v-icon>
              </template>
              <span>Edit this post?</span>
            </v-tooltip>
            <div v-if="profile.role === 'ADMIN'"
                 v-on:click="dropTheMessage(message)"
                 @click="snackbar = true"
            >
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon
                      small
                      v-bind="attrs"
                      v-on="on"
                  >
                    fas fa-trash-alt
                  </v-icon>
                </template>
                <span>Delete this post?</span>
              </v-tooltip>
            </div>
          </div>
        </div>
      </div>
      <p v-else>No messages found</p>
    </div>
    <pager v-if="pageInfo && pageInfo.totalPages > 1"></pager>
    <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
    >
      Post deleted
      <template v-slot:action="{ attrs }">
        <v-btn
            color="blue"
            text
            v-bind="attrs"
            @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import Pager from './../pager/Pager.vue'
import axios from "axios";

export default {
  components: {Pager},
  props: ['messages',   'profile', 'pageInfo'],
  data() {
    return {
      snackbar: false,
      timeout: 2000,
      message: this.message,
    }
  },
  computed: {
    sortedMessages() {
      return this.messages.sort((a, b) => -(a.id - b.id))
    }
  },
  methods: {
    getFilteredMessages(tag) {
      axios
          .get(`${location.origin}/posts/search/${tag}`)
          .then(response => {
            this.$store.commit('setMessages', response.data);
          })
          .catch(err => {
            console.log(err);
          });
    },
    dropTheMessage(item) {
      fetch(`${location.origin}/posts/${item.id}`, {
        method: 'delete'
      })
          .then(res => {
            if (res.apierror) {
              this.$toasted.error(` ${res.apierror.message}: ${res.apierror.status}!`, {
                theme: "bubble",
                position: "top-center",
                duration: 3000,
                icon: "error_outline"
              });
            } else {
              const index = this.messages.indexOf(item);
              if (index > -1) {
                this.messages.splice(index, 1);
                this.$store.commit('setMessages', this.messages);
              }
              this.snackbar = true
              this.timeout = 2000
            }
          })
          .catch(err => {
            debugger;
            console.log(err);
          });
    }
  }
}

</script>

<style lang="scss">

.message-footer_flex-block__parent {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .message-footer_flex-block {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: start;
    width: 80%;
    max-width: 80%;
    word-break: keep-all;

    img {
      max-width: 70px;
      max-height: 70px;
      border-radius: 50%;
    }
  }

  .directColumn {
    flex-direction: column;
  }
}
</style>
