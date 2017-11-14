package com.itguang.springboot_07.web;

import com.itguang.springboot_07.entity.User;
import com.itguang.springboot_07.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author itguang
 * @create 2017-11-14 10:02
 **/
@Controller
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("/")
    public String index() {
        System.out.println("index...");

        return "redirect:/list";
    }

    @RequestMapping("list")
    public String list(Model model) {
        List<User> users = userService.getUserList();
        model.addAttribute("users", users);

        return "user/list";
    }

    //跳转到添加用户界面
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(User user) {
        System.out.println("添加user=" + user.toString());
        userService.save(user);
        return "redirect:/list";
    }

    //跳转到修改用户界面,注意要把修改的用户信息带过去
    @RequestMapping("/toEdit")
    public String toEdit(Model model, Long id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);

        return "user/userEdit";
    }

    @RequestMapping("edit")
    public String edit(User user) {
        System.out.println("修改user=" + user);
        userService.edit(user);
        return "redirect:list";
    }


    //删除,删除之后跳转到列表

    @RequestMapping("delete")
    public String delete(Long id) {
        userService.delete(id);
        return "redirect:list";
    }


}
