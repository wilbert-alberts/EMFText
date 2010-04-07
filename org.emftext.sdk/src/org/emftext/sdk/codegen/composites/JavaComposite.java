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
package org.emftext.sdk.codegen.composites;

/**
 * A custom StringComposite that is configured with the Java-specific
 * line break characters and indentation starter and stoppers.
 */
public class JavaComposite extends StringComposite {

	public JavaComposite() {
		super(true);
		addIndentationStarter("{");
		addIndentationStopper("}");
		addLineBreaker("{");
		addLineBreaker(";");
		addLineBreaker(",");
		addLineBreaker("}");
		addLineBreaker("*/");
	}

	@Override
	protected boolean isLineBreaker(Component component) {
		boolean superResult = super.isLineBreaker(component);
		if (superResult) {
			return true;
		}
		// add line breaks after single line comments
		String componentText = component.toString();
		boolean isSingleLineComment = componentText.contains("//");
		if (isSingleLineComment) {
			return true;
		}
		boolean isMultiLineComment = 
			"/*".equals(componentText) ||      // start of multi-line comment
			"/**".equals(componentText) ||     // start of Javadoc comment
			componentText.startsWith(" * ") || // intermediate line
			" */".equals(componentText);       // end of multi-line comment
		return isMultiLineComment;
	}

	public void addJavadoc(String text) {
		add("/**");
		// TODO mseifert: split text into chunks of 80 characters (split at space)
		add(" * " + text);
		add(" */");
	}
}
