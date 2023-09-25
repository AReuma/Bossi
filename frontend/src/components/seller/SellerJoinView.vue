<template>
  <div id="basic">

    <div style="width: 100%; display: flex; justify-content:center; margin-top: 20px">
      <img src="../../assets/logo/Bossi_logo_3.png"  style="height: 100px;" alt="logo"/>
    </div> <!--로고-->

    <div style="display: flex; justify-content: center; margin-top: 30px; margin-bottom: 30px">
      <v-row style="width: 100%;" justify="center">
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
        <v-col align="center" style="color: rgba(33,33,36,0.6)">
          작가님 가입 페이지
        </v-col>
        <v-col style="padding-top: 20px">
          <hr/>
        </v-col>
      </v-row>
    </div> <!-- 간단한 회원가입-->

    <div> <!--회원가입 폼 -->
      <div style="height: 100px;">
        <p>가입 승인 받은 이메일 <span style="color: red; font-weight: lighter">*</span></p>
        <p>다를경우 가입이 안되니 제대로 기입부탁립니다.</p>
        <v-text-field
            label="승인 받은 이메일을 입력해주세요."
            outlined
            color="#434f58"
            style="height:60px;"
            v-model="approvedEmail"
        ></v-text-field>
        <p v-if="approvedEmailCheck" style="color: red">필수항목입니다. 승인받은 이메일을 작성해주세요.</p>
      </div>

      <div style="height: 100px; margin-top: 60px;">
        <p>이메일 <span style="color: red; font-weight: lighter">*</span></p>
        <div style="display: flex">
          <v-text-field
              style="height: 60px"
              label="이메일을 입력해주세요."
              outlined
              color="#434f58"
              v-model="email"
          ></v-text-field>
          <v-btn color="DEEP_PINK"
                 @click="idDubCheck()"
                 :disabled="idCheckSuccess && idCheck"
                 id="phone_check">중복 확인</v-btn>
        </div>
        <p v-if="emailCheck" style="color: red;">이메일을 제대로 작성해주세요</p>
      </div>

      <div style="height: 200px; margin-top: 25px;">
        <p>비밀번호 <span style="color: red; font-weight: lighter">*</span></p>
        <v-text-field
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            @click:append="show = !show"
            label="비밀번호 (영문+숫자+특수문자 8자 이상)"
            outlined
            style="height: 70px;"
            color="#434f58"
            v-model="password"
        ></v-text-field>
        <p v-if="passwordCheck" style="color: red">영문, 특수문자, 숫자를 포함해서 8자 이상 작성해주세요.</p>
        <v-text-field
            :append-icon="showRe ? 'mdi-eye' : 'mdi-eye-off'"
            :type="showRe ? 'text' : 'password'"
            @click:append="showRe = !showRe"
            label="비밀번호 확인"
            outlined
            style="height: 70px;"
            color="#434f58"
            v-model="password2"
        ></v-text-field>
        <p v-if="passwordCheck2" style="color: red">비밀번호가 서로 다릅니다.</p>
      </div>

      <div style="height: 100px; margin-top: 15px;">
        <p>스토어 이름 <span style="color: red; font-weight: lighter">*</span></p>
        <v-text-field
            label="스토어 이름을 입력해주세요."
            outlined
            color="#434f58"
            style="height:60px;"
            v-model="storeName"
        ></v-text-field>
        <p v-if="storeNameCheck" style="color: red">필수항목입니다. 스토어 이름을 작성해주세요.</p>
      </div>

      <div style="height: 350px; margin-top: 30px;">
        <p>스토어 대표 프로필 <span style="color: red; font-weight: lighter">*</span></p>

        <div class="store-profile" v-if="previewURL === null">
          <v-btn icon @click="addProductImage">
            <v-icon x-large>mdi-camera</v-icon>
          </v-btn>

          <p style="text-align: center; margin-top: 5px">
            스토어 대표 이미지를 선택해주세요.
          </p>
        </div>


        <div v-else style="border: #4d5159 1px solid; display: flex;">

          <div v-if="previewURL" class="preview-container">
            <v-icon @click="deleteProductImage" style="position: absolute; z-index: 3; background-color: #bbbbbb">mdi-window-close</v-icon>
            <img :src="previewURL" alt="이미지 미리보기" style="max-height: 250px">
          </div>
        </div>
        <input type="file" ref="uploadItemFile" style="display: none"  @change="previewImage"/>


      </div>


      <div style="height: 100px; margin-top: 25px;">
        <p>스토어 바이오 설정</p>
        <v-text-field
            label="선택사항"
            outlined
            color="#434f58"
            v-model="storeBio"
        ></v-text-field>
      </div>

      <div style="height: 100%; margin-top: 25px;">
        <p>스토어 소개글</p>
        <v-textarea
            label="선택사항"
            outlined
            color="#434f58"
            v-model="storeIntroduction"
        ></v-textarea>
      </div>

    </div>
    <div>
      <v-btn @click="register()" class="my_btn" style="height: 54px; margin-bottom: 70px; color: #fc9899; font-size: 15px" outlined depressed>
        가입하기
      </v-btn>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";

export default defineComponent({
  name: "SellerJoinView",
  props: ['checkIdDub'],
  data(){
    return{
      show: false, // 비밀번호
      showRe: false,
      approvedEmailCheck: false,
      emailCheck: false,
      idCheckSuccess: true, // 중복체크
      idCheck: true, // 중복, 이메일 형식 체크
      passwordCheck: false,
      passwordCheck2: false,
      storeNameCheck: false,
      email: '',
      approvedEmail: '',
      password: '',
      password2: '',
      storeName: '',
      selectedFile: null,
      previewURL: null,
      storeBio: '',
      storeIntroduction: '',

    }
  },
  methods: {
    register() {
      /*const {approvedEmail, email, password, storeName, storeBio, storeIntroduction} = this;
      let jsonData = JSON.stringify({approvedEmail, email, password, storeName, storeBio, storeIntroduction})*/
      let formData = new FormData();
      const {approvedEmail, email, password, storeName, storeBio, storeIntroduction, selectedFile} = this;
      console.log(approvedEmail)
      formData.append('approvedEmail', approvedEmail);
      formData.append('email', email);
      console.log(email)
      formData.append('password', password);
      formData.append('storeName', storeName);
      formData.append('storeBio', storeBio);
      formData.append('storeIntroduction', storeIntroduction);

      formData.append('profileImg', selectedFile);

      console.log(formData)
      this.$emit('register', formData)
    },
    gLogin() {
      this.$router.push({name: 'LoginPage'})
    },
    addProductImage(){
      this.$refs.uploadItemFile.click()
    },
    deleteProductImage(){
      this.previewURL = null;
      this.selectedFile = null
    },
    previewImage(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.previewURL = e.target.result;
        };
        reader.readAsDataURL(file);
        this.selectedFile = file;
      }
    },
    idDubCheck() {
      const {email} = this;

      axios.get(API_BASE_URL + `/api/v1/users/find/email/${email}`)
          .then((res) => {
            this.idCheck = res.data;
            //alert(this.idCheck)
            if (this.idCheck) {
              alert('사용가능한 아이디 입니다.')
            } else {
              alert('사용중인 아이디 입니다. ')
            }
            console.log(res)
          })
          .catch((res) => {
            console.log(res)
          })
    },
  },
  watch: {
    approvedEmail: function (val){
      const approvedEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/
      this.approvedEmailCheck = !val.match(approvedEmail);
    },
    email: function (val) { // dkfma!123@gmail.com 회원가입이 안됨.
      const email = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/

      this.emailCheck = !val.match(email);

      this.emailCheck = !val.match(email);
      this.idCheckSuccess = !val.match(email);
      this.idCheck = false;
    },
    password: function (val) {
      const password = /(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,16}/

      this.passwordCheck = !val.match(password);
    },
    password2: function (val) {
      this.passwordCheck2 = val !== this.password;
    },
    storeName: function (val) {
      this.storeNameCheck = val.length <= 0;
    },
    idCheck: function () {
      if (this.idCheck === false) {
        this.email = '';
        this.idCheck = true;
      }
    }
  }
})
</script>

<style scoped>
#basic{
  margin: 10px 27% 0 27%;
  height: 100%;
}
p{
  color: rgba(33,33,36,0.6);
}
#phone_check{
  font-size: 16px;
  width: 18%;
  height: 54px;
  color: white;
  align-content: center;
  margin-left: 20px;
}
.my_btn{
  width: 100%;
  font-weight: bolder;
  border-radius: 5px;
  height: 54px;
}

.store-profile {
  border: 1px solid rgba(144, 144, 144, 0.72);
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 300px;
  width: 100%;
  border-radius: 7px;
}
</style>