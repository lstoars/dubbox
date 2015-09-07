package com.alibaba.dubbo.demo.reply;

import com.alibaba.dubbo.demo.user.User;

/**
 * Created by lenovo on 2015/9/7.
 */
public class UserReply extends ApiReply {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
