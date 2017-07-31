package cn.itcast.bos.service.user;

import cn.itcast.activemq.producer.queue.bos.domain.user.User;

import java.util.List;

public interface UserService {
	public void save(User user);

	public void delete(User user);

	public User findUserById(Integer id);

	public List<User> findAll();

	// 业务 登录
	public User findUserByUsernameAndPassword(String username, String password);

	User findUserByTelephone(String telephone);

	void gobackpassword(String telephone, String password);

	void gobackpassword1(String email, String password);
}
