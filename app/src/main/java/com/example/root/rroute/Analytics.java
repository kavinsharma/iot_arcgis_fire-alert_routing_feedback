package com.example.root.rroute;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MultiPoint;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;

import static com.esri.core.symbol.SimpleMarkerSymbol.STYLE.CIRCLE;


/**
 * Created by root on 12/2/17.
 */

public class Analytics extends AppCompatActivity implements OnClickListener{
    public static MapView map = null;
    ArcGISTiledMapServiceLayer tileLayer;
    public static Point mLocation = null,alert1,zoom;
    GraphicsLayer fale,gras,hazm,othe,Analytics,tras;
    public final SpatialReference wm = SpatialReference.create(102100);
    public final SpatialReference egs = SpatialReference.create(4326);
    MultiPoint mPoint=null;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analytics);
        dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        map = (MapView) findViewById(R.id.map);
        tileLayer = new ArcGISTiledMapServiceLayer(
                "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
        map.addLayer(tileLayer);
        Analytics = new GraphicsLayer();

        SimpleMarkerSymbol simpleMarker = new SimpleMarkerSymbol(Color.RED, 20, CIRCLE);
        PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                map.getContext(), getResources().getDrawable(
                R.mipmap.hazmat));
        zoom= new Point(78.007928,30.329953);
        Point p = (Point) GeometryEngine.project(zoom, egs, wm);
        map.zoomToResolution(p, 10.0);
        //---------dummy points for false alarm
        fale = new GraphicsLayer();
        MultiPoint fal = new MultiPoint();
        fal.add(77.996781,30.326799);
        fal.add(77.9939497,30.3225402);
        try {
            addMultiPointToGraphicsLayer(fal,destinationSymbol,fale,1);
            map.addLayer(fale);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //dummy points for grass fire
        gras = new GraphicsLayer();
        MultiPoint gra = new MultiPoint();
        gra.add(77.992482,30.339929);
        gra.add(77.988113,30.339510);
        gra.add(78.012098,30.320101);
        try {
            addMultiPointToGraphicsLayer(gra,destinationSymbol,gras,2);
            map.addLayer(gras);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //dummy point for hazmat
        hazm = new GraphicsLayer();
        MultiPoint haz = new MultiPoint();
        haz.add(78.0010169,30.3232562);
        haz.add(77.9848491,30.3306837);
        haz.add(77.9873836,30.3225402);


        try {
            addMultiPointToGraphicsLayer(haz,destinationSymbol,hazm,3);
            map.addLayer(hazm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-- dummy point for other fire
        othe = new GraphicsLayer();
        MultiPoint oth = new MultiPoint();
        oth.add(78.010785,30.333194);
        oth.add(78.007963,30.327772);
        oth.add(78.003138,30.328270);


        try {
            addMultiPointToGraphicsLayer(oth,destinationSymbol,othe,4);
            map.addLayer(othe);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //--------------dummy points for structfire-------------------
        MultiPoint struct_fire = new MultiPoint();
        struct_fire.add(77.981931,30.331999);
        struct_fire.add(78.026619,30.321915);
        struct_fire.add(78.0005301,30.3246977);
        struct_fire.add(77.9971867,30.3232562);
        struct_fire.add(78.009450,30.315082);
        struct_fire.add(77.999033,30.321513);
        struct_fire.add(78.005493,30.323414);
        struct_fire.add(78.010510,30.322697);

        try {
            addMultiPointToGraphicsLayer(struct_fire,destinationSymbol,Analytics,5);
            map.addLayer(Analytics);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------dummy point for trash fire-----------
        tras = new GraphicsLayer();
        MultiPoint tra = new MultiPoint();
        tra.add(77.9840546,30.3232562);
        tra.add(77.9921223,30.323701);
        try {
            addMultiPointToGraphicsLayer(tra,destinationSymbol,tras,6);
            map.addLayer(tras);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** alert1 = new Point(77.981931,30.331999);
        alert1 = new Point(77.987478,30.330008 );
        Point a = (Point) GeometryEngine.project(alert1,egs,wm);
        Graphic pointGraphic = new Graphic(a, destinationSymbol);
        zoom= new Point(78.026619,30.321915);
        Point p = (Point) GeometryEngine.project(zoom, egs, wm);
        map.zoomToResolution(p, 70.0);
        Analytics.addGraphic(pointGraphic);
        map.addLayer(Analytics);
        */
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        map.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.unpause();
    }
    /**
     * add multipoint to a graphic layer
     */
    public void addMultiPointToGraphicsLayer(MultiPoint mPoint,
                                             PictureMarkerSymbol destinationSymbo, GraphicsLayer graphicsLayer,int aa)
            throws Exception {
        int len = mPoint.getPointCount();
        Graphic[] graphics = new Graphic[len];
        int type = aa;
        for (int i = 0; i < len; i++) {
           // mPoint.add(77.981931,30.331999);
            Point geom = mPoint.getPoint(i);
            Point a = (Point) GeometryEngine.project(geom,SpatialReference.create(4326),SpatialReference.create(102100));
            if (type ==1) {
                PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                        map.getContext(), getResources().getDrawable(
                        R.mipmap.fals));
                graphics[i] = new Graphic(a, destinationSymbol);
            }
            else if(type==2){
                PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                        map.getContext(), getResources().getDrawable(
                        R.mipmap.grass));
                graphics[i] = new Graphic(a, destinationSymbol);

            }
            else if (type==3){
                PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                        map.getContext(), getResources().getDrawable(
                        R.mipmap.hazmat));
                graphics[i] = new Graphic(a, destinationSymbol);

            }
            else if (type==4){
                PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                        map.getContext(), getResources().getDrawable(
                        R.mipmap.other));
                graphics[i] = new Graphic(a, destinationSymbol);

            }
            else if(type==5){
                PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                        map.getContext(), getResources().getDrawable(
                        R.mipmap.struct));
                graphics[i] = new Graphic(a, destinationSymbol);

            }
            else {
                PictureMarkerSymbol destinationSymbol = new PictureMarkerSymbol(
                        map.getContext(), getResources().getDrawable(
                        R.mipmap.trash));
                graphics[i] = new Graphic(a, destinationSymbol);

            }
            graphicsLayer.addGraphic(graphics[i]);

        }

    }// end of method

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        new MenuInflater(this).inflate(R.menu.info, menu);
        return true;
    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.direction:
            popup();
            return true;




        default:
            return super.onOptionsItemSelected(item);
    }
}

public void popup(){
    TextView close;
    dialog.setContentView(R.layout.pop);

    close = (TextView)  dialog.findViewById(R.id.textView9);
    close.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });
    dialog.show();
}
}
