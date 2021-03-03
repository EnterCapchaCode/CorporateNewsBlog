<template>
    <div v-if="!getProfile">
        <div class="login-block">
            <div>
                <h3>Привет!</h3>
                <p>Softeq News Blog - это веб-приложение, в котором публикуются и обсуждаются важные корпоративные новости</p>
                <p>Чтобы начать пользоваться приложением войдите с помощью своего корпоративного Google-аккаунта</p>
            </div>
            <div class="login-background">
                <img src="/static/assets/login.png" alt="login image">
            </div>
        </div>
    </div>
    <messages v-else></messages>
</template>

<script>
    import { mapGetters } from 'vuex'
    import Messages from '../messages/Messages.vue'
    export default {
        computed: {
            ...mapGetters([
                'getProfile'
            ])
        },
        components: { Messages }
    }
</script>

<style lang="scss">
    .login-block {
        display: flex;
        flex-direction: column;
        width: 100%;
        height: 100%;
        align-items: center;
        .login-background {
            align-items: center;
            img {
                width: 100%;
            }
        }
    }

</style>