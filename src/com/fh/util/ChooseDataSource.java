package com.fh.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ChooseDataSource extends AbstractRoutingDataSource {  
	@Override
	protected Object determineCurrentLookupKey() {
//		//System.out.println(HandleDataSource.getDataSource());
		return HandleDataSource.getDataSource();
	}

}
