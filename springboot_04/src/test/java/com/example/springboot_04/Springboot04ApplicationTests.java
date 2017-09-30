package com.example.springboot_04;

import com.example.springboot_04.dao.UserRespository;
import com.example.springboot_04.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot04ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    public UserRespository userRespository;

    @Test
    public void testRespository() {

        System.out.println("===count====" + userRespository.count());

        List<User> users = userRespository.findAll();
        System.out.println(users);
    }

    @Test
    public void test() {
//        userRespository.findByUserNameOrderByAgeDesc("itguang", 12);
        userRespository.findByUserNameOrAge("itguang", 12);
    }


    //测试分页查询
    @Test
    public void testPage() {
        int page = 1, size = 3;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> userPage = userRespository.findAll(pageable);
        System.out.println("====TotalElements======" + userPage.getTotalElements());//数据总条数
        System.out.println("====TotalPages====" + userPage.getTotalPages());//总页数
        System.out.println("====getContent====" + userPage.getContent());//得到User集合


    }

    //测试自定义sql语句
    @Test
    public void testSql() {

        userRespository.deleteUserById(1L);

    }

}
