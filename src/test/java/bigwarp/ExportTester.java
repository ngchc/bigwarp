package bigwarp;

import java.io.File;
import java.io.IOException;

import bdv.ij.ApplyBigwarpPlugin;
import bdv.viewer.Interpolation;
import bigwarp.landmarks.LandmarkTableModel;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;

/**
 * Tough to make tests of exporter from scratch.  
 * This is a helper to get there, or at least part of the way there
 * by visually confirming the sanity of exporter results for certain sets of parameters.
 * 
 * @author John Bogovic
 *
 */
public class ExportTester
{

	public static void main( String[] args ) throws IOException
	{
		new ImageJ();

//		ImagePlus impm = IJ.openImage("/groups/saalfeld/home/bogovicj/tmp/mri-stack_p2p2p4.tif");
//		ImagePlus impt = IJ.openImage("/groups/saalfeld/home/bogovicj/tmp/mri-stack.tif");
		
		ImagePlus impt = IJ.openImage("http://imagej.nih.gov/ij/images/mri-stack.zip");
		ImagePlus impm = impt.duplicate();
		IJ.run(impm, "Properties...", "channels=1 slices=27 frames=1 unit=pixel pixel_width=0.2 pixel_height=0.2 voxel_depth=0.4");
		
		LandmarkTableModel landmarks = new LandmarkTableModel( 3 );
		landmarks.load( new File( "src/test/resources/mr_landmarks_p2p2p4-111.csv" ));
		

		/*******************************
		 * fov_res
		 *******************************/
//		v_tgt_tgt( impm, impt, landmarks ); // not interesting
		
//		tgt_tgt( impm, impt, landmarks );
//		tgt_spc( impm, impt, landmarks );
//		tgt_mvg( impm, impt, landmarks );
		
//		mvg_tgt( impm, impt, landmarks );
//		mvg_mvg( impm, impt, landmarks );
//		mvg_spc( impm, impt, landmarks );
		
//		lmk_tgt( impm, impt, landmarks );
//		lmk_mvg( impm, impt, landmarks );
//		lmk_spc( impm, impt, landmarks );
		
		v_spc_spc( impm, impt, landmarks );
		spc_spc( impm, impt, landmarks );
//		pix_spc( impm, impt, landmarks );
		
	}
	
	public static void pix_spc( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.SPECIFIED_PIXEL;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.SPECIFIED;
		double[] resolutionSpec = new double[]{ 0.4, 0.4, 0.8 };
		double[] fovSpec = new double[]{ 125, 125, 13 };
		// test a case in which the offset spec here in pixels (output resolution) is the same
		// as the phyical offset specified in spc_spec
		double[] offsetSpec = new double[]{ 93 / 0.4 ,103 / 0.4, 7 / 0.8 }; 
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "PIX-MOVING" );
		a.show();
	}
	
	public static void v_spc_spc( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.SPECIFIED_PHYSICAL;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.SPECIFIED;
		double[] resolutionSpec = new double[]{ 0.4, 0.4, 0.8 };
		double[] fovSpec = new double[]{ 50, 50, 10};
		double[] offsetSpec = new double[]{ 93 ,103, 7};
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = true;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "VIRT-PHYS-MOVING" );
		a.show();
	}
	
	public static void spc_spc( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.SPECIFIED_PHYSICAL;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.SPECIFIED;
		double[] resolutionSpec = new double[]{ 0.4, 0.4, 0.8 };
		double[] fovSpec = new double[]{ 50, 50, 10};
		double[] offsetSpec = new double[]{ 93 ,103, 7};
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "PHYS-MOVING" );
		a.show();
	}
	
	public static void lmk_mvg( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.LANDMARK_POINTS;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.MOVING;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "LANDMARK-MOVING" );
		a.show();
	}

	public static void lmk_tgt( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.LANDMARK_POINTS;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.TARGET;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "LANDMARK-TARGET" );
		a.show();
	}

	public static void lmk_spc( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.LANDMARK_POINTS;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.SPECIFIED;
		double[] resolutionSpec = new double[]{ 0.4, 0.4, 0.8 };
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "LANDMARK-SPECD" );
		a.show();
	}
	
	
	public static void mvg_mvg( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.MOVING_WARPED;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.MOVING;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "MOVING-MOVING" );
		a.show();
	}
	
	public static void mvg_spc( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.MOVING_WARPED;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.SPECIFIED;
		double[] resolutionSpec = new double[]{ 0.4, 0.4, 0.8 };
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "MOVING-SPECD" );
		a.show();
	}
	
	public static void mvg_tgt( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.MOVING_WARPED;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.TARGET;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "MOVING-TARGET" );
		a.show();
	}	
	
	public static void tgt_tgt( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.TARGET;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.TARGET;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;
		
		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "TARGET-TARGET" );
		a.show();
	}
	
	public static void v_tgt_tgt( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.TARGET;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.TARGET;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = true;
		int nThreads = 4;
		
		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "VIRT-TARGET-TARGET" );
		a.show();
	}
	
	public static void tgt_spc( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.TARGET;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.SPECIFIED;
		double[] resolutionSpec = new double[]{ 0.4, 0.4, 0.8 };
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "TARGET-SPECIFIED" );
		a.show();
	}

	public static void tgt_mvg( ImagePlus impm, ImagePlus impt, LandmarkTableModel landmarks )
	{
		String fieldOfViewOption = ApplyBigwarpPlugin.TARGET;
		String fieldOfViewPointFilter = "";
		String resolutionOption = ApplyBigwarpPlugin.MOVING;
		double[] resolutionSpec = new double[ 3 ];
		double[] fovSpec = new double[ 3 ];
		double[] offsetSpec = new double[ 3 ];
		Interpolation interp = Interpolation.NLINEAR;
		boolean isVirtual = false;
		int nThreads = 4;

		ImagePlus a = ApplyBigwarpPlugin.apply(
			impm,
			impt,
			landmarks,
			fieldOfViewOption,
			fieldOfViewPointFilter,
			resolutionOption,
			resolutionSpec,
			fovSpec,
			offsetSpec,
			interp,
			isVirtual,
			nThreads );

		a.setTitle( "TARGET-MOVING" );
		a.show();
	}
		
	
}
