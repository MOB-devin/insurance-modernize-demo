const CHAT_URL = (import.meta.env.VUE_APP_CHAT_URL ? import.meta.env.VUE_APP_CHAT_URL : "/");

export default {
    create(user) {
        return new WebSocket(CHAT_URL + "ws/chat/main/" + user);
    }
}
