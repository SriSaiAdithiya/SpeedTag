package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class RandomImageChanger extends JFrame {
    private JPanel[] imagePanels;
    private JLabel[] imageLabels;
    private String[] initialImagePaths;
    private String[] newImagePaths;
    private Random random;
    private Timer imageChangeTimer;
    private Timer revertTimer;
    private Timer nextChangeTimer;
    private JButton pauseButton;
    private boolean isPaused = false;
    private int imageChangeCount = 0;
    private int currentIndex = -1;

    public RandomImageChanger() {
        // Initialize the frame
        setTitle("Random Image Changer");
        setSize(800, 300); // Set the frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 4, 10, 10)); // 10px horizontal and vertical gaps between panels

        // Initialize the panels and labels to hold the images
        imagePanels = new JPanel[4];
        imageLabels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            imagePanels[i] = new JPanel();
            imageLabels[i] = new JLabel();
            imageLabels[i].setHorizontalAlignment(JLabel.CENTER);
            imagePanels[i].add(imageLabels[i]);
            add(imagePanels[i]);
        }

        // Initialize the pause button
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPaused) {
                    resumeTimers();
                    pauseButton.setText("Pause");
                } else {
                    pauseTimers();
                    pauseButton.setText("Resume");
                }
            }
        });
        add(pauseButton, BorderLayout.SOUTH);

        // Array of initial image paths (ensure these paths are correct and images are accessible)
        initialImagePaths = new String[]{
                "D:\\OS NOTES\\javaprojects\\closedtoll.jpg",
                "D:\\OS NOTES\\javaprojects\\closedtoll.jpg",
                "D:\\OS NOTES\\javaprojects\\closedtoll.jpg",
                "D:\\OS NOTES\\javaprojects\\closedtoll.jpg"
        };

        // Array of new image paths (ensure these paths are correct and images are accessible)
        newImagePaths = new String[]{
                "D:\\OS NOTES\\javaprojects\\opentoll.jpg",
                "D:\\OS NOTES\\javaprojects\\opentoll.jpg",
                "D:\\OS NOTES\\javaprojects\\opentoll.jpg",
                "D:\\OS NOTES\\javaprojects\\opentoll.jpg"
        };

        // Initialize Random instance
        random = new Random();

        // Show the initial images
        showInitialImages();

        // Start changing images with an initial delay
        startInitialDelay();

        // Make the frame visible
        setVisible(true);
    }

    private void showInitialImages() {
        for (int i = 0; i < initialImagePaths.length; i++) {
            ImageIcon initialImageIcon = resizeImageIcon(initialImagePaths[i], 100, 100);
            imageLabels[i].setIcon(initialImageIcon);
        }
    }

    private void startInitialDelay() {
        // Start the image changing process after an initial delay
        int initialDelay = 10 + random.nextInt(11); // Random delay between 10 to 20 seconds
        imageChangeTimer = new Timer((int) TimeUnit.SECONDS.toMillis(initialDelay), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeRandomImage();
            }
        });
        imageChangeTimer.setRepeats(false); // Set to run only once
        imageChangeTimer.start(); // Start the timer
    }

    private void changeRandomImage() {
        // Stop if 4 image changes have been completed
        if (imageChangeCount >= 4) {
            return;
        }

        // Generate a random number between 0 and 3 to select an image to change
        currentIndex = random.nextInt(initialImagePaths.length);

        // Select a new image from the new image set to replace the selected initial image
        String newImagePath = newImagePaths[currentIndex];

        // Set the new image as the icon for the selected JLabel
        ImageIcon newImageIcon = resizeImageIcon(newImagePath, 100, 100);
        imageLabels[currentIndex].setIcon(newImageIcon);

        // Optional: Print the selected image path for debugging
        System.out.println("Replaced image at index " + currentIndex + " with: " + newImagePath);

        // Start timer to revert the image back to original after 5 to 10 seconds
        int delayToOldImage = 5 + random.nextInt(6); // Random delay between 5 to 10 seconds
        revertTimer = new Timer((int) TimeUnit.SECONDS.toMillis(delayToOldImage), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon initialImageIcon = resizeImageIcon(initialImagePaths[currentIndex], 100, 100);
                imageLabels[currentIndex].setIcon(initialImageIcon);

                // Increment the image change count
                imageChangeCount++;

                // Start changing the next image after a delay
                if (imageChangeCount < 4) {
                    int delayToNextChange = 1 + random.nextInt(5); // Random delay between 1 to 20 seconds
                    nextChangeTimer = new Timer((int) TimeUnit.SECONDS.toMillis(delayToNextChange), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            changeRandomImage(); // Change next image after delay
                        }
                    });
                    nextChangeTimer.setRepeats(false); // Set to run only once
                    nextChangeTimer.start(); // Start the timer for next image change
                }
            }
        });
        revertTimer.setRepeats(false); // Set to run only once
        revertTimer.start(); // Start the timer
    }

    private void pauseTimers() {
        isPaused = true;
        if (imageChangeTimer != null && imageChangeTimer.isRunning()) {
            imageChangeTimer.stop();
        }
        if (revertTimer != null && revertTimer.isRunning()) {
            revertTimer.stop();
        }
        if (nextChangeTimer != null && nextChangeTimer.isRunning()) {
            nextChangeTimer.stop();
        }
    }

    private void resumeTimers() {
        isPaused = false;
        if (imageChangeTimer != null) {
            imageChangeTimer.start();
        }
        if (revertTimer != null) {
            revertTimer.start();
        }
        if (nextChangeTimer != null) {
            nextChangeTimer.start();
        }
    }

    private ImageIcon resizeImageIcon(String imagePath, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(RandomImageChanger::new);
    }
}