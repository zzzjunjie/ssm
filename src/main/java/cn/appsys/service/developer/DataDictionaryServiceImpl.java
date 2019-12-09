//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.appsys.service.developer;

import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
  @Resource
  private DataDictionaryMapper mapper;

  public DataDictionaryServiceImpl() {
  }

  public List<DataDictionary> getDataDictionaryList(String typeCode) throws Exception {
    return this.mapper.getDataDictionaryList(typeCode);
  }
}
