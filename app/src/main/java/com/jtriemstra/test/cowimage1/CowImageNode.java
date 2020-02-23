/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jtriemstra.test.cowimage1;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;

/**
 * Node for rendering an augmented image. The image is framed by placing the virtual picture frame
 * at the corners of the augmented image trackable.
 */
@SuppressWarnings({"AndroidApiChecker"})
public class CowImageNode extends AnchorNode {

  private static final String TAG = "AugmentedImageNode";

  // The augmented image represented by this node.
  private AugmentedImage image;

  // Models of the 4 corners.  We use completable futures here to simplify
  // the error handling and asynchronous loading.  The loading is started with the
  // first construction of an instance, and then used when the image is set.
//  private static CompletableFuture<ModelRenderable> ulCorner;
//  private static CompletableFuture<ModelRenderable> urCorner;
//  private static CompletableFuture<ModelRenderable> lrCorner;
//  private static CompletableFuture<ModelRenderable> llCorner;
//  private static CompletableFuture<Material> cornerMaterial;
  private Context thisContext;

  public CowImageNode(Context context) {
    thisContext = context;
  }

  public void createCornersFromMaterial(Material material){
    Vector3 localPosition = new Vector3();
    ModelRenderable cubeModel;
    Node cornerNode;
    Vector3 size = new Vector3(0.01f, 0.01f, 0.01f);

    localPosition.set(-0.5f * image.getExtentX(), -0.00f, -0.5f * image.getExtentZ());

    cubeModel = ShapeFactory.makeCube(size, localPosition, material);
    cubeModel.setShadowCaster(false);
    cubeModel.setShadowReceiver(false);

    cornerNode = new Node();
    cornerNode.setParent(this);
    cornerNode.setRenderable(cubeModel);

    localPosition.set(-0.5f * image.getExtentX(), -0.00f, 0.5f * image.getExtentZ());

    cubeModel = ShapeFactory.makeCube(size, localPosition, material);
    cubeModel.setShadowCaster(false);
    cubeModel.setShadowReceiver(false);

    cornerNode = new Node();
    cornerNode.setParent(this);
    cornerNode.setRenderable(cubeModel);

    localPosition.set(0.5f * image.getExtentX(), -0.00f, 0.5f * image.getExtentZ());

    cubeModel = ShapeFactory.makeCube(size, localPosition, material);
    cubeModel.setShadowCaster(false);
    cubeModel.setShadowReceiver(false);

    cornerNode = new Node();
    cornerNode.setParent(this);
    cornerNode.setRenderable(cubeModel);

    localPosition.set(0.5f * image.getExtentX(), -0.00f, -0.5f * image.getExtentZ());

    cubeModel = ShapeFactory.makeCube(size, localPosition, material);
    cubeModel.setShadowCaster(false);
    cubeModel.setShadowReceiver(false);

    cornerNode = new Node();
    cornerNode.setParent(this);
    cornerNode.setRenderable(cubeModel);

  }
  /**
   * Called when the AugmentedImage is detected and should be rendered. A Sceneform node tree is
   * created based on an Anchor created from the image. The corners are then positioned based on the
   * extents of the image. There is no need to worry about world coordinates since everything is
   * relative to the center of the image, which is the parent node of the corners.
   */
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  public void setImage(AugmentedImage image) {
    this.image = image;

    Material m;

    MaterialFactory.makeOpaqueWithColor(thisContext, new Color(android.graphics.Color.RED))
            .thenAccept(
                    material -> {
                      createCornersFromMaterial(material);
                    }
            );


    // Set the anchor based on the center of the image.
    setAnchor(image.createAnchor(image.getCenterPose()));

    // Make the 4 corner nodes.
/*    Vector3 localPosition = new Vector3();
    Node cornerNode;

    // Upper left corner.
    localPosition.set(-0.5f * image.getExtentX(), 0.0f, -0.5f * image.getExtentZ());
    Vector3 cubeSize = new Vector3(0.02f, 0.02f, 0.02f);

      ModelRenderable cubeModel = ShapeFactory.makeCube(cubeSize, localPosition, cornerMaterial.get());


      cornerNode = new Node();
      cornerNode.setParent(this);
      cornerNode.setLocalPosition(localPosition);
      cornerNode.setRenderable(cubeModel);*/

//    // Upper right corner.
//    localPosition.set(0.5f * image.getExtentX(), 0.0f, -0.5f * image.getExtentZ());
//    cornerNode = new Node();
//    cornerNode.setParent(this);
//    cornerNode.setLocalPosition(localPosition);
//    cornerNode.setRenderable(urCorner.getNow(null));
//
//    // Lower right corner.
//    localPosition.set(0.5f * image.getExtentX(), 0.0f, 0.5f * image.getExtentZ());
//    cornerNode = new Node();
//    cornerNode.setParent(this);
//    cornerNode.setLocalPosition(localPosition);
//    cornerNode.setRenderable(lrCorner.getNow(null));
//
//    // Lower left corner.
//    localPosition.set(-0.5f * image.getExtentX(), 0.0f, 0.5f * image.getExtentZ());
//    cornerNode = new Node();
//    cornerNode.setParent(this);
//    cornerNode.setLocalPosition(localPosition);
//    cornerNode.setRenderable(llCorner.getNow(null));
  }

  public AugmentedImage getImage() {
    return image;
  }
}
