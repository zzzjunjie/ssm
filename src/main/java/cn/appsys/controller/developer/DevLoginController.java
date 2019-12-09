//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.controller.developer;


import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/dev"})
public class DevLoginController {
  private Logger logger = Logger.getLogger(DevLoginController.class);
  @Resource
  private DevUserService devUserService;

  public DevLoginController() {
  }

  @RequestMapping({"/login"})
  public String login() {
    this.logger.debug("LoginController welcome AppInfoSystem develpor==================");
    return "devlogin";
  }

  @RequestMapping(
   value = {"/dologin"},
   method = {RequestMethod.POST}
  )
  public String doLogin(@RequestParam String devCode, @RequestParam String devPassword, HttpServletRequest request, HttpSession session) {
    this.logger.debug("doLogin====================================");
    DevUser user = null;

    try {
      user = this.devUserService.login(devCode, devPassword);
    } catch (Exception var7) {
      var7.printStackTrace();
    }

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

  @RequestMapping({"/logout"})
  public String logout(HttpSession session) {
    session.removeAttribute("devUserSession");
    return "devlogin";
  }
}
