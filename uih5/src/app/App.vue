<template>
  <div class="router-view-wrapper">
    <router-view v-if="isRouteAlive" v-slot="{Component, route}" class="router-view">
      <transition :name="route.meta.transition">
        <component :is="Component" :key="route.path" />
      </transition>
    </router-view>
  </div>
</template>
<script setup>
import {useRefresh} from './useRefresh';
const {isRouteAlive} = useRefresh();
</script>

<style lang="scss" scoped></style>
