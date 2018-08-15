package com.vaadin.hibernate.back.service;

import com.vaadin.hibernate.back.HibernateSetup;
import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class KullanicilarDaoImpl implements KullanicilarDao {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private SessionFactory sessionFactory;

    public KullanicilarDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public KullanicilarDaoImpl() {
        sessionFactory = HibernateSetup.getSessionFactory();
    }

    @Override
    public void create(Kullanicilar kullanicilar) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(kullanicilar);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            logger.info("Create Error" + ex.getLocalizedMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Kullanicilar kullanicilar) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            session.delete(kullanicilar);
            session.getTransaction().commit();

        } catch (HibernateException ex) {
            logger.info("Delete Error: " + ex.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<Kullanicilar> getAll() {
        List<Kullanicilar> kullanicilarList = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            kullanicilarList = (List<Kullanicilar>) session.createCriteria(Kullanicilar.class).list();

        } catch (HibernateException ex) {
            logger.info("Get All Error: " + ex.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return kullanicilarList;
    }

    @Override
    public Kullanicilar getById(int id) {
        try(Session session=sessionFactory.openSession()){
            Kullanicilar kullanicilar=session.get(Kullanicilar.class,id);
            return kullanicilar;
        }catch (HibernateException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Kullanicilar kullanicilar) {
        Transaction tx=null;
        Session session=null;
        try {
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.update(kullanicilar);
            session.getTransaction().commit();

        }catch (HibernateException ex){
            ex.printStackTrace();
            if(tx!=null){tx.rollback();}
        }finally {
            if (session!=null){session.close();}
        }

    }

    @Override
    public List<Kullanicilar> getByEmailPassword(Kullanicilar entity) {
        try(Session session=sessionFactory.openSession()){
            List<Kullanicilar> list=new ArrayList<>();
            String hql="SELECT k FROM Kullanicilar k WHERE k.kullaniciEmail=:entityEmail AND k.kullaniciSifre=:entitySifre";
            Query query=session.createQuery(hql);
            query.setParameter("entityEmail",entity.getKullaniciEmail());
            query.setParameter("entitySifre",entity.getKullaniciSifre());
            list=query.list();
            session.close();
            return list;

        }catch (HibernateException ex){
            ex.printStackTrace();
            return null;
        }
    }
    /*    @Override
    public boolean getByEmailPassword(Kullanicilar entity) {
        try(Session session=sessionFactory.openSession()){
            List<Kullanicilar> list=new ArrayList<>();
            String hql="SELECT k  FROM Kullanicilar k WHERE k.kullaniciEmail=:entityEmail AND k.kullaniciSifre=:entitySifre AND k.kullaniciIzin='S'";
            Query query=session.createQuery(hql);
            query.setParameter("entityEmail",entity.getKullaniciEmail());
            query.setParameter("entitySifre",entity.getKullaniciSifre());
            list=query.list();
            if (!list.isEmpty()){
                entity.setKullaniciID(list.get(0).getKullaniciID());
                entity.setKullaniciAdSoyad(list.get(0).getKullaniciAdSoyad());
                entity.setKullaniciSifre(list.get(0).getKullaniciSifre());
                entity.setKullaniciEmail(list.get(0).getKullaniciEmail());
                entity.setKullaniciIzin(list.get(0).getKullaniciIzin());
                session.close();
                return true;
            }
            else{
                session.close();
                return false;
            }

        }catch (HibernateException ex){
            ex.printStackTrace();
            return false;
        }
    }*/

    @Override
    public List<Kullanicilar> selectPage(int start, int count) {
        return null;
    }

    @Override
    public Long countAll() {
        return null;
    }

    @Override
    public Integer deleteAll() {
        return null;
    }
}
