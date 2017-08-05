package by.enot;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Gui extends JFrame {
	private final int MAX_ITERATION_NUMBER  = 100_000_000;
	private int luckCount, noLuckCount, superLuckCount, totalCount, postPercentageCount;
	private JTextField inputChance, inputAutoIter;
	private JButton checkButton, checkAutoButton, clearCountButton;
	private JLabel infoLabel, infoAutoLabel, luckAndUnluckLabel, extraLuckLabel, totalTriesLabel, postPercentageLabel ;
	private JPanel panel;

	public Gui(String name) {
		super(name);
//		luckCount = 0;
//		noLuckCount = 0;
//		superLuckCount = 0;
//		totalCount = 0;
		inputChance = new JTextField(3);
		inputAutoIter = new JTextField(7);
		checkButton = new JButton("Check");
		checkAutoButton = new JButton("AutoCheck");
		clearCountButton = new JButton("Clear");
		checkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int chance =0;
				int iter = 1;
				try {
					chance = parceInputChance(inputChance.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Incorrect input chance number");
					inputChance.setText("");
					inputChance.requestFocus();
					return;
				}
				int[] result = testLuck(chance, iter);
				updateCounters(result);
				updateResultInfo();
				if (result[2] != 0) infoLabel.setText("Great Success!");
				else if (result[0] != 0) infoLabel.setText("Success!");
				else infoLabel.setText("Fail.");
			}

		});
		checkAutoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int chance = 0;
				int iter = 0;
				try {
					chance = parceInputChance(inputChance.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Incorrect input %");
					inputChance.setText("");
					inputChance.requestFocus();
					return;
				}
				try {
					iter = parceIter(inputAutoIter.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Incorrect number of tries");
					inputAutoIter.setText("");
					inputAutoIter.requestFocus();
					return;
				}
				int[] result = testLuck(chance, iter);
				updateCounters(result);
				updateResultInfo();
			}

		});
		
		clearCountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				luckCount = 0;
				noLuckCount = 0;
				superLuckCount = 0;
				totalCount = 0;
				postPercentageCount = 0;
				inputChance.setText("");
				inputAutoIter.setText("");
				infoLabel.setText("Input %");
				inputChance.requestFocus();
				updateResultInfo();

			}
			
		});
		infoLabel = new JLabel("Input %");
		infoAutoLabel = new JLabel("Number of tries");
		luckAndUnluckLabel = new JLabel("Lucky: " + luckCount + "   UnLucky: " + noLuckCount);
		extraLuckLabel = new JLabel("ExtraLucky: " + superLuckCount);
		totalTriesLabel = new JLabel("Total Tries: " + totalCount);
		postPercentageLabel = new JLabel("Post factum %: " + postPercentageCount);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		//panel.setBounds(46, 0, 100, 160);
		inputChance.setMaximumSize(inputChance.getPreferredSize());
		inputAutoIter.setMaximumSize(inputAutoIter.getPreferredSize());
		infoLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		inputChance.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		checkButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		infoAutoLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		inputAutoIter.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		checkAutoButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		luckAndUnluckLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		clearCountButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		extraLuckLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		totalTriesLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		postPercentageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		panel.add(infoLabel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(inputChance);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(checkButton);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		panel.add(infoAutoLabel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(inputAutoIter);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(checkAutoButton);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		panel.add(luckAndUnluckLabel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(extraLuckLabel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(totalTriesLabel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(postPercentageLabel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		panel.add(clearCountButton);
		
		this.add(panel);
		this.setSize(230, 330);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	private int[] testLuck(int chance, int iter) {
		int[] result = new int[3];
		for (int i = 0; i < iter; i++) {
			if (Luck.isLucky(chance))
				if (Luck.isLucky(10)) result[2] += 1;
				else result[0] += 1;
			else
				result[1] += 1;
		}
		return result;
	}

	private int parceInputChance(String input) throws NumberFormatException{
		int parced = 0;
		parced = Integer.parseInt(input);
		if (parced < 0 || parced > 100)
			throw new NumberFormatException();
		return parced;
	}
	
	private int parceIter(String input) throws NumberFormatException{
		int parced = 0;
		parced = Integer.parseInt(input);
		if (parced < 0 || parced > MAX_ITERATION_NUMBER)
			throw new NumberFormatException();
		return parced;
	}
	
	private void updateResultInfo() {
		postPercentageLabel.setText("Post factum %: " + postPercentageCount );
		totalTriesLabel.setText("Total Tries: " + totalCount);
		extraLuckLabel.setText("ExtraLucky: " + superLuckCount);
		luckAndUnluckLabel.setText("Lucky: " + luckCount + " UnLucky: " + noLuckCount);
		luckAndUnluckLabel.getParent().repaint();
	}
	private void updateCounters(int[] result) {
		luckCount += result[0];
		noLuckCount += result[1];
		superLuckCount += result[2];
		totalCount = luckCount + noLuckCount + superLuckCount;
		if (luckCount != 0 || superLuckCount != 0) {
			double currentPercent = (double)(luckCount + superLuckCount)/totalCount;
			postPercentageCount = (int) Math.round(currentPercent*100);
			}
	}
}
