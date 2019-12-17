//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.controller.backend;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/manage"})
public class UserLoginController {
  @Resource
  private BackendUserService backendUserService;

  public UserLoginController() {
  }

  @RequestMapping({"/login"})
  public String login() {
    return "backendlogin";
  }

  @RequestMapping(
   value = {"/dologin"},
   method = {RequestMethod.POST}
  )
  public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpServletRequest request, HttpSession session) {
    BackendUser user = null;

    try {
      user = this.backendUserService.login(userCode, userPassword);
    } catch (Exception var7) {
      var7.printStackTrace();
    }

    if (user != null) {
      session.setAttribute("userSession", user);
      return "redirect:/manage/backend/main";
    } else {
      request.setAttribute("error", "用户名或密码不正确");
      return "backendlogin";
    }
  }

  @RequestMapping({"/backend/main"})
  public String main(HttpSession session) {
    return session.getAttribute("userSession") == null ? "redirect:/manage/login" : "backend/main";
  }

  @RequestMapping({"/logout"})
  public String logout(HttpSession session) {
    session.removeAttribute("userSession");
    return "backendlogin";
  }
}
