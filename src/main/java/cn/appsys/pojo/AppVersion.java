//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class AppVersion {
  private Integer id;
  private Integer appId;
  private String versionNo;
  private String versionInfo;
  private Integer publishStatus;
  private String downloadLink;
  private BigDecimal versionSize;
  private Integer createdBy;
  private Date creationDate;
  private Integer modifyBy;
  private Date modifyDate;
  private String apkLocPath;
  private String appName;
  private String publishStatusName;
  private String apkFileName;

  public AppVersion() {
  }

  public String getApkFileName() {
    return this.apkFileName;
  }

  public void setApkFileName(String apkFileName) {
    this.apkFileName = apkFileName;
  }

  public String getPublishStatusName() {
    return this.publishStatusName;
  }

  public void setPublishStatusName(String publishStatusName) {
    this.publishStatusName = publishStatusName;
  }

  public String getAppName() {
    return this.appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getApkLocPath() {
    return this.apkLocPath;
  }

  public void setApkLocPath(String apkLocPath) {
    this.apkLocPath = apkLocPath;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAppId() {
    return this.appId;
  }

  public void setAppId(Integer appId) {
    this.appId = appId;
  }

  public String getVersionNo() {
    return this.versionNo;
  }

  public void setVersionNo(String versionNo) {
    this.versionNo = versionNo;
  }

  public String getVersionInfo() {
    return this.versionInfo;
  }

  public void setVersionInfo(String versionInfo) {
    this.versionInfo = versionInfo;
  }

  public Integer getPublishStatus() {
    return this.publishStatus;
  }

  public void setPublishStatus(Integer publishStatus) {
    this.publishStatus = publishStatus;
  }

  public String getDownloadLink() {
    return this.downloadLink;
  }

  public void setDownloadLink(String downloadLink) {
    this.downloadLink = downloadLink;
  }

  public BigDecimal getVersionSize() {
    return this.versionSize;
  }

  public void setVersionSize(BigDecimal versionSize) {
    this.versionSize = versionSize;
  }

  public Integer getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Integer getModifyBy() {
    return this.modifyBy;
  }

  public void setModifyBy(Integer modifyBy) {
    this.modifyBy = modifyBy;
  }

  public Date getModifyDate() {
    return this.modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }
}
