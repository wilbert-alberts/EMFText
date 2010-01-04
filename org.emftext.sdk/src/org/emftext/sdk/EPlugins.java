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
package org.emftext.sdk;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.emftext.sdk.codegen.OptionManager;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.OptionTypes;

/**
 * An enumeration of all plug-in types that might be generated by EMFText.
 */
public enum EPlugins {

	RESOURCE_PLUGIN(OptionTypes.RESOURCE_PLUGIN_ID) {

		@Override
		public String getPluginName(ConcreteSyntax syntax) {
			return getBasePackage(syntax);
		}

		@Override
		public String getBasePackage(ConcreteSyntax syntax) {
			String basePackage = OptionManager.INSTANCE.getStringOptionValue(syntax, OptionTypes.BASE_PACKAGE);
			if (basePackage != null) {
				// use package name from option
				return basePackage;
			} else {
				String packageName = "";
				// use default package name
				GenPackage concreteSyntaxPackage = syntax.getPackage();
				boolean hasBasePackage = concreteSyntaxPackage.getBasePackage() != null;
				if (hasBasePackage) {
					packageName = concreteSyntaxPackage.getBasePackage() + ".";
				}
				packageName += concreteSyntaxPackage.getEcorePackage().getName();
				packageName += ".resource." + syntax.getName();
				return packageName;
			}
		}
	},
	ANTLR_PLUGIN(OptionTypes.ANTLR_PLUGIN_ID) {
		
		@Override
		public String getPluginName(ConcreteSyntax syntax) {
			return "org.emftext.commons.antlr3_2_0";
		}

		@Override
		public String getBasePackage(ConcreteSyntax syntax) {
			return "";
		}
	};
	
	private OptionTypes option;
	
	private EPlugins(OptionTypes option) {
		this.option = option;
	}
	
	protected abstract String getPluginName(ConcreteSyntax syntax);
	public abstract String getBasePackage(ConcreteSyntax syntax);
	
	public String getName(ConcreteSyntax syntax) {
		String pluginID = OptionManager.INSTANCE.getStringOptionValue(syntax, option);
		if (pluginID != null) {
			// use package plug-in from option
			return pluginID;
		} else {
			// use default plug-in name
			return getPluginName(syntax);
		}
	}
}
