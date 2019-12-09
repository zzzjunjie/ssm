//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.controller.backend;


import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AppService;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping({"/manage/backend/app"})
public class AppCheckController {
  private Logger logger = Logger.getLogger(AppCheckController.class);
  @Resource
  private AppService appService;
  @Resource
  private AppVersionService appVersionService;
  @Resource
  private DataDictionaryService dataDictionaryService;
  @Resource
  private AppCategoryService appCategoryService;

  public AppCheckController() {
  }

  @RequestMapping({"/list"})
  public String getAppInfoList(Model model, HttpSession session, @RequestParam(value = "querySoftwareName",required = false) String querySoftwareName, @RequestParam(value = "queryCategoryLevel1",required = false) String _queryCategoryLevel1, @RequestParam(value = "queryCategoryLevel2",required = false) String _queryCategoryLevel2, @RequestParam(value = "queryCategoryLevel3",required = false) String _queryCategoryLevel3, @RequestParam(value = "queryFlatformId",required = false) String _queryFlatformId, @RequestParam(value = "pageIndex",required = false) String pageIndex) {
    this.logger.info("getAppInfoList -- > querySoftwareName: " + querySoftwareName);
    this.logger.info("getAppInfoList -- > queryCategoryLevel1: " + _queryCategoryLevel1);
    this.logger.info("getAppInfoList -- > queryCategoryLevel2: " + _queryCategoryLevel2);
    this.logger.info("getAppInfoList -- > queryCategoryLevel3: " + _queryCategoryLevel3);
    this.logger.info("getAppInfoList -- > queryFlatformId: " + _queryFlatformId);
    this.logger.info("getAppInfoList -- > pageIndex: " + pageIndex);
    List<AppInfo> appInfoList = null;
    List<DataDictionary> flatFormList = null;
    List<AppCategory> categoryLevel1List = null;
    List<AppCategory> categoryLevel2List = null;
    List<AppCategory> categoryLevel3List = null;
    int pageSize = 5;
    Integer currentPageNo = 1;
    if (pageIndex != null) {
      try {
        currentPageNo = Integer.valueOf(pageIndex);
      } catch (NumberFormatException var26) {
        var26.printStackTrace();
      }
    }

    Integer queryCategoryLevel1 = null;
    if (_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")) {
      queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
    }

    Integer queryCategoryLevel2 = null;
    if (_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")) {
      queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
    }

    Integer queryCategoryLevel3 = null;
    if (_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")) {
      queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
    }

    Integer queryFlatformId = null;
    if (_queryFlatformId != null && !_queryFlatformId.equals("")) {
      queryFlatformId = Integer.parseInt(_queryFlatformId);
    }

    int totalCount = 0;

    try {
      totalCount = this.appService.getAppInfoCount(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId);
    } catch (Exception var25) {
      var25.printStackTrace();
    }

    PageSupport pages = new PageSupport();
    pages.setCurrentPageNo(currentPageNo);
    pages.setPageSize(pageSize);
    pages.setTotalCount(totalCount);
    int totalPageCount = pages.getTotalPageCount();
    if (currentPageNo < 1) {
      currentPageNo = 1;
    } else if (currentPageNo > totalPageCount) {
      currentPageNo = totalPageCount;
    }

    try {
      appInfoList = this.appService.getAppInfoList(querySoftwareName, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, currentPageNo, Integer.valueOf(pageSize));
      flatFormList = this.getDataDictionaryList("APP_FLATFORM");
      categoryLevel1List = this.appCategoryService.getAppCategoryListByParentId((Integer)null);
    } catch (Exception var24) {
      var24.printStackTrace();
    }

    model.addAttribute("appInfoList", appInfoList);
    model.addAttribute("flatFormList", flatFormList);
    model.addAttribute("categoryLevel1List", categoryLevel1List);
    model.addAttribute("pages", pages);
    model.addAttribute("querySoftwareName", querySoftwareName);
    model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
    model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
    model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
    model.addAttribute("queryFlatformId", queryFlatformId);
    if (queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")) {
      categoryLevel2List = this.getCategoryList(queryCategoryLevel1.toString());
      model.addAttribute("categoryLevel2List", categoryLevel2List);
    }

    if (queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")) {
      categoryLevel3List = this.getCategoryList(queryCategoryLevel2.toString());
      model.addAttribute("categoryLevel3List", categoryLevel3List);
    }

    return "backend/applist";
  }

  public List<DataDictionary> getDataDictionaryList(String typeCode) {
    List dataDictionaryList = null;

    try {
      dataDictionaryList = this.dataDictionaryService.getDataDictionaryList(typeCode);
    } catch (Exception var4) {
      var4.printStackTrace();
    }

    return dataDictionaryList;
  }

  public List<AppCategory> getCategoryList(String pid) {
    List categoryLevelList = null;

    try {
      categoryLevelList = this.appCategoryService.getAppCategoryListByParentId(pid == null ? null : Integer.parseInt(pid));
    } catch (Exception var4) {
      var4.printStackTrace();
    }

    return categoryLevelList;
  }

  @RequestMapping(
   value = {"/categorylevellist.json"},
   method = {RequestMethod.GET}
  )
  @ResponseBody
  public List<AppCategory> getAppCategoryList(@RequestParam String pid) {
    this.logger.debug("getAppCategoryList pid ============ " + pid);
    if (pid.equals("")) {
      pid = null;
    }

    return this.getCategoryList(pid);
  }

  @RequestMapping(
   value = {"/check"},
   method = {RequestMethod.GET}
  )
  public String check(@RequestParam(value = "aid",required = false) String appId, @RequestParam(value = "vid",required = false) String versionId, Model model) {
    AppInfo appInfo = null;
    AppVersion appVersion = null;

    try {
      appInfo = this.appService.getAppInfo(Integer.parseInt(appId));
      appVersion = this.appVersionService.getAppVersionById(Integer.parseInt(versionId));
    } catch (Exception var7) {
      var7.printStackTrace();
    }

    model.addAttribute(appVersion);
    model.addAttribute(appInfo);
    return "backend/appcheck";
  }

  @RequestMapping(
   value = {"/checksave"},
   method = {RequestMethod.POST}
  )
  public String checkSave(AppInfo appInfo) {
    this.logger.debug("appInfo =========== > " + appInfo.getStatus());

    try {
      if (this.appService.updateSatus(appInfo.getStatus(), appInfo.getId())) {
        return "redirect:/manage/backend/app/list";
      }
    } catch (Exception var3) {
      var3.printStackTrace();
    }

    return "backend/appcheck";
  }
}
