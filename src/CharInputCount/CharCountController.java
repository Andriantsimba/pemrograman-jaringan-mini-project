/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CharInputCount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author acer
 */
public class CharCountController {
    private CountCharForm view;

    public CharCountController(CountCharForm view) {
        this.view= view;
        this.view.getReadbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Read();
                } catch (IOException ex) {
                    Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.view.getSaveBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Save();
            }
        });
        
        this.view.getClearbtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                clear();
            }
        });
    }
    
    
    public void Read() throws IOException{

        JFileChooser loadFile = view.getLoadFile();
        StyledDocument doc = view.getReadTxtArea().getStyledDocument();
        JTextArea vocab = view.getVocabtxt();
                if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
                 BufferedInputStream reader = null;
                 try {
                     reader = new BufferedInputStream(new FileInputStream(loadFile.getSelectedFile()));
                     doc.insertString(0, "", null);
                     int temp = 0;
                     List<Integer> list = new ArrayList<>();
                     while ((temp=reader.read()) != -1) {                    
                         list.add(temp);
                     }
                     
                   
                 /*Ascii value ranges that is up to 256*/
                 int counter[] = new int[256];
                 String mot="";
                 int Charcount=0;
                    BufferedReader read = new BufferedReader
                            (new FileReader(loadFile.getSelectedFile()));
                    String line ="";
                    String text="";
                    while((line = read.readLine()) != null){
                       text += line.trim();
                       Charcount+= line.length();
                    }
       
                    int len = text.length();
                    for (int i = 0; i < len; i++) {
                        if(text.charAt(i)!= ' ')
                    counter[text.charAt(i)]++; }
                    
                    /*Array used for storing each character of text*/
                    char array[] = new char[text.length()]; 
                        for (int i = 0; i < len; i++) { 
                           array[i] = text.charAt(i); 
                           int flag = 0; 
                           for (int j = 0; j <= i; j++) { 

                                /* If a char is found in String then set the flag 
                                 * so that we can print the occurrence
                                 */
                                if (text.charAt(i) == array[j])  
                                        flag++;                 
                           } 

                           if (flag == 1)  
                                mot+= " Character: "+ text.charAt(i)+" occurs "+counter[text.charAt(i)]+"\n";

                           view.getVocabtxt().setText(mot);
                        } 
                    
                    
                        if (!list.isEmpty()) {
                            byte[] dt = new byte[list.size()];
                            int i = 0;
                            for (Integer integer : list) {
                                dt[i]=integer.byteValue();
                                i++;
                            }
                            doc.insertString(doc.getLength(), new String(dt), null);
//                            vocab.setText(mot);
                            JOptionPane.showMessageDialog(view, "File read succeed"
                                    +"total Character: "+ Charcount
                                    , "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        }
                    
                       
                 } catch (FileNotFoundException ex) {
                     Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
                 } catch (IOException | BadLocationException ex) {
                     Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
                 } finally {
                     if (reader != null) {
                         try {
                             reader.close();
                         } catch (IOException ex) {
                             Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                 }
             }

    }
    
    public void Save(){
        JFileChooser loadFile = view.getLoadFile();
         if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
             BufferedOutputStream writer = null;
             try {
                 String contents = view.getReadTxtArea().getText();
                 if (contents != null && !contents.isEmpty()) {
                     writer = new BufferedOutputStream(new FileOutputStream(loadFile.getSelectedFile()));
                     writer.write(contents.getBytes());
                     JOptionPane.showMessageDialog(view, "File save succeed", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                 }

             } catch (FileNotFoundException ex) {
                 Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
             } finally {
                 if (writer != null) {
                     try {
                         writer.flush();
                         writer.close();
                         view.getReadTxtArea().setText("");
                     } catch (IOException ex) {
                         Logger.getLogger(CharCountController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
             }
         }
    }
    
    public void clear(){
        view.getReadTxtArea().setText(" ");
        view.getVocabtxt().setText(" ");
    }
    
}
