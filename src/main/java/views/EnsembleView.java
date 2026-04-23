package views;

import controllers.EnsembleController;
import controllers.GradeController;
import models.Ensemble;
import models.Grade;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class EnsembleView extends JFrame {

    private EnsembleController controller;
    private GradeController gradeController;

    // Champs du formulaire
    private JTextField nomField, prenomField, nationaliteField, dateNaissanceField, emailField, telephoneField;
    private JComboBox<String> genreCombo;
    private JComboBox<Grade> gradeCombo;

    private JTable tableEnsemble;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private Ensemble ensembleEnEdition = null;
    private JButton actionBtn;
    private JLabel titreForm;

    public EnsembleView() {
        this.controller = new EnsembleController();
        this.gradeController = new GradeController();
        initComponents();
        chargerDonnees();
    }

    private void initComponents() {
        setTitle("Gestion des Enseignants - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("📝 Formulaire", createFormPanel());
        tabbedPane.addTab("📋 Liste des Enseignants", createListePanel());
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
        titreForm = new JLabel("AJOUTER UN ENSEIGNANT");
        titreForm.setFont(new Font("Arial", Font.BOLD, 16));
        titreForm.setForeground(new Color(0, 102, 204));
        titreForm.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titreForm, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(boldLabel("Nom :"), gbc);
        gbc.gridx = 1; nomField = new JTextField(20); panel.add(nomField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(boldLabel("Prénom :"), gbc);
        gbc.gridx = 1; prenomField = new JTextField(20); panel.add(prenomField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panel.add(boldLabel("Nationalité :"), gbc);
        gbc.gridx = 1; nationaliteField = new JTextField(20); panel.add(nationaliteField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; panel.add(boldLabel("Genre :"), gbc);
        gbc.gridx = 1; genreCombo = new JComboBox<>(new String[]{"M", "F"}); panel.add(genreCombo, gbc);

        gbc.gridy = 5; gbc.gridx = 0; panel.add(boldLabel("Grade :"), gbc);
        gbc.gridx = 1; gradeCombo = new JComboBox<>(); chargerGradesCombo(); panel.add(gradeCombo, gbc);

        gbc.gridy = 6; gbc.gridx = 0; panel.add(boldLabel("Date de naissance (AAAA-MM-JJ) :"), gbc);
        gbc.gridx = 1; dateNaissanceField = new JTextField(20); panel.add(dateNaissanceField, gbc);

        gbc.gridy = 7; gbc.gridx = 0; panel.add(boldLabel("Téléphone :"), gbc);
        gbc.gridx = 1; telephoneField = new JTextField(20); panel.add(telephoneField, gbc);

        gbc.gridy = 8; gbc.gridx = 0; panel.add(boldLabel("Email :"), gbc);
        gbc.gridx = 1; emailField = new JTextField(20); panel.add(emailField, gbc);

        gbc.gridy = 9; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bp.setBackground(new Color(245, 245, 250));
        actionBtn = styledBtn("✅ Ajouter", new Color(76, 175, 80), 190, 40);
        actionBtn.addActionListener(e -> sauvegarder());
        JButton resetBtn = styledBtn("🔄 Réinitialiser", new Color(255, 152, 0), 160, 40);
        resetBtn.addActionListener(e -> resetForm());
        bp.add(actionBtn); bp.add(resetBtn);
        panel.add(bp, gbc);
        return panel;
    }

    private JPanel createListePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Nom", "Prénom", "Nationalité", "Genre", "Grade", "Date Naissance", "Téléphone", "Email"};
        tableModel = new DefaultTableModel(cols, 0) { 
            public boolean isCellEditable(int r, int c) { return false; } 
        };
        tableEnsemble = new JTable(tableModel);
        tableEnsemble.setFont(new Font("Arial", Font.PLAIN, 11));
        tableEnsemble.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableEnsemble.setRowHeight(25);
        tableEnsemble.setSelectionBackground(new Color(232, 240, 254));
        JScrollPane sp = new JScrollPane(tableEnsemble);
        sp.setBorder(BorderFactory.createTitledBorder("Liste des Enseignants"));
        panel.add(sp, BorderLayout.CENTER);

        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton refreshBtn = styledBtn("🔄 Actualiser", new Color(33, 150, 243), 130, 35);
        refreshBtn.addActionListener(e -> {
            chargerGradesCombo();
            chargerDonnees();
        });
        JButton modBtn = styledBtn("✏️ Modifier", new Color(255, 152, 0), 120, 35);
        modBtn.addActionListener(e -> preparerModification());
        JButton delBtn = styledBtn("🗑️ Supprimer", new Color(244, 67, 54), 130, 35);
        delBtn.addActionListener(e -> supprimerEnsemble());
        bp.add(refreshBtn); bp.add(modBtn); bp.add(delBtn);
        panel.add(bp, BorderLayout.SOUTH);
        return panel;
    }

    private void chargerGradesCombo() {
        gradeCombo.removeAllItems();
        List<Grade> grades = gradeController.getAllGrades();
        for (Grade g : grades) {
            gradeCombo.addItem(g);
        }
    }

    private void sauvegarder() {
        String nom   = nomField.getText().trim();
        String pren  = prenomField.getText().trim();
        String nat   = nationaliteField.getText().trim();
        String genre = (String) genreCombo.getSelectedItem();
        Grade grade  = (Grade) gradeCombo.getSelectedItem();
        String dateN = dateNaissanceField.getText().trim();
        String tel   = telephoneField.getText().trim();
        String email = emailField.getText().trim();

        if (nom.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "❌ Le nom est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return; 
        }
        if (pren.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "❌ Le prénom est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return; 
        }
        if (nat.isEmpty()) { 
            nat = "Non spécifiée";
        }
        if (grade == null) { 
            JOptionPane.showMessageDialog(this, "❌ Veuillez sélectionner un grade !", "Validation", JOptionPane.ERROR_MESSAGE); 
            return; 
        }

        String gradeFkStr = String.valueOf(grade.getIdGrade());

        if (ensembleEnEdition == null) {
            if (controller.ajouterEnsemble(nom, pren, nat, genre, gradeFkStr, dateN, tel, email)) { 
                resetForm(); 
                chargerDonnees(); 
                tabbedPane.setSelectedIndex(1); 
            }
        } else {
            ensembleEnEdition.setNom(nom); 
            ensembleEnEdition.setPrenom(pren);
            ensembleEnEdition.setNationalite(nat); 
            ensembleEnEdition.setGenre(genre);
            ensembleEnEdition.setGradeFk(grade.getIdGrade());
            ensembleEnEdition.setTelephone(tel); 
            ensembleEnEdition.setEmail(email);
            try {
                if (!dateN.isEmpty()) {
                    ensembleEnEdition.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse(dateN));
                } else {
                    ensembleEnEdition.setDateNaissance(null);
                }
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "❌ Format de date invalide ! Utilisez AAAA-MM-JJ", "Validation", JOptionPane.ERROR_MESSAGE); 
                return; 
            }
            if (controller.modifierEnsemble(ensembleEnEdition)) { 
                resetForm(); 
                chargerDonnees(); 
                tabbedPane.setSelectedIndex(1); 
            }
        }
    }

    private void preparerModification() {
        int row = tableEnsemble.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Sélectionnez un enseignant à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); 
            return; 
        }
        int id = (int) tableModel.getValueAt(row, 0);
        Ensemble e = controller.getEnsembleById(id);
        if (e == null) return;
        
        nomField.setText(e.getNom());
        prenomField.setText(e.getPrenom());
        nationaliteField.setText(e.getNationalite() != null ? e.getNationalite() : "");
        genreCombo.setSelectedItem(e.getGenre());
        telephoneField.setText(e.getTelephone() != null ? e.getTelephone() : "");
        emailField.setText(e.getEmail() != null ? e.getEmail() : "");
        if (e.getDateNaissance() != null) {
            dateNaissanceField.setText(new SimpleDateFormat("yyyy-MM-dd").format(e.getDateNaissance()));
        } else {
            dateNaissanceField.setText("");
        }
        
        // Sélectionner le grade correspondant
        for (int i = 0; i < gradeCombo.getItemCount(); i++) {
            if (gradeCombo.getItemAt(i).getIdGrade() == e.getGradeFk()) { 
                gradeCombo.setSelectedIndex(i); 
                break; 
            }
        }
        
        ensembleEnEdition = e;
        titreForm.setText("MODIFIER L'ENSEIGNANT : " + e.getPrenom() + " " + e.getNom());
        titreForm.setForeground(new Color(200, 80, 0));
        actionBtn.setText("💾 Enregistrer les modifications");
        actionBtn.setBackground(new Color(255, 152, 0));
        tabbedPane.setTitleAt(0, "✏️ Modifier Enseignant");
        tabbedPane.setSelectedIndex(0);
    }

    private void resetForm() {
        nomField.setText(""); 
        prenomField.setText(""); 
        nationaliteField.setText("");
        dateNaissanceField.setText(""); 
        telephoneField.setText(""); 
        emailField.setText("");
        genreCombo.setSelectedIndex(0);
        if (gradeCombo.getItemCount() > 0) gradeCombo.setSelectedIndex(0);
        nomField.requestFocus();
        ensembleEnEdition = null;
        titreForm.setText("AJOUTER UN ENSEIGNANT");
        titreForm.setForeground(new Color(0, 102, 204));
        actionBtn.setText("✅ Ajouter");
        actionBtn.setBackground(new Color(76, 175, 80));
        tabbedPane.setTitleAt(0, "📝 Formulaire");
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Ensemble> list = controller.getAllEnsembles();
        for (Ensemble e : list) {
            String dateNaissanceStr = e.getDateNaissance() != null ? 
                new SimpleDateFormat("yyyy-MM-dd").format(e.getDateNaissance()) : "";
            String gradeStr = e.getGradeNom() != null ? e.getGradeNom() : String.valueOf(e.getGradeFk());
            
            tableModel.addRow(new Object[]{ 
                e.getId(), 
                e.getNom(), 
                e.getPrenom(), 
                e.getNationalite(), 
                e.getGenre(), 
                gradeStr, 
                dateNaissanceStr, 
                e.getTelephone(), 
                e.getEmail() 
            });
        }
        tabbedPane.setTitleAt(1, "📋 Liste des Enseignants (" + list.size() + ")");
    }

    private void supprimerEnsemble() {
        int row = tableEnsemble.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(this, "Sélectionnez un enseignant à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); 
            return; 
        }
        int id = (int) tableModel.getValueAt(row, 0);    
        String prenom = (String) tableModel.getValueAt(row, 2);
        String nom = (String) tableModel.getValueAt(row, 1);
        String nomComplet = prenom + " " + nom;
        
        if (controller.supprimerEnsemble(id, nomComplet)) {
            chargerDonnees();
        }
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