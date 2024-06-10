package moteurJeu;

import gameLaby.laby.LabyJeu;
import gameLaby.laby.Perso;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MoteurJeu extends Application {

    private static double FPS = 100;
    private static double dureeFPS = 1000 / (FPS + 1);
    private static double WIDTH = 800;
    private static double HEIGHT = 600;
    private static final double HEALTH_BAR_HEIGHT = 30; // Hauteur de la barre de santé

    private final FrameStats frameStats = new FrameStats();
    private static Jeu jeu = null;
    private static DessinJeu dessin = null;
    private Clavier controle = new Clavier();

    public static void launch(Jeu jeu, DessinJeu dessin) {
        MoteurJeu.jeu = jeu;
        MoteurJeu.dessin = dessin;
        if (jeu != null)
            launch();
    }

    public static void setFPS(int FPSSouhaitees) {
        FPS = FPSSouhaitees;
        dureeFPS = 1000 / (FPS + 1);
    }

    public static void setTaille(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
    }

    @Override
    public void start(Stage primaryStage) {
        final Canvas canvas = new Canvas(WIDTH, HEIGHT); 
        final Pane canvasContainer = new Pane(canvas);
        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());

        final Label stats = new Label();
        stats.textProperty().bind(frameStats.textProperty());

        final BorderPane root = new BorderPane();
        root.setCenter(canvasContainer);
        root.setBottom(stats);

        final Scene scene = new Scene(root, WIDTH, HEIGHT + HEALTH_BAR_HEIGHT + 20); // Augmente la hauteur totale de la scène
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controle.appuyerTouche(event);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controle.relacherTouche(event);
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    jeu.init();
                }
            }
        });

        startAnimation(canvas);
    }

    private void startAnimation(final Canvas canvas) {
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() == 0) {
                    lastUpdateTime.set(timestamp);
                }
                long elapsedTime = timestamp - lastUpdateTime.get();
                double dureeEnMilliSecondes = elapsedTime / 1_000_000.0;

                if (dureeEnMilliSecondes > dureeFPS) {
                    jeu.update(dureeEnMilliSecondes / 1_000., controle);
                    dessin.dessinerJeu(jeu, canvas);
                    frameStats.addFrame(elapsedTime);
                    drawPlayerHealth(canvas);
                    lastUpdateTime.set(timestamp);
                }
            }
        };
        timer.start();
    }

    private void drawPlayerHealth(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, HEIGHT, WIDTH, HEALTH_BAR_HEIGHT);
        gc.setFill(Color.RED);
        if (jeu instanceof LabyJeu) {
            LabyJeu labyJeu = (LabyJeu) jeu;
            Perso player = labyJeu.getLabyrinthe().getPj();
            double healthPercentage = player.getPointsDeVie() / 20.0;
            gc.fillRect(10, HEIGHT + 5, healthPercentage * (WIDTH - 20), 20);
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            gc.fillText("Health: " + player.getPointsDeVie(), 10, HEIGHT - 5);
        }
    }
}
