(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6594defa"],{"5c89":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=this,a=t.$createElement,n=t._self._c||a;return n("page-header-wrapper",{attrs:{title:"角色资源配置"}},[n("a-tabs",{staticStyle:{height:"80%"},attrs:{"default-active-key":"api",tabPosition:"top"},on:{change:t.tabChanelHandle}},[n("a-tab-pane",{key:"api"},[n("span",{attrs:{slot:"tab"},slot:"tab"},[n("a-icon",{attrs:{type:"api"}}),t._v(" 接口权限 ")],1),n("a-spin",{attrs:{spinning:t.loading}},[n("a-card",{attrs:{bordered:!1}},[n("a-col",{attrs:{span:10}},[n("a-col",{attrs:{span:10}},[n("a-tree",{attrs:{checkStrictly:!0,checkable:"","show-line":"",defaultExpandAll:"","tree-data":t.treeData.apiTree,replaceFields:t.treeFields},model:{value:t.apiSelectedKeys,callback:function(e){t.apiSelectedKeys=e},expression:"apiSelectedKeys"}})],1)],1)],1)],1)],1),n("a-tab-pane",{key:"menu"},[n("span",{attrs:{slot:"tab"},slot:"tab"},[n("a-icon",{attrs:{type:"menu"}}),t._v(" 菜单权限 ")],1),n("a-spin",{attrs:{spinning:t.loading}},[n("a-card",{attrs:{bordered:!1}},[n("a-col",{attrs:{span:10}},[n("a-tree",{attrs:{checkStrictly:!0,checkable:"","show-line":"",defaultExpandAll:"","tree-data":t.treeData.menuTree,replaceFields:t.treeFields},model:{value:t.menuSelectedKeys,callback:function(e){t.menuSelectedKeys=e},expression:"menuSelectedKeys"}},[n("a-icon",{attrs:{slot:"switcherIcon",type:"down"},slot:"switcherIcon"})],1)],1)],1)],1)],1),n("template",{slot:"tabBarExtraContent"},[n("a-button",{attrs:{size:"small",icon:"rollback"},on:{click:function(){e.$router.go(-1)}}},[t._v("返回")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-button",{attrs:{size:"small",icon:"reload"},on:{click:t.loadSelectedList}},[t._v("重置")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-button",{attrs:{size:"small",icon:"close"},on:{click:t.clearSelectedKeys}},[t._v("清空")]),n("a-divider",{attrs:{type:"vertical"}}),n("a-button",{attrs:{size:"small",type:"primary",icon:"check",loading:t.submitLoading},on:{click:t.handleOk}},[t._v("提交")])],1)],2)],1)},r=[],i=(a("99af"),a("4de4"),a("4160"),a("159b"),a("c119"),a("d865")),c=a("a498"),s=a("cc5e"),o=a("ca00"),l={name:"Permission",components:{Tree:i["a"]},data:function(){return{roleId:null,model:{},loading:!1,submitLoading:!1,menuSelectedKeys:[],apiSelectedKeys:[],treeData:{apiTree:[],menuTree:[]},treeFields:{key:"id",title:"name",value:"id"}}},methods:{loadTree:function(){var e=this;Object(c["c"])().then((function(t){e.treeData.menuTree=[],e.treeData.apiTree=[],Object(o["a"])(t.data.filter((function(e){return"MENU"===e.type||"BUTTON"===e.type})),e.treeData.menuTree,"-1"),Object(o["a"])(t.data.filter((function(e){return"API"===e.type})),e.treeData.apiTree,"-1")}))},loadSelectedList:function(){var e=this;this.loading=!0,this.roleId=this.$route.params.id,Object(s["b"])(this.roleId).then((function(t){e.model=t.data,t.data&&t.data.resourceList&&(e.menuSelectedKeys=[],e.apiSelectedKeys=[],t.data.resourceList.forEach((function(t){"MENU"===t.type?e.menuSelectedKeys.push(t.id):e.apiSelectedKeys.push(t.id)}))),e.loading=!1}))},handleOk:function(){var e=this;this.loading=!0,this.submitLoading=!0;var t={id:this.roleId};t.resourceIdList=(this.menuSelectedKeys.checked||this.menuSelectedKeys).concat(this.apiSelectedKeys.checked||this.apiSelectedKeys),Object(s["f"])(t).then((function(t){e.loading=!1,e.$message.info("修改成功"),e.submitLoading=!1,e.$router.push({path:"/system/role"})}))},clearSelectedKeys:function(){this.menuSelectedKeys=[],this.apiSelectedKeys=[]},tabChanelHandle:function(e){}},created:function(){this.loadTree(),this.loadSelectedList()}},d=l,u=a("2877"),p=Object(u["a"])(d,n,r,!1,null,null,null);t["default"]=p.exports},a498:function(e,t,a){"use strict";a.d(t,"c",(function(){return i})),a.d(t,"a",(function(){return c})),a.d(t,"d",(function(){return s})),a.d(t,"b",(function(){return o}));a("99af");var n=a("b775"),r={PageInfo:"/api/resource/page",Route:"/api/resource/routes",Update:"/api/resource",AllList:"/api/resource/list",Delete:"/api/resource"};function i(e){return Object(n["b"])({url:r.AllList,method:"get",params:e})}function c(e){return Object(n["b"])({url:r.Update,method:"post",data:e})}function s(e){return Object(n["b"])({url:r.Update,method:"put",data:e})}function o(e){return Object(n["b"])({url:"".concat(r.Delete,"/").concat(e),method:"delete"})}},cc5e:function(e,t,a){"use strict";a.d(t,"d",(function(){return i})),a.d(t,"b",(function(){return c})),a.d(t,"f",(function(){return s})),a.d(t,"a",(function(){return o})),a.d(t,"e",(function(){return l})),a.d(t,"c",(function(){return d}));var n=a("b775"),r={RoleList:"/api/role/page",Permission:"/api/role/resources",Role:"/api/role"};function i(e){return Object(n["b"])({url:r.RoleList,method:"get",params:e})}function c(e){return Object(n["b"])({url:r.Permission,method:"get",params:{id:e}})}function s(e){return Object(n["b"])({url:r.Permission,method:"put",data:e})}function o(e){return Object(n["b"])({url:r.Role,method:"post",data:e})}function l(e){return Object(n["b"])({url:r.Role,method:"put",data:e})}function d(e){return Object(n["b"])({url:"".concat(r.Role,"/list"),method:"get",params:e})}}}]);