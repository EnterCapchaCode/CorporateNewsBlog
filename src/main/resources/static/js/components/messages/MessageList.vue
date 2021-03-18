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
                <span>Искать</span>
              </v-tooltip>
            </v-list-item-subtitle>
          </v-card-title>
          <div class="m-2">
            <span>{{ message.text }}</span><br>
          </div>
          <v-card-actions>

            <v-dialog
                v-model="dialog"
                max-width="600px"
                :retain-focus="false"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                    v-bind="attrs"
                    v-on="on"
                    text
                    color="#2196f3"
                    @click="getCommentsByPost(message)"
                >
                  Комментарии
                </v-btn>
              </template>
              <v-card align="center">
                <v-card-title class="blue white--text" align="start">
                  <span>{{ currentPostTitle }}</span>
                  <v-list-item-subtitle disabled="true" align="start">
                    <span>Создан: {{ message.creationDate }}</span>
                  </v-list-item-subtitle>
                </v-card-title>
                <v-card-text class="my-2 pb-0 pt-5">
                  <v-text-field
                      ref="commentText"
                      v-model="userComment"
                      label="Tap your comment"
                      solo
                      dense

                      :append-outer-icon="'mdi-send'"
                      @click:append-outer="sendComment()"
                  >
                  </v-text-field>
                </v-card-text>
                <div
                    v-for="comment in comments.slice().reverse()"
                >
                  <v-card
                      class="mx-auto"
                      width="500px"
                      align="start">
                    <v-card-title>
                      <div v-if="comment.employeeDto.surname != null">
                        {{ comment.employeeDto.name + " " + comment.employeeDto.surname }}
                      </div>
                      <div v-else>
                        {{ comment.employeeDto.name }}
                      </div>
                      <v-spacer></v-spacer>

                      <v-icon
                          v-if="profile && profile.role === 'ADMIN'"
                          small
                          v-bind="attrs"
                          v-on="on"
                          v-on:click="dropTheComment(comment)"
                      >
                        fas fa-trash-alt
                      </v-icon>

                    </v-card-title>
                    <v-card-subtitle>{{ comment.creationDate }}
                    </v-card-subtitle>
                    <v-card-text>
                      <span class="text--primary">{{ comment.commentText }}</span>
                    </v-card-text>
                    <v-divider></v-divider>
                  </v-card>
                </div>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="blue darken-1"
                      text
                      @click="dialog = false"
                  >
                    Закрыть
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

          </v-card-actions>
          <div class="card-footer text-muted message-footer_flex-block__parent">
            <div class="message-footer_flex-block"
                 v-bind:class="{ directColumn: message.author.id !== profile.id }">
              <span>
                <div v-if="message.author.surname != null">
                  {{ "Автор: " + message.author.name + " " + message.author.surname }}
                </div>
                <div v-else>
                  {{ "Автор: " + message.author.name }}
                </div>
              </span>
              <span>Создан: {{ message.creationDate }}</span>
            </div>
<!--            <v-tooltip bottom>-->
<!--              <template v-slot:activator="{ on, attrs }">-->
<!--                <v-icon-->
<!--                    v-bind="attrs"-->
<!--                    v-on="on"-->
<!--                    class="fas fa-pencil-alt"-->
<!--                    small-->
<!--                ></v-icon>-->
<!--              </template>-->
<!--              <span>Редактировать</span>-->
<!--            </v-tooltip>-->
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
                <span>Удалить</span>
              </v-tooltip>
            </div>
          </div>
        </div>
      </div>
      <v-card-text v-else align="center" class="pt-16"><h5>Посты не найдены</h5></v-card-text>
    </div>
    <pager v-if="pageInfo && pageInfo.totalPages > 1"></pager>
    <v-snackbar
        v-model="snackbar"
        :timeout="timeout"
    >
      {{snackbarMessage}}
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
  props: ['messages', 'profile', 'pageInfo'],
  data() {
    return {
      userComment: "",
      comments: [],
      dialog: false,
      snackbar: false,
      snackbarMessage: "",
      timeout: 2000,
      message: this.message,
      comment: this.comment,
      currentPost: 0,
      currentPostTitle: ""
    }
  },
  computed: {
    currentUser() {
      return this.$store.getters.getProfile;
    },
    sortedMessages() {
      return this.messages.sort((a, b) => -(a.id - b.id))
    },
  },
  methods: {
    dropTheComment(item) {
      fetch(`${location.origin}/comments/${item.id}`, {
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
              const index = this.comments.indexOf(item);
              if (index > -1) {
                this.comments.splice(index, 1);
                this.$store.commit('setComments', this.comments);
              }
              this.snackbarMessage = 'Комментарий удален'
              this.snackbar = true
              this.timeout = 2000
            }
          })
          .catch(err => {
            debugger;
            console.log(err);
          });
    },
    sendComment() {
      const requestBody = {
        commentText: this.userComment,
      }

      let today = new Date();
      const dd = String(today.getDate()).padStart(2, '0');
      const mm = String(today.getMonth() + 1).padStart(2, '0');
      const yyyy = today.getFullYear();

      today = yyyy + '-' + mm + '-' + dd;

      const currentPost = this.currentPost;

      return axios({
        method: 'post',
        url: `${location.origin}/comments/${currentPost}`,
        data: requestBody
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

              const response = {
                id: this.comments.length + 1,
                commentText: this.userComment,
                creationDate: today,
                employeeDto: {
                  name: this.profile.name,
                  surname: this.profile.surname
                }
              }
              this.comments.push(response)
              this.$store.commit('setComments', this.comments)
              this.userComment = ""
            }
          })
          .catch(err => {
            debugger;
            console.log(err);
          });

    },
    getCommentsByPost(message) {
      this.currentPost = message.id
      this.currentPostTitle = message.title
      return axios
          .get(`${location.origin}/comments/${message.id}`)
          .then(response => {
            const comments = response.data;
            this.$store.commit('setComments', comments);
            this.comments = comments;
          })
          .catch(err => {
            console.log(err);
          });
    },
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
              this.snackbarMessage = 'Пост удален'
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
