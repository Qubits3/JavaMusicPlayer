package musicPlayer;

import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;
//birden fazla playlist //   playlistten element silme	
public class DragAndDrop extends JFrame{
private static final long serialVersionUID = 1L;
	
	private JTextArea area;
	private static int numberOfLines = 0;

	private static int temp = 0;
    
    public void createAndShowGUI(){
    
    setTitle("Drag and Drop");
    setSize(400,400);
    setVisible(true);
    setLayout(new BorderLayout());
    setResizable(false);
    // Create JTextArea
    area = new JTextArea();
    area.setText("\n\n\n\n\n\n\n\n\n\n\t               "+">> Drag Files Here << ");
    add(area);
    
    enableDragAndDrop();
    
    setLocationRelativeTo(null);
    
    }
    
    private void enableDragAndDrop(){
    	
        @SuppressWarnings("unused")
		DropTarget target = new DropTarget(area,new DropTargetListener(){
            public void dragEnter(DropTargetDragEvent e)
            {
            }
            
            public void dragExit(DropTargetEvent e)
            {
            }
            
            public void dragOver(DropTargetDragEvent e)
            {
            }
            
            public void dropActionChanged(DropTargetDragEvent e)
            {
            
            }
            
            public void drop(DropTargetDropEvent e){
                try {
                    // Accept the drop first, important!
                    e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    
                    // Get the files that are dropped as java.util.List
                    
					@SuppressWarnings("rawtypes")
					List list = (List) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					
                    // Now get the file from the list
					System.out.println("Liste numarası : " + numberOfLines);
					System.out.println("Gelen liste boyutu : " + list.size());
					System.out.println("temp : " + temp);
					int j = 0;  //her dosya sürüklenme işleminde 0 a eşitlenir
                    for(int i = numberOfLines;i < list.size() + temp;numberOfLines++) {
                    	
                    	File file = (File) list.get(j);  //listedeki dosyaları file a ekler  // her seferinde yeni bir liste oluşturulduğu için 0 dan başlanmak zorundadır j her seferinde sıfırdan başlar
                    	System.out.println(file.getPath());  //dosyanın konumları
                    	
                    	GUI.getListModel().add(numberOfLines, file.getPath());  //Dosyaları liste ekleme
                    	GUI.jlist.setModel(GUI.getListModel());  //JList e DefaultListModel atama
                    	
                    	String savedFiles = "%" + file.getPath() + "&!\n";  //dosya yollarını değişkene aktarır
                    	SaveList.write(savedFiles);  //dosyaya yazma metodu
                    	j++;
                    	i++;
                    }
                    
                    temp = numberOfLines;  //for döngüsünü parantezinin içindeki temp olan yere numberOflines gelecekti, ama eğer gelise numberOfLines her seferinde arttığı için döngü sonsuz döngüye girecekti o yüzden temp oluşturuldu
                    
                }catch(Exception ex){
                	ex.printStackTrace();
                }
            }
            
        });
    }
    
    public static void addToList() {  //Program ilk açılışta dosyadan şarkıları okuyup listeye ekler

        for(int i = 0;i < SaveList.read();i++) {
        	String nameOfFiles[] = new String[SaveList.read()];
        	nameOfFiles = SaveList.read1();
        	
        	GUI.getListModel().add(i, nameOfFiles[i]);  //Dosyaları listeye ekleme  //Hatalı kısım dosyadan okunacak
        	GUI.jlist.setModel(GUI.getListModel());  //JList e DefaultListModel atama
        	System.out.println(nameOfFiles[i]);
        }
    }
}
