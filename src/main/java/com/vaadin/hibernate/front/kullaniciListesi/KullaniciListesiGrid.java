package com.vaadin.hibernate.front.kullaniciListesi;

import com.vaadin.hibernate.back.service.Kullanicilar;
import com.vaadin.hibernate.back.service.KullanicilarDaoImpl;
import com.vaadin.hibernate.front.anaPencere.AnaPencere;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;

import java.util.*;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class KullaniciListesiGrid {
    public Grid<Kullanicilar> kullaniciListesiGridTablo(){//KullanıcıListesi View
        Grid<Kullanicilar> grid=new Grid<>();
        KullanicilarDaoImpl kullanicilarDao=new KullanicilarDaoImpl();
        Set<Kullanicilar> kullanicilarSet=new LinkedHashSet<>();

        kullanicilarSet.addAll(kullanicilarDao.getAll());

        grid.setSizeFull();
        grid.setHeightMode(HeightMode.UNDEFINED);
        grid.setItems(kullanicilarSet);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        gridTiklanmaOlayi(grid);

        grid.addColumn(Kullanicilar::getKullaniciID).setCaption("No").setWidth(80);
        grid.addColumn(Kullanicilar::getKullaniciAdSoyad).setCaption("Ad Soyad");
        grid.addColumn(Kullanicilar::getKullaniciEmail).setCaption("E-posta");
        grid.addColumn(Kullanicilar::getKullaniciSifre).setCaption("Şifre");
        grid.addColumn(Kullanicilar::getKullaniciIzin).setCaption("Yetki").setWidth(180);
        return grid;
    }

    private void gridTiklanmaOlayi(Grid<Kullanicilar> grid) {
        grid.addItemClickListener(itemClick -> {
            try {
                KullaniciListesiGridDuzenlePopup kullaniciListesiGridDuzenlePopup=new KullaniciListesiGridDuzenlePopup();
                kullaniciListesiGridDuzenlePopup.setKullaniciId(itemClick.getItem().getKullaniciID());
                PopupView popupView = new PopupView(null, kullaniciListesiGridDuzenlePopup.duzenlePopup());
                popupView.setPopupVisible(true);
                popupView.setHideOnMouseOut(false);

                AnaPencere anaPencere=new AnaPencere();
                anaPencere.getLayout().addComponent(popupView, "left:50%");
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                //Notification.show("Zaten Seçim Yaptınız!!");
            }
        });
    }


}
