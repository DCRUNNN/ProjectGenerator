<template>
  <div class="myEditor">
    <p>
      <el-row>
        <el-col :span="12">
          语言：
          <el-select
            v-model="language"
            clearable
            placeholder="请选择"
            size="mini"
            @change="languageChange">
            <el-option
              v-for="item in sets.languageOption"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="12">
          主題：
          <el-select v-model="theme" size="mini"
                     placeholder="请选择"
                     @change="themeChange">
            <el-option
              v-for="item in sets.themeOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
    </p>
    <div id="container" ref="container" style="height:600px"></div>
  </div>
</template>
<script>
  import * as monaco from 'monaco-editor';

  export default {
    props: {
      codes: {
        type: String,
        default: function () {
          return '文件夹不允许编辑内容'
        }
      },
      language: {
        type: String,
        default: function () {
          return 'plaintext'
        }
      },
      readOnly: {
        type: Boolean,
        default: function () {
          return true
        }
      },
      editorOptions: {
        type: Object,
        default: function () {
          return {
            selectOnLineNumbers: true,
            roundedSelection: false,
            readOnly: true,         // 只读
            cursorStyle: 'line',        //光标样式
            automaticLayout: false, //自动布局
            glyphMargin: true,  //字形边缘
            useTabStops: false,
            fontSize: 28,       //字体大小
            autoIndent: true,//自动布局
            //quickSuggestionsDelay: 500,   //代码提示延时
          }
        }
      }
    },
    data() {
      return {
        sets: {
          languageOption: {
            'css': 'css',
            'dockerfile': 'dockerfile',
            'go': 'go',
            'html': 'html',
            'ini': 'ini',
            'java': 'java',
            'javascript': 'javascript',
            'json': 'json',
            'markdown': 'markdown',
            'mysql': 'mysql',
            'plaintext': 'plaintext',
            'python': 'python',
            'shell': 'shell',
            'sql': 'sql',
            'typescript': 'typescript',
            'xml': 'xml',
            'yaml': 'yaml'
          },
          themeOption: [
            {
              value: 'vs',
              label: '默认'
            },
            {
              value: 'vs-dark',
              label: '深色'
            },
            {
              value: 'hc-black',
              label: '高亮'
            },
          ],
        },
        theme: 'vs-dark',
        codesCopy: null,//内容备份
        monacoEditorInstance: null,
      }
    },
    watch: {
      codes() {
        if (this.codes !== this.monacoEditorInstance.getValue()) {
          this.monacoEditorInstance.setValue(this.codes);
        }
      },
      language(newValue, oldValue) {
        this.initEditor()
      },
      readOnly(newValue, oldValue) {
        this.initEditor()
      }
    },
    mounted() {
      this.initEditor();
    },
    methods: {
      initEditor() {
        let self = this;
        if (self.monacoEditorInstance !== null) {
          self.monacoEditorInstance.dispose()
        }
        self.$refs.container.innerHTML = '';
        self.monacoEditorInstance = monaco.editor.create(self.$refs["container"], {
          value: self.codesCopy || self.codes,
          language: self.language,
          theme: self.theme,//vs, hc-black, or vs-dark
          readOnly: this.readOnly, // 只读
          selectOnLineNumbers: true,
          roundedSelection: false,
          cursorStyle: 'line',        //光标样式
          automaticLayout: false, //自动布局
          glyphMargin: true,  //字形边缘
          useTabStops: false,
          fontSize: 20,       //字体大小
          autoIndent: true,//自动布局
          //editorOptions: self.editorOptions,
        });
        self.$emit('onMounted', self.monacoEditorInstance);//编辑器创建完成回调
        self.monacoEditorInstance.onDidChangeModelContent(function (event) {//编辑器内容changge事件
          self.codesCopy = self.monacoEditorInstance.getValue();
          self.$emit('onCodeChange', self.monacoEditorInstance.getValue(), event);
        });
        //编辑器随窗口自适应
        window.addEventListener('resize', function () {
          initEditor();
        })
      },
      getEditorValue() {
        return this.monacoEditorInstance.getValue()
      },
      themeChange(val) {
        this.initEditor();
      },
      languageChange(val) {
        this.initEditor();
        // alert(val)
        // this.editor.setModelLanguage(this.monacoEditorInstance.getModel(), val)
      }
    }
  }
</script>
<style scoped>
  #container {
    height: 100%;
    text-align: left;
  }
</style>
