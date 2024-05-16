package com.example.projectofinal;

public class Space {
    private String _id;
    private String ref_espai;
    private String ref_ins;
    private String ine;
    private String municipi;
    private String ubicaci_utmx;
    private String ubicaci_utmy;
    private String latitud;
    private String longitud;
    private String tipus_d_espai;
    private String classificaci;
    private String superf_cie;
    private String superf_cie_c_lcul;
    private String llarg;
    private String ample;
    private String tancament;
    private String estat_de_servei;
    private String paviment;
    private String activitat_principal;

    public Space(String _id, String ref_espai, String ref_ins, String ine, String municipi, String ubicaci_utmx, String ubicaci_utmy, String latitud, String longitud, String tipus_d_espai, String classificaci, String superf_cie, String superf_cie_c_lcul, String llarg, String ample, String tancament, String estat_de_servei, String paviment, String activitat_principal) {
        this._id = _id;
        this.ref_espai = ref_espai;
        this.ref_ins = ref_ins;
        this.ine = ine;
        this.municipi = municipi;
        this.ubicaci_utmx = ubicaci_utmx;
        this.ubicaci_utmy = ubicaci_utmy;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipus_d_espai = tipus_d_espai;
        this.classificaci = classificaci;
        this.superf_cie = superf_cie;
        this.superf_cie_c_lcul = superf_cie_c_lcul;
        this.llarg = llarg;
        this.ample = ample;
        this.tancament = tancament;
        this.estat_de_servei = estat_de_servei;
        this.paviment = paviment;
        this.activitat_principal = activitat_principal;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRef_espai() {
        return ref_espai;
    }

    public void setRef_espai(String ref_espai) {
        this.ref_espai = ref_espai;
    }

    public String getRef_ins() {
        return ref_ins;
    }

    public void setRef_ins(String ref_ins) {
        this.ref_ins = ref_ins;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public String getUbicaci_utmx() {
        return ubicaci_utmx;
    }

    public void setUbicaci_utmx(String ubicaci_utmx) {
        this.ubicaci_utmx = ubicaci_utmx;
    }

    public String getUbicaci_utmy() {
        return ubicaci_utmy;
    }

    public void setUbicaci_utmy(String ubicaci_utmy) {
        this.ubicaci_utmy = ubicaci_utmy;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTipus_d_espai() {
        return tipus_d_espai;
    }

    public void setTipus_d_espai(String tipus_d_espai) {
        this.tipus_d_espai = tipus_d_espai;
    }

    public String getClassificaci() {
        return classificaci;
    }

    public void setClassificaci(String classificaci) {
        this.classificaci = classificaci;
    }

    public String getSuperf_cie() {
        return superf_cie;
    }

    public void setSuperf_cie(String superf_cie) {
        this.superf_cie = superf_cie;
    }

    public String getSuperf_cie_c_lcul() {
        return superf_cie_c_lcul;
    }

    public void setSuperf_cie_c_lcul(String superf_cie_c_lcul) {
        this.superf_cie_c_lcul = superf_cie_c_lcul;
    }

    public String getLlarg() {
        return llarg;
    }

    public void setLlarg(String llarg) {
        this.llarg = llarg;
    }

    public String getAmple() {
        return ample;
    }

    public void setAmple(String ample) {
        this.ample = ample;
    }

    public String getTancament() {
        return tancament;
    }

    public void setTancament(String tancament) {
        this.tancament = tancament;
    }

    public String getEstat_de_servei() {
        return estat_de_servei;
    }

    public void setEstat_de_servei(String estat_de_servei) {
        this.estat_de_servei = estat_de_servei;
    }

    public String getPaviment() {
        return paviment;
    }

    public void setPaviment(String paviment) {
        this.paviment = paviment;
    }

    public String getActivitat_principal() {
        return activitat_principal;
    }

    public void setActivitat_principal(String activitat_principal) {
        this.activitat_principal = activitat_principal;
    }
}
