/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exemplomapa;

/**
 *
 * @author marcelojtelles
 */
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class RoutePainter implements Painter<JXMapViewer> {
    private final List<GeoPosition> track;

    public RoutePainter(List<GeoPosition> track) {
        this.track = track;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();
        
        // Do anti-aliasing for smooth lines
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Convert from viewport to world bitmap
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        // Set the color and stroke for the route
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));

        // Draw the track
        for (int i = 0; i < track.size() - 1; i++) {
            Point2D pt1 = map.getTileFactory().geoToPixel(track.get(i), map.getZoom());
            Point2D pt2 = map.getTileFactory().geoToPixel(track.get(i + 1), map.getZoom());
            g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
        }

        g.dispose();
    }
}
