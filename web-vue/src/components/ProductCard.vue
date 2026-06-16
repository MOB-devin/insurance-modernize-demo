<template>
    <div class="card">
        <img class="card-img-top" :src="product.image" :alt="product.name">
        <div class="card-body">
            <h5 class="card-title">{{ product.name }}</h5>
            <p class="card-text">
                {{ product.description }}
                <CoverList :covers="product.covers"></CoverList>
            </p>
            <router-link :to="{name: 'product', params: { productCode: product.code }}">
                <button type="button" class="btn btn-primary">Buy</button>
            </router-link>
        </div>
    </div>
</template>

<script>
    import CoverList from "./CoverList.vue";

    export default {
        name: 'ProductCard',
        props: ['product'],
        components: {CoverList}
    }
</script>

<style scoped>
    .card-img-top {
        max-height: 230px;
    }
</style>
