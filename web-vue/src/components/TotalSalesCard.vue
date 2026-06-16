<template>
    <div class="col-6 col-lg-2">
        <div class="card">
            <div class="card-body p-3 d-flex align-items-center">
            <i v-bind:class="iconClass"></i>
            <div>
                <div class="text-primary fw-semibold">{{amount}}</div>
                <div class="text-muted text-uppercase fw-bold small">{{title}}</div>
                <div v-if="policiesCount" class="text-muted text-uppercase fw-bold small">{{policiesCount}} pcs.</div>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'TotalSalesCard',
    props: ['title', 'icon', 'amount', 'policiesCount'],
    computed: {
        iconClass() {
            return "fas fa-" + this.icon + " bg-primary p-3 fs-2 me-3 text-white rounded";
        }
    }
}
</script>
