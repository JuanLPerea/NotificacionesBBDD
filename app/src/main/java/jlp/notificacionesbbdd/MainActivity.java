package jlp.notificacionesbbdd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText textoDB;
    Context context = this;
    DatabaseHelper db;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoDB = (EditText) findViewById(R.id.textoBD);
        // Obtenemos la instancia de la BB.DD. (Singleton) y hacemos las tareas iniciales
        db = DatabaseHelper.getInstance(context);
        db.openDB();

    }


    public void notificacion(View v) {
        //Iniciar un servicio que a su vez lanza un asyncTask que muestra las notificaciones con lo que haya en la BB.DD
        intent = new Intent(this, ServicioNotificacion.class);
        startService(intent);
    }


    public void guardar(View v) {
        // Con esto guardamos lo que haya escrito en el EditText en nuestra BB.DD.
        String texto = textoDB.getText().toString();
        db.insertarDato(texto);
        textoDB.setText("");
    }


    public void parar(View v) {
        // Sacar por consola el contenido de la BB.DD. y parar el servicio
        db.vertodo();
        Log.d("FIN DATOS", " ------------------------------------------------------------------ ");

        stopService(intent);

    }


}
