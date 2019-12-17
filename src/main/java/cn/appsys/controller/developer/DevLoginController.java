//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.controller.developer;


import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/dev"})
public class DevLoginController {
  @Autowired
  private DevUserService devUserService;

  public DevLoginController() {
  }

  @RequestMapping({"/login"})
  public String login() {
    return "devlogin";
  }
/*
前端输入账号密码，验证账号密码是否存在，如果存在则把账号密码访日session域里面.请求方式为post
 */
  @RequestMapping(
   value = {"/dologin"},
   method = {RequestMethod.POST}
  )
  public String doLogin(@RequestParam String devCode, @RequestParam String devPassword, HttpServletRequest request, HttpSession session) {
    DevUser user = null;

    try {
      user = devUserService.login(devCode, devPassword);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //如果账号密码存在，则把开发者的信息放入session域对象并且跳转到开发者首页，否者返回错误信息到登陆页面
    if (user != null) {
      session.setAttribute("devUserSession", user);
      return "redirect:/dev/flatform/main";
    } else {
      request.setAttribute("error", "用户名或密码不正确");
      return "devlogin";
    }
  }

  @RequestMapping({"/flatform/main"})
  public String main(HttpSession session) {
    return session.getAttribute("devUserSession") == null ? "redirect:/dev/login" : "developer/main";
  }
//退出登陆时清除session
  @RequestMapping({"/logout"})
  public String logout(HttpSession session) {
    session.removeAttribute("devUserSession");
    return "devlogin";
  }
}
