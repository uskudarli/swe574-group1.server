package edu.boun.swe574.fsn.backend.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

public class DaoFactory {
	
	private static final Logger logger = Logger.getLogger(DaoFactory.class);

    private static final String                    	PERSISTENCE_UNIT_NAME        = "FSN_PU";
    private static final String                    	PERSISTENCE_UNIT_PROD_NAME        = "FSN_PROD";
    private static final boolean					IS_PROD 					= false;
    
    private static EntityManagerFactory            emf                          = null;
    
    private EntityManager                          em;

    private static boolean                         isEMFInited                  = false;

    private DaoFactoryIdentifier                   id;

    public static Class<BaseDao>                   BASE_DAO                     = BaseDao.class;
  
    private BaseDao                                baseDao;
   
    private DaoFactory(DaoFactoryIdentifier id) {
        this.id = id;
    }

    public static DaoFactory getInstance() {

        DaoFactory existingFactory = DaoFactoryAccessor.getDaoFactory(DaoFactoryIdentifier.FSN);
        if (existingFactory != null) {
            return existingFactory;
        }
        initializeEMF();

        DaoFactory daoFactory = new DaoFactory(DaoFactoryIdentifier.FSN);
        daoFactory.em = emf.createEntityManager();


        DaoFactoryAccessor.setDaoFactory(DaoFactoryIdentifier.FSN, daoFactory);
        
        return daoFactory;
    }


    public static DaoFactory getInstance(DaoFactoryIdentifier id) {
            return getInstance();
    }

    public BaseDao instantiateDao(Class<?> clazz) {
        BaseDao dao;
        try {
            dao = (BaseDao) clazz.newInstance();
            dao.setEntityManager(em);
            return dao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized static void initializeEMF() {
        if (isEMFInited) {
            return;
        }

        logger.warn("{***} Initializing Entity Manager Factory...");

        
        emf = Persistence.createEntityManagerFactory(IS_PROD ? PERSISTENCE_UNIT_PROD_NAME : PERSISTENCE_UNIT_NAME);

        isEMFInited = true;
    }



    public void closeContext() {
        if (em != null)
            em.close();

        DaoFactoryAccessor.removeDaoFactory(id);
    }

    public BaseDao getBaseDao() {
        if (baseDao == null) {
            baseDao = (BaseDao) instantiateDao(BASE_DAO);
        }
        return baseDao;
    }

   
}
