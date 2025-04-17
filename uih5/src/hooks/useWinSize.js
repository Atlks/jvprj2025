import { ref, onMounted, onBeforeUnmount } from "vue";
//导出获取窗口的宽高
export function useWinSize() {
    const size = ref({ width: window.innerWidth, height: window.innerHeight });
    //窗口变化时候更新 size
    function onResize() {
        size.value = {
          //用窗口的最新宽高更新 width 与 height 
            width: window.innerWidth,
            height: window.innerHeight,
        };
    }
    //组件 挂载完毕 更新  size
    onMounted(() => {
        window.addEventListener("resize", onResize);
    });
    //组件 要卸载的时候移除 事件监听
    onBeforeUnmount(() => {
        window.removeEventListener("resize", onResize);
    });
    return size;
}