import {ref} from "vue";
import { io } from "socket.io-client";

// 브로드캐스팅 받은 데이터 구분을 위한 id
export const id = ref(Math.random().toString())
// 받은 데이터를 수집
export const chatMessages =ref([])
// 연결 상태
export const connected =ref(false)

export const socket = io('http://localhost:7777')

socket.on("connect", () => {
    connected.value = true;
});

socket.on("disconnect", () => {
    connected.value = false;
});

// 메시지를 받으면 채팅 메시지 데이터에 푸시
socket.on('chat', (data) => {
    console.log(data.message)
    chatMessages.value.push(data)
})