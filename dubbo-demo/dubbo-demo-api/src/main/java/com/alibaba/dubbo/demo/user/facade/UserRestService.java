/**
 * Copyright 1999-2014 dangdang.com.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.demo.user.facade;

import com.alibaba.dubbo.demo.user.User;

import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;

/**
 * This interface acts as some kind of service broker for the original UserService
 * <p/>
 * Here we want to simulate the twitter/weibo rest api, e.g.
 * <p/>
 * http://localhost:8888/user/1.json
 * http://localhost:8888/user/1.xml
 *
 * @author lishen
 */
public interface UserRestService {

    /**
     * the request object is just used to test jax-rs injection.
     */
    User getUser(@Min(value = 1L, message = "User ID must be greater than 1") Long id/*, HttpServletRequest request*/);

    RegistrationResult registerUser(User user);

    User getUser(Long id, String application);
}
