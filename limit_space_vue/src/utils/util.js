import config from '@/utils/config'
import {getUserId} from "@/utils/rongxunUtil";

/**
 * 日期转换成 2016-8-31形式
 * @param date
 * @returns {string}
 */
var dateToString = function (date) {
    
    var year = date.getFullYear()
    var month = date.getMonth() + 1
    if(Number(month) < 10){
        month = '0'+ month
    }
    var day = date.getDate()
    if(Number(day) < 10){
        day = '0'+ day
    }
    return year + '-' + month + '-' + day;
};

/**
 * 日期加上任意天数或减去任意天数
 * @param date
 * @param dayCount
 * @returns {Date}
 */
var dateAddDate = function (date, dayCount) {
    var tempDate = dateToDate(date);
    var count = parseInt(dayCount);
    var oldYear = tempDate.getFullYear();
    var oldMonth = tempDate.getMonth();
    var oldDate = tempDate.getDate();
    var newDate = oldDate + count;
    return new Date(oldYear, oldMonth, newDate)
};

/**
 * 把字符串转换成Date，如果是Date类型，则不转换。2016-8-31
 * @param date
 * @returns {Date}
 */
function dateToDate(date) {
    var sDate = new Date();
    if (typeof date == 'object' && typeof new Date().getMonth == "function") {
        sDate = date;
    } else if (typeof date == "string") {
        var arr = date.split('-');
        if (arr.length == 3) {
            sDate = new Date(arr[0] + '-' + arr[1] + '-' + arr[2]);
        }
    }
    return sDate;
};

function isValidDate(date) {
    return date instanceof Date && !isNaN(date.getTime())
}

/**
 * 日期加上任意月份
 * @param date
 * @param monthCount
 * @returns {Date}
 */
var dateAddMonth = function (date, monthCount) {
    if (!isValidDate(date)) {
        return '';
    }
    var tempDate = dateToDate(date);
    var count = parseInt(monthCount);
    if (count < 0) {
        return new Date();
    }
    var oldYear = tempDate.getFullYear();
    var oldMonth = tempDate.getMonth();
    var oldDate = tempDate.getDate();
    var newMonth = oldMonth + count;
    var newDate = new Date(oldYear, newMonth, oldDate);

    //防止月份数不一致，进行微调
    let flagType = 0;
    while (newDate.getMonth() != (newMonth % 12)) {
        flagType = 1;
        oldDate--;
        newDate = new Date(oldYear, newMonth, oldDate);
    }
    if(flagType == 1){
        newDate = new Date(oldYear, newMonth, oldDate + 1)
    }
    return newDate;
};

var dateAddMonthOther = function (date, monthCount) {
    if (!isValidDate(date)) {
        return '';
    }
    var tempDate = dateToDate(date);
    var count = parseInt(monthCount);
    if (count < 0) {
        return new Date();
    }
    var oldYear = tempDate.getFullYear();
    var oldMonth = tempDate.getMonth();
    var oldDate = tempDate.getDate();
    var newMonth = oldMonth + count;
    var newDate = new Date(oldYear, newMonth, oldDate);

    //防止月份数不一致，进行微调
    while (newDate.getMonth() != (newMonth % 12)) {
        oldDate--;
        newDate = new Date(oldYear, newMonth, oldDate);
    }
    return newDate;
};

var dateAddMonth2 = function (date, monthCount) {
    if (!isValidDate(date)) {
        return '';
    }
    var tempDate = dateToDate(date);
    var count = parseInt(monthCount);
    if (count < 0) {
        return new Date();
    }
    var oldYear = tempDate.getFullYear();
    var oldMonth = tempDate.getMonth();
    var oldDate = tempDate.getDate();
    var newMonth = oldMonth + count;
    var newDate = new Date(oldYear, newMonth, oldDate);

    return newDate;
};

/**
 * 日期加减年
 * @param date
 * @param yearCount
 * @returns {Date}
 */
var dateAddYear = function (date, yearCount) {
    var tempDate = dateToDate(date);
    var count = parseInt(yearCount);
    var oldYear = tempDate.getFullYear();
    var oldMonth = tempDate.getMonth();
    var oldDate = tempDate.getDate();
    var newYear = oldYear + count;
    var newDate = new Date(newYear, oldMonth, oldDate);
    //防止月份数不一致，进行微调
    while (newDate.getMonth() != oldMonth) {
        oldDate--;
        newDate = new Date(newYear, oldMonth, oldDate);
    }
    return newDate;
}
export default {
    dateAddYear: dateAddYear,
    dateAddMonth: dateAddMonth,
    dateAddMonthOther: dateAddMonthOther,
    dateAddMonth2: dateAddMonth2,
    dateAddDay: dateAddDate,
    dateToString: dateToString,
    strToDate: function (s) {
        s = s.replace(/-/g, "/");
        return new Date(s);
    },
    dateFormat: function (date, fmt) {
        if (date && date != '') {
            fmt = fmt || 'YYYY-mm-dd';
            date = new Date(date);
            let ret;
            const opt = {
                "Y+": date.getFullYear().toString(),        // 年
                "m+": (date.getMonth() + 1).toString(),     // 月
                "d+": date.getDate().toString(),            // 日
                "H+": date.getHours().toString(),           // 时
                "M+": date.getMinutes().toString(),         // 分
                "S+": date.getSeconds().toString()          // 秒
                // 有其他格式化字符需求可以继续添加，必须转化成字符串
            };
            for (let k in opt) {
                ret = new RegExp("(" + k + ")").exec(fmt);
                if (ret) {
                    fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
                }
                ;
            }
            ;
            return fmt;
        } else {
            return '';
        }

    },
    otherDateFormat: function (date, fmt) {
        if (date && date != '') {
            fmt = fmt || 'YYYY-mm-dd HH:MM:SS';
            date = new Date(date);
            let ret;
            const opt = {
                "Y+": date.getFullYear().toString(),        // 年
                "m+": (date.getMonth() + 1).toString(),     // 月
                "d+": date.getDate().toString(),            // 日
                "H+": date.getHours().toString(),           // 时
                "M+": date.getMinutes().toString(),         // 分
                "S+": date.getSeconds().toString()          // 秒
                // 有其他格式化字符需求可以继续添加，必须转化成字符串
            };
            for (let k in opt) {
                ret = new RegExp("(" + k + ")").exec(fmt);
                if (ret) {
                    fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
                }
                ;
            }
            ;
            return fmt;
        } else {
            return '';
        }

    },
    getDateRange: function (begin, end) {
        return begin + '-' + end;
    },
    getMonthLastDay: function (date) {
        if (typeof date == 'string') {
            date = new Date(date.replace(/-/g, "/"));
        }
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        // console.log('lastDay=',this.dateFormat(lastDay));
        return lastDay.getDate();
    },

    getGenderLabel: function (val) {
        return val == '0' ? '女' : '男'
    },
    getGenderValue: function (val) {
        return val == '男' ? 1 : 0
    },
    getValues: function (labels, list, valKey, labelKey) {
        var res = [];
        valKey = valKey || 'value';
        labelKey = labelKey || 'label';
        labels.forEach(function (item) {
            list.forEach(function (item2) {
                if (item2[labelKey] == item) {
                    res.push(item2[valKey]);
                }
            });
        });
        return res;
    },
    getValue: function (label, list, valKey, labelKey) {
        var res = '';
        valKey = valKey || 'value';
        labelKey = labelKey || 'label';
        list.forEach(function (item2) {
            console.log(item2[labelKey], label)
            if (item2[labelKey] == label) {
                res = item2[valKey];
            }
        });
        return res;
    },
    getContractTyepVal: function (type) {
        // TODO  确认type的值
        if (type == config.CONTRACT_TYPE_NEW) {
            return '新签';
        } else if (type == config.CONTRACT_TYPE_GOON) {
            return '续签';
        }
    },
    dayDifference: function (sDate1, sDate2) {
        var dateSpan,
            tempDate,
            iDays;
        dateSpan = sDate2 - sDate1;
        dateSpan = Math.abs(dateSpan);
        iDays = Math.floor(dateSpan / (24 * 3600 * 1000)) + 1;
        return iDays
    },
    dayMinus: function (sDate1, sDate2) {
        var dateSpan,
            tempDate,
            iDays;
        dateSpan = sDate2 - sDate1;
        iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
        return iDays
    },


    /*
     * var params = {
     *  type: 1,
     *  costType: '',
     *  payType: '',
     *  mount: 0,
     *  unitType: 1,
     *  detail: ''
     *}
   */
    creatBills: function (createDate, beginDate, endDate, feeList, advanceDays) {
        var me = this;
        var bills = [];
        feeList.forEach(function (item) {
            bills = bills.concat(me.createBillDetail(createDate, beginDate, endDate, item, advanceDays))
        });
        return bills;
    },

    getPayDirectionValue: function (val) {
        if (val == '收' || val == '应收') {
            return config.WILL_INCOME_CODE;
        }
        if (val == '实收') {
            return config.HAS_INCOME_CODE;
        }
        if (val == '支' || val == '应支') {
            return config.WILL_PAY_CODE;
        }
        if (val == '实支') {
            return config.HAS_PAY_CODE;
        }
        return val;
    },
    getPayDirectionLabel: function (val) {
        if (val == config.WILL_INCOME_CODE) {
            return '应收';
        }
        if (val == config.WILL_PAY_CODE) {
            return '应支';
        }
        if (val == config.HAS_INCOME_CODE) {
            return '已收';
        }
        if (val == config.HAS_PAY_CODE) {
            return '已支';
        }
    },

    createBillDetail: function (createDate, beginDate, endDate, params, advanceDays) {
        if (!params) {
            return;
        }

        var me = this;
        var payType = params.payTypeId;
        var otherPayType = params.otherPayTypeId;
        var bills = [];
        var unitType = params.payUnitType;
        var direction = params.billCollectionPaymentType;
        // 一次付清
        if (payType == config.PAY_TYPE_ONCE || otherPayType == config.PAY_TYPE_ONCE || otherPayType == config.PAY_TYPE_WITHRENT) {
            console.log('一次付清')
            let totalamount = params.totalamount;
            if (params.identity == 'owner' && params.typeFlag != 1 && params.costtype != config.COST_TYPE_OF_DEPOSIT) {
                totalamount = (parseFloat(totalamount) * (parseFloat(params.count) * 12)).toFixed(2);
                if (params.costtype == config.COST_TYPE_OF_RENT) {
                    //租金
                    let noFeeNum = parseInt(params.noFeeNum);
                    if (params.noFeeType == config.FREE_PAY_INNTER) {
                        let amount = params.totalamount;
                        let sum = totalamount;
                        //console.log('内置免租期')
                        let days = parseInt((((beginDate - endDate) / 1000 / 60 / 60 / 24) + 0.5) + 1)
                        console.log('days=', days)

                        if (noFeeNum > 30) {
                            //大于一个月
                            //大于的月数
                            let monthNum = parseInt(noFeeNum / 30);
                            sum = sum - amount * monthNum;
                            let dayNum = noFeeNum % 30;
                            sum = parseInt((sum - amount / 30 * dayNum) + 0.5);
                        } else {
                            //小于等于一个月
                            //总价-每月价格/30*天数
                            sum = parseInt((sum - amount / 30 * noFeeNum) + 0.5);
                        }
                        // let unitPrice = (sum/days).toFixed(2);
                        // sum = ((days - noFeeNum) * unitPrice).toFixed(2);
                        totalamount = sum;
                        noFeeNum = 0;
                    }
                }
            }
            
            if(params.identity == 'tenant' && (params.costtype == '1' || params.costtype == '8')){
                // let tempDate = beginDate;
                // let i = 1;
                // while(tempDate < endDate){
                //     tempDate = dateAddMonth(tempDate,1)
                //     if(tempDate > endDate){
                //         break;
                //     }
                // }
                // 计算日期间隔
                let days = me.getDaysSum(me.dateFormat(beginDate),me.dateFormat(endDate));
                // 月租金
                let monthMoney = Number(totalamount)/Number(params.count)
                let dayMoney = me.getDayRentMoney(monthMoney);

                // 不是整月时计算租金
                if(Number(days) > 0){
                    totalamount = (Number(dayMoney)*Number(days))
                }

            }
            bills.push({
                billCollectionPaymentType: me.getPayDirectionValue(direction),
                costtype: params.costtype,
                costtypeVal: params.costtypeVal,
                payTypeId: params.payTypeId,
                totalamount: Number(totalamount).toFixed(2),
                billdescription: params.describe,
                count: params.count,
                nowCount: params.nowCount,
                identity: params.identity,//owner 业主,tenant 租客
                // 应付日期
                collectionDate: me.dateFormat(createDate),
                costBeginDate: me.dateFormat(beginDate),
                costEndDate: me.dateFormat(endDate)
            });
            return bills;
        }
        var advanceDay = 0;
        // 月付
        if (payType != "") {
            if (payType == config.PAY_TYPE_MONTH || payType == config.PAY_TYPE_TWO_BY_ONE || payType == config.PAY_TYPE_TWO_BY_THREE) {
                console.log('月付')
                advanceDay = advanceDays.MONTH;
            }
            if (payType == config.PAY_TYPE_SEASON || payType == config.PAY_TYPE_ONE_BY_ONE || payType == config.PAY_TYPE_ONE_BY_THREE) {
                console.log('季付')
                advanceDay = advanceDays.SEASON;
            }
            if (payType == config.PAY_TYPE_HALF_YEAR) {
                console.log('半年')
                advanceDay = advanceDays.HALF_YEAR;
            }
            if (payType == config.PAY_TYPE_YEAR) {
                console.log('年付')
                advanceDay = advanceDays.YEAR;
            }
            if (payType == config.PAY_TYPE_WITHRENT) {
                console.log('随房租付')
                if(unitType == config.PAY_TYPE_MONTH || unitType == config.PAY_TYPE_TWO_BY_ONE || unitType == config.PAY_TYPE_TWO_BY_THREE){
                    console.log('月付')
                    advanceDay = advanceDays.MONTH;
                }
                if (unitType == config.PAY_TYPE_SEASON || unitType == config.PAY_TYPE_ONE_BY_ONE || unitType == config.PAY_TYPE_ONE_BY_THREE) {
                    console.log('季付')
                    advanceDay = advanceDays.SEASON;
                }
                if (unitType == config.PAY_TYPE_HALF_YEAR) {
                    console.log('半年')
                    advanceDay = advanceDays.HALF_YEAR;
                }
                if (unitType == config.PAY_TYPE_YEAR) {
                    console.log('年付')
                    advanceDay = advanceDays.YEAR;
                }
            }
        }
        if (otherPayType != "") {
            otherPayType = parseInt(otherPayType);
            //半年
            if (otherPayType == config.PAY_TYPE_MONTH) {
                console.log('月付')
                advanceDay = advanceDays.MONTH;
            }
            // 季付
            if (otherPayType == config.PAY_TYPE_SEASON) {
                console.log('季付')
                advanceDay = advanceDays.SEASON;
            }
            //半年
            if (otherPayType == config.PAY_TYPE_HALF_YEAR) {
                console.log('半年')
                advanceDay = advanceDays.HALF_YEAR;
            }
            // 年付
            if (otherPayType == config.PAY_TYPE_YEAR) {
                console.log('年付')
                advanceDay = advanceDays.YEAR;
            }
        }

        bills = me.getBills(createDate, beginDate, endDate, params, advanceDay);
        return bills;
    },

    /**
     * 账单费用周期的结束时间
     * @param date
     * @param payType
     * @returns {Date}
     */
    getBillEndDate: function (date, payType,unitType,endDate) {
        var me = this;
        date = new Date(date);
        let size = 0;
        //月付
        if (payType == config.PAY_TYPE_MONTH || payType == config.PAY_TYPE_ONE_BY_ONE || payType == config.PAY_TYPE_TWO_BY_ONE) {
            //date.setMonth(date.getMonth()+1);
            // size = 1;
            return me.dateAddDay(me.dateAddMonth(date, 1), -1);
        }
        //季付
        if (payType == config.PAY_TYPE_SEASON || payType == config.PAY_TYPE_ONE_BY_THREE || payType == config.PAY_TYPE_TWO_BY_THREE) {
            //date.setMonth(date.getMonth()+3);
            // size = 3;
            return me.dateAddDay(me.dateAddMonth(date, 3), -1);
        }
        //半年付
        if (payType == config.PAY_TYPE_HALF_YEAR) {
            //date.setMonth(date.getMonth()+6);
            // size = 6;
            return me.dateAddDay(me.dateAddMonth(date, 6), -1);
        }
        //年付
        if (payType == config.PAY_TYPE_YEAR) {
            //date.setMonth(date.getMonth()+12);

            // size = 12;
            return me.dateAddDay(me.dateAddMonth(date, 12), -1);
        }

        //随房租付，判断类型
        if (payType == config.PAY_TYPE_ONCE ) {
            if(unitType == config.PAY_TYPE_MONTH || unitType == config.PAY_TYPE_ONE_BY_ONE || unitType == config.PAY_TYPE_TWO_BY_ONE) {
                //date.setMonth(date.getMonth()+1);
                // size = 1;
                return me.dateAddDay(me.dateAddMonth(date, 1), -1);
            }
            //季付
            if (unitType == config.PAY_TYPE_SEASON || unitType == config.PAY_TYPE_ONE_BY_THREE || unitType == config.PAY_TYPE_TWO_BY_THREE) {
                //date.setMonth(date.getMonth()+3);
                // size = 3;
                return me.dateAddDay(me.dateAddMonth(date, 3), -1);
            }
            //半年付
            if (unitType == config.PAY_TYPE_HALF_YEAR) {
                //date.setMonth(date.getMonth()+6);
                // size = 6;
                return me.dateAddDay(me.dateAddMonth(date, 6), -1);
            }
            //年付
            if (unitType == config.PAY_TYPE_YEAR) {
                //date.setMonth(date.getMonth()+12);

                // size = 12;
                return me.dateAddDay(me.dateAddMonth(date, 12), -1);
            }
            if(unitType == config.PAY_TYPE_ONCE){
                return endDate;
            }
        }

        /*for (var i = 0; i < size; i++) {
            let nextDate = new Date(date);
            nextDate.setMonth(nextDate.getMonth()+1);
            let maxDay = this.getMonthLastDay(nextDate);
            date.setDate(date.getDate() + maxDay)
        }*/
        // date.setDate(date.getDate()-1)
        return date;
    },

    getBillEndDateOther: function (date, payType,unitType,endDate) {
        var me = this;
        date = new Date(date);
        let size = 0;
        //月付
        if (payType == config.PAY_TYPE_MONTH || payType == config.PAY_TYPE_ONE_BY_ONE || payType == config.PAY_TYPE_TWO_BY_ONE) {
            //date.setMonth(date.getMonth()+1);
            // size = 1;
            return me.dateAddDay(me.dateAddMonth2(date, 1), -1);
        }
        //季付
        if (payType == config.PAY_TYPE_SEASON || payType == config.PAY_TYPE_ONE_BY_THREE || payType == config.PAY_TYPE_TWO_BY_THREE) {
            //date.setMonth(date.getMonth()+3);
            // size = 3;
            return me.dateAddDay(me.dateAddMonth2(date, 3), -1);
        }
        //半年付
        if (payType == config.PAY_TYPE_HALF_YEAR) {
            //date.setMonth(date.getMonth()+6);
            // size = 6;
            return me.dateAddDay(me.dateAddMonth2(date, 6), -1);
        }
        //年付
        if (payType == config.PAY_TYPE_YEAR) {
            //date.setMonth(date.getMonth()+12);

            // size = 12;
            return me.dateAddDay(me.dateAddMonth2(date, 12), -1);
        }

        //随房租付，判断类型
        if (payType == config.PAY_TYPE_ONCE ) {
            if(unitType == config.PAY_TYPE_MONTH || unitType == config.PAY_TYPE_ONE_BY_ONE || unitType == config.PAY_TYPE_TWO_BY_ONE) {
                //date.setMonth(date.getMonth()+1);
                // size = 1;
                return me.dateAddDay(me.dateAddMonth2(date, 1), -1);
            }
            //季付
            if (unitType == config.PAY_TYPE_SEASON || unitType == config.PAY_TYPE_ONE_BY_THREE || unitType == config.PAY_TYPE_TWO_BY_THREE) {
                //date.setMonth(date.getMonth()+3);
                // size = 3;
                return me.dateAddDay(me.dateAddMonth2(date, 3), -1);
            }
            //半年付
            if (unitType == config.PAY_TYPE_HALF_YEAR) {
                //date.setMonth(date.getMonth()+6);
                // size = 6;
                return me.dateAddDay(me.dateAddMonth2(date, 6), -1);
            }
            //年付
            if (unitType == config.PAY_TYPE_YEAR) {
                //date.setMonth(date.getMonth()+12);

                // size = 12;
                return me.dateAddDay(me.dateAddMonth2(date, 12), -1);
            }
            if(unitType == config.PAY_TYPE_ONCE){
                return endDate;
            }
        }
        return date;
    },


    getBillStatusLable: function (status) {
        //账单状态(0 应收 1已收 2 应付 3 已付 4 停付)
        var me = this;
        if (status == 0) {
            return '应收';
        } else if (status == 1) {
            return '已收';
        } else if (status == 2) {
            return '应支';
        } else if (status == 3) {
            return '已支';
        } else if (status == 4) {
            return '停付';
        } else {
            return '';
        }
    },
    getApprovalStatusLabel: function (status) {
        //审批状态(0 未审批 1 已审批　2 审批拒绝)
        var me = this;
        if (status == 0) {
            return '未审批';
        } else if (status == 1) {
            return '已审批';
        } else if (status == 2) {
            return '审批拒绝';
        } else {
            return '';
        }
    },
    getChangeStateLabel: function (status) {
        //变更状态(0 申请中 1 待审批 2 已审批 3 已拒绝 4 已取消)
        var me = this;
        if (status == 0) {
            return '申请中';
        } else if (status == 1) {
            return '待审批';
        } else if (status == 2) {
            return '已审批';
        } else if (status == 3) {
            return '已拒绝';
        } else if (status == 4) {
            return '已取消';
        } else {
            return '';
        }
    },
    getChangeTypeLabel: function (status) {
        //变更类型(0 新签 1 续签 2 退房子 3 违约退房 4 转租 5 换房 )
        var me = this;
        if (status == 0) {
            return '新签';
        } else if (status == 1) {
            return '续签';
        } else if (status == 2) {
            return '违约退房';
        } else if (status == 3) {
            return '违约退房';
        } else if (status == 4) {
            return '转租';
        } else if (status == 5) {
            return '换房';
        } else {
            return '';
        }
    },

    monthDayDiff: function (startDate, endDate) {
        let flag = [1, 3, 5, 7, 8, 10, 12, 4, 6, 9, 11, 2];
        let start = new Date(startDate);
        let startYes = start.getTime() - 24 * 60 * 60 * 1000;
        let yesterDayStart = new Date(startYes);
        let end = new Date(endDate);
        // let year = end.getFullYear() - yesterDayStart.getFullYear();
        // let month = end.getMonth() - yesterDayStart.getMonth();
        // let day = end.getDate() - yesterDayStart.getDate();


        // 原本计算逻辑存在缺陷，修改为以下逻辑--方树成20210105
        //计算免租期开始时间与结束时间相差毫秒数，然后求出相差天数
        let feeDays = (end.getTime() - yesterDayStart.getTime())/(24 * 60 * 60 * 1000);
        let year, month ,day;


        //根据start、end开始时间确定开始的月份，结束的月份，算出月份实际的天数，
        //开始时间年份
        let startYear = start.getFullYear();
        //开始时间月份
        let startMonth = start.getMonth() + 1;
        //结束时间年份
        let endYear = end.getFullYear();
        //结束时间月份
        let endMonth = end.getMonth() + 1;

        let monthDays = 0;
        let endMonthDays = 0;
        //如果免租期开始与结束时间在同一年同一个月的话
        if(startYear == endYear && startMonth == endMonth){
            //获取这个月的天数
            monthDays = this.getDaysInMonth(startYear,startMonth);
            //进入比较阶段，根据免租期天数确定年、月、日
            year = end.getFullYear() - yesterDayStart.getFullYear();
            month = feeDays >= monthDays ? 1:0
            day = feeDays >= monthDays ? feeDays - monthDays : feeDays;
        //结束时间为2月末（特殊情况，1月31日开始，免租期29天，结束日期为2月28日）
        }else if(startYear == endYear && endMonth - startMonth == 1 && endMonth == 2){  //免租期相差一个月（非自然月）
            monthDays = this.getDaysInMonth(startYear,startMonth);  //开始月份天数
            endMonthDays = this.getDaysInMonth(endYear,endMonth);  //结束月份天数
            year = end.getFullYear() - yesterDayStart.getFullYear();
            month = feeDays >= monthDays + endMonthDays ? 2 : feeDays >= monthDays || feeDays >= endMonthDays ? 1 : 0
            day = feeDays >= monthDays + endMonthDays ? feeDays - (monthDays + endMonthDays) : feeDays >= monthDays ? feeDays-monthDays : feeDays;
        }else if(startYear == endYear && endMonth - startMonth == 1){  //免租期相差一个月（非自然月）
            monthDays = this.getDaysInMonth(startYear,startMonth);  //开始月份天数
            endMonthDays = this.getDaysInMonth(endYear,endMonth);  //结束月份天数
            year = end.getFullYear() - yesterDayStart.getFullYear();
            month = feeDays >= monthDays + endMonthDays ? 2 : feeDays >= monthDays ? 1 : 0
            day = feeDays >= monthDays + endMonthDays ? feeDays - (monthDays + endMonthDays) : feeDays >= monthDays ? feeDays-monthDays : feeDays;
        }else if(startYear == endYear && endMonth - startMonth == 2){  //免租期相差两个月（非自然月）
            let firstMonthDays = this.getDaysInMonth(startYear,startMonth);  //第一个月天数
            let secondMonthDays = this.getDaysInMonth(endYear,endMonth - 1);  //第二个月天数
            let thirdMonthDays = this.getDaysInMonth(endYear,endMonth);  //第三个月天数
            year = end.getFullYear() - yesterDayStart.getFullYear();
            month = feeDays >= firstMonthDays + secondMonthDays + thirdMonthDays ? 3 : feeDays >= firstMonthDays + secondMonthDays ? 2 : feeDays >= firstMonthDays  ? 1 : 0
            day = feeDays >= firstMonthDays + secondMonthDays + thirdMonthDays ? feeDays - (firstMonthDays + secondMonthDays + thirdMonthDays) : feeDays >= firstMonthDays + secondMonthDays ? feeDays-(firstMonthDays + secondMonthDays) : feeDays >= firstMonthDays ? feeDays-firstMonthDays : feeDays;
        }else{
            year = end.getFullYear() - yesterDayStart.getFullYear();
            month = end.getMonth() - yesterDayStart.getMonth();
            day = end.getDate() - yesterDayStart.getDate();
        }

        //yesterDayStart
        if (month < 0) {
            year--;
            month = end.getMonth() + (12 - yesterDayStart.getMonth());
        }
        if (day < 0) {
            month--;
            let index = flag.findIndex((temp) => {
                return temp === yesterDayStart.getMonth() + 1
            });
            let monthLength;
            if (index <= 6) {
                monthLength = 31;
            } else if (index > 6 && index <= 10) {
                monthLength = 30;
            } else {
                monthLength = 28;
            }
            day = end.getDate() + (monthLength - yesterDayStart.getDate());
        }
        console.log(12 * year + month + '月' + day + '天')
        return {
            'months': (12 * year + month),
            'days': day
        }

    },
    getDaysInMonth(year,month){
    month = parseInt(month,10); //parseInt(number,type)这个函数后面如果不跟第2个参数来表示进制的话，默认是10进制。
    var temp = new Date(year,month,0);
    return temp.getDate();
    },
    //计算两个日期相差几个月
    getDistanceMonth(startTime,endTime){
        startTime = new Date(startTime)
        endTime = new Date(endTime)
        let StartDay = startTime.getDate();
        let StartMonth = startTime.getMonth();
        let StartYear = startTime.getFullYear();
        let endDay = endTime.getDate();
        let endMonth = endTime.getMonth();
        let endYear = endTime.getFullYear();
        let day = endDay + 1 -StartDay >= 0 ? 0 : -1
        return (endYear - StartYear) * 12 + (endMonth - StartMonth) + (day)

    },
    // 计算不完整月份的租金（日租金*天数）
    getSumMoneyOther: function(params, curBegin, endDate){
        let rentMOney = params.totalamount;  //月租金

        // 计算开始与结束时间之间相差的整月
        let wholeMonths = this.getDistanceMonth(curBegin,endDate);
        //加上整月后的开始时间，用于计算出了整月剩余的天数
        let tempCurBegin = this.dateAddMonth(curBegin,wholeMonths);
        //计算tempCurBegin 和结束时间相差的天数
        let extraDays = this.getDaysSum(tempCurBegin,endDate);
        //计算日租金
        let dayRentMoney = this.getDayRentMoney(rentMOney);
        console.log("日租金" + dayRentMoney)

        let totalMoney = Number(wholeMonths) * Number(params.totalamount) + dayRentMoney * Math.round(extraDays);
        return totalMoney;
    },

    //计算两个日期之间的天数
    getDaysSum(dateString1,dateString2){
        var  startDate = Date.parse(dateString1);
        var  endDate = Date.parse(dateString2);
        var days=(endDate - startDate)/(1*24*60*60*1000);
        // alert(days);
        console.log("间隔天数"+ (Number(days) + 1))
        return  Number(days) + 1;
    },

    getBills: function (createDate, beginDate, endDate, params, advanceDay) {
        console.log('getBills', params)
        var me = this;
        
        var payType = params.payTypeId;
        var otherPayType = params.otherPayTypeId;
        var unitType = params.payUnitType;
        if (params.payTypeId == "") {
            payType = otherPayType;
        }
        if(params.renterType){
            if(params.payTypeId == 5){
                // params.payTypeId = params.renterType
                payType = params.renterType
            }
        }

        var curBegin = beginDate;
        var curEnd;

        var curBeginStr = me.dateToString(curBegin)
        var tempDateEnd = new Date(curBeginStr)
        //如果是内置且为月付
        // if(params.noFeeType == config.FREE_PAY_INNTER && payType == '0'){
        var tempCurEnd = tempDateEnd;
        tempCurEnd.setDate(tempCurEnd.getDate() + (Number(params.noFeeNum) - 1));
        //一月份 二月份因为2月有28天、29天的情况特俗

        if((beginDate.getMonth()  == 0 || beginDate.getMonth()  == 1) && (tempCurEnd.getMonth() == '2' || tempCurEnd.getMonth() == '3')){
            // var curBeginStr = me.dateToString(curBegin)
            var tempDate = new Date(curBeginStr)
            curEnd = tempDate;
            //内置
            if(params.noFeeType == config.FREE_PAY_INNTER){
                if(payType == '0'){
                    curEnd.setDate(curEnd.getDate() + (Number(params.noFeeNum) - 1));
                }else{
                    curEnd= me.getBillEndDate(curBegin, payType,unitType,endDate);
                }
            }else{
            //外置
                curEnd= me.getBillEndDateOther(curBegin, payType,unitType,endDate);
            //     curEnd = me.dateAddMonth(curEnd, 1)
            }
        }else{
            curEnd= me.getBillEndDate(curBegin, payType,unitType,endDate);
        }
        var bills = [];
        var num = 1;
        var feenum = 1;
        if (params.typeFlag == 1) {
        }
        var direction = me.getPayDirectionValue(params.billCollectionPaymentType);
        console.log('curEnd=', curEnd, 'endDate=', endDate)
        let noFeeNum = parseInt(params.noFeeNum);
        let oldNoFeeNum = parseInt(params.oldNoFeeNum);
        let newEndDate = new Date(endDate);

        // newEndDate.setDate(newEndDate.getDate() + 8)
        newEndDate.setDate(newEndDate.getDate() + 0)
        let newNoFeeNum = noFeeNum
        while (curEnd <= newEndDate) {
            // if()
            // 
            console.log('curEnd=', me.dateFormat(curEnd), 'curEnd=', me.dateFormat(endDate))
            let noFeeStr = ''
            let newNoFeeDate = new Date((beginDate.getTime() + ((noFeeNum - 1) * 24 * 60 * 60 * 1000)));
            // 免租日期
            let fee = me.monthDayDiff(curBegin, newNoFeeDate)
            // 免租日期（月）
            let feeMonths = fee.months
            // 免租日期（日）
            let feeDays = fee.days
            //
            var amount = params.totalamount;
            var sum = me.getSumMoney(params, curBegin, curEnd);
            if (params.identity == 'owner' && params.typeFlag != 1) {

                //业主 noFeeType
                if (params.noFeeType == config.FREE_PAY_INNTER) {
                    //console.log('内置免租期')
                    let days = parseInt((((curEnd - curBegin) / 1000 / 60 / 60 / 24) + 0.5) + 1)

                    // 付款方式
                    let payDate = me.monthDayDiff(curBegin, curEnd)
                    // 付款方式(月)
                    let payDateMonths = payDate.months
                    // 付款方式(日)
                    let payDateDays = payDate.days
                    if (newNoFeeNum > 0) {
                        if (feeMonths - payDateMonths == 0) {
                            if (feeDays == 0) {
                                //免租天数刚好够一期
                                noFeeStr += ';免租期' + newNoFeeNum + '天';
                                sum = 0;
                                newNoFeeNum = 0
                            } else {
                                sum = sum - (amount * feeMonths);
                                let day = parseInt((((curEnd - curBegin) / 1000 / 60 / 60 / 24) + 0.5) + 1)
                                noFeeStr += ';免租期' + day + '天';
                                newNoFeeNum = newNoFeeNum - day
                            }

                        } else if (feeMonths - payDateMonths < 0) {
                            if (feeDays == 0) {
                                sum = parseInt(sum - (amount * feeMonths))

                            } else {
                                sum = parseInt(sum - (amount * feeMonths) - (amount / 30 * feeDays))
                            }
                            noFeeStr += ';免租期' + newNoFeeNum + '天';
                            newNoFeeNum = 0
                        } else if (feeMonths - payDateMonths > 0) {
                            sum = 0
                            let day = parseInt((((curEnd - curBegin) / 1000 / 60 / 60 / 24) + 0.5) + 1)
                            noFeeStr += ';免租期' + day + '天';
                            newNoFeeNum = newNoFeeNum - day
                        }
                        feenum++

                    }


                    console.log("====================================================================================================================================")
                    console.log("curEnd=======>", curEnd)
                    console.log("curBegin=======>", curBegin)
                    console.log('days=', days)
                    // if (noFeeNum > 0 && noFeeNum - days == 0) {
                    //     //免租天数刚好够一期
                    //     noFeeStr += ';免租期' + noFeeNum + '天';
                    //     noFeeNum = 0;
                    //     sum = 0;
                    // } else if (noFeeNum > 0 && noFeeNum - days > 0) {
                    //     //免租期天数大于一期
                    //     noFeeNum = noFeeNum - days;
                    //     noFeeStr += ';免租期' + days + '天';
                    //     sum = 0;
                    // } else if (noFeeNum > 0 && noFeeNum - days < 0) {
                    //     //免租期天数小于一期
                    //     console.log('noFeeNum=', noFeeNum, 'oldNoFeeNum=', oldNoFeeNum)
                    //     if (noFeeNum == oldNoFeeNum) {
                    //         //正好一个月
                    //         sum = sum - amount;
                    //     } else {
                    //         if (noFeeNum > 30) {
                    //             //大于一个月
                    //             //大于的月数
                    //             let monthNum = parseInt(noFeeNum / 30);
                    //             sum = sum - amount * monthNum;
                    //             let dayNum = noFeeNum % 30;
                    //             sum = parseInt((sum - amount / 30 * dayNum) + 0.5);
                    //         } else {
                    //             //小于等于一个月
                    //             //总价-每月价格/30*天数
                    //             sum = parseInt((sum - amount / 30 * noFeeNum) + 0.5);
                    //         }
                    //         // let unitPrice = (sum/days).toFixed(2);
                    //         // sum = ((days - noFeeNum) * unitPrice).toFixed(2);
                    //     }
                    //     noFeeStr += ';免租期' + noFeeNum + '天';
                    //     noFeeNum = 0;
                    //     //noFeeNum - days
                    // }
                } else {
                    // console.log('外置免租期')
                    if (noFeeNum > 0) {
                        // if((beginDate.getMonth()  == 0 || beginDate.getMonth()  == 1) && (tempCurEnd.getMonth() == '2' || tempCurEnd.getMonth() == '3')){
                            var lastDay;
                            var newbeginDate = new Date(curBeginStr)
                            //月付
                            if(payType == '0'){
                                lastDay = this.getMonthLastDay(this.dateAddMonth2(tempCurEnd,1));
                                //重新计算结束日

                                //计算开始时间加免租期后的日期

                                newbeginDate.setDate(beginDate.getDate() + (Number(noFeeNum) - 1));
                                curEnd = this.dateAddMonth2(newbeginDate,1)
                            }else if(payType == '1'){
                                //季付
                                // lastDay = this.getMonthLastDay(this.dateAddMonth2(tempCurEnd,3));
                                newbeginDate.setDate(beginDate.getDate() + (Number(noFeeNum) - 1));
                                curEnd = this.dateAddMonth2(newbeginDate,3)
                            }else if(payType == '2'){
                                //半年付
                                // lastDay = this.getMonthLastDay(this.dateAddMonth2(tempCurEnd,6));
                                newbeginDate.setDate(beginDate.getDate() + (Number(noFeeNum) - 1));
                                curEnd = this.dateAddMonth2(newbeginDate,6)
                            }else{
                                newbeginDate.setDate(beginDate.getDate() + (Number(noFeeNum) - 1));
                                curEnd = this.dateAddMonth2(newbeginDate,12)
                            }
                            // curEnd.setDate(curEnd.getDate() + (Number(lastDay) - 1));
                        // }else{
                        //     curEnd.setDate(curEnd.getDate() + (Number(noFeeNum) - 1));
                        // }
                        // var lastDay;
                        // //月付
                        // if(payType == '0'){
                        //     lastDay = this.getMonthLastDay(this.dateAddMonth2(tempCurEnd,1));
                        // }else if(payType == '1'){
                        //     //季付
                        //     lastDay = this.getMonthLastDay(this.dateAddMonth2(tempCurEnd,3));
                        // }else if(payType == '2'){
                        //     //半年付
                        //     lastDay = this.getMonthLastDay(this.dateAddMonth2(tempCurEnd,6));
                        // }
                        // var needAddDays = Number(lastDay) +


                        // if (noFeeNum == oldNoFeeNum) {
                        // curEnd = me.dateAddMonth(curEnd, 1)
                        // } else {
                            // curEnd = me.dateAddMonth(curEnd, 1)
                        // }
                        noFeeStr += ';免租期' + noFeeNum + '天';
                        noFeeNum = 0;
                    }

                }

                if (num == 1) {
                    var collectionDate = me.dateFormat(createDate);
                } else {
                    var collectionDate = me.dateFormat(me.dateAddDay(curBegin, -1 * advanceDay));
                }
                let billdescription = '';
                if (params.typeFlag && params.typeFlag == 1) {
                    billdescription = params.describe;
                } else {
                    billdescription = '第' + params.nowCount + '年;第' + num + '期' + noFeeStr;
                    // billdescription = params.describe + num + '期';
                }
                let costEndDate = me.dateFormat(curEnd);
                if (curEnd > endDate) {
                    costEndDate = me.dateFormat(endDate);
                }
                bills.push({
                    billCollectionPaymentType: direction,
                    costtype: params.costtype,
                    costtypeVal: params.costtypeVal,
                    payTypeId: params.payTypeId,
                    otherPayTypeId: params.otherPayTypeId,
                    totalamount: Number(sum).toFixed(2),
                    count: params.count,
                    identity: params.identity,//owner业主,tenant租客
                    nowCount: params.nowCount,
                    billdescription: billdescription,
                    // 应付日期
                    collectionDate: me.shouleBranchDate(noFeeStr, createDate, curBegin, num, feenum, params.nowCount),
                    // collectionDate: collectionDate,
                    costBeginDate: me.dateFormat(curBegin),
                    costEndDate: costEndDate
                });
            } else {
                // 
                if (num == 1) {
                    var collectionDate = me.dateFormat(createDate);
                } else {
                    if(params.payTypeId =='6' || params.renterType == '6'){
                        var collectionDate = me.getPreMonth(me.dateFormat(curBegin));
                    }else{
                        var collectionDate = me.dateFormat(me.dateAddDay(curBegin, -1 * advanceDay));
                    }

                }
                let billdescription = '';
                if (params.typeFlag && params.typeFlag == 1) {
                    billdescription = params.describe;
                } else {
                    billdescription = '第' + num + '期'
                    // billdescription = params.describe + num + '期';
                }
                let costEndDate = me.dateFormat(curEnd);
                if (curEnd > endDate) {
                    costEndDate = me.dateFormat(endDate);
                }
                bills.push({
                    billCollectionPaymentType: direction,
                    costtype: params.costtype,
                    costtypeVal: params.costtypeVal,
                    payTypeId: params.payTypeId,
                    otherPayTypeId: params.otherPayTypeId,
                    totalamount: Number(sum).toFixed(2),
                    count: params.count,
                    identity: params.identity,//owner业主,tenant租客
                    nowCount: params.nowCount,
                    billdescription: billdescription,
                    // 应付日期
                    collectionDate: num == 1 ? collectionDate : collectionDate,
                    // collectionDate: collectionDate,
                    costBeginDate: me.dateFormat(curBegin),
                    costEndDate: costEndDate
                });
            }
            curBegin = me.dateAddDay(curEnd, 1);
            if(me.dateFormat(endDate) == me.dateFormat(curEnd)){
                break;
            }
            //加入对账单是否为最后一期的判断，如果有，则直接设置为结束日期
            if((payType == '0' && num == '11') || (payType == '1' && num == '3') || (payType == '2' && num == '1')){
                curEnd = newEndDate;
            }else{
                curEnd = me.getBillEndDate(curBegin, payType,unitType,me.dateFormat(endDate));
            }
            num++;
        }

        //如果计算出的账单结束日期大于实际合同截止日期，就说明剩下的时间不足一个月了，计算日租金
        if(curEnd > newEndDate && params.identity == 'tenant' && (params.costtype == config.COST_TYPE_OF_DEPOSIT || params.costtype == config.COST_TYPE_OF_RENTERRENT)){
            // 
            var amount = params.totalamount;
            var sum = me.getSumMoneyOther(params, curBegin, endDate);
            if (num == 1) {
                var collectionDate = me.dateFormat(createDate);
            } else {
                if(params.payTypeId =='6' || params.renterType == '6'){
                    var collectionDate = me.getPreMonth(me.dateFormat(curBegin));
                }else{
                    var collectionDate = me.dateFormat(me.dateAddDay(curBegin, -1 * advanceDay));
                }

            }
            let billdescription = '';
            if (params.typeFlag && params.typeFlag == 1) {
                billdescription = params.describe;
            } else {
                billdescription = '第' + num + '期'
                // billdescription = params.describe + num + '期';
            }
            let costEndDate = me.dateFormat(curEnd);
            if (curEnd > endDate) {
                costEndDate = me.dateFormat(endDate);
            }
            bills.push({
                billCollectionPaymentType: direction,
                costtype: params.costtype,
                costtypeVal: params.costtypeVal,
                payTypeId: params.payTypeId,
                otherPayTypeId: params.otherPayTypeId,
                totalamount: Number(sum).toFixed(2),
                count: params.count,
                identity: params.identity,//owner业主,tenant租客
                nowCount: params.nowCount,
                billdescription: billdescription,
                // 应付日期
                collectionDate: num == 1 ? collectionDate : collectionDate,
                // collectionDate: collectionDate,
                costBeginDate: me.dateFormat(curBegin),
                costEndDate: costEndDate
            });
        }

        

        //只针对其他费用的账单计算
        if((params.costtype != '1' && params.costtype != '2' && params.costtype != '8' && params.payTypeId != '4')
            && params.identity == 'tenant'){
            var amount = params.totalamount;
            let sum = 0
            if(me.dateFormat(endDate) == me.dateFormat(curEnd)){
                return bills;
            }
            if (num == 1) {
                var collectionDate = me.dateFormat(createDate);
            } else {
                if(params.payTypeId =='6' || params.renterType == '6'){
                    var collectionDate = me.getPreMonth(me.dateFormat(curBegin));
                }else{
                    var collectionDate = me.dateFormat(me.dateAddDay(curBegin, -1 * advanceDay));
                }

            }
            let tempCount = params.count;   //租期（月份数），
            let costEndDate = me.dateFormat(curEnd);
            if (curEnd > endDate) {
                costEndDate = me.dateFormat(endDate);
                if(num != 1){
                    tempCount = tempCount + 1;  //如果租期除了整月外还有多余且不足一个月的天数， + 1
                }
            }
            if(params.payTypeId == "1"){
                if(unitType == config.PAY_UNIT_YEAR){//每年
                    sum = (Number(amount/12,2))*(Math.floor(tempCount%3))
                }else if(unitType == config.PAY_UNIT_DAY) {//每天
                    var days = me.dayMinus(new Date(me.dateFormat(curBegin)),new Date(costEndDate))
                    sum = amount*days
                }else{
                    sum = amount * (Math.floor(tempCount % 3))
                }
            }else if(params.payTypeId == "2"){
                if(unitType == config.PAY_UNIT_YEAR) {
                    sum = (Number(amount/12,2)) * (Math.floor(tempCount % 6))
                }else if(unitType == config.PAY_UNIT_DAY) {//每天
                    var days = me.dayMinus(new Date(me.dateFormat(curBegin)),new Date(costEndDate))
                    sum = amount*days
                }else{
                    sum = amount * (Math.floor(tempCount % 6))
                }
            }else if(params.payTypeId == "3"){
                if(unitType == config.PAY_UNIT_YEAR) {
                    sum = (Number(amount/12,2)) * (Math.floor(tempCount % 12))
                }else if(unitType == config.PAY_UNIT_DAY) {//每天
                    var days = me.dayMinus(new Date(me.dateFormat(curBegin)),new Date(costEndDate))
                    sum = amount*days
                }else{
                    sum = amount * (Math.floor(tempCount % 12))
                }
            }else if(params.payTypeId == "5"){
                if(unitType == config.PAY_UNIT_YEAR) {
                    sum = (Number(amount/12,2)) * (Math.floor(tempCount % 12))
                }else if(unitType == config.PAY_UNIT_DAY) {//每天
                    var days = me.dayMinus(new Date(me.dateFormat(curBegin)),new Date(costEndDate))
                    sum = amount*days
                }else if(unitType == config.PAY_UNIT_MONTH) {//每月
                    sum = amount * (Math.floor(tempCount % 12))
                }else{
                    sum = amount
                }
            }

            if(params.payTypeId == "0"){
                if(unitType == 0){
                    //如果其他费用付款周期是每天，那么金额要按照每日计算，否则计算整月或是整年
                    // 
                    let curBeginString = this.dateFormat(curBegin)
                    let daysSum = this.getDaysSum(curBeginString,costEndDate);
                    sum = amount * Math.round(daysSum);
                }else if(num == 1 && (unitType == 2 || unitType == 1)){    //租期不满一个费用周期时，费用周日选择的是年或者月，直接加一个月的其他费用的金额

                    if(unitType == 1){
                        sum = sum + amount;
                    }else{
                        sum = sum + (Number(amount/12,2));
                    }
                }else if(num >= 2){
                    if(unitType == 1){
                        sum = amount;
                    }else{
                        sum = sum + (Number(amount/12,2));
                    }
                }
                // else if(){
                //     //先计算租期不满一个费用周期时，整月的日期结束日是哪天
                //     fullCostEndTime = this.dateAddMonth(curBegin,params.count);
                //     //再计算这个日期到合同结束日期的
                // }

                bills.push({
                    billCollectionPaymentType: direction,
                    costtype: params.costtype,
                    costtypeVal: params.costtypeVal,
                    payTypeId: params.payTypeId,
                    otherPayTypeId: params.otherPayTypeId,
                    totalamount: Number(sum).toFixed(2),
                    count: params.count,
                    identity: params.identity,//owner业主,tenant租客
                    nowCount: params.nowCount,
                    billdescription:params.describe,
                    // billdescription: '第'+num+'期',
                    // 应付日期
                    collectionDate: num == 1 ? collectionDate : collectionDate,
                    costBeginDate: me.dateFormat(curBegin),
                    costEndDate: costEndDate
                });
            }else if(params.payTypeId == "1"){
                if(unitType == 0){
                    //如果其他费用付款周期是每天，那么金额要按照每日计算，否则计算整月或是整年
                    // 
                    let curBeginString = this.dateFormat(curBegin)
                    let daysSum = this.getDaysSum(curBeginString,costEndDate);
                    sum = amount * Math.round(daysSum);
                }else if(num == 1 && (unitType == 2 || unitType == 1)){    //租期不满一个费用周期时，费用周日选择的是年或者月，直接加一个月的其他费用的金额

                        if(unitType == 1){
                            sum = sum + amount;
                        }else{
                            sum = sum + (Number(amount/12,2));
                        }
                }
                // else if(){
                //     //先计算租期不满一个费用周期时，整月的日期结束日是哪天
                //     fullCostEndTime = this.dateAddMonth(curBegin,params.count);
                //     //再计算这个日期到合同结束日期的
                // }

                bills.push({
                    billCollectionPaymentType: direction,
                    costtype: params.costtype,
                    costtypeVal: params.costtypeVal,
                    payTypeId: params.payTypeId,
                    otherPayTypeId: params.otherPayTypeId,
                    totalamount: Number(sum).toFixed(2),
                    count: params.count,
                    identity: params.identity,//owner业主,tenant租客
                    nowCount: params.nowCount,
                    billdescription:params.describe,
                    // billdescription: '第'+num+'期',
                    // 应付日期
                    collectionDate: num == 1 ? collectionDate : collectionDate,
                    costBeginDate: me.dateFormat(curBegin),
                    costEndDate: costEndDate
                });
            }else if(params.payTypeId == "2"){
                if(unitType == 0){
                    //如果其他费用付款周期是每天，那么金额要按照每日计算，否则计算整月或是整年
                    let curBeginString = this.dateFormat(curBegin)
                    let daysSum = this.getDaysSum(curBeginString,costEndDate);
                    sum = amount * Math.round(daysSum);
                }else if(num == 1 && (unitType == 2 || unitType == 1)){    //租期不满一个费用周期时，费用周日选择的是年或者月，直接加一个月的其他费用的金额

                    if(unitType == 1){
                        sum = sum + amount;
                    }else{
                        sum = sum + (Number(amount/12,2));
                    }
                }
                bills.push({
                    billCollectionPaymentType: direction,
                    costtype: params.costtype,
                    costtypeVal: params.costtypeVal,
                    payTypeId: params.payTypeId,
                    otherPayTypeId: params.otherPayTypeId,
                    totalamount: Number(sum).toFixed(2),
                    count: params.count,
                    identity: params.identity,//owner业主,tenant租客
                    nowCount: params.nowCount,
                    billdescription:params.describe,
                    // billdescription: '第'+num+'期',
                    // 应付日期
                    collectionDate: num == 1 ? collectionDate : collectionDate,
                    costBeginDate: me.dateFormat(curBegin),
                    costEndDate: costEndDate
                });

            }else if(params.payTypeId == "3"){
                if(unitType == 0){
                    //如果其他费用付款周期是每天，那么金额要按照每日计算，否则计算整月或是整年
                    // 
                    let curBeginString = this.dateFormat(curBegin)
                    let daysSum = this.getDaysSum(curBeginString,costEndDate);
                    sum = amount * Math.round(daysSum);
                }else if(num == 1 && (unitType == 2 || unitType == 1)){    //租期不满一个费用周期时，费用周日选择的是年或者月，直接加一个月的其他费用的金额

                    if(unitType == 1){
                        sum = sum + amount;
                    }else{
                        sum = sum + (Number(amount/12,2));
                    }
                }
                bills.push({
                    billCollectionPaymentType: direction,
                    costtype: params.costtype,
                    costtypeVal: params.costtypeVal,
                    payTypeId: params.payTypeId,
                    otherPayTypeId: params.otherPayTypeId,
                    totalamount: Number(sum).toFixed(2),
                    count: params.count,
                    identity: params.identity,//owner业主,tenant租客
                    nowCount: params.nowCount,
                    billdescription:params.describe,
                    // billdescription: '第'+num+'期',
                    // 应付日期
                    collectionDate: num == 1 ? collectionDate : collectionDate,
                    costBeginDate: me.dateFormat(curBegin),
                    costEndDate: costEndDate
                });
            }else if(params.payTypeId == "5"){
                if(sum != 0) {
                    sum = params.totalamount
                    bills.push({
                        billCollectionPaymentType: direction,
                        costtype: params.costtype,
                        costtypeVal: params.costtypeVal,
                        payTypeId: params.payTypeId,
                        otherPayTypeId: params.otherPayTypeId,
                        totalamount: Number(sum).toFixed(2),
                        count: params.count,
                        identity: params.identity,//owner业主,tenant租客
                        nowCount: params.nowCount,
                        billdescription: params.describe,
                        // billdescription: '第'+num+'期',
                        // 应付日期
                        collectionDate: num == 1 ? collectionDate : collectionDate,
                        costBeginDate: me.dateFormat(curBegin),
                        costEndDate: costEndDate
                    });
                }
            }
        }
        return bills;
    },


    getPreMonth: function (date) {
        var arr = date.split('-');
        var year = arr[0]; //获取当前日期的年份
        var month = arr[1]; //获取当前日期的月份
        var day = arr[2]; //获取当前日期的日
        var days = new Date(year, month, 0);
        days = days.getDate(); //获取当前日期中月的天数
        var year2 = year;
        var month2 = parseInt(month) - 1;
        if (month2 == 0) {
            year2 = parseInt(year2) - 1;
            month2 = 12;
        }
        var day2 = day;
        var days2 = new Date(year2, month2, 0);
        days2 = days2.getDate();
        if (day2 > days2) {
            day2 = days2;
        }
        if (month2 < 10) {
            month2 = '0' + month2;
        }
        var t2 = year2 + '-' + month2 + '-' + day2;
        return t2;
    },
    shouleBranchDate: function (noFeeStr, createDate, curBegin, num, feenum, yearNum) {
        let collectionDate
        let me = this
        if (yearNum == 1) {
            if (noFeeStr == "" && feenum - 1 == num) {
                collectionDate = me.dateFormat(createDate);
            } else if (noFeeStr != "") {
                collectionDate = me.dateFormat(createDate);
            } else {
                collectionDate = me.dateFormat(curBegin);
            }

        } else {
            collectionDate = me.dateFormat(curBegin);
        }
        return collectionDate
    },
    getContractSource: function (type) {
        //"变更类型(0 新签 1 续签 2 退房子 3 违约退房 4 转租 5 换房 )"
        type = parseInt(type);
        if (type == 0) {
            return '新签'
        } else if (type == 1) {
            return '续签'
        } else if (type == 2) {
            return '退租'
        } else if (type == 3) {
            return '违约退租'
        } else if (type == 4) {
            return '转租'
        } else if (type == 5) {
            return '换房'
        } else {
            return type;
        }
    },

    getSumMoney: function (params, curBegin, curEnd) {
        var me = this;
        // 输入的金额
        var amount = params.totalamount;
        var unitType = params.payUnitType;
        var payType = params.payTypeId;
        if (params.payTypeId == "") {
            payType = params.otherPayTypeId;
        }
        var sum = 0;
        //月付
        if (payType == config.PAY_TYPE_MONTH || payType == config.PAY_TYPE_ONE_BY_ONE || payType == config.PAY_TYPE_TWO_BY_ONE) {
            // 按天计算的费用
            if (unitType == config.PAY_UNIT_DAY) {
                sum = me.dayDifference(curBegin, curEnd) * amount;
            }
            if (unitType == config.PAY_UNIT_MONTH) {
                sum = amount;
            }
            if (unitType == config.PAY_UNIT_YEAR) {
                sum = Math.round(amount / 12);
            }
        }
        //季付
        if (payType == config.PAY_TYPE_SEASON || payType == config.PAY_TYPE_ONE_BY_THREE || payType == config.PAY_TYPE_TWO_BY_THREE) {
            // 按天计算的费用
            if (unitType == config.PAY_UNIT_DAY) {
                sum = me.dayDifference(curBegin, curEnd) * amount;
            }
            if (unitType == config.PAY_UNIT_MONTH) {
                sum = 3 * amount;
            }
            if (unitType == config.PAY_UNIT_YEAR) {
                sum = amount / 4;
            }
        }
        //半年付
        if (payType == config.PAY_TYPE_HALF_YEAR) {
            // 按天计算的费用
            if (unitType == config.PAY_UNIT_DAY) {
                sum = me.dayDifference(curBegin, curEnd) * amount;
            }
            if (unitType == config.PAY_UNIT_MONTH) {
                sum = 6 * amount;
            }
            if (unitType == config.PAY_UNIT_YEAR) {
                sum = amount / 2;
            }
        }
        //年付
        if (payType == config.PAY_TYPE_YEAR) {
            // 按天计算的费用
            if (unitType == config.PAY_UNIT_DAY) {
                sum = 365 * amount;
            }
            if (unitType == config.PAY_UNIT_MONTH) {
                sum = 12 * amount;
            }
            if (unitType == config.PAY_UNIT_YEAR) {
                sum = amount;
            }
        }
        //随房租付
        if (payType == config.PAY_TYPE_WITHRENT) {
            sum = amount;
        }

        return sum;
    },
    /**
     * 计算日租金
     * @param monthMoney
     */
    getDayRentMoney: function (monthMoney) {
        return (monthMoney * 12 / 365).toFixed(2);
    },

    /**
     * 银行卡卡号luhn算法校验
     * @param bankno
     * @returns {boolean}
     */
    luhnCheck: function (bankno) {
        var lastNum = bankno.substr(bankno.length - 1, 1);//取出最后一位（与luhm进行比较）

        var first15Num = bankno.substr(0, bankno.length - 1);//前15或18位
        var newArr = new Array();
        for (var i = first15Num.length - 1; i > -1; i--) { //前15或18位倒序存进数组
            newArr.push(first15Num.substr(i, 1));
        }
        var arrJiShu = new Array(); //奇数位*2的积 <9
        var arrJiShu2 = new Array(); //奇数位*2的积 >9

        var arrOuShu = new Array(); //偶数位数组
        for (var j = 0; j < newArr.length; j++) {
            if ((j + 1) % 2 == 1) {//奇数位
                if (parseInt(newArr[j]) * 2 < 9)
                    arrJiShu.push(parseInt(newArr[j]) * 2);
                else
                    arrJiShu2.push(parseInt(newArr[j]) * 2);
            } else //偶数位
                arrOuShu.push(newArr[j]);
        }

        var jishu_child1 = new Array();//奇数位*2 >9 的分割之后的数组个位数
        var jishu_child2 = new Array();//奇数位*2 >9 的分割之后的数组十位数
        for (var h = 0; h < arrJiShu2.length; h++) {
            jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
            jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
        }

        var sumJiShu = 0; //奇数位*2 < 9 的数组之和
        var sumOuShu = 0; //偶数位数组之和
        var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
        var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
        var sumTotal = 0;
        for (var m = 0; m < arrJiShu.length; m++) {
            sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
        }

        for (var n = 0; n < arrOuShu.length; n++) {
            sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
        }

        for (var p = 0; p < jishu_child1.length; p++) {
            sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
            sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
        }
        //计算总和
        sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

        //计算Luhm值
        var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
        var luhm = 10 - k;

        if (lastNum == luhm && lastNum.length != 0) {
            //$("#banknoInfo").html("Luhm验证通过");
            return true;
        } else {
            //$("#banknoInfo").html("银行卡号必须符合Luhm校验");
            return false;
        }
    },
    setContractInfo: function (param) {
        let me = {}
        let defaultImg1 = require('@/assets/images/default_img_1.png')
        let defaultImg2 = require('@/assets/images/default_img_2.png')
        let defaultImg3 = require('@/assets/images/default_img_3.png')
        let defaultImg4 = require('@/assets/images/default_img_4.png')
        let defaultImg5 = require('@/assets/images/default_img_5.png')
        let defaultImg6 = require('@/assets/images/default_img_6.png')
        me.formValidate = {
            ownerChange: false,
            depositMoney: '',
            contractType: '',
            contractTypeVal: '',
            sexState: true,//业主性别编辑状态
            nameState: false,//业主姓名编辑状态
            mobileState: false,//业主手机编辑状态
            certificateState: false,//业主证件编辑状态
            agentSexState: true,//代理人性别编辑状态
            agentNameState: false,//代理人姓名编辑状态
            agentMobileState: false,//代理人手机编辑状态
            agentCertificateState: false,//代理人证件编辑状态
            ownerNameAuthentication: 0,//业主实名认证 0 未认证,1 已认证
            agentNameAuthentication: 0,//代理人实名认证 0 未认证,1 已认证
            roomDisabled: true,//地址编辑状态
            'signType': '1',//1 线上合同,2 线下合同
            'mortgageSituation': '',
            'mortgageSituationVal': '',
            'heatingMode': '',
            'heatingModeVal': '',
            'signRoleType': '0',//0 业主,1 代理人
            'signRoleTypeVal': '',
            'roomId': '',
            'ownerContractType': '0',
            'houseAddress': '',
            'ownerContractSubType': '0',//托管类型
            'ownerMobile': '',//业主手机号
            'AddStaffId': '',//添加员工ID
            'ownerName': '',//业主姓名
            'ownerGender': '',//业主性别
            'ownerCertificateType': '',//业主证件类型
            'ownerCertificateCode': '',//业主证件编号
            'emergencyContactPerson': '',//紧急联系人姓名
            'emergencyContactPhone': '',//紧急联系人手机
            'emergencyContactAddress': '',//紧急联系人地址
            'emergencyContactEmail': '',//紧急联系人邮件
            'hasAgent': 'false',//是否有代理人
            'hasCoOwner': 'false',//是否有共有权人
            'contractCode': '',//合同ID
            'contractCodeNew': '',//合同ID
            'agentName': '',//代理人姓名
            'agentMobile': '',//代理人手机
            'agentCertificateType': '',//代理人证件类型
            'agentCertificateCode': '',//代理人证件编号
            'coOwnerName': '',//共有权人姓名
            'coOwnerMobile': '',//共有权人手机
            'coOwnerCertificateType': '',//共有权人证件类型
            'coOwnerCertificateCode': '',//共有权人证件编号
            'payTypeId': '',
            'otherPayTypeId': '',
            'payeeName': '',
            'bankCode': '',//银行卡号
            'bank': '',//银行
            'subBank': '',
            'noFeeType': config.FREE_PAY_INNTER,
            'propertyRightType_id': '',
            'propertyRightCode': '',
            'builtUpArea': '',
            'contractYears': '',
            'orientation_id': '',
            'potentialResource_id': '',
            'rentMoney': '',
            'startDate': '',
            'endDate': '',
            'renovationBeginTime': '',
            'renovationEndTime': '',
            'giveHouseTime': '',
            'footnote': '',
            'signStaffId': '0',//签约人ID
            'partnerStaffId': '0',//合作人ID
            'recommendUser_id': '0',//推荐人ID
            'partnerRatioId': '',
            'agentGender': '',//代理人性别
            'coOwnerGender': '',//共有权人性别
            'yzFrontOfCard': defaultImg4,
            'yzReverseOfCard': defaultImg5,
            'yzHoldCard': defaultImg3,
            'bankCardFront': defaultImg2,
            'bankCardReverse': defaultImg1,
            'wtrFrontOfCard': defaultImg4,
            'wtrReverseOfCard': defaultImg5,
            'commonOwnerFrontOfCard': defaultImg4,
            'commonOwnerReverseOfCard': defaultImg5,
            'entrustment': defaultImg6,
            'holdEntrustment': defaultImg6,
            'enclosureProperty': defaultImg6,
            'enclosureAuthorization': defaultImg6,
            'lables': [],
            noFeeSum: 0,
            otherFees: [],
            noFeeList: [],
            labelType: [],
            enclosurePropertyPicList: [],
            outLinecontratPicList: []
        }

        me.formValidate.contractCode = param.contractCode

        for (const paramInfoKey in param) {
            let evalStr = "me.formValidate." + paramInfoKey + " = param." + paramInfoKey

            if (paramInfoKey === "ownerMobile") {
                me.formValidate.ownerMobile = param.ownerMobile
                me.formValidate.ownerChange = false
            } else if (paramInfoKey === "signerRole") {
                me.formValidate.signRoleType = param.signerRole
                me.formValidate.signRoleTypeVal = param.signerRole == '0' ? '业主' : '代理人'
            } else if (paramInfoKey === "supplementaryRemark") {
                me.formValidate.footnote = param.supplementaryRemark
                me.formValidate.supplementaryRemark = param.supplementaryRemark
            } else if (paramInfoKey === "bank") {
                me.formValidate.bank = parseInt(param.bank)
            } else if (paramInfoKey === "noFeeType") {
                me.formValidate.noFeeType = parseInt(param.noFeeType)
            } else if (paramInfoKey === "heatingMode") {
                me.formValidate.heatingMode = parseInt(param.heatingMode)
            } else if (paramInfoKey === "mortgageSituation") {
                me.formValidate.mortgageSituation = parseInt(param.mortgageSituation)
            } else if (paramInfoKey === "builtUpArea") {
                me.formValidate.builtUpArea = param.builtUpArea + ''
            } else if (paramInfoKey === "contractYears") {
                me.formValidate.contractYears = param.contractYears + ''
            } else if (paramInfoKey === "rentMoney") {
                me.formValidate.rentMoney = param.rentMoney + ''
            } else if (paramInfoKey === "payType_id") {
                me.formValidate.payTypeId = param.payType_id + ''
            } else if (paramInfoKey === "pardicTitle") {
                me.formValidate.partnerRatioVal = param.pardicTitle + ''
            } else {
                eval(evalStr)
            }
        }

        if (me.formValidate.hasAgent && me.formValidate.agentMobile) {
            me.formValidate.agentChange = false
        }

        if (param && param.sinName) {
            me.formValidate.signStaffId = param.signStaff_id + ""
        } else {
            me.formValidate.signStaffId = getUserId() + ""
        }

        if (param && param.parUserName) {
            me.formValidate.parUserNameVal = param.parUserName
        } else {
            me.formValidate.parUserNameVal = ''
            me.formValidate.partnerStaffId = ''
        }

        if (param && param.partnerStaff_id) {
            me.formValidate.partnerStaffId = param.partnerStaff_id + ''
        }

        me.formValidate.ownerContractTypeVal = param.ownerContractType == 0 ? '重资产' : '轻资产';
        // detailInfo.ownerContractSubType == 0 ? '重资产' : '轻资产';
        if (param.ownerContractSubType == 1) {
            me.formValidate.ownerContractSubTypeVal = '委托出租';
        } else if (param.ownerContractSubType == 2) {
            me.formValidate.ownerContractSubTypeVal = '委托管理';
        } else {
            me.formValidate.ownerContractSubTypeVal = '全程管理';
        }
        me.formValidate.startDate = param.contractBeginTime;
        me.formValidate.endDate = param.contractEndTime;

        if (param.signDepartmentList) {
            let signDepartmentVal = '';
            for (var i = 0; i < param.signDepartmentList.length; i++) {
                if (signDepartmentVal == '') {
                    signDepartmentVal = param.signDepartmentList[i].departmentName
                } else {
                    signDepartmentVal += ' ' + param.signDepartmentList[i].departmentName
                }
            }
            me.formValidate.signDepartmentVal = signDepartmentVal;
        }
        if (param.partnerDepartmentList) {
            let partnerDepartmentVal = '';
            for (var i = 0; i < param.partnerDepartmentList.length; i++) {
                if (partnerDepartmentVal == '') {
                    partnerDepartmentVal = param.partnerDepartmentList[i].departmentName
                } else {
                    partnerDepartmentVal += ' ' + param.partnerDepartmentList[i].departmentName
                }
            }
            me.formValidate.partnerDepartmentVal = partnerDepartmentVal;
        }
        if (me.formValidate.noFeeType) {
            me.formValidate.noFeeTypeVal = param.noFeeType == 0 ? '内置' : '外置';
        }

        if (param && typeof param.ownerContractType !== 'undefined') {
            me.formValidate.ownerContractType = param.ownerContractType + ''
        }
        me.freePayList = []
        if (param && param.contractnofeeList) {
            let contractnofeeList = param.contractnofeeList
            for (let i = 0; i < contractnofeeList.length; i++) {
                me.freePayList.push({
                    num: (i + 1),
                    days: contractnofeeList[i].noFeeTimeLong,
                    beginDate: this.dateFormat(contractnofeeList[i].noFeeBeginTime),
                    endDate: this.dateFormat(contractnofeeList[i].noFeeEndTime),
                    contractStartDate: this.dateFormat(contractnofeeList[i].everyYearEntrustBeginTime),
                    contractEndDate: this.dateFormat(contractnofeeList[i].everyYearEntrustEndTime)
                })
            }
        }
        // if (param && param.labelIds && param.labelIds.length > 0) {
        //     let labelIds = param.labelIds.split(',')
        //     let labelType = []
        //     for (let i = 0; i < labelIds.length; i++) {
        //         for (let j = 0; j < me.labelList.length; j++) {
        //             if (me.labelList[j].id == labelIds[i]) {
        //                 labelType.push(me.labelList[j].label)
        //                 break
        //             }
        //         }
        //     }
        //
        //     me.formValidate.labelType = labelType
        // }


        me.formValidate.freePayList = []
        let noFeeSum = 0
        for (let i = 0; i < param.contractnofeeList.length; i++) {
            me.formValidate.freePayList.push({
                num: (i + 1),
                days: param.contractnofeeList[i].noFeeTimeLong,
                beginDate: this.dateFormat(param.contractnofeeList[i].noFeeBeginTime),
                endDate: this.dateFormat(param.contractnofeeList[i].noFeeEndTime),
                contractStartDate: this.dateFormat(param.contractnofeeList[i].everyYearEntrustBeginTime),
                contractEndDate: this.dateFormat(param.contractnofeeList[i].everyYearEntrustEndTime)
            })
            noFeeSum += param.contractnofeeList[i].noFeeTimeLong
        }
        me.formValidate.noFeeSum = noFeeSum

        if (param && param.photo) {
            let photo = param.photo
            if (photo.lesseeOrOwnerIdFront) {
                me.formValidate.yzFrontOfCard = photo.lesseeOrOwnerIdFront
            }
            if (photo.lesseeOrOwnerIdBack) {
                me.formValidate.yzReverseOfCard = photo.lesseeOrOwnerIdBack
            }
            if (photo.lesseeOrOwnerIdHand) {
                me.formValidate.yzHoldCard = photo.lesseeOrOwnerIdHand
            }
            if (photo.bankCardFront) {
                me.formValidate.bankCardFront = photo.bankCardFront
            }
            if (photo.bankCardBack) {
                me.formValidate.bankCardReverse = photo.bankCardBack
            }
            if (photo.togetherOrEntrustIdFront) {
                me.formValidate.wtrFrontOfCard = photo.togetherOrEntrustIdFront
            }
            if (photo.togetherOrEntrustIdBack) {
                me.formValidate.wtrReverseOfCard = photo.togetherOrEntrustIdBack
            }
            if (photo.commonOwnerIdCardFront) {
                me.formValidate.commonOwnerFrontOfCard = photo.commonOwnerIdCardFront
            }
            if (photo.commonOwnerIdCardBack) {
                me.formValidate.commonOwnerReverseOfCard = photo.commonOwnerIdCardBack
            }
            if (photo.entrustBook) {
                me.formValidate.entrustment = photo.entrustBook
            }
            if (photo.entrustBookHand) {
                me.formValidate.holdEntrustment = photo.entrustBookHand
            }
            if (photo.togetherOrAccessories1) {
                me.formValidate.enclosureProperty = photo.togetherOrAccessories1
            }
            if (photo.togetherOrAccessories2) {
                me.formValidate.enclosureAuthorization = photo.togetherOrAccessories2
            }
        }
        if (me.formValidate.signRoleType == '0') {
            me.formValidate.mobileState = true
            me.formValidate.agentMobileState = false
        } else {
            me.formValidate.mobileState = false
            me.formValidate.agentMobileState = true
        }
        console.log('me.formValidate==>', me.formValidate)
        return me.formValidate
    },
}
