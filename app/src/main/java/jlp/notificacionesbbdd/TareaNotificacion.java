package jlp.notificacionesbbdd;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by TorreJL on 21/04/2017.
 */

public class TareaNotificacion extends AsyncTask<Void, Integer, Void> {


    DatabaseHelper db;
    Context context;
    NotificationCompat.Builder mBuilder;
    String noti;
    String publicado;
    int id;


    public TareaNotificacion(Context context) {
        this.context = context;
        this.db = db.getInstance(context);
        db.vertodo();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        int n = 0;

        do {

            id = Integer.parseInt(db.obtenerDato(n)[0]);
            noti = db.obtenerDato(n)[1];
            publicado = db.obtenerDato(n)[2];

            Log.d("DATOS", " Notificación: " + noti);

            // si no está publicado lo hacemos y lo cambiamos en la base de datos a "publicado"
            if (publicado.equals("N")) {

                // Creamos la notificacion
                mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Servicio de notificaciones en BB.DD.")
                        .setContentText(noti);

                //Lanzamos la notificacion
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                int mId = 0;
                mNotificationManager.notify(mId, mBuilder.build());


                // esperar 3 segundos
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    Log.d("DATOS", "Error en el Hilo");
                }

                // cambiar en la base de datos el registro a "publicado"
                db.publicado(n);
            }

            //avanzamos un registro
            n++;

        } while (n < db.numregistros());


        return null;
    }


}
