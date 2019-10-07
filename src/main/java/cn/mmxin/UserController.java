package cn.mmxin;

import cn.mmxin.pojo.User;
import cn.mmxin.pojo.UserExample;
import cn.mmxin.pojo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    @ResponseBody
    public boolean login(String userName, String password){
        User user  = new User();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUser_nameEqualTo(userName).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() > 0){
            return true;
        }
        return false;
    }
}
