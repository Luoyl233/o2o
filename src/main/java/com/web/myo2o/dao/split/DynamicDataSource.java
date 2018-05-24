package com.web.myo2o.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author lw
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDbType();
    }
}

