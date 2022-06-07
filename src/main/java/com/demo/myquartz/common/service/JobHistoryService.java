package com.demo.myquartz.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.myquartz.common.CustomException;
import com.demo.myquartz.common.annotation.LoggingException;
import com.demo.myquartz.common.dto.JobHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class JobHistoryService {

	private final ObjectMapper objectMapper;
	
	@Autowired
	public JobHistoryService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@LoggingException
	public void addJobHistories(List<JobHistory> jobHistories, File file) {
		if (file.exists()) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader((new FileInputStream(file))))) {
				String line;
				while ((line = br.readLine()) != null) {
					jobHistories.add(objectMapper.readValue(line, JobHistory.class));
				}
			} catch (FileNotFoundException e) {
				throw new CustomException(e, null, "File not fund" + file.getPath());
			} catch (IOException e) {
				throw new CustomException(e, null, "The file could not be read." + file.getPath());
			}
		}
	}
}
