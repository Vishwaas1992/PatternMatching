package vidProc;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;
import org.opencv.features2d.Features2d;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class vidProc extends Thread
{

	/*----------------------------------DEVELOPER OPTIONS---------------------------------------------------*/

	double param = 1.7 ; // sift filtering parameter- param ~[1,3], best observed 10 for real time value=1.7
	int img_comp_area = 250000 ; // parameter to compress the image to an area of 250000 if image is too big
	int temp_comp_area = 40000; // parameter to compress the template to an area of 40000 if template is too big
	double angle_tol = 30; // objects which fall in the angle (directed from templ to obj) if less than angle_tol are considered to be a single obj	
	double def_rtrn_scl = 1;	// default return value if the no perfect scale is found
	double blur_img = 0.1; // img blur fator blur ~[0,6] 1
	double blur_templ =1.3; // template blur factor ~[0,6] 0.1
	boolean mon_only_chge = true;  
	int count=1;
	Mat templ;
	boolean flag=true;
	Mat out_templ = new Mat();
	String text="";
	double no_of_mtchs = 0;
	String match_found="";
	int X=0;
	int Y=0;
	String coord="";
	int prog=0;
	String inImg="";
	String inTempl="";
	boolean in_ok=true;
	boolean stop=false;


	/*-----------------------------------USER OPTIONS------------------------------------------------------------*/
	boolean is_vid = true;  //if it is a video this parameter is set to true, else false
	boolean real_time = !true; //if it is a real time video this parameter is set to true, else false
	boolean find_n = !true;  //if user wishes to find the number of matches this is set to true, else false
	boolean text_templ=!true;  //if user wishes to find text in an image this is set to true
	boolean get_coordinates=true; //if user wishes to find the coordinates of match on the image this is set to true

	/*---------------------------------ADDITIONAL USER OPTIONS-----------------------------------------------------*/

	double tol = 70; // tolerance to distance between 2 detected objects
	// ~[30,80]
	int n_angles =1 ; // number of angles to be considered - more the better but
	// more time is consumed
	boolean show_out_img = true; // true-shows output img
	double strt_scl = 0.1; // sets the starting scale of the template to 0.2
	// times the size of the template
	double scl_step_sze = 0.2; // increments scale in steps of 0.05 times the
	// size of the template
	double corr_thr = 0.3; // The values of correlation coefficients above corr_thr are considered corr_thr ~ < 0.4
	int smpl_at = 10; // sample at a every 50th(here) frame, decreasing smpl_at will increase sampling rate and vice-versa. smpl_at cannot be greater than
	// (frames/sec)x(duration of the video)


	/*---------------------------------------------------------------------------------------------------------------*/

	public vidProc(String inImg, String inTempl)
	{
		this.inImg=inImg;
		this.inTempl=inTempl;
		
	}

	public void run()
	{
		objDetect.out_btn.setEnabled(false);
		objDetect.submit.setEnabled(false);
		System.gc();


		VideoCapture vid = new VideoCapture(inImg);

		set_config(is_vid, real_time,find_n,text_templ,get_coordinates);


		if (real_time)
		{
			vid = new VideoCapture(0);

		}
		

		// read template and main image to Matrices
		Mat img = new Mat();
		if(flag)
		{
			templ = new Mat(1000,1000,CvType.CV_32F);
		}


		if(is_vid&&(!real_time))
		{
			if(smpl_at>vid.get(7))
			{
				JOptionPane.showMessageDialog(null,"'Sample at' cannot be greater than "+vid.get(7)+" !!");
				in_ok=false;
			}
		}

		if((text_templ==true)&&flag)
		{

			Mat out=new Mat(300,text.length()*190+4*(text.length()-1),CvType.CV_16UC3,new Scalar(255,255,255));
			Point pt=new Point(1,250);
		    Core.putText(out, text, pt, Core.FONT_HERSHEY_SIMPLEX, 10, new Scalar(0,0,0),35);
		    out.convertTo(templ,CvType.depth(16));
			
		   
			out.release();
			
		}
		if(text_templ==false)
		{

			templ=Highgui.imread(inTempl);
		}	
		flag=false;

		// check if img is empty

		if (!is_vid)
		{
			img = Highgui.imread(inImg);

			if (img.empty())
			{
				JOptionPane.showMessageDialog(null,"Image could not be found !!");
				in_ok=false;
			}
		}

		// check if template is empty

		if (templ.empty())
		{
			JOptionPane.showMessageDialog(null,"Template could not be found !!");
			in_ok=false;
		}

		// check if the number of angles given is equal to or less than zero


		if (is_vid && !vid.isOpened())
		{
			JOptionPane.showMessageDialog(null,"Video could not be found !!");
			in_ok=false;
		}// if all parameters are okay proceed

		objDetect.progressBar.setValue(prog++);

		double no_of_frms = 1;

		if (is_vid && !real_time)
		{
			no_of_frms = vid.get(7);
		}

		if (real_time)
			no_of_frms = 50;

		double spls = -1;
		int sl_no=1;

		

		if(in_ok)
			for (int t = 0; t < no_of_frms; t++)
			{

				if(stop)
					break;
				// create new matrices for image and template

				if (is_vid)
				{
					vid.read(img);
					Imgproc.GaussianBlur(img, img, new Size(1, 1),blur_img);
				}

				if(t%(smpl_at)==0)
				{

					Mat out_img = new Mat();

					objDetect.progressBar.setValue(prog++);

					img.copyTo(out_img);
					templ.copyTo(out_templ);


					// resize img if too big
					if(find_n||text_templ)
					{
						for (double k = 1; !is_compressed(out_img, true); k = k + 0.1)
							Imgproc.resize(img, out_img,new Size(img.cols() / k, img.rows() / k));

						// resize templ if too big

						for (double k = 1; !is_compressed(out_templ, false); k = k + 0.1)
							Imgproc.resize(templ, out_templ, new Size(templ.cols() / k,templ.rows() / k));
					}
					// blur the template to remove grains in the image
					if(!text_templ)
					Imgproc.GaussianBlur(out_templ, out_templ, new Size(0, 0),blur_templ);

					// release the image and template matrices - this will free the
					// memory
					
					// if user wants to find the NUMBER of matches find_n=true else
					// false

					if (find_n == true)
					{
						
						// calculate the hypotenuse of the templ d=sqrt(rows^2+cols^2)

						// get the scaling factor k - scaling factor is the most
						// probable scale found for the template found in the image
						double k = getK(out_img, out_templ);

						
						// resize img according to the best probable scale value k -
						// new_img_size= img_size/k;
						Imgproc.resize(out_img, out_img, new Size(out_img.cols() / k,out_img.rows() / k));


						Mat img1 = new Mat();

						/*
						 * apply blur according to the value k if k>1 then img is scaled
						 * down hence less blur is reqd if k<1 img is scaled up hence
						 * img sharpening is reqd k>1 implies that the matching object
						 * found in the image is bigger than the template image size
						 * hence image should be scaled down and vice versa
						 */

						if (k > 1)
							Imgproc.GaussianBlur(out_img, out_img, new Size(0, 0), 0.1);

						else
						{
							Imgproc.GaussianBlur(out_img, img1, new Size(0, 0), 0.1);
							Core.addWeighted(out_img, 1.5, img1, -0.5, 0, img1);
							Imgproc.GaussianBlur(img1, img1, new Size(0, 0), blur_img);
							img1.copyTo(out_img);
						}

					}

					// calculate the diagonal length of the template

					double diag = Math.sqrt(Math.pow(out_templ.rows(), 2)+ Math.pow(out_templ.cols(), 2));

					// calc the approximate size of objects found in the img
					// diag_tol is the new diagoanl length obtained after subtracting a
					// given tolerance

					double diag_tol = (diag) - (diag * tol/ 100);

					Mat out = new Mat();

					objDetect.progressBar.setValue(prog++);
					// to store keypoints in both the images
					
					
					MatOfKeyPoint mkp_templ = new MatOfKeyPoint();
					MatOfKeyPoint mkp_img = new MatOfKeyPoint();

					// get features
					FeatureDetector siftkey = FeatureDetector.create(FeatureDetector.SIFT);

					objDetect.progressBar.setValue(prog++);

					// store detected keypoints
					siftkey.detect(out_templ, mkp_templ);
					objDetect.progressBar.setValue(prog++);

					siftkey.detect(out_img, mkp_img);



					Mat des_img = new Mat();
					Mat des_templ = new Mat();

					// get descriptors
					DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);



					// compute descriptions for the obtained set of keypoints
					de.compute(out_templ, mkp_templ, des_templ);
					objDetect.progressBar.setValue(prog++);

					de.compute(out_img, mkp_img, des_img);

					// Mat to store the matching keypoints
					MatOfDMatch mdm = new MatOfDMatch();


					DescriptorMatcher desm = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);

					// obtain the matching key points in both the images
					desm.match(des_img, des_templ, mdm);
					objDetect.progressBar.setValue(prog++);

					// filter the matches to obtain goodmatches

					double max_dist = 0;
					double min_dist = 100;



					for (int i = 0; i < des_img.rows(); i++)
					{
						if(i%250==0)
							objDetect.progressBar.setValue(prog++);

						double dist = mdm.toList().get(i).distance;
						if (dist < min_dist)
							min_dist = dist;
						if (dist > max_dist)
							max_dist = dist;

					}

					double score = 0;
					List<DMatch> dm = new ArrayList<DMatch>();

					for (int i = 0; i < des_img.rows(); i++)
					{
						if(i%250==0)
							objDetect.progressBar.setValue(prog++);


						if (mdm.toList().get(i).distance <= Math.max(param * min_dist,0.02))
							dm.add(mdm.toList().get(i));
						score = score + mdm.toList().get(i).distance;
					}
					MatOfDMatch goodmatches = new MatOfDMatch();



					goodmatches.fromList(dm);


					
					// draws matches and shows output img if show_out_img=true
					if (show_out_img)
					{
						if(!goodmatches.empty())
						{
							Features2d.drawMatches(out_img, mkp_img, out_templ, mkp_templ,
									goodmatches, out);
							objDetect.progressBar.setValue(prog++);

							double r=out.rows();
							double c=out.cols();

							if(out.rows()>600)
								r=600;
							if(out.cols()>1100)
								c=1100;
							Imgproc.resize(out, out, new Size(c,r));

							MatOfByte mb=new MatOfByte();  

							Highgui.imencode(".jpg", out,mb);

							BufferedImage imag = new BufferedImage(1, 1, 1);
							try {
								imag=ImageIO.read(new ByteArrayInputStream(mb.toArray()));
							} catch (IOException e) {
								// 
								e.printStackTrace();
							}
							ImageIcon icon = new ImageIcon(imag);
							objDetect.out_frm=new JFrame();
							objDetect.out_frm.setSize(1300, 800);
							JLabel label = new JLabel(icon);

							objDetect.out_frm.add(label);
						}
					}
					if(!goodmatches.empty())
						match_found="Match Found !!";
					else
						match_found="No Match Found !!";


					List<KeyPoint> lokpm = new ArrayList<KeyPoint>();
					List<KeyPoint> lokpt = new ArrayList<KeyPoint>();

					lokpm = mkp_img.toList();
					lokpt = mkp_templ.toList();

					if(!goodmatches.empty())
					{
						X=(int)lokpm.get(goodmatches.toList().get(0).queryIdx).pt.x;
						Y=(int)lokpm.get(goodmatches.toList().get(0).queryIdx).pt.y;
					}

					if (find_n)
					{

						double slopes[] = new double[(int) goodmatches.size().height]; // to

						double distance[] = new double[(int) goodmatches.size().height]; // to


						for (int i = 0; i < goodmatches.size().height; i++)
						{
							objDetect.progressBar.setValue(prog++);


							double tx = (out_img.cols() + lokpt.get(goodmatches.toList().get(i).trainIdx).pt.x);
							double ty = (lokpt.get(goodmatches.toList().get(i).trainIdx).pt.y);
							double qx = (lokpm.get(goodmatches.toList().get(i).queryIdx).pt.x);
							double qy = (lokpm.get(goodmatches.toList().get(i).queryIdx).pt.y);

							slopes[i] = ((qy - ty) / (tx - qx)); // slope= Y/X
							distance[i] = Math.sqrt((Math.pow((qy - ty), 2))+ (Math.pow((qx - tx), 2)));


						}



						int count = 0;

						// eliminate the lines (keeping one of them) having angles
						// between them less than angle_tol and distance less than
						// diag_tol
						// i.e consider close and almost parallel lines to be pointing
						// at the same object
						for (int i = 0; i < slopes.length; i++)
						{

							for (int j = i + 1; j < slopes.length; j++)
							{
								objDetect.progressBar.setValue(prog++);

								if ((Math.abs(((int) (180 * (Math.atan(slopes[i])) / Math.PI))- ((int) (180 * (Math.atan(slopes[j])) / Math.PI))) < angle_tol))
								{
									if (Math.sqrt(Math.pow(distance[i], 2)+ Math.pow(distance[j], 2)- 2* distance[i]* distance[j]* Math.cos(Math.atan(slopes[i])- Math.atan(slopes[j]))) < diag_tol)
									{
										slopes[i] = 0;
										distance[i] = 0;
										count++;
										break;
									}
								}
							}
						}


						int h=1;

						if(get_coordinates)
						{

							for (int i = 0; i < slopes.length; i++)
							{
								if (slopes[i] != 0)
								{
									X=(int)lokpm.get(goodmatches.toList().get(h-1).queryIdx).pt.x;
									Y=(int)lokpm.get(goodmatches.toList().get(h-1).queryIdx).pt.y;

									coord=coord+"("+X+","+Y+")"+" ";

									h++;
								}
							}
						}

						no_of_mtchs = slopes.length - count;

					}

					else
					{
						if(!goodmatches.empty()&&!is_vid)
						{
							//objDetect.model.addRow(new Object[]{sl_no, match_found});

						}
						no_of_mtchs = goodmatches.height();
					}
					if (is_vid)
					{
						if(real_time)
						{

							if (find_n)
								objDetect.model.addRow(new Object[]{sl_no++, match_found,no_of_mtchs,"",coord});
							else 
							{

								if (!goodmatches.empty())
								{
									objDetect.model.addRow(new Object[]{sl_no++, match_found,"",""});

									spls = no_of_mtchs;
								}
								else
								{
									//objDetect.model.addRow(new Object[]{sl_no, match_found,no_of_mtchs});


								}

							}

						}
						if(!real_time)
						{

							if (!mon_only_chge)
								spls = -1;

							if ((no_of_mtchs != spls))
							{
								double time = (double) (t) / vid.get(5);
								int milsec = (int) ((time - Math.floor(time)) * 1000);
								int sec = (int) (((time / 60) - Math.floor(time / 60)) * 60);
								int min = (int) (Math.floor(time / 60));
								int hr = (int) (Math.floor(min / 60));
								String hour = null;
								String minute = null;
								String second = null;

								if (("0" + hr).length() == 3)
									hour = "" + hr;
								else
									hour = "0" + hr;
								if (("0" + min).length() == 3)
									minute = "" + min;
								else
									minute = "0" + min;
								if (("0" + sec).length() == 3)
									second = "" + sec;
								else
									second = "0" + sec;

								if (find_n)
								{
									objDetect.model.addRow(new Object[]{sl_no++, match_found,no_of_mtchs, hour + ":" + minute+ ":" + second + ":" + milsec,coord});


								}
								else 
								{
									if (!goodmatches.empty())
									{						
										objDetect.model.addRow(new Object[]{sl_no++, match_found,"", hour + ":" + minute+ ":" + second + ":" + milsec});


										spls = no_of_mtchs;
									}
									else
									{
										//
										objDetect.model.addRow(new Object[]{sl_no++, match_found});


									}

								}
							}
						}


					}
					else
					{
						if(find_n==true)
						{
							objDetect.model.addRow(new Object[]{sl_no++, match_found,no_of_mtchs,"",coord});

						}
						else
						{
							if(goodmatches.empty())
							{

								objDetect.model.addRow(new Object[]{sl_no++, match_found,""});

							}
							else
							{
								objDetect.model.addRow(new Object[]{sl_no++, match_found});

							}
						}
					}
					coord="";	
					objDetect.progressBar.setValue(prog++);
					if(is_vid||real_time)
					{
						if(prog>100)
							prog=0;
					}

			}
		count++;

		
		objDetect.out_btn.setEnabled(true);

		objDetect.progressBar.setValue(100);
		
					}

		vid.release();
		objDetect.out_btn.setEnabled(true);
		objDetect.submit.setEnabled(true);
			
	}
	static double pos;


	double getK(Mat img, Mat in_templ)
	{
		boolean flag = false;
		int n = 0;
		// to store templates of different scales
		double[] sizes = new double[(int) (Math.ceil(Math.max((double) img.cols() / in_templ.cols(), (double) img.rows()/ in_templ.rows())) / scl_step_sze)];

		Mat result = new Mat(img.rows(), img.cols(), CvType.CV_32FC1);

		Mat templ = new Mat();
		Mat a_img = new Mat();

		double mx_rptd_sze[] = new double[n_angles];
		double mx_rptd_pos[] = new double[n_angles];
		
		
		for (int a = 0; a < n_angles; a++)
		{
			n = 0;
			Point pt = new Point(img.cols() / 2, img.rows() / 2);
			Mat r = Imgproc.getRotationMatrix2D(pt, (360 / n_angles) * a, 1.0);
			Imgproc.warpAffine(img, a_img, r, new Size(img.cols(), img.rows()));

			for (double k = strt_scl; k <= ((double) img.cols() / in_templ.cols()) && k <= ((double) img.rows() / in_templ.rows()); k = k+ scl_step_sze)
			{
				
				if(stop)
					break;
				
				objDetect.progressBar.setValue(prog++);

				Imgproc.resize(in_templ, templ, new Size(in_templ.cols() * k,in_templ.rows() * k));

				Imgproc.matchTemplate(a_img, templ, result,	Imgproc.TM_CCOEFF_NORMED);

				// get highest correlation co-efficient. and filter the ones
				// above 0.3
				
				MinMaxLocResult mmr = Core.minMaxLoc(result);
				
			
				if (mmr.maxVal > corr_thr)
				{
					flag = true;
					sizes[n] = mmr.maxVal;

				} 
				else
				{					
					sizes[n] = 0;
				}

				n++;
				
				
			}
			mx_rptd_sze[a] = getMax(sizes);
			mx_rptd_pos[a] = pos;
			
		}

		getMax(mx_rptd_sze);

		double best_size = strt_scl + mx_rptd_pos[(int) pos] * (scl_step_sze);

		
		Point pt = new Point(in_templ.cols() / 2, in_templ.rows() / 2);
		Mat r = Imgproc.getRotationMatrix2D(pt, (360 / n_angles) * pos, 1.0);
		Imgproc.warpAffine(in_templ, this.out_templ, r, new Size(in_templ.cols(),in_templ.rows()));

		if (flag)
			return best_size;
		else
			return def_rtrn_scl;

	}

	boolean is_temp_les_than_img(Mat in_img, Mat in_temp)
	{
		if ((in_temp.cols() < in_img.cols())&& (in_temp.rows() < in_img.rows()))
			return true;
		else
			return false;
	}

	double getMax(double array[])
	{
		double max = 0;
		for (int i = 0; i < array.length; i++)
		{

			if (array[i] > max)

			{
				max = array[i];
				pos = i;
			}
		}

		return max;
	}

	boolean is_compressed(Mat img, boolean b)
	{
		if (b)
			if (img.total() < img_comp_area)
				return true;
			else
				return false;
		else if (img.total() < temp_comp_area)
			return true;
		else
			return false;
	}

	void set_config(boolean is_vid,	boolean real_time, boolean find_n,  boolean text_templ, boolean get_coordinates)
	{
		
		if(real_time)
		{
			blur_templ=0.001;
			blur_img=0.001;
			smpl_at=5;
		}
		if(text_templ)
		{
			
			

		}
		if(get_coordinates)
		{
			find_n=true;
		}
		if(find_n)
		{
			blur_img=0.001;
		}
		if(is_vid&&!real_time)
		{
			
			blur_img=0.001;
			blur_templ=0.5;
			tol=75;
			angle_tol=20;


		}
	}
}