import {useAnnouncementStore} from '@/stores/announcement';
import {useUserStore} from '@/stores/user';
import {heartbeat} from '@/api/heartbeat';
export function initData() {
  let heartbeatTimer = null;
  const heartbeatTime = 1000 * 60 * 5;
  const user = useUserStore();
  const announcement = useAnnouncementStore();
  if (user.isLogin) {
    heartbeatTimer = setInterval(heartbeat, heartbeatTime);
  }
  watch(
    () => user.isLogin,
    (isLogin) => {
      if (isLogin) {
        announcement.getAllUnread();
        heartbeatTimer = setInterval(heartbeat, heartbeatTime);
      } else {
        clearHeartbeatInterval();
      }
    },
  );

  function clearHeartbeatInterval() {
    clearInterval(heartbeatTimer);
    heartbeatTimer = null;
  }
}
