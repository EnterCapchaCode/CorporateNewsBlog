<template>
    <transition name="modal-fade">
        <div class="modal-backdrop">
            <div class="modal" role="dialog" aria-labelledby="modalTitle" aria-describedby="modalDescription">
                <header class="modal-header" id="modalTitle">
                    <slot name="header">
                        <h3>{{entity.name}} users</h3>
                        <button type="button" class="btn-close" @click="close" aria-label="Close modal">x</button>
                    </slot>
                </header>
                <section class="modal-body" id="modalDescription">
                    <slot name="body">
                        <ul class="list-group">
                            <li class="list-group-item" v-for="user in users">
                                <div class="user-block-item table-item__cursor-pointer" :ref="user.id"
                                     :class="user.alreadyAssigned ? 'assigned-user' : 'not-assigned-user'" v-on:click="actionWithUser(user)">
                                    <img v-if="user.userpic" :src="`${user.userpic}`" alt="User's avatar"/>{{user.name}}
                                </div>
                            </li>
                        </ul>
                    </slot>
                </section>
            </div>
        </div>
    </transition>
</template>

<script>
    import axios from "axios";

    export default {

        props: ['entity', 'users', 'url'],
        data() {
            return {
                allUsers: null
            }
        },
        computed: {
        },
        methods: {
            close() {
                this.$emit('close');
            },
            actionWithUser(user) {
                const action = user.alreadyAssigned ? 'unassign' : 'assign';
                return axios
                    .put(`${location.origin}/${this.url}/${this.entity.id}/${action}/${user.id}`)
                    .then(response => {
                        if (user.alreadyAssigned) {
                            user.alreadyAssigned = false;
                            const index = this.entity.users.map(item => item.id).indexOf(user.id);
                            if (index > -1) {
                                this.entity.users.splice(index, 1);
                            }
                        } else {
                            user.alreadyAssigned = true;
                            this.entity.users.push(user);
                        }
                    });
            }
        },
    };
</script>

<style lang="scss">

    .assigned-user {
        background-color: lightgreen;
    }

    .not-assigned-user {
        background-color: slategrey;
    }

    .modal-backdrop {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        background-color: rgba(0, 0, 0, 0.3);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .modal {
        background: #FFFFFF;
        box-shadow: 2px 2px 20px 1px;
        overflow-x: auto;
        display: flex;
        flex-direction: column;
    }

    .modal-header,
    .modal-footer {
        padding: 15px;
        display: flex;
    }

    .modal-header {
        border-bottom: 1px solid #eeeeee;
        color: #4AAE9B;
        justify-content: space-between;
    }

    .modal-footer {
        border-top: 1px solid #eeeeee;
        justify-content: flex-end;
    }

    .modal-body {
        position: relative;
        padding: 20px 10px;
        overflow: auto;
    }

    .btn-close {
        border: none;
        font-size: 20px;
        padding: 20px;
        cursor: pointer;
        font-weight: bold;
        color: #4AAE9B;
        background: transparent;
    }

    .btn-green {
        color: white;
        background: #4AAE9B;
        border: 1px solid #4AAE9B;
        border-radius: 2px;
    }

    .user-block-item {
        display: flex;
        flex-direction: row;

        img {
            width: 70px;
            height: 70px;
            border-radius: 50%;
        }

        a {
            padding-left: 30px;
        }
    }
</style>