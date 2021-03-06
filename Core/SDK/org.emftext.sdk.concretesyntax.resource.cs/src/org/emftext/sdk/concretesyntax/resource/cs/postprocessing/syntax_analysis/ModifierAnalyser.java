/*******************************************************************************
 * Copyright (c) 2006-2012
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
package org.emftext.sdk.concretesyntax.resource.cs.postprocessing.syntax_analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.ConcretesyntaxPackage;
import org.emftext.sdk.concretesyntax.resource.cs.ICsQuickFix;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.CsAnalysisProblemType;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.AbstractPostProcessor;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.quickfixes.MakeSyntaxConcreteFix;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.quickfixes.RemoveReferenceQuickFix;

/**
 * An analyser that checks whether the modifier ABSTRACT is used
 * correctly.
 */
public class ModifierAnalyser extends AbstractPostProcessor {

	@Override
	public void analyse(ConcreteSyntax syntax) {
		List<GenClass> symbols = syntax.getActiveStartSymbols();
		if (syntax.isAbstract()) {
			Collection<ICsQuickFix> quickFixes = new ArrayList<ICsQuickFix>(2);
			quickFixes.add(new MakeSyntaxConcreteFix(syntax));
			quickFixes.add(new RemoveReferenceQuickFix("Remove start symbols", syntax, ConcretesyntaxPackage.eINSTANCE.getConcreteSyntax_StartSymbols(), symbols));
			// assert there is no start symbol (not a 
			// declared one and not an imported one)
			if (symbols.size() > 0) {
				addProblem(
						CsAnalysisProblemType.ABSTRACT_SYNTAX_HAS_START_SYMBOLS, 
						"Syntax has start symbols (" + getListOfNames(symbols) + "), but is declared abstract. Note that these start symbols are thrown away during import.", 
						syntax, 
						quickFixes
				);
			}
		} else {
			// assert the is at least one start symbol (either a 
			// declared one or an imported one)
			if (symbols.size() == 0) {
				addProblem(
						CsAnalysisProblemType.CONCRETE_SYNTAX_HAS_NO_START_SYMBOLS, 
						"Syntax has no start symbols, but is not declared abstract.", 
						syntax
				);
			}
		}
	}

	private String getListOfNames(List<GenClass> classes) {
		boolean isFirst = true;
		String listOfNames = "";
		for (GenClass symbol : classes) {
			if (!isFirst) {
				listOfNames += ", ";
			}
			isFirst = false;
			listOfNames += symbol.getName();
		}
		return listOfNames;
	}
}
