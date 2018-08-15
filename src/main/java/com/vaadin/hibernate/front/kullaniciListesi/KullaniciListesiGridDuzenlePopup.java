package com.vaadin.hibernate.front.kullaniciListesi;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.hibernate.back.service.Kullanicilar;
import com.vaadin.hibernate.back.service.KullanicilarDaoImpl;
import com.vaadin.hibernate.front.anaPencere.AnaPencere;
import com.vaadin.hibernate.front.anaPencere.AnaPencereMenuBar;
import com.vaadin.hibernate.front.kayitOl.KayitOlPanelRadioGrubu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @author Emre Kirman
 * @since 1.0
 */
public class KullaniciListesiGridDuzenlePopup {
    private  VerticalLayout popupContent;
    private  Panel panel;
    private int kullaniciId;
    private TextField textId;
    private TextField textAd;
    private TextField textEmail;
    private TextField textSifre;
    private Button buttonGuncelle;
    private KayitOlPanelRadioGrubu kayitOlPanelRadioGrubu;
    static Kullanicilar k;
    Binder<Kullanicilar> binder;

    KullaniciListesiGridDuzenlePopup() {
        popupContent =new VerticalLayout();
        panel=new Panel("Kullanıcı Bilgilerini Düzenle");
        textId=new TextField();
        textAd=new TextField();
        textEmail=new TextField();
        textSifre=new TextField();
        buttonGuncelle=new Button("Güncelle");
        kayitOlPanelRadioGrubu=new KayitOlPanelRadioGrubu();
    }

    Panel duzenlePopup(){
        panel.setStyleName(ValoTheme.PANEL_SCROLL_INDICATOR);
        KullanicilarDaoImpl kullanicilarDao=new KullanicilarDaoImpl();

        k=new Kullanicilar();
        k=kullanicilarDao.getById(kullaniciId);


        textId.setWidth("100px");
        textId.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textId.setIcon(VaadinIcons.AT);
        textId.setEnabled(false);

        textAd.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textAd.setIcon(VaadinIcons.USER);
        textAd.setWidth("300px");

        textEmail.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textEmail.setIcon(VaadinIcons.MAILBOX);
        textEmail.setWidth("300px");


        textSifre.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textSifre.setIcon(VaadinIcons.KEY);
        textSifre.setWidth("300px");

        buttonGuncelle.setStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        buttonGuncelle.setIcon(VaadinIcons.CHECK_CIRCLE_O);

        buttonClickGuncelle(kullanicilarDao);

        binder=new Binder<>();
        binder.setBean(k);
        binder.forField(textAd).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez",1,120
        )).bind(Kullanicilar::getKullaniciAdSoyad,Kullanicilar::setKullaniciAdSoyad);
        binder.forField(textEmail).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez",1,100
        )).bind(Kullanicilar::getKullaniciEmail,Kullanicilar::setKullaniciEmail);
        binder.forField(textSifre).withValidator(new StringLengthValidator(
                "Bu alan boş geçilemez",1,50
        )).bind(Kullanicilar::getKullaniciSifre,Kullanicilar::setKullaniciSifre);



        if (k!=null){
            textId.setValue(String.valueOf(k.getKullaniciID()));
            textAd.setValue(k.getKullaniciAdSoyad());
            textEmail.setValue(k.getKullaniciEmail());
            textSifre.setValue(k.getKullaniciSifre());
            kayitOlPanelRadioGrubu.setVeriAdi(k.getKullaniciIzin());
        }

        buttonGuncelle.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonGuncelle.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        popupContent.addComponent(textId);
        popupContent.addComponent(textAd);
        popupContent.addComponent(textEmail);
        popupContent.addComponent(textSifre);
        popupContent.addComponent(kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup());
        popupContent.addComponent(buttonGuncelle);

        panel.setContent(popupContent);
        panel.setSizeUndefined();

        return panel;
    }

    private void buttonClickGuncelle(KullanicilarDaoImpl kullanicilarDao) {
        buttonGuncelle.addClickListener(clickEvent -> {
            try{
                Kullanicilar kullanicilar=new Kullanicilar();
                kullanicilar.setKullaniciID(k.getKullaniciID());
                kullanicilar.setKullaniciAdSoyad(textAd.getValue());
                kullanicilar.setKullaniciEmail(textEmail.getValue());
                kullanicilar.setKullaniciSifre(textSifre.getValue());
                kullanicilar.setKullaniciIzin(kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup().getValue().getAd());
                if (binder.isValid() && !kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup().getValue().getAd().isEmpty()){
                    kullanicilarDao.update(kullanicilar);
                    AnaPencere anaPencere=new AnaPencere();
                    anaPencere.getLayout().removeAllComponents();
                    KullaniciListesiGrid kullaniciListesiGrid=new KullaniciListesiGrid();
                    AnaPencereMenuBar anaPencereMenuBar=new AnaPencereMenuBar();
                    anaPencereMenuBar.sayfaYonlendir(kullaniciListesiGrid.kullaniciListesiGridTablo(),"top:10%;");
                }
            }catch (NullPointerException e){
                textAd.setComponentError(new UserError("Bu alan boş geçilemez!!"));
                textEmail.setComponentError(new UserError("Bu alan boş geçilemez!!"));
                textSifre.setComponentError(new UserError("Bu alan boş geçilemez!!"));
                kayitOlPanelRadioGrubu.kullaniciIzinRadioGrup().setComponentError(new UserError("Bu alan boş geçilemez!!"));
            }



        });
    }

    public VerticalLayout getPopupContent() {
        return popupContent;
    }

    public void setPopupContent(VerticalLayout popupContent) {
        this.popupContent = popupContent;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public TextField getTextId() {
        return textId;
    }

    public void setTextId(TextField textId) {
        this.textId = textId;
    }

    public TextField getTextAd() {
        return textAd;
    }

    public void setTextAd(TextField textAd) {
        this.textAd = textAd;
    }

    public TextField getTextEmail() {
        return textEmail;
    }

    public void setTextEmail(TextField textEmail) {
        this.textEmail = textEmail;
    }

    public TextField getTextSifre() {
        return textSifre;
    }

    public void setTextSifre(TextField textSifre) {
        this.textSifre = textSifre;
    }

    public Button getButtonGuncelle() {
        return buttonGuncelle;
    }

    public void setButtonGuncelle(Button buttonGuncelle) {
        this.buttonGuncelle = buttonGuncelle;
    }

    public KayitOlPanelRadioGrubu getKayitOlPanelRadioGrubu() {
        return kayitOlPanelRadioGrubu;
    }

    public void setKayitOlPanelRadioGrubu(KayitOlPanelRadioGrubu kayitOlPanelRadioGrubu) {
        this.kayitOlPanelRadioGrubu = kayitOlPanelRadioGrubu;
    }

    public static Kullanicilar getK() {
        return k;
    }

    public static void setK(Kullanicilar k) {
        KullaniciListesiGridDuzenlePopup.k = k;
    }
}
