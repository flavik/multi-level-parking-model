/*
 * Copyright (C) 2007 The Android Open Source Project
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A vertex shaded cube.
 */
class __Scene
{
	private IntBuffer   mVertexBuffer;

    private IntBuffer   mColorBuffer;
    private ByteBuffer  mIndexBuffer;

    
    private IntBuffer   mVertexBufferAxeX;
    private IntBuffer   mColorBufferAxeX;
    
    private IntBuffer   mVertexBufferAxeY;
    private IntBuffer   mColorBufferAxeY;
    
    private IntBuffer   mVertexBufferAxeZ;
    private IntBuffer   mColorBufferAxeZ;

    private ByteBuffer  mIndexBufferAxes;

	
	private int floors;
	private int cars;
	
	private float betweenFloorsHeight = 0.7f;
	private int floorHeight = 65536/32;
	private int floorWidth = 65536/2;
	private int floorLength = 65536;
	
	
	private IntBuffer _scratchIntBuffer;
	private FloatBuffer _scratchFloatBuffer;
	private boolean _scratchB;
	
//	public TextureManager _textureManager;
//	
//	public Object3dContainer carModel = null;
	
    public __Scene(int floorsCount, int carsCount)
    {
    	floors = floorsCount;
    	cars = carsCount;
        int one = 65536;
        int two = one*2;
        int w = floorWidth;
        int h = floorHeight;
        int l = floorLength;
        
        int vertices[] = {
                -l, -h, -w,
                l, -h, -w,
                l,  h, -w,
                -l,  h, -w,
                -l, -h,  w,
                l, -h,  w,
                l,  h,  w,
                -l,  h,  w,
        };

        int colors[] = {
                0,    0,    0,  one,
                one,    0,    0,  one,
                one,  one,    0,  one,
                0,  one,    0,  one,
                0,    0,  one,  one,
                one,    0,  one,  one,
                one,  one,  one,  one,
                0,  one,  one,  one,
        };

        byte indices[] = {
                0, 4, 5,    0, 5, 1,
                1, 5, 6,    1, 6, 2,
                2, 6, 7,    2, 7, 3,
                3, 7, 4,    3, 4, 0,
                4, 7, 6,    4, 6, 5,
                3, 0, 1,    3, 1, 2
        };
        
        int verticesAxeX[] = {
        	two, 0, 0,
        	0, 0, 0,
        };
        
        int verticesAxeY[] = {
            	0, two, 0,
            	0, 0, 0,
        };
        
        int verticesAxeZ[] = {
            	0, 0, two,
            	0, 0, 0,
        };
        
        int colorsAxeX[] = {
        		one, 0, 0, one,
        		one, 0, 0, one,
        };
        
        int colorsAxeY[] = {
        		0, one, 0, one,
        		0, one, 0, one,
        };
        
        int colorsAxeZ[] = {
        		0, 0, one, one,
        		0, 0, one, one,
        };
        
        byte indicesAxes[] = {
        	0, 1,	
        };

        // Buffers to be passed to gl*Pointer() functions
        // must be direct, i.e., they must be placed on the
        // native heap where the garbage collector cannot
        // move them.
        //
        // Buffers with multi-byte datatypes (e.g., short, int, float)
        // must have their byte order set to native order

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asIntBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
        
        
        
        // Axe X buffers
        ByteBuffer vbb0 = ByteBuffer.allocateDirect(verticesAxeX.length*4);
        vbb0.order(ByteOrder.nativeOrder());
        mVertexBufferAxeX = vbb0.asIntBuffer();
        mVertexBufferAxeX.put(verticesAxeX);
        mVertexBufferAxeX.position(0);

        ByteBuffer cbb0 = ByteBuffer.allocateDirect(colorsAxeX.length*4);
        cbb0.order(ByteOrder.nativeOrder());
        mColorBufferAxeX = cbb0.asIntBuffer();
        mColorBufferAxeX.put(colorsAxeX);
        mColorBufferAxeX.position(0);
        
        // Axe Y buffers
        ByteBuffer vbb1 = ByteBuffer.allocateDirect(verticesAxeY.length*4);
        vbb1.order(ByteOrder.nativeOrder());
        mVertexBufferAxeY = vbb1.asIntBuffer();
        mVertexBufferAxeY.put(verticesAxeY);
        mVertexBufferAxeY.position(0);

        ByteBuffer cbb1 = ByteBuffer.allocateDirect(colorsAxeY.length*4);
        cbb1.order(ByteOrder.nativeOrder());
        mColorBufferAxeY = cbb1.asIntBuffer();
        mColorBufferAxeY.put(colorsAxeY);
        mColorBufferAxeY.position(0);
        
        // Axe Z buffers
        ByteBuffer vbb2 = ByteBuffer.allocateDirect(verticesAxeZ.length*4);
        vbb2.order(ByteOrder.nativeOrder());
        mVertexBufferAxeZ = vbb2.asIntBuffer();
        mVertexBufferAxeZ.put(verticesAxeZ);
        mVertexBufferAxeZ.position(0);

        ByteBuffer cbb2 = ByteBuffer.allocateDirect(colorsAxeZ.length*4);
        cbb2.order(ByteOrder.nativeOrder());
        mColorBufferAxeZ = cbb2.asIntBuffer();
        mColorBufferAxeZ.put(colorsAxeZ);
        mColorBufferAxeZ.position(0);
        
        

        mIndexBufferAxes = ByteBuffer.allocateDirect(indicesAxes.length);
        mIndexBufferAxes.put(indicesAxes);
        mIndexBufferAxes.position(0);
        
        
        
//        // for automobile model
//        
//		_scratchIntBuffer = IntBuffer.allocate(4);
//		_scratchFloatBuffer = FloatBuffer.allocate(4);
//		
//		_textureManager = Shared.textureManager();
//		
//		
////		Log.i("parse", ""+Shared.context().getResources()
////				.getIdentifier("raw/camaro_obj", null, null));
//		IParser parser = Parser.createParser(Parser.Type.OBJ,
//				Shared.context().getResources(), "com.parking.model:raw/camaro_obj", true);
//		parser.parse();
//    }
//    
//    private void loadCarModel() {
//		IParser parser = Parser.createParser(Parser.Type.OBJ,
//				Shared.context().getResources(), "com.parking.model:raw/camaro_obj", true);
//		parser.parse();
//		carModel = parser.getParsedObject();
//		carModel.scale().x = carModel.scale().y = carModel.scale().z = .7f;
    }
//    
//	protected void drawObject(Object3d $o, GL10 _gl)
//	{
//		if ($o.isVisible() == false) return;		
//
//		// Various per-object settings:
//		
//		// Normals
//
//		if ($o.hasNormals() && $o.normalsEnabled()) {
//			$o.vertices().normals().buffer().position(0);
//			_gl.glNormalPointer(GL10.GL_FLOAT, 0, $o.vertices().normals().buffer());
//			_gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//		}
//		else {
//			_gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
//		}
//		
//		// Is lighting enabled for object...
//		
//		/*
//		// *** this version not working properly on emulator - why not? ***
//		_scratchIntBuffer.position(0);
//		_gl.glGetIntegerv(GL10.GL_LIGHTING, _scratchIntBuffer);
//		if (useLighting != _scratchIntBuffer.get(0))
//		{
//			if (useLighting == 1) {
//				_gl.glEnable(GL10.GL_LIGHTING);
//			} else {
//				_gl.glDisable(GL10.GL_LIGHTING);
//			}
//		}
//		*/
//		
//		// Enable lighting
//		/*
//		boolean useLighting = ($o.hasNormals() && $o.normalsEnabled() && $o.lightingEnabled());
//		if (useLighting) {
//			_gl.glEnable(GL10.GL_LIGHTING);
//		} else {
//			_gl.glDisable(GL10.GL_LIGHTING);
//		}
//		*/
//		// Shademodel
//		
//		_gl.glGetIntegerv(GL11.GL_SHADE_MODEL, _scratchIntBuffer);
//		if ($o.shadeModel().glConstant() != _scratchIntBuffer.get(0)) {
//			_gl.glShadeModel($o.shadeModel().glConstant());
//		}
//		
//		// Colors: either per-vertex, or per-object
//
//		if ($o.hasVertexColors() && $o.vertexColorsEnabled()) {
//			$o.vertices().colors().buffer().position(0);
//			_gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, $o.vertices().colors().buffer());
//			_gl.glEnableClientState(GL10.GL_COLOR_ARRAY); 
//		}
//		else {
//			_gl.glColor4f(
//				(float)$o.defaultColor().r / 255f, 
//				(float)$o.defaultColor().g / 255f, 
//				(float)$o.defaultColor().b / 255f, 
//				(float)$o.defaultColor().a / 255f
//			);
//			_gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//		}
//		
//		// Colormaterial
//		
//		_gl.glGetIntegerv(GL10.GL_COLOR_MATERIAL, _scratchIntBuffer);
//		_scratchB = (_scratchIntBuffer.get(0) != 0);
//		if ($o.colorMaterialEnabled() != _scratchB) {
//			if ($o.colorMaterialEnabled())
//				_gl.glEnable(GL10.GL_COLOR_MATERIAL);
//			else
//				_gl.glDisable(GL10.GL_COLOR_MATERIAL);
//		}
//		
//		// Point size
//		
//		if ($o.renderType() == RenderType.POINTS) 
//		{
//			if ($o.pointSmoothing()) 
//				_gl.glEnable(GL10.GL_POINT_SMOOTH);
//			else
//				_gl.glDisable(GL10.GL_POINT_SMOOTH);
//			
//			_gl.glPointSize($o.pointSize());
//		}
//
//		// Line properties
//		
//		if ($o.renderType() == RenderType.LINES || $o.renderType() == RenderType.LINE_STRIP || $o.renderType() == RenderType.LINE_LOOP) 
//		{
//			if ( $o.lineSmoothing() == true) {
//				_gl.glEnable(GL10.GL_LINE_SMOOTH);
//			}
//			else {
//				_gl.glDisable(GL10.GL_LINE_SMOOTH);
//			}
//
//			_gl.glLineWidth($o.lineWidth());
//		}
//
//		// Backface culling 
//		
//		if ($o.doubleSidedEnabled()) {
//		    _gl.glDisable(GL10.GL_CULL_FACE);
//		} 
//		else {
//		    _gl.glEnable(GL10.GL_CULL_FACE);
//		}
//		
//
//		drawObject_textures($o, _gl);
//
//		
//		// Matrix operations in modelview
//
//		_gl.glPushMatrix();
//		
//		_gl.glTranslatef($o.position().x, $o.position().y, $o.position().z);
//		
//		_gl.glRotatef($o.rotation().x, 1,0,0);
//		_gl.glRotatef($o.rotation().y, 0,1,0);
//		_gl.glRotatef($o.rotation().z, 0,0,1);
//		
//		_gl.glScalef($o.scale().x, $o.scale().y, $o.scale().z);
//		
//		// Draw
//
//		$o.vertices().points().buffer().position(0);
//		_gl.glVertexPointer(3, GL10.GL_FLOAT, 0, $o.vertices().points().buffer());
//
//		if (! $o.ignoreFaces())
//		{
//			int pos, len;
//			
//			if (! $o.faces().renderSubsetEnabled()) {
//				pos = 0;
//				len = $o.faces().size();
//			}
//			else {
//				pos = $o.faces().renderSubsetStartIndex() * FacesBufferedList.PROPERTIES_PER_ELEMENT;
//				len = $o.faces().renderSubsetLength();
//			}
//
//			$o.faces().buffer().position(pos);
//
//			_gl.glDrawElements(
//					$o.renderType().glValue(),
//					len * FacesBufferedList.PROPERTIES_PER_ELEMENT, 
//					GL10.GL_UNSIGNED_SHORT, 
//					$o.faces().buffer());
//		}
//		else
//		{
//			_gl.glDrawArrays($o.renderType().glValue(), 0, $o.vertices().size());
//		}
//		
//		//
//		// Recurse on children
//		//
//		
//		if ($o instanceof Object3dContainer)
//		{
//			Object3dContainer container = (Object3dContainer)$o;
//			
//			for (int i = 0; i < container.children().size(); i++)
//			{
//				Object3d o = container.children().get(i);
//				drawObject(o, _gl);
//			}
//		}
//		
//		// Restore matrix
//		
//		_gl.glPopMatrix();
//	}
//	
//	private void drawObject_textures(Object3d $o, GL10 _gl)
//	{
//		// iterate thru object's textures
//		
//		for (int i = 0; i < RenderCaps.maxTextureUnits(); i++)
//		{
//			_gl.glActiveTexture(GL10.GL_TEXTURE0 + i);
//			_gl.glClientActiveTexture(GL10.GL_TEXTURE0 + i); 
//
//			if ($o.hasUvs() && $o.texturesEnabled())
//			{
//				$o.vertices().uvs().buffer().position(0);
//				_gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, $o.vertices().uvs().buffer());
//
//				TextureVo textureVo = ((i < $o.textures().size())) ? textureVo = $o.textures().get(i) : null;
//
//				if (textureVo != null)
//				{
//					// activate texture
//					int glId = _textureManager.getGlTextureId(textureVo.textureId);
//					_gl.glBindTexture(GL10.GL_TEXTURE_2D, glId);
//				    _gl.glEnable(GL10.GL_TEXTURE_2D);
//					_gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//					
//					int minFilterType = _textureManager.hasMipMap(textureVo.textureId) ? GL10.GL_LINEAR_MIPMAP_NEAREST : GL10.GL_NEAREST; 
//					_gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, minFilterType);
//					_gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR); // (OpenGL default)
//					
//					// do texture environment settings
//					for (int j = 0; j < textureVo.textureEnvs.size(); j++)
//					{
//						_gl.glTexEnvx(GL10.GL_TEXTURE_ENV, textureVo.textureEnvs.get(j).pname, textureVo.textureEnvs.get(j).param);
//					}
//					
//					// texture wrapping settings
//					_gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, (textureVo.repeatU ? GL10.GL_REPEAT : GL10.GL_CLAMP_TO_EDGE));
//					_gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, (textureVo.repeatV ? GL10.GL_REPEAT : GL10.GL_CLAMP_TO_EDGE));		
//
//					// texture offset, if any
//					if (textureVo.offsetU != 0 || textureVo.offsetV != 0)
//					{
//						_gl.glMatrixMode(GL10.GL_TEXTURE);
//						_gl.glLoadIdentity();
//						_gl.glTranslatef(textureVo.offsetU, textureVo.offsetV, 0);
//						_gl.glMatrixMode(GL10.GL_MODELVIEW); // .. restore matrixmode
//					}
//				}
//				else
//				{
//					_gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
//				    _gl.glDisable(GL10.GL_TEXTURE_2D);
//					_gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//				}
//			}
//			else
//			{
//				_gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
//			    _gl.glDisable(GL10.GL_TEXTURE_2D);
//				_gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			}
//		}
//	}
	

    public void draw(GL10 gl)
    {	        
        gl.glPushMatrix();
        gl.glTranslatef(0, -((floors+1)*betweenFloorsHeight)/2f, 0);
        
        for (int i=0; i<floors; i++) {
        	gl.glTranslatef(0, betweenFloorsHeight, 0);
            gl.glVertexPointer(3 ,GL10.GL_FIXED, 0, mVertexBuffer);
            gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBuffer);
            gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIndexBuffer); 
            
            if (i < (floors-1)) {
	            // Attempt draw floors lifting
	            gl.glPushMatrix();
	            draw_floors_lifting(gl, 1);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();
	            draw_floors_lifting(gl, -1);
	            gl.glPopMatrix();
            }
            
        }
        
        gl.glPopMatrix();
        
        // Axe X
        gl.glVertexPointer(3 ,GL10.GL_FIXED, 0, mVertexBufferAxeX);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBufferAxeX);
        gl.glDrawElements(GL10.GL_LINE_STRIP, 2, GL10.GL_UNSIGNED_BYTE, mIndexBufferAxes);
        
        // Axe Y
        gl.glVertexPointer(3 ,GL10.GL_FIXED, 0, mVertexBufferAxeY);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBufferAxeY);
        gl.glDrawElements(GL10.GL_LINE_STRIP, 2, GL10.GL_UNSIGNED_BYTE, mIndexBufferAxes);
        
        // Axe Z
        gl.glVertexPointer(3 ,GL10.GL_FIXED, 0, mVertexBufferAxeZ);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBufferAxeZ);
        gl.glDrawElements(GL10.GL_LINE_STRIP, 2, GL10.GL_UNSIGNED_BYTE, mIndexBufferAxes);
        
//        if (carModel == null) {
//        	loadCarModel();
//        }
//        drawObject(carModel, gl);
    }
    
    public void draw_floors_lifting(GL10 gl, int side) {  // side = 1 - right, side = -1 - left
        gl.glTranslatef(side, 0, 0);

        int partition = 8;
        double alpha = 0;
        int h = 0;
        double dAlpha = (double)side*180/partition;
        int one = 65536;
        int dh = (int)(one*betweenFloorsHeight/partition);
        
        for (int i=0; i<partition; i++) {
            
            int vertices[] = {
            	(int)((one/2)*Math.sin(alpha*Math.PI/180f)), h-floorHeight, 			  (int)(-side*(one/2)*Math.cos(alpha*Math.PI/180f)),	
            	(int)((one/2)*Math.sin((alpha+dAlpha)*Math.PI/180f)), h-floorHeight+dh, (int)(-side*(one/2)*Math.cos((alpha+dAlpha)*Math.PI/180f)),	
            	(int)((one/2)*Math.sin((alpha+dAlpha)*Math.PI/180f)), h+floorHeight+dh, (int)(-side*(one/2)*Math.cos((alpha+dAlpha)*Math.PI/180f)),		
            	(int)((one/2)*Math.sin(alpha*Math.PI/180f)), h+floorHeight, 			  (int)(-side*(one/2)*Math.cos(alpha*Math.PI/180f)),		
            	
            	(int)((one/8)*Math.sin(alpha*Math.PI/180f)), h-floorHeight, 			  (int)(-side*(one/8)*Math.cos(alpha*Math.PI/180f)),
            	(int)((one/8)*Math.sin((alpha+dAlpha)*Math.PI/180f)), h-floorHeight+dh, (int)(-side*(one/8)*Math.cos((alpha+dAlpha)*Math.PI/180f)),
            	(int)((one/8)*Math.sin((alpha+dAlpha)*Math.PI/180f)), h+floorHeight+dh, (int)(-side*(one/8)*Math.cos((alpha+dAlpha)*Math.PI/180f)),
            	(int)((one/8)*Math.sin(alpha*Math.PI/180f)), h+floorHeight, 			  (int)(-side*(one/8)*Math.cos(alpha*Math.PI/180f)),
       
            	/*
            	(int)((one/4)*Math.sin(alpha*Math.PI/180f)), h, (int)(-(one/4)*Math.cos(alpha*Math.PI/180f)),
            	
            	(int)((one/4)*Math.sin((alpha+dAlpha)*Math.PI/180f)), h+dh, (int)(-(one/4)*Math.cos((alpha+dAlpha)*Math.PI/180f)),
            	
            	(int)((one/2)*Math.sin((alpha+dAlpha)*Math.PI/180f)), h+dh, (int)(-(one/2)*Math.cos((alpha+dAlpha)*Math.PI/180f)),
            	
            	(int)((one/2)*Math.sin(alpha*Math.PI/180f)), h, (int)(-(one/2)*Math.cos(alpha*Math.PI/180f)),
            	*/
            };
            
            /*
        	int colors[] = {
                    0,    0,    0,  one,
                    one,    0,    0,  one,
                    one,  one,    0,  one,
                    0,  one,    0,  one
            };
        	
        	byte indices[] = {
        			0, 1, 2,   2, 3, 0
        	};
        	*/
            int colors[] = {
                    0,    0,    0,  one,
                    one,    0,    0,  one,
                    one,  one,    0,  one,
                    0,  one,    0,  one,
                    0,    0,  one,  one,
                    one,    0,  one,  one,
                    one,  one,  one,  one,
                    0,  one,  one,  one,
            };

            byte indices[] = {
                    0, 4, 5,    0, 5, 1,
                    1, 5, 6,    1, 6, 2,
                    2, 6, 7,    2, 7, 3,
                    3, 7, 4,    3, 4, 0,
                    4, 7, 6,    4, 6, 5,
                    3, 0, 1,    3, 1, 2
            };
            
            ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
            vbb.order(ByteOrder.nativeOrder());
            IntBuffer mVBuffer = vbb.asIntBuffer();
            mVBuffer.put(vertices);
            mVBuffer.position(0);

            ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
            cbb.order(ByteOrder.nativeOrder());
            IntBuffer mCBuffer = cbb.asIntBuffer();
            mCBuffer.put(colors);
            mCBuffer.position(0);

            ByteBuffer mIBuffer = ByteBuffer.allocateDirect(indices.length);
            mIBuffer.put(indices);
            mIBuffer.position(0);
        	
            gl.glVertexPointer(3 ,GL10.GL_FIXED, 0, mVBuffer);
            gl.glColorPointer(4, GL10.GL_FIXED, 0, mCBuffer);
            gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIBuffer);
            
        	h += dh;
        	alpha += dAlpha;
        }
    }
}
