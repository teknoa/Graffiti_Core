/*******************************************************************************
 * 
 *    Copyright (c) 2003-2009 Plant Bioinformatics Group, IPK Gatersleben
 * 
 *******************************************************************************/
/*
 * Created on Feb 24, 2010 by Christian Klukas
 */

package org.color;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

/**
 * @author klukas
 *
 */
public class ColorUtil {

	@Test
	public void testColorConversion() {
		Color c1 = new Color(120,20,90);
		ColorXYZ cx1 = new ColorXYZ(c1);
		assertEquals(cx1.x, 9.841,  0.001);
		assertEquals(cx1.y, 5.232,  0.001);
		assertEquals(cx1.z, 10.164, 0.001);
		
		c1 = new Color(240,120,0);
		cx1 = new ColorXYZ(c1);
		assertEquals(42.652, cx1.x, 0.001);
		assertEquals(31.958, cx1.y, 0.001);
		assertEquals(3.921, cx1.z, 0.001);
		
		Color_CIE_Lab cie = colorXYZ2CIELAB(cx1);
		assertEquals(63.308, cie.getL(),  0.001);
		assertEquals(40.951, cie.getA(), 0.001);
		assertEquals(70.696, cie.getB(), 0.001);
		
		cx1 = cie.getColorXYZ();
		assertEquals(42.652, cx1.x, 0.001);
		assertEquals(31.958, cx1.y, 0.001);
		assertEquals(3.921, cx1.z, 0.001);
		
		c1 = cx1.getColor();
		assertEquals(240, c1.getRed(), 0.001);
		assertEquals(120, c1.getGreen(), 0.001);
		assertEquals(0, c1.getBlue(), 0.001);
	}
	
	public static ColorXYZ colorRGB2XYZ(double R, double G, double B) {
		double var_R = ( R / 255 );        //R from 0 to 255
		double var_G = ( G / 255 );        //G from 0 to 255
		double var_B = ( B / 255 );        //B from 0 to 255

		if ( var_R > 0.04045 ) var_R = Math.pow(( ( var_R + 0.055 ) / 1.055 ) , 2.4);
		else                   var_R = var_R / 12.92;
		if ( var_G > 0.04045 ) var_G = Math.pow(( ( var_G + 0.055 ) / 1.055 ) , 2.4);
		else                   var_G = var_G / 12.92;
		if ( var_B > 0.04045 ) var_B = Math.pow(( ( var_B + 0.055 ) / 1.055 ) , 2.4);
		else                   var_B = var_B / 12.92;

		var_R = var_R * 100;
		var_G = var_G * 100;
		var_B = var_B * 100;

		//Observer. = 2°, Illuminant = D65
		double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
		double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
		double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;
		return new ColorXYZ(X,Y,Z);
	}
	
	private static Color_CIE_Lab colorXYZ2CIELAB(ColorXYZ XYZ) {
		double X = XYZ.getX();
		double Y = XYZ.getY();
		double Z = XYZ.getZ();
		double ref_X =  95.047;  // Observer= 2°, Illuminant= D65
		double ref_Y = 100.000;
		double ref_Z = 108.883;
		double var_X = X / ref_X;          //
		double var_Y = Y / ref_Y;          //
		double var_Z = Z / ref_Z;          //

		if ( var_X > 0.008856 ) var_X = Math.pow(var_X , ( 1/3d ));
		else                    var_X = ( 7.787 * var_X ) + ( 16 / 116d );
		if ( var_Y > 0.008856 ) var_Y = Math.pow(var_Y , ( 1/3d ));
		else                    var_Y = ( 7.787 * var_Y ) + ( 16 / 116d );
		if ( var_Z > 0.008856 ) var_Z = Math.pow(var_Z , ( 1/3d ));
		else                    var_Z = ( 7.787 * var_Z ) + ( 16 / 116d );

		double CIE_L = ( 116 * var_Y )- 16;
		double CIE_a = 500 * ( var_X - var_Y );
		double CIE_b = 200 * ( var_Y - var_Z ); 
		return new Color_CIE_Lab(CIE_L, CIE_a, CIE_b);
	}
	
	public static double deltaE2000(Color c1, Color c2) {
		Color_CIE_Lab cCL1 = colorXYZ2CIELAB(colorRGB2XYZ(c1.getRed(), c1.getGreen(), c1.getBlue()));
		Color_CIE_Lab cCL2 = colorXYZ2CIELAB(colorRGB2XYZ(c2.getRed(), c2.getGreen(), c2.getBlue()));
		double CIE_L1 = cCL1.getL();
		double CIE_a1 = cCL1.getA();
		double CIE_b1 = cCL1.getB();          //Color #1 CIE-L*ab values
		
		double CIE_L2 = cCL2.getL();
		double CIE_a2 = cCL2.getA();
		double CIE_b2 = cCL2.getB();          //Color #2 CIE-L*ab values
		
		double WHT_L = 1;
		double WHT_C = 1;
		double WHT_H = 1;	                 //Wheight factor
	
		double xC1 = Math.sqrt( CIE_a1 * CIE_a1 + CIE_b1 * CIE_b1 );
		double xC2 = Math.sqrt( CIE_a2 * CIE_a2 + CIE_b2 * CIE_b2 );
		double xCX = ( xC1 + xC2 ) / 2;
		double xGX = 0.5 * ( 1 - Math.sqrt( ( Math.pow(xCX,7) ) / ( ( Math.pow(xCX,7) ) + ( Math.pow(25,7) ) ) ) );
		double xNN = ( 1 + xGX ) * CIE_a1;
				 xC1 = Math.sqrt( xNN * xNN + CIE_b1 * CIE_b1 );
		double xH1 = CieLab2Hue( xNN, CIE_b1 );
				 xNN = ( 1 + xGX ) * CIE_a2;
				 xC2 = Math.sqrt( xNN * xNN + CIE_b2 * CIE_b2 );
		double xH2 = CieLab2Hue( xNN, CIE_b2 );
		double xDL = CIE_L2 - CIE_L1;
		double xDC = xC2 - xC1;
		double xDH;
		if ( ( xC1 * xC2 ) == 0 ) {
		   xDH = 0;
		}
		else {
		   xNN = xH2 - xH1; // round( xH2 - xH1, 12 )
		   if ( Math.abs( xNN ) <= 180 ) {
		      xDH = xH2 - xH1;
		   }
		   else {
		      if ( xNN > 180 ) xDH = xH2 - xH1 - 360;
		      else             xDH = xH2 - xH1 + 360;
		   }
		}
		xDH = 2 * Math.sqrt( xC1 * xC2 ) * Math.sin( dtor( xDH / 2 ) );
		double xLX = ( CIE_L1 + CIE_L2 ) / 2;
		double xCY = ( xC1 + xC2 ) / 2;
		double xHX;
		if ( ( xC1 *  xC2 ) == 0 ) {
		   xHX = xH1 + xH2;
		}
		else {
		   xNN = Math.abs(xH1 - xH2); // abs( round( xH1 - xH2, 12 ) )
		   if ( xNN >  180 ) {
		      if ( ( xH2 + xH1 ) <  360 ) xHX = xH1 + xH2 + 360;
		      else                        xHX = xH1 + xH2 - 360;
		   }
		   else {
		      xHX = xH1 + xH2;
		   }
		   xHX /= 2;
		}
		double xTX = 1 - 0.17 * Math.cos( dtor( xHX - 30 ) ) + 0.24
		               * Math.cos( deg2rad( 2 * xHX ) ) + 0.32
		               * Math.cos( deg2rad( 3 * xHX + 6 ) ) - 0.20
		               * Math.cos( dtor( 4 * xHX - 63 ) );
		double xPH = 30 * Math.exp( - ( ( xHX  - 275 ) / 25 ) * ( ( xHX  - 275 ) / 25 ) );
		double xRC = 2 * Math.sqrt( ( Math.pow(xCY, 7) ) / ( ( Math.pow(xCY, 7) ) + ( Math.pow(25, 7) ) ) );
		double xSL = 1 + ( ( 0.015 * ( ( xLX - 50 ) * ( xLX - 50 ) ) )
		        / Math.sqrt( 20 + ( ( xLX - 50 ) * ( xLX - 50 ) ) ) );
		double xSC = 1 + 0.045 * xCY;
		double xSH = 1 + 0.015 * xCY * xTX;
		double xRT = - Math.sin( deg2rad( 2 * xPH ) ) * xRC;
				 xDL = xDL / ( WHT_L * xSL );
				 xDC = xDC / ( WHT_C * xSC );
				 xDH = xDH / ( WHT_H * xSH );
		double Delta_E00 = Math.sqrt( xDL*xDL + xDC*xDC + xDH*xDH + xRT * xDC * xDH );
		return Delta_E00;

	}

	private static double deg2rad(double d) {
		return dtor(d);
	}

	/**
	 * @return degree to radian
	 */
	private static double dtor(double deg) {
		return deg/180*Math.PI;
	}

	private static double CieLab2Hue(double var_a, double var_b)          //Function returns CIE-H° value
	{
	   double var_bias = 0;
	   if ( var_a >= 0 && var_b == 0 ) return 0;
	   if ( var_a <  0 && var_b == 0 ) return 180;
	   if ( var_a == 0 && var_b >  0 ) return 90;
	   if ( var_a == 0 && var_b <  0 ) return 270;
	   if ( var_a >  0 && var_b >  0 ) var_bias = 0;
	   if ( var_a <  0               ) var_bias = 180;
	   if ( var_a >  0 && var_b <  0 ) var_bias = 360;
	   return ( rad2deg( Math.atan( var_b / var_a ) ) + var_bias );
	}

	private static double rad2deg(double rad) {
		return rad/Math.PI*180;
	}	
}