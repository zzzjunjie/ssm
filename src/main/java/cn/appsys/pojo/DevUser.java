//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.pojo;

import java.util.Date;

public class DevUser {
  private Integer id;
  private String devCode;
  private String devName;
  private String devPassword;
  private String devEmail;
  private String devInfo;
  private Integer createdBy;
  private Date creationDate;
  private Integer modifyBy;
  private Date modifyDate;

  public DevUser() {
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(String devCode) {
    this.devCode = devCode;
  }

  public String getDevName() {
    return this.devName;
  }

  public void setDevName(String devName) {
    this.devName = devName;
  }

  public String getDevPassword() {
    return this.devPassword;
  }

  public void setDevPassword(String devPassword) {
    this.devPassword = devPassword;
  }

  public String getDevEmail() {
    return this.devEmail;
  }

  public void setDevEmail(String devEmail) {
    this.devEmail = devEmail;
  }

  public String getDevInfo() {
    return this.devInfo;
  }

  public void setDevInfo(String devInfo) {
    this.devInfo = devInfo;
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
