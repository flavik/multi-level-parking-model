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

package com.parking.model.min3d.core;

import com.parking.model.min3d.Shared;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Wrapper activity demonstrating the use of {@link GLSurfaceView}, a view
 * that uses OpenGL drawing into a dedicated surface.
 *
 * Shows:
 * + How to redraw in response to user input.
 */
public class TouchSurfaceView extends GLSurfaceView {
	private ScaleGestureDetector mScaleDetector;
	
    private final float TOUCH_SCALE_FACTOR = 180.0f / (320);
    private final float TRACKBALL_SCALE_FACTOR = 36.0f;
    private float mPreviousX;
    private float mPreviousY;
	
	private static final int INVALID_POINTER_ID = -1;

	// The ‘active pointer’ is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;

    public TouchSurfaceView(Context context) {
        super(context);
        // Create our ScaleGestureDetector
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override 
    public boolean onTrackballEvent(MotionEvent e) {
    	Shared.renderer().mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
    	Shared.renderer().mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
        requestRender();
        return true;
    }

    @Override 
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
    	
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
	        case MotionEvent.ACTION_DOWN: {
	            float x = ev.getX();
	            float y = ev.getY();
	            
	            mPreviousX = x;
	            mPreviousY = y;
	
	            // Save the ID of this pointer
	            mActivePointerId = ev.getPointerId(0);
	            break;
	        }
	        case MotionEvent.ACTION_MOVE: {
	            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
	            final float x = ev.getX(pointerIndex);
	            final float y = ev.getY(pointerIndex);
	            
	            if (!mScaleDetector.isInProgress()) {
		            float dx = x - mPreviousX;
		            float dy = y - mPreviousY;
		            Shared.renderer().mAngleX += dx * TOUCH_SCALE_FACTOR;
		            Shared.renderer().mAngleY += dy * TOUCH_SCALE_FACTOR;
		            requestRender();
	            }
	            
	            mPreviousX = x;
	            mPreviousY = y;
	            break;
	        }
	        
	        case MotionEvent.ACTION_UP: {
	            mActivePointerId = INVALID_POINTER_ID;
	            break;
	        }
	            
	        case MotionEvent.ACTION_CANCEL: {
	            mActivePointerId = INVALID_POINTER_ID;
	            break;
	        }
	        
	        case MotionEvent.ACTION_POINTER_UP: {
	            // Extract the index of the pointer that left the touch sensor
	            final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	            final int pointerId = ev.getPointerId(pointerIndex);
	            if (pointerId == mActivePointerId) {
	                // This was our active pointer going up. Choose a new
	                // active pointer and adjust accordingly.
	                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	                mPreviousX = ev.getX(newPointerIndex);
	                mPreviousY = ev.getY(newPointerIndex);
	                mActivePointerId = ev.getPointerId(newPointerIndex);
	            }
	            break;
	        }
        }
        return true;
    }
    
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Shared.renderer().mScaleFactor *= Math.pow(detector.getScaleFactor(),0.5f);
            
            // Don't let the object get too small or too large.
            Shared.renderer().mScaleFactor = Math.max(0.3f, Math.min(Shared.renderer().mScaleFactor, 1.3f));

            requestRender();
            return true;
        }
    }
}


