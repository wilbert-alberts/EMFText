/*******************************************************************************
 * Copyright (c) 2006-2014
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.sdk.codegen.resource.ui.generators.ui;

import static org.emftext.sdk.codegen.resource.ClassNameConstants.ABSTRACT_PREFERENCE_INITIALIZER;
import static org.emftext.sdk.codegen.resource.ClassNameConstants.COLLECTION;
import static org.emftext.sdk.codegen.resource.ui.UIClassNameConstants.I_PREFERENCE_STORE;

import org.emftext.sdk.codegen.parameters.ArtifactParameter;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;

import de.devboost.codecomposers.java.JavaComposite;

public class PreferenceInitializerGenerator extends UIJavaBaseGenerator<ArtifactParameter<GenerationContext>> {

	public void generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() +";");sc.addLineBreak();sc.addImportsPlaceholder();
		sc.addLineBreak();
		
		sc.addJavadoc("This class can be used to initialize default preference values.");
		sc.add("public class " + getResourceClassName() + " extends " + ABSTRACT_PREFERENCE_INITIALIZER(sc) + " {");
		sc.addLineBreak();
		addMethods(sc);
		sc.add("}");
		sc.addLineBreak();
	}

	private void addMethods(JavaComposite sc) {
		addInitializeDefaultPreferencesMethod(sc);
		addInitializeDefaultBracketsMethod1(sc);
		addInitializeDefaultBracketsMethod2(sc);
		addInitializeDefaultSyntaxHighlightingMethod1(sc);
		addInitializeDefaultSyntaxHighlightingMethod2(sc);
		addInitializeDefaultsContentAssistMethod(sc);
		addSetPropertiesMethod(sc);
		addGetColorStringMethod(sc);
	}

	private void addInitializeDefaultsContentAssistMethod(JavaComposite sc) {
		sc.add("private void initializeDefaultsContentAssist() {");
		sc.add(I_PREFERENCE_STORE(sc) + " store = " + uiPluginActivatorClassName + ".getDefault().getPreferenceStore();");
		sc.add("store.setDefault(" + preferenceConstantsClassName + ".EDITOR_CONTENT_ASSIST_ENABLED, " + preferenceConstantsClassName + ".EDITOR_CONTENT_ASSIST_ENABLED_DEFAULT);");
		sc.add("store.setDefault(" + preferenceConstantsClassName + ".EDITOR_CONTENT_ASSIST_DELAY, " + preferenceConstantsClassName + ".EDITOR_CONTENT_ASSIST_DELAY_DEFAULT);");
		sc.add("store.setDefault(" + preferenceConstantsClassName + ".EDITOR_CONTENT_ASSIST_TRIGGERS, " + preferenceConstantsClassName + ".EDITOR_CONTENT_ASSIST_TRIGGERS_DEFAULT);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInitializeDefaultPreferencesMethod(JavaComposite sc) {
		sc.add("public void initializeDefaultPreferences() {");
		sc.addLineBreak();
		sc.add("initializeDefaultSyntaxHighlighting();");
		sc.add("initializeDefaultBrackets();");
		sc.add("initializeDefaultsContentAssist();");
		sc.addLineBreak();
		sc.add(I_PREFERENCE_STORE(sc) + " store = " + uiPluginActivatorClassName + ".getDefault().getPreferenceStore();");
		sc.addComment("Set default value for matching brackets");
		sc.add("store.setDefault(" + preferenceConstantsClassName + ".EDITOR_MATCHING_BRACKETS_COLOR, \"192,192,192\");");
		sc.add("store.setDefault(" + preferenceConstantsClassName + ".EDITOR_MATCHING_BRACKETS_CHECKBOX, true);");
		sc.addLineBreak();
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInitializeDefaultBracketsMethod1(JavaComposite sc) {
		sc.add("protected void initializeDefaultBrackets() {");
		sc.add(I_PREFERENCE_STORE(sc) + " store = " + uiPluginActivatorClassName + ".getDefault().getPreferenceStore();");
		sc.add("initializeDefaultBrackets(store, new " + metaInformationClassName + "());");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInitializeDefaultSyntaxHighlightingMethod1(JavaComposite sc) {
		sc.add("public void initializeDefaultSyntaxHighlighting() {");
		sc.add(I_PREFERENCE_STORE(sc) + " store = " + uiPluginActivatorClassName + ".getDefault().getPreferenceStore();");
		sc.add("initializeDefaultSyntaxHighlighting(store, new " + metaInformationClassName + "());");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInitializeDefaultBracketsMethod2(JavaComposite sc) {
		sc.add("protected void initializeDefaultBrackets(" + I_PREFERENCE_STORE(sc) + " store, " + iMetaInformationClassName + " metaInformation) {");
		sc.add("String languageId = metaInformation.getSyntaxName();");
		sc.addComment("set default brackets");
		sc.add(bracketSetClassName + " bracketSet = new " + bracketSetClassName + "();");
		sc.add("final " + COLLECTION(sc) + "<" + iBracketPairClassName + "> bracketPairs = metaInformation.getBracketPairs();");
		sc.add("if (bracketPairs != null) {");
		sc.add("for (" + iBracketPairClassName + " bracketPair : bracketPairs) {");
		sc.add("bracketSet.addBracketPair(bracketPair.getOpeningBracket(), bracketPair.getClosingBracket(), bracketPair.isClosingEnabledInside(), bracketPair.isCloseAfterEnter());");
		sc.add("}");
		sc.add("}");
		sc.add("store.setDefault(languageId + " + preferenceConstantsClassName + ".EDITOR_BRACKETS_SUFFIX, bracketSet.serialize());");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInitializeDefaultSyntaxHighlightingMethod2(JavaComposite sc) {
		sc.add("protected void initializeDefaultSyntaxHighlighting(" + I_PREFERENCE_STORE(sc) + " store, " + metaInformationClassName + " metaInformation) {");
		sc.add("String languageId = metaInformation.getSyntaxName();");
		sc.add("String[] tokenNames = metaInformation.getSyntaxHighlightableTokenNames();");
		sc.add("if (tokenNames == null) {");
		sc.add("return;");
		sc.add("}");
		sc.add("for (int i = 0; i < tokenNames.length; i++) {");
		sc.add("String tokenName = tokenNames[i];");
		sc.add(iTokenStyleClassName + " style = metaInformation.getDefaultTokenStyle(tokenName);");
		sc.add("if (style != null) {");
		sc.add("String color = getColorString(style.getColorAsRGB());");
		sc.add("setProperties(store, languageId, tokenName, color, style.isBold(), true, style.isItalic(), style.isStrikethrough(), style.isUnderline());");
		sc.add("} else {");
		sc.add("setProperties(store, languageId, tokenName, \"0,0,0\", false, false, false, false, false);");
		sc.add("}");
		sc.add("}");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addSetPropertiesMethod(JavaComposite sc) {
		sc.add("protected void setProperties(" + I_PREFERENCE_STORE(sc) + " store, String languageID, String tokenName, String color, boolean bold, boolean enable, boolean italic, boolean strikethrough, boolean underline) {");
		sc.add("store.setDefault(" + syntaxColoringHelperClassName + ".getPreferenceKey(languageID, tokenName, " + syntaxColoringHelperClassName + ".StyleProperty.BOLD), bold);");
		sc.add("store.setDefault(" + syntaxColoringHelperClassName + ".getPreferenceKey(languageID, tokenName, " + syntaxColoringHelperClassName + ".StyleProperty.COLOR), color);");
		sc.add("store.setDefault(" + syntaxColoringHelperClassName + ".getPreferenceKey(languageID, tokenName, " + syntaxColoringHelperClassName + ".StyleProperty.ENABLE), enable);");
		sc.add("store.setDefault(" + syntaxColoringHelperClassName + ".getPreferenceKey(languageID, tokenName, " + syntaxColoringHelperClassName + ".StyleProperty.ITALIC), italic);");
		sc.add("store.setDefault(" + syntaxColoringHelperClassName + ".getPreferenceKey(languageID, tokenName, " + syntaxColoringHelperClassName + ".StyleProperty.STRIKETHROUGH), strikethrough);");
		sc.add("store.setDefault(" + syntaxColoringHelperClassName + ".getPreferenceKey(languageID, tokenName, " + syntaxColoringHelperClassName + ".StyleProperty.UNDERLINE), underline);");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addGetColorStringMethod(JavaComposite sc) {
		sc.add("protected String getColorString(int[] colorAsRGB) {");
		sc.add("if (colorAsRGB == null) {");
		sc.add("return \"0,0,0\";");
		sc.add("}");
		sc.add("if (colorAsRGB.length != 3) {");
		sc.add("return \"0,0,0\";");
		sc.add("}");
		sc.add("return colorAsRGB[0] + \",\" +colorAsRGB[1] + \",\"+ colorAsRGB[2];");
		sc.add("}");
		sc.addLineBreak();
	}
}
