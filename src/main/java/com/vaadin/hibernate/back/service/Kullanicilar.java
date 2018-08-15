package com.vaadin.hibernate.back.service;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/**
 * @author Emre Kirman
 * @since 1.0
 */
@Entity
@Table(name = "kullanicilar")
public class Kullanicilar  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int KullaniciID;

    @Column(name = "KullaniciEmail")
    private String kullaniciEmail;

    @Column(name = "KullaniciSifre")
    private String kullaniciSifre;

    @Column(name = "KullaniciAdSoyad")
    private String kullaniciAdSoyad;

    @Column(name = "KullaniciIzin")
    private String kullaniciIzin;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "KullaniciID",fetch = FetchType.EAGER)
    private List<Blog> blogList;


    public int getKullaniciID() {
        return KullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        KullaniciID = kullaniciID;
    }

    public String getKullaniciEmail() {
        return kullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }

    public String getKullaniciSifre() {
        return kullaniciSifre;
    }

    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }

    public String getKullaniciAdSoyad() {
        return kullaniciAdSoyad;
    }

    public void setKullaniciAdSoyad(String kullaniciAdSoyad) {
        this.kullaniciAdSoyad = kullaniciAdSoyad;
    }

    public String getKullaniciIzin() {
        return kullaniciIzin;
    }

    public void setKullaniciIzin(String kullaniciIzin) {
        this.kullaniciIzin = kullaniciIzin;
    }
}
