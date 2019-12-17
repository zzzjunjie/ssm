//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.controller.developer;

import cn.appsys.pojo.*;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/*
开发者后台管理相关操作
 */
@Controller
@RequestMapping({"/dev/flatform/app"})
public class AppController {
  @Resource
  private AppInfoService appInfoService;
  @Resource
  private DataDictionaryService dataDictionaryService;
  @Resource
  private AppCategoryService appCategoryService;
  @Resource
  private AppVersionService appVersionService;

  public AppController() {
  }

  //查询APP的所有操作
  @RequestMapping({"/list"})
  public String getAppInfoList(Model model, HttpSession session, @RequestParam(value = "querySoftwareName",required = false) String querySoftwareName, @RequestParam(value = "queryStatus",required = false) String _queryStatus, @RequestParam(value = "queryCategoryLevel1",required = false) String _queryCategoryLevel1, @RequestParam(value = "queryCategoryLevel2",required = false) String _queryCategoryLevel2, @RequestParam(value = "queryCategoryLevel3",required = false) String _queryCategoryLevel3, @RequestParam(value = "queryFlatformId",required = false) String _queryFlatformId, @RequestParam(value = "pageIndex",required = false) String pageIndex) {
    //默认分页大小10、默认总总数0、默认当前页面的页码为1
    int pageSize = 10, totalCount = 0,currentPageNo = 1;
    //根据用户ID查询用户所有上传的开发软件
    Integer devId = ((DevUser)session.getAttribute("devUserSession")).getId();
    List<AppInfo> appInfoList = null;
    //APP状态列表
    List<DataDictionary> statusList = null;
    //APP所属平台
    List<DataDictionary> flatFormList = null;
    //APP类别信息分别代表1，2，3级分类
    List<AppCategory> categoryLevel1 = null;
    List<AppCategory> categoryLevel2 = null;
    List<AppCategory> categoryLevel3 = null;

    //如果有传入页面的话就使用传入的页码，否者直接使用默认页码和默认页面大小
    if (pageIndex != null) {
      try {
        currentPageNo = Integer.valueOf(pageIndex);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
//组合查询条件
    //按照APP的状态查询
    Integer queryStatus = null;
    if (_queryStatus != null && !_queryStatus.equals("")) {
      queryStatus = Integer.parseInt(_queryStatus);
    }
    //按照APP一级分类查询
    Integer queryCategoryLevel1 = null;
    if (_queryCategoryLevel1 != null && !_queryCategoryLevel1.equals("")) {
      queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
    }
    //按照APP二级分类查询
    Integer queryCategoryLevel2 = null;
    if (_queryCategoryLevel2 != null && !_queryCategoryLevel2.equals("")) {
      queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
    }
    //按照APP三级分类查询
    Integer queryCategoryLevel3 = null;
    if (_queryCategoryLevel3 != null && !_queryCategoryLevel3.equals("")) {
      queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3);
    }
    //按照APP所属的平台查询
    Integer queryFlatformId = null;
    if (_queryFlatformId != null && !_queryFlatformId.equals("")) {
      queryFlatformId = Integer.parseInt(_queryFlatformId);
    }
//组合查询条件完毕

    try {
      //根据上面的组合条件查询符合的APP数量，用于分页
      totalCount = appInfoService.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    //分页插件实例化
    PageSupport pages = new PageSupport();
    //设置当前的页码
    pages.setCurrentPageNo(currentPageNo);
    //设置每一页的大小，默认为5条数据/页
    pages.setPageSize(pageSize);
    //设置总共的数据条数
    pages.setTotalCount(totalCount);
    int totalPageCount = pages.getTotalPageCount();
    //页码最小为1
    if (currentPageNo < 1) {
      currentPageNo = 1;
    } else if (currentPageNo > totalPageCount) {
      currentPageNo = totalPageCount;
    }

    try {
      //查询所有符合组合条件的APP信息列表
      appInfoList = appInfoService.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, currentPageNo, Integer.valueOf(pageSize));
      //获取APP类型编码，1、待审核，2审核通过，3审核未通过，4已上架，5已下架
      statusList = getDataDictionaryList("APP_STATUS");
      //获取APP类型编码 1、手机，2平板 所属平台
      flatFormList = getDataDictionaryList("APP_FLATFORM");
      //获取一级下拉列表的所有选项，返回为分类id、分类编码、分类名称(id,categoryCode,categoryName)
      categoryLevel1 = appCategoryService.getAppCategoryListByParentId((Integer)null);
    } catch (Exception e) {
      e.printStackTrace();
    }
//  将app信息放入model域中，方便前端使用jstl标签接收数据
    model.addAttribute("appInfoList", appInfoList);//所有符合条件的app信息列表
    model.addAttribute("statusList", statusList);//有满足查询条件的app的状态列表
    model.addAttribute("flatFormList", flatFormList);//所有满足查询条件的APP的所属平台
    model.addAttribute("categoryLevel1List", categoryLevel1);//所有下拉列表一级选项
    model.addAttribute("pages", pages);//分页
    model.addAttribute("queryStatus", queryStatus);//查询状态编号
    model.addAttribute("querySoftwareName", querySoftwareName);//所要查询的软件名
    model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);//一级分类id
    model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);//二级分类id
    model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);//三级分类id
    model.addAttribute("queryFlatformId", queryFlatformId);//所属平台的id
    //如果二级分类不为null，根据一级分类查询所有二级分类
    if (queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")) {
      categoryLevel2 = getCategoryList(queryCategoryLevel1.toString());
      model.addAttribute("categoryLevel2List", categoryLevel2);//下拉列表所有二级分类选项（根据一级分类的查出来的）
    }
    //如果三级分类不为null，根据二级分类查询所有三级分类
    if (queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")) {
      categoryLevel3 = getCategoryList(queryCategoryLevel2.toString());//下拉列表所有三级分类选项（根据二级分类查出来的）
      model.addAttribute("categoryLevel3List", categoryLevel3);
    }
    //返回到页面展示
    return "developer/appinfolist";
  }

  //根据类型编码查询类型列表
  public List<DataDictionary> getDataDictionaryList(String typeCode) {
    List dataDictionaryList = null;

    try {
      dataDictionaryList = dataDictionaryService.getDataDictionaryList(typeCode);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return dataDictionaryList;
  }

  //根据类型编码查询类型列表
  @RequestMapping(
   value = {"/datadictionarylist.json"},
   method = {RequestMethod.GET}
  )
  @ResponseBody
  public List<DataDictionary> getDataDicList(@RequestParam String tcode) {
    return getDataDictionaryList(tcode);
  }

  //根据父类型编号查询该分类的所有列表
  @RequestMapping(
   value = {"/categorylevellist.json"},
   method = {RequestMethod.GET}
  )
  @ResponseBody
  public List<AppCategory> getAppCategoryList(@RequestParam String pid) {
    if (pid.equals("")) {
      pid = null;
    }

    return getCategoryList(pid);
  }
  //根据父类型编号查询该分类的所有列表
  public List<AppCategory> getCategoryList(String pid) {
    List categoryLevelList = null;

    try {
      categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid == null ? null : Integer.parseInt(pid));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return categoryLevelList;
  }
  //添加app信息
  @RequestMapping(
   value = {"/appinfoadd"},
   method = {RequestMethod.GET}
  )
  public String add(@ModelAttribute("appInfo") AppInfo appInfo) {
    //返回添加appinfoadd页面
    return "developer/appinfoadd";
  }

  //保存app信息
  @RequestMapping(
   value = {"/appinfoaddsave"},
   method = {RequestMethod.POST}
  )
  //根据前端传入的参数，保存app信息
  public String addSave(AppInfo appInfo, HttpSession session, HttpServletRequest request, @RequestParam(value = "a_logoPicPath",required = false) MultipartFile attach) {
   //定义图标的URL图片路径
    String logoPicPath = "";
    //定义图标的存储路径
    String logoLocPath = "";
    //上传图片文件 File.separator   windos: \ linux /
     if (!attach.isEmpty()) {
      String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
      String oldFileName = attach.getOriginalFilename();
      String prefix = FilenameUtils.getExtension(oldFileName);
      int filesize = 500000;
      //图片文件超过最大文件限制返回错误信息
      if (attach.getSize() > (long)filesize) {
        request.setAttribute("fileUploadError", " * 上传文件过大！");
        return "developer/appinfoadd";
      }
      //图片文上传格式只允许为jpg和png
      if (!prefix.equalsIgnoreCase("jpg") && !prefix.equalsIgnoreCase("png") && !prefix.equalsIgnoreCase("jepg") && !prefix.equalsIgnoreCase("pneg")) {
        request.setAttribute("fileUploadError", " * 上传文件格式不正确！");
        return "developer/appinfoadd";
      }
      //拼接图标文件命名
      String fileName = appInfo.getAPKName() + ".jpg";
      File targetFile = new File(path, fileName);
      //创建图标文件
      if (!targetFile.exists()) {
        targetFile.mkdirs();
      }

      try {
        //写入图标文件
        attach.transferTo(targetFile);
      } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("fileUploadError", " * 上传失败！");
        return "developer/appinfoadd";
      }
      //获取文件的url地址和服务器存储地址
      logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
      logoLocPath = path + File.separator + fileName;
    }
    //保存appinfo的所有信息
    appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());//开发者id
    appInfo.setCreationDate(new Date());//上传日期
    appInfo.setLogoPicPath(logoPicPath);//上传图片的url
    appInfo.setLogoLocPath(logoLocPath);//上传图片的存储位置
    appInfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());//开发者id
    appInfo.setStatus(1);//待审核

    try {
      //添加进入数据库成功的话直接返回首页显示
      if (appInfoService.add(appInfo)) {
        return "redirect:/dev/flatform/app/list";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    //添加失败，返回添加页面继续添加
    return "developer/appinfoadd";
  }

  //app版本添加
  @RequestMapping(
   value = {"/appversionadd"},
   method = {RequestMethod.GET}
  )
  public String addVersion(@RequestParam("id") String appId, @RequestParam(value = "error",required = false) String fileUploadError, AppVersion appVersion, Model model) {
    if (fileUploadError != null && fileUploadError.equals("error1")) {
      fileUploadError = " * APP信息填写错误@_@ ";
    } else if (fileUploadError != null && fileUploadError.equals("error2")) {
      fileUploadError = " 文件上传失败+_+ ";
    } else if (fileUploadError != null && fileUploadError.equals("error3")) {
      fileUploadError = " 上传文件格式错误>_<";
    }
    //设置appid
    appVersion.setAppId(Integer.parseInt(appId));
    List appVersionList = null;

    try {
      //获取版本列表
      appVersionList = appVersionService.getAppVersionList(Integer.parseInt(appId));
      //设置app版本的app名称
      appVersion.setAppName(appInfoService.getAppInfo(Integer.parseInt(appId), (String)null).getSoftwareName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    //版本版本列表、当前版本信息、文件上传是否错误 放入域对象
    model.addAttribute("appVersionList", appVersionList);
    model.addAttribute(appVersion);
    model.addAttribute("fileUploadError", fileUploadError);
    return "developer/appversionadd";
  }
  //保存添加的app版本信息
  @RequestMapping(
   value = {"/addversionsave"},
   method = {RequestMethod.POST}
  )
  public String addVersionSave(AppVersion appVersion, HttpSession session, HttpServletRequest request, @RequestParam(value = "a_downloadLink",required = false) MultipartFile attach) {
    //下载链接
    String downloadLink = null;
    //apk文件的服务器存储路径
    String apkLocPath = null;
    //上传的apk文件名称
    String apkFileName = null;
    //如果上传的文件不为null
    if (!attach.isEmpty()) {
      //构建保存的路径，statics/uploadfiles，如果是linux下的话File.separator是/，windows下File.separator是\
      String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
      //获取上传时的文件名
      String oldFileName = attach.getOriginalFilename();
      //获取文件的后缀
      String prefix = FilenameUtils.getExtension(oldFileName);
      //如果后缀不是.apk的话直接返回错误信息 error3（上传格式错误）
      if (!prefix.equalsIgnoreCase("apk")) {
        return "redirect:/dev/flatform/app/appversionadd?id=" + appVersion.getAppId() + "&error=error3";
      }

      String apkName = null;

      try {
        //根据appid查询app名称
        apkName = appInfoService.getAppInfo(appVersion.getAppId(), (String)null).getAPKName();
      } catch (Exception e) {
        e.printStackTrace();
      }
      //如果app的名称为null，说明app信息填写有问题，返回error1（app信息填写错误）
      if (apkName == null || "".equals(apkName)) {
        return "redirect:/dev/flatform/app/appversionadd?id=" + appVersion.getAppId() + "&error=error1";
      }
      //拼装apk文件名，xxx-1.1.0.apk
      apkFileName = apkName + "-" + appVersion.getVersionNo() + ".apk";
      //创建apk文件
      File targetFile = new File(path, apkFileName);
      if (!targetFile.exists()) {
        targetFile.mkdirs();
      }

      try {
        //写入apk文件信息
        attach.transferTo(targetFile);
      } catch (Exception e) {
        e.printStackTrace();
        //上传失败，返回error2（apk文件上传错误）
        return "redirect:/dev/flatform/app/appversionadd?id=" + appVersion.getAppId() + "&error=error2";
      }
      //构建下载路径
      downloadLink = request.getContextPath() + "/statics/uploadfiles/" + apkFileName;
      //构建存储路径
      apkLocPath = path + File.separator + apkFileName;
    }
    //加入app版本信息中
    appVersion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());//开发者id
    appVersion.setCreationDate(new Date());//文件上传日期
    appVersion.setDownloadLink(downloadLink);//apk下载路径
    appVersion.setApkLocPath(apkLocPath);//apk服务器存储路径
    appVersion.setApkFileName(apkFileName);//apk名字

    try {
      //添加成功返回首页（appinfolist.jsp）
      if (appVersionService.appsysadd(appVersion)) {
        return "redirect:/dev/flatform/app/list";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    //否者返回继续添加apk版本信息
    return "redirect:/dev/flatform/app/appversionadd?id=" + appVersion.getAppId();
  }

  @RequestMapping(
   value = {"/{appid}/sale"},
   method = {RequestMethod.PUT}
  )
  @ResponseBody
  public Object sale(@PathVariable String appid, HttpSession session) {
    HashMap<String, Object> resultMap = new HashMap();
    Integer appIdInteger = 0;

    try {
      appIdInteger = Integer.parseInt(appid);
    } catch (Exception e) {
      appIdInteger = 0;
    }

    resultMap.put("errorCode", "0");
    resultMap.put("appId", appid);
    if (appIdInteger > 0) {
      try {
        DevUser devUser = (DevUser)session.getAttribute("devUserSession");
        AppInfo appInfo = new AppInfo();
        appInfo.setId(appIdInteger);
        appInfo.setModifyBy(devUser.getId());
        if (appInfoService.appsysUpdateSaleStatusByAppId(appInfo)) {
          resultMap.put("resultMsg", "success");
        } else {
          resultMap.put("resultMsg", "success");
        }
      } catch (Exception e) {
        resultMap.put("errorCode", "exception000001");
      }
    } else {
      resultMap.put("errorCode", "param000001");
    }

    return resultMap;
  }

  @RequestMapping(
   value = {"/apkexist.json"},
   method = {RequestMethod.GET}
  )
  @ResponseBody
  public Object apkNameIsExit(@RequestParam String APKName) {
    HashMap<String, String> resultMap = new HashMap();
    if (StringUtils.isNullOrEmpty(APKName)) {
      resultMap.put("APKName", "empty");
    } else {
      AppInfo appInfo = null;

      try {
        appInfo = appInfoService.getAppInfo((Integer)null, APKName);
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (appInfo != null) {
        resultMap.put("APKName", "exist");
      } else {
        resultMap.put("APKName", "noexist");
      }
    }

    return JSONArray.toJSONString(resultMap);
  }

  //根据appid查看
  @RequestMapping(
   value = {"/appview/{id}"},
   method = {RequestMethod.GET}
  )
  public String view(@PathVariable String id, Model model) {
    //当前app信息
    AppInfo appInfo = null;
    //当前app版本信息列表
    List appVersionList = null;

    try {
      //根据appid查询app信息
      appInfo = appInfoService.getAppInfo(Integer.parseInt(id), (String)null);
      //根据appid查询该app所有的版本
      appVersionList = appVersionService.getAppVersionList(Integer.parseInt(id));
    } catch (Exception e) {
      e.printStackTrace();
    }

    model.addAttribute("appVersionList", appVersionList);
    model.addAttribute(appInfo);
    //返回appinfoview.jsp
    return "developer/appinfoview";
  }

  //修改app信息
  @RequestMapping(
   value = {"/appinfomodify"},
   method = {RequestMethod.GET}
  )
  public String modifyAppInfo(@RequestParam("id") String id, @RequestParam(value = "error",required = false) String fileUploadError, Model model) {
    //保存app信息
    AppInfo appInfo = null;
    if (fileUploadError != null && fileUploadError.equals("error1")) {
      fileUploadError = "  APP信息填写错误@_@ ";
    } else if (fileUploadError != null && fileUploadError.equals("error2")) {
      fileUploadError = " 文件上传失败+_+ ";
    } else if (fileUploadError != null && fileUploadError.equals("error3")) {
      fileUploadError = " 上传文件格式错误>_<";
    } else if (fileUploadError != null && fileUploadError.equals("error4")) {
      fileUploadError = "  上传文件过大Q_Q ";
    }

    try {
      //根据appid查询app信息
      appInfo = appInfoService.getAppInfo(Integer.parseInt(id), (String)null);
    } catch (Exception e) {
      e.printStackTrace();
    }

    model.addAttribute(appInfo);
    //记录文件上传情况
    model.addAttribute("fileUploadError", fileUploadError);
    //返回appinfomodify.jsp
    return "developer/appinfomodify";
  }
  //修改app版本信息
  @RequestMapping(
   value = {"/appversionmodify"},
   method = {RequestMethod.GET}
  )
  public String modifyAppVersion(@RequestParam("vid") String versionId, @RequestParam("aid") String appId, @RequestParam(value = "error",required = false) String fileUploadError, Model model) {
    AppVersion appVersion = null;
    List<AppVersion> appVersionList = null;
    if (fileUploadError != null && fileUploadError.equals("error1")) {
      fileUploadError = " * APP信息填写错误@_@ ";
    } else if (fileUploadError != null && fileUploadError.equals("error2")) {
      fileUploadError = " 文件上传失败+_+ ";
    } else if (fileUploadError != null && fileUploadError.equals("error3")) {
      fileUploadError = " 上传文件格式错误>_<";
    }

    try {
      //根据app的版本id查询该app信息
      appVersion = appVersionService.getAppVersionById(Integer.parseInt(versionId));
      //根据app的信息id查询该app所有的版本信息
      appVersionList = appVersionService.getAppVersionList(Integer.parseInt(appId));
    } catch (Exception e) {
      e.printStackTrace();
    }

    model.addAttribute(appVersion);
    model.addAttribute("appVersionList", appVersionList);
    model.addAttribute("fileUploadError", fileUploadError);
    //返回appversionsmodify.jsp页面
    return "developer/appversionmodify";
  }

  //保存app版本修改信息
  @RequestMapping(
   value = {"/appversionmodifysave"},
   method = {RequestMethod.POST}
  )
  public String modifyAppVersionSave(AppVersion appVersion, HttpSession session, HttpServletRequest request, @RequestParam(value = "attach",required = false) MultipartFile attach) {
   //下载链接
    String downloadLink = null;
    //apk文件的服务器存储路径
    String apkLocPath = null;
    //apk文件名
    String apkFileName = null;
    //如果传入的文件不是null，就构建文件的路径
    if (!attach.isEmpty()) {
      //构建保存的路径，statics/uploadfiles，如果是linux下的话File.separator是/，windows下File.separator是\
      String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
      //获取上传时的文件名
      String oldFileName = attach.getOriginalFilename();
      //获取文件后缀
      String prefix = FilenameUtils.getExtension(oldFileName);
      //不是apk类型的提示error3错误（文件类型上传错误）
      if (!prefix.equalsIgnoreCase("apk")) {
        return "redirect:/dev/flatform/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppId() + "&error=error3";
      }

      String apkName = null;

      try {

        //根据appid获取apk名字
        apkName = appInfoService.getAppInfo(appVersion.getAppId(), (String) null).getAPKName();
      } catch (Exception e) {
        e.printStackTrace();
      }
      //apk名字不为null，代表apk信息完整，否者出错为error1（app信息错误）
      if (apkName == null || "".equals(apkName)) {
        return "redirect:/dev/flatform/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppId() + "&error=error1";
      }
      //apk文件名为xxx-1.1.0.apk
      apkFileName = apkName + "-" + appVersion.getVersionNo() + ".apk";
      //创建文件
      File targetFile = new File(path, apkFileName);
      if (!targetFile.exists()) {
        targetFile.mkdirs();
      }

      try {
        //写入数据
        attach.transferTo(targetFile);
      } catch (Exception e) {
        e.printStackTrace();
        //如果数据写入失败，返回error2（文件上传错误）
        return "redirect:/dev/flatform/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppId() + "&error=error2";
      }
      //构建下载路径
      downloadLink = request.getContextPath() + "/statics/uploadfiles/" + apkFileName;
      //构建服务器存储路径
      apkLocPath = path + File.separator + apkFileName;
    }
    //保存修改后的app版本信息
    appVersion.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
    appVersion.setModifyDate(new Date());
    appVersion.setDownloadLink(downloadLink);
    appVersion.setApkLocPath(apkLocPath);
    appVersion.setApkFileName(apkFileName);

    try {
      if (appVersionService.modify(appVersion)) {
        //修改成功返appinfolist.jsp
        return "redirect:/dev/flatform/app/list";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    //继续修改完善信息
    return "developer/appversionmodify";
  }

  @RequestMapping(
   value = {"/delfile"},
   method = {RequestMethod.GET}
  )
  @ResponseBody
  public Object delFile(@RequestParam(value = "flag",required = false) String flag, @RequestParam(value = "id",required = false) String id) {
    HashMap<String, String> resultMap = new HashMap();
    String fileLocPath = null;
    if (flag != null && !flag.equals("") && id != null && !id.equals("")) {
      File file;
      if (flag.equals("logo")) {
        try {
          fileLocPath = appInfoService.getAppInfo(Integer.parseInt(id), (String)null).getLogoLocPath();
          file = new File(fileLocPath);
          if (file.exists() && file.delete() && appInfoService.deleteAppLogo(Integer.parseInt(id))) {
            resultMap.put("result", "success");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else if (flag.equals("apk")) {
        try {
          fileLocPath = appVersionService.getAppVersionById(Integer.parseInt(id)).getApkLocPath();
          file = new File(fileLocPath);
          if (file.exists() && file.delete() && appVersionService.deleteApkFile(Integer.parseInt(id))) {
            resultMap.put("result", "success");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else {
      resultMap.put("result", "failed");
    }

    return JSONArray.toJSONString(resultMap);
  }

  //app信息修改保存
  @RequestMapping(
   value = {"/appinfomodifysave"},
   method = {RequestMethod.POST}
  )
  public String modifySave(AppInfo appInfo, HttpSession session, HttpServletRequest request, @RequestParam(value = "attach",required = false) MultipartFile attach) {
    //图片url地址
    String logoPicPath = null;
    //图片保存再服务器的路径
    String logoLocPath = null;
    //apk名称
    String APKName = appInfo.getAPKName();
    //如果前端更上传图片，则也需要更新
    if (!attach.isEmpty()) {
      //构建存储路径
      String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
      //文件上传时的文件名
      String oldFileName = attach.getOriginalFilename();
      //后缀
      String prefix = FilenameUtils.getExtension(oldFileName);
      //最大上传文件大小
      int filesize = 500000;
      //超过最大限度返回错误信息
      if (attach.getSize() > (long)filesize) {
        return "redirect:/dev/flatform/app/appinfomodify?id=" + appInfo.getId() + "&error=error4";
      }
      //后缀不为png，上传类型错误
      if (!prefix.equalsIgnoreCase("jpg") && !prefix.equalsIgnoreCase("png") && !prefix.equalsIgnoreCase("jepg") && !prefix.equalsIgnoreCase("pneg")) {
        return "redirect:/dev/flatform/app/appinfomodify?id=" + appInfo.getId() + "&error=error3";
      }
      //拼接图片名
      String fileName = APKName + ".jpg";
      //创建图片
      File targetFile = new File(path, fileName);
      if (!targetFile.exists()) {
        targetFile.mkdirs();
      }

      try {
        //写入图片信息
        attach.transferTo(targetFile);
      } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/dev/flatform/app/appinfomodify?id=" + appInfo.getId() + "&error=error2";
      }
      //拼接url路径
      logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
      //拼接存储路径
      logoLocPath = path + File.separator + fileName;
    }
    //保存修改后的信息
    appInfo.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
    appInfo.setModifyDate(new Date());
    appInfo.setLogoLocPath(logoLocPath);
    appInfo.setLogoPicPath(logoPicPath);

    try {
      //更新修改信息
      if (appInfoService.modify(appInfo)) {
        return "redirect:/dev/flatform/app/list";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "developer/appinfomodify";
  }
  //根据appid删除app信息
  @RequestMapping({"/delapp.json"})
  @ResponseBody
  public Object delApp(@RequestParam String id) {
    //保存删除情况的结果
    HashMap<String, String> resultMap = new HashMap();
    //如果appid不存在返回notexist
    if (StringUtils.isNullOrEmpty(id)) {
      resultMap.put("delResult", "notexist");
    } else {
      try {
        //根据appid删除，成功返回true，失败返回false
        if (appInfoService.appsysdeleteAppById(Integer.parseInt(id))) {
          resultMap.put("delResult", "true");
        } else {
          resultMap.put("delResult", "false");
        }
      } catch (NumberFormatException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    //返回json数据给前端的ajax验证
    return JSONArray.toJSONString(resultMap);
  }
}
