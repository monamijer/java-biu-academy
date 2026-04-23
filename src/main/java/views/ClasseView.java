package views;

import controllers.ClasseController;
import controllers.FaculteController;
import models.Classe;
import models.Faculte;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClasseView extends JFrame {

    private ClasseController controller;
    private FaculteController faculteController;

    private JTextField nomClasseField, responsableField, nomProjetField;
    private JComboBox<String> typeClasseCombo;
    private JComboBox<Faculte> faculteCombo;
    private JTextArea descriptionArea;
    private JTable tableClasse;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    private Classe classeEnEdition = null;
    private JButton actionBtn;
    private JLabel titreForm;

    public ClasseView() {
        this.controller = new ClasseController();
        this.faculteController = new FaculteController();
        initComponents();
        chargerDonnees();
    }

    private void initComponents() {
        setTitle("Gestion des Classes - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("📝 Formulaire", createFormPanel());
        tabbedPane.addTab("📋 Liste des Classes", createListePanel());
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
        titreForm = new JLabel("AJOUTER UNE CLASSE");
        titreForm.setFont(new Font("Arial", Font.BOLD, 16));
        titreForm.setForeground(new Color(0, 102, 204));
        titreForm.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titreForm, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(boldLabel("Nom Classe :"), gbc);
        gbc.gridx = 1; nomClasseField = new JTextField(20); panel.add(nomClasseField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(boldLabel("Faculté :"), gbc);
        gbc.gridx = 1; faculteCombo = new JComboBox<>(); chargerFacultesCombo(); panel.add(faculteCombo, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panel.add(boldLabel("Doyen(nne) :"), gbc);
        gbc.gridx = 1; responsableField = new JTextField(20); panel.add(responsableField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; panel.add(boldLabel("Nom Projet :"), gbc);
        gbc.gridx = 1; nomProjetField = new JTextField(20); panel.add(nomProjetField, gbc);

        gbc.gridy = 5; gbc.gridx = 0; panel.add(boldLabel("Type Classe :"), gbc);
        gbc.gridx = 1; typeClasseCombo = new JComboBox<>(new String[]{"TP", "TD", "Cours", "Projet"}); panel.add(typeClasseCombo, gbc);

        gbc.gridy = 6; gbc.gridx = 0; panel.add(boldLabel("Description :"), gbc);
        gbc.gridx = 1; descriptionArea = new JTextArea(4, 20); descriptionArea.setLineWrap(true); panel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridy = 7; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bp.setBackground(new Color(245, 245, 250));
        actionBtn = styledBtn("✅ Ajouter la Classe", new Color(76, 175, 80), 190, 40);
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

        String[] cols = {"ID", "Nom Classe", "ID Fac", "Responsable", "Nom Projet", "Type", "Description"};
        tableModel = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tableClasse = new JTable(tableModel);
        tableClasse.setFont(new Font("Arial", Font.PLAIN, 11));
        tableClasse.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tableClasse.setRowHeight(25);
        tableClasse.setSelectionBackground(new Color(232, 240, 254));
        JScrollPane sp = new JScrollPane(tableClasse);
        sp.setBorder(BorderFactory.createTitledBorder("Liste des Classes"));
        panel.add(sp, BorderLayout.CENTER);

        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton refreshBtn = styledBtn("🔄 Actualiser", new Color(33, 150, 243), 130, 35);
        refreshBtn.addActionListener(e -> chargerDonnees());
        JButton modBtn = styledBtn("✏️ Modifier", new Color(255, 152, 0), 120, 35);
        modBtn.addActionListener(e -> preparerModification());
        JButton delBtn = styledBtn("🗑️ Supprimer", new Color(244, 67, 54), 130, 35);
        delBtn.addActionListener(e -> supprimerClasse());
        bp.add(refreshBtn); bp.add(modBtn); bp.add(delBtn);
        panel.add(bp, BorderLayout.SOUTH);
        return panel;
    }

    private void chargerFacultesCombo() {
        faculteCombo.removeAllItems();
        List<Faculte> facs = faculteController.getAllFacultes();
        for (Faculte f : facs) faculteCombo.addItem(f);
    }

    private void sauvegarder() {
        String nom   = nomClasseField.getText().trim();
        Faculte fac  = (Faculte) faculteCombo.getSelectedItem();
        String resp  = responsableField.getText().trim();
        String proj  = nomProjetField.getText().trim();
        String type  = (String) typeClasseCombo.getSelectedItem();
        String desc  = descriptionArea.getText().trim();

        if (nom.isEmpty()) { JOptionPane.showMessageDialog(this, "❌ Le nom de la classe est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); return; }
        if (fac == null) { JOptionPane.showMessageDialog(this, "❌ Veuillez sélectionner une faculté !", "Validation", JOptionPane.ERROR_MESSAGE); return; }

        if (classeEnEdition == null) {
            if (controller.ajouterClasse(nom, String.valueOf(fac.getIdFac()), resp, proj, type, desc)) { resetForm(); chargerDonnees(); tabbedPane.setSelectedIndex(1); }
        } else {
            classeEnEdition.setNomClasse(nom); classeEnEdition.setIdFac(fac.getIdFac());
            classeEnEdition.setResponsable(resp); classeEnEdition.setNomProjet(proj);
            classeEnEdition.setTypeClasse(type); classeEnEdition.setDescription(desc);
            if (controller.modifierClasse(classeEnEdition)) { resetForm(); chargerDonnees(); tabbedPane.setSelectedIndex(1); }
        }
    }

    private void preparerModification() {
        int row = tableClasse.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une classe à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        Classe c = controller.getClasseById(id);
        if (c == null) return;
        nomClasseField.setText(c.getNomClasse());
        responsableField.setText(c.getResponsable() != null ? c.getResponsable() : "");
        nomProjetField.setText(c.getNomProjet() != null ? c.getNomProjet() : "");
        typeClasseCombo.setSelectedItem(c.getTypeClasse());
        descriptionArea.setText(c.getDescription() != null ? c.getDescription() : "");
        // Sélectionner la faculté correspondante
        for (int i = 0; i < faculteCombo.getItemCount(); i++) {
            if (faculteCombo.getItemAt(i).getIdFac() == c.getIdFac()) { faculteCombo.setSelectedIndex(i); break; }
        }
        classeEnEdition = c;
        titreForm.setText("MODIFIER LA CLASSE : " + c.getNomClasse());
        titreForm.setForeground(new Color(200, 80, 0));
        actionBtn.setText("💾 Enregistrer les modifications");
        actionBtn.setBackground(new Color(255, 152, 0));
        tabbedPane.setTitleAt(0, "✏️ Modifier Classe");
        tabbedPane.setSelectedIndex(0);
    }

    private void resetForm() {
        nomClasseField.setText(""); responsableField.setText(""); nomProjetField.setText(""); descriptionArea.setText("");
        typeClasseCombo.setSelectedIndex(0);
        if (faculteCombo.getItemCount() > 0) faculteCombo.setSelectedIndex(0);
        nomClasseField.requestFocus();
        classeEnEdition = null;
        titreForm.setText("AJOUTER UNE CLASSE");
        titreForm.setForeground(new Color(0, 102, 204));
        actionBtn.setText("✅ Ajouter la Classe");
        actionBtn.setBackground(new Color(76, 175, 80));
        tabbedPane.setTitleAt(0, "📝 Formulaire");
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Classe> list = controller.getAllClasses();
        for (Classe c : list) {
            tableModel.addRow(new Object[]{ c.getIdClasse(), c.getNomClasse(), c.getIdFac(), c.getResponsable(), c.getNomProjet(), c.getTypeClasse(), c.getDescription() });
        }
        tabbedPane.setTitleAt(1, "📋 Liste des Classes (" + list.size() + ")");
    }

    private void supprimerClasse() {
        int row = tableClasse.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez une classe à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        String nom = (String) tableModel.getValueAt(row, 1);
        if (controller.supprimerClasse(id, nom)) chargerDonnees();
    }

    private JLabel boldLabel(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Arial", Font.BOLD, 12)); return l; }
    private JButton styledBtn(String t, Color bg, int w, int h) {
        JButton b = new JButton(t); b.setFont(new Font("Arial", Font.BOLD, 12)); b.setBackground(bg); b.setForeground(Color.WHITE); b.setFocusPainted(false); b.setPreferredSize(new Dimension(w, h));
        b.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseEntered(java.awt.event.MouseEvent e){b.setBackground(bg.darker());} public void mouseExited(java.awt.event.MouseEvent e){b.setBackground(bg);} });
        return b;
    }
}
