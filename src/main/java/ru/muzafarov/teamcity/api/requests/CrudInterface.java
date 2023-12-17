package ru.muzafarov.teamcity.api.requests;

public interface CrudInterface {

    Object create(Object obj);

    Object get(String id);

    Object update(Object obj);

    Object deleteById(String id);

    Object deleteByUserName(String userName);
}
