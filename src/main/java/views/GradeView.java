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
    private JTabbedPane tabbedPane;
    private Grade gradeEnEdition = null;
    private JButton actionBtn;
    private JLabel titreForm;

    public GradeView() {
        this.controller = new GradeController();
        initComponents();
        chargerDonnees();
    }

    private void initComponents() {
        setTitle("Gestion des Grades - Jérôme Cirhulwire");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 540);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.addTab("📝 Formulaire", createFormPanel());
        tabbedPane.addTab("📋 Liste des Grades", createListePanel());
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
        titreForm = new JLabel("AJOUTER UN NOUVEAU GRADE");
        titreForm.setFont(new Font("Arial", Font.BOLD, 16));
        titreForm.setForeground(new Color(0, 102, 204));
        titreForm.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titreForm, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(boldLabel("Nom du Grade :"), gbc);
        gbc.gridx = 1; nomGradeField = new JTextField(20); panel.add(nomGradeField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(boldLabel("Niveau :"), gbc);
        gbc.gridx = 1; niveauField = new JTextField(20); panel.add(niveauField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panel.add(boldLabel("Description :"), gbc);
        gbc.gridx = 1; descriptionArea = new JTextArea(4, 20); descriptionArea.setLineWrap(true); panel.add(new JScrollPane(descriptionArea), gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bp.setBackground(new Color(245, 245, 250));
        actionBtn = styledBtn("✅ Ajouter le Grade", new Color(76, 175, 80), 190, 40);
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

        String[] cols = {"ID", "Nom Grade", "Niveau", "Description", "Date Création"};
        tableModel = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tableGrade = new JTable(tableModel);
        tableGrade.setFont(new Font("Arial", Font.PLAIN, 12));
        tableGrade.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableGrade.setRowHeight(25);
        tableGrade.setSelectionBackground(new Color(232, 240, 254));
        JScrollPane sp = new JScrollPane(tableGrade);
        sp.setBorder(BorderFactory.createTitledBorder("Liste des Grades"));
        panel.add(sp, BorderLayout.CENTER);

        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        JButton refreshBtn = styledBtn("🔄 Actualiser", new Color(33, 150, 243), 130, 35);
        refreshBtn.addActionListener(e -> chargerDonnees());
        JButton modBtn = styledBtn("✏️ Modifier", new Color(255, 152, 0), 120, 35);
        modBtn.addActionListener(e -> preparerModification());
        JButton delBtn = styledBtn("🗑️ Supprimer", new Color(244, 67, 54), 130, 35);
        delBtn.addActionListener(e -> supprimerGrade());
        bp.add(refreshBtn); bp.add(modBtn); bp.add(delBtn);
        panel.add(bp, BorderLayout.SOUTH);
        return panel;
    }

    private void sauvegarder() {
        String nom  = nomGradeField.getText().trim();
        String niv  = niveauField.getText().trim();
        String desc = descriptionArea.getText().trim();

        if (nom.isEmpty()) { JOptionPane.showMessageDialog(this, "❌ Le nom du grade est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); nomGradeField.requestFocus(); return; }
        if (niv.isEmpty()) { JOptionPane.showMessageDialog(this, "❌ Le niveau est obligatoire !", "Validation", JOptionPane.ERROR_MESSAGE); niveauField.requestFocus(); return; }

        if (gradeEnEdition == null) {
            if (controller.ajouterGrade(nom, niv, desc)) { resetForm(); chargerDonnees(); tabbedPane.setSelectedIndex(1); }
        } else {
            gradeEnEdition.setNomGrade(nom); gradeEnEdition.setNiveau(niv); gradeEnEdition.setDescription(desc);
            if (controller.modifierGrade(gradeEnEdition)) { resetForm(); chargerDonnees(); tabbedPane.setSelectedIndex(1); }
        }
    }

    private void preparerModification() {
        int row = tableGrade.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez un grade à modifier.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        Grade g = controller.getGradeById(id);
        if (g == null) return;
        nomGradeField.setText(g.getNomGrade());
        niveauField.setText(g.getNiveau());
        descriptionArea.setText(g.getDescription() != null ? g.getDescription() : "");
        gradeEnEdition = g;
        titreForm.setText("MODIFIER LE GRADE : " + g.getNomGrade());
        titreForm.setForeground(new Color(200, 80, 0));
        actionBtn.setText("💾 Enregistrer les modifications");
        actionBtn.setBackground(new Color(255, 152, 0));
        tabbedPane.setTitleAt(0, "✏️ Modifier Grade");
        tabbedPane.setSelectedIndex(0);
    }

    private void resetForm() {
        nomGradeField.setText(""); niveauField.setText(""); descriptionArea.setText("");
        nomGradeField.requestFocus();
        gradeEnEdition = null;
        titreForm.setText("AJOUTER UN NOUVEAU GRADE");
        titreForm.setForeground(new Color(0, 102, 204));
        actionBtn.setText("✅ Ajouter le Grade");
        actionBtn.setBackground(new Color(76, 175, 80));
        tabbedPane.setTitleAt(0, "📝 Formulaire");
    }

    private void chargerDonnees() {
        tableModel.setRowCount(0);
        List<Grade> list = controller.getAllGrades();
        for (Grade g : list)
            tableModel.addRow(new Object[]{ g.getIdGrade(), g.getNomGrade(), g.getNiveau(), g.getDescription(), g.getDateCreation() });
        tabbedPane.setTitleAt(1, "📋 Liste des Grades (" + list.size() + ")");
    }

    private void supprimerGrade() {
        int row = tableGrade.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Sélectionnez un grade à supprimer.", "Aucune sélection", JOptionPane.WARNING_MESSAGE); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        String nom = (String) tableModel.getValueAt(row, 1);
        if (controller.supprimerGrade(id, nom)) chargerDonnees();
    }

    private JLabel boldLabel(String t) { JLabel l = new JLabel(t); l.setFont(new Font("Arial", Font.BOLD, 12)); return l; }
    private JButton styledBtn(String t, Color bg, int w, int h) {
        JButton b = new JButton(t); b.setFont(new Font("Arial", Font.BOLD, 12)); b.setBackground(bg); b.setForeground(Color.WHITE); b.setFocusPainted(false); b.setPreferredSize(new Dimension(w, h));
        b.addMouseListener(new java.awt.event.MouseAdapter() { public void mouseEntered(java.awt.event.MouseEvent e){b.setBackground(bg.darker());} public void mouseExited(java.awt.event.MouseEvent e){b.setBackground(bg);} });
        return b;
    }
}
