<template>
    <div>
        <div class="filter-container">
            <h4>Search policies</h4>
            <form class="d-flex">
                <input v-model="filterFields.number" placeholder="Policy number" class="form-control me-2 search-input"/>
                <input v-model="filterFields.policyHolder" placeholder="Policy Holder" class="form-control me-2 search-input"/>
                <button type="button" class="btn btn-primary search-button" @click="search()">Search</button>
            </form>
        </div>

        <table class="table table-bordered table-striped table-hover">
            <thead>
                <tr>
                    <th>Number</th>
                    <th>Date From</th>
                    <th>Date To</th>
                    <th>Policy Holder</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="policy in policies" :key="policy.number" @click="showDetails(policy)" style="cursor: pointer;">
                    <td>{{ policy.number }}</td>
                    <td>{{ policy.dateFrom }}</td>
                    <td>{{ policy.dateTo }}</td>
                    <td>{{ policy.policyHolder }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    import {HTTP} from "./http/ApiClient";
    const AND = "%20AND%20";

    export default {
        name: "PolicyList",
        data() {
            return {
                policies: [],
                filterFields: {
                    policyHolder: '',
                    number: ''
                }
            }
        },
        created: function () {
            this.runSearch();
        },
        methods: {
            showDetails(record) {
                this.$router.push({name: 'policyDetails', params: {policyNumber: record.number}});
            },
            search() {
                let queryString = '';
                queryString = this.addCriteria(queryString, this.filterFields.policyHolder);
                queryString = this.addCriteria(queryString, this.filterFields.number);

                this.runSearch(this.formatQueryString(queryString));
            },
            runSearch(queryString = '') {
                HTTP.get('policies' + queryString).then(response => {
                    this.policies = response.data.policies;
                });
            },
            addCriteria(queryString, criteria) {
                if(criteria !== '')
                    queryString += criteria + AND;

                return queryString;
            },
            formatQueryString(queryString) {
                if(queryString.endsWith(AND))
                    queryString = queryString.substring(0, queryString.length - AND.length);

                console.log(queryString);
                return queryString !== '' ? '?q=' + queryString : '';
            }
        }
    }
</script>

<style scoped>
    .filter-container {
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .search-input {
        width: 45% !important;
    }

    .search-button {
        width: 8% !important;;
    }
</style>
