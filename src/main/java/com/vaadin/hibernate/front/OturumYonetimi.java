package com.vaadin.hibernate.front;

import com.vaadin.hibernate.back.service.Kullanicilar;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class OturumYonetimi extends Kullanicilar{
    public static int KullaniciID;
    public static String KullaniciEmail;
    public static String KullaniciAdSoyad;
    public static String KullaniciSifre;
    public static String kullaniciIzin;

    public static void oturumSonlandir(){
        KullaniciID=0;
        KullaniciEmail=null;
        KullaniciSifre=null;
        KullaniciAdSoyad=null;
        kullaniciIzin=null;
    }

    public static void oturumBaslat(List<Kullanicilar> list){
        KullaniciID=list.get(0).getKullaniciID();
        KullaniciAdSoyad=list.get(0).getKullaniciAdSoyad();
        KullaniciEmail=list.get(0).getKullaniciEmail();
        KullaniciSifre=list.get(0).getKullaniciSifre();
        kullaniciIzin=list.get(0).getKullaniciIzin();

    }

    public  int getKullaniciID() {
        return KullaniciID;
    }

    public  void setKullaniciID(int kullaniciID) {
        KullaniciID = kullaniciID;
    }
}
