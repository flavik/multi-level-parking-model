package com.parking.model;


import com.parking.model.min3d.Utils;
import com.parking.model.min3d.core.Object3dContainer;
import com.parking.model.min3d.vos.Color4;


/**
 * Note how each 'face' (quad) of the box uses its own set of 4 vertices each, 
 * rather than sharing with adjacent faces.  This allows for each face to be 
 * texture mapped, normal'ed, and colored independently of the others. 
 * 
 * Object origin is center of box. 
 */
public class Lift extends Object3dContainer
{
	private Color4[] _cols;
	private float _width;
	private float _height;
	private float _heightEl;
	private static int partition = 16;

	public Lift(float $width, float $height, float $heightEl, Color4[] $sixColor4s, Boolean $useUvs, Boolean $useNormals, Boolean $useVertexColors)
	{
		super(0, 0, false, false, false);
		
		_width = $width;
		_height = $height;
		_heightEl = $heightEl;
		
		if ($sixColor4s != null)
		{
			_cols = $sixColor4s;
		}
		else
		{
			_cols = new Color4[6];
			_cols[0] = new Color4(103,111,140,255);
			_cols[1] = new Color4(156,161,177,255);
			_cols[2] = new Color4(103,111,140,255);
			_cols[3] = new Color4(156,161,177,255);
			_cols[4] = new Color4(167,200,201,255);
			_cols[5] = new Color4(167,200,201,255);
		}

		float a = 0;
        float h = -_heightEl/2f;
        float da = 180.0f/partition;
        float dh = _height/partition;

		for (int i=0; i<partition; i++) {
			Object3dContainer obj = new Object3dContainer(4*6, 2*6, true, true, true);
			obj.lightingEnabled(false);
			make(obj, a, da, h, dh);
			addChild(obj);
        	h += dh;
        	a += da;
		}
	}
	
	public Lift(float $width, float $height, float $heightEl, Color4[] $sixColor4s)
	{
		this($width,$height,$heightEl,$sixColor4s, true,true,true);
	}
	
	public Lift(float $width, float $height, float $heightEl, Color4 color)
	{
		this($width,$height,$heightEl, new Color4[] { color, color, color, color, color, color }, true, true, true);
	}

	public Lift(float $width, float $height, float $heightEl)
	{
		this($width, $height, $heightEl, null, true, true, true);
	}

	private void make(Object3dContainer obj, float a, float da, float h, float dh)
	{
		short ul, ur, lr, ll;
		
		// front
    	ul = obj.vertices().addVertex((float)((_width/2)*Math.sin(a*Math.PI/180f)), h, (float)(-(_width/2)*Math.cos(a*Math.PI/180f)), 
    			0f,0f,		0,0,1,		_cols[0].r,_cols[0].g,_cols[0].b,_cols[0].a);
    	ur = obj.vertices().addVertex((float)((_width/2)*Math.sin((a+da)*Math.PI/180f)), h+dh, (float)(-(_width/2)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,0f,		0,0,1, 	_cols[0].r,_cols[0].g,_cols[0].b,_cols[0].a); 	
    	lr = obj.vertices().addVertex((float)((_width/2)*Math.sin((a+da)*Math.PI/180f)), h+_heightEl+dh, (float)(-(_width/2)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,1f,		0,0,1, 	_cols[0].r,_cols[0].g,_cols[0].b,_cols[0].a);
    	ll = obj.vertices().addVertex((float)((_width/2)*Math.sin(a*Math.PI/180f)), h+_heightEl, (float)(-(_width/2)*Math.cos(a*Math.PI/180f)), 
    			0f,1f,		0,0,1, 	_cols[0].r,_cols[0].g,_cols[0].b,_cols[0].a);
    	Utils.addQuad(obj, ul,ur,lr,ll);
		
		// right
    	ll = obj.vertices().addVertex((float)((_width/5)*Math.sin((a+da)*Math.PI/180f)), h+dh, (float)(-(_width/5)*Math.cos((a+da)*Math.PI/180f)), 
    			0f,0f,		1,0,0, 	_cols[1].r,_cols[1].g,_cols[1].b,_cols[1].a); 	
    	lr = obj.vertices().addVertex((float)((_width/2)*Math.sin((a+da)*Math.PI/180f)), h+dh, (float)(-(_width/2)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,0f,		1,0,0, 	_cols[1].r,_cols[1].g,_cols[1].b,_cols[1].a); 	
    	ur = obj.vertices().addVertex((float)((_width/2)*Math.sin((a+da)*Math.PI/180f)), h+_heightEl+dh, (float)(-(_width/2)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,1f,		1,0,0, 	_cols[1].r,_cols[1].g,_cols[1].b,_cols[1].a);
    	ul = obj.vertices().addVertex((float)((_width/5)*Math.sin((a+da)*Math.PI/180f)), h+_heightEl+dh, (float)(-(_width/5)*Math.cos((a+da)*Math.PI/180f)), 
    			0f,1f,		1,0,0, 	_cols[1].r,_cols[1].g,_cols[1].b,_cols[1].a);
    	Utils.addQuad(obj, ul,ur,lr,ll);

		// back
    	ll = obj.vertices().addVertex((float)((_width/5)*Math.sin(a*Math.PI/180f)), h, (float)(-(_width/5)*Math.cos(a*Math.PI/180f)), 
    			0f,0f,		0,0,-1,		_cols[2].r,_cols[2].g,_cols[2].b,_cols[2].a);
    	lr = obj.vertices().addVertex((float)((_width/5)*Math.sin((a+da)*Math.PI/180f)), h+dh, (float)(-(_width/5)*Math.cos((a+da)*Math.PI/180f)), 
    			0f,0f,		0,0,-1, 	_cols[2].r,_cols[2].g,_cols[2].b,_cols[2].a); 	
    	ur = obj.vertices().addVertex((float)((_width/5)*Math.sin((a+da)*Math.PI/180f)), h+_heightEl+dh, (float)(-(_width/5)*Math.cos((a+da)*Math.PI/180f)), 
    			0f,0f,		0,0,-1, 	_cols[2].r,_cols[2].g,_cols[2].b,_cols[2].a);
    	ul = obj.vertices().addVertex((float)((_width/5)*Math.sin(a*Math.PI/180f)), h+_heightEl, (float)(-(_width/5)*Math.cos(a*Math.PI/180f)), 
    			0f,0f,		0,0,-1, 	_cols[2].r,_cols[2].g,_cols[2].b,_cols[2].a);
    	Utils.addQuad(obj, ul,ur,lr,ll);
	
		// left
    	ul = obj.vertices().addVertex((float)((_width/5)*Math.sin(a*Math.PI/180f)), h, (float)(-(_width/5)*Math.cos(a*Math.PI/180f)), 
    			0f,0f,		-1,0,0,		_cols[3].r,_cols[3].g,_cols[3].b,_cols[3].a);
    	ur = obj.vertices().addVertex((float)((_width/2)*Math.sin(a*Math.PI/180f)), h, (float)(-(_width/2)*Math.cos(a*Math.PI/180f)), 
    			1f,0f,		-1,0,0,		_cols[3].r,_cols[3].g,_cols[3].b,_cols[3].a);
    	lr = obj.vertices().addVertex((float)((_width/2)*Math.sin(a*Math.PI/180f)), h+_heightEl, (float)(-(_width/2)*Math.cos(a*Math.PI/180f)), 
    			1f,1f,		-1,0,0, 	_cols[3].r,_cols[3].g,_cols[3].b,_cols[3].a);
    	ll = obj.vertices().addVertex((float)((_width/5)*Math.sin(a*Math.PI/180f)), h+_heightEl, (float)(-(_width/5)*Math.cos(a*Math.PI/180f)), 
    			0f,1f,		-1,0,0, 	_cols[3].r,_cols[3].g,_cols[3].b,_cols[3].a);
		Utils.addQuad(obj, ul,ur,lr,ll);
		
		// top
    	ul = obj.vertices().addVertex((float)((_width/2)*Math.sin(a*Math.PI/180f)), h+_heightEl, (float)(-(_width/2)*Math.cos(a*Math.PI/180f)), 
    			0f,0f,		0,1,0, 	_cols[4].r,_cols[4].g,_cols[4].b,_cols[4].a);
    	ur = obj.vertices().addVertex((float)((_width/2)*Math.sin((a+da)*Math.PI/180f)), h+_heightEl+dh, (float)(-(_width/2)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,0f,		0,1,0, 	_cols[4].r,_cols[4].g,_cols[4].b,_cols[4].a);
    	lr = obj.vertices().addVertex((float)((_width/5)*Math.sin((a+da)*Math.PI/180f)), h+_heightEl+dh, (float)(-(_width/5)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,1f,		0,1,0, 	_cols[4].r,_cols[4].g,_cols[4].b,_cols[4].a);
    	ll = obj.vertices().addVertex((float)((_width/5)*Math.sin(a*Math.PI/180f)), h+_heightEl, (float)(-(_width/5)*Math.cos(a*Math.PI/180f)), 
    			0f,1f,		0,1,0, 	_cols[4].r,_cols[4].g,_cols[4].b,_cols[4].a);
		Utils.addQuad(obj, ul,ur,lr,ll);

		// bottom
    	ll = obj.vertices().addVertex((float)((_width/2)*Math.sin(a*Math.PI/180f)), h, (float)(-(_width/2)*Math.cos(a*Math.PI/180f)), 
    			0f,0f,		0,-1,0, 	_cols[5].r,_cols[5].g,_cols[5].b,_cols[5].a);
    	lr = obj.vertices().addVertex((float)((_width/2)*Math.sin((a+da)*Math.PI/180f)), h+dh, (float)(-(_width/2)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,0f,		0,-1,0, 	_cols[5].r,_cols[5].g,_cols[5].b,_cols[5].a);
    	ur = obj.vertices().addVertex((float)((_width/5)*Math.sin((a+da)*Math.PI/180f)), h+dh, (float)(-(_width/5)*Math.cos((a+da)*Math.PI/180f)), 
    			1f,1f,		0,-1,0, 	_cols[5].r,_cols[5].g,_cols[5].b,_cols[5].a);
    	ul = obj.vertices().addVertex((float)((_width/5)*Math.sin(a*Math.PI/180f)), h, (float)(-(_width/5)*Math.cos(a*Math.PI/180f)), 
    			0f,1f,		0,-1,0, 	_cols[5].r,_cols[5].g,_cols[5].b,_cols[5].a);
		Utils.addQuad(obj, ul,ur,lr,ll);
	}
}
