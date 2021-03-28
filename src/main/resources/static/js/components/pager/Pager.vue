<template>
  <div v-if="pageInfo" class="mt-3 pager-container">
    <ul class="pagination">
      <li class="page-item disabled">
        <a class="page-link" tabindex="-1">Pages</a>
      </li>
      <li v-for="index in pagerBody"
          v-bind:class="{ active: pagerBody instanceof Array ? index === pageInfo.currentPage : index - 1 === pageInfo.currentPage, disabled: index === -1 }"
          class="page-item">
        <a class="page-link" tabindex="-1"
          v-on:click="loadMessagePage(index)">{{index !== -1 ? index : '...'}}</a>
      </li>
    </ul>
  </div>
</template>

<script>
  import axios from 'axios'
  
  export default {
    computed: {
      pageInfo() {
        return this.$store.getters.getPageInfo;
      },
      filter() {
        return this.$store.getters.getFilter;
      }
    },
    mounted() {
      this.updatePager();
    },
    methods: {
      loadMessagePage(page) {
        if (page === -1) {
          return;
        }
        axios
          .get(`${location.origin}/message?page=${this.pagerBody instanceof Array ? page : page - 1}${this.filter ? `&filter=${this.filter}` : ''}`)
          .then(response => {
            this.$store.commit('setMessages', response.data.messages);
            this.$store.commit('setPageInfo', response.data.page);
            this.updatePager();
          })
          .catch(err => {
            console.log(err)
          });
      },
      updatePager() {
        if (this.pageInfo.totalPages < 7) {
          return this.pagerBody = this.pageInfo.totalPages;
        }
        const currentPage = this.pageInfo.currentPage;
        const head = currentPage > 4
          ? [1, -1]
          : [1, 2, 3];
        const tail = currentPage < this.pageInfo.totalPages - 3
                ? [-1, this.pageInfo.totalPages]
                : [this.pageInfo.totalPages - 2, this.pageInfo.totalPages - 1, this.pageInfo.totalPages];
        const bodyBefore = currentPage + 1 > 4 && currentPage < this.pageInfo.totalPages - 1
                ? [currentPage - 2, currentPage - 1]
                : [];
        const bodyAfter = currentPage + 1 > 2 && currentPage < this.pageInfo.totalPages - 3
                ? [currentPage + 1, currentPage + 2]
                : [];
        let tempBody = head.concat(bodyBefore);
        if (currentPage > 3 && currentPage < this.pageInfo.totalPages - 2) {
          tempBody.push(currentPage);
        }
        tempBody = tempBody.concat(bodyAfter, tail);
        this.pagerBody = tempBody;
      },
    },
    data() {
      return {
        pagerBody: null,
      }
    }
  }
  
</script>

<style lang="scss">
  .pager-container {
    .pagination {
      justify-content: center;
    }
  }
</style>