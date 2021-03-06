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
import java.util.List;

import org.emftext.sdk.concretesyntax.CompleteTokenDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.TokenRedefinition;
import org.emftext.sdk.concretesyntax.resource.cs.mopp.CsAnalysisProblemType;
import org.emftext.sdk.concretesyntax.resource.cs.postprocessing.AbstractPostProcessor;

/**
 * An analyser that checks that each token name is used at most once.
 */
public class DuplicateTokenNameAnalyser extends AbstractPostProcessor {

	public void analyse(ConcreteSyntax syntax) {
		List<CompleteTokenDefinition> duplicateDefinitions = getDuplicateTokenDefinitions(syntax);
		for (CompleteTokenDefinition duplicate : duplicateDefinitions) {
			addProblem(CsAnalysisProblemType.DUPLICATE_TOKEN_NAME, "Duplicate token name " + duplicate.getName() + " (names are not case sensitive).", duplicate);
		}
	}

	public List<CompleteTokenDefinition> getDuplicateTokenDefinitions(ConcreteSyntax syntax) {
		List<CompleteTokenDefinition> duplicateTokens = new ArrayList<CompleteTokenDefinition>();
		
		List<String> foundTokenNames = new ArrayList<String>();
		List<CompleteTokenDefinition> tokens = syntax.getActiveTokens();
		for (int i = 0; i < tokens.size(); i++) {
			CompleteTokenDefinition token_i = tokens.get(i);
			if (token_i instanceof TokenRedefinition) {
				continue;
			}
			String token_i_name = token_i.getName();
			if (foundTokenNames.contains(token_i_name.toLowerCase())) {
				continue;
			}
			
			foundTokenNames.add(token_i_name.toLowerCase());
			
			boolean foundDuplicate = false;
			// name was not found before
			for (int j = i + 1; j < tokens.size(); j++) {
				CompleteTokenDefinition token_j = tokens.get(j);
				if (token_j.getName().equalsIgnoreCase(token_i_name)) {
					duplicateTokens.add(token_j);
					foundDuplicate = true;
				}
			}
			if (foundDuplicate) {
				duplicateTokens.add(token_i);
			}
		}
		return duplicateTokens;
	}
}
