(window.webpackJsonp=window.webpackJsonp||[]).push([[17],{"/aY+":function(e,t,r){"use strict";var o=r("ckEY");r.n(o).a},BiX5:function(e,t,r){"use strict";var o={name:"BackToTop",props:{visibilityHeight:{type:Number,default:400},backPosition:{type:Number,default:0},customStyle:{type:Object,default:function(){return{right:"50px",bottom:"50px",width:"40px",height:"40px","border-radius":"4px","line-height":"45px",background:"#e7eaf1"}}},transitionName:{type:String,default:"fade"}},data:function(){return{visible:!1,interval:null,isMoving:!1}},mounted:function(){window.addEventListener("scroll",this.handleScroll)},beforeDestroy:function(){window.removeEventListener("scroll",this.handleScroll),this.interval&&clearInterval(this.interval)},methods:{handleScroll:function(){this.visible=window.pageYOffset>this.visibilityHeight},backToTop:function(){var e=this;if(!this.isMoving){var t=window.pageYOffset,r=0;this.isMoving=!0,this.interval=setInterval(function(){var o=Math.floor(e.easeInOutQuad(10*r,t,-t,500));o<=e.backPosition?(window.scrollTo(0,e.backPosition),clearInterval(e.interval),e.isMoving=!1):window.scrollTo(0,o),r++},16.7)}},easeInOutQuad:function(e,t,r,o){return(e/=o/2)<1?r/2*e*e+t:-r/2*(--e*(e-2)-1)+t}}},n=(r("/aY+"),r("KHd+")),i=Object(n.a)(o,function(){var e=this.$createElement,t=this._self._c||e;return t("transition",{attrs:{name:this.transitionName}},[t("div",{directives:[{name:"show",rawName:"v-show",value:this.visible,expression:"visible"}],staticClass:"back-to-ceiling",style:this.customStyle,on:{click:this.backToTop}},[t("svg",{staticClass:"Icon Icon--backToTopArrow",staticStyle:{height:"16px",width:"16px"},attrs:{width:"16",height:"16",viewBox:"0 0 17 17",xmlns:"http://www.w3.org/2000/svg","aria-hidden":"true"}},[t("title",[this._v("回到顶部")]),this._v(" "),t("g",[t("path",{attrs:{d:"M12.036 15.59c0 .55-.453.995-.997.995H5.032c-.55 0-.997-.445-.997-.996V8.584H1.03c-1.1 0-1.36-.633-.578-1.416L7.33.29c.39-.39 1.026-.385 1.412 0l6.878 6.88c.782.78.523 1.415-.58 1.415h-3.004v7.004z","fill-rule":"evenodd"}})])])])])},[],!1,null,"3256046b",null);t.a=i.exports},JNL0:function(e,t,r){"use strict";r.d(t,"d",function(){return n}),r.d(t,"e",function(){return i}),r.d(t,"f",function(){return s}),r.d(t,"a",function(){return a}),r.d(t,"h",function(){return c}),r.d(t,"b",function(){return l}),r.d(t,"c",function(){return p}),r.d(t,"g",function(){return u});var o=r("t3Un");function n(){return Object(o.a)({url:"/project/getAllProjects",method:"get"})}function i(e){return Object(o.a)({url:"/project/getProjectById",method:"get",params:{projectId:e}})}function s(e){return Object(o.a)({url:"/project/getProjectDetailById",method:"get",params:{projectId:e}})}function a(e){return Object(o.a)({url:"/project/insert",method:"post",data:e})}function c(e){return Object(o.a)({url:"/project/update",method:"post",data:e})}function l(e){return Object(o.a)({url:"/project/deleteProjectById",method:"get",params:{projectId:e}})}function p(e){return Object(o.a)({url:"/rateRange/deleteRateRange",method:"get",params:{rateRangeId:e}})}function u(e,t){return Object(o.a)({url:"/rateRange/getRateRange",method:"get",params:{projectId:e,investMoney:t}})}},ckEY:function(e,t,r){},z6Tb:function(e,t,r){"use strict";r.r(t);var o=r("P2sY"),n=r.n(o),i=r("JNL0"),s={projectId:"",projectName:"",investPeriod:"",publisher:"",guarantor:"",filingAgency:"",underwriter:"",preRaisedFunds:"",investorType:"",startAmount:"",salesMethod:"",sourceOfRepayment:"",investTarget:"",subscriptionMethod:"",consultPlace:"",subscriptionPeriod:"周一至周六，上午9点-12点；下午2点-7点",establishDate:"产品认购完成第一个工作日，具体以产品实际募集情况，不超过3个工作日",valueDate:"产品成立日【当日】，具体日期以实际情况为准（融资主体出具成立通知书）",investorFundsArrivalDate:"投资者投资到期后，本金及当期收益将于合同规定日期后的3个工作日内到账，到期日及投资者资金到帐日之间，不计利息。",rateRangeList:[{projectId:"",rateRangeId:"",startMoney:"",endMoney:"",rate:""}]},a={name:"ProjectDetail",components:{BackToTop:r("BiX5").a},props:{isEdit:{type:Boolean,default:!1}},data:function(){return{projectId:"",projectForm:n()({},s),saveBTLoading:!1,userListOptions:[],rules:{projectName:[{required:!0,message:"请填写项目名称",trigger:"blur"}],investPeriod:[{required:!0,message:"请填写投资期限",trigger:"blur"}]},myBackToTopStyle:{right:"50px",bottom:"50px",width:"40px",height:"40px","border-radius":"4px","line-height":"45px",background:"#e7eaf1"}}},computed:{},created:function(){this.hasPermission("project:update")&&(this.isEdit?(this.projectId=this.$route.params&&this.$route.params.id,this.getProjectDetail(this.projectId)):this.projectForm=n()({},s))},methods:{getProjectDetail:function(e){var t=this;Object(i.f)(e).then(function(e){t.projectForm=e.data}).catch(function(e){console.error(e)})},addProject:function(e){var t=this;this.saveBTLoading=!0,Object(i.a)(e).then(function(e){200===e.returnCode?(t.saveBTLoading=!1,t.$confirm("是否前往完善项目信息","新增项目成功",{confirmButtonText:"确定",cancelButtonText:"取消",type:"info"}).then(function(){t.$router.push({path:"/project/edit/"+e.data})}).catch(function(){t.$message.success("新增项目成功！"),t.$router.push({path:"/project/index"})})):(t.saveBTLoading=!1,t.$message.error(e.message),console.error(e))}).catch(function(e){t.saveBTLoading=!1,console.error(e),t.$message.error(e.message)})},submitForm:function(){var e=this;this.$refs.projectForm.validate(function(t){if(!t)return console.error("error submit!!"),!1;e.isEdit?e.update(e.projectForm):e.addProject(e.projectForm)})},resetForm:function(e){this.$refs[e].resetFields()},update:function(e){var t=this;this.saveBTLoading=!0,Object(i.h)(e).then(function(e){200===e.returnCode?(t.$message.success("更新项目信息成功！"),t.saveBTLoading=!1,t.$router.push({path:"/project/index"})):(t.saveBTLoading=!1,console.error(e),t.$message.error(e.message))}).catch(function(e){t.saveBTLoading=!1,t.$message.error("更新项目信息失败"),console.error(e)})},addRange:function(){this.projectForm.rateRangeList.push({startMoney:"",endMoney:"",projectId:this.projectId,rateRangeId:"",rate:""})},removeRange:function(e){var t=this;if(this.isEdit)this.$confirm("确定要删除该利息区间吗？","Warning",{confirmButtonText:"删除",showCancelButton:!0,type:"warning"}).then(function(){Object(i.c)(e.rateRangeId).then(function(e){200===e.returnCode?(t.$message.success("删除成功！"),t.getProjectDetail(t.projectId)):(console.error(e),t.$message.error(e.message))}).catch(function(e){console.error(e),t.$message.error(e.message)})});else{var r=this.projectForm.rateRangeList.indexOf(e);-1!==r&&this.projectForm.rateRangeList.splice(r,1)}}}},c=r("KHd+"),l=Object(c.a)(a,function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-row",[r("el-col",{attrs:{span:20,offset:2}},[r("el-form",{ref:"projectForm",staticClass:"demo-ruleForm",attrs:{model:e.projectForm,rules:e.rules,"label-position":"top"}},[r("h4",[e._v("产品基本信息")]),e._v(" "),r("el-form-item",{attrs:{label:"项目名称",prop:"projectName"}},[r("el-input",{model:{value:e.projectForm.projectName,callback:function(t){e.$set(e.projectForm,"projectName",t)},expression:"projectForm.projectName"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"投资期限",prop:"investPeriod"}},[r("el-input",{model:{value:e.projectForm.investPeriod,callback:function(t){e.$set(e.projectForm,"investPeriod",t)},expression:"projectForm.investPeriod"}})],1),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"发行人",prop:"publisher"}},[r("el-input",{model:{value:e.projectForm.publisher,callback:function(t){e.$set(e.projectForm,"publisher",t)},expression:"projectForm.publisher"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"担保人",prop:"guarantor"}},[r("el-input",{model:{value:e.projectForm.guarantor,callback:function(t){e.$set(e.projectForm,"guarantor",t)},expression:"projectForm.guarantor"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"备案机构",prop:"filingAgency"}},[r("el-input",{model:{value:e.projectForm.filingAgency,callback:function(t){e.$set(e.projectForm,"filingAgency",t)},expression:"projectForm.filingAgency"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"承销商",prop:"underwriter"}},[r("el-input",{model:{value:e.projectForm.underwriter,callback:function(t){e.$set(e.projectForm,"underwriter",t)},expression:"projectForm.underwriter"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"预募集资金规模",prop:"preRaisedFunds"}},[r("el-input",{model:{value:e.projectForm.preRaisedFunds,callback:function(t){e.$set(e.projectForm,"preRaisedFunds",t)},expression:"projectForm.preRaisedFunds"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"投资者类型",prop:"investorType"}},[r("el-input",{model:{value:e.projectForm.investorType,callback:function(t){e.$set(e.projectForm,"investorType",t)},expression:"projectForm.investorType"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"认购起点金额",prop:"startAmount"}},[r("el-input",{model:{value:e.projectForm.startAmount,callback:function(t){e.$set(e.projectForm,"startAmount",t)},expression:"projectForm.startAmount"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"销售方式",prop:"salesMethod"}},[r("el-input",{model:{value:e.projectForm.salesMethod,callback:function(t){e.$set(e.projectForm,"salesMethod",t)},expression:"projectForm.salesMethod"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"还款来源",prop:"sourceOfRepayment"}},[r("el-input",{attrs:{type:"textarea"},model:{value:e.projectForm.sourceOfRepayment,callback:function(t){e.$set(e.projectForm,"sourceOfRepayment",t)},expression:"projectForm.sourceOfRepayment"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"投资标的",prop:"investTarget"}},[r("el-input",{model:{value:e.projectForm.investTarget,callback:function(t){e.$set(e.projectForm,"investTarget",t)},expression:"projectForm.investTarget"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"认购方式",prop:"subscriptionMethod"}},[r("el-input",{attrs:{type:"textarea"},model:{value:e.projectForm.subscriptionMethod,callback:function(t){e.$set(e.projectForm,"subscriptionMethod",t)},expression:"projectForm.subscriptionMethod"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"咨询认购地点",prop:"consultPlace"}},[r("el-input",{model:{value:e.projectForm.consultPlace,callback:function(t){e.$set(e.projectForm,"consultPlace",t)},expression:"projectForm.consultPlace"}})],1):e._e(),e._v(" "),e.isEdit?r("h4",[e._v("认购日期和收益起始计算")]):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"认购期间",prop:"subscriptionPeriod"}},[r("el-input",{model:{value:e.projectForm.subscriptionPeriod,callback:function(t){e.$set(e.projectForm,"subscriptionPeriod",t)},expression:"projectForm.subscriptionPeriod"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"产品成立日",prop:"establishDate"}},[r("el-input",{model:{value:e.projectForm.establishDate,callback:function(t){e.$set(e.projectForm,"establishDate",t)},expression:"projectForm.establishDate"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"产品起息日",prop:"valueDate"}},[r("el-input",{model:{value:e.projectForm.valueDate,callback:function(t){e.$set(e.projectForm,"valueDate",t)},expression:"projectForm.valueDate"}})],1):e._e(),e._v(" "),e.isEdit?r("el-form-item",{attrs:{label:"投资者资金到帐日",prop:"investorFundsArrivalDate"}},[r("el-input",{model:{value:e.projectForm.investorFundsArrivalDate,callback:function(t){e.$set(e.projectForm,"investorFundsArrivalDate",t)},expression:"projectForm.investorFundsArrivalDate"}})],1):e._e(),e._v(" "),e._l(e.projectForm.rateRangeList,function(t,o){return r("el-form-item",{key:o,attrs:{label:"利息区间 ["+o+"]",prop:"rateRangeList."+o+".rate",rules:{required:!0,message:"利息区间信息不能为空",trigger:"blur"}}},[r("el-row",{attrs:{gutter:30}},[r("el-col",{attrs:{span:8}},[r("el-form-item",[r("el-input",{attrs:{placeholder:"起始金额"},model:{value:t.startMoney,callback:function(r){e.$set(t,"startMoney",e._n(r))},expression:"range.startMoney"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:8}},[r("el-form-item",[r("el-input",{attrs:{placeholder:"结束金额"},model:{value:t.endMoney,callback:function(r){e.$set(t,"endMoney",e._n(r))},expression:"range.endMoney"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:6}},[r("el-form-item",[r("el-input",{attrs:{placeholder:"区间利率"},model:{value:t.rate,callback:function(r){e.$set(t,"rate",r)},expression:"range.rate"}})],1)],1),e._v(" "),r("el-col",{attrs:{span:2}},[r("el-button",{attrs:{type:"danger",icon:"el-icon-delete",size:"small"},on:{click:function(r){return r.preventDefault(),e.removeRange(t)}}})],1)],1)],1)}),e._v(" "),r("br"),e._v(" "),r("el-form-item",[r("el-row",{staticClass:"row-bg",attrs:{type:"flex"}},[r("el-col",{attrs:{span:4}},[r("el-button",{attrs:{type:"info",size:"medium",icon:"el-icon-plus"},on:{click:e.addRange}},[e._v("新增利息区间")])],1),e._v(" "),r("el-col",{attrs:{span:4,push:14}},[r("el-button",{attrs:{type:"primary",loading:e.saveBTLoading,size:"medium",icon:"el-icon-success"},on:{click:function(t){return e.submitForm("projectForm")}}},[e._v("保存")])],1),e._v(" "),r("el-col",{attrs:{span:4,push:13}},[r("el-button",{attrs:{type:"warning",size:"medium",icon:"el-icon-refresh"},on:{click:function(t){return e.resetForm("projectForm")}}},[e._v("重置")])],1)],1)],1),e._v(" "),r("el-tooltip",{attrs:{placement:"top",content:"回到顶部"}},[r("back-to-top",{attrs:{"custom-style":e.myBackToTopStyle,"visibility-height":300,"back-position":50,"transition-name":"fade"}})],1)],2)],1)],1)},[],!1,null,"086f7c53",null);t.default=l.exports}}]);
//# sourceMappingURL=17.00bc31d6613f192b9933.js.map