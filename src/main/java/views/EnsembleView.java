package views;

import controllers.EnsembleController;
import models.Ensemble;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EnsembleView extends JFrame {
    
    private EnsembleController controller;
    private JTextField nomField, prenomField, nationaliteField, gradeField, dateNaissanceField, emailField, telephoneField;
    private JComboBox<String> genreCombo;
    private JTable tableEnsemble;
    private DefaultTableModel tableModel;
    
    
    public EnsembleView() {
        this.controller = new EnsembleController();
        initComponents();
        chargerDonnees();
    }
    
    private void initComponents() {
        setTitle("Gestion des Ensembles - VotrePrenom");
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
        
        // Titre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titre = new JLabel("AJOUTER UN ENSEMBLE");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(0, 102, 204));
        titre.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titre, gbc);
        
        // Champs
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        panel.add(nomField, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        panel.add(prenomField, gbc);
        
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Nationalité:"), gbc);
        gbc.gridx = 1;
        nationaliteField = new JTextField(20);
        panel.add(nationaliteField, gbc);
        
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(new JLabel("Genre:"), gbc);
        gbc.gridx = 1;
        genreCombo = new JComboBox<>(new String[]{"M", "F"});
        panel.add(genreCombo, gbc);
        
        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(new JLabel("Grade FK:"), gbc);
        gbc.gridx = 1;
        gradeField = new JTextField(20);
        panel.add(gradeField, gbc);
        
        // Boutons
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton ajouterBtn = new JButton("✅ Ajouter");
        ajouterBtn.setBackground(new Color(76, 175, 80));
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.addActionListener(e -> ajouterEnsemble());
        
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
        
        // Table
        String[] colonnes = {"ID", "Nom", "Prénom", "Nationalité", "Genre", "Grade FK", "Date"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEnsemble = new JTable(tableModel);
        tableEnsemble.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(tableEnsemble);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton refreshBtn = new JButton("🔄 Actualiser");
        refreshBtn.addActionListener(e -> chargerDonnees());
        
        JButton supprimerBtn = new JButton("🗑️ Supprimer");
        supprimerBtn.addActionListener(e -> supprimerEnsemble());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(supprimerBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    
  private void ajouterEnsemble() {
    boolean success = controller.ajouterEnsemble(
        nomField.getText(),
        prenomField.getText(),
        nationaliteField.getText(),
        (String) genreCombo.getSelectedItem(),
        gradeField.getText(),
        dateNaissanceField.getText(),  
        telephoneField.getText(),       
        emailField.getText()           
    );
    
    if (success) {
        resetForm();
        chargerDonnees();
    }
}
    
    private void resetForm() {
        nomField.setText("");
        prenomField.setText("");
        nationaliteField.setText("");
        genreCombo.setSelectedIndex(0);
        gradeField.setText("");
    }
    
    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Ensemble> ensembles = controller.getAllEnsembles();
        
        for (Ensemble e : ensembles) {
            tableModel.addRow(new Object[]{
                e.getId(),
                e.getNom(),
                e.getPrenom(),
                e.getNationalite(),
                e.getGenre(),
                e.getGradeFk(),
                e.getDateCreation()
            });
        }
    }
    
    private void supprimerEnsemble() {
        int selectedRow = tableEnsemble.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un ensemble à supprimer");
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nom = tableModel.getValueAt(selectedRow, 2) + " " + tableModel.getValueAt(selectedRow, 1);
        
        if (controller.supprimerEnsemble(id, nom)) {
            chargerDonnees();
        }
    }
    
}