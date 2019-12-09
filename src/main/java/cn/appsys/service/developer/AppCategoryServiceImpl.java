//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.developer;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {
  @Resource
  private AppCategoryMapper mapper;

  public AppCategoryServiceImpl() {
  }

  public List<AppCategory> getAppCategoryListByParentId(Integer parentId) throws Exception {
    return this.mapper.getAppCategoryListByParentId(parentId);
  }
}
