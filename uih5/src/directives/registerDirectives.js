import {update} from 'lodash';

export default function (app) {
  app.directive('number', {
    mounted: function (el) {
      let input = getInput(el);
      input.onkeyup = function () {
        input.value = input.value.replace(/[^\d]/g, '');
      };
      input.onblur = function () {
        input.value = input.value.replace(/[^\d]/g, '');
      };
    },
  });

  app.directive('float', {
    mounted: function (el) {
      let input = getInput(el);
      input.onkeyup = function () {
        input.value = input.value.replace(/[^\d.]/g, '');
      };
      input.onblur = function () {
        input.value = input.value.replace(/[^\d.]/g, '');
      };
    },
  });

  app.directive('focus', {
    mounted(el) {
      el.focus();
    },
  });
  app.directive('scale-on-click', {
    mounted(el) {
      el.style.transition = 'transform 0.2s ease-in-out';
      el.addEventListener('mousedown', (event) => {
        if (event.target.closest('.stop')) {
          return;
        }
        el.style.transform = 'scale(1.05)';
        setTimeout(() => {
          el.style.transform = 'scale(1)';
        }, 150);
      });
    },
    unmounted(el) {
      el.removeEventListener('mousedown');
    },
  });

  app.directive('like-animated', {
    mounted(el, binding) {
      el.style.transition = 'transform 0.2s ease-in-out';

      // 定义变量控制缩放状态
      let intervalId = null;
      let scaleUp = true;

      // 定义点击事件处理器
      const onMouseDown = () => {
        const play = binding.value;
        console.log(play);

        //
        clearInterval(intervalId);

        // Settings心跳动画的效果
        intervalId = setInterval(() => {
          el.style.transform = scaleUp ? 'scale(1.1)' : 'scale(1)';
          scaleUp = !scaleUp;
        }, 200); // 每 200ms 切换一次大小

        setTimeout(() => {
          stopScaling();
        }, 800);
      };

      // 停止心跳效果
      const stopScaling = () => {
        clearInterval(intervalId);
        el.style.transform = 'scale(1)'; // 恢复原始大小
      };

      // 添加事件监听器
      el.addEventListener('mousedown', onMouseDown);
    },
    unmounted(el) {
      el.removeEventListener('mousedown', el._onMouseDown);
    },
  });
}
const getInput = (el) => {
  let input = null;
  // 如果是原生html元素
  if (el instanceof HTMLInputElement) {
    input = el;
  } else {
    // vue组件 则向下继续查找
    input = el.getElementsByTagName('input')[0];
  }
  return input;
};
