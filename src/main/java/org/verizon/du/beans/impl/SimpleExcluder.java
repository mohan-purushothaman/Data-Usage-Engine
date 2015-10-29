/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.verizon.du.beans.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.verizon.du.core.DataUsage;
import org.verizon.du.core.ExcludeFilter;
import org.verizon.du.core.Filter;

/**
 *
 * @author Administrator
 */
@Component
public class SimpleExcluder implements ExcludeFilter{

    private DataSource dataSource;
    
   // @Autowired
    //@Qualifier(value ="filterList")
    List<Filter> excludeFilterSet;
    
    @Autowired
    public SimpleExcluder( DataSource dataSource) {
        if(null==excludeFilterSet){
            this.dataSource=dataSource;
            excludeFilterSet=loadExcludeFilters();
        }
    }
       
    @Override
    public boolean canExclude(DataUsage usage) {
       for(Filter fltr:excludeFilterSet){
           if(fltr.canExclude(usage))
                return true; 
       }
      return  false; 
    }
    
   // @Bean(name="filterList")
    private List<Filter> loadExcludeFilters(){
        
       String query="SELECT * FROM EXCLUDE_USAGE_FILTERS";
       List<Filter> filters=new JdbcTemplate(dataSource).query(query,new FilterMapper());
       return filters;		
    }
    public class FilterMapper implements RowMapper<Filter> {
        @Override
        public Filter mapRow(ResultSet rs, int rowNum) throws SQLException {
         Filter filterObj = new Filter(rs.getString("CUSTID"),rs.getString("WEBSITE"),rs.getInt("START_TIME_HR"),rs.getInt("START_TIME_MIN"),
                 rs.getInt("END_TIME_HR"),rs.getInt("END_TIME_MIN"));
         return filterObj;
       }
    }
    
}