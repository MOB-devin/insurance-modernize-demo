<template>
  <Pie :data="chartData" :options="options" />
</template>

<script>
import { Pie } from 'vue-chartjs'
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'

ChartJS.register(ArcElement, Tooltip, Legend)

export default {
  name: 'SalesPie',
  components: { Pie },
  props: {
    chartData: {
      type: Object,
      required: true
    }
  },
  data: () => ({
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  })
}
</script>
