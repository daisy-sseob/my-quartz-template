package com.demo.myquartz.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@Slf4j
@Component
public class JobLoggingUtils {
	
	@Value("${job.log.path}")
	private String jobLoggingPath;
	
	@Value("${job.log.max.history}")
	private int maxHistory;
	
	/**
	 * 
	 * logFileOfDate 로그파일을 삭제하고 난 뒤 모든 파일이 삭제 되었으면 root directory삭제.
	 * 그러나 logFileOfDate가 해당 월의 마지막 날짜라면 해당 월 directory hard delete함. 
	 * 
	 * @param logFileOfDate (현재 날짜 - maxHistory) 만큼에 해당하는 log file
	 * @param targetDate (현재 날짜 - maxHistory) 만큼의 날짜
	 */
	public void clearRootDirectory(File logFileOfDate, LocalDate targetDate) {
		
		final File monthDirectory = logFileOfDate.getParentFile();
		final File yearDirectory = monthDirectory.getParentFile();

		// (현재 날짜 - maxHistory) 해당일이 해당월의 말일이면 바로삭제
		if (targetDate.equals(YearMonth.now().minusMonths(maxHistory).atEndOfMonth())) {
			if (monthDirectory.exists()) {
				try {
					FileUtils.deleteDirectory(monthDirectory);
				} catch (IOException e) {
					log.error("this is directory was not deleted " + monthDirectory, e);
				}
			}
			deleteSelfDirectory(yearDirectory);
		} else {
			deleteSelfDirectory(monthDirectory);
			deleteSelfDirectory(yearDirectory);
		}
		
	}
	
	public File getJobLoggingDirectory() {
		final LocalDate now = LocalDate.now();
		return new File(jobLoggingPath + File.separator + now.getYear() + File.separator + now.getMonth());
	}
	public File getJobLoggingDirectory(LocalDate localDate) {
		return new File(jobLoggingPath + File.separator + localDate.getYear() + File.separator + localDate.getMonth());
	}
	public File getJobLogFileOfDate(LocalDate localDate) {
		return new File(this.getJobLoggingDirectory(localDate).getPath() + File.separator + localDate.getDayOfMonth() + ".log");
	}
	public Optional<File[]> getJobLogFilesOfMonth(LocalDate localDate) {
		final File monthDirectory = new File(this.getJobLoggingDirectory(localDate).getPath());
		return Optional.ofNullable(monthDirectory.listFiles());
	}

	private void deleteSelfDirectory(File self) {
		if (self.exists()) {
			final File[] files = self.listFiles();
			if (files != null) {
				if (files.length == 0) {
					self.delete();
				}
			}
		}
	}
}
