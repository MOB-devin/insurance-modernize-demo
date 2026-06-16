<template>
    <div class="mx-auto" style="max-width: 20rem;">
        <div v-if="!auth.isAuthenticated()">
            <h2>Log in to your account</h2>
            <form>
                <div class="mb-3">
                    <label for="userName" class="form-label">Username:</label>
                    <input id="userName"
                           type="text"
                           class="form-control"
                           v-model="credentials.username"
                           required
                           placeholder="Enter username">
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input id="password"
                           type="password"
                           class="form-control"
                           v-model="credentials.password"
                           required
                           placeholder="Enter password">
                </div>

                <button type="button" class="btn btn-primary" @click="login()">Login</button>
            </form>

        </div>
        <div v-else>
            <form>
                <button type="button" class="btn btn-primary" @click="logout()">Logout</button>
            </form>
        </div>
    </div>

</template>

<script>
    import auth from './http/Auth'

    export default {
        name: "Account",
        data() {
            return {
                credentials: {
                    username: '',
                    password: ''
                },
                error: '',
                auth: auth
            }
        },
        methods: {
            login() {
                const credentials = {
                    username: this.credentials.username,
                    password: this.credentials.password
                };
                auth.login(credentials).then(() => {
                    window.location.href = '/';
                });
            },
            logout() {
                auth.logout();
                window.location.reload();
            }
        }

    }
</script>
