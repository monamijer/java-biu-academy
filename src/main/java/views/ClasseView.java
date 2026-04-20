package views;

import controllers.ClasseController;
import models.Classe;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClasseView extends JFrame {
    
    private ClasseController controller;
    private JTextField nomClasseField, idFacField, responsableField, nomProjetField;
    private JComboBox<String> typeClasseCombo;
    private JTextArea descriptionArea;
    private JTable tableClasse;
    private DefaultTableModel tableModel;
    
    public ClasseView() {
        this.controller = new ClasseController();
        initComponents();
        chargerDonnees();
    }
    
    private void initComponents() {
        setTitle("Gestion des Classes - VotrePrenom");
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
        JLabel titre = new JLabel("AJOUTER UNE CLASSE");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(0, 102, 204));
        titre.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titre, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Nom Classe:"), gbc);
        gbc.gridx = 1;
        nomClasseField = new JTextField(20);
        panel.add(nomClasseField, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("ID Fac:"), gbc);
        gbc.gridx = 1;
        idFacField = new JTextField(20);
        panel.add(idFacField, gbc);
        
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Responsable:"), gbc);
        gbc.gridx = 1;
        responsableField = new JTextField(20);
        panel.add(responsableField, gbc);
        
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(new JLabel("Nom Projet:"), gbc);
        gbc.gridx = 1;
        nomProjetField = new JTextField(20);
        panel.add(nomProjetField, gbc);
        
        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(new JLabel("Type Classe:"), gbc);
        gbc.gridx = 1;
        typeClasseCombo = new JComboBox<>(new String[]{"TP", "TD", "Cours", "Projet"});
        panel.add(typeClasseCombo, gbc);
        
        gbc.gridy = 6; gbc.gridx = 0;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 20);
        panel.add(new JScrollPane(descriptionArea), gbc);
        
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton ajouterBtn = new JButton("✅ Ajouter");
        ajouterBtn.setBackground(new Color(76, 175, 80));
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.addActionListener(e -> ajouterClasse());
        panel.add(ajouterBtn, gbc);
        
        return panel;
    }
    
    private JPanel createListePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] colonnes = {"ID", "Nom Classe", "ID Fac", "Responsable", "Nom Projet", "Type", "Description"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableClasse = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableClasse);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("🔄 Actualiser");
        refreshBtn.addActionListener(e -> chargerDonnees());
        panel.add(refreshBtn, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void ajouterClasse() {
        boolean success = controller.ajouterClasse(
            nomClasseField.getText(),
            idFacField.getText(),
            responsableField.getText(),
            nomProjetField.getText(),
            (String) typeClasseCombo.getSelectedItem(),
            descriptionArea.getText()
        );
        
        if (success) {
            nomClasseField.setText("");
            idFacField.setText("");
            responsableField.setText("");
            nomProjetField.setText("");
            descriptionArea.setText("");
            chargerDonnees();
        }
    }
    
    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Classe> classes = controller.getAllClasses();
        
        for (Classe c : classes) {
            tableModel.addRow(new Object[]{
                c.getIdClasse(),
                c.getNomClasse(),
                c.getIdFac(),
                c.getResponsable(),
                c.getNomProjet(),
                c.getTypeClasse(),
                c.getDescription()
            });
        }
    }
}