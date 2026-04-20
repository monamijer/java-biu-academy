package views;

import controllers.CoursController;
import models.Cours;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursView extends JFrame {
    
    private CoursController controller;
    private JTextField codeField, vhField, mardiProjetField;
    private JTextArea descriptionArea;
    private JCheckBox divisibleCheck;
    private JTable tableCours;
    private DefaultTableModel tableModel;
    
    public CoursView() {
        this.controller = new CoursController();
        initComponents();
        chargerDonnees();
    }
    
    private void initComponents() {
        setTitle("Gestion des Cours - VotrePrenom");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📝 Ajouter", createAjouterPanel());
        tabbedPane.addTab("📋 Liste", createListePanel());
        
        add(tabbedPane);
    }
    
    private JPanel createAjouterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titre = new JLabel("AJOUTER UN COURS");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(0, 102, 204));
        titre.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titre, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Code:"), gbc);
        gbc.gridx = 1;
        codeField = new JTextField(20);
        panel.add(codeField, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Divisible:"), gbc);
        gbc.gridx = 1;
        divisibleCheck = new JCheckBox("Oui");
        panel.add(divisibleCheck, gbc);
        
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("VH:"), gbc);
        gbc.gridx = 1;
        vhField = new JTextField(20);
        panel.add(vhField, gbc);
        
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(new JLabel("Mardi Projet FK:"), gbc);
        gbc.gridx = 1;
        mardiProjetField = new JTextField(20);
        panel.add(mardiProjetField, gbc);
        
        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        panel.add(new JScrollPane(descriptionArea), gbc);
        
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        JButton ajouterBtn = new JButton("✅ Ajouter");
        ajouterBtn.setBackground(new Color(76, 175, 80));
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.addActionListener(e -> ajouterCours());
        buttonPanel.add(ajouterBtn);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createListePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] colonnes = {"ID", "Code", "Divisible", "VH", "Mardi Projet FK", "Description", "Date"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableCours = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCours);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("🔄 Actualiser");
        refreshBtn.addActionListener(e -> chargerDonnees());
        panel.add(refreshBtn, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void ajouterCours() {
        boolean success = controller.ajouterCours(
            codeField.getText(),
            divisibleCheck.isSelected(),
            vhField.getText(),
            mardiProjetField.getText(),
            descriptionArea.getText()
        );
        
        if (success) {
            codeField.setText("");
            divisibleCheck.setSelected(false);
            vhField.setText("");
            mardiProjetField.setText("");
            descriptionArea.setText("");
            chargerDonnees();
        }
    }
    
    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Cours> coursList = controller.getAllCours();
        
        for (Cours c : coursList) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getCode(),
                c.isDivisible() ? "Oui" : "Non",
                c.getVh(),
                c.getMardiProjetFk(),
                c.getDescription(),
                c.getDateCreation()
            });
        }
    }
}