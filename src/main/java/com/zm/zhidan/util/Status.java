package com.zm.zhidan.util;

/**
 * Created by liuchao on 2017/6/3.
 */
public class Status {
    public static final byte NORMAL = 1;//"正常
    public static final byte CANCEL = 10;//注销（撤单、停用）
    public static final byte DELETE = 11;// 删除
    public static final byte PENDING_APPROVAL = 2;//待审批
    public static final byte ERROR_APPROVAL = 3;//审批未通过
    public static final byte UNSUITED = 0;//不适用
    public static final byte RECORD_MODIFY = 4;//改单状态
    public static final byte LOST = 5;//丢失、遗失
    public static final byte BROKEN_DOWN = 6;//报废
    public static final byte RECEIPT_INIT = 0;//初始
    public static final byte RECEIPT_FILE_DATA = 9;//数据异常
    public static final byte RECEIPT_FTP_FILE = 10;//盖章完成上传影像失败
    public static final byte RECEIPT_SUCCESS = 1;//成功
    public static final byte RECEIPT_FILE = 11;//失败
    public static final byte PACTTYPE = 1;//"电子合同模板标识
    public static final byte CONTRACT_RESULT_SUCCESS = 1;//"电子合同成功
    public static final byte CONTRACT_RESULT_FAIL = 11;//"电子合同失败
    public static final byte CONTRACT_PROCESS_INIT = 0;//"电子合同初始化
    public static final byte CONTRACT_PROCESS_SIGN = 1;//"电子合同待签名
    public static final byte CONTRACT_PROCESS_SUCCESS = 2;//"电子合同完成
    public static final byte CONTRACT_SEQNO_ERROR = 2;//"流水号重复提交,旧的状态改为 2-无效
}
