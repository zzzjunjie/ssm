//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.developer;

import cn.appsys.pojo.AppVersion;
import java.util.List;

public interface AppVersionService {
  List<AppVersion> getAppVersionList(Integer var1) throws Exception;

  boolean appsysadd(AppVersion var1) throws Exception;

  AppVersion getAppVersionById(Integer var1) throws Exception;

  boolean modify(AppVersion var1) throws Exception;

  boolean deleteApkFile(Integer var1) throws Exception;
}

