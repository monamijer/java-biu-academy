package views;

import controllers.FaculteController;
import models.Faculte;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FaculteView extends JFrame {
    
    private FaculteController controller;
    private JTextField nomFacField, codeFacField, adresseField, telephoneField, emailField, doyenField;
    private JTable tableFaculte;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    
    public FaculteView() {
        this.controller = new FaculteController();
        initComponents();
        chargerDonnees();
    }
    
    private void initComponents() {
        setTitle("Gestion des Facultés - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("📝 Ajouter une Faculté", createFormPanel());
        tabbedPane.addTab("📋 Liste des Facultés", createListePanel());
        
        add(tabbedPane);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titre
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titre = new JLabel("AJOUTER UNE NOUVELLE FACULTÉ");
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        titre.setForeground(new Color(0, 102, 204));
        titre.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titre, gbc);
        
        gbc.gridwidth = 1;
        
        // Nom Faculté
        gbc.gridy = 1; gbc.gridx = 0;
        JLabel nomLabel = new JLabel("Nom de la Faculté:");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(nomLabel, gbc);
        gbc.gridx = 1;
        nomFacField = new JTextField(25);
        nomFacField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(nomFacField, gbc);
        
        // Code Faculté
        gbc.gridy = 2; gbc.gridx = 0;
        JLabel codeLabel = new JLabel("Code Faculté:");
        codeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(codeLabel, gbc);
        gbc.gridx = 1;
        codeFacField = new JTextField(10);
        codeFacField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(codeFacField, gbc);
        
        // Adresse
        gbc.gridy = 3; gbc.gridx = 0;
        JLabel adresseLabel = new JLabel("Adresse:");
        adresseLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(adresseLabel, gbc);
        gbc.gridx = 1;
        adresseField = new JTextField(25);
        adresseField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(adresseField, gbc);
        
        // Téléphone
        gbc.gridy = 4; gbc.gridx = 0;
        JLabel telLabel = new JLabel("Téléphone:");
        telLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(telLabel, gbc);
        gbc.gridx = 1;
        telephoneField = new JTextField(15);
        telephoneField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(telephoneField, gbc);
        
        // Email
        gbc.gridy = 5; gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(emailField, gbc);
        
        // Doyen
        gbc.gridy = 6; gbc.gridx = 0;
        JLabel doyenLabel = new JLabel("Doyen:");
        doyenLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(doyenLabel, gbc);
        gbc.gridx = 1;
        doyenField = new JTextField(25);
        doyenField.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(doyenField, gbc);
        
        // Boutons
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 250));
        
        JButton ajouterBtn = new JButton("✅ Ajouter la Faculté");
        ajouterBtn.setFont(new Font("Arial", Font.BOLD, 13));
        ajouterBtn.setBackground(new Color(76, 175, 80));
        ajouterBtn.setForeground(Color.WHITE);
        ajouterBtn.setFocusPainted(false);
        ajouterBtn.setPreferredSize(new Dimension(180, 40));
        ajouterBtn.addActionListener(e -> ajouterFaculte());
        
        JButton resetBtn = new JButton("🔄 Réinitialiser");
        resetBtn.setFont(new Font("Arial", Font.BOLD, 13));
        resetBtn.setBackground(new Color(255, 152, 0));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFocusPainted(false);
        resetBtn.setPreferredSize(new Dimension(150, 40));
        resetBtn.addActionListener(e -> resetForm());
        
        buttonPanel.add(ajouterBtn);
        buttonPanel.add(resetBtn);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createListePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table
        String[] colonnes = {"ID", "Code", "Nom Faculté", "Adresse", "Téléphone", "Email", "Doyen", "Date Création"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableFaculte = new JTable(tableModel);
        tableFaculte.setFont(new Font("Arial", Font.PLAIN, 11));
        tableFaculte.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableFaculte.setRowHeight(25);
        tableFaculte.setSelectionBackground(new Color(232, 240, 254));
        tableFaculte.setSelectionForeground(Color.BLACK);
        
        // Ajuster les largeurs des colonnes
        tableFaculte.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tableFaculte.getColumnModel().getColumn(1).setPreferredWidth(60);  // Code
        tableFaculte.getColumnModel().getColumn(2).setPreferredWidth(180); // Nom
        tableFaculte.getColumnModel().getColumn(3).setPreferredWidth(130); // Adresse
        tableFaculte.getColumnModel().getColumn(4).setPreferredWidth(100); // Téléphone
        tableFaculte.getColumnModel().getColumn(5).setPreferredWidth(150); // Email
        tableFaculte.getColumnModel().getColumn(6).setPreferredWidth(130); // Doyen
        
        JScrollPane scrollPane = new JScrollPane(tableFaculte);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Facultés Enregistrées"));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JButton refreshBtn = createStyledButton("🔄 Actualiser", new Color(33, 150, 243));
        refreshBtn.addActionListener(e -> chargerDonnees());
        
        JButton modifierBtn = createStyledButton("✏️ Modifier", new Color(255, 152, 0));
        modifierBtn.addActionListener(e -> preparerModification());
        
        JButton supprimerBtn = createStyledButton("🗑️ Supprimer", new Color(244, 67, 54));
        supprimerBtn.addActionListener(e -> supprimerFaculte());
        
        JButton fermerBtn = createStyledButton("❌ Fermer", new Color(158, 158, 158));
        fermerBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(modifierBtn);
        buttonPanel.add(supprimerBtn);
        buttonPanel.add(fermerBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 35));
        
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
    
    private void ajouterFaculte() {
        String nomFac = nomFacField.getText().trim();
        String codeFac = codeFacField.getText().trim();
        
        // Validation
        if (nomFac.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "❌ Le nom de la faculté est obligatoire !", 
                "Erreur de validation", 
                JOptionPane.ERROR_MESSAGE);
            nomFacField.requestFocus();
            return;
        }
        
        if (codeFac.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "❌ Le code de la faculté est obligatoire !", 
                "Erreur de validation", 
                JOptionPane.ERROR_MESSAGE);
            codeFacField.requestFocus();
            return;
        }
        
        String adresse = adresseField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String email = emailField.getText().trim();
        String doyen = doyenField.getText().trim();
        
        boolean success = controller.ajouterFaculte(nomFac, codeFac, adresse, telephone, email, doyen);
        
        if (success) {
            resetForm();
            chargerDonnees();
            tabbedPane.setSelectedIndex(1); // Basculer vers l'onglet liste
        }
    }
    
    private void resetForm() {
        nomFacField.setText("");
        codeFacField.setText("");
        adresseField.setText("");
        telephoneField.setText("");
        emailField.setText("");
        doyenField.setText("");
        nomFacField.requestFocus();
    }
    
    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Faculte> facultes = controller.getAllFacultes();
        
        for (Faculte f : facultes) {
            tableModel.addRow(new Object[]{
                f.getIdFac(),
                f.getCodeFac(),
                f.getNomFac(),
                f.getAdresse(),
                f.getTelephone(),
                f.getEmail(),
                f.getDoyen(),
                f.getDateCreation()
            });
        }
        
        // Mettre à jour le titre de l'onglet
        tabbedPane.setTitleAt(1, "📋 Liste des Facultés (" + facultes.size() + ")");
    }
    
    private void preparerModification() {
        int selectedRow = tableFaculte.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une faculté à modifier", 
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Faculte faculte = controller.getFaculteById(id);
        
        if (faculte != null) {
            // Remplir le formulaire
            nomFacField.setText(faculte.getNomFac());
            codeFacField.setText(faculte.getCodeFac());
            adresseField.setText(faculte.getAdresse());
            telephoneField.setText(faculte.getTelephone());
            emailField.setText(faculte.getEmail());
            doyenField.setText(faculte.getDoyen());
            
            // Basculer vers l'onglet formulaire
            tabbedPane.setSelectedIndex(0);
            
            // Créer un bouton de mise à jour temporaire
            int choix = JOptionPane.showConfirmDialog(this,
                "Voulez-vous mettre à jour cette faculté ?\n\n" +
                "ID: " + id + "\n" +
                "Nom: " + faculte.getNomFac(),
                "Confirmation de modification",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (choix == JOptionPane.YES_OPTION) {
                // Mettre à jour l'objet avec les nouvelles valeurs
                faculte.setNomFac(nomFacField.getText().trim());
                faculte.setCodeFac(codeFacField.getText().trim());
                faculte.setAdresse(adresseField.getText().trim());
                faculte.setTelephone(telephoneField.getText().trim());
                faculte.setEmail(emailField.getText().trim());
                faculte.setDoyen(doyenField.getText().trim());
                
                if (controller.modifierFaculte(faculte)) {
                    resetForm();
                    chargerDonnees();
                    tabbedPane.setSelectedIndex(1);
                }
            } else {
                resetForm();
                tabbedPane.setSelectedIndex(1);
            }
        }
    }
    
    private void supprimerFaculte() {
        int selectedRow = tableFaculte.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une faculté à supprimer", 
                "Aucune sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nomFac = (String) tableModel.getValueAt(selectedRow, 2);
        String codeFac = (String) tableModel.getValueAt(selectedRow, 1);
        
        boolean success = controller.supprimerFaculte(id, codeFac, nomFac);
        
        if (success) {
            chargerDonnees();
        }
    }
}