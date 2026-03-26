//src/main/java/com/cognine/controller/ExportExcelController.java
package com.cognine.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.cognine.model.ExcelByteStream;
import com.cognine.model.PbiTimeLineData;
import com.cognine.service.ExportExcelService;

@RestController
public class ExportExcelController {

	@Autowired
	private ExportExcelService exportExcelService;
	

	@PostMapping("exporttoexcel")
	@Secured({ "ROLE_ADMIN", "ROLE_PMO", "ROLE_DEVELOPER", "ROLE_QA", "ROLE_TECH.LEAD", "ROLE_PMO.LEAD", "ROLE_PROJECT_MANAGER"})
	public ExcelByteStream exportToExcel(@RequestBody PbiTimeLineData pbiTimeLineData) throws IOException {
		return exportExcelService.exportToExcel(pbiTimeLineData);

	}

	
}
