package ssm.music.test;

import com.cloudmusic.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cloudmusic.service.UserService;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class Test {
	
//	@Autowired
//	UserDao userdao;
	
	@Autowired
	private UserService userService;
	@org.junit.Test
	public void testUserById() {
		System.out.println(userService.getUserById(2).getUsername());
	}
	
//	@org.junit.Test
//	public void testUserList() {
//		List<User> userList = userdao.getUserList();
//		for(int i = 0; i<userList.size(); i++) {
//			System.out.println(userList.get(i).getUsername());
//		}
//	}

	@org.junit.Test
	public void testPageHepler(){
		PageHelper.startPage(2,2);
		List<Map<String, Object>> list = userService.getUserList();
		System.out.println(list.size());
		PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String, Object>>(list);
		long total = pageInfo.getTotal();
		System.out.println(total);
	}
}
