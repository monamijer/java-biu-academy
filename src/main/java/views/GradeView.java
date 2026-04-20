package views;

import controllers.GradeController;
import models.Grade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GradeView extends JFrame {
    
    private GradeController controller;
    private JTextField nomGradeField, niveauField;
    private JTextArea descriptionArea;
    private JTable tableGrade;
    private DefaultTableModel tableModel;
    
    public GradeView() {
        this.controller = new GradeController();
        initComponents();
        chargerDonnees();
    }
    
    private void initComponents() {
        setTitle("Gestion des Grades - VotrePrenom");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📝 Ajouter/Modifier", createFormPanel());
        tabbedPane.addTab("📋 Liste des Grades", createListePanel());
        
        add(tabbedPane);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titre = new JLabel("GESTION DES GRADES");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(0, 102, 204));
        titre.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titre, gbc);
        
        // Champs
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Nom du Grade:"), gbc);
        gbc.gridx = 1;
        nomGradeField = new JTextField(20);
        panel.add(nomGradeField, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Niveau:"), gbc);
        gbc.gridx = 1;
        niveauField = new JTextField(20);
        panel.add(niveauField, gbc);
        
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        panel.add(new JScrollPane(descriptionArea), gbc);
        
        // Boutons
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton ajouterBtn = new JButton("✅ Ajouter");
        ajouterBtn.setBackground(new Color(76, 175, 80));
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.addActionListener(e -> ajouterGrade());
        
        JButton resetBtn = new JButton("🔄 Réinitialiser");
        resetBtn.setBackground(new Color(255, 152, 0));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.addActionListener(e -> resetForm());
        
        buttonPanel.add(ajouterBtn);
        buttonPanel.add(resetBtn);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createListePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] colonnes = {"ID", "Nom Grade", "Niveau", "Description", "Date Création"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableGrade = new JTable(tableModel);
        tableGrade.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(tableGrade);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton refreshBtn = new JButton("🔄 Actualiser");
        refreshBtn.setBackground(new Color(33, 150, 243));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(e -> chargerDonnees());
        
        JButton modifierBtn = new JButton("✏️ Modifier");
        modifierBtn.setBackground(new Color(255, 152, 0));
        modifierBtn.setForeground(Color.WHITE);
        modifierBtn.addActionListener(e -> preparerModification());
        
        JButton supprimerBtn = new JButton("🗑️ Supprimer");
        supprimerBtn.setBackground(new Color(244, 67, 54));
        supprimerBtn.setForeground(Color.WHITE);
        supprimerBtn.addActionListener(e -> supprimerGrade());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(modifierBtn);
        buttonPanel.add(supprimerBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void ajouterGrade() {
        boolean success = controller.ajouterGrade(
            nomGradeField.getText(),
            niveauField.getText(),
            descriptionArea.getText()
        );
        
        if (success) {
            resetForm();
            chargerDonnees();
        }
    }
    
    private void resetForm() {
        nomGradeField.setText("");
        niveauField.setText("");
        descriptionArea.setText("");
    }
    
    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Grade> grades = controller.getAllGrades();
        
        for (Grade g : grades) {
            tableModel.addRow(new Object[]{
                g.getIdGrade(),
                g.getNomGrade(),
                g.getNiveau(),
                g.getDescription(),
                g.getDateCreation()
            });
        }
    }
    
    private void preparerModification() {
        int selectedRow = tableGrade.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un grade à modifier");
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Grade grade = controller.getGradeById(id);
        
        if (grade != null) {
            nomGradeField.setText(grade.getNomGrade());
            niveauField.setText(grade.getNiveau());
            descriptionArea.setText(grade.getDescription());
            
            // Changer l'onglet
            JTabbedPane tabbedPane = (JTabbedPane) getContentPane().getComponent(0);
            tabbedPane.setSelectedIndex(0);
            
            // Ajouter un bouton de mise à jour temporaire
            // (Dans une vraie application, on aurait un bouton dédié)
            int choix = JOptionPane.showConfirmDialog(this,
                "Voulez-vous mettre à jour ce grade ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            
            if (choix == JOptionPane.YES_OPTION) {
                grade.setNomGrade(nomGradeField.getText());
                grade.setNiveau(niveauField.getText());
                grade.setDescription(descriptionArea.getText());
                
                if (controller.modifierGrade(grade)) {
                    resetForm();
                    chargerDonnees();
                }
            }
        }
    }
    
    private void supprimerGrade() {
        int selectedRow = tableGrade.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un grade à supprimer");
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nomGrade = (String) tableModel.getValueAt(selectedRow, 1);
        
        if (controller.supprimerGrade(id, nomGrade)) {
            chargerDonnees();
        }
    }
}