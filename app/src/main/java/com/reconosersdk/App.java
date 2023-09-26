package com.reconosersdk;

import android.app.Application;
import android.content.Context;

import com.reconosersdk.reconosersdk.BuildConfig;
import com.reconosersdk.reconosersdk.ui.LibraryReconoSer;

public class App extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        App app = this;
        context = getApplicationContext();
        String datos = "";
        /***
         * Develop-Staging
         * val GUID_CONVENIO = "97464498-D532-4964-ABF7-81DC0F884B21"
         * Production
         * val GUID_CONVENIO = "97464498-D532-4964-ABF7-81DC0F884B21"
         ***/
        String guidConv = "97464498-D532-4964-ABF7-81DC0F884B21";
        String clientId = BuildConfig.USER_TOKEN;
        String clientSecret = BuildConfig.PASS_TOKEN;
        //Banco Agrario Develop
        //String guidConv = "e997fc39-a84d-44ef-afdb-3dc2537e9165";

        //Banco Agrario Production
        //String guidConv = "9387abeb-6d74-40f6-998f-424870d51182";

        //OWOTECH PRODUCTION
        //String guidConv = "4b403b5f-a49a-412a-b6f8-52d27caf6835";

        //Convenio fuentes abiertas
        //String guidConv = "a0088109-c67d-4671-937e-2eb08c16884c";

        //Convenio EasyTrackProduction
        //String guidConv = "cbcafb35-8b17-40d1-bf6d-4aa8162fcfab";

        //Convenio OWOTECH develop
        //String guidConv = "e5eec12a-0e4b-43a6-8340-1360c454ec28";

        getInitServices(app, context, guidConv, datos, clientId, clientSecret);
    }

    public void getInitServices(App app, Context context, String guidConv, String datos, String clientId, String clientSecret) {
        LibraryReconoSer.init(app, context, guidConv, datos, clientId, clientSecret );
    }
}
