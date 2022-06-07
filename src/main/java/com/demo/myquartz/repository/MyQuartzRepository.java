package com.demo.myquartz.repository;

import com.demo.myquartz.common.dto.ErrorLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface MyQuartzRepository {

	/**
	 * Propagation -> REQUIRES_NEW
	 * insertErrorLog까지 rollback되지 않게 하기 위해 Transaction분리.
	 * error logging
	 * @param errorLog
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	int insErrorLog(ErrorLog errorLog);
}
