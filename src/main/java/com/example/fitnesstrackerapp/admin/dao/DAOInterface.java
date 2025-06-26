package com.example.fitnesstrackerapp.admin.dao;

import com.example.fitnesstrackerapp.admin.dto.UserDto;
import com.example.fitnesstrackerapp.admin.model.User;
import com.example.fitnesstrackerapp.admin.model.UserActivity;

import java.util.List;

public interface DAOInterface<T> {


    List<T> addObject(Object object, String objectType);

    List updateObjectListToJsonFile(UserDto userDto, String objectType);

    List<T> getAll();

    List getAllUserActivity();

    String add(List<T> object);
    T getObj(Long id);

    Object getObjVital(Long id);

    UserActivity getObjActivity(Long id);

    User getObjbyEmail(String email);


    List saveObjectListToJsonFile(List objects, String objectType);

    List readObjectListFromJsonFile(String objectType);
}
