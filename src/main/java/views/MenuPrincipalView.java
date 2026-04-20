package views;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {
    
    public MenuPrincipalView() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Système de Gestion Académique - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(240, 240, 245));
        
        // Titre
        JLabel titreLabel = new JLabel("📚 SYSTÈME DE GESTION ACADÉMIQUE");
        titreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titreLabel.setForeground(new Color(0, 102, 204));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titreLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel auteurLabel = new JLabel("Jérôme Cirhulwire - Architecture MVC");
        auteurLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        auteurLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(auteurLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Section Gestion Principale
        JLabel sectionLabel = new JLabel("GESTION PRINCIPALE");
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        sectionLabel.setForeground(new Color(100, 100, 100));
        sectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(sectionLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton ensembleBtn = createMenuButton("👥 Gestion des Enseignants", new Color(33, 150, 243));
        ensembleBtn.addActionListener(e -> new EnsembleView().setVisible(true));
        mainPanel.add(ensembleBtn);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton coursBtn = createMenuButton("📖 Gestion des Cours", new Color(76, 175, 80));
        coursBtn.addActionListener(e -> new CoursView().setVisible(true));
        mainPanel.add(coursBtn);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton classeBtn = createMenuButton("🏛️ Gestion des Classes", new Color(255, 152, 0));
        classeBtn.addActionListener(e -> new ClasseView().setVisible(true));
        mainPanel.add(classeBtn);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Section Administration
        JLabel adminLabel = new JLabel("ADMINISTRATION");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 14));
        adminLabel.setForeground(new Color(100, 100, 100));
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(adminLabel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        adminPanel.setBackground(new Color(240, 240, 245));
        
        JButton gradeBtn = createSmallButton("📊 Grades", new Color(156, 39, 176));
        gradeBtn.addActionListener(e -> new GradeView().setVisible(true));
        adminPanel.add(gradeBtn);
        
        JButton faculteBtn = createSmallButton("🏫 Facultés", new Color(0, 150, 136));
        faculteBtn.addActionListener(e -> new FaculteView().setVisible(true));
        adminPanel.add(faculteBtn);
        
        adminPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(adminPanel);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(450, 1));
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(separator);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton quitterBtn = createMenuButton("🚪 Quitter l'Application", new Color(244, 67, 54));
        quitterBtn.addActionListener(e -> System.exit(0));
        mainPanel.add(quitterBtn);
        
        add(mainPanel);
    }
    
    private JButton createMenuButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setMaximumSize(new Dimension(350, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private JButton createSmallButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}