/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.parking.model;

import com.parking.model.min3d.core.Object3dContainer;
import com.parking.model.min3d.core.RendererActivity;
import com.parking.model.min3d.parser.IParser;
import com.parking.model.min3d.parser.Parser;
import com.parking.model.min3d.vos.Light;

import android.content.Intent;
import android.opengl.GLSurfaceView;

/**
 * Wrapper activity demonstrating the use of {@link GLSurfaceView}, a view
 * that uses OpenGL drawing into a dedicated surface.
 *
 * Shows:
 * + How to redraw in response to user input.
 */
public class TouchRotateActivity extends RendererActivity {
	@Override
	public void initScene() {
		Intent intent = getIntent();
		int floors = intent.getIntExtra("floors", 1);
		int cars = intent.getIntExtra("cars", 1);
		
		scene.lights().add(new Light());
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "com.parking.model:raw/camaro_obj", true);
		parser.parse();
		
		Object3dContainer carModel = parser.getParsedObject();
		carModel.scale().x = carModel.scale().y = carModel.scale().z = 0.07f;
		carModel.position().y = -(floors-1)*0.5f/2;
		carModel.rotation().x = -90;
		carModel.position().y += 0.05f;
		
		float carPlaceW = 0.16f;
		
		for (int i=0; i<floors; i++) {
			Object3dContainer floorModel = new Floor((cars+6)*carPlaceW/3f, 0.1f, 2.1f);
			floorModel.position().y = -(floors-1)*0.5f/2+i*0.5f;
			floorModel.colorMaterialEnabled(true);
			floorModel.lightingEnabled(false);
			scene.addChild(floorModel);
			
			if (i < (floors-1)) {
				Object3dContainer floorsLiftRight = new Lift(1.2f, 0.5f, 0.1f);
				floorsLiftRight.position().x += (cars+6)*carPlaceW/6f;
				floorsLiftRight.position().y = -(floors-1)*0.5f/2+i*0.5f;
				floorsLiftRight.colorMaterialEnabled(true);
				floorsLiftRight.lightingEnabled(false);
				
				Object3dContainer floorsLiftLeft = new Lift(1.2f, 0.5f, 0.1f);
				floorsLiftLeft.position().x -= (cars+6)*carPlaceW/6f;
				floorsLiftLeft.position().y = -(floors-1)*0.5f/2+i*0.5f;
				floorsLiftLeft.rotation().y += 180f;
				floorsLiftLeft.colorMaterialEnabled(true);
				floorsLiftLeft.lightingEnabled(false);
				
				scene.addChild(floorsLiftRight);
				scene.addChild(floorsLiftLeft);
			}
			
			carModel.position().x = -cars*carPlaceW/6f+carPlaceW/2f;
			for (int j=0; j<cars/3; j++) {
				carModel.position().z = -0.8f;
				scene.addChild(carModel.clone());
				
				carModel.position().z = 0f;
				scene.addChild(carModel.clone());
				
				carModel.position().z = 0.8f;
				scene.addChild(carModel.clone());
				
				carModel.position().x += carPlaceW;
			}
			carModel.position().y += 0.5f;
		}
		
	}

	@Override
	public void updateScene() {
	}
}