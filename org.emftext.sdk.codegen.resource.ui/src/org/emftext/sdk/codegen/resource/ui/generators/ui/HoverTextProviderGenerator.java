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
package org.emftext.sdk.codegen.resource.ui.generators.ui;

import static org.emftext.sdk.codegen.resource.ui.IUIClassNameConstants.E_OBJECT;
import static org.emftext.sdk.codegen.resource.ui.IUIClassNameConstants.STRING;

import org.emftext.sdk.codegen.ICodeGenerationComponent;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.GeneratorProvider;
import org.emftext.sdk.codegen.resource.GenerationContext;
import org.emftext.sdk.codegen.resource.ui.TextResourceUIArtifacts;
import org.emftext.sdk.codegen.resource.ui.generators.UIJavaBaseGenerator;
import org.emftext.sdk.concretesyntax.OptionTypes;

public class HoverTextProviderGenerator extends UIJavaBaseGenerator {
	
	public static final GeneratorProvider<GenerationContext, Object> PROVIDER = 
		new GeneratorProvider<GenerationContext, Object>(new HoverTextProviderGenerator());

	private HoverTextProviderGenerator() {
		super();
	}

	private HoverTextProviderGenerator(ICodeGenerationComponent parent, GenerationContext context) {
		super(parent, context, TextResourceUIArtifacts.HOVER_TEXT_PROVIDER);
	}

	@Override
	public void generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " implements " + iHoverTextProviderClassName + " {");
		sc.addLineBreak();
		addFields(sc);
		addGetHoverTestMethod(sc);
		sc.add("}");
	}

	private void addFields(StringComposite sc) {
		sc.add("private " + defaultHoverTextProviderClassName + " defaultProvider = new " + defaultHoverTextProviderClassName + "();");
		sc.addLineBreak();
	}

	private void addGetHoverTestMethod(JavaComposite sc) {
		sc.add("public " + STRING + " getHoverText(" + E_OBJECT + " object) {");
		sc.addComment(
			"Set option " + OptionTypes.OVERRIDE_HOVER_TEXT_PROVIDER.getLiteral() + " to false and customize this method to obtain " +
			"custom hover texts."
		);
		sc.add("return defaultProvider.getHoverText(object);");
		sc.add("}");
		sc.addLineBreak();
	}

	public IGenerator<GenerationContext, Object> newInstance(ICodeGenerationComponent parent, GenerationContext context, Object parameters) {
		return new HoverTextProviderGenerator(parent, context);
	}

}
