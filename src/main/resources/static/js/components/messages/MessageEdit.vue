<template>
  <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
  >
    <template v-slot:activator="{ on, attrs }">
      <div v-if="profile.role === 'CREATOR' || profile.role === 'ADMIN'">
        <v-btn class="btn btn-primary color-white" role="button"
               v-bind="attrs"
               v-on="on"
               text
        >
          New post
        </v-btn>
      </div>
    </template>

    <v-card>
      <v-card-title>
        <span class="headline">Post creating</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-text-field
              label="Title"
              outlined
              dense
              ref="messageTitle"
              v-model="editingMessage && editingMessage.title"
              type="title"
              name="user-title"
          ></v-text-field>
          <v-textarea
              ref="messageText"
              v-model="editingMessage && editingMessage.text"
              type="text"
              name="user-text"
              color="teal"
          >
            <template v-slot:label>
              <div>
                Your text
              </div>
            </template>
          </v-textarea>
          <v-text-field
              label="Tag"
              outlined
              dense
              ref="messageTag"
              v-model="editingMessage && editingMessage.tag"
              type="tag"

          ></v-text-field>
          <input type="hidden" name="id" :value="editingMessage && editingMessage.id" ref="messageId"/>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
            color="blue darken-1"
            text
            @click="dialog = false"
            v-on:click="postMessage"
        >
          Save
        </v-btn>
        <v-btn
            color="blue darken-1"
            text
            @click="dialog = false"
        >
          Close
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";

export default {
  props: ['profile', 'messages'],
  computed: {
    editingMessage() {
      return this.$store.getters.getEditingMessage;
    }
  },
  data() {
    return {
      textError: null,
      tagError: null,
      dialog: false,
    }
  },
  methods: {
    postMessage() {
      const requestBody = {
        id: this.$refs.messageId.value,
        text: this.$refs.messageText.value,
        title: this.$refs.messageTitle.value,
        tag: {
          tagName: this.$refs.messageTag.value
        },
      }

      return axios({
        method: this.$refs.messageId.value ? 'put' : 'post',
        url: `${location.origin}/posts`,
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
              this.messages.push(res.data)
              this.$store.commit('setMessages', this.messages)
              const editingMessage = {
                id: null,
                text: '',
                title: '',
                tag: ''
              }
              this.$store.commit("setEditingMessage", editingMessage)
            }
          })
          .catch(err => {
            debugger;
            console.log(err);
          });
    },
  }
}

</script>

<style>

</style>
