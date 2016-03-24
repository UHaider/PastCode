package com.FFTBT;

import Android.Arduino.Bluetooth.R;
import android.R.color;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.androidplot.util.PlotStatistics;

public class BluetoothTest extends Activity {
	TextView myLabel;
	EditText myTextbox;
	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket mmSocket;
	BluetoothDevice mmDevice;
	OutputStream mmOutputStream;
	InputStream mmInputStream;
	Thread workerThread;
	byte[] readBuffer;
	int readBufferPosition;
	int counter;
	volatile boolean stopWorker;
	Button openButton;
	Button sendButton;
	//public static plotgraph pg;
	public static final Number[] series1Numbers = { 1, 8, 5, 2, 7, 4 };
	public static final Number[] series2Numbers = { 4, 6, 3, 8, 2, 10 };
	
	//My add
	public View mWaveform = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {

		//pg = new plotgraph();
		//mWaveform = findViewById(R.id.mySimpleXYPlot);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		openButton = (Button) findViewById(R.id.open);
		sendButton = (Button) findViewById(R.id.send);
		Button closeButton = (Button) findViewById(R.id.close);
		myLabel = (TextView) findViewById(R.id.label);
		myTextbox = (EditText) findViewById(R.id.entry);

		// Open Button
		openButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// try
				// {

				// Create a couple arrays of y-values to plot:
				//try {
					//openBT();
				//} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
				//beginListenForData();
				Intent intent = new Intent(BluetoothTest.this, plotgraph.class);
				//intent.putExtra("series1", series1Numbers);
				//intent.putExtra("series2", series2Numbers);
				// plotgraph.floatplot(series1Numbers, series2Numbers);
				startActivity(intent);
				
				
				// plotgraph.floatplot(series1Numbers, series2Numbers);

				// findBT();

				// openBT();
			}
			// catch (IOException ex) { }
			// }
		});

		// Send Button
		sendButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					sendData();
				} catch (IOException ex) {
				}
			}
		});

		// Close button
		closeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					closeBT();
				} catch (IOException ex) {
				}
			}
		});
	}

	void findBT() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			myLabel.setText("No bluetooth adapter available");
		}

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBluetooth = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBluetooth, 0);
		}

		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
				.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				// if(device.getName().equals("MattsBlueTooth"))
				if (device.getName().equals("HC-05")) {
					mmDevice = device;
					myLabel.setText("Bluetooth Device Found "
							+ device.getName());

					// break;
					//
					// }
					// else
					// {
					//
					//
					// try {
					// myLabel.setText("Bluetooth Device not Found");
					// openButton.setBackgroundColor(color.black);
					// Thread.sleep(3);
					// } catch (InterruptedException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			}
		}

	}

	void openBT() throws IOException {

		TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String uui = tManager.getDeviceId();
		// myLabel.setText("Bluetooth uuid1:"+uui);
		// UUID uuid = UUID.randomUUID(); //Standard SerialPortService ID
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard
																				// SerialPortService
																				// ID
		// UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		myLabel.setText("Bluetooth Opened1");
		mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
		myLabel.setText("Bluetooth Opened2");
		mmSocket.connect();
		myLabel.setText("Bluetooth Opened3");
		mmOutputStream = mmSocket.getOutputStream();
		mmInputStream = mmSocket.getInputStream();
		myLabel.setText("CAll begin lisen data");
		try {
			// openButton.setBackgroundColor(color.holo_green_light);
			Thread.sleep(3);
			Intent intent = new Intent(BluetoothTest.this, plotgraph.class);
			startActivity(intent);
			// setContentView(R.layout.simple_xy_plot_example);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// beginListenForData();
		//

	}

	void beginListenForData() {
		final Handler handler = new Handler();
		final byte delimiter = 10; // This is the ASCII code for a newline
									// character

		stopWorker = false;
		readBufferPosition = 0;
		readBuffer = new byte[1024];
		workerThread = new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted() && !stopWorker) {
					try {
						int bytesAvailable = mmInputStream.available();
						if (bytesAvailable > 0) {
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for (int i = 0; i < bytesAvailable; i++) {
								byte b = packetBytes[i];
								if (b == delimiter) {
									byte[] encodedBytes = new byte[readBufferPosition];
									System.arraycopy(readBuffer, 0,
											encodedBytes, 0,
											encodedBytes.length);
									final String data = new String(
											encodedBytes, "US-ASCII");
									readBufferPosition = 0;

									handler.post(new Runnable() {
										public void run() {
											myLabel.setText(data);

										}
									});
								} else {
									readBuffer[readBufferPosition++] = b;
								}
							}
						}
					} catch (IOException ex) {
						stopWorker = true;
					}
				}
			}
		});

		workerThread.start();
	}

	void sendData() throws IOException {
		String msg = myTextbox.getText().toString();
		msg += "\n";
		msg = "A";
		mmOutputStream.write(msg.getBytes());
		myLabel.setText("Data Sent: " + msg);
	}

	void closeBT() throws IOException {
		stopWorker = true;
		mmOutputStream.close();
		mmInputStream.close();
		mmSocket.close();
		myLabel.setText("Bluetooth Closed");
	}

	void numberploting(Number[] ser1, Number[] ser2)

	{

	}
}
