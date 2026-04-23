package views;

import controllers.CoursController;
import models.Cours;
import models.Ensemble;
import models.Faculte;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursView extends JFrame {

    private CoursController controller;
    
    private JTextField codeField, intituleField, vhField;
    private JTextArea descriptionArea;
    private JComboBox<Ensemble> professeurCombo;
    private JComboBox<Faculte> faculteCombo;
    private JTable tableCours;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private Cours coursEnEdition = null;
    private JButton actionBtn;
    private JLabel titreForm;

    public CoursView() {
        this.controller = new CoursController();
        initComponents();
        chargerDonnees();
    }

    private void initComponents() {
        setTitle("Gestion des Cours - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("📝 Formulaire", createFormPanel());
        tabbedPane.addTab("📋 Liste des Cours", createListePanel());
        add(tabbedPane);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        titreForm = new JLabel("AJOUTER UN COURS");
        titreForm.setFont(new Font("Arial", Font.BOLD, 16));
        titreForm.setForeground(new Color(0, 102, 204));
        titreForm.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titreForm, gbc);

        gbc.gridwidth = 1;
        
        // Code
        gbc.gridy = 1; gbc.gridx = 0; 
        panel.add(boldLabel("Code :"), gbc);
        gbc.gridx = 1; 
        codeField = new JTextField(20); 
        panel.add(codeField, gbc);

        // Intitulé
        gbc.gridy = 2; gbc.gridx = 0; 
        panel.add(boldLabel("Intitulé :"), gbc);
        gbc.gridx = 1; 
        intituleField = new JTextField(20); 
        panel.add(intituleField, gbc);

        // Professeur (ComboBox)
        gbc.gridy = 3; gbc.gridx = 0; 
        panel.add(boldLabel("Professeur :"), gbc);
        gbc.gridx = 1; 
        professeurCombo = new JComboBox<>();
        chargerProfesseurs();
        panel.add(professeurCombo, gbc);

        // Faculté (ComboBox)
        gbc.gridy = 4; gbc.gridx = 0; 
        panel.add(boldLabel("Faculté :"), gbc);
        gbc.gridx = 1; 
        faculteCombo = new JComboBox<>();
        chargerFacultes();
        panel.add(faculteCombo, gbc);

        // Volume Horaire
        gbc.gridy = 5; gbc.gridx = 0; 
        panel.add(boldLabel("VH (Volume Horaire) :"), gbc);
        gbc.gridx = 1; 
        vhField = new JTextField(20); 
        panel.add(vhField, gbc);

        // Description
        gbc.gridy = 6; gbc.gridx = 0; 
        panel.add(boldLabel("Description :"), gbc);
        gbc.gridx = 1; 
        descriptionArea = new JTextArea(4, 20); 
        descriptionArea.setLineWrap(true); 
        descriptionArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(descriptionArea), gbc);

        // Boutons
        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bp.setBackground(new Color(245, 245, 250));
        
        actionBtn = styledBtn("✅ Ajouter le Cours", new Color(76, 175, 80), 190, 40);
        actionBtn.addActionListener(e -> sauvegarder());
        
        JButton resetBtn = styledBtn("🔄 Réinitialiser", new Color(255, 152, 0), 160, 40);
        resetBtn.addActionListener(e -> resetForm());
        
        bp.add(actionBtn); 
        bp.add(resetBtn);
        panel.add(bp, gbc);
        
        return panel;
    }

    private JPanel createListePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Code", "Intitulé", "Professeur", "Faculté", "VH", "Description", "Date"};
        tableModel = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        tableCours = new JTable(tableModel);
        tableCours.setFont(new Font("Arial", Font.PLAIN, 11));
        tableCours.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableCours.setRowHeight(25);
        tableCours.setSelectionBackground(new Color(232, 240, 254));
        
        JScrollPane sp = new JScrollPane(tableCours);
        sp.setBorder(BorderFactory.createTitledBorder("Liste des Cours"));
        panel.add(sp, BorderLayout.CENTER);

        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton refreshBtn = styledBtn("🔄 Actualiser", new Color(33, 150, 243), 130, 35);
        refreshBtn.addActionListener(e -> {
            chargerProfesseurs();
            chargerFacultes();
            chargerDonnees();
        });
        JButton modBtn = styledBtn("✏️ Modifier", new Color(255, 152, 0), 120, 35);
        modBtn.addActionListener(e -> preparerModification());
        JButton delBtn = styledBtn("🗑️ Supprimer", new Color(244, 67, 54), 130, 35);
        delBtn.addActionListener(e -> supprimerCours());
        
        bp.add(refreshBtn); 
        bp.add(modBtn); 
        bp.add(delBtn);
        panel.add(bp, BorderLayout.SOUTH);
        return panel;
    }

    private void chargerProfesseurs() {
        professeurCombo.removeAllItems();
        List<Ensemble> professeurs = controller.getAllProfesseurs();
        for (Ensemble prof : professeurs) {
            professeurCombo.addItem(prof);
        }
    }

    private void chargerFacultes() {
        faculteCombo.removeAllItems();
        List<Faculte> facultes = controller.getAllFacultes();
        for (Faculte fac : facultes) {
            faculteCombo.addItem(fac);
        }
    }

    private void sauvegarder() {
        String code = codeField.getText().trim();
        String intitule = intituleField.getText().trim();
        String vhStr = vhField.getText().trim();
        String desc = descriptionArea.getText().trim();
        
        Ensemble profSelectionne = (Ensemble) professeurCombo.getSelectedItem();
        Faculte facSelectionnee = (Faculte) faculteCombo.getSelectedItem();

        if (code.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "❌ Le code est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return; 
        }
        if (intitule.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "❌ L'intitulé est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return; 
        }
        if (profSelectionne == null) {
            JOptionPane.showMessageDialog(this, "❌ Veuillez sélectionner un professeur !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return;
        }
        if (facSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "❌ Veuillez sélectionner une faculté !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return;
        }
        
        int vh;
        try { 
            vh = Integer.parseInt(vhStr); 
            if (vh <= 0) {
                JOptionPane.showMessageDialog(this, "❌ Le volume horaire doit être positif !", "Validation", JOptionPane.ERROR_MESSAGE); 
                return;
            }
        } catch (NumberFormatException ex) { 
            JOptionPane.showMessageDialog(this, "❌ VH doit être un nombre entier !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return; 
        }

        if (coursEnEdition == null) {
            if (controller.ajouterCours(code, intitule, vh, profSelectionne.getId(), 
                                       facSelectionnee.getIdFac(), desc)) { 
                resetForm(); 
                chargerDonnees(); 
                tabbedPane.setSelectedIndex(1); 
            }
        } else {
            coursEnEdition.setCode(code);
            coursEnEdition.setIntitule(intitule);
            coursEnEdition.setVh(vh);
            coursEnEdition.setProfesseurId(profSelectionne.getId());
            coursEnEdition.setFaculteId(facSelectionnee.getIdFac());
            coursEnEdition.setDescription(desc);
            
            if (controller.modifierCours(coursEnEdition)) { 
                resetForm(); 
                chargerDonnees(); 
                tabbedPane.setSelectedIndex(1); 
            }
        }
    }

    private void preparerModification() {
        int row = tableCours.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Sélectionnez un cours à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); 
            return; 
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Cours c = controller.getCoursById(id);
        if (c == null) return;
        
        codeField.setText(c.getCode());
        intituleField.setText(c.getIntitule());
        vhField.setText(String.valueOf(c.getVh()));
        descriptionArea.setText(c.getDescription() != null ? c.getDescription() : "");
        
        for (int i = 0; i < professeurCombo.getItemCount(); i++) {
            Ensemble prof = professeurCombo.getItemAt(i);
            if (prof.getId() == c.getProfesseurId()) {
                professeurCombo.setSelectedIndex(i);
                break;
            }
        }
        
        for (int i = 0; i < faculteCombo.getItemCount(); i++) {
            Faculte fac = faculteCombo.getItemAt(i);
            if (fac.getIdFac() == c.getFaculteId()) {
                faculteCombo.setSelectedIndex(i);
                break;
            }
        }
        
        coursEnEdition = c;
        titreForm.setText("MODIFIER LE COURS : " + c.getCode());
        titreForm.setForeground(new Color(200, 80, 0));
        actionBtn.setText("💾 Enregistrer les modifications");
        actionBtn.setBackground(new Color(255, 152, 0));
        tabbedPane.setTitleAt(0, "✏️ Modifier Cours");
        tabbedPane.setSelectedIndex(0);
    }

    private void resetForm() {
        codeField.setText(""); 
        intituleField.setText("");
        vhField.setText(""); 
        descriptionArea.setText("");
        if (professeurCombo.getItemCount() > 0) professeurCombo.setSelectedIndex(0);
        if (faculteCombo.getItemCount() > 0) faculteCombo.setSelectedIndex(0);
        codeField.requestFocus();
        coursEnEdition = null;
        titreForm.setText("AJOUTER UN COURS");
        titreForm.setForeground(new Color(0, 102, 204));
        actionBtn.setText("✅ Ajouter le Cours");
        actionBtn.setBackground(new Color(76, 175, 80));
        tabbedPane.setTitleAt(0, "📝 Formulaire");
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Cours> list = controller.getAllCours();
        for (Cours c : list) {
            tableModel.addRow(new Object[]{ 
                c.getId(), 
                c.getCode(), 
                c.getIntitule(),
                c.getProfesseurNom() != null ? c.getProfesseurNom() : "N/A",
                c.getFaculteNom() != null ? c.getFaculteNom() : "N/A",
                c.getVh(), 
                c.getDescription(), 
                c.getDateCreation() 
            });
        }
        tabbedPane.setTitleAt(1, "📋 Liste des Cours (" + list.size() + ")");
    }

    private void supprimerCours() {
        int row = tableCours.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Sélectionnez un cours à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); 
            return; 
        }
        int id = (int) tableModel.getValueAt(row, 0);
        String code = (String) tableModel.getValueAt(row, 1);
        if (controller.supprimerCours(id, code)) chargerDonnees();
    }

    private JLabel boldLabel(String t) { 
        JLabel l = new JLabel(t); 
        l.setFont(new Font("Arial", Font.BOLD, 12)); 
        return l; 
    }
    
    private JButton styledBtn(String t, Color bg, int w, int h) {
        JButton b = new JButton(t); 
        b.setFont(new Font("Arial", Font.BOLD, 12)); 
        b.setBackground(bg); 
        b.setForeground(Color.WHITE); 
        b.setFocusPainted(false); 
        b.setPreferredSize(new Dimension(w, h));
        b.addMouseListener(new java.awt.event.MouseAdapter() { 
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(bg.darker());
            } 
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(bg);
            } 
        });
        return b;
    }
}