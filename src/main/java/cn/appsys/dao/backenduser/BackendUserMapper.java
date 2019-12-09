//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.dao.backenduser;

import cn.appsys.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;

public interface BackendUserMapper {
  BackendUser getLoginUser(@Param("userCode") String var1) throws Exception;
}
