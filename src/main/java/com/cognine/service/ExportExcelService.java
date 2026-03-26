package com.cognine.service;

import java.io.IOException;

import com.cognine.model.ExcelByteStream;
import com.cognine.model.PbiTimeLineData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ExportExcelService {

	ExcelByteStream exportToExcel(PbiTimeLineData pbiTimeLineData) throws JsonMappingException, JsonProcessingException, IOException;

}
