package com.hitachi_tstv.mist.it.pod_val_mitsu;

/**
 * Created by Tunyaporn on 9/18/2017.
 */

public interface MyConstant {
    public static final String serverString = "http://203.154.103.43/";
    public static final String projectString = "mmth";
//    public static final String serverString = "http://service.eternity.co.th/";
//    public static final String projectString = "mmth-close";
    public static final String pathString = "/app/CenterService/";
    public static final String urlGetUserLogin = serverString + projectString + pathString + "getUserLogin.php";
    public static final String urlGetPlanDate = serverString + projectString + pathString + "getPlanDate.php";
    public static final String urlGetTrip = serverString + projectString + pathString + "getTrip.php";
    public static final String urlGetUpdateDCStart = serverString + projectString + pathString + "UpdateDCStart.php";
    public static final String urlGetPlanTrip = serverString + projectString + pathString + "getPlanTrip.php";
    public static final String urlUpdateArrival = serverString + projectString + pathString + "updateArrival.php";
    public static final String urlUpdateDCFinish = serverString + projectString + pathString + "updateDCFinish.php";
    public static final String urlGetTripDetailPickup = serverString + projectString + pathString + "getTripDetailPickup.php";
    public static final String urlUpdateDeparture = serverString + projectString + pathString + "updateDeparture.php";
    public static final String urlGetTripDetailDelivery = serverString + projectString + pathString + "getTripDetailDelivery.php";
    public static final String[] getColumnLogin = new String[]{"drv_id", "drv_name", "ven_id", "drv_pic", "checkGPSIn", "checkGPSOut", "gender", "drv_username"};
    public static final int getColumnLoginSize = getColumnLogin.length;
}
