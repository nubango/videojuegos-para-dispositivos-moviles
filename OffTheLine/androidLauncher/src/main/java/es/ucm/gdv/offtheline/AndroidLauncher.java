package es.ucm.gdv.offtheline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.ucm.gdv.engine.android.Engine;

public class AndroidLauncher extends AppCompatActivity {

    Engine engine;
    /**
     * Método llamado por Android como parte del ciclo de vida de
     * la actividad. Se llama en el momento de lanzarla.
     *
     * @param savedInstanceState Información de estado de la actividad
     *                           previamente serializada por ella misma
     *                           para reconstruirse en el mismo estado
     *                           tras un reinicio. Será null la primera
     *                           vez.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        engine = new Engine(this, new OffTheLineLogic());
        setContentView(engine);
    }

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad va a pasar a primer plano,
     * estando en la cima de la pila de actividades y completamente
     * visible.
     *
     * Es llamado durante la puesta en marcha de la actividad (algo después
     * de onCreate()) y también después de un periodo de pausa (notificado
     * a través de onPause()).
     */
    @Override
    protected void onResume() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        engine.resume();

    } // onResume

    //--------------------------------------------------------------------

    /**
     * Método llamado por Android como parte del ciclo de vida de la
     * actividad. Notifica que la actividad ha dejado de ser la de
     * primer plano. Es un indicador de que el usuario está, de alguna
     * forma, abandonando la actividad.
     */
    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        engine.pause();

    } // onPause

}
