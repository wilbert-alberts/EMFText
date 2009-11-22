/*******************************************************************************
 * Copyright (c) 2006-2009 
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
package org.emftext.sdk.codegen.generators.util;

import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.JavaBaseGenerator;

public class CastUtilGenerator extends JavaBaseGenerator {

	public CastUtilGenerator() {
		super();
	}

	private CastUtilGenerator(GenerationContext context) {
		super(context, EArtifact.CAST_UTIL);
	}

	public IGenerator newInstance(GenerationContext context) {
		return new CastUtilGenerator(context);
	}

	public boolean generateJavaContents(StringComposite sc) {
		
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		
		sc.add("// Utility class that provides a method to cast objects to");
		sc.add("// type parameterized classes without a warning.");
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();
		sc.add("@SuppressWarnings(\"unchecked\")").addLineBreak();
		sc.add("public static <T> T cast(Object temp) {");
		sc.add("return (T) temp;");
		sc.add("}");
		sc.add("}");
		return true;
	}
}
