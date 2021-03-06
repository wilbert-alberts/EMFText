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
package org.emftext.sdk.codegen.generators;

import java.io.PrintWriter;

import org.emftext.sdk.codegen.AbstractGenerator;
import org.emftext.sdk.codegen.IContext;
import org.emftext.sdk.codegen.parameters.ClassPathParameters;

import de.devboost.codecomposers.StringComposite;
import de.devboost.codecomposers.xml.XMLComposite;

/**
 * Creates the content for .classpath files, which are used by Eclipse to determine the
 * classes used by generated plug-ins. The content of the file is determined by
 * the given parameters.
 */
public class DotClasspathGenerator<ContextType extends IContext<ContextType>> extends AbstractGenerator<ContextType, ClassPathParameters<ContextType>> {

	public DotClasspathGenerator() {
		super();
	}

	@Override
	public void doGenerate(PrintWriter out) {
		StringComposite sc = new XMLComposite();
		
		sc.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sc.add("<classpath>");
		for (String sourceFolder : getParameters().getSourceFolders()) {
			sc.add("<classpathentry kind=\"src\" path=\"" + sourceFolder + "\"/>");
		}
		for (String libraryPath : getParameters().getAdditionalLibraries()) {
			sc.add("<classpathentry exported=\"true\" kind=\"lib\" path=\"" + libraryPath + "\"/>");
		}
		// TODO Add CS option to configure JDK
		sc.add("<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.6\"/>");
		sc.add("<classpathentry kind=\"con\" path=\"org.eclipse.pde.core.requiredPlugins\"/>");
		sc.add("<classpathentry kind=\"output\" path=\"bin\"/>");
		sc.add("</classpath>");
		
		out.write(sc.toString());
	}
}
