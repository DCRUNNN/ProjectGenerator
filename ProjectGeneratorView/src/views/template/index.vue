<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="components-container">
        <split-pane split="vertical">
          <template slot="paneL">
            <split-pane split="horizontal">
              <template slot="paneL">
                <el-scrollbar style="height:100%">
                  <div class="top-container">
                    <div class="custom-tree-container">
                      <div class="block">
                        <p>项目模板</p>
                        <el-tree :data="data"
                                 :props="defaultProps"
                                 node-key="id"
                                 @node-click="handleNodeClick"
                                 default-expand-all
                                 :expand-on-click-node="false">
                        <span class="custom-tree-node" slot-scope="{ node, data }">
                          <span>
                            <i :class="data.fileType===1 ? 'el-icon-folder' : 'el-icon-document'" style="margin-right: 5px"></i>
                            {{ node.label }}
                          </span>
                          <span>
                            <el-button
                              icon="el-icon-plus"
                              size="mini"
                              @click="createFileDialogVisible=true"
                              v-if="data.fileType===1"
                              type="text">
                            </el-button>
                            <el-button
                              icon="el-icon-edit"
                              size="mini"
                              @click="() => editFileName(node, data)"
                              type="text">
                            </el-button>
                            <el-button
                              icon="el-icon-delete"
                              size="mini"
                              @click="() =>  remove(node, data)"
                              type="text">
                            </el-button>
                          </span>
                        </span>
                        </el-tree>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </template>
              <template slot="paneR">
                <div class="bottom-container"/>
              </template>
            </split-pane>
          </template>
          <template slot="paneR">
            <div class="right-container">
              <el-card class="editor-box">
                <div slot="header" class="clearfix">
                  <span>文件内容</span>
                  <el-button @click="saveFileContent" icon="el-icon-check" style="float: right; padding: 3px 0"
                             type="text">保存
                  </el-button>
                </div>
                <!--                <prism-editor v-model="currentNode.content" language="js" :readonly="true"></prism-editor>-->
                <MyEditor
                  ref="monacoEditor"
                  :language="currentFile.language"
                  :codes="currentFile.content"
                  :readOnly="currentFile.fileType!==2"
                  @onMounted="onMounted"
                  @onCodeChange="onCodeChange"/>
              </el-card>
            </div>
          </template>
        </split-pane>
      </div>

      <el-dialog title="提示" :visible.sync="createFileDialogVisible" width="20%">
        <el-form :model="createFileForm" :rules="createFileFormRules" ref="createFileForm" label-position="right" label-width="80px">
          <el-form-item label="文件类型" prop="fileType">
            <el-select v-model="createFileForm.fileType" placeholder="请选择新建的文件类型">
              <el-option label="文件夹" value=1></el-option>
              <el-option label="文件" value=2></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="文件名称" prop="name">
            <el-input v-model="createFileForm.name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="resetForm('createFileForm'); createFileDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="createFile('createFileForm')">确定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 16px;
  }

  .components-container {
    position: relative;
    height: 100vh;
  }

  .right-container {
    height: 100%;
  }

  .top-container {
    width: 100%;
    height: 100%;
  }

  .bottom-container {
    width: 100%;
    background-color: #95E1D3;
    height: 100%;
  }

  .editor-box {
    height: 100%;
  }

  .el-form-item .el-select {
    width: 100%;
  }

</style>

<script>
  import splitPane from 'vue-splitpane'
  import PrismEditor from 'vue-prism-editor'
  import "prismjs";
  import "prismjs/themes/prism.css";
  import MyEditor from '../../components/MonacoEditor'

  let id = 1000;
  export default {
    components: {splitPane, PrismEditor, MyEditor},
    computed: {},
    watch: {},
    data() {
      const data = [{
        id: 1,
        name: 'main',
        fileType: 1,
        children: [{
          id: 4,
          name: 'java',
          fileType: 1,
          children: [{
            id: 9,
            name: 'ProjectGeneratorServerMain',
            language: "java",
            fileType: 2,
            content: "public static void main(String[] args) {\n"
              + "    System.out.Println(666);\n"
              + "}",
          }]
        }]
      }, {
        id: 2,
        name: 'resources',
        fileType: 1,
        children: [{
          id: 5,
          name: 'application.yaml',
          fileType: 2,
          language: "yaml",
          content: "active: dev"
        }, {
          id: 6,
          name: 'application-dev.yaml',
          fileType: 2,
          language: "yaml",
          content: "context-path: /ProjectGenerator/dev"
        },
          {
            id: 7,
            name: 'application-prod.yaml',
            fileType: 2,
            language: "yaml",
            content: "context-path: /ProjectGenerator/prod"
          },
          {
            id: 8,
            name: 'index.html',
            fileType: 2,
            language: "html",
            content: "<div>this is html</div>"
          },
          {
            id: 9,
            name: 'global.css',
            fileType: 2,
            language: "css",
            content: "body{}"
          }]
      }];
      return {
        data: JSON.parse(JSON.stringify(data)),
        editor: null,
        currentContentHasChanged: false,
        currentFile: {
          name: "",
          fileType: 1,
          language: "css",
          content: "请点击左侧文件以查看文件内容",
        },
        currentNode: null,
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        createFileDialogVisible: false,
        opts: {
          value: '',
          readOnly: false, // 是否可编辑
          language: 'plaintext', // 语言类型
          theme: 'vs-dark' // 编辑器主题
        },
        createFileForm: {
          name: '',
          fileType: '',
        },
        createFileFormRules: {
          name: [{required: true, trigger: 'blur', message: '文件名称不能为空'}],
          fileType: [{required: true, trigger: 'blur', message: '请选择要新建的文件类型'}]
        },
      }
    },
    mounted() {

    },
    methods: {
      // 点击保存按钮
      saveFileContent() {
        this.currentFile.content = this.$refs.monacoEditor.getEditorValue()
        this.currentContentHasChanged = false
      },
      onMounted() {

      },
      // editor内容改变自动获取值
      onCodeChange(val, event) {
        if (!event.isFlush) {
          if (this.currentFile.fileType === 2) {
            this.currentContentHasChanged = true
          }
        }
      },
      handleNodeClick(clickedFile, nodeObject, vueComponent) {
        if (this.currentFile === clickedFile) {
          return
        }
        if (!this.currentContentHasChanged) {
          this.currentFile = clickedFile
          return
        }
        if (this.currentFile.fileType !== 2) {
          this.currentFile = clickedFile;
          return
        }
        this.$confirm('您是否需要保存 [' + this.currentFile.name + '] 的内容?', 'Warning', {
          confirmButtonText: '保存',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.saveFileContent()
          this.currentFile = clickedFile
        }).catch(() => {
          this.currentFile = clickedFile
          this.currentContentHasChanged = false
        })
      },
      createFile(formName) {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            this.$message({
              type: 'waring',
              message: '请检查输入'
            });
            return false;
          }
          console.log(this.createFileForm)
          if (this.createFileForm.fileType === "1") { // 文件夹
            const newChild = {
              id: id++,
              name: this.createFileForm.name,
              fileType: 1,
              children: []
            };
            if (!this.currentFile.children) {
              this.$set(this.currentFile, 'children', []);
            }
            this.currentFile.children.push(newChild);
          } else if (this.createFileForm.fileType === "2") { // 文件
            const newChild = {
              id: id++,
              name: this.createFileForm.name,
              fileType: 2,
              content: "new new new",
              language: "plaintext",
              children: []
            };
            if (!this.currentFile.children) {
              this.$set(this.currentFile, 'children', []);
            }
            this.currentFile.children.push(newChild);
          }
          this.$refs[formName].resetFields()
          this.createFileDialogVisible = false
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      editFileName(node, data) {
        this.$prompt('请输入新文件名', '提示', {
          inputValue: data.name,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputErrorMessage: '输入不能为空',
          inputValidator: (value) => { // 点击按钮时，对文本框里面的值进行验证
            if (!value) {
              return '输入不能为空';
            }
          },
        }).then(({value}) => {
          data.name = value
          this.$message({
            type: 'success',
            message: '修改文件名成功！'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
      },
      remove(node, data) {
        const parent = node.parent;
        const children = parent.data.children || parent.data;
        const index = children.findIndex(d => d.id === data.id);
        children.splice(index, 1);
      },
    }
  }
</script>
