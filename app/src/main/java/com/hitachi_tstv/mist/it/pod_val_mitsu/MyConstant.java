package com.hitachi_tstv.mist.it.pod_val_mitsu;

/**
 * Created by Tunyaporn on 9/18/2017.
 */

public interface MyConstant {
    public static final String projectString = "MMTH";
    public static final String serverString = "http://service.eternity.co.th/";
    public static final String pathString = "/app/CenterService/";
    public static final String urlGetUserLogin = serverString + projectString + pathString + "getUserLogin.php";
    public static final String urlGetPlanDate = serverString + projectString + pathString +"getPlanDate.php";
    public static final String urlGetTrip = serverString + projectString + pathString +"getTrip.php";
    public static final String urlGetUpdateDCStart = serverString + projectString + pathString +"getupdateDCStart.php";
    public static final String urlGetPlanTrip = serverString + projectString + pathString + "getPlanTrip.php";
    public static final String urlUpdateArrival = serverString + projectString + pathString + "updateArrival.php";
    public static final String urlUpdateDCFinish = serverString + projectString + pathString + "updateDCFinish.php";

}
