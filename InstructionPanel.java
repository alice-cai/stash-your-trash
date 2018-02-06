/**
* InstructionPanel.java
*
* This class is responsible for displaying the instructions for the "Stash Your Trash"
* game and for prompting the user to input their name.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import java.io.*;

public class InstructionPanel extends JPanel
{
   public static final int MAX_NAME_LENGTH = 15;
   public static final String INSTRUCTIONS_FILE = "instructions.txt";

   private SpringLayout layout = new SpringLayout();
   private Color backgroundColour = new Color(232,232,232);
   private JLabel prompt;
   private JTextField nameField;
   private JButton startButton;
   private Player player;

   private JFrame mainFrame;
   private Consumer<Player> onGameInitiation;
   
   public InstructionPanel(JFrame mainFrame, Consumer<Player> onGameInitiation)
   {
      this.mainFrame = mainFrame;
      this.onGameInitiation = onGameInitiation;
      setLayout(layout);
      setBackground(backgroundColour);
      createInstructions();
      createTextField();
      createStartButton();
   }

   private void createInstructions () {
      JLabel title = new JLabel("INSTRUCTIONS:");
      title.setFont(new Font("Optima", Font.BOLD, 50));
      title.setForeground(Color.DARK_GRAY);
      layout.putConstraint(SpringLayout.NORTH, title, 20, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.WEST, title, 50, SpringLayout.WEST, this);
      add(title);

      String[] instructions = new String[0];

      try {
         BufferedReader in = new BufferedReader (new FileReader(INSTRUCTIONS_FILE));
         int numLines = Integer.parseInt(in.readLine());
         in.readLine(); // flush

         instructions = new String[numLines];
         for (int i = 0; i < numLines; i++) {
            instructions[i] = in.readLine();
         }
         in.close();
      } catch (IOException iox) {

      }

      createInstructions(instructions, 90, 50);
   }

   private void createInstructions (String[] instructions, int distanceFromNorth, int distanceFromWest) {
      final int LINE_SPACING = 50;

      for (int i = 0; i < instructions.length; i++) {
         JLabel line = new JLabel(instructions[i]);
         line.setFont(new Font("Optima", Font.BOLD, 35));
         line.setForeground(Color.DARK_GRAY);
         layout.putConstraint(SpringLayout.NORTH, line, distanceFromNorth + (i * LINE_SPACING), SpringLayout.NORTH, this);
         layout.putConstraint(SpringLayout.WEST, line, distanceFromWest, SpringLayout.WEST, this);
         add(line);
      }
   }

   private void createPrompt () {
      prompt = new JLabel("Enter your name:");
      prompt.setFont(new Font("Optima", Font.BOLD, 40));
      prompt.setForeground(Color.DARK_GRAY);
      layout.putConstraint(SpringLayout.NORTH, prompt, 50, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.WEST, prompt, 200, SpringLayout.WEST, this);
      add(prompt);
   }

   private void createTextField () {
      nameField = new JTextField("", 10);
      nameField.setHorizontalAlignment(JTextField.CENTER);
      nameField.setFont(new Font("Optima", Font.BOLD, 50));
      nameField.setForeground(Color.DARK_GRAY);
      nameField.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            if (nameField.getText().length() > MAX_NAME_LENGTH) {
                e.consume();
            }
         }
      });
      layout.putConstraint(SpringLayout.NORTH, nameField, 470, SpringLayout.NORTH,this);
      layout.putConstraint(SpringLayout.WEST, nameField, 140, SpringLayout.WEST, this);
      add(nameField);
   }

   private void createStartButton () {
      startButton = new JButton("PLAY");
      startButton.setFont(new Font("Optima", Font.BOLD, 50));
      startButton.setBackground(new Color (35, 137, 238));
      startButton.setForeground(Color.WHITE);
      startButton.setBorderPainted(false);
      startButton.setOpaque(true);
      startButton.setPreferredSize(new Dimension(170, 80));
   
      layout.putConstraint(SpringLayout.NORTH, startButton, 570, SpringLayout.NORTH,this);
      layout.putConstraint(SpringLayout.WEST, startButton, 265, SpringLayout.WEST,this);
      
      startButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               String name = nameField.getText().trim();
               if (name.isEmpty())
               {
                  JOptionPane.showMessageDialog(mainFrame, "Please enter your name.", "Error!", JOptionPane.ERROR_MESSAGE);
               } else {
                  player = new Player(name);
                  onGameInitiation.accept(player);
                  nameField.setText("");
               }
            }
         });
      
      add(startButton);
   }
}
