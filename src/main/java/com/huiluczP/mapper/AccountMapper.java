package com.huiluczP.mapper;

import com.huiluczP.Annotation.Permission;
import com.huiluczP.bean.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Permission
    @Select("select * from account")
    public List<AccountInfo> getAccountInfoList();
}
