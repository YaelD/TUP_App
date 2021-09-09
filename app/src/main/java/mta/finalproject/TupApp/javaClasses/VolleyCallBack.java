package mta.finalproject.TupApp.javaClasses;

public interface VolleyCallBack {
    //void onSuccessResponse(Object result);

    void onSuccessResponse(String result);

    void onErrorResponse(String error);
}
