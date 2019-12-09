//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.developer;

import cn.appsys.pojo.AppInfo;
import java.util.List;

public interface AppInfoService {
  boolean add(AppInfo var1) throws Exception;

  boolean modify(AppInfo var1) throws Exception;

  boolean deleteAppInfoById(Integer var1) throws Exception;

  List<AppInfo> getAppInfoList(String var1, Integer var2, Integer var3, Integer var4, Integer var5, Integer var6, Integer var7, Integer var8, Integer var9) throws Exception;

  int getAppInfoCount(String var1, Integer var2, Integer var3, Integer var4, Integer var5, Integer var6, Integer var7) throws Exception;

  AppInfo getAppInfo(Integer var1, String var2) throws Exception;

  boolean deleteAppLogo(Integer var1) throws Exception;

  boolean appsysdeleteAppById(Integer var1) throws Exception;

  boolean appsysUpdateSaleStatusByAppId(AppInfo var1) throws Exception;
}
