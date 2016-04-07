package com.comarch.hackathon.c3tax2xmi.gui;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

/**
 *
 * @author Szlachtap
 */
public class SubjectTreeNode {
    private RdfSubject subject;
    private boolean checked = true;
    private boolean selected = false;

    public SubjectTreeNode(RdfSubject subject, boolean checked, boolean selected) {
        this.subject = subject;
        this.checked = checked;
        this.selected = selected;
    }

    public RdfSubject getSubject() {
        return subject;
    }

    public void setSubject(RdfSubject subject) {
        this.subject = subject;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}
