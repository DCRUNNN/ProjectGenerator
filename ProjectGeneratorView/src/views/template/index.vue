<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-row :gutter="15" type="flex">
            <el-col :span="6">
              <el-button type="primary" size="small" icon="el-icon-refresh"
                         @click.native.prevent="">刷新
              </el-button>
              <router-link :to="'/template/create'">
                <el-button type="primary" size="small" icon="el-icon-plus">新增
                </el-button>
              </router-link>
            </el-col>
            <el-col :span="8" :offset="10">
              <el-input
                v-model="searchText"
                size="medium"
                placeholder="输入模板名进行搜索..."/>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <el-table :data="templateList.filter(data => !searchText
                    || data.name.toLowerCase().includes(searchText.toLowerCase()))"
                v-loading.body="listLoading"
                element-loading-text="全力加载中..."
                stripe border fit highlight-current-row>
        <el-table-column label="模板ID" align="center" prop="id" min-width="40%">
          <template slot-scope="scope">
            <i class="el-icon-user"></i>
            <span v-text="scope.row.id"></span>
          </template>
        </el-table-column>
        <el-table-column label="模板名称" align="center" prop="name" min-width="60%">
          <template slot-scope="scope">
            <i class="el-icon-place"></i>
            <span v-text="scope.row.name"></span>
          </template>
        </el-table-column>
        <el-table-column label="模板简介" align="center" prop="description" min-width="50%">
          <template slot-scope="scope">
            <i class="el-icon-chat-dot-round"></i>
            <span v-text="scope.row.description"></span>
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
              <router-link :to="'/template/edit/'+scope.row.id">
                <el-button type="primary" size="mini" icon="el-icon-document">修改</el-button>
              </router-link>
              <el-button type="warning" size="mini" icon="el-icon-delete"
                         :loading="deleteTemplateBtnLoading"
                         @click.native.prevent="deleteTemplate(scope.row.id, scope.row.name)">删除
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
    </div>
  </div>
</template>

<style>

</style>

<script>
import {listAllTemplate, deleteByTemplateID} from '@/api/template'

export default {
  name: 'TemplateManage',
  components: {},
  data() {
    return {
      searchText: '',
      templateList: [],
      listLoading: false,
      deleteTemplateBtnLoading: false,
      total: 0,
      page: 1,
      size: 10,
    }
  },
  mounted() {

  },
  created() {
    this.listAllTemplate();
  },
  methods: {
    listAllTemplate() {
      this.listLoading = true;
      listAllTemplate(this.page, this.size).then(response => {
        if (response.returnCode === 200) {
          this.templateList = response.data.list;
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
    deleteTemplate(templateID, templateName) {
      this.$confirm('您确认要删除 ' + templateName + ' 模板吗？', 'Warning', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteTemplateBtnLoading = true
        deleteByTemplateID(templateID).then(response => {
          if (response.returnCode === 200) {
            this.$message.success('删除模板成功！')
            this.deleteTemplateBtnLoading = false
            this.listAllTemplate();
          } else {
            this.$message.error(response.message)
            this.deleteTemplateBtnLoading = false
            console.log(response)
          }
        }).catch(error => {
          this.$message.error(error.message);
          console.error(error);
          this.deleteTemplateBtnLoading = false
        })
      });
    },
    handleSizeChange(size) {
      this.size = size;
      this.page = 1;
      this.listAllTemplate()
    },
    handleCurrentChange(page) {
      this.page = page;
      this.listAllTemplate()
    },
  }
}
</script>
