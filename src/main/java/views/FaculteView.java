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
    private Faculte faculteEnEdition = null;
    private JButton actionBtn;
    private JLabel titreForm;

    public FaculteView() {
        this.controller = new FaculteController();
        initComponents();
        chargerDonnees();
    }

    private void initComponents() {
        setTitle("Gestion des Facultés - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 620);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("📝 Formulaire", createFormPanel());
        tabbedPane.addTab("📋 Liste des Facultés", createListePanel());
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
        titreForm = new JLabel("AJOUTER UNE NOUVELLE FACULTÉ");
        titreForm.setFont(new Font("Arial", Font.BOLD, 16));
        titreForm.setForeground(new Color(0, 102, 204));
        titreForm.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titreForm, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(boldLabel("Nom de la Faculté :"), gbc);
        gbc.gridx = 1; nomFacField = new JTextField(25); panel.add(nomFacField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(boldLabel("Code Faculté :"), gbc);
        gbc.gridx = 1; codeFacField = new JTextField(10); panel.add(codeFacField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panel.add(boldLabel("Adresse :"), gbc);
        gbc.gridx = 1; adresseField = new JTextField(25); panel.add(adresseField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; panel.add(boldLabel("Téléphone :"), gbc);
        gbc.gridx = 1; telephoneField = new JTextField(15); panel.add(telephoneField, gbc);

        gbc.gridy = 5; gbc.gridx = 0; panel.add(boldLabel("Email :"), gbc);
        gbc.gridx = 1; emailField = new JTextField(25); panel.add(emailField, gbc);

        gbc.gridy = 6; gbc.gridx = 0; panel.add(boldLabel("Doyen :"), gbc);
        gbc.gridx = 1; doyenField = new JTextField(25); panel.add(doyenField, gbc);

        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bp.setBackground(new Color(245, 245, 250));
        actionBtn = styledBtn("✅ Ajouter la Faculté", new Color(76, 175, 80), 200, 40);
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

        String[] cols = {"ID", "Code", "Nom Faculté", "Adresse", "Téléphone", "Email", "Doyen", "Date Création"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tableFaculte = new JTable(tableModel);
        tableFaculte.setFont(new Font("Arial", Font.PLAIN, 11));
        tableFaculte.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableFaculte.setRowHeight(25);
        tableFaculte.setSelectionBackground(new Color(232, 240, 254));
        tableFaculte.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableFaculte.getColumnModel().getColumn(1).setPreferredWidth(60);
        tableFaculte.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane sp = new JScrollPane(tableFaculte);
        sp.setBorder(BorderFactory.createTitledBorder("Liste des Facultés"));
        panel.add(sp, BorderLayout.CENTER);

        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton refreshBtn = styledBtn("🔄 Actualiser", new Color(33, 150, 243), 130, 35);
        refreshBtn.addActionListener(e -> chargerDonnees());
        JButton modBtn = styledBtn("✏️ Modifier", new Color(255, 152, 0), 120, 35);
        modBtn.addActionListener(e -> preparerModification());
        JButton delBtn = styledBtn("🗑️ Supprimer", new Color(244, 67, 54), 130, 35);
        delBtn.addActionListener(e -> supprimerFaculte());
        JButton closeBtn = styledBtn("❌ Fermer", new Color(120, 120, 120), 110, 35);
        closeBtn.addActionListener(e -> dispose());
        bp.add(refreshBtn); bp.add(modBtn); bp.add(delBtn); bp.add(closeBtn);
        panel.add(bp, BorderLayout.SOUTH);
        return panel;
    }

    private void sauvegarder() {
        String nom   = nomFacField.getText().trim();
        String code  = codeFacField.getText().trim();
        String adr   = adresseField.getText().trim();
        String tel   = telephoneField.getText().trim();
        String email = emailField.getText().trim();
        String doyen = doyenField.getText().trim();

        if (nom.isEmpty()) { JOptionPane.showMessageDialog(this, "❌ Le nom est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); nomFacField.requestFocus(); return; }
        if (code.isEmpty()) { JOptionPane.showMessageDialog(this, "❌ Le code est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); codeFacField.requestFocus(); return; }
        if (!email.isEmpty() && !email.contains("@")) { JOptionPane.showMessageDialog(this, "❌ Email invalide !", "Validation", JOptionPane.ERROR_MESSAGE); emailField.requestFocus(); return; }

        if (faculteEnEdition == null) {
            if (controller.ajouterFaculte(nom, code, adr, tel, email, doyen)) { resetForm(); chargerDonnees(); tabbedPane.setSelectedIndex(1); }
        } else {
            faculteEnEdition.setNomFac(nom); faculteEnEdition.setCodeFac(code.toUpperCase());
            faculteEnEdition.setAdresse(adr); faculteEnEdition.setTelephone(tel);
            faculteEnEdition.setEmail(email); faculteEnEdition.setDoyen(doyen);
            if (controller.modifierFaculte(faculteEnEdition)) { resetForm(); chargerDonnees(); tabbedPane.setSelectedIndex(1); }
        }
    }

    private void preparerModification() {
        int row = tableFaculte.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une faculté à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        Faculte f = controller.getFaculteById(id);
        if (f == null) return;
        nomFacField.setText(f.getNomFac());
        codeFacField.setText(f.getCodeFac());
        adresseField.setText(f.getAdresse() != null ? f.getAdresse() : "");
        telephoneField.setText(f.getTelephone() != null ? f.getTelephone() : "");
        emailField.setText(f.getEmail() != null ? f.getEmail() : "");
        doyenField.setText(f.getDoyen() != null ? f.getDoyen() : "");
        faculteEnEdition = f;
        titreForm.setText("MODIFIER LA FACULTÉ : " + f.getCodeFac());
        titreForm.setForeground(new Color(200, 80, 0));
        actionBtn.setText("💾 Enregistrer les modifications");
        actionBtn.setBackground(new Color(255, 152, 0));
        tabbedPane.setTitleAt(0, "✏️ Modifier Faculté");
        tabbedPane.setSelectedIndex(0);
    }

    private void resetForm() {
        nomFacField.setText(""); codeFacField.setText(""); adresseField.setText("");
        telephoneField.setText(""); emailField.setText(""); doyenField.setText("");
        nomFacField.requestFocus();
        faculteEnEdition = null;
        titreForm.setText("AJOUTER UNE NOUVELLE FACULTÉ");
        titreForm.setForeground(new Color(0, 102, 204));
        actionBtn.setText("✅ Ajouter la Faculté");
        actionBtn.setBackground(new Color(76, 175, 80));
        tabbedPane.setTitleAt(0, "📝 Formulaire");
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Faculte> list = controller.getAllFacultes();
        for (Faculte f : list) {
            tableModel.addRow(new Object[]{ f.getIdFac(), f.getCodeFac(), f.getNomFac(), f.getAdresse(), f.getTelephone(), f.getEmail(), f.getDoyen(), f.getDateCreation() });
        }
        tabbedPane.setTitleAt(1, "📋 Liste des Facultés (" + list.size() + ")");
    }

    private void supprimerFaculte() {
        int row = tableFaculte.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une faculté à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        String code = (String) tableModel.getValueAt(row, 1);
        String nom  = (String) tableModel.getValueAt(row, 2);
        if (controller.supprimerFaculte(id, code, nom)) chargerDonnees();
    }

    private JLabel boldLabel(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Arial", Font.BOLD, 12)); return l; }
    private JButton styledBtn(String t, Color bg, int w, int h) {
        JButton b = new JButton(t); b.setFont(new Font("Arial", Font.BOLD, 12)); b.setBackground(bg); b.setForeground(Color.WHITE); b.setFocusPainted(false); b.setPreferredSize(new Dimension(w, h));
        b.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseEntered(java.awt.event.MouseEvent e){b.setBackground(bg.darker());} public void mouseExited(java.awt.event.MouseEvent e){b.setBackground(bg);} });
        return b;
    }
}
