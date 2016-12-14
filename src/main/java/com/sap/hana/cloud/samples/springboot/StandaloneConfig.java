package com.sap.hana.cloud.samples.springboot;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.sap.hana.cloud.samples.springboot.util.DBInformation;

//@Configuration
//@Profile({"standalone", "dev"})
public class StandaloneConfig 
{
	private static final Logger log = LoggerFactory.getLogger(StandaloneConfig.class);
	
	@Value("${spring.datasource.url}") private String url;
	@Value("${spring.datasource.username}") private String username;
	@Value("${spring.datasource.password}") private String password;
	@Value("${spring.datasource.driver-class-name}") private String driver;

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatFactory() 
	{
		
		return new TomcatEmbeddedServletContainerFactory() 
	    {
			/*
			 * Enable JNDI support of embedded Tomcat
			 */
			@Override
	        protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) 
	        {
	            tomcat.enableNaming();
	            return super.getTomcatEmbeddedServletContainer(tomcat);
	        }
	        
	        @Override
			protected void postProcessContext(Context context) 
		    {
				ContextResource resource = new ContextResource();
				
				resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				
				resource.setName("jdbc/DefaultDB");
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", driver);
				resource.setProperty("url", url);
				resource.setProperty("password", password);
                resource.setProperty("username", username);

				context.getNamingResources().addResource(resource);
			}
	    };   
	}
	
	@Bean(destroyMethod="")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException, SQLException
	{
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/jdbc/DefaultDB");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();
		
		DataSource ds = (DataSource)bean.getObject();
		
		DBInformation dbInfo = new DBInformation(ds.getConnection().getMetaData());
		log.info(dbInfo.toString());
		
		return ds;
	}
	
}