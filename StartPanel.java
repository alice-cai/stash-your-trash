/**
* StartPanel.java
*
* This class is responsible for displaying the main game menu. It provides a GUI
* that allows the user either to start the game or view the leaderboard.
*/

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class StartPanel extends JPanel
{
   private static final String CONFIG_FILE = "logo.txt";
   private SpringLayout layout;
   private Color backgroundColour = new Color(232,232,232);
   private JButton startButton;
   private JButton leaderboardButton;
   private JLabel imageLabel;
   private ImageIcon logo;

   private CardLayout cardLayout;
   private JPanel cards;

   public StartPanel(CardLayout cardLayout, JPanel cards)
   {
      this.cardLayout = cardLayout;
      this.cards = cards;

      layout = new SpringLayout();
      setBackground(backgroundColour);
      setLayout(layout);
      createImage();
      createStartButton();
      createLeaderboardButton();
   }

   private void createImage () {
      String imageFile = "";
      try {
         BufferedReader in = new BufferedReader(new FileReader(CONFIG_FILE));
         imageFile = in.readLine();
      } catch (IOException iox) {
         System.out.println("StartPanel: Error loading image file name.");
      }

      logo = new ImageIcon(imageFile);
      imageLabel = new JLabel();
      imageLabel.setIcon(logo);
      layout.putConstraint(SpringLayout.NORTH, imageLabel, 50, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.WEST, imageLabel, 75, SpringLayout.NORTH,this);
      add(imageLabel);
   }

   private void createStartButton () {
      JButton startButton = new JButton("START GAME");
      startButton.setFont(new Font("Optima", Font.BOLD, 50));
      startButton.setBackground(new Color (35, 137, 238));
      startButton.setForeground(Color.WHITE);
      startButton.setBorderPainted(false);
      startButton.setOpaque(true);
      startButton.setPreferredSize(new Dimension(400, 100));

      layout.putConstraint(SpringLayout.NORTH, startButton, 420, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.WEST, startButton, 150, SpringLayout.NORTH, this);

      startButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               cardLayout.show(cards, StashYourTrash.INSTRUCTION_PANEL);
            }
         });

      add(startButton);
   }

   private void createLeaderboardButton () {
      JButton leaderboardButton = new JButton("LEADERBOARD");
      leaderboardButton.setFont(new Font("Optima", Font.BOLD, 30));
      leaderboardButton.setBackground(new Color (103, 53, 54));
      leaderboardButton.setForeground(Color.WHITE);
      leaderboardButton.setBorderPainted(false);
      leaderboardButton.setOpaque(true);
      leaderboardButton.setPreferredSize(new Dimension(300, 70));

      layout.putConstraint(SpringLayout.NORTH, leaderboardButton, 550, SpringLayout.NORTH, this);
      layout.putConstraint(SpringLayout.WEST, leaderboardButton, 200, SpringLayout.NORTH,this);

      leaderboardButton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               cardLayout.show(cards, StashYourTrash.LEADERBOARD_PANEL);
            }
         });

      add(leaderboardButton);
   }
}