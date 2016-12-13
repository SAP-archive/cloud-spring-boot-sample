package com.sap.hana.cloud.samples.springboot.web;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sap.hana.cloud.samples.springboot.util.DBInformation;

@Controller
public class HomeController
{
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired(required = false)
	DataSource dataSource;

	// @Autowired ApplicationInstanceInfo instanceInfo;

	@RequestMapping("/")
	public String home(Model model)
	{
		Map<Class<?>, String> services = new LinkedHashMap<Class<?>, String>();
		
		if (dataSource != null)
		{
			services.put(dataSource.getClass(), toString(dataSource));
			model.addAttribute("services", services.entrySet());
		}
		
		// model.addAttribute("instanceInfo", instanceInfo);

		return "home";
	}

	private String toString(DataSource dataSource)
	{
		if (dataSource == null)
		{
			return "<none>";
		}
		else
		{
			try
			{				
				DBInformation dbInfo = new DBInformation(dataSource);
				return stripCredentials(dbInfo.getUrl());
			}
			catch (Exception ex)
			{
				log.error("An error occured while trying to acquire DB information", ex);
				return "<error>";
			}
		}
	}

	private String stripCredentials(String urlString)
	{
		try
		{
			if (urlString.startsWith("jdbc:"))
			{
				urlString = urlString.substring("jdbc:".length());
			}
			URI url = new URI(urlString);
			return new URI(url.getScheme(), null, url.getHost(), url.getPort(), url.getPath(), null, null).toString();
		}
		catch (URISyntaxException e)
		{
			System.out.println(e);
			return "<bad url> " + urlString;
		}
	}

}
