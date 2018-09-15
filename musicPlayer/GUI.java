package musicPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import myLibrary.Metin;
//bir klasörün içindeki tüm müzikleri alma  //aynı müzikten varsa silme(eklememe!)  //listede sadece müzik ismi gösterme
//otomatik scroll yapma https://stackoverflow.com/questions/6379061/how-to-auto-scroll-to-bottom-in-java-swing?rq=1
public class GUI extends JPanel{
	private static final long serialVersionUID = 1L;
	PlayerMain guı2 = new PlayerMain();
	MusicPlayer player = new MusicPlayer();
	
	//Components
	private JPanel mainPanel;
	private JButton playButton;
	private JButton pauseButton;
	private JButton resumeButton;
	private JButton stopButton;
	private JButton deleteButton;
	public static JList<String> jlist;
	private JPanel jPanel2;
	private static JLabel stateLabel;
	private JScrollPane jScrollPane1;
	private JPanel listPanel;
	static JMenuBar bar;
	private JMenu fileMenu, playlist;
	private JMenuItem exitItem, open;
	private static DefaultListModel<String> dlm;
	
	static String[] nameofFiles;
	
	static Thread currentChooserThread;

	//Threads
	Thread playerThread = new Thread(player);
	
	public void init(){
		
		//Event classes
		Event e = new Event();
		playButton.addActionListener(e);
		
		Event1 e1 = new Event1();
		resumeButton.addActionListener(e1);
		
		Event2 e2 = new Event2();
		pauseButton.addActionListener(e2);
		
		Event3 e3 = new Event3();
		stopButton.addActionListener(e3);
		
		Event4 e4 = new Event4();
		exitItem.addActionListener(e4);
		
		Event5 e5 = new Event5();
		deleteButton.addActionListener(e5);
		
		Event6 e6 = new Event6();
		open.addActionListener(e6);
		
	}
	
	public void code() {
		
		jlist.addListSelectionListener(new ListSelectionListener() {  //jlist elementlerine basınca çalışır
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if(!event.getValueIsAdjusting()) {  //1 kere basınca 2 kere çalışmaması için
					try {
						String songPath = Metin.convertBackslash(jlist.getSelectedValue());  //jlist üzerindeki yazılı değeri verir
						MusicPlayer.currentSongPath = songPath;
					}catch (NullPointerException e) {
						System.out.println("null");
					}
				}
			}
		}
		);
		
		dlm = new DefaultListModel<String>();
	}
	
	public void jList() {  //itemi kaldırınca jlist güncellenmiyor
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
		        jScrollPane1.setViewportView(jlist);

		        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		        stateLabel.setText("Choose a song");

		        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
		        listPanel.setLayout(listPanelLayout);
		        listPanelLayout.setHorizontalGroup(
		            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addComponent(jScrollPane1)
		            .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		        );
		        listPanelLayout.setVerticalGroup(
		            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(listPanelLayout.createSequentialGroup()
		                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
		                .addGap(0, 0, Short.MAX_VALUE))
		        );
			}
		});
	}
	
	public static void setStateLabel(String str) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				stateLabel.setText(str);
			}
		});
	}
			
	public GUI() {
		
		//Menu Components
		bar = new JMenuBar();
		add(bar);
		
		fileMenu = new JMenu("File");
		bar.add(fileMenu);
		
		playlist = new JMenu("Playlist");
		bar.add(playlist);
		
		exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		
		open = new JMenuItem("Open");
		open.setToolTipText("Import songs");
		playlist.add(open);
		initComponents();
		init();
		code();
		DragAndDrop.addToList();
	}
	
	//Events
	public class Event implements ActionListener{  //Start Button
		public void actionPerformed(ActionEvent e) {
			MusicPlayer.start(playerThread, player);
			if(MusicPlayer.currentSongPath == null) 
				setStateLabel("Please choose a song from above");
		}
	}
	
	public class Event1 implements ActionListener{  //Resume Button

		@Override
		public void actionPerformed(ActionEvent e1) {
			MusicPlayer.resume();
		}
		
	}
	
	public class Event2 implements ActionListener{  //Pause Button

		@Override
		public void actionPerformed(ActionEvent e2) {
			MusicPlayer.pause();
		}
		
	}
	
	public class Event3 implements ActionListener{  //Stop Button

		@Override
		public void actionPerformed(ActionEvent e3) {
			setStateLabel(MusicPlayer.file.getName() + " - stopped");
			MusicPlayer.stop();
		}
		
	}
	
	public class Event4 implements ActionListener{  //Exit JMenuItem

		@Override
		public void actionPerformed(ActionEvent e4) {
			System.exit(0);
		}
		
	}
	
	public class Event5 implements ActionListener{  //Delete Button

		@Override
		public void actionPerformed(ActionEvent e5) {
			dlm.remove(jlist.getSelectedIndex());
			jList();		
		}
		
	}
	
	public class Event6 implements ActionListener{  //Open DragAndDrop Window

		@Override
		public void actionPerformed(ActionEvent e) {
			DragAndDrop dragAndDrop = new DragAndDrop();
			dragAndDrop.createAndShowGUI();
		}
		
	}
	
	//Getters Setters
	public static DefaultListModel<String> getListModel() {  /* diğer sınıflardan erişebilmek 
															  * için getter
															  * */
		return dlm;
	}
	
	//
	
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        listPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlist = new javax.swing.JList<>();
        stateLabel = new javax.swing.JLabel();
        playButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        resumeButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(400, 340));
        
        jScrollPane1.setViewportView(jlist);

        stateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stateLabel.setText("Choose a song");

        javax.swing.GroupLayout listPanelLayout = new javax.swing.GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(stateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        playButton.setText("Play");
        playButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        pauseButton.setText("Pause");

        resumeButton.setText("Resume");

        stopButton.setText("Stop");

        deleteButton.setText("Delete");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(stopButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resumeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pauseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(listPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton)
                    .addComponent(pauseButton)
                    .addComponent(deleteButton)
                    .addComponent(resumeButton)
                    .addComponent(stopButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }
}
