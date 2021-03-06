/*******************************************************************************
 * Copyright (c) 2006-2014
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

package org.emftext.sdk.concretesyntax.resource.cs.ui;

import org.eclipse.core.resources.IResource;

public class CsUIMetaInformation extends org.emftext.sdk.concretesyntax.resource.cs.mopp.CsMetaInformation {
	
	public org.emftext.sdk.concretesyntax.resource.cs.ICsHoverTextProvider getHoverTextProvider() {
		return new org.emftext.sdk.concretesyntax.resource.cs.ui.CsHoverTextProvider();
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.ui.CsImageProvider getImageProvider() {
		return org.emftext.sdk.concretesyntax.resource.cs.ui.CsImageProvider.INSTANCE;
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.ui.CsColorManager createColorManager() {
		return new org.emftext.sdk.concretesyntax.resource.cs.ui.CsColorManager();
	}
	
	/**
	 * @deprecated this method is only provided to preserve API compatibility. Use
	 * createTokenScanner(org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource,
	 * org.emftext.sdk.concretesyntax.resource.cs.ui.CsColorManager) instead.
	 */
	public org.emftext.sdk.concretesyntax.resource.cs.ui.CsTokenScanner createTokenScanner(org.emftext.sdk.concretesyntax.resource.cs.ui.CsColorManager colorManager) {
		return (org.emftext.sdk.concretesyntax.resource.cs.ui.CsTokenScanner) createTokenScanner(null, colorManager);
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.ui.ICsTokenScanner createTokenScanner(org.emftext.sdk.concretesyntax.resource.cs.ICsTextResource resource, org.emftext.sdk.concretesyntax.resource.cs.ui.CsColorManager colorManager) {
		return new org.emftext.sdk.concretesyntax.resource.cs.ui.CsTokenScanner(resource, colorManager);
	}
	
	public org.emftext.sdk.concretesyntax.resource.cs.ui.CsCodeCompletionHelper createCodeCompletionHelper() {
		return new org.emftext.sdk.concretesyntax.resource.cs.ui.CsCodeCompletionHelper();
	}
	
	@SuppressWarnings("rawtypes")
	public Object createResourceAdapter(Object adaptableObject, Class adapterType, IResource resource) {
		return new org.emftext.sdk.concretesyntax.resource.cs.ui.debug.CsLineBreakpointAdapter();
	}
	
}
