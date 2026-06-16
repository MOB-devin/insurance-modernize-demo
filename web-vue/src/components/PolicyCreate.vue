<template>
    <div class="form-container">
        <h2>Fill information about Policy Holder</h2>
        <form @submit="createPolicy">
            <div class="mb-3">
                <label for="firstName" class="form-label">First name:</label>
                <input id="firstName"
                       type="text"
                       class="form-control"
                       v-model="policyHolder.firstName"
                       required
                       placeholder="Enter first name">
            </div>

            <div class="mb-3">
                <label for="lastName" class="form-label">Last name:</label>
                <input id="lastName"
                       type="text"
                       class="form-control"
                       v-model="policyHolder.lastName"
                       required
                       placeholder="Enter last name">
            </div>

            <div class="mb-3">
                <label for="taxId" class="form-label">Tax id:</label>
                <input id="taxId"
                       type="text"
                       class="form-control"
                       v-model="policyHolder.taxId"
                       required
                       placeholder="Enter tax id">
            </div>

            <button type="submit" class="btn btn-primary">Confirm</button>
        </form>

    </div>
</template>

<script>
    import {HTTP} from "./http/ApiClient";

    export default {
        name: "PolicyCreate",
        props: {
            offerNumber: String
        },
        data() {
            return {
                policyHolder: {
                    firstName: '',
                    lastName: '',
                    taxId: ''
                }
            }
        },
        methods: {
            createPolicy: function (evt) {
                evt.preventDefault();

                const request = {
                    offerNumber: this.offerNumber,
                    policyHolder: this.policyHolder
                };

                HTTP.post('policies/create', request).then(response => {
                    this.$router.push({name: 'policyDetails', params: {policyNumber: response.data.policyNumber}});
                })
            }
        }
    }
</script>

<style scoped>
    .form-container {
        width: 40%;
        margin: 0 auto;
    }

    h2 {
        margin-bottom: 40px;
    }
</style>
