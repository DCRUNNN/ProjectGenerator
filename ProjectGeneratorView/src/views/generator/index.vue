<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="components-container">
        <el-row type="flex">
          <el-col :span="10">
            <el-steps :active="3" align-center>
              <el-step title="步骤 1" icon="el-icon-s-data" description="选择项目模板"></el-step>
              <el-step title="步骤 2" icon="el-icon-edit" description="填写模板参数"></el-step>
              <el-step title="步骤 3" icon="el-icon-download" description="生成项目代码"></el-step>
            </el-steps>
            <el-form label-position="top" label-width="120px">
              <el-form-item label="1. 选择项目模板">
                <el-select v-model="templateID" size="mini" placeholder="请选择模板" @change="getFormSchema">
                  <el-option
                    v-for="item in allTemplates"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                    <span style="float: left">{{ item.name }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.description }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="2. 填写模板参数">
                <el-card shadow="always">
                  <ncform :form-schema="formSchema" form-name="projectGeneratorForm" v-model="formSchema.value">
                  </ncform>
                </el-card>
              </el-form-item>
              <el-form-item label="3. 生成项目代码">
                <el-button type="primary" icon="el-icon-download" @click.native.prevent="generateProject()">创建项目</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>

<script>
  import {listAllTemplate} from '@/api/template'
  import {getFormSchema, generateProject} from '@/api/generator'
  import { downloadFile } from '@/api/file'

  export default {
    name: "generator",
    components: {},
    computed: {},
    watch: {},
    data() {
      return {
        templateID: '',
        allTemplates: [],
        formSchema: {
          "type": "object",
          "properties": {
            "name": {
              "type": "string"
            },
            "email": {
              "type": "string"
            },
            "age": {
              "type": "integer"
            },
            "adult": {
              "type": "boolean"
            }
          }
        }
      }
    },
    mounted() {

    },
    created() {
      this.listAllTemplates()
    },
    methods: {
      listAllTemplates() {
        listAllTemplate(0, 10000).then(response => {
          if (response.returnCode === 200) {
            this.allTemplates = response.data.list;
          } else {
            this.$message.error(response.message);
            console.error(response);
          }
        }).catch(error => {
          this.$message.error(error.message);
          console.error(error);
        })
      },
      getFormSchema() {
        getFormSchema(this.templateID).then(response => {
          this.formSchema = response.data
        }).catch(error => {
          this.$message.error(error.message);
          console.error(error);
        })
      },
      generateProject() {
        this.$ncformValidate('projectGeneratorForm').then(data => {
          if (data.result) {
            let postParamValueList = []
            for (let [key, value] of Object.entries(this.$data.formSchema.value)) {
              postParamValueList.push({
                name: key,
                value: value
              })
            }
            let projectDTO = {
              templateID: this.templateID,
              paramValueDTOList: postParamValueList
            }
            generateProject(projectDTO).then(response => {
              if (response.returnCode === 200) {
                this.$message.success('创建工程成功，准备下载文件');
                console.log(response)
                this.downloadFile(response.data.targetZipPath, response.data.downloadFileName)
              } else {
                this.$message.error(response.message);
                console.error(response);
              }
            }).catch(error => {
              this.$message.error(error.message);
              console.error(error);
            })
          }
        })
      },
      downloadFile(targetFileName, downloadFileName) {
        downloadFile(targetFileName, downloadFileName).then(response => {
          let blob = new Blob([response]);
          if (window.navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, downloadFileName);
          } else {
            let link = document.createElement("a");
            let evt = document.createEvent("HTMLEvents");
            evt.initEvent("click", false, false);
            link.href = URL.createObjectURL(blob);
            link.download = downloadFileName;
            link.style.display = "none";
            document.body.appendChild(link);
            link.click();
            window.URL.revokeObjectURL(link.href);
          }
        }).catch(error => {
          this.$message.error("无法下载文件");
          console.error(error);
        })
      },
    }
  }
</script>
