//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.backend;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppService {
  List<AppInfo> getAppInfoList(@Param("softwareName") String var1, @Param("categoryLevel1") Integer var2, @Param("categoryLevel2") Integer var3, @Param("categoryLevel3") Integer var4, @Param("flatformId") Integer var5, @Param("from") Integer var6, @Param("pageSize") Integer var7) throws Exception;

  int getAppInfoCount(@Param("softwareName") String var1, @Param("categoryLevel1") Integer var2, @Param("categoryLevel2") Integer var3, @Param("categoryLevel3") Integer var4, @Param("flatformId") Integer var5) throws Exception;

  AppInfo getAppInfo(@Param("id") Integer var1) throws Exception;

  boolean updateSatus(@Param("status") Integer var1, @Param("id") Integer var2) throws Exception;
}
