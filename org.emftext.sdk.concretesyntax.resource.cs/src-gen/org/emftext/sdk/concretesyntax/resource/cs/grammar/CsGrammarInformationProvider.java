/*******************************************************************************
 * Copyright (c) 2006-2010 
 * Software Technology Group, Dresden University of Technology
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany 
 *      - initial API and implementation
 ******************************************************************************/

package org.emftext.sdk.concretesyntax.resource.cs.grammar;

public class CsGrammarInformationProvider {
	
	public final static org.eclipse.emf.ecore.EStructuralFeature ANONYMOUS_FEATURE = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEAttribute();
	static {
		ANONYMOUS_FEATURE.setName("_");
	}
	
	public static class Sequence extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		public Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement... elements) {
			super(cardinality, elements);
		}
		
		public org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement[] getElements() {
			return getChildren();
		}
	}
	
	public static class Choice extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		public Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement... choices) {
			super(cardinality, choices);
		}
		
		public org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement[] getChoices() {
			return getChildren();
		}
	}
	
	public static class LineBreak extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		private final int tabs;
		
		public LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality, int tabs) {
			super(cardinality, null);
			this.tabs = tabs;
		}
		
		public int getTabs() {
			return tabs;
		}
	}
	
	public static class WhiteSpaces extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		private final int amount;
		
		public WhiteSpaces(int amount, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality) {
			super(cardinality, null);
			this.amount = amount;
		}
		
		public int getAmount() {
			return amount;
		}
	}
	
	public static class Containment extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		private final org.eclipse.emf.ecore.EStructuralFeature feature;
		
		public Containment(org.eclipse.emf.ecore.EStructuralFeature feature, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality) {
			super(cardinality, null);
			this.feature = feature;
		}
		
		public org.eclipse.emf.ecore.EStructuralFeature getFeature() {
			return feature;
		}
	}
	
	public static class Compound extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		public Compound(Choice choice, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality) {
			super(cardinality, new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement[] {choice});
		}
		
		public Choice getDefinition() {
			return (Choice) getChildren()[0];
		}
	}
	
	public static class Rule extends org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement {
		
		private final org.eclipse.emf.ecore.EClass metaclass;
		
		public Rule(org.eclipse.emf.ecore.EClass metaclass, Choice choice, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality cardinality) {
			super(cardinality, new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsSyntaxElement[] {choice});
			this.metaclass = metaclass;
		}
		
		public org.eclipse.emf.ecore.EClass getMetaclass() {
			return metaclass;
		}
		
		public Choice getDefinition() {
			return (Choice) getChildren()[0];
		}
	}
	
	public final static Containment CS_0_0_0_0_0_0_0 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__ANNOTATIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_0_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static Sequence CS_0_0_0_0_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_0_0_0_0, CS_0_0_0_0_0_0_1);
	public final static Choice CS_0_0_0_0_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_0_0_0);
	public final static Compound CS_0_0_0_0 = new Compound(CS_0_0_0_0_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static Containment CS_0_0_0_1_0_0_0 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__MODIFIER), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_0_0_0_1_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_1_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_1_0_0_0, CS_0_0_0_1_0_0_1);
	public final static Choice CS_0_0_0_1_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_1_0_0);
	public final static Compound CS_0_0_0_1 = new Compound(CS_0_0_0_1_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("SYNTAXDEF", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_0_0_0_3 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_0_0_0_4 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__NAME), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_5 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("FOR", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_0_0_0_7 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_0_0_0_8 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__PACKAGE), "QUOTED_60_62", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_0_0_0_9_0_0_0 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_0_0_0_9_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__PACKAGE_LOCATION_HINT), "QUOTED_60_62", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_9_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_9_0_0_0, CS_0_0_0_9_0_0_1);
	public final static Choice CS_0_0_0_9_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_9_0_0);
	public final static Compound CS_0_0_0_9 = new Compound(CS_0_0_0_9_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static LineBreak CS_0_0_0_10 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_11_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("START", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_0_0_0_11_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_0_0_0_11_0_0_2_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__START_SYMBOLS), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_11_0_0_2_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_2_0_0_0);
	public final static Choice CS_0_0_0_11_0_0_2_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_2_0_0);
	public final static Compound CS_0_0_0_11_0_0_2 = new Compound(CS_0_0_0_11_0_0_2_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_11_0_0_3_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(",", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_0_0_0_11_0_0_3_0_0_1_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__START_SYMBOLS), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_11_0_0_3_0_0_1_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_3_0_0_1_0_0_0);
	public final static Choice CS_0_0_0_11_0_0_3_0_0_1_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_3_0_0_1_0_0);
	public final static Compound CS_0_0_0_11_0_0_3_0_0_1 = new Compound(CS_0_0_0_11_0_0_3_0_0_1_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_11_0_0_3_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_3_0_0_0, CS_0_0_0_11_0_0_3_0_0_1);
	public final static Choice CS_0_0_0_11_0_0_3_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_3_0_0);
	public final static Compound CS_0_0_0_11_0_0_3 = new Compound(CS_0_0_0_11_0_0_3_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static Sequence CS_0_0_0_11_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0_0, CS_0_0_0_11_0_0_1, CS_0_0_0_11_0_0_2, CS_0_0_0_11_0_0_3);
	public final static Choice CS_0_0_0_11_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_11_0_0);
	public final static Compound CS_0_0_0_11 = new Compound(CS_0_0_0_11_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static LineBreak CS_0_0_0_12_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static LineBreak CS_0_0_0_12_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_12_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("IMPORTS", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_12_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("{", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_12_0_0_4_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 1);
	public final static Containment CS_0_0_0_12_0_0_4_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__IMPORTS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_12_0_0_4_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_12_0_0_4_0_0_0, CS_0_0_0_12_0_0_4_0_0_1);
	public final static Choice CS_0_0_0_12_0_0_4_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_12_0_0_4_0_0);
	public final static Compound CS_0_0_0_12_0_0_4 = new Compound(CS_0_0_0_12_0_0_4_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static LineBreak CS_0_0_0_12_0_0_5 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_12_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("}", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_12_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_12_0_0_0, CS_0_0_0_12_0_0_1, CS_0_0_0_12_0_0_2, CS_0_0_0_12_0_0_3, CS_0_0_0_12_0_0_4, CS_0_0_0_12_0_0_5, CS_0_0_0_12_0_0_6);
	public final static Choice CS_0_0_0_12_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_12_0_0);
	public final static Compound CS_0_0_0_12 = new Compound(CS_0_0_0_12_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static LineBreak CS_0_0_0_13_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static LineBreak CS_0_0_0_13_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_13_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("OPTIONS", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_13_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("{", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_13_0_0_4_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 1);
	public final static Containment CS_0_0_0_13_0_0_4_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__OPTIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_13_0_0_4_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(";", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_13_0_0_4_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_13_0_0_4_0_0_0, CS_0_0_0_13_0_0_4_0_0_1, CS_0_0_0_13_0_0_4_0_0_2);
	public final static Choice CS_0_0_0_13_0_0_4_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_13_0_0_4_0_0);
	public final static Compound CS_0_0_0_13_0_0_4 = new Compound(CS_0_0_0_13_0_0_4_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static LineBreak CS_0_0_0_13_0_0_5 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_13_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("}", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_13_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_13_0_0_0, CS_0_0_0_13_0_0_1, CS_0_0_0_13_0_0_2, CS_0_0_0_13_0_0_3, CS_0_0_0_13_0_0_4, CS_0_0_0_13_0_0_5, CS_0_0_0_13_0_0_6);
	public final static Choice CS_0_0_0_13_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_13_0_0);
	public final static Compound CS_0_0_0_13 = new Compound(CS_0_0_0_13_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static LineBreak CS_0_0_0_14_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static LineBreak CS_0_0_0_14_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_14_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("TOKENS", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_14_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("{", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_14_0_0_4_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 1);
	public final static Containment CS_0_0_0_14_0_0_4_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__TOKENS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_14_0_0_4_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(";", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_14_0_0_4_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_14_0_0_4_0_0_0, CS_0_0_0_14_0_0_4_0_0_1, CS_0_0_0_14_0_0_4_0_0_2);
	public final static Choice CS_0_0_0_14_0_0_4_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_14_0_0_4_0_0);
	public final static Compound CS_0_0_0_14_0_0_4 = new Compound(CS_0_0_0_14_0_0_4_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static LineBreak CS_0_0_0_14_0_0_5 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_14_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("}", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_14_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_14_0_0_0, CS_0_0_0_14_0_0_1, CS_0_0_0_14_0_0_2, CS_0_0_0_14_0_0_3, CS_0_0_0_14_0_0_4, CS_0_0_0_14_0_0_5, CS_0_0_0_14_0_0_6);
	public final static Choice CS_0_0_0_14_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_14_0_0);
	public final static Compound CS_0_0_0_14 = new Compound(CS_0_0_0_14_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static LineBreak CS_0_0_0_15_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static LineBreak CS_0_0_0_15_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_15_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("TOKENSTYLES", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_15_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("{", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_15_0_0_4_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 1);
	public final static Containment CS_0_0_0_15_0_0_4_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__TOKEN_STYLES), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_15_0_0_4_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_15_0_0_4_0_0_0, CS_0_0_0_15_0_0_4_0_0_1);
	public final static Choice CS_0_0_0_15_0_0_4_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_15_0_0_4_0_0);
	public final static Compound CS_0_0_0_15_0_0_4 = new Compound(CS_0_0_0_15_0_0_4_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static LineBreak CS_0_0_0_15_0_0_5 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_15_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("}", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0_15_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_15_0_0_0, CS_0_0_0_15_0_0_1, CS_0_0_0_15_0_0_2, CS_0_0_0_15_0_0_3, CS_0_0_0_15_0_0_4, CS_0_0_0_15_0_0_5, CS_0_0_0_15_0_0_6);
	public final static Choice CS_0_0_0_15_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_15_0_0);
	public final static Compound CS_0_0_0_15 = new Compound(CS_0_0_0_15_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static LineBreak CS_0_0_0_16 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static LineBreak CS_0_0_0_17 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_18 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("RULES", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_19 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("{", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_20_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 1);
	public final static Containment CS_0_0_0_20_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONCRETE_SYNTAX__RULES), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static Sequence CS_0_0_0_20_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_20_0_0_0, CS_0_0_0_20_0_0_1);
	public final static Choice CS_0_0_0_20_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_20_0_0);
	public final static Compound CS_0_0_0_20 = new Compound(CS_0_0_0_20_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_0_0_0_21 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_0_0_0_22 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("}", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_0_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0_0, CS_0_0_0_1, CS_0_0_0_2, CS_0_0_0_3, CS_0_0_0_4, CS_0_0_0_5, CS_0_0_0_6, CS_0_0_0_7, CS_0_0_0_8, CS_0_0_0_9, CS_0_0_0_10, CS_0_0_0_11, CS_0_0_0_12, CS_0_0_0_13, CS_0_0_0_14, CS_0_0_0_15, CS_0_0_0_16, CS_0_0_0_17, CS_0_0_0_18, CS_0_0_0_19, CS_0_0_0_20, CS_0_0_0_21, CS_0_0_0_22);
	public final static Choice CS_0_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_0_0_0);
	public final static Rule CS_0 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax(), CS_0_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_1_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getImport().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.IMPORT__PREFIX), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_1_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(":", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_1_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getImport().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.IMPORT__PACKAGE), "QUOTED_60_62", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_1_0_0_3_0_0_0 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_1_0_0_3_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getImport().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.IMPORT__PACKAGE_LOCATION_HINT), "QUOTED_60_62", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_1_0_0_3_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_3_0_0_0, CS_1_0_0_3_0_0_1);
	public final static Choice CS_1_0_0_3_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_3_0_0);
	public final static Compound CS_1_0_0_3 = new Compound(CS_1_0_0_3_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static WhiteSpaces CS_1_0_0_4_0_0_0 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_1_0_0_4_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("WITH", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_1_0_0_4_0_0_2 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_1_0_0_4_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("SYNTAX", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_1_0_0_4_0_0_4 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_1_0_0_4_0_0_5 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getImport().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.IMPORT__CONCRETE_SYNTAX), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_1_0_0_4_0_0_6_0_0_0 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_1_0_0_4_0_0_6_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getImport().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.IMPORT__CS_LOCATION_HINT), "QUOTED_60_62", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_1_0_0_4_0_0_6_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_4_0_0_6_0_0_0, CS_1_0_0_4_0_0_6_0_0_1);
	public final static Choice CS_1_0_0_4_0_0_6_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_4_0_0_6_0_0);
	public final static Compound CS_1_0_0_4_0_0_6 = new Compound(CS_1_0_0_4_0_0_6_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_1_0_0_4_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_4_0_0_0, CS_1_0_0_4_0_0_1, CS_1_0_0_4_0_0_2, CS_1_0_0_4_0_0_3, CS_1_0_0_4_0_0_4, CS_1_0_0_4_0_0_5, CS_1_0_0_4_0_0_6);
	public final static Choice CS_1_0_0_4_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_4_0_0);
	public final static Compound CS_1_0_0_4 = new Compound(CS_1_0_0_4_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_1_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0_0, CS_1_0_0_1, CS_1_0_0_2, CS_1_0_0_3, CS_1_0_0_4);
	public final static Choice CS_1_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_1_0_0);
	public final static Rule CS_1 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getImport(), CS_1_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_2_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getOption().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.OPTION__TYPE), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_2_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("=", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_2_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getOption().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.OPTION__VALUE), "QUOTED_34_34_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_2_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_2_0_0_0, CS_2_0_0_1, CS_2_0_0_2);
	public final static Choice CS_2_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_2_0_0);
	public final static Rule CS_2 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getOption(), CS_2_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_3_0_0_0_0_0_0 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static Containment CS_3_0_0_0_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getRule().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.RULE__ANNOTATIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_3_0_0_0_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_3_0_0_0_0_0_0, CS_3_0_0_0_0_0_1);
	public final static Choice CS_3_0_0_0_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_3_0_0_0_0_0);
	public final static Compound CS_3_0_0_0 = new Compound(CS_3_0_0_0_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static LineBreak CS_3_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_3_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getRule().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.RULE__METACLASS), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_3_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("::=", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_3_0_0_4 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getRule().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.RULE__DEFINITION), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_3_0_0_5 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(";", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_3_0_0_6 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static Sequence CS_3_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_3_0_0_0, CS_3_0_0_1, CS_3_0_0_2, CS_3_0_0_3, CS_3_0_0_4, CS_3_0_0_5, CS_3_0_0_6);
	public final static Choice CS_3_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_3_0_0);
	public final static Rule CS_3 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getRule(), CS_3_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_4_0_0_0 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getSequence().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.SEQUENCE__PARTS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.PLUS);
	public final static Sequence CS_4_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_4_0_0_0);
	public final static Choice CS_4_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_4_0_0);
	public final static Rule CS_4 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getSequence(), CS_4_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_5_0_0_0 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getChoice().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CHOICE__OPTIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_5_0_0_1_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("|", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_5_0_0_1_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getChoice().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CHOICE__OPTIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_5_0_0_1_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_5_0_0_1_0_0_0, CS_5_0_0_1_0_0_1);
	public final static Choice CS_5_0_0_1_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_5_0_0_1_0_0);
	public final static Compound CS_5_0_0_1 = new Compound(CS_5_0_0_1_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static WhiteSpaces CS_5_0_0_2 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_5_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_5_0_0_0, CS_5_0_0_1, CS_5_0_0_2);
	public final static Choice CS_5_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_5_0_0);
	public final static Rule CS_5 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getChoice(), CS_5_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_6_0_0_0 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_6_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getCsString().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CS_STRING__VALUE), "QUOTED_34_34_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_6_0_0_2 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_6_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_6_0_0_0, CS_6_0_0_1, CS_6_0_0_2);
	public final static Choice CS_6_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_6_0_0);
	public final static Rule CS_6 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getCsString(), CS_6_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_7_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingSpecifiedToken().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_USING_SPECIFIED_TOKEN__FEATURE), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_7_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("[", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_7_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingSpecifiedToken().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_USING_SPECIFIED_TOKEN__TOKEN), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_7_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("]", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_7_0_0_4 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingSpecifiedToken().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_USING_SPECIFIED_TOKEN__CARDINALITY), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_7_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_7_0_0_0, CS_7_0_0_1, CS_7_0_0_2, CS_7_0_0_3, CS_7_0_0_4);
	public final static Choice CS_7_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_7_0_0);
	public final static Rule CS_7 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingSpecifiedToken(), CS_7_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_8_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingDefaultToken().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_USING_DEFAULT_TOKEN__FEATURE), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_8_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("[", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_8_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("]", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_8_0_0_3 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingDefaultToken().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_USING_DEFAULT_TOKEN__CARDINALITY), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_8_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_8_0_0_0, CS_8_0_0_1, CS_8_0_0_2, CS_8_0_0_3);
	public final static Choice CS_8_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_8_0_0);
	public final static Rule CS_8 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderUsingDefaultToken(), CS_8_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_9_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_IN_QUOTES__FEATURE), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_9_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("[", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_9_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_IN_QUOTES__PREFIX), "QUOTED_39_39_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_9_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(",", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_9_0_0_4 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_IN_QUOTES__SUFFIX), "QUOTED_39_39_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_9_0_0_5_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(",", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_9_0_0_5_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_IN_QUOTES__ESCAPE_CHARACTER), "QUOTED_39_39_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_9_0_0_5_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_9_0_0_5_0_0_0, CS_9_0_0_5_0_0_1);
	public final static Choice CS_9_0_0_5_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_9_0_0_5_0_0);
	public final static Compound CS_9_0_0_5 = new Compound(CS_9_0_0_5_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_9_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("]", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_9_0_0_7 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_9_0_0_8 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PLACEHOLDER_IN_QUOTES__CARDINALITY), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_9_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_9_0_0_0, CS_9_0_0_1, CS_9_0_0_2, CS_9_0_0_3, CS_9_0_0_4, CS_9_0_0_5, CS_9_0_0_6, CS_9_0_0_7, CS_9_0_0_8);
	public final static Choice CS_9_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_9_0_0);
	public final static Rule CS_9 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPlaceholderInQuotes(), CS_9_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_10_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getContainment().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONTAINMENT__FEATURE), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_10_0_0_1_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(":", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_10_0_0_1_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getContainment().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONTAINMENT__TYPES), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_10_0_0_1_0_0_2_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(",", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_10_0_0_1_0_0_2_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getContainment().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONTAINMENT__TYPES), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_10_0_0_1_0_0_2_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_10_0_0_1_0_0_2_0_0_0, CS_10_0_0_1_0_0_2_0_0_1);
	public final static Choice CS_10_0_0_1_0_0_2_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_10_0_0_1_0_0_2_0_0);
	public final static Compound CS_10_0_0_1_0_0_2 = new Compound(CS_10_0_0_1_0_0_2_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static Sequence CS_10_0_0_1_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_10_0_0_1_0_0_0, CS_10_0_0_1_0_0_1, CS_10_0_0_1_0_0_2);
	public final static Choice CS_10_0_0_1_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_10_0_0_1_0_0);
	public final static Compound CS_10_0_0_1 = new Compound(CS_10_0_0_1_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Containment CS_10_0_0_2 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getContainment().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.CONTAINMENT__CARDINALITY), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static WhiteSpaces CS_10_0_0_3 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_10_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_10_0_0_0, CS_10_0_0_1, CS_10_0_0_2, CS_10_0_0_3);
	public final static Choice CS_10_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_10_0_0);
	public final static Rule CS_10 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getContainment(), CS_10_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_11_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("(", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_11_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getCompoundDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.COMPOUND_DEFINITION__DEFINITIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_11_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(")", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_11_0_0_3 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getCompoundDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.COMPOUND_DEFINITION__CARDINALITY), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_11_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_11_0_0_0, CS_11_0_0_1, CS_11_0_0_2, CS_11_0_0_3);
	public final static Choice CS_11_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_11_0_0);
	public final static Rule CS_11 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getCompoundDefinition(), CS_11_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_12_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getWhiteSpaces().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.WHITE_SPACES__AMOUNT), "HEXNUMBER", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_12_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_12_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_12_0_0_0, CS_12_0_0_1);
	public final static Choice CS_12_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_12_0_0);
	public final static Rule CS_12 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getWhiteSpaces(), CS_12_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_13_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("!", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_13_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getLineBreak().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.LINE_BREAK__TAB), "NUMBER", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_13_0_0_2 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_13_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_13_0_0_0, CS_13_0_0_1, CS_13_0_0_2);
	public final static Choice CS_13_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_13_0_0);
	public final static Rule CS_13 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getLineBreak(), CS_13_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_14_0_0_0_0_0_0 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getNormalTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.NORMAL_TOKEN_DEFINITION__ANNOTATIONS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static LineBreak CS_14_0_0_0_0_0_1 = new LineBreak(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, 0);
	public final static Sequence CS_14_0_0_0_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_0_0_0_0, CS_14_0_0_0_0_0_1);
	public final static Choice CS_14_0_0_0_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_0_0_0);
	public final static Compound CS_14_0_0_0 = new Compound(CS_14_0_0_0_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_14_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("DEFINE", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_14_0_0_2 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_14_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getNormalTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.NORMAL_TOKEN_DEFINITION__NAME), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_14_0_0_4 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getNormalTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.NORMAL_TOKEN_DEFINITION__REGEX_PARTS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_14_0_0_5_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("+", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_14_0_0_5_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getNormalTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.NORMAL_TOKEN_DEFINITION__REGEX_PARTS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_14_0_0_5_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_5_0_0_0, CS_14_0_0_5_0_0_1);
	public final static Choice CS_14_0_0_5_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_5_0_0);
	public final static Compound CS_14_0_0_5 = new Compound(CS_14_0_0_5_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static WhiteSpaces CS_14_0_0_6_0_0_0 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_14_0_0_6_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("COLLECT", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_14_0_0_6_0_0_2 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_14_0_0_6_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("IN", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_14_0_0_6_0_0_4 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_14_0_0_6_0_0_5 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getNormalTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.NORMAL_TOKEN_DEFINITION__ATTRIBUTE_NAME), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_14_0_0_6_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_6_0_0_0, CS_14_0_0_6_0_0_1, CS_14_0_0_6_0_0_2, CS_14_0_0_6_0_0_3, CS_14_0_0_6_0_0_4, CS_14_0_0_6_0_0_5);
	public final static Choice CS_14_0_0_6_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_6_0_0);
	public final static Compound CS_14_0_0_6 = new Compound(CS_14_0_0_6_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_14_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0_0, CS_14_0_0_1, CS_14_0_0_2, CS_14_0_0_3, CS_14_0_0_4, CS_14_0_0_5, CS_14_0_0_6);
	public final static Choice CS_14_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_14_0_0);
	public final static Rule CS_14 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getNormalTokenDefinition(), CS_14_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_15_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("DEFINE", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_15_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_15_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("FRAGMENT", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_15_0_0_3 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_15_0_0_4 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPartialTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PARTIAL_TOKEN_DEFINITION__NAME), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_15_0_0_5 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPartialTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PARTIAL_TOKEN_DEFINITION__REGEX_PARTS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_15_0_0_6_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("+", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_15_0_0_6_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPartialTokenDefinition().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.PARTIAL_TOKEN_DEFINITION__REGEX_PARTS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_15_0_0_6_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_15_0_0_6_0_0_0, CS_15_0_0_6_0_0_1);
	public final static Choice CS_15_0_0_6_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_15_0_0_6_0_0);
	public final static Compound CS_15_0_0_6 = new Compound(CS_15_0_0_6_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static Sequence CS_15_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_15_0_0_0, CS_15_0_0_1, CS_15_0_0_2, CS_15_0_0_3, CS_15_0_0_4, CS_15_0_0_5, CS_15_0_0_6);
	public final static Choice CS_15_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_15_0_0);
	public final static Rule CS_15 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPartialTokenDefinition(), CS_15_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_16_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("PRIORITIZE", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_16_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_16_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getTokenPriorityDirective().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.TOKEN_PRIORITY_DIRECTIVE__TOKEN), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_16_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_16_0_0_0, CS_16_0_0_1, CS_16_0_0_2);
	public final static Choice CS_16_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_16_0_0);
	public final static Rule CS_16 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getTokenPriorityDirective(), CS_16_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_17_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAtomicRegex().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.ATOMIC_REGEX__ATOMIC_EXPRESSION), "QUOTED_36_36", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_17_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_17_0_0_0);
	public final static Choice CS_17_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_17_0_0);
	public final static Rule CS_17 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAtomicRegex(), CS_17_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_18_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getRegexReference().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.REGEX_REFERENCE__TARGET), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_18_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_18_0_0_0);
	public final static Choice CS_18_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_18_0_0);
	public final static Rule CS_18 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getRegexReference(), CS_18_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_19_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("+", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_19_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_19_0_0_0);
	public final static Choice CS_19_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_19_0_0);
	public final static Rule CS_19 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getPLUS(), CS_19_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_20_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("*", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_20_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_20_0_0_0);
	public final static Choice CS_20_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_20_0_0);
	public final static Rule CS_20 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getSTAR(), CS_20_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_21_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("?", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_21_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_21_0_0_0);
	public final static Choice CS_21_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_21_0_0);
	public final static Rule CS_21 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getQUESTIONMARK(), CS_21_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_22_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("ABSTRACT", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_22_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_22_0_0_0);
	public final static Choice CS_22_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_22_0_0);
	public final static Rule CS_22 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAbstract(), CS_22_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_23_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getTokenStyle().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.TOKEN_STYLE__TOKEN_NAME), "QUOTED_34_34_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_23_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_23_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("COLOR", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_23_0_0_3 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_23_0_0_4 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getTokenStyle().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.TOKEN_STYLE__RGB), "HEXNUMBER", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_23_0_0_5_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(",", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static WhiteSpaces CS_23_0_0_5_0_0_1 = new WhiteSpaces(1, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_23_0_0_5_0_0_2 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getTokenStyle().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.TOKEN_STYLE__FONT_STYLES), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_23_0_0_5_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_23_0_0_5_0_0_0, CS_23_0_0_5_0_0_1, CS_23_0_0_5_0_0_2);
	public final static Choice CS_23_0_0_5_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_23_0_0_5_0_0);
	public final static Compound CS_23_0_0_5 = new Compound(CS_23_0_0_5_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_23_0_0_6 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(";", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_23_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_23_0_0_0, CS_23_0_0_1, CS_23_0_0_2, CS_23_0_0_3, CS_23_0_0_4, CS_23_0_0_5, CS_23_0_0_6);
	public final static Choice CS_23_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_23_0_0);
	public final static Rule CS_23 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getTokenStyle(), CS_23_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_24_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("@", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_24_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAnnotation().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.ANNOTATION__TYPE), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_24_0_0_2_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("(", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_24_0_0_2_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAnnotation().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.ANNOTATION__PARAMETERS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_24_0_0_2_0_0_2_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(",", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Containment CS_24_0_0_2_0_0_2_0_0_1 = new Containment(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAnnotation().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.ANNOTATION__PARAMETERS), org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_24_0_0_2_0_0_2_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_24_0_0_2_0_0_2_0_0_0, CS_24_0_0_2_0_0_2_0_0_1);
	public final static Choice CS_24_0_0_2_0_0_2_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_24_0_0_2_0_0_2_0_0);
	public final static Compound CS_24_0_0_2_0_0_2 = new Compound(CS_24_0_0_2_0_0_2_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.STAR);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_24_0_0_2_0_0_3 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword(")", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_24_0_0_2_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_24_0_0_2_0_0_0, CS_24_0_0_2_0_0_1, CS_24_0_0_2_0_0_2, CS_24_0_0_2_0_0_3);
	public final static Choice CS_24_0_0_2_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_24_0_0_2_0_0);
	public final static Compound CS_24_0_0_2 = new Compound(CS_24_0_0_2_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_24_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_24_0_0_0, CS_24_0_0_1, CS_24_0_0_2);
	public final static Choice CS_24_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_24_0_0);
	public final static Rule CS_24 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getAnnotation(), CS_24_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_25_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getKeyValuePair().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.KEY_VALUE_PAIR__KEY), "QUALIFIED_NAME", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword CS_25_0_0_1_0_0_0 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsKeyword("=", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder CS_25_0_0_1_0_0_1 = new org.emftext.sdk.concretesyntax.resource.cs.grammar.CsPlaceholder(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getKeyValuePair().getEStructuralFeature(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.KEY_VALUE_PAIR__VALUE), "QUOTED_34_34_92", org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
	public final static Sequence CS_25_0_0_1_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_25_0_0_1_0_0_0, CS_25_0_0_1_0_0_1);
	public final static Choice CS_25_0_0_1_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_25_0_0_1_0_0);
	public final static Compound CS_25_0_0_1 = new Compound(CS_25_0_0_1_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.QUESTIONMARK);
	public final static Sequence CS_25_0_0 = new Sequence(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_25_0_0_0, CS_25_0_0_1);
	public final static Choice CS_25_0 = new Choice(org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE, CS_25_0_0);
	public final static Rule CS_25 = new Rule(org.emftext.sdk.concretesyntax.ConcretesyntaxPackage.eINSTANCE.getKeyValuePair(), CS_25_0, org.emftext.sdk.concretesyntax.resource.cs.grammar.CsCardinality.ONE);
}
