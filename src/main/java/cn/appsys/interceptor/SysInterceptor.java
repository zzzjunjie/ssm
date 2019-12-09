//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.interceptor;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SysInterceptor extends HandlerInterceptorAdapter {
  private Logger logger = Logger.getLogger(SysInterceptor.class);

  public SysInterceptor() {
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    this.logger.debug("SysInterceptor preHandle ==========================");
    HttpSession session = request.getSession();
    BackendUser backendUser = (BackendUser)session.getAttribute("userSession");
    DevUser devUser = (DevUser)session.getAttribute("devUserSession");
    if (devUser != null) {
      return true;
    } else if (backendUser != null) {
      return true;
    } else {
      response.sendRedirect(request.getContextPath() + "/403.jsp");
      return false;
    }
  }
}
