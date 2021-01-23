<template>
    <div>
        <!--${message?ifExists}-->
            <div class="form-group">
                <label class="col-sm-2 col-form-label"> Name: </label>
                <div class="col-sm-6">
                    <input class="form-control" type="text" name="name" :placeholder="profile != null ? profile.name : 'Enter your name'"
                           v-model="updatedName" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-form-label"> Email: </label>
                <div class="col-sm-6">
                    <input class="form-control" type="email" name="email" :placeholder="profile != null ? profile.email : 'Enter your email'"
                           v-model="updatedEmail" />
                </div>
            </div>
            <!--<input type="hidden" name="_csrf" value="${_csrf.token}" />-->
            <button v-on:click="updateProfile()" class="btn btn-primary">Save</button>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        computed: {
          profile() {
            return this.$store.getters.getProfile;
          }
        },
        data() {
          return {
              updatedName: null,
              updatedEmail: null
          }
        },
        methods: {
            commitUpdatedProfile(data) {
                this.$store.commit('setProfile', data);
            },
            updateProfile() {
                debugger;
                axios
                    .post(location.origin + this.$route.path, {
                        name: this.updatedName,
                        email: this.updatedEmail
                    })
                    .then(() => {
                        this.commitUpdatedProfile({
                            ...this.profile,
                            name: this.updatedName,
                            email: this.updatedEmail
                        });
                    })
                    .catch(err => {
                        console.log(err);
                    });
            },
        }
    }
</script>

<style>

</style>