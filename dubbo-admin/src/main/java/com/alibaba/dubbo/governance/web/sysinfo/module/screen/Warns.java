package com.alibaba.dubbo.governance.web.sysinfo.module.screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.governance.service.ConsumerService;
import com.alibaba.dubbo.governance.service.ProviderService;
import com.alibaba.dubbo.governance.web.common.module.screen.Restful;
import com.alibaba.dubbo.registry.common.domain.Consumer;
import com.alibaba.dubbo.registry.common.domain.Provider;
import com.alibaba.fastjson.JSONArray;

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
			if (!providerServices.contains(service) && !getIgnoreService().contains(service)) {
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

	private List<String> getIgnoreService() {
		List<String> result = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(new File("/home/admin/noprovider_ignore.txt"));
			while (scanner.hasNextLine()) {
				result.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
		}
		return result;
	}

	/**  
	 * @功能描述: 根据IP查询服务器的提供接口以及消费接口
	 * @创建作者: 欧阳文斌
	 * @创建日期: 2016年12月5日 上午10:28:42
	 * @param ip
	 * @throws IOException
	 */ 
	public void queryByIp(Map<String, Object> context) throws IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(context==null){result.put("status", -1);result.put("msg", "参数错误");}
		String ip = (String) context.get("ip");
		if(StringUtils.isEmpty(ip)){result.put("status", -1);result.put("msg", "参数错误");}
		String port = (String) context.get("port");
		if(StringUtils.isEmpty(port)) port = "20880";
		String providerIp = ip+":"+port;
		//默认端口  如果一台服务器多个提供者，需要拓展这里的方法
		List<Provider> providers= providerService.findByAddress(providerIp);
		List<Consumer> consumers= consumerService.findByAddress(ip);

		List<CheckServiceModel> providerResult = new ArrayList<Warns.CheckServiceModel>();
		if(providers!=null){
			for (Provider provider : providers) {
				CheckServiceModel model = new CheckServiceModel();
				model.setUrl(provider.getUrl());
				providerResult.add(model);
			}
		}
		result.put("providerDatas", providerResult);
		List<CheckServiceModel> consumerResult = new ArrayList<Warns.CheckServiceModel>();
		if(consumers!=null){
			for (Consumer consumer : consumers) {
				CheckServiceModel model = new CheckServiceModel();
				model.setService(consumer.getService());
				consumerResult.add(model);
			}
		}
		result.put("consumerDatas", consumerResult);
		result.put("status", 1);result.put("msg", "调用成功");
		response.getWriter().println(JSONArray.toJSONString(result));

	}
	

	/**  
	 * @功能描述: 根据接口名查询提供者的URL
	 * @创建作者: 欧阳文斌
	 * @创建日期: 2016年12月5日 上午10:28:42
	 * @param ip
	 * @throws IOException
	 */ 
	public void queryByService(Map<String, Object> context) throws IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(context==null){result.put("status", -1);result.put("msg", "参数错误");}
		String interFace = (String) context.get("interface");
		if(StringUtils.isEmpty(interFace)){result.put("status", -1);result.put("msg", "参数错误");}
		List<Provider> providers= providerService.findByService(interFace);

		List<CheckServiceModel> providerResult = new ArrayList<Warns.CheckServiceModel>();
		if(providers!=null){
			for (Provider provider : providers) {
				CheckServiceModel model = new CheckServiceModel();
				model.setUrl(provider.getUrl());
				providerResult.add(model);
			}
		}
		result.put("providerDatas", providerResult);
		result.put("status", 1);result.put("msg", "调用成功");
		response.getWriter().println(JSONArray.toJSONString(result));

	}
	
	class CheckServiceModel implements Serializable{
		private static final long serialVersionUID = 1L;
		/** dubbo url */
		private String url;
		/** 接口 */
		private String service;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getService() {
			return service;
		}
		public void setService(String service) {
			this.service = service;
		}
	}
}
