export default {

    /***资金周期***/

    PAY_UNIT_DAY: '0',

    PAY_UNIT_MONTH: '1',

    PAY_UNIT_YEAR: '2',

    PAY_UNIT_All: '3',

    /***支付方式***/

    // 随房租付
    PAY_TYPE_WITHRENT: 5,
    // 一次付清
    PAY_TYPE_ONCE: 4,
    // 月付
    PAY_TYPE_MONTH: 0,
    // 季付
    PAY_TYPE_SEASON: 1,
    // 半年付
    PAY_TYPE_HALF_YEAR: 2,
    // 年付
    PAY_TYPE_YEAR: 3,
    // 押一付一
    PAY_TYPE_ONE_BY_ONE : 6,
    // 押二付一
    PAY_TYPE_TWO_BY_ONE : 8,

    CONTRACT_STATUS: {
        0: '即将搬入',
        1: '在租中',
        2: '即将搬出',
        3: '已到期',
        4: '已退租',
        5: '待签字',
        6: '已签字',
        7: '已作废'
    },

    DICTIONARY: {
        PARTNER_RATIO:  'contractRaito',
        BANK: 'bankList',
        PROPERTY_RIGHT_TYPE: 'propertyRightType',
        TERMINATE_REASON: 'contractTerminationType',
        SUBLETREASON: 'subletReason',
        CONTRACTTERMINATIONREASON: 'contractTerminationReason'
    },

    RENTER_CONTRACT_CHANGE_LIST_PATH: '/rentercontract/findEditHistory.json',
    RENTER_CONTRACT_APPROVAL_LIST_PATH: '/rentercontract/findApprovalHistory.json',
    MOBILE_SEACH_PATH: '/manageCommon/getUserList.json',
    RENDER_HOUSE_LIST_PATH: '/manageCommon/getRoomHouseList.json',
    HOUSE_DETAIL_PATH: '/rentercontract/findRentByRoomId.json',
    HOUSE_OWNER_END_DATE_PATH: '/rentercontract/querygOwnerContractEndDate.json',
    GET_CONTRACT_ID_RENDER_PATH: '/ownercontract/createContractCode.json',

    /**
     * 潜房列表
     */
    POTENTIAL_RESOURCE_PATH: '/manageCommon/getCollectionRoom.json',

    /**
     * 合同类型-新签
     */
    CONTRACT_TYPE_NEW: 1,

    /**
     * 合同类型-续签
     */
    CONTRACT_TYPE_GOON: 1,

    /**
     * 内置免租期
     */
    FREE_PAY_INNTER: 0,

    /**
     * 外置免租期
     */
    FREE_PAY_OUT: 1,

    ONE_DAY: 24 * 60 * 60 * 1000,

    // 应收入
    WILL_INCOME_CODE: 0,
    // 应该支出
    WILL_PAY_CODE: 1,
    // 实收
    HAS_INCOME_CODE: 3,
    // 实支
    HAS_PAY_CODE: 4,

    /**
     * 提前付款天数
     */
    ADVANCE_DAYS:  {
        MONTH: 1,
        SEASON: 30,
        HALF_YEAR: 30,
        YEAR: 30,
    },
    /**********费用类型*******
        费用类型
        租金(业主)(房租(租客)) 1
        违约金 7
        维修费 8
        水电费 4
        热水费 9
        装配费 10
        保洁费 11
        定金   12
        滞纳金 5
        宽带费 13
        装修费 14
        其他费用 15
    */
    // 房租
    // TODO 确认
    COST_TYPE_OF_RENT: 1,
    // 押金
    COST_TYPE_OF_DEPOSIT: 2,
    // 物品损坏
    COST_TYPE_OF_MAINTENANCE: 3,
    // 水电费
    COST_TYPE_OF_WATER: 4,
    // 滞纳金
    COST_TYPE_OF_LATE_FEE: 5,
    // 利息
    COST_TYPE_OF_INTEREST: 6,
    // 违约金
    COST_TYPE_OF_BREAK: 7,
    // 维修费
    COST_TYPE_OF_REPAIR: 8,
    // 热水费
    COST_TYPE_OF_HOTWATER: 9,
    // 装配费
    COST_TYPE_OF_ASSEMBLING: 10,
    // 保洁费
    COST_TYPE_OF_CLEANING: 11,
    // 定金
    COST_TYPE_OF_EARNEST: 12,
    // 宽带费
    COST_TYPE_OF_BROADBAND: 13,
    // 宽带费
    COST_TYPE_OF_RENOVATION: 14,
    // 其他费用
    COST_TYPE_OF_OTHER: 15,
    // 租金
    COST_TYPE_OF_RENTERRENT: 8,
    //服务费
    COST_TYPE_OF_SERVICEFEE: 11,

    // 正常退租
    TERMINATE_NORMAL: 1,

    // 非正常退租
    TERMINATE_NOT_NORMAL: 0,

    // 违约方  平台
    WRONG_SIDE_PLATFORM: 0,

    // 违约方   客户
    //WRONG_SIDE_CUSTOM: 2,

    // 违约方   业主
    WRONG_SIDE_OWNER: 1,

    // 违约方   租客
    WRONG_SIDE_RENTER: 2,

    // 正常退房/违约/转租/换房/续签/无责解除

    TERMINATE_TYPE_NORMAL: 108,

    //违约
    TERMINATE_TYPE_BREAK: 109,
    // 转租
    TERMINATE_TYPE_TRANSFER: 110,
    // 换房
    TERMINATE_TYPE_CHANGE: 111,
    //续签
    TERMINATE_TYPE_GOON: 112,
    //无责解除
    TERMINATE_TYPE_NO_REPONSE: 113,

    // 违约金是房租的多少倍
    BREAK_MONEY_MOUTH_COUNT: {
        1: 0,
        2: 2,
        3: 1,
        4: 1,
        5: 0,
        6: 0,
    },

    //姓名校验 2字以上
    NAME_REG: /^(([a-zA-Z+\.?\·?a-zA-Z+]{2,30}$)|([\u4e00-\u9fa5+\·?\u4e00-\u9fa5+]{2,30}$))/,
    //身份证校验
    CERTIFICATE_REG: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
    //手机号校验
    MOBILE_REG: /^[1][3,4,5,6,7,8,9][0-9]{9}$/,
    //邮箱校验
    EMAIL_REG: /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
    //正数（包括小数）
    NUMBER_REG_1: /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/,
    //正整数(不包括小数)
    NUMBER_REG_2: /^[+]{0,1}(\d+)$/,
    //数字且大于等于0
    NUMBER_REG_3: /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/,
    //正整数（包含0）
    NUMBER_REG_4: /^([1-9]\d*|[0]{1,1})$/,
    //1-28正整数
    NUMBER_REG_5: /^([12][0-9]|28|[1-9])$/
}
