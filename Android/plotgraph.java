package com.FFTBT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidplot.xy.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Android.Arduino.Bluetooth.R;

public class plotgraph extends Activity{
	private static XYPlot plot;
	public static plotgraph insPlotgraph;
	Context context = this;
  public static  plotgraph getInstance()
  {
	  return insPlotgraph;
	  
  }

	 public void onCreate(Bundle savedInstanceState)
	    {
		 insPlotgraph =this;
		 super.onCreate(savedInstanceState);
		 
	        // fun little snippet that prevents users from taking screenshots
	        // on ICS+ devices :-)
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
	                                 WindowManager.LayoutParams.FLAG_SECURE);
	 
	        setContentView(R.layout.simple_xy_plot_example);
	       // Intent intent = getIntent();
	       // Number number1 = intent.getExtras().getInt("series1");
	        String abc =  "Just Toast";
	        Toast.makeText(context, abc, Toast.LENGTH_LONG).show();
	        int counter =1;
	        while (true)
	        {
	        	
	        	  floatplot();
	        	  try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					 Toast.makeText(context, "counter :"+counter, Toast.LENGTH_LONG).show();
					 BluetoothTest.series1Numbers[0] = 3;
					 BluetoothTest.series2Numbers[0] = 2;
					 counter++;
					 plot =null;
				
	        	
	        }
	      
//	        try {
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	       // plotgraphing.insPlotgraphing.floatplot(BluetoothTest.series1Numbers,BluetoothTest.series2Numbers);
	       // floatplot(BluetoothTest.series1Numbers,BluetoothTest.series2Numbers);
	 
	    }
	 


	public static void floatplot( ){
		
	     //     initialize our XYPlot reference:
	        plot = (XYPlot) plotgraph.insPlotgraph.findViewById(R.id.mySimpleXYPlot);
	 
	
	        // Turn the above arrays into XYSeries':
	        Number[] serie1 = BluetoothTest.series1Numbers;
	        Number[] serie2 = BluetoothTest.series2Numbers;
	        XYSeries series1 = new SimpleXYSeries(
	                Arrays.asList(serie1),          // SimpleXYSeries takes a List so turn our array into a List
	                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
	                "Series1");                             // Set the display title of the series
	 
	        // same as above
	        XYSeries series2 = new SimpleXYSeries(Arrays.asList(serie2), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
	 
	        // Create a formatter to use for drawing a series using LineAndPointRenderer
	        // and configure it from xml:
	        LineAndPointFormatter series1Format = new LineAndPointFormatter();
	        series1Format.setPointLabelFormatter(new PointLabelFormatter());
	        series1Format.configure(plotgraph.insPlotgraph.getApplicationContext(),
	                R.xml.line_point_formatter_with_plf1);
	 
	        // add a new series' to the xyplot:
	        plot.addSeries(series1, series1Format);
	 
	        // same as above:
	        LineAndPointFormatter series2Format = new LineAndPointFormatter();
	        series2Format.setPointLabelFormatter(new PointLabelFormatter());
	        series2Format.configure(plotgraph.insPlotgraph.getApplicationContext(),
	                R.xml.line_point_formatter_with_plf2);
	        plot.addSeries(series2, series2Format);
	 
	        // reduce the number of range labels
	        plot.setTicksPerRangeLabel(3);
	        plot.getGraphWidget().setDomainLabelOrientation(-45);
	       
	
	 }
	}
class abd implements Runnable

{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}