package ui;

import util.FileManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class EditorFrame extends JFrame {
    private JTextArea textArea;
    private JLabel statusLabel;

    public EditorFrame() {
        setTitle("Text Editor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        
        //Menu Bar
        JScrollPane scrollPane = new JScrollPane(textArea);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        menuBar.add(editMenu);

        JMenuItem search = new JMenuItem("Search");
        editMenu.add(search);

        JMenu themeMenu = new JMenu("Theme");
        JMenuItem randomTheme = new JMenuItem("Random Theme");
        themeMenu.add(randomTheme);
        menuBar.add(themeMenu);

        // Edit Actions
        cutItem.addActionListener(e -> textArea.cut());
        copyItem.addActionListener(e -> textArea.copy());
        pasteItem.addActionListener(e -> textArea.paste());
        
        //New File
        newFile.addActionListener(e ->{
            textArea.setText("");
            statusLabel.setText("New file created");
        });
        
        //Open File
        openFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            if(result==JFileChooser.APPROVE_OPTION){
               try{
                File file = chooser.getSelectedFile();
                String content = FileManager.readFile(file);
                textArea.setText(content);
               } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        });
 
        //Save File
        saveFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(null);
            if(result==JFileChooser.APPROVE_OPTION){
                try{
                    File file = chooser.getSelectedFile();
                    FileManager.writeFile(file, textArea.getText());
                    JOptionPane.showConfirmDialog(null, "File saved successfully!");
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        //Search Functionality
        search.addActionListener(e ->
        {
            String searchText= JOptionPane.showInputDialog(null,"Enter text to search:");
            String content=textArea.getText();
            int index=content.indexOf(searchText);
            if(index>=0){
                textArea.requestFocus();
                textArea.select(index, index+searchText.length());
                JOptionPane.showMessageDialog(null,"Text found at position: "+index);
            }
            else{
                JOptionPane.showMessageDialog(null,"Text not found!");
            }
        });

        //Random Theme
        randomTheme.addActionListener(e -> {

        Color[] backgrounds = {
                Color.BLACK,
                Color.DARK_GRAY,
                new Color(25, 25, 112),
                new Color(34, 40, 49),
                new Color(44, 62, 80)
        };

        Color[] foregrounds = {
                Color.WHITE,
                Color.GREEN,
                Color.CYAN,
                Color.ORANGE,
                Color.PINK
        };

        Random random = new Random();
        int index = random.nextInt(backgrounds.length);
        textArea.setBackground(backgrounds[index]);
        textArea.setForeground(foregrounds[index]);
        textArea.setCaretColor(foregrounds[index]);
        statusLabel.setBackground(backgrounds[index]);
        statusLabel.setForeground(foregrounds[index]);
        statusLabel.setOpaque(true);
        });

        //Status Bar
        statusLabel=new JLabel("Words: 0 | Characters: 0");

        //Word and Character Count
        textArea.addKeyListener(new java.awt.event.KeyAdapter(){
            public void keyReleased(java.awt.event.KeyEvent e)
            {
                String text=textArea.getText();
                int words;
                if(text.trim().isEmpty()){
                    words=0;
                }
                else{
                    words=text.trim().split("\\s+").length;
                }
                int characters=text.length();
                statusLabel.setText("Words: "+words+" | Characters: "+characters);
            }
        });

        //Add components
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        setVisible(true);
    }
    
}
