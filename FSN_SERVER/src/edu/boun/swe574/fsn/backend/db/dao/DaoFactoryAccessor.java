package edu.boun.swe574.fsn.backend.db.dao;

import java.util.Hashtable;

public class DaoFactoryAccessor {

    private static ThreadLocal<Hashtable<DaoFactoryIdentifier, DaoFactory>> threadLocal = new ThreadLocal<Hashtable<DaoFactoryIdentifier, DaoFactory>>();

    public static void setDaoFactory(DaoFactoryIdentifier id, DaoFactory daoFactory) {
        Hashtable<DaoFactoryIdentifier, DaoFactory> map = threadLocal.get();
        if (map == null) {
            map = new Hashtable<DaoFactoryIdentifier, DaoFactory>(2);
            threadLocal.set(map);
        }
        map.put(id, daoFactory);
    }

    public static DaoFactory getDaoFactory(DaoFactoryIdentifier id) {
        Hashtable<DaoFactoryIdentifier, DaoFactory> map = threadLocal.get();
        if (map == null) {
            return null;
        }
        return map.get(id);
    }

    public static void removeDaoFactory(DaoFactoryIdentifier id) {
        Hashtable<DaoFactoryIdentifier, DaoFactory> map = threadLocal.get();
        if (map != null) {
            map.remove(id);
        }
    }

}