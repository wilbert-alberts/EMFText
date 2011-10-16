/*******************************************************************************
 * Copyright (c) 2006-2011
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

package org.emftext.sdk.concretesyntax.resource.cs.util;

/**
 * Class CsTextResourceUtil can be used to perform common tasks on text resources,
 * such as loading and saving resources, as well as, checking them for errors.
 * This class is deprecated and has been replaced by
 * org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.
 */
public class CsTextResourceUtil {
	
	/**
	 * Use
	 * org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.emftext.sdk.concretesyntax.resource.cs.mopp.CsResource getResource(org.eclipse.core.resources.IFile file) {
		return new org.emftext.sdk.concretesyntax.resource.cs.util.CsEclipseProxy().getResource(file);
	}
	
	/**
	 * Use
	 * org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.emftext.sdk.concretesyntax.resource.cs.mopp.CsResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource(file, options);
	}
	
	/**
	 * Use
	 * org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.emftext.sdk.concretesyntax.resource.cs.mopp.CsResource getResource(org.eclipse.emf.common.util.URI uri) {
		return org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource(uri);
	}
	
	/**
	 * Use
	 * org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource()
	 * instead.
	 */
	@Deprecated	
	public static org.emftext.sdk.concretesyntax.resource.cs.mopp.CsResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		return org.emftext.sdk.concretesyntax.resource.cs.util.CsResourceUtil.getResource(uri, options);
	}
	
}
