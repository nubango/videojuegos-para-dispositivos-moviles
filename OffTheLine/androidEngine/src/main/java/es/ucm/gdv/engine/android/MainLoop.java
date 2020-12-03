package es.ucm.gdv.engine.android;

import es.ucm.gdv.engine.Logic;

public class MainLoop implements Runnable {

    private Logic _logic;
    private boolean _running;
    private Thread _renderThread;
    private Engine _engine;

    public MainLoop(Logic logic, Engine engine) {
        _logic = logic;
        _engine = engine;
    }


/* ---------------------------------------------------------------------------------------------- *
 * -------------------------------------- MÉTODOS PÚBLICOS -------------------------------------- *
 * ---------------------------------------------------------------------------------------------- */

    /**
     * Método llamado para solicitar que se continue con el
     * active rendering. El "juego" se vuelve a poner en marcha
     * (o se pone en marcha por primera vez).
     */
    public void resume() {

        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva, nunca se sabe quién va a
            // usarnos...)
            _running = true;
            // Lanzamos la ejecución de nuestro método run()
            // en una hebra nueva.
            _renderThread = new Thread(this);
            _renderThread.start();
        } // if (!_running)

    } // resume

    /**
     * Método llamado cuando el active rendering debe ser detenido.
     * Puede tardar un pequeño instante en volver, porque espera a que
     * se termine de generar el frame en curso.
     *
     * Se hace así intencionadamente, para bloquear la hebra de UI
     * temporalmente y evitar potenciales situaciones de carrera (como
     * por ejemplo que Android llame a resume() antes de que el último
     * frame haya terminado de generarse).
     */
    public void pause() {

        if (_running) {
            _running = false;
            while (true) {
                try {
                    _renderThread.join();
                    _renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause

    /**
     * Método que implementa el bucle principal del "juego" y que será
     * ejecutado en otra hebra. Aunque sea público, NO debe ser llamado
     * desde el exterior.
     */
    @Override
    public void run() {
        if (_renderThread != Thread.currentThread()) {
            // ¿¿Quién es el tuercebotas que está llamando al
            // run() directamente?? Programación defensiva
            // otra vez, con excepción, por merluzo.
            throw new RuntimeException("run() should not be called directly");
        }

        // Antes de saltar a la simulación, confirmamos que tenemos
        // un tamaño mayor que 0. Si la hebra se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while(_running && _engine.getSurfaceView().getWidth() == 0)
            // Espera activa. Sería más elegante al menos dormir un poco.
            ;

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;


        while (!_engine.getSurfaceView().getHolder().getSurface().isValid())
            ;
        _engine.getGraphics().setCanvas(_engine.getSurfaceView().getHolder().lockCanvas());
        _engine.getGraphics().setScaleFactor(_engine.getSurfaceView().getWidth(), _engine.getSurfaceView().getHeight());
        _engine.getSurfaceView().getHolder().unlockCanvasAndPost(_engine.getGraphics().getCanvas());

        if(!_logic.init(_engine)) {
            System.err.println("****Init de la lógica ha devuelto false****");
            return;
        }

        // Bucle principal.
        while(_running) {

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            _logic.update(elapsedTime);

            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            while (!_engine.getSurfaceView().getHolder().getSurface().isValid())
                ;
            _engine.getGraphics().setCanvas(_engine.getSurfaceView().getHolder().lockCanvas());

            _logic.render(_engine.getGraphics());

            _engine.getGraphics().renderBlackBars();
            _engine.getSurfaceView().getHolder().unlockCanvasAndPost(_engine.getGraphics().getCanvas());

        } // while

    } // run
}
