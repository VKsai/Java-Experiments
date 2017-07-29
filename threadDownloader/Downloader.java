package threadDownloader;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

class Downloader extends JPanel implements Runnable {
	
	private URL downloadURL;
	private InputStream is;
	private OutputStream os;
	private byte[] buffer;
	private int fileSize;
	private int bytesRead;
	private JLabel urlLabel;
	private JLabel sizeLabel;
	private JLabel completeLabel;
	private JProgressBar progress;
	public final static int BUFFER_SIZE = 1000;
	private boolean downloadStopped;
	private boolean threadSleepScheduled;
	private boolean threadSuspended;
	
	public final static int SLEEP_TIME = 5 * 1000;
	
	private Thread newThread;
	
	public Downloader(URL resource, OutputStream out) throws IOException {
		downloadURL = resource;
		os = out;
		bytesRead = 0;
		URLConnection conn = downloadURL.openConnection();
		fileSize = conn.getContentLength();
		if (fileSize == -1) {
			throw new FileNotFoundException(resource.toString());
		}
		is = new BufferedInputStream(conn.getInputStream());
		buffer = new byte[BUFFER_SIZE];
		newThread = new Thread(this);
		createLayout();
		
		downloadStopped = false;
		threadSleepScheduled = false;
		threadSuspended = false;
		//stopped = false;
	}
	
	public void createLayout() {
		JLabel label;
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 10, 5, 10);

		constraints.gridx = 0;
		label = new JLabel("URL:", JLabel.LEFT);
		add(label, constraints);

		label = new JLabel("Complete:", JLabel.LEFT);
		add(label, constraints);

		label = new JLabel("Downloaded:", JLabel.LEFT);
		add(label, constraints);

		constraints.gridx = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.weightx = 1;
		urlLabel = new JLabel(downloadURL.toString());
		add(urlLabel, constraints);

		progress = new JProgressBar(0, fileSize);
		progress.setStringPainted(true);
		add(progress, constraints);

		constraints.gridwidth = 1;
		completeLabel = new JLabel(Integer.toString(bytesRead));
		add(completeLabel, constraints);

		constraints.gridx = 2;
		constraints.weightx = 0;
		constraints.anchor = GridBagConstraints.EAST;
		label = new JLabel("Size:", JLabel.LEFT);
		add(label, constraints);

		constraints.gridx = 3;
		constraints.weightx = 1;
		sizeLabel = new JLabel(Integer.toString(fileSize));
		add(sizeLabel, constraints);
	}
	
	@Override
	public void run() {
		downloadActivity();
	}
	
	public void startDownload() {
		newThread.start();
	}
	
	public synchronized void setSleepScheduled(boolean doSleep) {
		threadSleepScheduled = doSleep;
	}
	
	public synchronized boolean isSleepScheduled() {
		return threadSleepScheduled;
	}
	
	public synchronized void setSuspended(boolean val) {
		threadSuspended = val;
	}
	
	public synchronized boolean isSuspended() {
		return threadSuspended;
	}
	
	public synchronized void resumeDownload() {
		this.notify();
	}
	
	public synchronized void setStopped(boolean stop) {
		downloadStopped = stop;
	}
	
	public synchronized boolean isStopped() {
		return downloadStopped;
	}
	
	public void stopDownload() {
		newThread.interrupt();
	}
	
	public void downloadActivity() {
		int byteCount;
		
		Runnable progressUpdateThreadRunnable = () -> {
			progress.setValue(bytesRead);
			completeLabel.setText(Integer.toString(bytesRead));
		};
		
		while((bytesRead < fileSize) && (!downloadStopped)) {
			
			if(isSleepScheduled()) {
				try {
					Thread.sleep(SLEEP_TIME);
					setSleepScheduled(false);
			} catch(InterruptedException e) {
				
			}
			
			try {
				byteCount = is.read(buffer);
				if(byteCount == -1) {
					setStopped(true);
					break;
				} else {
					os.write(buffer, 0, byteCount);
					bytesRead += byteCount;
					SwingUtilities.invokeLater(progressUpdateThreadRunnable);
				}
			} catch (IOException e) {
				setStopped(true);
				JOptionPane.showMessageDialog(this, e.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
			
		synchronized(this) {
			if(isSuspended()) {
				try {
					this.wait();
				} catch(InterruptedException ie) {
					
				}
			}
		}
			
		if(Thread.interrupted()) {
			setStopped(true);
			break;
		}
		
		try {
			os.close();
			is.close();
		} catch (IOException e) {
			 	
		}
		
	}
	}
}
