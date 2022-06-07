package com.demo.myquartz.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.demo.myquartz.common.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MailSms extends Log {
	private String wdtbSeqno;               /* 배포 seqno */
    private String userId;                  /* user id */
    private String userNm;                  /* 사용자 */
    private String sj;                      /* 제목 */
    
    @JsonIgnore //내용은 json으로 변환하지 않는다. 너무크기 때문.
    private String cn;                      /* 내용 */
    
    private String wdtbPrearngeDt;          /* 배포예정 일시 */
    private String email;                   /* 받는사람 이메일 (여러개) */
    private String moblphon;                /* 받는사람 전화번호 */
    private String emailTrnsmisAt;          /* 이메일 발송 여부 */
    private String emailTrnsmisComptAt;     /* 이메일 전송 완료 여부 */
    private String chrctrTrnsmisAt;         /* 문자 발송 여부 */
    private String chrctrTrnsmisComptAt;    /* 문자 전송 완료 여부 */
    private String inspctInsttNm;           /* 사업장 명 */
    private String inspctInsttCode;         /* 사업장 코드 */
    private String dmstcTelno;              /* 사업장 국내 전화번호 */
    private String lastChangerId;


    /**
     * logging시 json화가 필요 없기 때문에 ignore처리함.
     * return String[] 이메일을 String Array로 만들어서 return함.
     */
    @JsonIgnore
    public String[] getEmailArray() {
        if(this.email.length() == 0){
            throw new RuntimeException("user email not found");
        }
        return this.email.split(",");
    }

    /**
     *  이메일 발송 유효성 검사
     *  이메일 발송여부 Y && 이메일 전송 완료 여부 N일 경우에만 이메일 전송한다.
     */
    public boolean emailSendValid() {
        return ( "Y".equals(this.emailTrnsmisAt) && "N".equals(this.emailTrnsmisComptAt) ) && (this.email != null);
    }
    
    /**
     *  문자 발송 유효성 검사
     *  문자 발송여부 Y && 문자 전송 완료 여부 N일 경우에만 문자 전송한다.
     */
    public boolean messageSendValid() {
        return ( "Y".equals(this.chrctrTrnsmisAt) && "N".equals(this.chrctrTrnsmisComptAt) ) && (this.moblphon != null);
    }
}
