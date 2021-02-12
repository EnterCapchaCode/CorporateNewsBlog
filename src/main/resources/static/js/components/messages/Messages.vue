<template>
  <div>
    <div class="form-row">
      <div class="form-group col-md-6">
        <form class="form-inline">
          <label>
            <input type="text" name="filter" placeholder="Search by tag"  v-model="filter"/>
          </label>
          <button v-on:click="getFilteredMessages()" class="btn btn-primary ml-2" type="button">Search</button>
        </form>
      </div>
    </div>
    <message-edit :profile="profile" :messages="messages"></message-edit>
    <message-list :pageInfo="pageInfo" :messages="messages" :profile="profile"></message-list>
  </div>
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
    data() {
      return {
        filter: ""
      }
    },
    methods: {
      getFilteredMessages() {
        if (this.filter !== "") {
          this.$store.commit('setFilter', this.filter);
        }
        axios
          .get(`${location.origin}/message?page=${this.pageInfo.currentPage}&filter=${this.filter}`)
          .then(response => {
            this.$store.commit('setMessages', response.data.messages);
            this.$store.commit('setPageInfo', response.data.page);
          })
          .catch(err => {
            console.log(err);
          });
      }
    }
  }
</script>

<style>

</style>
