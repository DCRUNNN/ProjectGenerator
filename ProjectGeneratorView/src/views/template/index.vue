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
                          <span>{{ node.label }}</span>
                          <span>
                            <el-button
                              icon="el-icon-plus"
                              size="mini"
                              @click="() => append(data)"
                              v-if="data.fileType===1"
                              type="text">
                            </el-button>
                            <el-button
                              icon="el-icon-edit"
                              size="mini"
                              @click="() => append(data)"
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
                <div class="bottom-container" />
              </template>
            </split-pane>
          </template>
          <template slot="paneR">
            <div class="right-container">
              <el-card class="editor-box">
                <div slot="header" class="clearfix">
                  <span>文件内容</span>
                  <el-button icon="el-icon-check" style="float: right; padding: 3px 0" type="text">保存</el-button>
                </div>
                <prism-editor v-model="currentNode.content" language="js" :readonly="true"></prism-editor>
              </el-card>

            </div>
          </template>
        </split-pane>
      </div>
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

</style>

<script>
  import splitPane from 'vue-splitpane'
  import PrismEditor from 'vue-prism-editor'
  import "prismjs";
  import "prismjs/themes/prism.css";

  let id = 1000;
  export default {
    components: { splitPane, PrismEditor },
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
            fileType: 2,
            content: "System.out.Println(666)",
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
          content: "active: dev"
        }, {
          id: 6,
          name: 'application-dev.yaml',
          fileType: 2,
          content: "context-path: /ProjectGenerator/dev"
        },
        {
          id: 7,
          name: 'application-prod.yaml',
          fileType: 2,
          content: "context-path: /ProjectGenerator/prod"
        }]
      }];
      return {
        data: JSON.parse(JSON.stringify(data)),
        currentNode: {
          content: "请点击左侧文件以查看文件内容"
        },
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    mounted() {

    },
    methods: {
      handleNodeClick(clickedFile, nodeObject, vueComponent) {
        this.currentNode = clickedFile
      },

      append(data) {
        const newChild = {id: id++, name: 'testtest', fileType:2, content:"test file content", children: []};
        if (!data.children) {
          this.$set(data, 'children', []);
        }
        data.children.push(newChild);
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
