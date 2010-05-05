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
package org.emftext.sdk.codegen.generators.interfaces;

import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.TextResourceArtifacts;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.generators.JavaBaseGenerator;

public class ITextScannerGenerator extends JavaBaseGenerator {

	public ITextScannerGenerator() {
		super();
	}

	private ITextScannerGenerator(GenerationContext context) {
		super(context, TextResourceArtifacts.I_TEXT_SCANNER);
	}

	public IGenerator<GenerationContext> newInstance(GenerationContext context) {
		return new ITextScannerGenerator(context);
	}

	public boolean generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.addJavadoc(
			"A common interface for scanners to be used by EMFText. " +
			"A scanner is initialized with a text and delivers a " +
			"sequence of tokens."
		);
		sc.add("public interface " + getResourceClassName() + " {");
		sc.addLineBreak();
		
		sc.addJavadoc("Sets the text that must be scanned.");
		sc.add("public void setText(String text);");
		sc.addLineBreak();
		
		sc.addJavadoc("Returns the next token found in the text.");
		sc.add("public " + iTextTokenClassName + " getNextToken();");
		sc.addLineBreak();
		
		sc.add("}");
		return true;
	}
}
