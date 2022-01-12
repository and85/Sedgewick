/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Jannik.Richter
 */
public class FastCollinearPoints {

    private final Point[] points;
    private LineSegment segments[];
    private int segmentCount;

    /**
     * @description finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {

        // check to see if argument matches constraints
        checksPoints(points);

        this.points = points.clone();
        this.segments = new LineSegment[2];
        this.segmentCount = 0;
        LinkedList<Point> collinearPoints = new LinkedList<Point>();

        //Arrays.sort(this.points);
        // check to see if argument matches constraints
        for (Point point : this.points) {
            Arrays.sort(this.points, point.slopeOrder());
            double prevSlope = 0.0;

            for (int j = 0; j < this.points.length; j++) {
                double currentSlope = point.slopeTo(this.points[j]);
                if(j == 0 || currentSlope != prevSlope) {

                    if(collinearPoints.size() >= 3) {
                        //Collections.sort(collinearPoints);
                        this.enqueue(new LineSegment(collinearPoints.getFirst(), collinearPoints.getLast()));
                        collinearPoints.getFirst().drawTo(collinearPoints.getLast());
                        StdDraw.show();
                    }

                    collinearPoints.clear();
                }

                collinearPoints.add(this.points[j]);
                prevSlope = currentSlope;
            }
        }

    }

    /**
     * @description the number of line segments
     * @return
     */
    public int numberOfSegments() {
        return this.segmentCount;
    }

    /**
     * @description the line segments
     * @return
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(this.segments, this.segmentCount);
    }

    /**
     * @description resize the underlying array holding the elements
     * @param capacity
     */
    private void resize(int capacity) {
        assert capacity >= this.segmentCount;

        // textbook implementation
        LineSegment[] temp = new LineSegment[capacity];
        System.arraycopy(this.segments, 0, temp, 0, this.segmentCount);
        this.segments = temp;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }

    /**
     * @description add the item
     */
    private void enqueue(LineSegment item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if(this.segmentCount == this.segments.length) {
            resize(2 * this.segments.length);
        }

        this.segments[this.segmentCount++] = item;
    }

    /**
     * @description do check on argument
     */
    private void checksPoints(Point[] points){
        if(points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i ++) {
            for(int j = 0; j < points.length; j++) {

                if(points[i] == null || points[j] == null) {
                    throw new IllegalArgumentException();
                }

                if(i != j && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public static void main(String[] args) {
        // UncommentedEmptyMethodBody
    }
}
