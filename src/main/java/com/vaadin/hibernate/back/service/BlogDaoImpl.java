package com.vaadin.hibernate.back.service;

import com.vaadin.hibernate.back.HibernateSetup;
import com.vaadin.hibernate.front.OturumYonetimi;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.query.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.sql.Timestamp;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class BlogDaoImpl implements BlogDao {
    private final Logger logger=Logger.getLogger(getClass().getName());
    private SessionFactory sessionFactory;

    public BlogDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public BlogDaoImpl() {
        sessionFactory=HibernateSetup.getSessionFactory();
    }

    public void blogEkle(Blog entity){
        Transaction transaction=null;
        Session session=null;
        try {
            session=sessionFactory.openSession();
            transaction=session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();

        }catch (HibernateException ex){
            ex.printStackTrace();
            if(transaction!=null){
                session.getTransaction().rollback();
            }
        }finally {
            if (session!=null){
                session.close();
            }
        }

    }

    public List<Blog> getByKullaniciID(){
        try(Session session=sessionFactory.openSession()) {
            List<Blog> blogList;
            String hql="SELECT b FROM Blog b WHERE b.KullaniciID.KullaniciID=:kullaniciID";
            Query query=session.createQuery(hql);
            query.setParameter("kullaniciID",OturumYonetimi.KullaniciID);
            blogList=query.list();
            session.close();
            return blogList;
        }catch (HibernateException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(Blog entity) {

    }

    @Override
    public void delete(Blog entity) {

    }

    @Override
    public List<Blog> getAll() {
        List<Blog> result=null;
        Session session=null;
        try {
            session=sessionFactory.openSession();
            result=(List<Blog>) session.createCriteria(Blog.class).list();

        }catch (HibernateException ex){
            logger.info("Get All Error: " + ex.getLocalizedMessage());
        }finally {
            if (session!=null)
                session.close();
        }
        return result;
    }

    @Override
    public Blog  getById(int id) {
        try(Session session=sessionFactory.openSession()) {
            Blog blog=session.get(Blog.class,id);
            return blog;
        }catch (HibernateException ex){
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    public void update(Blog entity) {
        Transaction tx=null;
        Session session=null;
        try {
            session=sessionFactory.openSession();
            tx=session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }catch (HibernateException ex){
            ex.printStackTrace();
            if (tx!=null){tx.rollback();}
        }finally {
            if (session!=null){session.close();}
        }

    }

    @Override
    public List<Blog> selectPage(int start, int count) {
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

    @Override
    public List<Kullanicilar> getByEmailPassword(Blog entity) {
        return null;
    }

    //_______________________________________________
    public Timestamp yeniTimeStampOlustur(){
        return new Timestamp((new java.util.Date().getTime()));
    }
    public Date dateOlustur(){
        return new Date(new java.util.Date().getTime());

    }
}
