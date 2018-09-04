package com.example.demo.dao.mapper;

import com.example.demo.dao.model.User;

//@Mapper 这里可以使用@Mapper注解，但是每个mapper都加注解比较麻烦，所以统一配置@MapperScan在扫描路径在application类中
//@Component
public interface UserMapper {

    int insert(User record);

    int insertSelective(User record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByPrimaryKey(Long id);

    User selectSelective(User record);
}