package core;

/**
 * Created by NF on 2/9/2017.
 */


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class NetworkVis extends Application {
    final Group root = new Group();
    final XformWorld world = new XformWorld();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final XformCamera cameraXform = new XformCamera();
    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
    double mouseFactorX, mouseFactorY;
    private Group axisGroup = new Group();

    @Override
    public void start(Stage primaryStage) {
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        buildCamera();
        buildBodySystem();
        buildAxes();
        Scene scene = new Scene(root, 800, 600, true);
        scene.setFill(Color.GREY);
        handleMouse(scene);
        primaryStage.setTitle("TrafoTest");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setCamera(camera);
        mouseFactorX = 180.0 / scene.getWidth();
        mouseFactorY = 180.0 / scene.getHeight();

    }

    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
    }

    private void buildBodySystem() {
        PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);
        Box box = new Box(400, 200, 100);
        box.setMaterial(whiteMaterial);
        PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        Sphere sphere = new Sphere(5);
        sphere.setMaterial(redMaterial);
        sphere.setTranslateX(10.0);
        sphere.setTranslateY(-100.0);
        sphere.setTranslateZ(-50.0);
//        world.getChildren().addAll(box);

        Graph mapGraph = new Graph(world);
//
        mapGraph.addEdge(new Vertex(0,0,0),new Vertex(100,100,50),whiteMaterial,new Object());
        mapGraph.addEdge(new Vertex(0,0,0),new Vertex(70,700,100),whiteMaterial,new Object());
        mapGraph.addEdge(new Vertex(0,0,0),new Vertex(-80,100,-50),whiteMaterial,new Object());
        mapGraph.addEdge(new Vertex(100,100,50),new Vertex(0,0,500),whiteMaterial,new Object());
        mapGraph.addEdge(new Vertex(70,700,100),new Vertex(0,0,500),whiteMaterial,new Object());
        mapGraph.addEdge(new Vertex(-80,100,-50),new Vertex(0,0,500),whiteMaterial,new Object());


//        Edge edge1 = mapGraph.createEdge(new Vertex(10,10,10),new Vertex(100,100,100));
//        mapGraph.addEdge(edge1,whiteMaterial,new Object());
//        Edge edge2 = mapGraph.createEdge(new Vertex(100,100,100),new Vertex(200,600,900));
//        Edge edge3 = mapGraph.createEdge(new Vertex(200,600,900),new Vertex(700,900,1200));

//        world.getChildren().addAll(edge1);
        world.getChildren().addAll(sphere);



        world.getChildren().addAll(mapGraph.getEdges());
        world.getChildren().addAll(mapGraph.getVertices());

        Random rn = new Random();

        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            Point3D startP = new Point3D(0,0,0);
            double alpha = 0;
            @Override
            public void handle(ActionEvent event) {
//                Point3D newPoint =  startP.add((rn.nextDouble()-0.5)*100,(rn.nextDouble()-0.5)*100,(rn.nextDouble()-0.5)*100);
//                world.getChildren().addAll(mapGraph.createEdge(new Vertex(startP),new Vertex(newPoint)));
//                startP = newPoint;

                double x=10*Math.sin(alpha);
                double y=10*Math.cos(alpha);
                double dx=(rn.nextDouble()-0.5)*10;
                double dy=(rn.nextDouble()-0.5)*10;
                double dz=(rn.nextDouble()-0.5)*10;

//                mapGraph.transformEdge(edge1,edge1.getStartPoint().getPoint3D(),new Point3D(x+dx,y+dy,z+dz));

//                edge1.setTranslateX(x+dx);

                startP = new Point3D(80+dx,80+dy,80+dz);
//                mapGraph.transformEdge(edge1,edge1.getStartPoint().getPoint3D(),startP);
                for(Vertex vertex: mapGraph.getVertices()){

                    mapGraph.transformVertex(vertex,new Point3D(80+(rn.nextDouble()-0.5)*10,(rn.nextDouble()-0.5)*10,(rn.nextDouble()-0.5)*10));
//                    Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(0.1), new Point3D(1,0,0));
//                    Translate translate = new Translate(x,y,0);
////                    edge1.getTransforms().addAll(translate);
////                    edge1.setTranslateX(x);
////                    edge1.setTranslateY(y);
//                    edge1.setRotationAxis(new Point3D(1,0,0));
//                    edge1.setRotate(alpha);
//                    edge1.getTransforms().removeAll();
//                    alpha+=0.01;
//                    if(alpha>Math.PI*6){
//                        alpha=0;
//                    }
//                    Translate moveToMidpoint = new Translate(dx, dy, dz);
//                    edge1.setTranslateX(dx);
//                    edge1.setTranslateX(dy);
//                    edge1.setTranslateX(dz);
//                    edge1.getTransforms().addAll(moveToMidpoint);
                }


//                for(Node node : world.getChildren()) {
//                    node.getTransforms().add(new Rotate(0.5, 0, 0, 0, Rotate.X_AXIS));
//
////                    node.setTranslateX(node.getTranslateX()+10);
////                    node.setRotationAxis(Rotate.X_AXIS);
////                    matrixRotateNode(node, 0, Math.PI / 4, 0);
//                }
            }
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }


    private void buildAxes() {
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }


    private void handleMouse(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent me) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry(mouseDeltaX * 180.0 / scene.getWidth());
                cameraXform.rx(-mouseDeltaY * 180.0 / scene.getHeight());
            } else if (me.isSecondaryButtonDown()) {
                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}


class XformCamera extends Group {
    Point3D px = new Point3D(1.0, 0.0, 0.0);
    Point3D py = new Point3D(0.0, 1.0, 0.0);
    Rotate r;
    Transform t = new Rotate();

    public XformCamera() {
        super();
    }

    public void rx(double angle) {
        r = new Rotate(angle, px);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

    public void ry(double angle) {
        r = new Rotate(angle, py);
        this.t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().addAll(t);
    }

}