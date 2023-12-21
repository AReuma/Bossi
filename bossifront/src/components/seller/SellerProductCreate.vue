<template>
  <div id="basic">
    <div style="margin-top: 12px">
      <span class="input-name">카테고리: </span><br/>
    <v-select
        style="margin-top: 8px; max-width: 180px; max-height: 45px;"
        height="45"
        color="DEEP_PINK"
        v-model="category"
        :items="categoryList"
        item-text="category"
        item-value="categoryId"
        :style="{outline: 'none'}"
        label="Category"
        dense
    ></v-select>
    </div>

    <div>
      <div style="margin-top: 20px">
        <span class="input-name">작품 제목: </span><br/>
        <input v-model="title" placeholder=" 제목" type="text" class="input-element" style="width: 100%">
      </div>

      <div>
        <div style="margin-top: 20px; font-size: 18px">
          대표이미지:
        </div>

        <div>
          <div class="container-image">
            <v-btn icon @click="addProductImage">
              <v-icon x-large>mdi-camera</v-icon>
            </v-btn>

            <p v-if="previewProductURL.length === 0" style="text-align: center; margin-top: 5px">
            대표 이미지를 선택해주세요.
              <br>
            첫번째 사진은 검색결과에 뜰 사진입니다.
            </p>

            <div style="border: #4d5159 1px solid; display: flex; overflow: hidden; overflow-x: auto">
              <div v-for="(image, index) in previewProductURL" :key="index" class="preview-container">
                <v-icon @click="deleteProductImage(index)" style="position: absolute; z-index: 3; background-color: #bbbbbb">mdi-window-close</v-icon>
                <img :src="image" alt="이미지 미리보기" style="max-height: 250px">
              </div>
            </div>

            <input multiple type="file" style="display: none" ref="uploadItemFile"  @change="previewProductImage" />
          </div>


        </div>
      </div>

      <div style="display: flex; margin-top: 20px">
        <div style="margin-top: 14px; margin-right: 20px; width: 33%">
          <span class="input-name">가격: </span> <br/>
          <input type="text" v-model="price" placeholder=" ex) 18000" @input="formatInput(0)" class="input-element">
        </div>

        <div style="margin-top: 14px; margin-right: 20px; width: 33%">
          <div>
            <span class="input-name">할인율: </span> <br/>
            <input type="text" v-model="rating" placeholder=" ex) 30" class="input-element" @input="formatInputRate" >
          </div>
          <div style="margin-top: 12px;">
            <span class="input-name">할인 가격: </span> <br/>
            <input readonly type="text" :value="ratingPrice" placeholder=" 할인 가격" style="background-color: rgba(106,106,106,0.07)" class="input-element">
          </div>
        </div>

        <div style="margin-top: 14px; margin-right: 20px; width: 33%">
          <span class="input-name">배송비: </span> <br/>
          <input v-bind:class="{ 'disabled-button': checkFreeDelivery }" v-model="deliveryCount" :disabled="checkFreeDelivery" type="text" placeholder=" ex) 3500" class="input-element" @input="formatInput(2)">
          <div style="margin-top: 10px">
            <v-checkbox style="height: 15px;" label="무료 배송" v-model="checkFreeDelivery"></v-checkbox>
            <v-checkbox style="height: 35px;" label="무료 배송 가격 설정" v-model="checkFreeDeliveryFreePrice"></v-checkbox>
            <span style="font-size: 14px; color: #9f9fa2"> * 작가님 전 상품 주문 총합에 따른 무료배송 설정 </span>
            <input v-if="checkFreeDeliveryFreePrice" v-model="freeCount" type="text" placeholder=" ex) 100000" class="input-element" @input="formatInput(3)">
          </div>
        </div>

      </div>


      <div style="margin-top: 20px">
        <span class="input-name">수량: </span> <br/>
        <v-checkbox style="height: 35px" v-model="noCount" label="수량 제한 없음"></v-checkbox>
        <input v-if="!noCount" v-model="stockQuantity" type="text" placeholder=" ex) 100" class="input-element">
      </div>

      <div style="margin-top: 20px">
        <span class="input-name">옵션: </span>
        <div style="margin-top: 10px">
          <v-btn text depressed outlined  @click="addOption" style="width: 100px; margin-right: 10px">옵션 추가</v-btn>
          <v-btn text depressed outlined @click="delOption">옵션 삭제</v-btn>

          <div style="margin-top: 15px">
            <div v-for="(option, index) in options" :key="index">
              <input v-model="option.value" type="text" placeholder=" ex) 색상" class="input-element" style="width: 75%; margin-right: 3%">
              <v-btn depressed text outlined @click="addDetailOption(index)" style="width: 15%; height: 45px;">상세 옵션 추가</v-btn>

              <div v-for="(detail, detailIndex) in detailOption[index]" :key="detailIndex" style="margin-left: 40px; margin-top: 5px">
                <input v-model="detail.value" type="text" placeholder=" ex) 빨강" class="input-element" style="width: 50%; ">
                <input v-model="detail.price" type="text" placeholder=" ex) 가격 1000" class="input-element" style="width: 30%; margin-left: 20px">
                <v-btn depressed icon @click="deleteOptionDetail(index, detailIndex)" style="margin-left: 5px"><v-icon>mdi-window-close</v-icon></v-btn>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>

    <div style="font-size: 20px; margin-top: 40px">작품 정보 작성: </div>

    <hr style="margin: 10px 0 25px 0; border: 1px solid rgba(67,79,88,0.11)"/>


    <div v-if="editor" style="margin-bottom: 5px;">
      <div style="display: flex;">
        <v-btn text depressed @click="editor.chain().focus().toggleBold().run()" :disabled="!editor.can().chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
          <v-icon>mdi-format-bold</v-icon>
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleItalic().run()" :disabled="!editor.can().chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
          <i style="font-family: Baskerville, sans-serif; font-size: 18px">I</i>
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleStrike().run()" :disabled="!editor.can().chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
          <del>T</del>
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleCode().run()" :disabled="!editor.can().chain().focus().toggleCode().run()" :class="{ 'is-active': editor.isActive('code') }">
          <v-icon>
            mdi-code-brackets
          </v-icon>
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().unsetAllMarks().run()">
          <v-icon>mdi-pen-remove</v-icon>
        </v-btn>


        <v-btn text depressed @click="editor.chain().focus().setHorizontalRule().run()">
          <v-icon>mdi-minus</v-icon>
          구분선
        </v-btn>

        <v-btn text depressed @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'is-active': editor.isActive('codeBlock') }">
          <v-icon>mdi-code-braces</v-icon>
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'is-active': editor.isActive('blockquote') }">
          <v-icon>mdi-format-quote-open</v-icon>
        </v-btn>

        <v-btn text depressed @click="editor.chain().focus().setHardBreak().run()">
          hard break br 태그
        </v-btn>


        <v-btn text depressed @click="imgBtn">
          <v-icon>mdi-image-plus-outline</v-icon>
        </v-btn>

        <div style="display: flex; width: 100%; justify-content: end; padding-right: 10px">
          <v-btn icon @click="editor.chain().focus().undo().run()" :disabled="!editor.can().chain().focus().undo().run()" style="margin-right: 5px">
            <v-icon>mdi-arrow-u-left-top</v-icon>
          </v-btn>

          <v-btn icon @click="editor.chain().focus().redo().run()" :disabled="!editor.can().chain().focus().redo().run()">
            <v-icon>mdi-arrow-u-right-top</v-icon>
          </v-btn>
        </div>
      </div>

      <div style="display: flex; align-items: center">
        <v-btn text depressed @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
          h1
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
          h2
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
          h3
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleHeading({ level: 4 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
          h4
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleHeading({ level: 5 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
          h5
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleHeading({ level: 6 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
          h6
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }">
          <v-icon>mdi-format-list-bulleted-type</v-icon>
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }">
          <v-icon>mdi-format-list-numbered</v-icon>
        </v-btn>

        <input
            style="margin-left: 10px"
            type="color"
            @input="editor.chain().focus().setColor($event.target.value).run()"
            :value="editor.getAttributes('textStyle').color"
        >
        <v-btn text depressed @click="editor.chain().focus().setColor('#fc9899').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#fc9899' })}">
          pink
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().setColor('#70CFF8').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#70CFF8' })}">
          blue
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().setColor('#958DF1').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#958DF1' })}">
          purple
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().setColor('#ff0000').run()" :class="{ 'is-active': editor.isActive('textStyle', { color: '#ff0000' })}">
          red
        </v-btn>
        <v-btn text depressed @click="editor.chain().focus().unsetColor().run()">
          unsetColor
        </v-btn>

      </div>
    </div>


    <v-dialog v-model="imgDialog" persistent width="auto" @keydown.enter="imgDialog = false">
      <v-card style="padding: 40px 20px 10px 20px">
        <v-card-text style="font-size: 20px">
          이미 업로드
        </v-card-text>

        <v-card-actions>
          <input type="file" @change="previewImage" />
          <img v-if="previewURL" :src="previewURL" alt="Preview" style="height: 500px" />
          <button @click="uploadImage">Upload</button>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <editor-content :editor="editor"></editor-content>

    <v-btn style="margin-top: 15px; height: 50px; color: #787878; font-size: 24px" depressed color="PINK" @click="saveContent">저장</v-btn>
  </div>
</template>

<script>
import {Editor, EditorContent} from '@tiptap/vue-3'
import {defineComponent} from "vue";
import axios from "axios";
import {API_BASE_URL} from "@/constant/basic";
import StarterKit from '@tiptap/starter-kit'
import Document from '@tiptap/extension-document'
import Dropcursor from '@tiptap/extension-dropcursor'
import Image from '@tiptap/extension-image'
import Paragraph from '@tiptap/extension-paragraph'
import Text from '@tiptap/extension-text'
import {Color} from "@tiptap/extension-color";
import {TextStyle} from "@tiptap/extension-text-style";
import Blockquote from '@tiptap/extension-blockquote'
import index from "vuex";
import {useCookies} from "vue3-cookies";

export default defineComponent({
  name: "SellerProductCreate",
  computed: {
    index() {
      return index
    }
  },
  props: {
    categoryList: {
      type: Array,
    }
  },
  components: {
    EditorContent
  },
  data() {
    return {
      sellerId: useCookies().cookies.get('sellerId'),
      editor: null,
      title: '',
      price: '',
      ratingPrice: '',
      rating: 0,
      deliveryCount: '',
      freeCount: '',
      noCount: true,
      stockQuantity: '',
      selectedFile: null,
      previewURL: null,
      selectProductFile: [],
      previewProductURL: [],
      imgDialog: false,
      category: null,
      checkFreeDelivery: false,
      checkFreeDeliveryFreePrice: false,
      options: [
        {value: ''}
      ],
      detailOption: [],
      imgUploadList: [],
      imgUploadAll: [],
      ProductImgDialog: false
    };
  },
  methods: {
    formatInput(index) {
      if(index === 0) {
        this.price = this.price.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      }else if(index === 1){
        this.ratingPrice = this.ratingPrice.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      }else if(index === 2) {
        this.deliveryCount = this.deliveryCount.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      }else if(index === 3){
        this.freeCount = this.freeCount.replace(/\D/g, '').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      }
    },
    formatInputRate(){
      let rate = (Number)(this.rating);
      let oPrice = parseFloat(this.price.replace(/,/g, ''));
      let dicountAmount = (oPrice * rate) / 100;
      this.ratingPrice = String(oPrice - dicountAmount);

      this.formatInput(1)
    },
    addDetailOption(index){
      if(this.detailOption.length === index){
        this.detailOption.push([]);
      }
      this.detailOption[index].push({})
    },
    deleteOptionDetail(index, detailIndex){
      this.detailOption[index].splice(detailIndex, 1);
    },
    addOption(){
      this.options.push({value: ''});
    },
    delOption(){
      this.options.pop();
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
    addProductImage(){
      this.$refs.uploadItemFile.click()
    },
    deleteProductImage(index){
      this.previewProductURL.splice(index, 1)
      this.selectProductFile.splice(index, 1)
    },
    previewProductImage(event){
      const files = event.target.files;

      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();

        reader.onload = () => {
          this.previewProductURL.push(reader.result);
        };
        reader.readAsDataURL(files[i]);
      }
      this.selectProductFile.push(files)
    },
    uploadImage() {
      const formData = new FormData();
      formData.append('image', this.selectedFile);
      formData.append('id', this.sellerId);

      console.log(this.selectedFile)

      axios.post(API_BASE_URL+"/api/v1/seller/product/save/contentImg", formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            console.log('이미지 업로드 성공', response.data.imgUrl);
            let imgUrl = "https://s3.ap-northeast-2.amazonaws.com/my.example.s3.bucket.bossi/"+response.data.imgUrl;
            this.imgUploadAll.push(response.data.imgUrl);
            console.log(imgUrl)
            this.addImageToEditor(imgUrl)
            this.imgDialog = false
            this.selectedFile = null
            this.previewURL = null
          })
          .catch(error => {
            console.error('이미지 업로드 에러', error);
          });
    },
    imgBtn(){
      this.imgDialog = true;
    },
    addImageToEditor(imageUrl) {
      //this.editor.chain().focus().setImage({ src: imageUrl }).run()
      this.editor.commands.setImage({src: imageUrl})
    },
    makeImageList(content){

      const parser = new DOMParser();
      const doc = parser.parseFromString(content, 'text/html');

      const imageTags = doc.querySelectorAll('img');
      const imgUrlList = Array.from(imageTags).map(img => img.src);

      return this.sliceImgUrl(imgUrlList);
    },
    replaceElement(element){
      return parseFloat(element.replace(/,/g, ''));
    },
    saveContent(){
      const {sellerId, category, title, rating, options, detailOption} = this;
      let price = parseFloat(this.price.replace(/,/g, ''));
      let ratingPrice = parseFloat(this.ratingPrice.replace(/,/g, ''));

      console.log(price)
      console.log(ratingPrice)
      const content = this.editor.getHTML();

     if(category === null || title === null || price === null || content === null || this.selectProductFile === null){
        alert('내용을 채워야 저장할 수 있습니다!')
     }else {

      let freeCount;
      if(this.checkFreeDeliveryFreePrice) {

        freeCount = parseFloat(this.freeCount.replace(/,/g, ''));
      }else {
        freeCount = -1
      }

      let deliveryCount;
      if(this.checkFreeDelivery) deliveryCount = 0;
      else deliveryCount = parseFloat(this.deliveryCount.replace(/,/g, ''));

      console.log(freeCount)

      let stockQuantity;
      if(this.noCount){
        stockQuantity =-1
      }else {
        stockQuantity = this.stockQuantity
        console.log(stockQuantity+detailOption)
      }


      let allImgUrlList = this.imgUploadAll;

      if(allImgUrlList.length === 0){
        console.log('비어있다')
        allImgUrlList.push("")
      }

      console.log(allImgUrlList)

      let imgUrlLists = this.makeImageList(content);

      console.log(imgUrlLists)
      console.log(rating);

      const formData = new FormData();

      console.log("==========")
      console.log(this.selectProductFile)

      //this.selectProductFile[0].forEach(image => formData.append('productImages', image))
      if(this.selectProductFile.length !== 0){
        for(let i = 0; i < this.selectProductFile[0].length; i++){
          console.log(this.selectProductFile[0][i])
          formData.append('productImages', this.selectProductFile[0][i])
        }
      }

      console.log(options)
      console.log(category)

      console.log([...formData.entries()]);
      // {category, title, price, rating, ratingPrice, deliveryCount, freeCount, options, detailOption, stockQuantity, content, imgUrlLists, allImgUrlList}
      axios.post(API_BASE_URL+"/api/v1/seller/product/create", {sellerId, category, title, price, rating, ratingPrice, deliveryCount, freeCount, options, detailOption, stockQuantity, content, imgUrlLists, allImgUrlList}, {

      })
          .then((res) => {
            console.log(res.data)
            let id = res.data

            if(this.selectProductFile.length !== 0){
              axios.post(API_BASE_URL+`/api/v1/seller/product/${id}/saveProductImage`, formData, {
                headers: {
                  'Content-Type': 'multipart/form-data'
                }
              })
                  .then((res) => {
                    console.log(res)
                    if(res.status === 200){
                      //this.$router.push() // 이미지 업로드 후 이동
                    }
                  })
            }
          })
          .catch((res) => {
            console.log(res)
          })
      }
    },
    sliceImgUrl(arr){
      for(let i = 0; i < arr.length; i++){
        arr[i] = arr[i].substring(arr[i].indexOf("users"))
      }

      return arr;
    }
  },
  mounted() {
    this.editor  = new Editor({
      extensions: [
        StarterKit,
        Document,
        Paragraph,
        Text,
        Image,
        Dropcursor,
        Blockquote,
        TextStyle,
        Color
      ],
      content: ``,
    })

  },
  beforeUnmount() {
    this.editor.destroy()
  },
  watch: {
    checkFreeDelivery() {
      if(this.checkFreeDelivery) this.deliveryCount = 0;
    }
  }
})
</script>

<style lang="scss">
#basic {
  display: flex;
  height: 100vh;
  margin: 10px 12% 0 12%;
  flex-direction: column;
}

.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 3px solid rgba(#ff8485, 0.1);
  }
}

.ProseMirror:focus {
  outline: none;
}

.ProseMirror {
  min-height: 500px;
  max-height: 500px;
  margin: 0 10px;
  overflow: scroll;
}

.element {
  height: 500px; /* 원하는 높이로 설정 */
  width: 800px; /* 원하는 너비로 설정 */
}

.input-name {
  font-size: 18px;
  color: #3f3f3f;
}

.input-element {
  outline: none;
  border-radius: 4px;
  padding-left: 5px;
  border: 1px solid rgba(77, 81, 89, 0.32);
  height: 45px;
  width: 100%;
  margin-top: 5px;
}

.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  img {
    max-width: 100%;
    height: auto;

    &.ProseMirror-selectednode {
      outline: 3px solid #68CEF8;
    }
  }
}

.disabled-button {
  background-color: rgba(106,106,106,0.07)
}

.container-image {
  border: #4d5159 1px solid;
  height: 300px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  overflow: hidden;
  overflow-x: scroll;
}
</style>
