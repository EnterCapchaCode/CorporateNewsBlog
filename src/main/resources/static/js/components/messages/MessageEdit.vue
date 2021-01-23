<template>
  <div v-if="profile.role === 'CREATOR' || profile.role === 'ADMIN'">
    <a class="btn btn-primary color-white" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
      Message Editor
    </a>
    <div class="collapse" v-bind:class="{ show: editingMessage && editingMessage.text }" id="collapseExample">
      <div class="form-group mt-3">
        <form enctype="multipart/form-data">
          <div class="form-group">
            <input class="form-control" v-bind:class="{ 'is-invalid': textError }" ref="messageText"
                   v-model="editingMessage && editingMessage.text" type="text" name="text" placeholder="Input your message"/>
            <div v-if="textError" class="invalid-feedback">{{ textError }}</div>
          </div>
          <div class="form-group">
            <input class="form-control" v-bind:class="{ 'is-invalid': textError }" ref="messageTag"
                   type="text" name="tag" placeholder="Input your tag"
                   v-model="editingMessage && editingMessage.tag"/>
            <div v-if="tagError" class="invalid-feedback">{{ tagError }}</div>
          </div>
          <input type="hidden" name="id" :value="editingMessage && editingMessage.id" ref="messageId"/>
          <div class="form-group">
            <button class="btn btn-primary mt-3" v-on:click="postMessage">Save message</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import fontawesome from '@fortawesome/fontawesome'
import faSolid from '@fortawesome/fontawesome-free-solid'
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
