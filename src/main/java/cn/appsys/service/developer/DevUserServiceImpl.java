//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.developer;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {
  @Resource
  private DevUserMapper mapper;

  public DevUserServiceImpl() {
  }

  public DevUser login(String devCode, String devPassword) throws Exception {
    DevUser user = null;
    user = this.mapper.getLoginUser(devCode);
    if (user != null && !user.getDevPassword().equals(devPassword)) {
      user = null;
    }

    return user;
  }
}
