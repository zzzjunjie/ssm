//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.backend;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BackendUserServiceImpl implements BackendUserService {
  @Resource
  private BackendUserMapper mapper;

  public BackendUserServiceImpl() {
  }

  public BackendUser login(String userCode, String userPassword) throws Exception {
    BackendUser user = null;
    user = this.mapper.getLoginUser(userCode);
    if (user != null && !user.getUserPassword().equals(userPassword)) {
      user = null;
    }

    return user;
  }
}
