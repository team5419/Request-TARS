// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team5419.lib.math.geometry;

import java.util.Arrays;
import java.util.List;

/** Represents a 3d box */
public class Box3d {

    Vector3d corner1, corner2, corner3, corner4, //Represents the front of the box
        corner5, corner6, corner7, corner8; //Represents the back of the box
    List<Vector3d> corners = Arrays.asList(corner1, corner2, corner3, corner4, corner5, corner6, corner7, corner8);

    public Box3d() {
        
    }
    public Box3d(double width, double height, double depth) {
        

    }

    public static Box3d fromBox2d(Box2d frontFace, Box2d sideFace, Box2d topFace) {
        return new Box3d();
    }


}
