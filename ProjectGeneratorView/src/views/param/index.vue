<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-row :gutter="15" type="flex">
            <el-col :span="6">
              <el-button type="primary" size="small" icon="el-icon-refresh"
                         @click.native.prevent="refresh">刷新
              </el-button>
              <el-button type="primary" size="small" icon="el-icon-plus"
                         @click.native.prevent="showCreateParamDialog">新增
              </el-button>
            </el-col>
            <el-col :span="8" :offset="10">
              <el-input
                v-model="searchText"
                size="medium"
                placeholder="输入参数名进行搜索..."/>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <el-table :data="paramList.filter(data => !searchText
                    || data.name.toLowerCase().includes(searchText.toLowerCase()))"
                v-loading.body="listLoading"
                element-loading-text="全力加载中..."
                stripe border fit highlight-current-row>
        <el-table-column label="参数ID" align="center" prop="id" min-width="40%">
          <template slot-scope="scope">
            <i class="el-icon-user"></i>
            <span v-text="scope.row.id"></span>
          </template>
        </el-table-column>
        <el-table-column label="参数中文名称" align="center" prop="nameCN" min-width="60%">
          <template slot-scope="scope">
            <i class="el-icon-place"></i>
            <span v-text="scope.row.nameCN"></span>
          </template>
        </el-table-column>
        <el-table-column label="参数英文名称" align="center" prop="nameEN" min-width="60%">
          <template slot-scope="scope">
            <i class="el-icon-place"></i>
            <span v-text="scope.row.nameEN"></span>
          </template>
        </el-table-column>
        <el-table-column label="参数简介" align="center" prop="description" min-width="50%">
          <template slot-scope="scope">
            <i class="el-icon-chat-dot-round"></i>
            <span v-text="scope.row.description"></span>
          </template>
        </el-table-column>
        <el-table-column label="参数类型" align="center" prop="type" min-width="50%">
          <template slot-scope="scope">
            <i class="el-icon-chat-dot-round"></i>
            <span v-text="scope.row.type===1?'模板公参':'模板私参'"></span>
          </template>
        </el-table-column>
        <el-table-column label="字段类型" align="center" prop="type" min-width="50%">
          <template slot-scope="scope">
            <i class="el-icon-chat-dot-round"></i>
            <span v-text="scope.row.fieldType"></span>
          </template>
        </el-table-column>
        <el-table-column label="创建日期" align="center" sortable prop="createTime" min-width="70%">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span v-text="scope.row.createTime"></span>
          </template>
        </el-table-column>
        <el-table-column label="修改日期" align="center" sortable prop="updateTime" min-width="70%">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span v-text="scope.row.updateTime"></span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="primary" size="mini" icon="el-icon-document"
                         @click.native.prevent="showUpdateParamDialog(scope.row)">修改
              </el-button>
              <el-button type="warning" size="mini" icon="el-icon-delete"
                         :loading="deleteParamBtnLoading"
                         @click.native.prevent="deleteParam(scope.row.nameEN, scope.row.id)">删除
              </el-button>
            </el-button-group>
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

      <el-dialog :title="textMap[dialogStatus]"
                 :visible.sync="createParamDialogVisible">
        <el-form status-icon class="small-space" label-position="left" label-width="100px"
                 style="width: 500px; margin-left:40px;"
                 :model="tempParam"
                 :rules="createParamRules"
                 ref="tempParamForm">
          <el-form-item label="参数中文名" prop="nameCN">
            <el-input type="text" prefix-icon="el-icon-info" auto-complete="off"
                      v-model="tempParam.nameCN"/>
          </el-form-item>
          <el-form-item label="参数英文名" prop="nameEN">
            <el-input type="text" prefix-icon="el-icon-info" auto-complete="off"
                      v-model="tempParam.nameEN"/>
          </el-form-item>
          <el-form-item label="参数简介" prop="description">
            <el-input type="text" prefix-icon="el-icon-edit" auto-complete="off"
                      v-model="tempParam.description"/>
          </el-form-item>
          <el-form-item label="参数类型" prop="bankAccount">
            <el-tag effect="dark"><span v-text="tempParam.type===1?'模板公参':'模板私参'"></span></el-tag>
          </el-form-item>
          <el-form-item label="字段类型" prop="bankAccount">
            <el-select v-model="tempParam.fieldType" placeholder="请选择字段类型" size="mini">
              <el-option
                v-for="item in fieldTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                <span style="float: left">{{ item.label }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="createParamDialogVisible = false"
                     icon="el-icon-error" style="float:left">取消
          </el-button>
          <el-button type="danger"
                     v-if="dialogStatus === 'add'"
                     icon="el-icon-refresh"
                     style="float:left"
                     @click="$refs['tempParamForm'].resetFields()">重置
          </el-button>
          <el-button type="success"
                     v-if="dialogStatus === 'add'"
                     :loading="createParamBtnLoading"
                     icon="el-icon-success"
                     @click.native.prevent="insertPublicParam">添加
          </el-button>
          <el-button type="primary"
                     v-else
                     :loading="createParamBtnLoading"
                     icon="el-icon-success"
                     @click.native.prevent="updatePublicParam">更新
          </el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<style>

</style>

<script>
  import { listAllPublicParams, insertPublicParam, updatePublicParam, deletePublicParam} from '@/api/param'

  export default {
    name: 'ParamManage',
    components: {},
    data() {
      return {
        dialogStatus: 'add',
        textMap: {
          update: '更新参数',
          add: '新增参数'
        },
        tempParam: {
          id: '',
          nameCN: '',
          nameEN: '',
          description: '',
          type: '',
          fieldType: '',
          createTime: '',
          updateTime: '',
        },
        createParamRules: {
          nameCN: [
            {required: true, trigger: 'blur', message: "请输入参数中文名"}
          ],
          nameEN: [
            {required: true, trigger: 'blur', message: "请输入参数英文名"}
          ],
          description: [
            {required: true, trigger: 'blur', message: "请输入参数简介"}
          ],
          fieldType: [
            {required: true, trigger: 'blur', message: "请选择参数字段类型"}
          ],
        },
        fieldTypeList: [
          {value: 'string', label: '字符串'},
          {value: 'number', label: '数字类型'},
          {value: 'array', label: '数组'},
        ],
        createParamDialogVisible: false,
        createParamBtnLoading: false,
        deleteParamBtnLoading: false,
        searchText: '',
        paramList: [],
        listLoading: false,
        total: 0,
        page: 1,
        size: 10,
      }
    },
    mounted() {

    },
    created() {
      this.listAllPublicParams();
    },
    methods: {
      refresh() {
        this.listAllPublicParams();
        this.$message.success('已是最新');
      },
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
      showCreateParamDialog() {
        // 显示新增对话框
        this.createParamDialogVisible = true;
        this.dialogStatus = 'add';
        this.tempParam.id = 0;
        this.tempParam.nameCN = '';
        this.tempParam.nameEN = '';
        this.tempParam.description = '';
        this.tempParam.fieldType = '';
        this.tempParam.type = 1;
        this.tempParam.createTime = '';
        this.tempParam.updateTime = '';
      },
      showUpdateParamDialog(param) {
        // 显示更新对话框
        this.createParamDialogVisible = true;
        this.dialogStatus = 'update';
        this.tempParam.id = param.id;
        this.tempParam.nameCN = param.nameCN;
        this.tempParam.nameEN = param.nameEN;
        this.tempParam.description = param.description;
        this.tempParam.fieldType = param.fieldType;
        this.tempParam.type = param.type;
        this.tempParam.createTime = param.createTime;
        this.tempParam.updateTime = param.updateTime;
      },
      insertPublicParam(param) {
        this.createParamBtnLoading = true;
        this.$refs.tempParamForm.validate(valid => {
          if (!valid) {
            this.createParamBtnLoading = false;
            return
          }
          insertPublicParam(this.tempParam).then(response => {
            if (response.returnCode === 200) {
              this.$message.success('新增公共参数成功');
              this.listAllPublicParams();
              this.createParamDialogVisible = false;
              this.createParamBtnLoading = false;
            } else {
              this.$message.error(response.message);
              console.error(response);
              this.createParamBtnLoading = false
            }
          }).catch(error => {
            console.error(error);
            this.createParamBtnLoading = false;
          })
        })
      },
      updatePublicParam() {
        this.createParamBtnLoading = true;
        this.$refs.tempParamForm.validate(valid => {
          if (!valid) {
            this.createParamBtnLoading = false;
            return
          }
          updatePublicParam(this.tempParam).then(response => {
            if (response.returnCode === 200) {
              this.$message.success('修改公共参数成功');
              this.listAllPublicParams();
              this.createParamDialogVisible = false;
              this.createParamBtnLoading = false;
            } else {
              this.$message.error(response.message);
              console.error(response);
              this.createParamBtnLoading = false
            }
          }).catch(error => {
            console.error(error);
            this.createParamBtnLoading = false;
          })
        })
      },
      deleteParam(paramNameEN, paramID) {
        this.$confirm('您确认要删除 ' + paramNameEN + ' 参数吗?', 'Warning', {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteParamBtnLoading = true
          deletePublicParam(paramID).then(response => {
            if (response.returnCode === 200) {
              this.$message.success('删除公共参数成功');
              this.deleteParamBtnLoading = false
              this.listAllPublicParams();
            } else {
              this.$message.error(response.message);
              this.deleteParamBtnLoading = false
              console.error(response);
            }
          }).catch(error => {
            console.error(error);
            this.deleteParamBtnLoading = false
          })
        });
      },
      handleSizeChange(size) {
        this.size = size;
        this.page = 1;
        this.listAllPublicParams()
      },
      handleCurrentChange(page) {
        this.page = page;
        this.listAllPublicParams()
      },
    }
  }
</script>
