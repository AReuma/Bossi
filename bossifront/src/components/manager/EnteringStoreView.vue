<template>
  <div>
    <div style="position: relative; height: 828px">
      <div style="position: absolute; z-index: 0; width: 100%; height: 100%;">
        <img class="imageStore" src="../../assets/manager/handmade_backgroundGif.gif" />
      </div>
      <div id="basic" style="position: absolute; z-index: 5; height: 66px; width: 70%;">
        <div id="title" @click.left="gMain" style="position: absolute; z-index: 8; background-color: rgba(0,0,0,0.02);">
          <div id="bColor">B</div>
          <div id="ossiColor">ossi</div>
        </div>

        <div style="float: right; width: 100%; justify-content: end; display: flex; align-items: center; position: absolute; z-index: 3; color: white; height: 66px; padding-right: 20px">
          <div @click="scrollClick" style="background-color: rgba(0,0,0,0.04)">입점하기</div>
        </div>
      </div>

      <div style="font-family: 'ONE-Mobile-POP', sans-serif; color: white; font-size: 70px; position: absolute; z-index: 4; left: 20%; top: 350px; background-color: rgba(0,0,0,0.04); padding: 5px">
        누구나 쉽게 상품을 판매할 수 있어요
      </div>

      <div style="position: absolute; z-index: 4; left: 48%; top: 760px;" @click="scrollClick()">
        <v-icon x-large color="white">mdi-chevron-down</v-icon>
      </div>
    </div>

    <div class="scroll-content" style="height: 505px; top: 800px;">
      <div style="display: flex; justify-content: center; align-items: center; padding-top: 100px">
        <img @click="gMain" id="logoImg" src="../../assets/logo/Bossi_logo_3.png"/>
      </div>

      <div id="inputBox">
        <div style="padding: 20px 0">
          <div style="height: 54px">
            <v-text-field
                :label="email"
                outlined
                disabled
                color="#434f58"
            ></v-text-field>
          </div>
        </div>

        <div style="padding: 20px 0">
          <div style="height: 54px">
            <v-text-field
                v-model="sendEmail"
                label="연락받을 메일"
                outlined
                color="#434f58"
                @keydown.enter="submit"
            ></v-text-field>
          </div>
        </div>

        <div>
          <v-btn depressed class="my_btn"  style="height: 54px; font-size: 17px; color: white" color="DEEP_PINK" @click="submit">
            입점 신청하기
          </v-btn>
        </div>
      </div>

      <v-dialog :value="existDialog = existCheck && existDialog === true" persistent width="auto" @keydown.enter="existDialog = false">
        <v-card style="padding: 40px 20px 10px 20px">
          <v-card-text style="font-size: 20px">
            이미 신청한 사용자 입니다
          </v-card-text>

          <v-card-text style="font-size: 15px">
            문의는 고객센터를 통해 연락해 주시면 이른 시일 내에 연락드리겠습니다 ^^
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="DEEP_PINK" depressed style="color: white; width: 27%; height: 40px" @click="closeExistDialog()">확인</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="errorDialog" persistent width="auto" @keydown.enter="errorDialog=false">
        <v-card style="padding: 40px 20px 10px 20px">
          <v-card-text style="font-size: 20px">
            기입된 정보를 다시 확인해주세요.
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="DEEP_PINK" depressed style="color: white; width: 27%; height: 40px" @click="errorDialog = false">확인</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "EnteringStoreView",
  props: ['existCheck'],
  data(){
    return {
      email: useCookies().cookies.get("email"),
      sendEmail: '',
      existDialog: false,
      errorDialog: false,
    }
  },
  methods: {
    scrollClick() {
      window.scrollTo(0, 830);
    },
    submit(){
      const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/
      if(this.sendEmail === '' || !this.sendEmail.match(email)){
       this.errorDialog = true;
      }else {
        const {email, sendEmail} = this;
        this.existDialog = true;

        this.$emit('submit', {email, sendEmail})
      }
    },
    gMain(){
      this.$router.push({name: "HomePage"})
    },
    closeExistDialog(){
      this.existDialog = false;
      this.$router.push({name: "HomePage"})
    }
  }
})
</script>

<style scoped>
#basic{
  margin: 0 15% 0 15%;
  height: 100%;
}
#bColor{
  color: #fc9899;
  font-family: ONE-Mobile-POP, serif;
  font-size: 42px;
  float: left;
  -webkit-text-stroke: 2px #ffffff;
}
#ossiColor{
  color: #ffffff;
  font-family: GangwonEduSaeeum_OTFMediumA, serif;
  font-size: 42px;
  float: left;
}
.imageStore{
  width: 100%;
  height: 100%;
}
#inputBox{
  margin: 5% 27% 0 27%;
  height: 100%;
}
.my_btn{
  width: 100%;
  font-weight: bolder;
  border-radius: 5px;
  height: 54px;
  margin-top: 20px;
}
#logoImg{
  height: 150px;
  width: auto;
}
</style>