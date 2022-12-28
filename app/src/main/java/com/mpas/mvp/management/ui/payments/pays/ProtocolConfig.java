package com.mpas.mvp.management.ui.payments.pays;

public class ProtocolConfig {

    //PROTOCOL IP
    public static final String host = "211.48.96.28";
    //HEADER JOB_CODEs

    // CREDIT
    public static final String _JOB_CODE_CREDIT = "1010";
    public static final String _JOB_CODE_CREDIT_CANCEL = "1050";
    public static final String _JOB_CODE_CREDIT_EMV_QR = "1014"; // 승인 취소 공통

    // CASH
    public static final String _JOB_CODE_CASH = "5010";
    public static final String _JOB_CODE_CASH_CANCEL = "5050";

    // ZERO
    public static final String _JOB_CODE_ZERO = "8050";
    public static final String _JOB_CODE_ZERO_CANCEL = "8051";
    public static final String _JOB_CODE_ZERO_ENQUIRY = "8052";
    public static final String _JOB_CODE_ZERO_CANCEL_ENQUIRY = "8053";

    // KAKAO
    public static final String _JOB_CODE_KAKAO_INQUIRY = "8050"; // 승인
    public static final String _JOB_CODE_KAKAO_CANCEL_INQUIRY = "8051"; // 취소
    public static final String _JOB_CODE_KAKAO_MONEY = "8052";
    public static final String _JOB_CODE_KAKAO_CANCEL = "8053";

    // SSG
    public static final String _JOB_CODE_SSG = "8030";
    public static final String _JOB_CODE_SSG_CANCEL = "8031";
    public static final String _JOB_CODE_SSG_CONFIRM = "8036";

    // PAY PRO
    public static final String _JOB_CODE_PAY_PRO = "8066";
    public static final String _JOB_CODE_PAY_PRO_CANCEL = "8067";

    // Total Pays
    public static final String _JOB_CODE_TOTAL_PAYS = "8099";

    // MPAS Response
    public static final String _JOB_CODE_MPAS_RESPONSE = "8098";

    public static enum Pays {
        CREDIT,
        ZERO,
        KAKAO,
        SSG,
        LPAY,
        CASH,
        PAYPRO,
        TOTAL
    }

    public static enum PayClass {
        AUTH,
        CANCEL,
        INQUIRY,
        CONFIRM
    }

}
