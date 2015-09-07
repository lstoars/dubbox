package com.alibaba.dubbo.demo.user;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.demo.reply.UserReply;
import com.alibaba.dubbo.rpc.service.EchoService;

/**
 * Created by lenovo on 2015/9/7.
 */
public class UserServiceStub implements UserService, EchoService {
	private final UserService userService;

	public UserServiceStub(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserReply getUser(Long id) {
		if (id < 1) {
			UserReply reply = new UserReply();
			reply.setCode("998");
			reply.setMsg("参数错误");
			return reply;
		}
		return userService.getUser(id);
	}

	@Override
	public UserReply getUser(String userName) {
		if (StringUtils.isEmpty(userName) || userName.length() < 1 || userName.length() > 20) {
			UserReply reply = new UserReply();
			reply.setCode("998");
			reply.setMsg("参数错误");
			return reply;
		}
		return userService.getUser(userName);
	}

	@Override
	public Long registerUser(User user) {
		return userService.registerUser(user);
	}

	@Override
	public Object $echo(Object message) {
		return ((EchoService) userService).$echo(message);
	}
}
