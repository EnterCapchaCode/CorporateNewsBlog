<template>
  <div>
    <pager v-if="pageInfo && pageInfo.totalPages > 1"></pager>
    <div class="card-columns">
      <div v-if="messages && messages.length">
        <div class="card my-3" v-for="message in messages">
          <v-card-title class="blue white--text">
            <span class="headline">{{ message.title }}</span>
          </v-card-title>
          <div class="m-2">
            <span>{{ message.text }}</span><br>
            <v-btn
                depressed
                small
            >
              #{{ message.tag.tagName }}
            </v-btn>
            <i></i>
          </div>
          <div class="card-footer text-muted message-footer_flex-block__parent">
            <div class="message-footer_flex-block" v-bind:class="{ directColumn: message.author.id !== profile.id }">
              <span>
                <div v-if="message.author.surname != null">
                  {{ "Author: " + message.author.name + " " + message.author.surname }}
                </div>
                <div v-else>
                  {{ "Author: " + message.author.name }}
                </div>
              </span>
              <span>Created: {{ message.creationDate }}</span>
              <div v-if="message.edited"><i alt="Edited" class="fas fa-pencil-alt"></i></div>
            </div>
            <a v-if="profile.role === 'ADMIN'"
               v-on:click="dropTheMessage(message)">
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
            </a>
          </div>
        </div>
      </div>
      <p v-else>No messages found</p>
    </div>
    <pager v-if="pageInfo && pageInfo.totalPages > 1"></pager>
  </div>
</template>

<script>
import Pager from './../pager/Pager.vue'

export default {
  components: {Pager},
  props: ['messages', 'profile', 'pageInfo'],
  data() {
    return {
      message: this.message
    }
  },
  methods: {
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
