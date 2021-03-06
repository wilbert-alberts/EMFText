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
package org.emftext.sdk.generatorconfig.resource.generatorconfig;

// An extended resource that can hold information about the exact positions
// of each element of its content in a text file. This can be used to give
// more detailed error feedback.
//
public interface IGeneratorconfigTextResource extends org.eclipse.emf.ecore.resource.Resource, org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigTextResourcePluginPart {

	// Try to load the content of this resource from the given stream. If
	// loading fails, the state of this resource is kept. If loading is
	// successful, the content of this resource is replaced with the new
	// content.
	// This method can be used to try loading erroneous files, as e.g.,
	// needed during background parsing in the editor.
	//
	// @param stream the stream to read from
	// @param options the load options to use
	// @throws java.io.IOException thrown if the stream can not be read
	public void reload(java.io.InputStream stream, java.util.Map<?,?> options) throws java.io.IOException;

	// Try to cancel a current reload of this resource. It is not guaranteed
	// that canceling is successful. If this resource has already finished
	// parsing the new content, it will replace its content unconditionally.
	public void cancelReload();

	// Returns a map containing information about the location of model
	// elements in the text.
	//
	// @return the model element to text location mapping
	public org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigLocationMap getLocationMap();

	// Add an error that should be displayed at the position of the given element.
	public void addProblem(org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigProblem problem, org.eclipse.emf.ecore.EObject element);

	// Add an error to be displayed at the indicated position.
	public void addProblem(org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigProblem problem, int column, int line, int charStart, int charEnd);

	// Internal method used by the parser to register a context dependent proxy object for later resolution.
	//
	// @param container
	// @param reference
	// @param pos
	// @param id
	// @param proxyElement
	public <ContainerType extends org.eclipse.emf.ecore.EObject, ReferenceType extends org.eclipse.emf.ecore.EObject> void registerContextDependentProxy(org.emftext.sdk.generatorconfig.resource.generatorconfig.IGeneratorconfigContextDependentURIFragmentFactory<ContainerType, ReferenceType> factory, ContainerType container, org.eclipse.emf.ecore.EReference reference, java.lang.String id, org.eclipse.emf.ecore.EObject proxyElement);
}
