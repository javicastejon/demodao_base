package com.daoexample.factory;

import com.daoexample.daos.PlayerDao;
import com.daoexample.iface.IDao;

public class DaoFactory {

    public enum DaoType{
        PLAYER
    }

    public static <T, V> IDao<T, V> getDao(DaoType daoType) {
        return switch (daoType) {
            case PLAYER -> (IDao<T, V>) new PlayerDao();
        };
    }
}


