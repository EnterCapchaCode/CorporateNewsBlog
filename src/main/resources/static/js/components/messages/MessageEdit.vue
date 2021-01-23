<template>
  <div>
    <a class="btn btn-primary color-white" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
      Message Editor
    </a>
    <div class="collapse" v-bind:class="{ show: editingMessage && editingMessage.text }" id="collapseExample">
      <div class="form-group mt-3">
        <form enctype="multipart/form-data" >
          <div class="form-group">
            <input class="form-control" v-bind:class="{ 'is-invalid': textError }" ref="messageText"
                    v-model="editingMessage && editingMessage.text" type="text" name="text" placeholder="Input your message" />
            <div v-if="textError" class="invalid-feedback">{{textError}}</div>
          </div>
          <div class="form-group">
            <input class="form-control" v-bind:class="{ 'is-invalid': textError }" ref="messageTag"
                   type="text" name="tag" placeholder="Input your tag"
                   v-model="editingMessage && editingMessage.tag" />
            <div v-if="tagError" class="invalid-feedback">{{tagError}}</div>
          </div>
          <div class="custom-file">
            <input id="customFile" type="file" name="file" ref="customFile" />
            <label for="customFile" class="custom-file-label">Choose file</label>
          </div>
          <input type="hidden" name="id" :value="editingMessage && editingMessage.id" ref="messageId" />
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
  
 export default {
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
       debugger;
       const formData = new FormData();
       formData.append('text', this.$refs.messageText.value);
       formData.append('tag', this.$refs.messageTag.value);
       if (this.$refs.customFile.files[0]) {
         formData.append('file', this.$refs.customFile.files[0]);
       }
       if (this.$refs.messageId.value) {
         formData.append('id', this.$refs.messageId.value);
       }
       fetch(`${location.origin}/message`, {
         method: this.$refs.messageId.value ? 'put' : 'post',
         body: formData
       })
         .then(res => res.json())
         .then(res => {
           if (res.apierror) {
             this.$toasted.error(` ${res.apierror.message}: ${res.apierror.status}!`, {
               theme: "bubble",
               position: "top-center",
               duration : 3000,
               icon: "error_outline"
             });
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