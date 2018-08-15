package com.vaadin.hibernate.front.kayitOl;

import com.vaadin.ui.RadioButtonGroup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class KayitOlPanelRadioGrubu {
    private int id;
    private String ad;
    private  String veriAdi;
    private RadioButtonGroup<KayitOlPanelRadioGrubu> radioButtonGroup;


    public KayitOlPanelRadioGrubu() {
        radioButtonGroup=new RadioButtonGroup<>();
    }

    public RadioButtonGroup<KayitOlPanelRadioGrubu> kullaniciIzinRadioGrup(){
        List<KayitOlPanelRadioGrubu> kayitOlPanelRadioGrubuList=new ArrayList<>();

        kayitOlPanelRadioGrubuList.add(new KayitOlPanelRadioGrubu(1,"Blog Sahibi","S"));
        kayitOlPanelRadioGrubuList.add(new KayitOlPanelRadioGrubu(2,"Yönetici","Y"));
        kayitOlPanelRadioGrubuList.add(new KayitOlPanelRadioGrubu(3,"Standart Kullanıcı","K"));

        radioButtonGroup.setItemCaptionGenerator(KayitOlPanelRadioGrubu::getAd);
        radioButtonGroup.setItems(kayitOlPanelRadioGrubuList);

        return radioButtonGroup;
    }


    public static Timestamp yeniTimeStampOlustur(){
        return new Timestamp((new java.util.Date().getTime()));
    }


    public KayitOlPanelRadioGrubu(int id, String ad, String veriAdi) {
        this.id = id;
        this.ad = ad;
        this.veriAdi = veriAdi;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getVeriAdi() {
        return veriAdi;
    }

    public void setVeriAdi(String veriAdi) {
        this.veriAdi = veriAdi;
    }
}
