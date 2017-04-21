package jlp.notificacionesbbdd;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by TorreJL on 21/04/2017.
 */

public class ServicioNotificacion extends Service {


    Context context;

    public ServicioNotificacion() {
        this.context = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d("SERVICIO", "Empieza el servicio");

        TareaNotificacion notificacionTask = new TareaNotificacion(context);
        notificacionTask.execute();

        return START_STICKY;
    }




}
