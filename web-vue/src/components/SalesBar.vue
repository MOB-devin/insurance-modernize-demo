<template>
  <Bar :data="chartData" :options="options" />
</template>

<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js'

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  name: 'SalesBar',
  components: { Bar },
  props: {
    chartData: {
      type: Object,
      required: true
    }
  },
  data: () => ({
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          grid: {
            offset: true
          }
        },
        y: {
          beginAtZero: true,
          min: 0
        }
      }
    }
  })
}
</script>
