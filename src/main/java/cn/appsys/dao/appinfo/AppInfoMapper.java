//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.dao.appinfo;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInfoMapper {
  int add(AppInfo var1) throws Exception;

  int modify(AppInfo var1) throws Exception;

  int deleteAppInfoById(@Param("id") Integer var1) throws Exception;

  List<AppInfo> getAppInfoList(@Param("softwareName") String var1, @Param("status") Integer var2, @Param("categoryLevel1") Integer var3, @Param("categoryLevel2") Integer var4, @Param("categoryLevel3") Integer var5, @Param("flatformId") Integer var6, @Param("devId") Integer var7, @Param("from") Integer var8, @Param("pageSize") Integer var9) throws Exception;

  int getAppInfoCount(@Param("softwareName") String var1, @Param("status") Integer var2, @Param("categoryLevel1") Integer var3, @Param("categoryLevel2") Integer var4, @Param("categoryLevel3") Integer var5, @Param("flatformId") Integer var6, @Param("devId") Integer var7) throws Exception;

  AppInfo getAppInfo(@Param("id") Integer var1, @Param("APKName") String var2) throws Exception;

  int deleteAppLogo(@Param("id") Integer var1) throws Exception;

  int updateVersionId(@Param("versionId") Integer var1, @Param("id") Integer var2) throws Exception;

  int updateSaleStatusByAppId(@Param("id") Integer var1) throws Exception;

  int updateSatus(@Param("status") Integer var1, @Param("id") Integer var2) throws Exception;
}
