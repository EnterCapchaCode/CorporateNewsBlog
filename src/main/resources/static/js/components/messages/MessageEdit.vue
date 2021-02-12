<template>
  <div v-if="profile.role === 'CREATOR' || profile.role === 'ADMIN'">
    <a class="btn btn-primary color-white" data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="false"
       aria-controls="collapseExample">
      New post
    </a>
    <div class="collapse" v-bind:class="{ show: editingMessage && editingMessage.text }" id="collapseExample">
      <v-form>
        <v-container>
          <v-text-field
              label="Title"
              outlined
              dense
              ref="messageTitle"
              v-model="editingMessage && editingMessage.title"
              type="title"
              name="title"
          ></v-text-field>
          <v-textarea
              ref="messageText"
              v-model="editingMessage && editingMessage.text"
              type="text"
              name="text"
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
              name="tag"
          ></v-text-field>
          <input type="hidden" name="id" :value="editingMessage && editingMessage.id" ref="messageId"/>
          <div class="form-group">
            <button class="btn btn-primary mt-3" v-on:click="postMessage">Save</button>
          </div>
        </v-container>
      </v-form>
    </div>
  </div>
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
