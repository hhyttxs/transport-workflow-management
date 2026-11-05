import Cookies from 'js-cookie'
import {getUser} from '@/utils/auth'
import {forEach, hasOneOf} from "@/libs/tools";
import {hasChild} from "@/libs/util";

// 把下拉菜单放入表单莫模板中
export const setSelectItems = function (formInfo, rowName, selectItems) {
  for (var i = 0; i < formInfo.length; i++) {
    if (formInfo[i]['value'] == rowName) {
      formInfo[i]['addtion']['selectItems'] = selectItems
    }
  }
}

export const setSearchSelectItems = function (searchTable, rowName, selectItems) {
  for (var i = 0; i < searchTable.length; i++) {
    let inList = searchTable[i]
    for (let j = 0; j < inList.length; j++) {
      if (inList[j]['value'] == rowName) {
        inList[j]['items'] = selectItems
      }
    }
  }
}

// 判断列表是否包含某个对象
export const arrayContain = function (array, obj) {
  for (var i = 0; i < array.length; i++) {
    var arrayItem = array[i]
    if (arrayItem == obj) {
      return true
    }
  }
   return false //开发阶段把权限先暂时去掉 gaoyang
  //return true
}

/**
 * 把数据库查出的信息放入列表中
 * @param data
 * @param form
 * @param trans
 */
export const saveDataToForm = function (data, form, trans) {
  for (var i = 0, keys = Object.keys(data); i < keys.length; i++) {
    var key = keys[i]
    var newKey = key
    if (trans) {
      if (trans[key]) {
        newKey = trans[key]
      }
    }

    if (typeof data[key] === 'number') {
      form[newKey] = data[key] + ''
    } else {
      form[newKey] = data[key]
    }
  }
}

export const getMenuByRouter = (list, access) => {
  let res = []
  forEach(list, item => {
    if (!item.meta || (item.meta && !item.meta.hideInMenu)) {
      let obj = {
        header: item.name,
        path: item.path,
        title: ''
      }
      if(item.meta && item.meta.title){
        obj.title = item.meta.title
      }
      if ((hasChild(item) || (item.meta && item.meta.showAlways)) && showThisMenuEle(item, access)) {
        obj.children = getMenuByRouterChild(item,item.children, access)
      }
      if (item.meta && item.meta.href) obj.href = item.meta.href
      if (showThisMenuEle(item, access)) res.push(obj)
    }
  })
  return res
}

export const getMenuByRouterChild = (parent,list, access) => {
  let res = []
  forEach(list, item => {
    if (!item.meta || (item.meta && !item.meta.hideInMenu)) {
      let obj = {
        header: item.name,
        path: parent.path + item.path,
        title: ''
      }
      if(item.meta && item.meta.title){
        obj.title = item.meta.title
      }
      if ((hasChild(item) || (item.meta && item.meta.showAlways)) && showThisMenuEle(item, access)) {
        obj.children = getMenuByRouterChild(item,item.children, access)
      }
      if (item.meta && item.meta.href) obj.href = item.meta.href
      if (showThisMenuEle(item, access)) res.push(obj)
    }
  })
  return res
}


const showThisMenuEle = (item, access) => {
  if (item.meta && item.meta.access && item.meta.access.length) {
    if (hasOneOf(item.meta.access, access)) return true
    else return false
  } else return true
}

/**
 * 处理拿到respons后的操作
 * @type {{dealResponse: responseUtil.dealResponse, isResponseSuccess(*): boolean}}
 */
export const responseUtil = {
  /**
   * 最原始的处理response的操作
   * @param that
   * @param response
   * @param success
   * @param error
   */
  dealResponseNative: function (that, response, success, error) {
    if (this.isResponseSuccess(response)) {
      if (success) {
        success()
      }
    } else {
      if (error) {
        error()
      }
    }
  },
  /**
   * 处理response,错误后弹出异常信息
   * @param that
   * @param response
   * @param success
   */
  dealResponse (that, response, success, config) {
      that.$store.state.buttonStatus = false
    var title = '提示'
    var content = response.msg
    content = content.substr(0, 20)
    if (config) {
      if (config['title']) title = config['title']
      if (config['content']) content = config['content']
    }

    var error = () => {
      that.$Notice.config({
        top: 106,
        duration: 2
      })

      that.$Notice.error({
        title: title,
        desc: content
      })
      // that.$Modal.error({
      //   title: title,
      //   content: content
      // })
    }

    this.dealResponseNative(that, response, success, error)
  },
  /**
   * 成功后弹出提示并直接跳转
   * @param that
   * @param response
   * @param index
   */
  dealResponseAndJump (that, response, index, config) {    var title = '操作成功'
    var content = response.msg
    var errorTitle = '提示'
    var errorContent = response.msg

    if (config) {
      if (config['title']) title = config['title']
      if (config['content']) content = config['content']
      if (config['errorTitle']) errorTitle = config['errorTitle']
      if (config['errorContent']) errorContent = config['errorContent']
    }

    var errorInfo = {
      title: errorTitle,
      content: errorContent
    }

    var success = () => {
      that.$Notice.config({
        top: 106,
        duration: 2
      });

      that.$Notice.success({
        title: title,
        desc: ''
      });
      // that.$Modal.success({
      //   title: title,
      //   content: content
      // })
    }

    that.$router.push({ name: index })

    this.dealResponse(that, response, success, errorInfo)
  },
  /**
   * 判断是response是否成功
   * @param response
   * @returns {boolean}
   */
  isResponseSuccess (response) {
    if (response.code == "0") {
      return true
    }
    return false
  },

  alertErrMsg (obj,msg) {
    obj.$Notice.config({
      top: 25,
      duration: 2
    });

    obj.$Notice.error({
      title: '异常提示',
      desc: msg
    });
  },

  alertMsg (obj,msg) {
    obj.$Notice.config({
      top: 25,
      duration: 2
    });

    obj.$Notice.warning({
      title: '提示',
      desc: msg
    });
  },
  alertOK (obj) {
    obj.$Notice.config({
      top: 95,
      duration: 2
    });

    obj.$Notice.success({
      title: '提示',
      desc: '操作成功'
    });
  }
}

/**
 * 获取用户的user_id
 * @returns {string}
 */
export const getUserId = function () {
  // return Cookies.get('staff_id') ? Cookies.get('staff_id') + '' : '-1'
  return getUser().staff_id ? getUser().staff_id : "-1"
}

export const getStaffId = function () {
  // return Cookies.get('staff_id') ? Cookies.get('staff_id') + '' : '-1'
  return getUser().staff_id ? getUser().staff_id : "-1"
}

export const globaluserId = function () {
    // return Cookies.get('user_id') ? Cookies.get('user_id') + '' : '-1'
  return getUser().user_id ? getUser().user_id : "-1"
}

export const getUserName = function () {
    // return Cookies.get('user') ? Cookies.get('user') + '' : '-1'
  return getUser().staffName ? getUser().staffName : '-1'
}

export const saveListInfo = (that, info) => {
  that.tableData = info.child
  that.page.pageCount = info.pageCount
  that.page.totalPage = info.totalpage
}

export const formUtil = {
  addFormItem (that, beforeItemName, addItem) {
    for (var i = 0; i < that.formInfo.length; i++) {
      var obj = that.formInfo[i]
      if (obj.value == beforeItemName) {
        let beforePart = that.formInfo.slice(0, i + 1)
        let endPart = that.formInfo.slice(i + 1, that.formInfo.length)

        that.formInfo = [...beforePart, addItem, ...endPart]

        break
      }
    }
  },

  removeFormItem (that, itemName) {
    for (var i = 0; i < that.formInfo.length; i++) {
      var obj = that.formInfo[i]
      if (obj.value == itemName) {
        that.formInfo.splice(i, 1)
      }
    }
  }

}


// 把list分组
export const list2groupList = function (list, rowCount) {
  var groupList = []//searchTable
  var tempList = []


  for (var i = 0; i < list.length; i++) {
    if (i % rowCount == 0) {
      tempList = []
      groupList.push(tempList)
    }
    var item = list[i]
    tempList.push(item)
  }

  return groupList
}

// 把list分组
export const groupList2list = function (list) {
  var result = []
  for(let i = 0 ; i < list.length ; i++){
    result = [...(list[i]),...result]
  }
  return result
}
/**
 * @description 格式化金额
 * @param number：要格式化的数字
 * @param decimals：保留几位小数 默认0位
 * @param decPoint：小数点符号 默认.
 * @param thousandsSep：千分位符号 默认为,
 */
export const money = (number) => {
  if(!number) return "0.00"
  var num = "";
  if(number){
    num = (number+ '').replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,'$1,');
    var xsd=num.toString().split(".");
    if(xsd.length==1){
      num=num.toString()+".00";
    }
    if(xsd.length>1) {
      if (xsd[1].length < 2) {
        num = num.toString() + "0";
      }
    }
  }

  return num;
}

/**
 * 反解开EMOJI编码后的字符串 与上对应使用
 * @param str
 * @returns {*}
 */
export const uncodeUtf16 = function (str) {
  var reg = /\&#.*?;/g;
  var result = str.replace(reg, function (char) {
    var H, L, code;
    if (char.length == 9) {
      code = parseInt(char.match(/[0-9]+/g));
      H = Math.floor((code - 0x10000) / 0x400) + 0xD800;
      L = (code - 0x10000) % 0x400 + 0xDC00;
      return unescape("%u" + H.toString(16) + "%u" + L.toString(16));
    } else {
      return char;
    }
  });
  return result;
}



export const objInit = function (obj) {
  var keys = Object.getOwnPropertyNames(obj)
  for(var i = 0 ; i <keys.length ; i++){
    var key = keys[i]
    if(key == '__ob__') continue;
    obj[key] = ""
  }
}

export const backToHomePage = function (routes) {
  Cookies.set('user', '')
  Cookies.set('token', '')
  Cookies.set('user_id', '')
  window.location.href = "#/home"
  // router.push({name: 'login'})
}

export const authButtons = function(buttons,authList){
  var notNullAuthList = authList || []
  var result = buttons.filter(({name})=>{
    return arrayContain(notNullAuthList,name);
  })
  return result
}

export const checkAuthList = function(authButtonsList, name){
  var authButtonsList = authButtonsList == undefined ? [] : authButtonsList
  for (var i = 0; i < authButtonsList.length; i++) {
    if (authButtonsList[i] == name) {
      return true
    }
  }
  return false //开发阶段把权限先暂时去掉 gaoyang
  // return true
}

var baseValidateCancel = function (form) {
  form.fields.forEach(field => {
    field.validateState=""
  })
}
/**
 * 验证失败后一段时间，validate消失
 * @param form
 */
export const delayValidateCancel = function(form){
  setTimeout(()=>form.fields.forEach(field => {
    field.validateState=""
  }),3000)
}


export const validateCancelNow = function(form){
  if(form instanceof Array)    form.map((item)=>baseValidateCancel(item))
                        else    baseValidateCancel(form)
}

export const addMonth = function(date, offset) {
    if (date instanceof Date && !isNaN(offset)) {
        let givenMonth = date.getMonth();
        let newMonth = givenMonth + offset;
        date.setMonth(newMonth);
        date.setDate(1);
        return date;
    }
}
export const CurrentMonthLast = function(date){

    let currentMonth=date.getMonth();
    let nextMonth=++currentMonth;
    let nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
    let oneDay=1000*60*60*24;
    let lastTime = new Date(nextMonthFirstDay-oneDay);
    let month = parseInt(lastTime.getMonth()+1);
    let day = lastTime.getDate();
    if (month < 10) {
        month = '0' + month
    }
    if (day < 10) {
        day = '0' + day
    }
    return date.getFullYear() + '-' + month + '-' + day;
}

export const getMapByDic = function(list){
  let dicMap = null
  if(list && list.length > 0){
      dicMap = list.reduce((obj, item) => {
      obj[item.dictionaryValue.toString()] = item
      return obj
    }, {})
  }
  return dicMap
}

export const closeThisView = function(that,path){
  let list = that.$store._state.data.admin.page.opened
  let thisRoute = that.$route
  let newList = list.filter((param)=>{
    return param.fullPath !== thisRoute.fullPath
  })
  that.$store._state.data.admin.page.opened = newList
  that.$router.push({path:path})
}

export const closeOtherView = function(that,path){
  let list = that.$store._state.data.admin.page.opened
  let newList = list.filter((param)=>{
    return param.fullPath !== path
  })
  that.$store._state.data.admin.page.opened = newList
}

