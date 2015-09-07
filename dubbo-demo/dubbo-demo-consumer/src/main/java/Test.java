import com.alibaba.dubbo.demo.bid.BidRequest;
import com.alibaba.dubbo.demo.bid.BidService;
import com.alibaba.dubbo.demo.bid.Device;
import com.alibaba.dubbo.demo.bid.Geo;
import com.alibaba.dubbo.demo.bid.Impression;
import com.alibaba.dubbo.demo.user.User;
import com.alibaba.dubbo.demo.user.UserService;
import com.alibaba.dubbo.demo.user.facade.AnotherUserRestService;
import com.alibaba.dubbo.demo.user.facade.UserRestService;
import com.alibaba.dubbo.rpc.service.EchoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/8/29.
 */
public class Test {

	static ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/spring/dubbo-demo-consumer.xml");

	public static void main(String[] args) {

       /*BidService bidService = (BidService) ac.getBean("bidService");
		BidRequest request = new BidRequest();

        Impression imp = new Impression();
        imp.setBidFloor(1.1);
        imp.setId("abc");
        List<Impression> imps = new ArrayList<Impression>(1);
        imps.add(imp);
        request.setImpressions(imps);

        Geo geo = new Geo();
        geo.setCity("beijing");
        geo.setCountry("china");
        geo.setLat(100.1f);
        geo.setLon(100.1f);

        Device device = new Device();
        device.setMake("apple");
        device.setOs("ios");
        device.setVersion("7.0");
        device.setLang("zh_CN");
        device.setModel("iphone");
        device.setGeo(geo);
        request.setDevice(device);

//        long start = System.currentTimeMillis();

        //for (int i = 0; i < 10000; i ++) {
        //    System.out.println(bidService.bid(request).getId());
        //}

        System.out.println("SUCCESS: got bid response id: " + bidService.bid(request).getId());*/

        /*UserService userService = (UserService) ac.getBean("userService");
		User user = new User();
        user.setId(11L);
        user.setName("testuser");
        userService.registerUser(user);
        userService.getUser(100L);
        userService.registerUser(user);
        userService.getUser(100L);
        userService.registerUser(user);
        userService.getUser(100L);*/


        /*BidService bidService = (BidService) ac.getBean("bidService");
		bidService.throwNPE();*/

		UserService userService = (UserService) ac.getBean("userService");
		BidService bidService = (BidService) ac.getBean("bidService");

		//for (int i = 0; i < 5000; i++) {
		System.out.println(userService.getUser("111"));

		EchoService echoService = (EchoService)userService;
		System.out.println(echoService.$echo("OK"));
		//}
		echoService = (EchoService) bidService;
		echoService.$echo("OK");
	}
}
