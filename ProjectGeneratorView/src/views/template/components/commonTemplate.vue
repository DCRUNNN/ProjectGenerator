<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="components-container">
        <el-form>
          <el-form-item>
            <el-row :gutter="15" type="flex">
              <el-col :span="6">
                <el-button type="primary" size="small" icon="el-icon-check"
                           @click.native.prevent="saveTemplateDialogVisible=true">保存
                </el-button>
                <el-button type="primary" size="small" icon="el-icon-plus"
                           @click.native.prevent="createFirstLevelFileDialogVisible=true">新建文件&nbsp;/&nbsp;文件夹
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
        <split-pane split="vertical">
          <template slot="paneL">
            <split-pane split="horizontal">
              <template slot="paneL">
                <el-scrollbar style="height:100%">
                  <div class="top-container">
                    <div class="custom-tree-container">
                      <div class="block">
                        <p>项目模板</p>
                        <!--  TODO 完善这里的拖拽逻辑（文件夹不能拖到文件下方）-->
                        <el-tree :data="templateDTO.content"
                                 :props="defaultProps"
                                 node-key="id"
                                 @node-click="handleNodeClick"
                                 default-expand-all
                                 draggable
                        :expand-on-click-node="false">
                        <span class="custom-tree-node" slot-scope="{ node, data }">
                          <span>
                            <i :class="data.fileType===1 ? 'el-icon-folder' : 'el-icon-document'"
                               style="margin-right: 5px"></i>
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
                <div class="bottom-container">
                  <div>
                    <el-table :data="paramList"
                              @selection-change="handleParamSelectionChange"
                              v-loading.body="listLoading"
                              ref="paramListTable"
                              element-loading-text="全力加载中..."
                              stripe border fit highlight-current-row>
                      <el-table-column type="selection" width="55">
                      </el-table-column>
                      <el-table-column label="参数中文名称" align="center" prop="name" min-width="60%">
                        <template slot-scope="scope">
                          <i class="el-icon-connection"></i>
                          <span v-text="scope.row.nameCN"></span>
                        </template>
                      </el-table-column>
                      <el-table-column label="参数英文名称" align="center" prop="name" min-width="60%">
                        <template slot-scope="scope">
                          <i class="el-icon-connection"></i>
                          <span v-text="scope.row.nameEN"></span>
                        </template>
                      </el-table-column>
                      <el-table-column label="参数简介" align="center" prop="description" min-width="50%">
                        <template slot-scope="scope">
                          <i class="el-icon-info"></i>
                          <span v-text="scope.row.description"></span>
                        </template>
                      </el-table-column>
                      <el-table-column label="参数类型" align="center" prop="name" min-width="60%">
                        <template slot-scope="scope">
                          <i :class="scope.row.type===1? 'el-icon-star-on': 'el-icon-star-off'"></i>
                          <span v-text="scope.row.type===1?'模板公参':'模板私参'"></span>
                        </template>
                      </el-table-column>
                      <el-table-column label="字段类型" align="center" prop="name" min-width="60%">
                        <template slot-scope="scope">
                          <i class=el-icon-magic-stick></i>
                          <span v-text="scope.row.fieldType"></span>
                        </template>
                      </el-table-column>
                    </el-table>
                    <div class="pagination-container">
                      <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-size="size"
                        :total="total"
                        :page-sizes="[10, 30, 50, 100]"
                        background
                        layout="total, sizes, prev, pager, next, jumper">
                      </el-pagination>
                    </div>
                  </div>
                </div>
              </template>
            </split-pane>
          </template>
          <template slot="paneR">
            <div class="right-container">
              <el-card class="editor-box">
                <div slot="header" class="clearfix">
                  <span>文件内容：</span>
                  <el-tag effect="dark">{{ currentFile.name }}</el-tag>
                  <el-button @click="saveFileContent" icon="el-icon-check" style="float: right; padding: 3px 0"
                             type="text">保存
                  </el-button>
                </div>
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

      <el-dialog title="新建第一级文件/文件夹" :visible.sync="createFirstLevelFileDialogVisible" width="20%">
        <el-form :model="createFileForm" :rules="createFileFormRules" ref="createFirstLevelFileForm"
                 label-position="right"
                 label-width="80px">
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
            <el-button @click="resetForm('createFirstLevelFileForm'); createFirstLevelFileDialogVisible = false">取消
            </el-button>
            <el-button type="primary" @click="createFirstLevelFile('createFirstLevelFileForm')">确定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog title="新建文件/文件夹" :visible.sync="createFileDialogVisible" width="30%">
        <el-form :model="createFileForm" :rules="createFileFormRules" ref="createFileForm" label-position="right"
                 label-width="80px">
          <el-form-item label="文件类型" prop="fileType">
            <el-select v-model="createFileForm.fileType" placeholder="请选择新建的文件类型">
              <el-option label="文件夹" value=1></el-option>
              <el-option label="文件" value=2></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="文件名称" prop="name">
            <el-input v-model="createFileForm.name"></el-input>
          </el-form-item>
          <!-- TODO 增加language联动-->
          <el-form-item>
            <el-button @click="resetForm('createFileForm'); createFileDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="createFile('createFileForm')">确定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog title="保存模板"
                 :visible.sync="saveTemplateDialogVisible">
        <el-form status-icon class="small-space" label-position="left" label-width="100px"
                 style="width: 500px; margin-left:40px;"
                 :model="templateDTO"
                 :rules="saveTemplateRules"
                 ref="saveTemplateForm">
          <el-form-item label="模板名称" prop="name">
            <el-input type="text" prefix-icon="el-icon-info" auto-complete="off" v-model="templateDTO.name"/>
          </el-form-item>
          <el-form-item label="模板简介" prop="description">
            <el-input type="text" prefix-icon="el-icon-edit" auto-complete="off" v-model="templateDTO.description"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="saveTemplateDialogVisible = false"
                     icon="el-icon-error" style="float:left">取消
          </el-button>
          <el-button type="success" :loading="saveTemplateBtnLoading"
                     icon="el-icon-success"
                     @click.native.prevent="saveTemplate">保存
          </el-button>
        </div>
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
  import MyEditor from '../../../components/MonacoEditor'
  import {listAllPublicParams} from '@/api/param'
  import {getByTemplateID, insertTemplate, updateTemplate} from '@/api/template'

  const defaultTemplateData = [{
    id: 1,
    name: 'main',
    fileType: 1,
    children: [{
      id: 4,
      name: 'java',
      fileType: 1,
      children: [{
        id: 9,
        name: 'ProjectGeneratorServerMain.java',
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
      }]
  }];

  const defaultTemplateDTO = {
    id: 0,
    name: '',
    description: '',
    author: '',
    contentID: 0,
    content: JSON.parse(JSON.stringify(defaultTemplateData)),
    paramList: [],
    createTime: '',
    updateTime: '',
  };

  let id = 1000;
  export default {
    name: 'CommonTemplate',
    components: {splitPane, MyEditor},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    computed: {},
    watch: {},
    data() {
      return {
        templateDTO: {},
        editor: null,
        currentContentHasChanged: false,
        currentFile: {
          name: "请点击左侧文件以查看文件内容",
          fileType: 1,
          language: "plaintext",
          content: "请点击左侧文件以查看文件内容",
        },
        currentNode: null,
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        createFileDialogVisible: false,
        createFirstLevelFileDialogVisible: false,
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
        // 参数
        paramList: [],
        listLoading: false,
        total: 0,
        page: 1,
        size: 10,

        // 保存模板
        saveTemplateDialogVisible: false,
        saveTemplateBtnLoading: false,
        saveTemplateRules: {
          name: [
            {required: true, trigger: 'blur', message: "请输入模板名称"}
          ],
          description: [
            {required: true, trigger: 'blur', message: "请输入模板简介"}
          ]
        }
      }
    },
    mounted() {

    },
    created() {
      this.listAllPublicParams();
      if (this.isEdit) {
        this.templateID = this.$route.params && this.$route.params.id
        this.getTemplateByID(this.templateID)
      } else {
        this.templateDTO = Object.assign({}, defaultTemplateDTO);
      }
    },
    methods: {
      // 点击保存按钮
      saveFileContent() {
        this.currentFile.content = this.$refs.monacoEditor.getEditorValue()
        // TODO 设置this.currentFile.language
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
      createFirstLevelFile(formName) {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            this.$message({
              type: 'waring',
              message: '请检查输入'
            });
            this.createFirstLevelFileDialogVisible = false
            return false;
          }
          if (this.createFileForm.fileType === "1") { // 文件夹
            const newFile = {
              id: id++,
              name: this.createFileForm.name,
              fileType: 1,
              children: []
            };
            if (!this.templateDTO.content) {
              this.$set(this.templateDTO, 'content', []);
            }
            this.templateDTO.content.push(newFile);
            this.createFirstLevelFileDialogVisible = false
          } else if (this.createFileForm.fileType === "2") { // 文件
            const newFile = {
              id: id++,
              name: this.createFileForm.name,
              fileType: 2,
              content: "new new new",
              language: "plaintext",
              children: []
            };
            if (!this.templateDTO.content) {
              this.$set(this.templateDTO, 'content', []);
            }
            this.templateDTO.content.push(newFile);
            this.createFirstLevelFileDialogVisible = false
          }
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
      getTemplateByID(templateID) {
        getByTemplateID(templateID).then(response => {
          let resultTemplateDTO = response.data
          this.templateDTO = {
            id: resultTemplateDTO.id,
            name: resultTemplateDTO.name,
            description: resultTemplateDTO.description,
            author: resultTemplateDTO.author,
            contentID: resultTemplateDTO.contentID,
            content: JSON.parse(resultTemplateDTO.content),
            paramList: resultTemplateDTO.paramList,
            createTime: resultTemplateDTO.createTime,
            updateTime: resultTemplateDTO.createTime,
          }
          this.$nextTick(function () {
            this.setSelectParams()
          });
        }).catch(err => {
          console.error(err)
        })
      },
      saveTemplate() {
        this.saveTemplateBtnLoading = true;
        this.$refs.saveTemplateForm.validate(valid => {
          if (!valid) {
            this.saveTemplateBtnLoading = false;
            return
          }
          if (!this.isEdit) {
            // TODO set template author
            insertTemplate(this.templateDTO).then(response => {
              if (response.returnCode === 200) {
                this.$message.success('保存模板成功');
                this.saveTemplateDialogVisible = false;
                this.saveTemplateBtnLoading = false;
              } else {
                this.$message.error(response.message);
                console.error(response);
                this.saveTemplateBtnLoading = false
              }
            }).catch(error => {
              console.error(error);
              this.saveTemplateBtnLoading = false;
            })
          } else {
            updateTemplate(this.templateDTO).then(response => {
              if (response.returnCode === 200) {
                this.$message.success('更新模板成功');
                this.saveTemplateDialogVisible = false;
                this.saveTemplateBtnLoading = false;
              } else {
                this.$message.error(response.message);
                console.error(response);
                this.saveTemplateBtnLoading = false
              }
            }).catch(error => {
              console.error(error);
              this.saveTemplateBtnLoading = false;
            })
          }
        })
      },
      // 参数
      listAllPublicParams() {
        this.listLoading = true;
        listAllPublicParams(this.page, this.size).then(response => {
          if (response.returnCode === 200) {
            this.paramList = response.data.list;
            this.total = response.data.total;
            this.listLoading = false;
          } else {
            this.$message.error(response.message);
            console.error(response);
          }
        }).catch(error => {
          this.$message.error(error.message);
          console.error(error);
        })
      },
      setSelectParams() {
        let selectedParamIDSet = new Set()
        this.templateDTO.paramList.forEach(p => selectedParamIDSet.add(p.id))
        this.paramList.forEach(row => {
          if (selectedParamIDSet.has(row.id)) {
            this.$refs.paramListTable.toggleRowSelection(row, true);
          }
        });
      },
      handleParamSelectionChange(val) {
        this.templateDTO.paramList = val;
      },
      handleSizeChange(size) {
        this.size = size;
        this.page = 1;
        this.listLogsByType()
      },
      handleCurrentChange(page) {
        this.page = page;
        this.listLogsByType()
      },
    }
  }
</script>
