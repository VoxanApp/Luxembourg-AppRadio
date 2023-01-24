package lu.voxhost.LuxoRadio.models;

public class Settings {
    int inter_clicks;
    String ad_status, ad_network, admob_small_banner, admob_medium_banner, admob_inter, admob_native,
            applovin_small_banner, applovin_medium_banner, applovin_inter, applovin_native;

    public Settings(int inter_clicks, String ad_status, String ad_network, String admob_small_banner, String admob_medium_banner, String admob_inter, String admob_native, String applovin_small_banner, String applovin_medium_banner, String applovin_inter, String applovin_native) {
        this.inter_clicks = inter_clicks;
        this.ad_status = ad_status;
        this.ad_network = ad_network;
        this.admob_small_banner = admob_small_banner;
        this.admob_medium_banner = admob_medium_banner;
        this.admob_inter = admob_inter;
        this.admob_native = admob_native;
        this.applovin_small_banner = applovin_small_banner;
        this.applovin_medium_banner = applovin_medium_banner;
        this.applovin_inter = applovin_inter;
        this.applovin_native = applovin_native;
    }

    public int getInter_clicks() {
        return inter_clicks;
    }

    public String getAd_status() {
        return ad_status;
    }

    public String getAd_network() {
        return ad_network;
    }

    public String getAdmob_small_banner() {
        return admob_small_banner;
    }

    public String getAdmob_medium_banner() {
        return admob_medium_banner;
    }

    public String getAdmob_inter() {
        return admob_inter;
    }

    public String getAdmob_native() {
        return admob_native;
    }

    public String getApplovin_small_banner() {
        return applovin_small_banner;
    }

    public String getApplovin_medium_banner() {
        return applovin_medium_banner;
    }

    public String getApplovin_inter() {
        return applovin_inter;
    }

    public String getApplovin_native() {
        return applovin_native;
    }
}
