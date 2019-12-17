//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.interceptor;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
如果session失效的话拦截所有操作，返回403页面
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
  public SysInterceptor() {
  }
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    BackendUser backendUser = (BackendUser)session.getAttribute("userSession");
    DevUser devUser = (DevUser)session.getAttribute("devUserSession");
    if (devUser != null) {
      return true;
    } else if (backendUser != null) {
      return true;
    } else {
      //如果获取session为null则跳转至403.jsp页面
      response.sendRedirect(request.getContextPath() + "/403.jsp");
      return false;
    }
  }
}
