package com.cognine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.cognine.model.AllocatedProjectResource;
import com.cognine.model.AvailableResource;
import com.cognine.model.ResourceAllocation;

@Configuration
public class AvailableResourceAllocation {
	private static final long ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;

	AvailableResource resource = null;

	public void availableResourceAllocation(Date startDate, int remainingProjectAllocation, Date endDate,
			List<AvailableResource> resourceList, ResourceAllocation resultMapList) {

		resource = new AvailableResource();
		resource.setStartDate(startDate);
		resource.setAvailableProjectAllocation(remainingProjectAllocation);
		resource.setEndDate(endDate);

		resourceList.add(resource);
		resultMapList.setAvailableResource(resourceList);

	}

	public int remainingProjectAllocation(int previousProjectAllocation) {
		return 100 - previousProjectAllocation;

	}

	public Date getEndDateAsStartDate(AllocatedProjectResource allocatedProjectResource) {
		return allocatedProjectResource.getGeneratedDates();
	}

	public Date getNextDate(Date curDate) throws ParseException {

		Date nextDate = new Date(curDate.getTime() + ONE_DAY_MILLI_SECONDS);
		String date = null;
		date = new SimpleDateFormat("yyyy-MM-dd").format(nextDate);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return new java.sql.Date(df.parse(date).getTime());
	}

}
