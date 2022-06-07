package com.demo.myquartz.common.dto;

import lombok.*;

/**
 * Error logging용 vo
 * 
 * @author shs
 */
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class ErrorLog {
	private String excpLogSeqno;	/* 예외 로그 일련번호 */
	private String url;            	/* URL */
	private String excpNm;          /* 예외 명 */
	private String excpCn;          /* 예외 내용 */
	private String excpTraceCn;     /* 예외 추적 내용 */
	private String dvlprDc;			/* 개발자 설명 */
	private String vriablCn;		/* 변수 내용 */
	private String lastChangerId;	/* 최종 변경자 ID */
	private String lastChangeDt;	/* 최종 변경 일시 */
}
