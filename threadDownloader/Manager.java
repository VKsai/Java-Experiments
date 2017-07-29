package threadDownloader;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class Manager extends JPanel {

	private Downloader d;
	
	private JButton startB;
	private JButton suspendB;
	private JButton sleepB;
	private JButton resumeB;
	private JButton stopB;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(args[0]);
		FileOutputStream fout = new FileOutputStream(args[1]);
		
		JFrame f = new JFrame();
		Manager m = new Manager(url, fout);
		f.getContentPane().add(m);
		f.setSize(600, 400);
		f.setVisible(true);
	}
	
	public Manager(URL src, OutputStream os) throws IOException {
		d = new Downloader(src, os);
		createLayout();
		
	}
	
	private void createLayout() {
		setLayout(new BorderLayout());
		d.setBorder(new BevelBorder(BevelBorder.RAISED));
		add(d, BorderLayout.CENTER);
		add(getButtonPanel(), BorderLayout.SOUTH);
	}

	private JPanel getButtonPanel() {
		
		JPanel outerPanel;
		JPanel innerPanel;
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(1, 5, 10, 0));
		
		startB = new JButton("Start");
		startB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startB.setEnabled(false);
				sleepB.setEnabled(true);
				resumeB.setEnabled(false);
				suspendB.setEnabled(true);
				stopB.setEnabled(true);
				d.downloadActivity();
			}
		});
		innerPanel.add(startB);
		
		sleepB = new JButton("Sleep");
		sleepB.setEnabled(false);
		sleepB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				d.setSleepScheduled(true);
			}
		});
		innerPanel.add(sleepB);
		
		resumeB = new JButton("Resume");
		resumeB.setEnabled(false);
		resumeB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				resumeB.setEnabled(false);
				suspendB.setEnabled(true);
				stopB.setEnabled(true);
				d.resumeDownload();
			}
		});
		innerPanel.add(resumeB);
		
		suspendB = new JButton("Suspend");
		suspendB.setEnabled(false);
		suspendB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				suspendB.setEnabled(false);
				resumeB.setEnabled(true);
				stopB.setEnabled(true);
				d.setSuspended(true);
			}
		});
		innerPanel.add(suspendB);
		
		stopB = new JButton("Stop");
		stopB.setEnabled(false);
		stopB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stopB.setEnabled(false);
				sleepB.setEnabled(false);
				suspendB.setEnabled(false);
				resumeB.setEnabled(false);
				d.stopDownload();
			}
		});
		innerPanel.add(stopB);
		
		outerPanel = new JPanel();
		outerPanel.add(innerPanel);
		
		return outerPanel;	
	}
}
