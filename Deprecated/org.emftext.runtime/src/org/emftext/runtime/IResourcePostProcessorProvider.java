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
package org.emftext.runtime;

/**
 * Implementors of this interface can provide a post-processor
 * for text resources.
 */
public interface IResourcePostProcessorProvider {

	/**
	 * Returns the processor that shall be called after text
	 * resource are successfully parsed.
	 */
	public IResourcePostProcessor getResourcePostProcessor();
}
