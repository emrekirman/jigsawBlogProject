package com.vaadin.hibernate.back.service;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
/**
 * @author Emre Kirman
 * @since 1.0
 */
@Entity
@Table(name = "blog")
public class Blog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BlogId;

    @Column(name = "BlogBaslik")
    private String BlogBaslik;

    @Column(name = "Aciklama")
    private String Aciklama;

    @Column(name = "tarih")
    private Date Tarih;

    @JoinColumn(name = "KullaniciID",referencedColumnName = "KullaniciID")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Kullanicilar KullaniciID;

    public int getBlogId() {
        return BlogId;
    }

    public void setBlogId(int blogId) {
        BlogId = blogId;
    }

    public String getBlogBaslik() {
        return BlogBaslik;
    }

    public void setBlogBaslik(String blogBaslik) {
        BlogBaslik = blogBaslik;
    }

    public String getAciklama() {
        return Aciklama;
    }

    public void setAciklama(String aciklama) {
        Aciklama = aciklama;
    }

    public Kullanicilar getKullaniciID() {
        return KullaniciID;
    }

    public void setKullaniciID(Kullanicilar kullaniciID) {
        KullaniciID = kullaniciID;
    }

    public Date getTarih() {
        return Tarih;
    }

    public void setTarih(Date tarih) {
        Tarih = tarih;
    }
}
