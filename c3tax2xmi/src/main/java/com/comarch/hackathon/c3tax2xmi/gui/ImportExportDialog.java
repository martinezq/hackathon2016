package com.comarch.hackathon.c3tax2xmi.gui;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;
import com.comarch.hackathon.c3tax2xmi.saxparser.C3TaxParser;
import com.comarch.hackathon.c3tax2xmi.util.RdfUtils;
import com.comarch.hackathon.c3tax2xmi.xmi.GeneratorConfig;
import com.comarch.hackathon.c3tax2xmi.xmi.StaxXmiGenerator;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Szlachtap
 */
public class ImportExportDialog extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5180349036763470504L;
	private C3TaxParser parser = new C3TaxParser();
    private final Collection<String> defaultCategories = Arrays.asList(GeneratorConfig.DEFAULT_CATEGORIES);
    
    public ImportExportDialog() {
        super(null, java.awt.Dialog.ModalityType.TOOLKIT_MODAL);
        initLookAndFeel();
        initComponents();
        initActions();
        initSubjectTree();
        initCategoryList();
        initIconAndPosition();
    }
    
    private void initIconAndPosition() {
        setIconImage(GuiUtils.loadImage("icon.png"));
        GuiUtils.centreOnScreen(this);
    }
    
    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        importFileLabel = new javax.swing.JLabel();
        importFilePathText = new javax.swing.JTextField();
        selectImportFileButton = new javax.swing.JButton();
        subjectScrollPane = new javax.swing.JScrollPane();
        subjectTree = new javax.swing.JTree();
        exportFileLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        categoryScrollPane = new javax.swing.JScrollPane();
        categoryList = new javax.swing.JList<>();
        exportFilePathText = new javax.swing.JTextField();
        selectExportFileButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comarch RDF converter");

        importFileLabel.setText("Import rdf file");

        importFilePathText.setEditable(false);
        importFilePathText.setText("<SELECT FILE>");

        selectImportFileButton.setText("Select");

        subjectTree.setEditable(true);
        subjectScrollPane.setViewportView(subjectTree);

        exportFileLabel.setText("Export xmi file");

        categoryLabel.setText("Change category selection for export");

        categoryScrollPane.setViewportView(categoryList);

        exportFilePathText.setEditable(false);
        exportFilePathText.setText("<SELECT FILE>");

        selectExportFileButton.setText("Select");

        exportButton.setText("Export");

        closeButton.setText("Close");

        infoLabel.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryScrollPane)
                    .addComponent(subjectScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(importFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(importFilePathText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectImportFileButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exportButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(exportFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exportFilePathText, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectExportFileButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(categoryLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importFileLabel)
                    .addComponent(importFilePathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectImportFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(subjectScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(categoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectExportFileButton)
                    .addComponent(exportFilePathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exportFileLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(exportButton)
                    .addComponent(infoLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JList<String> categoryList;
    private javax.swing.JScrollPane categoryScrollPane;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel exportFileLabel;
    private javax.swing.JTextField exportFilePathText;
    private javax.swing.JLabel importFileLabel;
    private javax.swing.JTextField importFilePathText;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JButton selectExportFileButton;
    private javax.swing.JButton selectImportFileButton;
    private javax.swing.JScrollPane subjectScrollPane;
    private javax.swing.JTree subjectTree;
    // End of variables declaration//GEN-END:variables

    private void initActions() {
        selectImportFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(new File("d:\\Projekty\\Hackathon\\Codding_Challange"));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                int returnVal = fc.showOpenDialog(ImportExportDialog.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    importFilePathText.setText(file.getAbsolutePath());

                    // Info
                    long t = System.currentTimeMillis();
                    info("Parsing RDF file " + file.getAbsolutePath() + " ...");
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    // Parsing RDF
                    try {
                        parser.parse(file);
                        refreshSubjectTree();
                        refreshCategoryList();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ImportExportDialog.this, "File parsing error:\n" + ex.getMessage(), "Parsing file error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Info
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    info("Parsing RDF file finished, time: " + (System.currentTimeMillis() - t) + "[ms]");
                }
            }
        });
        
        selectExportFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(new File("d:\\Projekty\\Hackathon\\Codding_Challange"));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                int returnVal = fc.showSaveDialog(ImportExportDialog.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    exportFilePathText.setText(file.getAbsolutePath());
                }
            }
        });
        
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = exportFilePathText.getText();
                if (filePath != null && !filePath.equals("<SELECT FILE>")) {
                    Collection<RdfSubject> checkedSubjects = getCheckedSubjects();
                    if (checkedSubjects != null && !checkedSubjects.isEmpty()) {
                        File file = new File(filePath);
                        
                        // Info
                        long t = System.currentTimeMillis();
                        info("Writing XMI file " + file.getAbsolutePath() + " ...");
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        
                        // XMI generating
                        StaxXmiGenerator generator = new StaxXmiGenerator();
                        generator.setRoot(getRootSubject());
                        generator.setSubjects(checkedSubjects);
                        generator.setCategoriesToExport(getSelectedCategories());
                        generator.write(file.getAbsolutePath());
                        
                        // Info
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        info("Writing XMI finished, objects: " + generator.getObjects().size() + ",time: " + (System.currentTimeMillis() - t) + "[ms]");
                    } else {
                        JOptionPane.showMessageDialog(ImportExportDialog.this, "Select objects to export", "Export file error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ImportExportDialog.this, "Select valid file to export", "Export file error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImportExportDialog.this.setVisible(false);
                System.exit(0);
            }
        });
    }
    
    @SuppressWarnings("serial")
    private void initCategoryList() {
        categoryList.setModel(new DefaultListModel<>());
        categoryList.setCellRenderer(new DefaultListCellRenderer(){
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof String) {
                    String name = RdfUtils.getRdfTypeFriendlyName((String)value);
                    return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        
        categoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Collection<String> selectedCategories = getSelectedCategories();
                Collection<SubjectTreeNode> allNodes = getSubjectTreeNodes();
                if (allNodes != null) {
                    for (SubjectTreeNode node : allNodes) {
                        node.setSelected(false);
                        
                        if (node.getSubject().hasAnyType(selectedCategories)) {
                            node.setSelected(true);
                        }
                    }
                }
                subjectTree.repaint();
            }
        });
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void refreshCategoryList() {
        DefaultListModel model = (DefaultListModel)categoryList.getModel();
        model.removeAllElements();
        if (parser.getHandler().getSubjects() != null) {
            Collection<String> categories = RdfUtils.findUniqueRdfTypes(parser.getHandler().getSubjects());
            
            int index = 0;
            if (categories != null) {
                List<String> categories2 = new ArrayList<>(categories);
                Collections.sort(categories2);
                for (String cat : categories2) {
                    String elementName = cat;
                    if (cat.contains("/")) {
                        elementName = cat.substring(cat.lastIndexOf("/"));
                    }
                    model.addElement(elementName);
                    if (defaultCategories.contains(elementName)) {
                        categoryList.addSelectionInterval(index, index);
                    }
                    index ++;
                }
            }
        }
    }
    
    private void initSubjectTree() {
        subjectTree.setModel(new DefaultTreeModel(null));
        //subjectTree.setRootVisible(false);
        
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = -7236862381322266727L;

			@Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                    if (node.getUserObject() instanceof SubjectTreeNode) {
                        SubjectTreeNode subjectTreeNode = (SubjectTreeNode)node.getUserObject();
                        JCheckBox checkBox = new JCheckBox(subjectTreeNode.getSubject().getExportName(), subjectTreeNode.isChecked());
                        if (subjectTreeNode.isSelected()) {
                            checkBox.setBackground(categoryList.getSelectionBackground());
                        } else {
                            checkBox.setBackground(subjectTree.getBackground());
                        }
                        return checkBox;
                    }
                }
                return super.getTreeCellRendererComponent(tree, value, leaf, expanded, leaf, row, hasFocus);
            }
        };
        subjectTree.setCellRenderer(renderer);
        
        DefaultTreeCellEditor editor = new DefaultTreeCellEditor(subjectTree, renderer) {
            @Override
            public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
                    if (node.getUserObject() instanceof SubjectTreeNode) {
                        SubjectTreeNode subjectTreeNode = (SubjectTreeNode)node.getUserObject();
                        JCheckBox checkBox = new JCheckBox(subjectTreeNode.getSubject().getExportName(), subjectTreeNode.isChecked());
                        if (subjectTreeNode.isSelected()) {
                            checkBox.setBackground(categoryList.getSelectionBackground());
                        } else {
                            checkBox.setBackground(tree.getBackground());
                        }
                        checkBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                checkNode(node, !subjectTreeNode.isChecked());
                                subjectTree.repaint();
                            }
                        });
                        return checkBox;
                    }
                }
                return super.getTreeCellEditorComponent(tree, value, leaf, expanded, leaf, row);
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }
        };
        subjectTree.setCellEditor(editor);
    }
    
    private void checkNode(DefaultMutableTreeNode node, boolean check) {
        if (node.getUserObject() instanceof SubjectTreeNode) {
            SubjectTreeNode subjectTreeNode = (SubjectTreeNode)node.getUserObject();
            subjectTreeNode.setChecked(check);
            for (int i = 0; i < node.getChildCount(); i ++) {
                TreeNode childNode = node.getChildAt(i);
                if (childNode instanceof DefaultMutableTreeNode) {
                    checkNode((DefaultMutableTreeNode)childNode, check);
                }
            }
        }
    }
    
    public void refreshSubjectTree() {
        DefaultTreeModel treeModel = ((DefaultTreeModel)subjectTree.getModel());
        treeModel.setRoot(null);
        if (parser.getHandler().getRootElement() != null) {
            DefaultMutableTreeNode root = subjectToNode(parser.getHandler().getRootElement());
            if (root != null) {
                treeModel.setRoot(root);
                refreshChildren(root);
                subjectTree.expandRow(0);
            }
        }
    }
    
    private void refreshChildren(DefaultMutableTreeNode parentNode) {
        if (parentNode.getUserObject() instanceof SubjectTreeNode) {
            SubjectTreeNode subjectTreeNode = (SubjectTreeNode)parentNode.getUserObject();
            if (subjectTreeNode.getSubject().getChildren() != null) {
                Collection<DefaultMutableTreeNode> childrenNodes = subjectsToNodes(subjectTreeNode.getSubject().getChildren());
                if (childrenNodes != null) {
                    for (DefaultMutableTreeNode childNode : childrenNodes) {
                        parentNode.add(childNode);
                        refreshChildren(childNode);
                    }
                }
            }
        }
    }
    
    private Collection<DefaultMutableTreeNode> subjectsToNodes(Collection<RdfSubject> subjects) {
        Collection<DefaultMutableTreeNode> result = new ArrayList<>();
        if (subjects != null) {
            for (RdfSubject subject : subjects) {
                DefaultMutableTreeNode node = subjectToNode(subject);
                if (node != null) {
                    result.add(node);
                }
            }
        }
        return result;
    }
    
    private DefaultMutableTreeNode subjectToNode(RdfSubject subject) {
        if (subject != null) {
            return new DefaultMutableTreeNode(new SubjectTreeNode(subject, true, false));
        } 
        return null;
    }
    
    public RdfSubject getRootSubject() {
        DefaultTreeModel treeModel = ((DefaultTreeModel)subjectTree.getModel());
        if (treeModel.getRoot() instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
            if (root.getUserObject() instanceof SubjectTreeNode) {
                SubjectTreeNode subjectTreeNode = (SubjectTreeNode)root.getUserObject();
                return subjectTreeNode.getSubject();
            }
        }
        return null;
    }
    
    public Collection<RdfSubject> getCheckedSubjects() {
        Collection<RdfSubject> result = new ArrayList<>();
        Collection<SubjectTreeNode> subjectTreeNodes = getSubjectTreeNodes();
        if (subjectTreeNodes != null) {
            for (SubjectTreeNode subjectTreeNode : subjectTreeNodes) {
                if (subjectTreeNode.isChecked()) {
                    result.add(subjectTreeNode.getSubject());
                }
            }
        }
        return result;
    }
    
    public Collection<SubjectTreeNode> getSubjectTreeNodes() {
        Collection<SubjectTreeNode> result = new ArrayList<>();
        DefaultTreeModel treeModel = ((DefaultTreeModel)subjectTree.getModel());
        if (treeModel.getRoot() instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
            if (root.getUserObject() instanceof SubjectTreeNode) {
                SubjectTreeNode subjectTreeNode = (SubjectTreeNode)root.getUserObject();
                result.add(subjectTreeNode);

                Collection<SubjectTreeNode> children = getSubjectTreeNodes(root);
                if (children != null) {
                    result.addAll(children);
                }
            }
        }
        return result;
    }
    
    private Collection<SubjectTreeNode> getSubjectTreeNodes(DefaultMutableTreeNode parentNode) {
        Collection<SubjectTreeNode> result = new ArrayList<>();
        for (int i = 0; i < parentNode.getChildCount(); i ++) {
            if (parentNode.getChildAt(i) instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)parentNode.getChildAt(i);
                if (node.getUserObject() instanceof SubjectTreeNode) {
                    SubjectTreeNode subjectTreeNode = (SubjectTreeNode)node.getUserObject();
                    result.add(subjectTreeNode);

                    Collection<SubjectTreeNode> children = getSubjectTreeNodes(node);
                    if (children != null) {
                        result.addAll(children);
                    }
                }
            }
        }
        return result;
    }
    
    private Collection<String> getSelectedCategories() {
        return categoryList.getSelectedValuesList();
    }
    
    private void info(String info) {
        infoLabel.setText(info);
        System.out.println(info);
    }

	public void setParser(C3TaxParser parser) {
		this.parser = parser;
	}

	public javax.swing.JTextField getImportFilePathText() {
		return importFilePathText;
	}

	public javax.swing.JTextField getExportFilePathText() {
		return exportFilePathText;
	}

}
