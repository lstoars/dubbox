package com.alibaba.dubbo.governance.web.sysinfo.module.screen;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.governance.service.ConsumerService;
import com.alibaba.dubbo.governance.service.ProviderService;
import com.alibaba.dubbo.governance.web.common.module.screen.Restful;

/**
 * Created by lenovo on 2016/3/3.
 */
public class Warns extends Restful {

	@Autowired
	private ProviderService providerService;
	@Autowired
	private ConsumerService consumerService;

	@Autowired
	HttpServletResponse response;

	/**
	 * 没有提供方的服务
	 * 
	 * @param context
	 * @throws IOException
	 */
	public void noprovider(Map<String, Object> context) throws IOException {
		List<String> providerServices = providerService.findServices();
		List<String> consumerServices = consumerService.findServices();
		Set<String> services = new TreeSet<String>();
		if (providerServices != null) {
			services.addAll(providerServices);
		}
		if (consumerServices != null) {
			services.addAll(consumerServices);
		}
		StringBuilder noProviderService = new StringBuilder();
		for (String service : services) {
			if (!providerServices.contains(service)) {
				noProviderService.append(service).append(",");
			}
		}
		if (StringUtils.isNotEmpty(noProviderService.toString())) {
			response.getWriter()
					.println(noProviderService.deleteCharAt(noProviderService.length() - 1).toString());
		} else {
			response.getWriter().println("1");
		}

	}
}
