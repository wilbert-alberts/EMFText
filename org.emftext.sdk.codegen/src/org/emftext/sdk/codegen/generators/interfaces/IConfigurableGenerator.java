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

import static org.emftext.sdk.codegen.generators.IClassNameConstants.MAP;

import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.TextResourceArtifacts;
import org.emftext.sdk.codegen.composites.JavaComposite;
import org.emftext.sdk.codegen.generators.JavaBaseGenerator;

public class IConfigurableGenerator extends JavaBaseGenerator {

	public IConfigurableGenerator() {
		super();
	}

	private IConfigurableGenerator(GenerationContext context) {
		super(context, TextResourceArtifacts.I_CONFIGURABLE);
	}

	public IGenerator<GenerationContext> newInstance(GenerationContext context) {
		return new IConfigurableGenerator(context);
	}

	public boolean generateJavaContents(JavaComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.addJavadoc("Implementors of this interface can be configured by a map of options (or parameters).");
		sc.add("public interface " + getResourceClassName() + " {");
		sc.addLineBreak();
		
		sc.addJavadoc("Passes the options given by the map to the configurable object.");
		sc.add("public void setOptions(" + MAP + "<?,?> options);");
		sc.add("}");
		return true;
	}
}
