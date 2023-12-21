<template>
  <div>
    <home-header :clickCategory="index"></home-header>
    <v-btn @click="connect()">test</v-btn>
    <home-swiper></home-swiper>
    <live-order-list></live-order-list>
  </div>
</template>

<script>
import HomeHeader from "@/components/home/HomeHeader.vue";
import HomeSwiper from "@/components/home/HomeSwiper.vue";
import LiveOrderList from "@/components/home/sell/liveOrderList.vue";
import SockJS from 'sockjs-client';
import * as Stomp from "webstomp-client";

export default {
  name: 'HomePage',
  methods: {
    connect(){

      const serverURL = "http://localhost:7777/ws-stomp"
      let socket = new SockJS(serverURL);
      let stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)

      stompClient.connect(
          {},//frame
          frame => {
            console.log('소켓 연결 성공', frame);

            console.log("=====")
            // 구독
            stompClient.subscribe("/topic/greetings", res => {
              console.log(res)
              try {
                // 메세지 수신
                let message = JSON.parse(res.body);
                console.log("msg: ", message.message)
              } catch (error) {
                console.error("메시지 파싱 오류:", error);
              }
            });

            console.log("=====")
            // 접속되었다는 메세지
            let chatMessages = {
              message: "아름",
              test: "test"
            }

            let chatMessage = JSON.stringify(chatMessages)
            // 사용자 -> 브로커 메세지 전송
            stompClient.send("/send/firstChat", chatMessage
            )
          },
          error => {
            // 소켓 연결 실패
            console.log('소켓 연결 실패', error);
          }
      );
    }
  },
  components: {
    LiveOrderList,
    HomeSwiper,
    HomeHeader
  },
  data(){
    return {
      index: 1,
    }
  },

}
</script>
