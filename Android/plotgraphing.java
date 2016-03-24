package com.FFTBT;

import java.util.Arrays;

import Android.Arduino.Bluetooth.R;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

public class plotgraphing {
	
	public static plotgraphing insPlotgraphing;
	private XYPlot plot;
	
	public plotgraphing()
	{
		
		insPlotgraphing=this;
	}
	 public  void floatplot(Number[]  series1Numbers ,Number[]  series2Numbers ){
	     //     initialize our XYPlot reference:
	        plot = (XYPlot) plotgraph.insPlotgraph.findViewById(R.id.mySimpleXYPlot);
	 
	     
	 
	        // Turn the above arrays into XYSeries':
	        XYSeries series1 = new SimpleXYSeries(
	                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
	                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
	                "Series1");                             // Set the display title of the series
	 
	        // same as above
	        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
	 
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
