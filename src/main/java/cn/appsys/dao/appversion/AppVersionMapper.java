//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.dao.appversion;

import cn.appsys.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppVersionMapper {
  List<AppVersion> getAppVersionList(@Param("appId") Integer var1) throws Exception;

  int add(AppVersion var1) throws Exception;

  int getVersionCountByAppId(@Param("appId") Integer var1) throws Exception;

  int deleteVersionByAppId(@Param("appId") Integer var1) throws Exception;

  AppVersion getAppVersionById(@Param("id") Integer var1) throws Exception;

  int modify(AppVersion var1) throws Exception;

  int deleteApkFile(@Param("id") Integer var1) throws Exception;
}
