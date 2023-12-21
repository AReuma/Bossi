<template>
  <div>
  <div v-if="editor">
    <v-btn text outlined @click="editor.chain().focus().toggleBold().run()" :disabled="!editor.can().chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }">
     <B>B</B>
    </v-btn>
    <v-btn text outlined @click="editor.chain().focus().toggleItalic().run()" :disabled="!editor.can().chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }">
      <i style="font-family: Baskerville, sans-serif" >I</i>
    </v-btn>
    <v-btn text outlined @click="editor.chain().focus().toggleStrike().run()" :disabled="!editor.can().chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }">
      <del>T</del>
    </v-btn>
    <v-btn text outlined  @click="editor.chain().focus().toggleCode().run()" :disabled="!editor.can().chain().focus().toggleCode().run()" :class="{ 'is-active': editor.isActive('code') }">
      <v-icon>
        mdi-code-brackets
      </v-icon>
    </v-btn>
    <v-btn @click="editor.chain().focus().unsetAllMarks().run()">
      <v-icon>mdi-pen-remove</v-icon>
    </v-btn>
<!--    <v-btn @click="editor.chain().focus().clearNodes().run()">
      clear nodes
    </v-btn>-->
<!--    <v-btn @click="editor.chain().focus().setParagraph().run()" :class="{ 'is-active': editor.isActive('paragraph') }">
      paragraph
      paragraph
    </v-btn>-->
    <v-btn @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }">
      h1
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }">
      h2
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }">
      h3
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleHeading({ level: 4 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 4 }) }">
      h4
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleHeading({ level: 5 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 5 }) }">
      h5
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleHeading({ level: 6 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 6 }) }">
      h6
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }">
      <v-icon>mdi-format-list-bulleted-type</v-icon>
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }">
      <v-icon>mdi-format-list-numbered</v-icon>
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'is-active': editor.isActive('codeBlock') }">
      <v-icon>mdi-code-braces</v-icon>
    </v-btn>
    <v-btn @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'is-active': editor.isActive('blockquote') }">
      <v-icon>mdi-format-quote-open</v-icon>
    </v-btn>
    <v-btn @click="editor.chain().focus().setHorizontalRule().run()">
      <v-icon>mdi-minus</v-icon>
      구분선
    </v-btn>
    <v-btn @click="editor.chain().focus().setHardBreak().run()">
      hard break br 태그
    </v-btn>
    <v-btn @click="editor.chain().focus().undo().run()" :disabled="!editor.can().chain().focus().undo().run()">
      <v-icon>mdi-arrow-u-left-top</v-icon>
    </v-btn>
    <v-btn @click="editor.chain().focus().redo().run()" :disabled="!editor.can().chain().focus().redo().run()">
      <v-icon>mdi-arrow-u-right-top</v-icon>
    </v-btn>

    <v-btn @click="addImage">
      <v-icon>mdi-image-plus-outline</v-icon>
    </v-btn>
  </div>
  <editor-content :editor="editor" />

    <v-btn @click="saveContent">저장</v-btn>
  </div>
</template>

<script>
import StarterKit from '@tiptap/starter-kit'
import Document from '@tiptap/extension-document'
import Dropcursor from '@tiptap/extension-dropcursor'
import Image from '@tiptap/extension-image'
import Paragraph from '@tiptap/extension-paragraph'
import Text from '@tiptap/extension-text'
import { Editor, EditorContent } from '@tiptap/vue-2'
import {defineComponent} from "vue";

export default defineComponent({
  name: "SellerProductCreateCopy",
  components: {
    EditorContent
  },
  data() {
    return {
      editor: null,
    };
  },
  methods: {
    addImage() {
      const url = window.prompt('URL')

      if (url) {
        this.editor.chain().focus().setImage({ src: url }).run()
      }
    },
    saveContent(){
      console.log()
    }
  },
  mounted() {
    this.editor = new Editor({
      extensions: [
        StarterKit,
        Document,
        Paragraph,
        Text,
        Image,
        Dropcursor,
      ],
      content: `
        <h2>
          Hi there,
        </h2>
        <p>
          this is a <em>basic</em> example of <strong>tiptap</strong>. Sure, there are all kind of basic text styles you’d probably expect from a text editor. But wait until you see the lists:
        </p>
        <ul>
          <li>
            That’s a bullet list with one …
          </li>
          <li>
            … or two list items.
          </li>
        </ul>
        <p>
          Isn’t that great? And all of that is editable. But wait, there’s more. Let’s try a code block:
        </p>
        <pre><code class="language-css">body {
  display: none;
}</code></pre>
        <p>
          I know, I know, this is impressive. It’s only the tip of the iceberg though. Give it a try and click a little bit around. Don’t forget to check the other examples too.
        </p>
        <blockquote>
          Wow, that’s amazing. Good work, boy! 👏
          <br />
          — Mom
        </blockquote>
      `,
    })
  },
  beforeUnmount() {
    this.editor.destroy()
  },
})
</script>

<style scoped>
.editor {
  border: 1px solid #ccc;
  padding: 8px;
}
</style>

<style lang="scss" scoped>
</style>