//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.developer;


import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {
  @Resource
  private AppInfoMapper mapper;
  @Resource
  private AppVersionMapper appVersionMapper;

  public AppInfoServiceImpl() {
  }

  public boolean add(AppInfo appInfo) throws Exception {
    boolean flag = false;
    if (this.mapper.add(appInfo) > 0) {
      flag = true;
    }

    return flag;
  }

  public boolean modify(AppInfo appInfo) throws Exception {
    boolean flag = false;
    if (this.mapper.modify(appInfo) > 0) {
      flag = true;
    }

    return flag;
  }

  public boolean deleteAppInfoById(Integer delId) throws Exception {
    boolean flag = false;
    if (this.mapper.deleteAppInfoById(delId) > 0) {
      flag = true;
    }

    return flag;
  }

  public AppInfo getAppInfo(Integer id, String APKName) throws Exception {
    return this.mapper.getAppInfo(id, APKName);
  }

  public List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId, Integer currentPageNo, Integer pageSize) throws Exception {
    return this.mapper.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (currentPageNo - 1) * pageSize, pageSize);
  }

  public int getAppInfoCount(String querySoftwareName, Integer queryStatus, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId) throws Exception {
    return this.mapper.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
  }

  public boolean deleteAppLogo(Integer id) throws Exception {
    boolean flag = false;
    if (this.mapper.deleteAppLogo(id) > 0) {
      flag = true;
    }

    return flag;
  }

  public boolean appsysdeleteAppById(Integer id) throws Exception {
    boolean flag = false;
    int versionCount = this.appVersionMapper.getVersionCountByAppId(id);
    List<AppVersion> appVersionList = null;
    if (versionCount > 0) {
      appVersionList = this.appVersionMapper.getAppVersionList(id);
      Iterator var6 = appVersionList.iterator();

      while(var6.hasNext()) {
        AppVersion appVersion = (AppVersion)var6.next();
        if (appVersion.getApkLocPath() != null && !appVersion.getApkLocPath().equals("")) {
          File file = new File(appVersion.getApkLocPath());
          if (file.exists() && !file.delete()) {
            throw new Exception();
          }
        }
      }

      this.appVersionMapper.deleteVersionByAppId(id);
    }

    AppInfo appInfo = this.mapper.getAppInfo(id, (String)null);
    if (appInfo.getLogoLocPath() != null && !appInfo.getLogoLocPath().equals("")) {
      File file = new File(appInfo.getLogoLocPath());
      if (file.exists() && !file.delete()) {
        throw new Exception();
      }
    }

    if (this.mapper.deleteAppInfoById(id) > 0) {
      flag = true;
    }

    return flag;
  }

  public boolean appsysUpdateSaleStatusByAppId(AppInfo appInfoObj) throws Exception {
    Integer operator = appInfoObj.getModifyBy();
    if (operator >= 0 && appInfoObj.getId() >= 0) {
      AppInfo appInfo = this.mapper.getAppInfo(appInfoObj.getId(), (String)null);
      if (appInfo == null) {
        return false;
      } else {
        switch((int) appInfo.getStatus()) {
          case 2:
            this.onSale(appInfo, operator, 4, 2);
            break;
          case 3:
          default:
            return false;
          case 4:
            this.offSale(appInfo, operator, 5);
            break;
          case 5:
            this.onSale(appInfo, operator, 4, 2);
        }

        return true;
      }
    } else {
      throw new Exception();
    }
  }

  private void onSale(AppInfo appInfo, Integer operator, Integer appInfStatus, Integer versionStatus) throws Exception {
    this.offSale(appInfo, operator, appInfStatus);
    this.setSaleSwitchToAppVersion(appInfo, operator, versionStatus);
  }

  private boolean offSale(AppInfo appInfo, Integer operator, Integer appInfStatus) throws Exception {
    AppInfo _appInfo = new AppInfo();
    _appInfo.setId(appInfo.getId());
    _appInfo.setStatus(appInfStatus);
    _appInfo.setModifyBy(operator);
    _appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
    this.mapper.modify(_appInfo);
    return true;
  }

  private boolean setSaleSwitchToAppVersion(AppInfo appInfo, Integer operator, Integer saleStatus) throws Exception {
    AppVersion appVersion = new AppVersion();
    appVersion.setId(appInfo.getVersionId());
    appVersion.setPublishStatus(saleStatus);
    appVersion.setModifyBy(operator);
    appVersion.setModifyDate(new Date(System.currentTimeMillis()));
    this.appVersionMapper.modify(appVersion);
    return false;
  }
}
