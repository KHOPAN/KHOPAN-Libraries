package com.khopan.project.textEditor;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

public class SettingsDialog {
	public TextEditor Editor;
	public Frame Parent;
	public JDialog Dialog;

	public SettingsDialog(TextEditor Editor) {
		this.Editor = Editor;
		this.Parent = this.Editor.Frame;
		this.Dialog = new JDialog(this.Parent);
		this.Dialog.setModal(true);
		this.Dialog.setModalityType(ModalityType.TOOLKIT_MODAL);
		this.Dialog.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		this.Dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.Dialog.setSize(500, 300);
		this.Dialog.setLocationRelativeTo(this.Parent);
	}

	public void show() {
		this.Dialog.getContentPane().setBackground(this.Editor.background(this.Editor.WindowTheme));
		this.Dialog.setVisible(true);
	}
}
